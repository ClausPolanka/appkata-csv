package appkata;

import java.util.HashMap;
import java.util.StringTokenizer;

import static appkata.CsvViewer.CVS_COLUMN_SEPARATOR;
import static java.lang.Math.max;

public class MaximumDistanceCalculator {
    HashMap<Integer, Integer> calculateMaximumDistancesForColumnSeparator(String[] lines) {
        HashMap<Integer, Integer> maxDelimDistances = new HashMap<Integer, Integer>();
        for (int lineIndex = 1; lineIndex < lines.length; lineIndex++) {
            putMaximumDistanceFor(lines[lineIndex], maxDelimDistances);
        }
        return maxDelimDistances;
    }

    private void putMaximumDistanceFor(String line, HashMap<Integer, Integer> maxDelimDistances) {
        StringTokenizer tokenizer = new StringTokenizer(line, CVS_COLUMN_SEPARATOR);
        for (int columnIndex = 0; tokenizer.hasMoreElements(); columnIndex++) {
            Integer currentMaxDistance = maxDelimDistances.get(columnIndex);
            String token = tokenizer.nextToken();
            maxDelimDistances.put(columnIndex, max(currentMaxDistance == null ? 0 : currentMaxDistance, token.length()));
        }
    }
}
