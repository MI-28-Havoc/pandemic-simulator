package Controller;

import lombok.NoArgsConstructor;

public class Time {
    static int currentDay = 0;
    static long timer = 0;
    static double dayDurationInSeconds = 1;
    public static void startTimer() {
        timer = System.currentTimeMillis();
    }

    public static boolean nextTickReached() {
        if (System.currentTimeMillis() > timer + (dayDurationInSeconds*1000)) {
            timer = System.currentTimeMillis();
            return true;
        }
        else {
            return false;
        }
    }

    public static void nextDay() {
        currentDay++;
    }

    public static int getCurrentDay() {
        return currentDay;
    }

    public static void reset() {currentDay = 0;timer = 0;}
}
