package main;

public class Debuglog {

    /**
     * loglevel is the value a log is required to have before it is written
     * loglevel 5 should equal no logging
     */
    public static int loglevel = 5;

    /**
     * higher level equals higher importance
     */
    public static void log(String log, int level) {
        if (level >= loglevel) {
            System.out.println(log);
        }
    }
}
