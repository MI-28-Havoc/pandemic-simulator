package Model;

import lombok.Data;

import java.util.Random;

import static Controller.SimulatorConfig.*;

@Data
public class Person {
    int posX;
    int posY;
    int age;

    boolean infected; //Wenn true, dann noch nicht erkrankt, sondern Beginn Inkubationszeit
    boolean asymptomatic = false; //Vllt noch verwenden?
    boolean recovered = false; //Damit auch Immun?
    boolean dead = false;
    boolean inRiskGroup = false;
    int incubationPeriod;   //Für jede Person individuell
    int illnessPeriod;      //Für jede Person individuell
    boolean pastIncubationPeriod = false;


    public Person () {
        this.age = (int)(Math.random() * 88)+12;
        this.inRiskGroup = (Math.random() < PROB_PERSON_IS_IN_RISK_GROUP);
        this.infected = false;
        //this.asymptomatic = false; //Vllt noch verwenden?
        this.recovered = false;
        this.dead = false;
    }
}
