package test.unit.spikes;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadingFromConsoleTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    public InputProcessor inputProcessor;

    @Mock
    public Keyboard keyboard;

    @Test
    public void testOneInputFromKeyBoard() throws Exception {
        context.checking(new Expectations() {{
            oneOf(keyboard).getInput(); will(returnValue("quit"));
        }});
        Application app = new Application(inputProcessor);
        app.readInputFrom(keyboard);
    }
    @Test
    public void testTwoInputsFromKeyBoard() throws Exception {
        context.checking(new Expectations() {{
            oneOf(keyboard).getInput(); will(returnValue("hello"));
            oneOf(inputProcessor).process("hello");

            oneOf(keyboard).getInput();
            will(returnValue("quit"));
        }});
        Application app = new Application(inputProcessor);
        app.readInputFrom(keyboard);
    }
    @Test
    public void testThreeInputsFromKeyBoard() throws Exception {
        context.checking(new Expectations() {{
            oneOf(keyboard).getInput(); will(returnValue("hello"));
            oneOf(inputProcessor).process("hello");

            oneOf(keyboard).getInput(); will(returnValue("world"));
            oneOf(inputProcessor).process("world");

            oneOf(keyboard).getInput(); will(returnValue("quit"));
        }});
        Application app = new Application(inputProcessor);
        app.readInputFrom(keyboard);
    }

    interface InputProcessor {
        void process(String input);
    }

    interface Keyboard {
        String getInput();
    }

    static class Application {
        private InputProcessor inputProcessor;

        public Application(InputProcessor inputProcessor) {
            this.inputProcessor = inputProcessor;
        }

        public void readInputFrom(Keyboard keyboard) {
            while (true) {
                String input = keyboard.getInput();
                if (input.equals("quit")) {
                    break;
                }
                inputProcessor.process(input);
            }
        }
    }
    static class ConsoleKeyboard implements Keyboard {
        private BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        @Override
        public String getInput() {
            try {
                return keyboard.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
    static class Main {
        public static void main(String[] args) {
            Application app = new Application(new InputProcessor() {
                @Override
                public void process(String input) {
                    System.out.println(input);
                }
            });
            app.readInputFrom(new ConsoleKeyboard());
        }
    }
}
