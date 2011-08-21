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
    private final CsvFileBuilder aCsvFile = new CsvFileBuilder();
    private final ApplicationRunner client = new ApplicationRunner();

    @Test
    public void showCsvFileContainingOneHeaderColumn() throws Exception {
        String fileContent = "Name;" + lineSeparator();
        aCsvFile.withName("test.csv").containing(fileContent).build();
        client.startsCsvViewerForFile("test.csv");
        String expectedCsvViewerOutput = "Name|" + lineSeparator() +
                                         "----+";
        assertThat("Csv Viewer Output", client.csvViewerOutput, is(equalTo(expectedCsvViewerOutput)));
    }

    @After
    public void deleteCsvFile() {
        deleteQuietly(new File("test.csv"));
    }
}
