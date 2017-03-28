package eu.vytenis.opendata.experiments;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class CsvFilesTest {
    @Test
    public void allFilesArePresent() {
        Path path = new File(CsvFiles.DARZELIAI).toPath();
        assertTrue(Files.exists(path));
        assertTrue(Files.isReadable(path));
        
    }

}
