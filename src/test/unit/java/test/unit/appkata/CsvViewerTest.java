package test.unit.appkata;

import appkata.CsvViewer;
import appkata.Display;
import org.junit.Test;

import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CsvViewerTest {
    private static final Display IGNORE_DISPLAY = null;

    @Test
    public void viewOutputContainingHeaderWithOneColumnForGivenCsvFileContent() throws Exception {
        CsvViewer csvViewer = new CsvViewer(IGNORE_DISPLAY);
        String fileContent = "Name;" + lineSeparator();
        csvViewer.view(fileContent);
        String expectedViewerOutput = "Name|" + lineSeparator() +
                                      "----+";
        assertThat("Generated result", csvViewer.output(), is(equalTo(expectedViewerOutput)));
    }
}
