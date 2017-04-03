package eu.vytenis.opendata.experiments;

public class ValidColumnNameFinder {
    private final String initialName;

    public ValidColumnNameFinder(String initialName) {
        this.initialName = initialName;
    }

    String getValidColumnName() {
        String name = initialName.replaceAll("\\s|\\.", "");
        name = name.replaceAll("\\(|\\)", "");
        name = name.replaceAll("/", "");
        if (name.matches("^[0-9].*"))
            name = "_sk" + name;
        return name;
    }
}