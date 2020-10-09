package Controller;

import lombok.NoArgsConstructor;

public class Time {
    static int currentDay = 0;
    static long timer = 0;
    public static int dayDurationInMs = 1000;

    public static boolean nextTick() {
        if (System.currentTimeMillis() > timer + (dayDurationInMs)) {
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
