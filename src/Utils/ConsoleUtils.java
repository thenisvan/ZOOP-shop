package Utils;

public class ConsoleUtils {
// https://stackoverflow.com/questions/2979383/how-to-clear-the-console
    public static void clearConsole()
{
    try
    {
        final String os = System.getProperty("os.name");

        if (os.contains("Windows"))
        {
            Runtime.getRuntime().exec("cls");
        }
        else
        {
            Runtime.getRuntime().exec("clear");
        }
    }
    catch (final Exception e)
    {
        System.err.printf("Program rised an error [Cause: %s]\n", e.getMessage());
//        Clear sout manually
        for (int temp=0; temp<50; temp++) System.out.println();

    }
}

}
