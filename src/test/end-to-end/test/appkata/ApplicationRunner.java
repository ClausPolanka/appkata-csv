package test.appkata;

import appkata.Main;

public class ApplicationRunner {
    public void startsCsvViewerWith(final String filePath) {
        try {
            Main.main(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
