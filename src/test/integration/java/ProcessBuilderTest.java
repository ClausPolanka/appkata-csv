import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static appkata.Main.FILE_ENCODING;
import static java.lang.System.lineSeparator;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProcessBuilderTest {
    private static final String FILE_NAME = "simple.bat";
    private static final String EXPECTED_OUTPUT = "Hello World";
    private static final File CURRENT_DIRECTORY = new File(".");
    private static final String END_OF_LINE_WITHOUT_SPECIAL_LINE_CHAR_ENDING = "\\Z";

    @Test
    public void executeSelfMadeBatchFileAndReadOutput() throws Exception {
        writeStringToFile(new File(FILE_NAME), "@echo off" + lineSeparator() +
                                               "echo " + EXPECTED_OUTPUT, FILE_ENCODING);

        Process batchFile = new ProcessBuilder(FILE_NAME)
                            .directory(CURRENT_DIRECTORY)
                            .start();

        assertThat("Batch output", outputOf(batchFile), is(equalTo(EXPECTED_OUTPUT)));
    }

    private String outputOf(Process p) {
        Scanner s = new Scanner(p.getInputStream()).useDelimiter(END_OF_LINE_WITHOUT_SPECIAL_LINE_CHAR_ENDING);
        return s.next();
    }

    @AfterClass
    public static void deleteBatchFile() {
        deleteQuietly(new File(FILE_NAME));
    }
}
