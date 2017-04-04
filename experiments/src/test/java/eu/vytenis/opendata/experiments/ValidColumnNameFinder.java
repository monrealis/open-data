package eu.vytenis.opendata.experiments;

public class ValidColumnNameFinder {
    private String name;

    public ValidColumnNameFinder(String initialName) {
        name = initialName;
    }

    public String getValidColumnName() {
        name = name.replaceAll("\\s|\\.", "");
        name = name.replaceAll("\\(|\\)", "");
        name = name.replaceAll("/", "");
        if (name.matches("^[0-9].*"))
            name = "_sk" + name;
        name = translateLithuanianCharacters(name);
        if (name.toLowerCase().equals("rownum"))
            name = "rownum_";
        return name;
    }

    private String translateLithuanianCharacters(String name) {
        String source = "ąčęėįšųūžĄČĘĖĮŠŲŪŽ";
        String target = "aceeisuuzACEEISUUZ";
        char[] nameChars = name.toCharArray();
        for (int i = 0; i < source.length(); ++i)
            for (int j = 0; j < nameChars.length; ++j)
                if (nameChars[j] == source.charAt(i))
                    nameChars[j] = target.charAt(i);
        return new String(nameChars);
    }
}