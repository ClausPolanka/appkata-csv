package test.endtoend.appkata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.Assert.assertTrue;

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
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(fileContent);
            out.close();
        } catch (IOException e) {
            System.err.println("Problems occured while creating a file and writing to it");
        }
        assertTrue("File exists", new File(fileName).exists());
    }
}
