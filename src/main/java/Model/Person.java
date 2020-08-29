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

    int infectedAtDay;      //Tag der Infizierung
    int illnessAtDay;       //Tag Beginn der Erkrankung
    int deathAtDay;         //Tag des Todes
    int recoveredAtDay;     //Tag der Erholung

    boolean illnessBegun = false;  //Inkubation abgeschlossen, Person ist erkrankt


    public Person() {
        //Individuelle Werte ermitteln...
        this.age = (int) (Math.random() * 88) + 12;
        this.inRiskGroup = (Math.random() < PROB_PERSON_IS_IN_RISK_GROUP);
        this.incubationPeriod = (int) (Math.random() * (MAX_INCUBATION_PERIOD_DAYS - MIN_INCUBATION_PERIOD_DAYS) + MIN_INCUBATION_PERIOD_DAYS);
        this.illnessPeriod = (int) (Math.random() * (MIN_ILLNESS_PERIOD_DAYS - MAX_ILLNESS_PERIOD_DAYS) + MAX_ILLNESS_PERIOD_DAYS);

        this.infected = false;
        this.recovered = false;
        this.dead = false;
    }

    public void infect() {
        this.infected = true;
        this.infectedAtDay = Time.getCurrentDay();
    }

    public void checkCondition() {
        //Inkubationszeit prüfen
        if (Time.getCurrentDay() > this.infectedAtDay + this.incubationPeriod) {
            this.illnessBegun = true;
            illnessAtDay = Time.getCurrentDay();
        }
        //Krankheitszeit prüfen
        if (Time.getCurrentDay() > this.illnessAtDay + this.illnessPeriod) {
            if (Math.random() < PROB_BASIC_FATALITY) {
                this.dead = true;
                this.deathAtDay = Time.getCurrentDay();
            } else {
                this.recovered = true;
                this.recoveredAtDay = Time.getCurrentDay();
            }
            this.infected = false; //Resetten, damit diese Person nicht mehr überprüft wird
        }
    }
}
