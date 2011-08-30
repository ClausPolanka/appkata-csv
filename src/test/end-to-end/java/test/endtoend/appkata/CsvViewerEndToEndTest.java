package test.endtoend.appkata;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;

import static java.lang.System.lineSeparator;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CsvViewerEndToEndTest {
    private static final String FILE_NAME = "test.csv";
    private final CsvFileBuilder aCsvFile = new CsvFileBuilder();
    private final ApplicationRunner client = new ApplicationRunner();

    @Test
    public void showCsvFileContainingOneHeaderColumn() throws Exception {
        String fileContent = "Name;" + lineSeparator();
        aCsvFile.withName(FILE_NAME).containing(fileContent).build();
        client.startsCsvViewerForFile(FILE_NAME);
        String expectedCsvViewerOutput = "Name|" + lineSeparator() +
                                         "----+";
        assertThat("Csv Viewer Output", client.csvViewerOutput, is(equalTo(expectedCsvViewerOutput)));
    }

    @Test
    public void showCsvFileContainingSeveralHeaderColumns() throws Exception {
        String fileContent = "Name;Age;City;" + lineSeparator();
        aCsvFile.withName(FILE_NAME).containing(fileContent).build();
        client.startsCsvViewerForFile(FILE_NAME);
        String expectedCsvViewerOutput = "Name|Age|City|" + lineSeparator() +
                                         "----+---+----+";
        assertThat("Csv Viewer Output", client.csvViewerOutput, is(equalTo(expectedCsvViewerOutput)));
    }

    @After
    public void deleteCsvFile() {
        deleteQuietly(new File(FILE_NAME));
    }
}
