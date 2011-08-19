package test.appkata;

import appkata.Main;

import static java.lang.System.lineSeparator;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class TestConsole {
    public void showsFileContent() {
        String content = "Name;Age;City" + lineSeparator() +
                         "Peter;42;New York" + lineSeparator() +
                         "Paul;57;London" + lineSeparator() +
                         "Mary;35;Munich" + lineSeparator() +
                         "Jaques;66;Paris" + lineSeparator() +
                         "Yuri;23;Moscow" + lineSeparator() +
                         "Stephanie;47;Stockholm" + lineSeparator() +
                         "Nadia;29;Madrid";

        assertThat("CSV file content", content, is(equalTo(Main.fileContent.toString().trim())));
    }
}
