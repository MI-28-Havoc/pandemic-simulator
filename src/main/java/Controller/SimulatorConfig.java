package Controller;

import lombok.Data;

public class SimulatorConfig {
    //PROB = Wahrscheinlichkeit, Angabe in Prozent
    //Demografische Eigenschaften
    public static int AMOUNT_OF_PERSONS = 100;
    public static double PROB_PERSON_IS_IN_RISK_GROUP = 0.1;            //Gilt für alle Personen
    public static int AGE_FOR_RISK_GROUP = 65;                          //über 100 setzen wenn keine Risikoalter gewünscht ist

    //Pandemie Eigenschaften
    public static int MAX_INCUBATION_PERIOD_DAYS = 14;                  //Max. Inkubationszeit
    public static int MIN_INCUBATION_PERIOD_DAYS = 3;                   //Min. Inkubationszeit
    public static double PROB_BASIC_FATALITY = 0.2;                     //0 = nicht tödlich
    public static double PROB_BASIC_INFECTIVITY = 0.5;                  //0 = nicht ansteckend
    public static int MAX_ILLNESS_PERIOD_DAYS = 3;                      //Erholung/Tod frühestens nach mind. x Tagen
    public static int MIN_ILLNESS_PERIOD_DAYS = 10;                     //Erholung/Tod spätestens nach max. x Tagen
    public static boolean IS_INFECTIOUS_IN_INCUBATION_PERIOD = true;    //False = Kann erst nach der Inkubationszeit andere Personen anstecken
    public static double PROB_OF_MOVE = 0.4;                            //0 = bewegt sich nicht

    //Darstellung
    public static int CIRCLE_HEIGHT = 10;
    public static int CIRCLE_WIDTH = 10;
}