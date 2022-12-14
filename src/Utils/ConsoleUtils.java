package Utils;

import java.io.IOException;

public final class ConsoleUtils {

    // https://stackoverflow.com/questions/2979383/how-to-clear-the-console
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//                Runtime.getRuntime().exec("cls");
            else
                System.out.print("\033\143");
//            Runtime.getRuntime().exec("clear");

        } catch (final Exception exc) {
            System.err.printf("Program rised an error [Cause: %s]\n", exc.getMessage());
//        Clear sout manually
            for (int temp = 0; temp < 50; temp++) System.out.println();

        }
    }

}
