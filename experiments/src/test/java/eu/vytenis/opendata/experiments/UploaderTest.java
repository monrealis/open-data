package eu.vytenis.opendata.experiments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private String tableName;
    private CsvSqls csv;
    private PreparedStatement insertStatement;

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
    public void darzeliai() throws IOException, SQLException {
        fill(CsvFiles.darzeliai(), "darzeliai");
    }

    @Test
    public void prasymai() throws IOException, SQLException {
        fill(CsvFiles.prasymai(), "prasymai");
    }

    private void fill(String filename, String tableName) throws FileNotFoundException, IOException, SQLException {
        input = loadCsv(filename);
        this.tableName = tableName;
        fill();
    }

    private void fill() throws SQLException, IOException {
        csv = new CsvSqls(input, tableName);
        createTable();
        insertRows();
    }

    private CSVParser loadCsv(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
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
        insertStatement = connection.prepareStatement(csv.getInsertDml());
        for (CSVRecord record : input.getRecords())
            insert(record);
        insertStatement.close();
    }

    private void insert(CSVRecord record) throws SQLException {
        if (record.size() != getNumberOfColumns()) {
            System.err.println("Skip");
            return;
        }
        for (int i = 0; i < record.size(); ++i)
            insertStatement.setString(i + 1, record.get(i));
        insertStatement.executeUpdate();
    }

    private int getNumberOfColumns() {
        return input.getHeaderMap().size();
    }
}
