package test.unit.appkata;

import appkata.CsvConverter;
import appkata.Display;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.Scanner;

import static java.lang.System.lineSeparator;

public class CsvConverterTest {
    @Mock
    private Display display;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void viewOutputContainingHeaderWithOneColumnForGivenCsvFileContent() {
        context.checking(new Expectations() {{
            oneOf(display).view("Name|" + lineSeparator() +
                "----+");
        }});
        CsvConverter csvConverter = new CsvConverter(display);
        csvConverter.convert("Name;" + lineSeparator());
    }

    @Test
    public void viewOutputContainingHeaderWithSeveralColumnsForGivenCsvFileContent() {
        context.checking(new Expectations() {{
            oneOf(display).view("Name|Age|City|" + lineSeparator() +
                "----+---+----+");
        }});
        CsvConverter csvConverter = new CsvConverter(display);
        csvConverter.convert("Name;Age;City;" + lineSeparator());
    }

    @Test
    public void viewOutputContainingHeaderWithOneColumnAndOneTableLineForGivenCsvFileContent() {
        context.checking(new Expectations() {{
            oneOf(display).view("Name |" + lineSeparator() +
                "-----+" + lineSeparator() +
                "Peter|");
        }});
        CsvConverter csvConverter = new CsvConverter(display);
        csvConverter.convert("Name;" + lineSeparator() +
                             "Peter;");
    }

    @Test
    public void viewOutputContainingHeaderWithSeveralColumnsAndOneTableLineForGivenCsvFileContent() {
        context.checking(new Expectations() {{
            oneOf(display).view("Name |Age|City    |" + lineSeparator() +
                "-----+---+--------+" + lineSeparator() +
                "Peter|42 |New York|");
        }});
        CsvConverter csvConverter = new CsvConverter(display);
        csvConverter.convert("Name;Age;City;" + lineSeparator() +
                             "Peter;42;New York;");
    }

    @Test
    public void viewOutputContainingHeaderWithSeveralColumnsAndSeveralTableLinesForGivenCsvFileContent() {
        context.checking(new Expectations() {{
            oneOf(display).view("Name |Age|City    |" + lineSeparator() +
                "-----+---+--------+" + lineSeparator() +
                "Peter|42 |New York|" + lineSeparator() +
                "Paul |57 |London  |" + lineSeparator() +
                "Mary |35 |Munich  |");
        }});
        CsvConverter csvConverter = new CsvConverter(display);
        csvConverter.convert("Name;Age;City;"       + lineSeparator() +
                             "Peter;42;New York;"   + lineSeparator() +
                             "Paul;57;London;"      + lineSeparator() +
                             "Mary;35;Munich;");
    }

    @Test
    public void showFirstTablePageIfFileContentContainsMoreCvsRowsThanDefaultTablePageSize() {
        context.checking(new Expectations() {{
            oneOf(display).view("Name |Age|City    |" + lineSeparator() +
                "-----+---+--------+" + lineSeparator() +
                "Peter|42 |New York|" + lineSeparator() +
                "Paul |57 |London  |" + lineSeparator() +
                "Mary |35 |Munich  |" + lineSeparator() + lineSeparator() +
                "N(ext page, P(revious page, F(irst page, L(ast page, eX(it");
        }});
        CsvConverter csvConverter = new CsvConverter(display);
        csvConverter.convert("Name;Age;City;" + lineSeparator() +
                             "Peter;42;New York;" + lineSeparator() +
                             "Paul;57;London;" + lineSeparator() +
                             "Mary;35;Munich;" + lineSeparator() +
                             "Jaques;66;Paris");
    }

    @Test @Ignore("Next test case to implement.")
    public void exitAfterFirstTablePageIsShown() {
        context.checking(new Expectations() {{
            String page1 = "Name |Age|City    |" + lineSeparator() +
                           "-----+---+--------+" + lineSeparator() +
                           "Peter|42 |New York|" + lineSeparator() +
                           "Paul |57 |London  |" + lineSeparator() +
                           "Mary |35 |Munich  |" + lineSeparator() + lineSeparator() +
                           "N(ext page, P(revious page, F(irst page, L(ast page, eX(it";

            String page2 = "Name  |Age|City    |" + lineSeparator() +
                           "------+---+--------+" + lineSeparator() +
                           "Jaques|66 |Paris   |";

            oneOf(display).view(page1, page2);
        }});
        CsvConverter csvConverter = new CsvConverter(display);
        csvConverter.convert("Name;Age;City;" + lineSeparator() +
                             "Peter;42;New York;" + lineSeparator() +
                             "Paul;57;London;" + lineSeparator() +
                             "Mary;35;Munich;" + lineSeparator() +
                             "Jaques;66;Paris");
    }

}
