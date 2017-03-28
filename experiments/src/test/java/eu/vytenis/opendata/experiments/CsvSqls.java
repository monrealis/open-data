package eu.vytenis.opendata.experiments;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVParser;

import com.google.common.base.Joiner;

public class CsvSqls {
    private final CSVParser parser;
    private final String tableName;

    public CsvSqls(CSVParser parser, String tableName) {
        this.parser = parser;
        this.tableName = tableName;
    }

    public String getDropTableIfExistsDdl() {
        return String.format("drop table if exists %s", tableName);
    }

    public String getCreateTableDdl() {
        String createTableDdl = String.format("create table %s (\n%s\n)", tableName, getColumnsDdl());
        return createTableDdl;
    }

    private String getColumnsDdl() {
        List<String> columns = new ArrayList<>();
        for (String column : getColumnHeaders())
            columns.add(String.format("  %s %s", column, "varchar"));
        return Joiner.on(",\n").join(columns);
    }

    String getInsertDml() {
        String qm = getQuestionMarksForPreparedInsert();
        return String.format("insert into %s values(%s)", tableName, qm);
    }

    private String getQuestionMarksForPreparedInsert() {
        List<String> questionMarks = new ArrayList<>();
        for (int i = 0; i < getColumnHeaders().size(); ++i)
            questionMarks.add("?");
        return Joiner.on(", ").join(questionMarks);
    }

    private List<String> getColumnHeaders() {
        return new ArrayList<>(parser.getHeaderMap().keySet());
    }
}