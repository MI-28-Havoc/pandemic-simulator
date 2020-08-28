package Model;

import Controller.Time;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import static Controller.SimulatorConfig.*;

@Data
public class Person {
    int posX;
    int posY;
    int age;

    @Setter(AccessLevel.NONE)
    boolean infected; //Wenn true, dann noch nicht erkrankt, sondern Beginn Inkubationszeit
    boolean recovered = false; //Damit auch Immun ?
    boolean dead = false;
    boolean inRiskGroup = false;
    int incubationPeriod;   //Für jede Person individuell
    int illnessPeriod;      //Für jede Person individuell
    int infectedAtDay = 0;  //Tag der Infizierung
    int illnessAtDay = 0; //Tag Beginn der Erkrankung
    boolean illnessBegun = false;  //Inkubation abgeschlossen, Person ist erkrankt
    boolean hasPassedIncubationPeriod = false;


    public Person () {
        this.age = (int)(Math.random() * 88)+12;
        this.inRiskGroup = (Math.random() < PROB_PERSON_IS_IN_RISK_GROUP);
        this.infected = false;
        this.recovered = false;
        this.dead = false;
    }

    public void infect() {
        this.infected = true;
        this.infectedAtDay = Time.getCurrentDay();
        this.incubationPeriod = (int)(Math.random() * (MAX_INCUBATION_PERIOD_DAYS - MIN_INCUBATION_PERIOD_DAYS) + MIN_INCUBATION_PERIOD_DAYS);
        this.illnessPeriod = (int)(Math.random() * (MIN_ILLNESS_PERIOD_DAYS - MAX_ILLNESS_PERIOD_DAYS) + MAX_ILLNESS_PERIOD_DAYS);
    }

    public void checkCondition() {

    }
}
