package test.appkata;

import org.junit.Test;

public class CsvViewerEndToEndTest {
    private final ApplicationRunner client = new ApplicationRunner();
    private final TestConsole console = new TestConsole();

    @Test
    public void csvViewerReadsInFileAndDisplaysContentOnConsole() {
        client.startsCsvViewerWith("test.csv");
        console.showsFileContent();
    }
}
