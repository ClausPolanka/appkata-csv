package appkata;

import java.util.Arrays;

import static appkata.CsvViewer.HEADER_ROW_INDEX;
import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.split;

public class CsvTableRowSplitter {
    private static final int HEADER_ROW = 1;
    private int pageSize;
    private CsvViewer csvViewer;

    public CsvTableRowSplitter(int pageSize, CsvViewer csvViewer) {
        this.pageSize = pageSize;
        this.csvViewer = csvViewer;
    }

    public String[] toTableRows(String fileContent) {
        String[] fileContentRows = split(fileContent, lineSeparator());
        return withAppliedFooterIfNecessary(fileContentRows);
    }

    private String[] withAppliedFooterIfNecessary(String[] fileContentRows) {
        if (footerMustBeAppended(fileContentRows)) {
            csvViewer.footerMustBeAppended(); // Right place?
            fileContentRows = Arrays.copyOfRange(fileContentRows, HEADER_ROW_INDEX, HEADER_ROW + pageSize);
        }
        return fileContentRows;
    }

    private boolean footerMustBeAppended(String[] fileContentRows) {
        return (fileContentRows.length - HEADER_ROW) > pageSize;
    }
}
