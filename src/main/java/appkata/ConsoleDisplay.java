package appkata;

public class ConsoleDisplay implements Display {
    public void view(String... result) {
        System.out.println(result);
    }
}
