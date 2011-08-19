package test.unit.appkata;

import appkata.CsvFile;
import org.junit.Test;
import static java.lang.System.lineSeparator;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class CsvFileTest {
    private static final int FIRST_LINE = 0;
    private static final int SECOND_LINE = 1;
    private static final int FIRST_TOKEN = 0;

    @Test
    public void mapFileContentWhichContainsSingleLineContainingOneToken() throws Exception {
        String fileContent = "token1;";
        CsvFile csvFile = new CsvFile(fileContent);
        assertThat("Number of CSV file lines", csvFile.lineCount(), is(1));
        assertThat("Token", csvFile.line(FIRST_LINE).token(FIRST_TOKEN), is(equalTo("token1")));
    }

    @Test
    public void mapFileContentWhichContainsTwoLinesEachContainingOneToken() throws Exception {
        String fileContent = "token1;" + lineSeparator() +
                             "token1;";
        CsvFile csvFile = new CsvFile(fileContent);
        assertThat("Number of CSV file lines", csvFile.lineCount(), is(2));
        assertThat("Token", csvFile.line(SECOND_LINE).token(FIRST_TOKEN), is(equalTo("token1")));
    }
}
