package Model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.swing.*;

import Controller.Time;

import static Controller.PandemicController.*;
import static Controller.SimulatorConfig.*;

public class Person extends JPanel {
    int posX;
    int posY;
    Graphics2D visualPerson;
    int age;

    boolean infected; //Wenn true, dann noch nicht erkrankt, sondern Beginn Inkubationszeit
    boolean recovered = false; //Damit auch immun
    boolean dead = false;
    boolean inRiskGroup = false;
    int incubationPeriod;   //Für jede Person individuell
    int illnessPeriod;      //Für jede Person individuell

    int infectedAtDay;      //Tag der Infizierung
    int illnessAtDay;       //Tag Beginn der Erkrankung
    int deathAtDay;         //Tag des Todes
    int recoveredAtDay;     //Tag der Erholung

    boolean illnessBegun = false;  //Inkubation abgeschlossen, Person ist erkrankt


    public Person (Graphics g) {
    	//Individuelle Werte ermitteln...
        visualPerson = (Graphics2D) g;
        this.age = (int) (Math.random() * 88) + 12;
        if (age >= AGE_FOR_RISK_GROUP) {
            this.inRiskGroup = true;
        }
        else {
            this.inRiskGroup = (Math.random() < PROB_PERSON_IS_IN_RISK_GROUP);
        }
        this.incubationPeriod = (int) (Math.random() * (MAX_INCUBATION_PERIOD_DAYS - MIN_INCUBATION_PERIOD_DAYS) + MIN_INCUBATION_PERIOD_DAYS);
        this.illnessPeriod = (int) (Math.random() * (MIN_ILLNESS_PERIOD_DAYS - MAX_ILLNESS_PERIOD_DAYS) + MAX_ILLNESS_PERIOD_DAYS);
        this.setBackground(null);
        this.infected = false;
        this.recovered = false;
        this.dead = false;
        this.setVisible(true);
        this.setLayout(null);
        amountAlive++;
    }
    
    public void infect() {
        this.infected = true;
        this.infectedAtDay = Time.getCurrentDay();
        amountInfected++;
    }
    
    public void checkCondition() {
        //Inkubationszeit prüfen
        if ((Time.getCurrentDay() > this.infectedAtDay + this.incubationPeriod) && !this.illnessBegun) {
            this.illnessBegun = true;
            illnessAtDay = Time.getCurrentDay();
        }

        //Krankheitszeit prüfen
        if (Time.getCurrentDay() > (this.illnessAtDay + this.illnessPeriod) && this.illnessBegun) {
            int factorForRiskGroup = this.isInRiskGroup() ? 2 : 1; //Todeswahrscheinlichkeit verdoppelt bei Risikogruppe
            if (Math.random() < PROB_BASIC_FATALITY*factorForRiskGroup) {
                this.dead = true;
                this.deathAtDay = Time.getCurrentDay();
                amountDead++;
                amountAlive--;
            } else {
                this.recovered = true;
                this.recoveredAtDay = Time.getCurrentDay();
                amountRecovered++;
            }
            amountInfected--;
            this.infected = false; //Resetten, damit diese Person nicht mehr überprüft wird
        }
    }

    public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	@Override
    public void paintComponent(final Graphics g) {
        //Grün   : neutral
        //Rot    : Krank
        //Blau   : Genesen
        //Schwarz: tot (aber nur für kurze Zeit/paar Ticks, danach weg vom Grid)
        super.paintComponent(g);
        if (this.infected) {
            visualPerson.setColor(Color.RED);
        } else if (this.recovered) {
            visualPerson.setColor(Color.BLUE);
        } else if (this.dead) {
            visualPerson.setColor(Color.BLACK);
        }
        else {
            visualPerson.setColor(Color.GREEN);
        }
        visualPerson.fill(new Ellipse2D.Double(posX, posY, CIRCLE_WIDTH, CIRCLE_HEIGHT));
    }

    public boolean isInfected() {
        return infected;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isInRiskGroup() {
        return inRiskGroup;
    }

    public int getDeathAtDay() {
        return deathAtDay;
    }

    public boolean isRecovered() {
        return recovered;
    }
}
