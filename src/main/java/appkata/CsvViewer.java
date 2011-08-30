package appkata;

import java.util.HashMap;
import java.util.StringTokenizer;

import static java.lang.Math.log;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.lang.System.lineSeparator;

public class CsvViewer {
    private static final String HEADER_COLUMN_SEPARATOR = "|";
    private static final int HEADER_ROW = 0;
    private static final String COLUMN_SEPARATOR = ";";

    private final Display display;
    private String generatedResult;

    public CsvViewer(Display display) {
        this.display = display;
    }

    public void view(String fileContent) {
        String[] lines = toArrayOfLines(fileContent);

        HashMap<Integer, Integer> maxDelimDistances = calculateMaximumDistancesForColumnSeparator(lines);

        String headerColumns = createHeaderFor(lines[HEADER_ROW], maxDelimDistances);
        String headerSeparator = createHeaderSeparatorFor(headerColumns);
        String tableContent = createTableContentFor(lines, maxDelimDistances);

        StringBuffer output = new StringBuffer();
        output.append(headerColumns + lineSeparator());
        output.append(headerSeparator);
        output.append(tableContent);
        generatedResult = output.toString();
    }

    private String createTableContentFor(String[] lines, HashMap<Integer, Integer> maxDelimDistances) {
        if (onlyContainsHeader(lines)) {
            return "";
        }
        StringBuffer tableContent = new StringBuffer();
        tableContent.append(lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            StringBuffer tableRow = new StringBuffer();
            StringTokenizer tokenizer = new StringTokenizer(lines[i], COLUMN_SEPARATOR);
            for (int j = 0; tokenizer.hasMoreTokens(); j++) {
                String token = tokenizer.nextToken();
                tableRow.append(format("%s%s|", token, whiteSpaces(maxDelimDistances.get(i-1) - token.length())));
            }
            tableContent.append(tableRow);
        }
        return tableContent.toString();
    }

    private boolean onlyContainsHeader(String[] lines) {
        return lines.length <= 1;
    }

    private String[] toArrayOfLines(String fileContent) {
        StringTokenizer tokenizer = new StringTokenizer(fileContent, lineSeparator());
        String[] lines = new String[tokenizer.countTokens()];
        for (int i = 0; tokenizer.hasMoreTokens(); i++) {
            lines[i] = tokenizer.nextToken();
        }
        return lines;
    }

    private HashMap<Integer, Integer> calculateMaximumDistancesForColumnSeparator(String[] lines) {
        HashMap<Integer, Integer> maxDelimDistances = new HashMap<Integer, Integer>();
        for (int lineIndex = 1; lineIndex < lines.length; lineIndex++) {
            StringTokenizer tokenizer = new StringTokenizer(lines[lineIndex], COLUMN_SEPARATOR);
            for (int columnIndex = 0; tokenizer.hasMoreElements(); columnIndex++) {
                Integer currentMaxDistance = maxDelimDistances.get(columnIndex);
                String token = tokenizer.nextToken();
                if (currentMaxDistance == null) {
                    maxDelimDistances.put(columnIndex, token.length());
                    continue;
                }
                maxDelimDistances.put(columnIndex, max(currentMaxDistance, token.length()));
            }
        }
        return maxDelimDistances;
    }

    private String createHeaderFor(String firstLineOfFile, HashMap<Integer, Integer> maxDelimDistances) {
        StringBuffer headerColumns = new StringBuffer();
        StringTokenizer st = new StringTokenizer(withoutLineSeparator(firstLineOfFile), COLUMN_SEPARATOR);
        for (int i = 0; st.hasMoreTokens(); i++) {
            String column = st.nextToken();
            int max = max(column.length(), maxDelimDistances.get(i) == null ? 0 : maxDelimDistances.get(i));
            maxDelimDistances.put(i, max);
            headerColumns.append(format("%s%s%s", column, whiteSpaces(max-column.length()), HEADER_COLUMN_SEPARATOR));
        }
        return headerColumns.toString();
    }

    private String whiteSpaces(int times) {
        StringBuilder hyphens = new StringBuilder();
        for (int i = 0; i < times; i++) {
            hyphens.append(" ");
        }
        return hyphens.toString();
    }

    private String withoutLineSeparator(String fileContent) {
        return fileContent.replace(lineSeparator(), "");
    }

    private String createHeaderSeparatorFor(String headerColumns) {
        StringBuffer headerSeparator = new StringBuffer();
        StringTokenizer st = new StringTokenizer(headerColumns, HEADER_COLUMN_SEPARATOR);
        while (st.hasMoreTokens()) {
            String column = st.nextToken();
            headerSeparator.append(format("%s+", hyphens(column.length())));
        }
        return headerSeparator.toString();
    }

    private String hyphens(int times) {
        StringBuilder hyphens = new StringBuilder();
        for (int i = 0; i < times; i++) {
            hyphens.append("-");
        }
        return hyphens.toString();
    }

    public String output() {
        return generatedResult;
    }
}
