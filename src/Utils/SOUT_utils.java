package Utils;

import java.util.concurrent.TimeUnit;

public class SOUT_utils {

    public static void delayMessage(double sec, String m) {
        try {
            String dashedStr = new String(new char[m.length()]).replace('\0', '─');
            String spacedStr = new String(new char[m.length()-"Message".length()]).replace('\0', ' ');
            System.out.printf("""
┌────%s┐
│    Message%s│
├────%s┤
│ %s   │
└────%s┘

                    """, dashedStr,spacedStr,dashedStr,m,dashedStr);

            TimeUnit.MILLISECONDS.sleep( (long) (sec*1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void afterLogIn() {
        delayMessage(1.5, "Woohaa, Login Successful");
    }
}
