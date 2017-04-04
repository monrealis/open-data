package eu.vytenis.opendata.experiments;

public class CsvFiles {
    public static final String DIR = "../modules/vilnius/darzeliai/data/";
    private static final String DARZELIAI = DIR + "darzeliai.csv";
    private static final String DARZELIAI_GRUPES = DIR + "darzeliai_grupes.csv";
    private static final String GRUPES = DIR + "grupes.csv";
    private static final String ISTAIGOS = DIR + "istaigos.csv";
    private static final String PRIORITETAI = DIR + "priorities_all.csv";
    private static final String PRASYMAI = DIR + "visi_prasymai.csv";

    public static String darzeliai() {
        return DARZELIAI;
    }

    public static String darzeliaiGrupes() {
        return DARZELIAI_GRUPES;
    }

    public static String grupes() {
        return GRUPES;
    }

    public static String istaigos() {
        return ISTAIGOS;
    }

    public static String prioritetai() {
        return PRIORITETAI;
    }

    public static String prasymai() {
        return PRASYMAI;
    }

}
