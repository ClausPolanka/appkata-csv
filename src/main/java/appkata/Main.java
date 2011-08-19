package appkata;

import java.io.InputStream;
import java.util.Scanner;

import static java.lang.System.lineSeparator;

public class Main {
    private static final int ARG_FILE_PATH = 0;

    public static StringBuilder fileContent = new StringBuilder();

    public static void main(String... args) throws Exception {
        Scanner scanner = new Scanner(file(args[ARG_FILE_PATH]), "UTF-8");
        try {
            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine() + lineSeparator());
            }
        } finally {
            scanner.close();
        }
        new CsvFile(fileContent.toString().trim());
    }

    private static InputStream file(String filePath) {
        return Class.class.getResourceAsStream("/" + filePath);
    }
}
