package appkata;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.readFileToString;

public class Main {
    private static final int ARGS_FILE_NAME = 0;
    public static final String FILE_ENCODING = "UTF-8";

    public static void main(String... args) {
        CsvConverter csvConverter = new CsvConverter(new ConsoleDisplay());
        csvConverter.convert(fileContentOf(args[ARGS_FILE_NAME]));
    }

    private static String fileContentOf(String fileName) {
        try {
            return readFileToString(new File(fileName));
        } catch (IOException e) {
            System.err.println("Problems reading file content.");
        }
        return "";
    }
}
