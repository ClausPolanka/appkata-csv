package appkata;

public class ConsoleDisplay implements Display {
    public void view(String... pages) {
        for (String p : pages) {
            System.out.println(p);
        }
    }
}
