package Utils;

import java.util.concurrent.TimeUnit;

public class SOUT_utils {

    public static void sleep(long sec, String m) {
        try {
            System.out.printf("""
                                            
                    ---------------------------------------------------
                    %s
                    ---------------------------------------------------
                                        
                    """, m);
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void postLogin() {
        sleep(2, "Login success! Redirecting you to your dashboard...");
    }
}
