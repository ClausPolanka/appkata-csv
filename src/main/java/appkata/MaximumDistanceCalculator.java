package appkata;

import java.util.HashMap;
import java.util.StringTokenizer;

import static appkata.CsvConverter.CVS_COLUMN_SEPARATOR;
import static java.lang.Math.max;

public class MaximumDistanceCalculator {
    public static HashMap<Integer, Integer> calcMaxDistancesForColumnSeparator(String[] rows) {
        HashMap<Integer, Integer> maxDelimDistances = new HashMap<Integer, Integer>();
        for (int i = 1; i < rows.length; i++) {
            putMaximumDistanceFor(rows[i], maxDelimDistances);
        }
        return maxDelimDistances;
    }

    private static void putMaximumDistanceFor(String row, HashMap<Integer, Integer> distances) {
        StringTokenizer columnsInRow = new StringTokenizer(row, CVS_COLUMN_SEPARATOR);
        for (int columnIndex = 0; columnsInRow.hasMoreElements(); columnIndex++) {
            String column = columnsInRow.nextToken();
            distances.put(columnIndex, max(currentMaxOf(distances, columnIndex),
                                           column.length()));
        }
    }

    private static int currentMaxOf(HashMap<Integer, Integer> distances, int columnIndex) {
        Integer max = distances.get(columnIndex);
        return max == null ? 0 : max;
    }
}
