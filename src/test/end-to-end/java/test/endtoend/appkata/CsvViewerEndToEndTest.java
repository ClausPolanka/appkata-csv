package test.endtoend.appkata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static java.lang.System.lineSeparator;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class CsvViewerEndToEndTest {
    private static final String FILE_NAME = "test.csv";
    private final CsvFileBuilder aCsvFile = new CsvFileBuilder();
    private final ApplicationRunner client = new ApplicationRunner();
    private ByteArrayOutputStream fos;

    @Before
    public void redirectSystemOut() {
        fos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);
    }

    @Test
    public void showCsvFileContainingSeveralHeaderColumnsAndSeveralTableLines() throws Exception {
        String fileContent = "Name;Age;City;" + lineSeparator() +
                             "Peter;42;New York;" + lineSeparator() +
                             "Paul;57;London;" + lineSeparator() +
                             "Mary;35;Munich;";
        aCsvFile.withName(FILE_NAME).containing(fileContent).build();
        client.startsCsvViewerForFile(FILE_NAME);
        String expectedCsvViewerOutput = "Name |Age|City    |" + lineSeparator() +
                                         "-----+---+--------+" + lineSeparator() +
                                         "Peter|42 |New York|" + lineSeparator() +
                                         "Paul |57 |London  |" + lineSeparator() +
                                         "Mary |35 |Munich  |";
        assertEquals("Csv Viewer Output", expectedCsvViewerOutput, generatedCvsViewerOutput());
    }

    private String generatedCvsViewerOutput() {
        ByteArrayInputStream inStream = new ByteArrayInputStream(fos.toByteArray());
        int inBytes = inStream.available();
        byte inBuf[] = new byte[inBytes];
        inStream.read(inBuf, 0, inBytes);
        return new String(inBuf).trim();
    }

    @After
    public void deleteCsvFile() {
        deleteQuietly(new File(FILE_NAME));
    }
}
