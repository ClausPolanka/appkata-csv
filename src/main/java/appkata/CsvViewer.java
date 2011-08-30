package appkata;

import java.util.StringTokenizer;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

public class CsvViewer {
    private static final String HEADER_COLUMN_SEPARATOR = "|";

    private final Display display;
    private String generatedResult;

    public CsvViewer(Display display) {
        this.display = display;
    }

    public void view(String fileContent) {
        String headerColumns = createHeaderFor(fileContent);
        String headerSeparator = createHeaderSeparatorFor(headerColumns);

        StringBuffer output = new StringBuffer();
        output.append(headerColumns + lineSeparator());
        output.append(headerSeparator);
        generatedResult = output.toString();
    }

    private String createHeaderFor(String fileContent) {
        StringBuffer headerColumns = new StringBuffer();
        StringTokenizer st = new StringTokenizer(withoutLineSeparator(fileContent), ";");
        while (st.hasMoreTokens()) {
            String column = st.nextToken();
            headerColumns.append(format("%s%s", column, HEADER_COLUMN_SEPARATOR));
        }
        return headerColumns.toString();
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
