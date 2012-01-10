package test.endtoend.appkata;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static appkata.Main.FILE_ENCODING;
import static java.lang.System.lineSeparator;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class ApplicationRunner {
    private static final String APPLICATION_NAME = "csvviewer.bat";
    String output = "";

    public void startsCsvViewerForFile(String fileName) {
        try {
            cleanCsvViewerApplicationAssembly();
            assembleCsvViewerApplication();
            createCsvViewerBatchFile();
            runCsvViewerBatchFileFor(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanCsvViewerApplicationAssembly() {
        deleteQuietly(new File("target" + File.pathSeparator + "appkata-csv-1.0.0-SNAPSHOT-jar-with-dependencies.jar"));
    }

    private void assembleCsvViewerApplication() throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        Process mvn = rt.exec("c:/Dev/Programs/Maven/3.0.3/bin/mvn.bat package -DskipTests");
        mvn.waitFor();
    }

    private void createCsvViewerBatchFile() throws IOException {
        writeStringToFile(new File(APPLICATION_NAME),
            "@echo off" + lineSeparator() +
            "java -jar target/appkata-csv-1.0.0-SNAPSHOT-jar-with-dependencies.jar %1", FILE_ENCODING);
    }

    private void runCsvViewerBatchFileFor(String fileName) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process batchFile = rt.exec(APPLICATION_NAME + " " + fileName);
        saveOutputFor(batchFile);
    }

    private void saveOutputFor(Process batchFile) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(batchFile.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                output += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
