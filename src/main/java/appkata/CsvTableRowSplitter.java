package appkata;

import java.util.Arrays;

import static appkata.CsvConverter.HEADER_ROW_INDEX;
import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.split;

public class CsvTableRowSplitter {
    private static final int HEADER_ROW = 1;
    private int pageSize;
    private CsvConverter csvConverter;

    public CsvTableRowSplitter(int pageSize, CsvConverter csvConverter) {
        this.pageSize = pageSize;
        this.csvConverter = csvConverter;
    }

    public String[] toTableRows(String fileContent) {
        String[] fileContentRows = split(fileContent, lineSeparator());
        return withAppliedFooterIfNecessary(fileContentRows);
    }

    private String[] withAppliedFooterIfNecessary(String[] fileContentRows) {
        if (footerMustBeAppended(fileContentRows)) {
            csvConverter.footerMustBeAppended(); // Right place?
            fileContentRows = Arrays.copyOfRange(fileContentRows, HEADER_ROW_INDEX, HEADER_ROW + pageSize);
        }
        return fileContentRows;
    }

    private boolean footerMustBeAppended(String[] fileContentRows) {
        return (fileContentRows.length - HEADER_ROW) > pageSize;
    }
}
