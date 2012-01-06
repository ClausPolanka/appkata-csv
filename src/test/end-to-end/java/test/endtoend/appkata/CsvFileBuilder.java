package test.endtoend.appkata;

import java.io.File;
import java.io.IOException;

import static appkata.Main.FILE_ENCODING;
import static junit.framework.Assert.assertTrue;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class CsvFileBuilder {
    private String fileName;
    private String fileContent;

    public CsvFileBuilder withName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public CsvFileBuilder containing(String fileContent) {
        this.fileContent = fileContent;
        return this;
    }

    public void build() {
        try {
            writeStringToFile(new File(fileName), fileContent, FILE_ENCODING);
        } catch (IOException e) {
            System.err.println("Problems occurred while creating a file and writing to it");
        }
        assertTrue("Csv file must exist", new File(fileName).exists());
    }
}
