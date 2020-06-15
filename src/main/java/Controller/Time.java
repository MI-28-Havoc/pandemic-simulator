package Controller;

import lombok.NoArgsConstructor;

public class Time {
    static int currentDay = 0;
    public static void nextDay() {
        currentDay++;
    }

    public static int getCurrentDay() {
        return currentDay;
    }
}
