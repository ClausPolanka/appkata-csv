package appkata;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.readFileToString;

public class Main {
    private static final int ARGS_FILE_NAME = 0;
    private static final String FILE_ENCODING = "UTF-8";
    public static String generatedResult;

    public static void main(String... args) {
        CsvViewer csvViewer = new CsvViewer(new Console());
        csvViewer.view(fileContentOf(args[ARGS_FILE_NAME]));
        generatedResult = csvViewer.output();
    }

    private static String fileContentOf(String fileName) {
        try {
            readFileToString(new File(fileName), FILE_ENCODING);
        } catch (IOException e) {
            System.err.println("Problems reading file content.");
        }
        return "";
    }
}
