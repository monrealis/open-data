package eu.vytenis.opendata.experiments;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValidColumnNameFinderTest {
    @Test
    public void doesNotChangeValidColumnName() {
        assertEquals("a", getValidColumnName("a"));
        assertEquals("a1", getValidColumnName("a1"));
    }

    @Test
    public void prefixesNumber() {
        assertEquals("_sk12a", getValidColumnName("12a"));
    }

    @Test
    public void translatesLithuanianLetters() {
        String input = "ąčęėįšųūžABCdefĄČĘĖĮŠŲŪŽ";
        assertEquals("aceeisuuzABCdefACEEISUUZ", getValidColumnName(input));
    }

    private String getValidColumnName(String input) {
        return new ValidColumnNameFinder(input).getValidColumnName();
    }
}
