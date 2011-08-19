package appkata;

import java.io.InputStream;
import java.util.Scanner;

import static java.lang.System.lineSeparator;

public class Main {
    private static final int ARG_FILE_PATH = 0;
    private static final String FILE_ENCODING = "UTF-8";
    public static StringBuilder fileContent = new StringBuilder();

    public static void main(String... args) throws Exception {
        new CsvFile(readFileContentOf(args[ARG_FILE_PATH]));
    }

    public static String readFileContentOf(String filePath) {
        Scanner scanner = new Scanner(file(filePath), FILE_ENCODING);
        try {
            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine() + lineSeparator());
            }
        } finally {
            scanner.close();
        }
        return fileContent.toString().trim();
    }

    private static InputStream file(String filePath) {
        return Class.class.getResourceAsStream("/" + filePath);
    }
}
