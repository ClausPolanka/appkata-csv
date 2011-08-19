package appkata;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.System.lineSeparator;

public class CsvFile {
    private static final String NEW_LINE_AS_DELIMITER = lineSeparator();
    private List<CsvLine> lines = new ArrayList<CsvLine>();

    public CsvFile(String fileContent) {
        filterLines(fileContent);
    }

    private void filterLines(String fileContent) {
        StringTokenizer tokenizer = new StringTokenizer(fileContent, NEW_LINE_AS_DELIMITER);
        while (tokenizer.hasMoreTokens()) {
            lines.add(new CsvLine(tokenizer.nextToken()));
        }
    }

    public int lineCount() {
        return lines.size();
    }

    public List<CsvLine> lines() {
        return lines;
    }

    public CsvLine line(int lineIndex) {
        return lines.get(lineIndex);
    }
}
