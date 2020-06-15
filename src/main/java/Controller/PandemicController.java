package Controller;

import Model.Location;
import Model.Person;
import static Controller.SimulatorConfig.*;
import java.util.ArrayList;

public class PandemicController {
    ArrayList<Person> idioten = new ArrayList<>();
    ArrayList<Location> locations = new ArrayList<>();
    /*TODO:
        -> Tick machen, also nächsten Tag ansetzen mit Time.nextDay
        -> Dann jede Person ticken(gucken ob incubPeriod vorüber -> pastIncub = true usw.)
        UND gucken ob illnessPeriod vorüber -> DEAD OR ALIVE)
        -> Dann jede Location ticken (ERST EVTL. POSITIONSWECHSEL)
     */
    public void tick() {
        Time.nextDay();
        for(Person aPerson: idioten) {
            if (aPerson.isInfected())
                aPerson.checkCondition();
        }
        //Platzhalter, Personen auf Grid moven (wahrscheinlichkeit für moven einbauen)
        for(Location aLocation: locations) {
            if (aLocation.containsPersons())
                aLocation.spread();
        }
    }
}
