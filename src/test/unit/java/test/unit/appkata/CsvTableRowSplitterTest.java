package test.unit.appkata;

import appkata.CsvTableRowSplitter;
import appkata.CsvViewer;
import appkata.Display;
import org.junit.Test;

import static java.lang.System.lineSeparator;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CsvTableRowSplitterTest {
    private static final CsvViewer IGNORE_CSV_VIEWER = null;
    private static final String EMPTY_FILE_CONTENT = "";
    private static final Display IGNORE_DISPLAY = null;
    private static final int IGNORE_DEFAULT_PAGE_SIZE = 0;

    @Test
    public void givenAnEmptyFileContentNothingIsSplit() {
        CsvTableRowSplitter splitter = new CsvTableRowSplitter(IGNORE_DEFAULT_PAGE_SIZE, IGNORE_CSV_VIEWER);
        String[] rows = splitter.toTableRows(EMPTY_FILE_CONTENT);
        assertThat("Table rows", rows, is(StringArrayBeautifier.emptyStringArray()));
    }

    @Test
    public void whenLineNumberIsLowerThanDefaultPageSizeReturnTheSame() {
        int defaultPageSize = 3;
        CsvTableRowSplitter splitter = new CsvTableRowSplitter(defaultPageSize, IGNORE_CSV_VIEWER);

        String[] rows = splitter.toTableRows("Header" + lineSeparator() +
                                             "Word_1" + lineSeparator() +
                                             "Word_2");

        assertThat("Table rows", rows, is(StringArrayBeautifier.asArray("Header", "Word_1", "Word_2")));
    }

    @Test
    public void whenLineNumberIsHigherThanDefaultPageSizeCsvViewerMustAppendFooter() {
        int defaultPageSize = 3;
        CsvTableRowSplitter splitter = new CsvTableRowSplitter(defaultPageSize,
                new CsvViewer(IGNORE_DISPLAY) {
                    @Override
                    public void footerMustBeAppended() {
                        int call = 0;
                        assertThat("Footer must be appended", ++call, is(1));
                    }
                }
         );
        String[] rows = splitter.toTableRows("Header" + lineSeparator() +
                                             "Word_1" + lineSeparator() +
                                             "Word_2" + lineSeparator() +
                                             "Word_3" + lineSeparator() +
                                             "Word_4");
        assertThat("Table rows", rows, is(StringArrayBeautifier.asArray("Header", "Word_1", "Word_2", "Word_3")));
    }

}
