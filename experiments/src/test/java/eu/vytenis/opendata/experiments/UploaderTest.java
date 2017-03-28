package eu.vytenis.opendata.experiments;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UploaderTest {
    private Connection connection;
    private Statement statement;
    private CSVParser input;
    private CsvSqls csv;

    @Before
    public void before() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        statement = connection.createStatement();
    }

    @After
    public void after() throws SQLException {
        statement.close();
        connection.close();
    }

    @Test
    public void run() throws IOException, SQLException {
        input = loadCsv();
        csv = new CsvSqls(input, "test");
        createTable();
        insertRows();
    }

    private CSVParser loadCsv() throws IOException {
        File file = new File(CsvFiles.DARZELIAI);
        FileInputStream fis = new FileInputStream(file);
        BOMInputStream bis = new BOMInputStream(fis);
        Reader reader = new InputStreamReader(bis, StandardCharsets.UTF_8);
        CSVParser csv = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
        return csv;
    }

    private void createTable() throws SQLException {
        statement.executeUpdate(csv.getDropTableIfExistsDdl());
        String createTableDdl = csv.getCreateTableDdl();
        System.out.println(createTableDdl);
        statement.executeUpdate(createTableDdl);
    }

    private void insertRows() throws IOException, SQLException {
        System.out.println(csv.getInsertDml());
        PreparedStatement ps = connection.prepareStatement(csv.getInsertDml());
        for (CSVRecord record : input.getRecords()) {
            for (int i = 0; i < record.size(); ++i)
                ps.setString(i + 1, record.get(i));
            ps.executeUpdate();
        }
        ps.close();
    }
}
