package Controller;

import Model.Location;
import Model.Person;

import java.awt.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Optional;

import static View.MainGui.getRandomNumberInRange;
import static View.MainGui.instance;

public class PandemicController{
    public static ArrayList<Location> locations = new ArrayList<>();
    public static int amountInfected = 0;
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

        //Platzhalter: Personen auf Grid moven (wahrscheinlichkeit für moven einbauen)
        //Platzhalter: Personen / Punkte zeichnen. Am besten auch in eigenem thread
        for(Location aLocation: locations) {
            if (aLocation.containsPersons())
                aLocation.spread();
        }
    }

    public static void initialPaint(Graphics oldG) { //Für den Graphen
        Graphics2D g = (Graphics2D)oldG;

        //Achsenkreuz
        g.setPaint(new Color(0,0,0));
        //g.drawLine(x+10,y+10,500,y+10); //X
        //g.drawLine(x+10,y+10,x+10,0); //Y
        g.setFont(new Font("Arial", Font.BOLD,20));  //Attribut, Gr��e in [pt]
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.75f));
        //Formel
        //g.drawString("f(x) = "+model.getA()+"*x^3 + "+model.getB()+"*x^2 + "+model.getC()+"*x + "+model.getD(),10,30);
        g.drawLine(50, 950, 550, 950);	//x-Achse
        g.drawLine(50, 950, 50, 600);	//y-Achse


        // Werte
        //g.setPaint(new Color(255,0,0));

        // g.drawString("HalloWelt",100,100);
        // g.drawLine(50,50+50*(int)(model.getA()),150,150);
        // g.draw
	    /*int lastX = 0, lastY = 0;
	    //Funktion plotten
	    for(int i=-1000; i<=1000; i++) {
	    	x = i / 100.0;
	    	y = model.f(x);
	    	//g.fillOval((int)(150+x*50), (int)(150-y*50), 5, 5);
	    	if (i!=-1000)
	    		g.drawLine(lastX, lastY, (int)(150+x*pixelPerValue), (int)(150-y*pixelPerValue));
	    	lastX = (int)(150+x*pixelPerValue);
	    	lastY = (int)(150-y*pixelPerValue);
	    }*/
    }
    public static void spawnPersons() {
        //Personen initial spawnen...
        boolean patientZeroIsSet = false;
        for(Location l : locations) {
            for (int i = 0; i < getRandomNumberInRange(0, 4); i++) {
                Person p = new Person(instance.getGraphics());
                l.presentPersons.add(p);
                int locX = l.getX()+getRandomNumberInRange(15,105);
                int locY = l.getY()+getRandomNumberInRange(35,50);
                p.setPosX(locX);
                p.setPosY(locY);
                //p.setLocation(locX, locY);
                p.paintComponent(locX, locY);
            }
        }
    }

    public static void setPatientZero() {
        //Random Zelle ermitteln...
        int randomGridX = 0;
        int randomGridY = 0;
        Location randomLocation;
        do {
            randomGridX = getRandomNumberInRange(1,10);
            randomGridY = getRandomNumberInRange(1,10);
            int finalRandomGridX = randomGridX;
            int finalRandomGridY = randomGridY;
            randomLocation = locations.stream().filter(Location -> (finalRandomGridX == Location.getXGrid() && finalRandomGridY == Location.getYGrid())).findAny().get();
        } while(randomLocation.presentPersons.size() == 0);
        //Ab hier Zelle ermittelt mit mind. 1 Person drin
        randomLocation.presentPersons.get(0).infect();
        randomLocation.presentPersons.get(0).paintComponent(randomLocation.presentPersons.get(0).getPosX(),randomLocation.presentPersons.get(0).getPosY());
    }

    public static void refreshGrid() {
        for(Location aLocation: locations) {
            for(Person aPerson: aLocation.presentPersons) {
                aPerson.refreshComponent();
            }
        }
    }
}