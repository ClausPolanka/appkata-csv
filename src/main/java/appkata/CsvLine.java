package appkata;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CsvLine {
    private static final String SEMI_COLON_AS_DELIMITER = ";";
    private List<String> tokens = new ArrayList<String>();

    public CsvLine(String csvLine) {
        filterTokens(csvLine);
    }

    private void filterTokens(String csvLine) {
        StringTokenizer tokenizer = new StringTokenizer(csvLine, SEMI_COLON_AS_DELIMITER);
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
    }

    public String token(int tokenIndex) {
        return tokens.get(tokenIndex);
    }
}
