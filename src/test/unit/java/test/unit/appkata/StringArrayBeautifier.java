package test.unit.appkata;

public class StringArrayBeautifier {
    public static String[] asArray(String... contentRows) {
        String[] asArray = new String[contentRows.length];
        for (int i = 0; i < contentRows.length; i++) {
            asArray[i] = contentRows[i];
        }
        return asArray;
    }

    public static String[] emptyStringArray() {
        return asArray();
    }
}
