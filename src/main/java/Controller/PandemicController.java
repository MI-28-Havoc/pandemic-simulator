package Controller;

import Model.Location;
import Model.Person;
import View.MainGui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static View.MainGui.getRandomNumberInRange;
import static View.MainGui.instance;

public class PandemicController{
    public static ArrayList<Location> locations = new ArrayList<>();
    public static int amountInfected = 0;
    public static int amountAlive = 0;
    public static int amountRecovered = 0;
    public static int amountDead = 0;
    
    public static int lastX = 70;
    public static int lastYAlive = 930;	//in setPateientZero setzen?
    public static int lastYInfected = 930;
    public static int lastYDead = 930;
    public static int lastYRecovered = 930;

    /*TODO:
        -> Tick machen, also nächsten Tag ansetzen mit Time.nextDay (DONE)
        -> Dann jede Person ticken(gucken ob incubPeriod vorüber -> pastIncub = true usw.)
        UND gucken ob illnessPeriod vorüber -> DEAD OR ALIVE)
        -> Dann jede Location ticken (ERST EVTL. POSITIONSWECHSEL)
     */
    public static void tick() {
        Time.nextDay();
        for(Location aLocation: locations) {
            for(Person aPerson: aLocation.presentPersons) {
                if (aPerson.isInfected())
                    aPerson.checkCondition();
            }
        }
        graphPaint(instance.getGraphics());
        //Platzhalter: Personen auf Grid moven (wahrscheinlichkeit für moven einbauen)
        //Platzhalter: Personen / Punkte zeichnen. Am besten auch in eigenem thread
        for(Location aLocation: locations) {
            if (aLocation.containsPersons())
                aLocation.spread();
        }
    }

    public static void initialPaint(Graphics oldG) { //Achsenkreuz
        Graphics2D g = (Graphics2D)oldG;
	    
	    //Achsenkreuz
	    g.setPaint(Color.BLACK);
	    g.drawLine(70,930,560,930); //X
	    g.drawLine(70,930,70,550); //Y
	    
	    for (int i=1; i<25; i++) {
	    	g.drawLine(i*20+70, 920, i*20+70,940 ); //X div
	    }
	    for (int i=1; i<19; i++) {
	    	g.drawLine(60,930 - i*20,80,930-i*20); //Y div	
	    }
    }
    
    public static void graphPaint(Graphics oldG) { //Kurven
        Graphics2D g = (Graphics2D)oldG;
	    
        g.setPaint(Color.GREEN);
 	    	
 	    	g.drawLine(lastX, lastYAlive, lastX+20, 930-(amountAlive/10));
 	    	lastYAlive = 930-(amountAlive/10);
 	    	
 	    	g.setPaint(Color.RED);
 	    	g.drawLine(lastX, lastYInfected, lastX+20, 930-(amountInfected/10));
 	    	lastYInfected = 930-(amountInfected/10);
 	    	
 	    	g.setPaint(Color.BLACK);
 	    	g.drawLine(lastX, lastYDead, lastX+20, 930-(amountDead/10));
 	    	lastYDead = 930-(amountDead/10);
 	    	
 	    	g.setPaint(Color.BLUE);
 	    	g.drawLine(lastX, lastYRecovered, lastX+20, 930-(amountRecovered/10));
 	    	lastX = lastX + 20;
 	    	lastYRecovered = 930-(amountRecovered/10);
    }
    
    public static void spawnPersons() {
        //Personen initial spawnen...
        boolean patientZeroIsSet = false;
        for(Location l : locations) {
        	int amountPersons = getRandomNumberInRange(0, 50);
            for (int i = 0; i < amountPersons; i++) { 
                Person p = new Person(instance.getGraphics());
                l.presentPersons.add(p);
                int locX = l.getX()+getRandomNumberInRange(10,l.getWidth()-10);
                int locY = l.getY()+getRandomNumberInRange(30,l.getHeight()+20);
                p.setPosX(locX);
                p.setPosY(locY);
                p.setBounds(0,0,10,10);
                l.add(p);
                p.paintComponent(instance.getGraphics());
            }
        }
    }

    public static void setPatientZero() {
        if (amountInfected == 0) {
            //Random Zelle ermitteln...
            int randomGridX = 0;
            int randomGridY = 0;
            Location randomLocation;
            do {
                randomGridX = getRandomNumberInRange(1, 10);
                randomGridY = getRandomNumberInRange(1, 10);
                int finalRandomGridX = randomGridX;
                int finalRandomGridY = randomGridY;
                randomLocation = locations.stream().filter(Location -> (finalRandomGridX == Location.getXGrid() && finalRandomGridY == Location.getYGrid())).findAny().get();
            } while (randomLocation.presentPersons.size() == 0);
            //Ab hier Zelle ermittelt mit mind. 1 Person drin
            randomLocation.presentPersons.get(0).infect();
            //RAUS TEST randomLocation.presentPersons.get(0).paintComponent(randomLocation.presentPersons.get(0).getPosX(), randomLocation.presentPersons.get(0).getPosY());
            randomLocation.presentPersons.get(0).revalidate();
            randomLocation.presentPersons.get(0).repaint();
        }
    }

    public static void resetSim() {
        amountInfected = 0;
        amountAlive = 0;
        amountRecovered = 0;
        amountDead = 0;

        for(Location aLocation: locations) {
            aLocation.removeAll();
            aLocation.presentPersons.clear();
        }
        lastX = 70;
        Time.reset();
        MainGui.main(null);
    }

    public static void refreshGrid() {
        /*for(Location aLocation: locations) {
            for(Person aPerson: aLocation.presentPersons) {
                aPerson.refreshComponent();
            }
        }*/
        instance.lblAliveValue.setText(String.valueOf(amountAlive));
        instance.lblInfectedValue.setText(String.valueOf(amountInfected));
        instance.lblDeadValue.setText(String.valueOf(amountDead));
        instance.lblRecoveredValue.setText(String.valueOf(amountRecovered));


        instance.grid.revalidate();
        instance.grid.repaint();
        //raus. Sorgt für ständigen reset des Verlaufsgrafen
        //instance.overview.revalidate();
        //instance.overview.repaint();
    }
}