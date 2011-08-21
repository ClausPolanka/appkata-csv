package test.endtoend.appkata;

import appkata.Main;

public class ApplicationRunner {
    public String csvViewerOutput;

    public void startsCsvViewerForFile(String fileName) {
        Main.main(fileName);
        csvViewerOutput = Main.generatedResult;
    }
}
