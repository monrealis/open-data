package eu.vytenis.opendata.experiments;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class CsvFilesTest {
    @Test
    public void allFilesArePresent() {
        assertReadable(CsvFiles.DARZELIAI);
        assertReadable(CsvFiles.DARZELIAI_GRUPES);
        assertReadable(CsvFiles.GRUPES);
        assertReadable(CsvFiles.ISTAIGOS);
        assertReadable(CsvFiles.PRIORITETAI);
        assertReadable(CsvFiles.PRASYMAI);
    }

    private void assertReadable(String darzeliai) {
        Path path = new File(darzeliai).toPath();
        assertTrue(Files.exists(path));
        assertTrue(Files.isReadable(path));
    }
}
