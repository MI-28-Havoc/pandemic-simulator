package Controller;

import Model.Location;
import Model.Person;
import View.MainGui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

import static View.MainGui.getRandomNumberInRange;
import static View.MainGui.instance;
import static Controller.SimulatorConfig.*;

public class PandemicController{
    public static ArrayList<Location> locations = new ArrayList<>();
    public static int amountInfected = 0;
    public static int amountAlive = 0;
    public static int amountRecovered = 0;
    public static int amountDead = 0;
    public static int status;
    
    public static int lastX = 70;
    public static int lastYAlive;
    public static int lastYInfected;
    public static int lastYDead;
    public static int lastYRecovered;

    public static void tick() {
        Time.nextDay();
        for(Location aLocation: locations) {
            for(Person aPerson: aLocation.presentPersons) {
                if (aPerson.isInfected())
                    aPerson.checkCondition();
            }
        }
        graphPaint(instance.getGraphics());
        //Personen auf Grid moven (wahrscheinlichkeit für moven einbauen)
        relocatePersons();
        //Personen stecken andere an ihrer Location an...
        for(Location aLocation: locations) {
            if (aLocation.containsPersons())
                aLocation.spread();
        }
    }

    public static void initialPaint(Graphics oldG) { //Achsenkreuz
        Graphics2D g = (Graphics2D)oldG;

	    g.setPaint(Color.BLACK);
	    g.drawLine(70,930,560,930); //X
	    g.drawLine(70,930,70,550); //Y
	    
	    for (int i=1; i<60; i++) {
	    	g.drawLine(i*8+70, 920, i*8+70,940 ); //X div
	    }
	    for (int i=1; i<19; i++) {
	    	g.drawLine(60,930 - i*20,80,930-i*20); //Y div	
	    }
	    //label
	    g.drawString("t[d]", 565, 930);
	    g.drawString("People[205]", 50, 545);
	    
	    lastYAlive = 930-amountAlive/10;	
	    lastYInfected = 930 - amountInfected/10;
	    lastYDead = 930 - amountDead/10;
	    lastYRecovered = 930 - amountRecovered/10;
    }
    
    public static void graphPaint(Graphics oldG) { //Kurven
        Graphics2D g = (Graphics2D)oldG;
	    g.setStroke(new BasicStroke(5));
        g.setPaint(Color.GREEN);
 	    	
 	    	g.drawLine(lastX, lastYAlive, lastX+8, 930-(amountAlive/10));
 	    	lastYAlive = 930-(amountAlive/10);
 	    	
 	    	g.setPaint(Color.RED);
 	    	g.drawLine(lastX, lastYInfected, lastX+8, 930-(amountInfected/10));
 	    	lastYInfected = 930-(amountInfected/10);
 	    	
 	    	g.setPaint(Color.BLACK);
 	    	g.drawLine(lastX, lastYDead, lastX+8, 930-(amountDead/10));
 	    	lastYDead = 930-(amountDead/10);
 	    	
 	    	g.setPaint(Color.BLUE);
 	    	g.drawLine(lastX, lastYRecovered, lastX+8, 930-(amountRecovered/10));
 	    	lastX = lastX + 8;
 	    	lastYRecovered = 930-(amountRecovered/10);
    }
    
    public static void spawnPersons() {
        //Personen initial spawnen...
        boolean patientZeroIsSet = false;
        for(Location l : locations) {
        	int amountPersons = getRandomNumberInRange(0, AMOUNT_OF_PERSONS);
            for (int i = 0; i < amountPersons; i++) { 
                Person p = new Person(instance.getGraphics());
                l.presentPersons.add(p);
                int locX = l.getX() + 10 + getRandomNumberInRange(0,l.getWidth()-20);
                int locY = l.getY() + 30 + getRandomNumberInRange(0,l.getHeight()-10);
                p.setPosX(locX);
                p.setPosY(locY);
                p.setBounds(0,0,10,10);
                l.add(p);
                p.paintComponent(instance.getGraphics());
            }
        }
    }

    public static void relocatePersons() {
        //alle Locations iterieren und für jede Person darin eine neue Location ermitteln...
        for(Location l : locations) {
            ArrayList<Person> gonePersons = new ArrayList<>();
            if (!l.presentPersons.isEmpty()) {
                for (Person p: l.presentPersons) {
                    if (!p.isDead()) {
                        if (Math.random() < PROB_OF_MOVE) {
                            int newXGrid;
                            int newYGrid;
                            do {
                                newXGrid = l.getXGrid() + getRandomNumberInRange(-1, 1);
                                newYGrid = l.getYGrid() + getRandomNumberInRange(-1, 1);
                            } while (!(0 < newXGrid && newXGrid <= instance.gridCols && 0 < newYGrid && newYGrid <= instance.gridRows));

                            if (newXGrid != l.getXGrid() || newYGrid != l.getYGrid()) {
                                int directionX = newXGrid - l.getXGrid();
                                int directionY = newYGrid - l.getYGrid();

                                int finalNewXGrid = newXGrid;
                                int finalNewYGrid = newYGrid;
                                Optional<Location> newLocation = locations.stream().filter(Location -> (finalNewXGrid == Location.getXGrid() && finalNewYGrid == Location.getYGrid())).findAny();
                                gonePersons.add(p);

                                newLocation.get().presentPersons.add(p);
                                int destX = p.getPosX() + l.getWidth() * directionX;
                                int destY = p.getPosY() + l.getHeight() * directionY;
                                p.setPosX(destX);
                                p.setPosY(destY);
                            }
                        }
                        p.revalidate();
                        p.repaint();
                    }
                    else if (p.getDeathAtDay() + 3 > Time.getCurrentDay()) {
                        //Tote sollen nach 3 Tagen nicht mehr erscheinen
                        p.revalidate();
                        p.repaint();
                    }


                }
            }
            l.presentPersons.removeAll(gonePersons);
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
        instance.revalidate();
        instance.repaint();
        MainGui.main(null);
        //initialPaint(instance.getGraphics()); // TODO warum geht das nicht?? kp lol xd
    }

    public static void refreshGrid() {
        /*for(Location aLocation: locations) {
            for(Person aPerson: aLocation.presentPersons) {
                aPerson.refreshComponent();
            }
        }*/

        instance.grid.revalidate();
        instance.grid.repaint();
        for (Location l : locations) {
            for (Person p: l.presentPersons) {
                p.revalidate();
                p.repaint();
            }
        }
    }

    public static void refreshInfoPanel() {
        instance.lblAliveValue.setText(String.valueOf(amountAlive));
        instance.lblInfectedValue.setText(String.valueOf(amountInfected));
        instance.lblDeadValue.setText(String.valueOf(amountDead));
        instance.lblRecoveredValue.setText(String.valueOf(amountRecovered));
    }
}