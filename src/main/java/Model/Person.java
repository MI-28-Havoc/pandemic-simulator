package Model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.swing.*;

import Controller.Time;

import static Controller.SimulatorConfig.*;
import static Controller.PandemicController.amountInfected;

@Data
public class Person extends JPanel {
    int posX;
    int posY;
    Graphics2D visualPerson;
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


    public Person (Graphics g) {
    	//Individuelle Werte ermitteln...
        visualPerson = (Graphics2D) g;
        this.age = (int) (Math.random() * 88) + 12;
        this.inRiskGroup = (Math.random() < PROB_PERSON_IS_IN_RISK_GROUP);
        this.incubationPeriod = (int) (Math.random() * (MAX_INCUBATION_PERIOD_DAYS - MIN_INCUBATION_PERIOD_DAYS) + MIN_INCUBATION_PERIOD_DAYS);
        this.illnessPeriod = (int) (Math.random() * (MIN_ILLNESS_PERIOD_DAYS - MAX_ILLNESS_PERIOD_DAYS) + MAX_ILLNESS_PERIOD_DAYS);

        this.infected = false;
        this.recovered = false;
        this.dead = false;
        this.setVisible(true);
        this.setLayout(null);
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

    public void paintComponent(int x, int y) {
        //Grün   : neutral
        //Rot    : Krank
        //Blau   : Genesen
        //Schwarz: tot (aber nur für kurze Zeit/paar Ticks, danach weg vom Grid)
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
        visualPerson.fill(new Ellipse2D.Double(x, y, CIRCLE_WIDTH, CIRCLE_HEIGHT));
        this.setBounds(x, y, CIRCLE_WIDTH, CIRCLE_HEIGHT);
        //visualPerson.translate(x,y);
    }

    public void refreshComponent() {
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
        this.setBounds(posX, posY, CIRCLE_WIDTH, CIRCLE_HEIGHT);
        this.revalidate();
        this.repaint();
    }
}
