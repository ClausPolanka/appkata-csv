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
    public void viewOutputContainingHeaderWithOneColumnForGivenCsvFileContent() {
        context.checking(new Expectations() {{
            oneOf(display).print("Name|" + lineSeparator() +
                                 "----+");
        }});
        CsvViewer csvViewer = new CsvViewer(display);
        csvViewer.view("Name;" + lineSeparator());
    }

    @Test
    public void viewOutputContainingHeaderWithSeveralColumnsForGivenCsvFileContent() {
        context.checking(new Expectations() {{
            oneOf(display).print("Name|Age|City|" + lineSeparator() +
                                 "----+---+----+");
        }});
        CsvViewer csvViewer = new CsvViewer(display);
        String fileContent = "Name;Age;City;" + lineSeparator();
        csvViewer.view(fileContent);
    }

    @Test
    public void viewOutputContainingHeaderWithOneColumnAndOneTableLineForGivenCsvFileContent() {
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
    public void viewOutputContainingHeaderWithSeveralColumnsAndOneTableLineForGivenCsvFileContent() {
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
    public void viewOutputContainingHeaderWithSeveralColumnsAndSeveralTableLinesForGivenCsvFileContent() {
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

    @Test
    public void showFirstTablePageIfFileContentContainsMoreCvsRowsThanDefaultTablePageSize() {
        context.checking(new Expectations() {{
            oneOf(display).print("Name |Age|City    |" + lineSeparator() +
                                 "-----+---+--------+" + lineSeparator() +
                                 "Peter|42 |New York|" + lineSeparator() +
                                 "Paul |57 |London  |" + lineSeparator() +
                                 "Mary |35 |Munich  |" + lineSeparator() + lineSeparator() +
                                 "N(ext page, P(revious page, F(irst page, L(ast page, eX(it");
        }});
        CsvViewer csvViewer = new CsvViewer(display);
        String fileContent = "Name;Age;City;" + lineSeparator() +
                             "Peter;42;New York;" + lineSeparator() +
                             "Paul;57;London;" + lineSeparator() +
                             "Mary;35;Munich;" + lineSeparator() +
                             "Jaques;66;Paris";
        csvViewer.view(fileContent);
    }
}
