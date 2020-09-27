package Controller;

import Model.Location;
import Model.Person;

import java.util.ArrayList;

import javax.swing.JPanel;

public class PandemicController{
    ArrayList<Person> persons = new ArrayList<>();
    ArrayList<Location> locations = new ArrayList<>();
    /*TODO:
        -> Tick machen, also nächsten Tag ansetzen mit Time.nextDay (DONE)
        -> Dann jede Person ticken(gucken ob incubPeriod vorüber -> pastIncub = true usw.)
        UND gucken ob illnessPeriod vorüber -> DEAD OR ALIVE)
        -> Dann jede Location ticken (ERST EVTL. POSITIONSWECHSEL)
     */
    public void tick() {
        Time.nextDay();
        for(Person aPerson: persons) {
            if (aPerson.isInfected())
                aPerson.checkCondition();
        }
        //Platzhalter: Personen auf Grid moven (wahrscheinlichkeit für moven einbauen)
        //Platzhalter: Personen / Punkte zeichnen. Am besten auch in eigenem thread
        for(Location aLocation: locations) {
            if (aLocation.containsPersons())
                aLocation.spread();
        }
    }

    public PandemicController (){
        //Objekte erstellen
    }
}