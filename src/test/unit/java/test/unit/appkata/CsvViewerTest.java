package test.unit.appkata;

import appkata.CsvViewer;
import appkata.Display;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import static java.lang.System.lineSeparator;

public class CsvViewerTest {
    @Mock
    private Display display;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void viewOutputContainingHeaderWithOneColumnForGivenCsvFileContent() throws Exception {
        context.checking(new Expectations() {{
            oneOf(display).print("Name|" + lineSeparator() +
                                 "----+");
        }});
        CsvViewer csvViewer = new CsvViewer(display);
        csvViewer.view("Name;" + lineSeparator());
    }

    @Test
    public void viewOutputContainingHeaderWithSeveralColumnsForGivenCsvFileContent() throws Exception {
        context.checking(new Expectations() {{
            oneOf(display).print("Name|Age|City|" + lineSeparator() +
                                 "----+---+----+");
        }});
        CsvViewer csvViewer = new CsvViewer(display);
        String fileContent = "Name;Age;City;" + lineSeparator();
        csvViewer.view(fileContent);
    }

    @Test
    public void viewOutputContainingHeaderWithOneColumnAndOneTableLineForGivenCsvFileContent() throws Exception {
        context.checking(new Expectations() {{
            oneOf(display).print("Name |" + lineSeparator() +
                                 "-----+" + lineSeparator() +
                                 "Peter|");
        }});
        CsvViewer csvViewer = new CsvViewer(display);
        String fileContent = "Name;" + lineSeparator() +
                             "Peter;";
        csvViewer.view(fileContent);
    }

    @Test
    public void viewOutputContainingHeaderWithSeveralColumnsAndOneTableLineForGivenCsvFileContent() throws Exception {
        context.checking(new Expectations() {{
            oneOf(display).print("Name |Age|City    |" + lineSeparator() +
                                 "-----+---+--------+" + lineSeparator() +
                                 "Peter|42 |New York|");
        }});
        CsvViewer csvViewer = new CsvViewer(display);
        String fileContent = "Name;Age;City;" + lineSeparator() +
                             "Peter;42;New York;";
        csvViewer.view(fileContent);
    }

    @Test
    public void viewOutputContainingHeaderWithSeveralColumnsAndSeveralTableLinesForGivenCsvFileContent() throws Exception {
        context.checking(new Expectations() {{
            oneOf(display).print("Name |Age|City    |" + lineSeparator() +
                                 "-----+---+--------+" + lineSeparator() +
                                 "Peter|42 |New York|" + lineSeparator() +
                                 "Paul |57 |London  |" + lineSeparator() +
                                 "Mary |35 |Munich  |");
        }});
        CsvViewer csvViewer = new CsvViewer(display);
        String fileContent = "Name;Age;City;" + lineSeparator() +
                             "Peter;42;New York;" + lineSeparator() +
                             "Paul;57;London;" + lineSeparator() +
                             "Mary;35;Munich;";
        csvViewer.view(fileContent);
    }
}
