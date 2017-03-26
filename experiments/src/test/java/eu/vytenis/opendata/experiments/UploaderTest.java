package eu.vytenis.opendata.experiments;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.input.BOMInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UploaderTest {
    private Connection connection;
    private Statement statement;
    private CSVParser input;

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
        createTable();
    }

    private CSVParser loadCsv() throws IOException {
        File file = new File("../modules/vilnius/darzeliai/data/darzeliai.csv");
        FileInputStream fis = new FileInputStream(file);
        BOMInputStream bis = new BOMInputStream(fis);
        Reader reader = new InputStreamReader(bis, StandardCharsets.UTF_8);
        CSVParser csv = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
        return csv;
    }

    private void createTable() throws SQLException {
        statement.executeUpdate("drop table if exists test");
        List<String> columns = new ArrayList<String>(input.getHeaderMap().keySet());
        String createTableDdl = String.format("create table test (%s varchar)", columns.get(0));
        System.out.println(createTableDdl);
        statement.executeUpdate(createTableDdl);
    }
}
