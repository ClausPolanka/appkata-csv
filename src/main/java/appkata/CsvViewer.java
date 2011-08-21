package appkata;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

public class CsvViewer {
    private Display display;
    private String generatedResult;

    public CsvViewer(Display display) {
        this.display = display;
    }

    public void view(String fileContent) {
        String headerColumn = withoutLineSeparator(withoutSemicolon(fileContent));
        generatedResult = format("%s|%s%s+", headerColumn, lineSeparator(), hyphens(headerColumn.length()));
    }

    private String withoutLineSeparator(String fileContent) {
        return fileContent.replace(lineSeparator(), "");
    }

    private String withoutSemicolon(String fileContent) {
        return fileContent.replace(";", "");
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
