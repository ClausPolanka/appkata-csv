package appkata;

import java.util.HashMap;
import java.util.StringTokenizer;

import static appkata.MaximumDistanceCalculator.calcMaxDistancesForColumnSeparator;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.repeat;

public class CsvConverter {
    public static final int HEADER_ROW_INDEX = 0;

    private static final int FIRST_TABLE_ROW = 1;
    private static final String HEADER_COLUMN_SEPARATOR = "|";
    private static final String WHITE_SPACE = " ";

    static final String CVS_COLUMN_SEPARATOR = ";";

    private int pageSize = 3;
    private boolean footerMustBeAppended;
    private CsvTableRowSplitter splitter = new CsvTableRowSplitter(pageSize, this);

    private final Display display;

    public CsvConverter(Display display) {
        this.display = display;
    }

    public void convert(String fileContent) {
        String[] tableRows = splitter.toTableRows(fileContent);
        HashMap<Integer, Integer> separatorDistances = calcMaxDistancesForColumnSeparator(tableRows);
        String headerColumns = createHeaderFor(tableRows[HEADER_ROW_INDEX], separatorDistances);
        String headerSeparator = createHeaderSeparatorFor(headerColumns);
        String tableContent = createTableContentFor(tableRows, separatorDistances);
        display.view(headerColumns + lineSeparator() + headerSeparator + tableContent);
    }

    private String createHeaderFor(String firstLineOfFile, HashMap<Integer, Integer> allMaxDelimDistances) {
        String headerColumns = EMPTY;
        StringTokenizer tokenizer = new StringTokenizer(withoutLineSeparator(firstLineOfFile), CVS_COLUMN_SEPARATOR);
        for (int columnIndex = 0; tokenizer.hasMoreTokens(); columnIndex++) {
            String column = tokenizer.nextToken();
            int max = calculateMaximumOfDelimiterDistance(allMaxDelimDistances, columnIndex, column);
            headerColumns += format("%s%s%s", column, repeat(WHITE_SPACE, max - column.length()), HEADER_COLUMN_SEPARATOR);
        }
        return headerColumns;
    }

    private String withoutLineSeparator(String fileContent) {
        return fileContent.replace(lineSeparator(), EMPTY);
    }

    private int calculateMaximumOfDelimiterDistance(HashMap<Integer, Integer> maxDelimDistances, int columnIndex, String column) {
        Integer delimDistance = maxDelimDistances.get(columnIndex);
        int max = max(column.length(), delimDistance == null ? 0 : delimDistance);
        maxDelimDistances.put(columnIndex, max);
        return max;
    }

    private String createHeaderSeparatorFor(String headerColumns) {
        String headerSeparator = EMPTY;
        StringTokenizer st = new StringTokenizer(headerColumns, HEADER_COLUMN_SEPARATOR);
        while (st.hasMoreTokens()) {
            String column = st.nextToken();
            headerSeparator += format("%s+", repeat("-", column.length()));
        }
        return headerSeparator;
    }

    private String createTableContentFor(String[] lines, HashMap<Integer, Integer> maxDelimDistances) {
        if (onlyContainsHeader(lines)) {
            return EMPTY;
        }
        String tableContent = lineSeparator();
        tableContent += tableContentRows(lines, maxDelimDistances);
        tableContent += footer();
        return tableContent;
    }

    private boolean onlyContainsHeader(String[] lines) {
        return lines.length <= 1;
    }

    private String tableContentRows(String[] lines, HashMap<Integer, Integer> maxDelimDistances) {
        String tableContent = EMPTY;
        for (int rowIndex = FIRST_TABLE_ROW; rowIndex < lines.length; rowIndex++) {
            tableContent += toTableRow(lines[rowIndex], maxDelimDistances);
            boolean isNotLastLine = rowIndex < (lines.length - 1);
            if (isNotLastLine) {
                tableContent += lineSeparator();
            }
        }
        return tableContent;
    }

    private String toTableRow(String line, HashMap<Integer, Integer> maxDelimiterDistances) {
        String tableRow = EMPTY;
        StringTokenizer tokenizer = new StringTokenizer(line, CVS_COLUMN_SEPARATOR);
        for (int i = 0; tokenizer.hasMoreTokens(); i++) {
            String token = tokenizer.nextToken();
            tableRow += columnContentWithAppliedWhiteSpacesToSeparator(maxDelimiterDistances, i, token);
        }
        return tableRow;
    }

    private String columnContentWithAppliedWhiteSpacesToSeparator(HashMap<Integer, Integer> maxDelimiterDistances, int i, String token) {
        return format("%s%s|", token, numberOfWhiteSpacesToSeparator(maxDelimiterDistances, i, token));
    }

    private String numberOfWhiteSpacesToSeparator(HashMap<Integer, Integer> maxDelimiterDistances, int i, String token) {
        return repeat(WHITE_SPACE, maxDelimiterDistances.get(i) - token.length());
    }

    private String footer() {
        if ( ! footerMustBeAppended) {
            return EMPTY;
        }
        return lineSeparator() + lineSeparator() + "N(ext page, P(revious page, F(irst page, L(ast page, eX(it";
    }

    public void setTablePageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void footerMustBeAppended() {
        footerMustBeAppended = true;
    }
}
