package Utils;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class SOUT_utils {

    public static void delayMessage(double sec, String m) {
        try {
            System.out.printf("""
                    |++++++++++++++++++++++++++++++++++++++++++
                    | %s
                    |++++++++++++++++++++++++++++++++++++++++++
                    """, m);
//            TimeUnit.SECONDS.sleep(sec);
            TimeUnit.MILLISECONDS.sleep( (long) (sec*1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void postLogin() {
        delayMessage(1.5, "Woohaa, nice... Going To Dashboard");
    }
}
