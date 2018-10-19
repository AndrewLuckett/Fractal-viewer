package main;

public class Debuglog {

    /**
     * loglevel is the value a log is required to have before it is written
     * The lower the level the more important
     * negative loglevel should equal no logging
     * loglevel MAX_INT allows all logs
     */
    public static int loglevel = -1;

    public static void log(String log, int level) {
        if (level <= loglevel && level >= 0) {
            System.out.println(log);
        }
    }
}
