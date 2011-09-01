package appkata;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.StringTokenizer;

import static java.lang.Math.max;
import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.apache.commons.lang3.StringUtils.split;

public class CsvViewer {
    private static final String HEADER_COLUMN_SEPARATOR = "|";
    private static final int HEADER_ROW = 0;
    private static final String COLUMN_SEPARATOR = ";";

    private final Display display;

    public CsvViewer(Display display) {
        this.display = display;
    }

    public void view(String fileContent) {
        String[] fileContentRows = split(fileContent, lineSeparator());
        HashMap<Integer, Integer> separatorDistances = calculateMaximumDistancesForColumnSeparator(fileContentRows);
        String headerColumns = createHeaderFor(fileContentRows[HEADER_ROW], separatorDistances);
        String headerSeparator = createHeaderSeparatorFor(headerColumns);
        String tableContent = createTableContentFor(fileContentRows, separatorDistances);
        StringBuilder output = new StringBuilder();
        output.append(headerColumns + lineSeparator())
              .append(headerSeparator)
              .append(tableContent);
        display.print(output.toString());
    }

    private HashMap<Integer, Integer> calculateMaximumDistancesForColumnSeparator(String[] lines) {
        HashMap<Integer, Integer> maxDelimDistances = new HashMap<Integer, Integer>();
        for (int lineIndex = 1; lineIndex < lines.length; lineIndex++) {
            putMaximumDistanceFor(lines[lineIndex], maxDelimDistances);
        }
        return maxDelimDistances;
    }

    private void putMaximumDistanceFor(String line, HashMap<Integer, Integer> maxDelimDistances) {
        StringTokenizer tokenizer = new StringTokenizer(line, COLUMN_SEPARATOR);
        for (int columnIndex = 0; tokenizer.hasMoreElements(); columnIndex++) {
            Integer currentMaxDistance = maxDelimDistances.get(columnIndex);
            String token = tokenizer.nextToken();
            maxDelimDistances.put(columnIndex, max(currentMaxDistance == null ? 0 : currentMaxDistance, token.length()));
        }
    }

    private String createHeaderFor(String firstLineOfFile, HashMap<Integer, Integer> maxDelimDistances) {
        StringBuilder headerColumns = new StringBuilder();
        StringTokenizer st = new StringTokenizer(withoutLineSeparator(firstLineOfFile), COLUMN_SEPARATOR);
        for (int columnIndex = 0; st.hasMoreTokens(); columnIndex++) {
            String column = st.nextToken();
            Integer delimDistance = maxDelimDistances.get(columnIndex);
            int max = max(column.length(), delimDistance == null ? 0 : delimDistance);
            maxDelimDistances.put(columnIndex, max);
            headerColumns.append(format("%s%s%s", column, repeat(" ", max - column.length()), HEADER_COLUMN_SEPARATOR));
        }
        return headerColumns.toString();
    }

    private String withoutLineSeparator(String fileContent) {
        return fileContent.replace(lineSeparator(), "");
    }

    private String createHeaderSeparatorFor(String headerColumns) {
        StringBuilder headerSeparator = new StringBuilder();
        StringTokenizer st = new StringTokenizer(headerColumns, HEADER_COLUMN_SEPARATOR);
        while (st.hasMoreTokens()) {
            String column = st.nextToken();
            headerSeparator.append(format("%s+", repeat("-", column.length())));
        }
        return headerSeparator.toString();
    }

    private String createTableContentFor(String[] lines, HashMap<Integer, Integer> maxDelimDistances) {
        if (onlyContainsHeader(lines)) {
            return EMPTY;
        }
        StringBuilder tableContent = new StringBuilder();
        tableContent.append(lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            StringBuilder tableRow = toTableRow(lines[i], maxDelimDistances);
            tableContent.append(tableRow);
            boolean isNotLastLine = i < (lines.length - 1);
            if (isNotLastLine) {
                tableContent.append(lineSeparator());
            }
        }
        return tableContent.toString();
    }

    private boolean onlyContainsHeader(String[] lines) {
        return lines.length <= 1;
    }

    private StringBuilder toTableRow(String line, HashMap<Integer, Integer> maxDelimiterDistance) {
        StringBuilder tableRow = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(line, COLUMN_SEPARATOR);
        for (int i = 0; tokenizer.hasMoreTokens(); i++) {
            String token = tokenizer.nextToken();
            tableRow.append(format("%s%s|", token, repeat(" ", maxDelimiterDistance.get(i) - token.length())));
        }
        return tableRow;
    }
}
