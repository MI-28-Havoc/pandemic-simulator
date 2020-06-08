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
        -> Dann jede Person ticken(gucken ob incubPeriod vorüber -> pastIncub = true
        UND gucken ob illnessPeriod vorüber -> DEAD OR ALIVE)
        -> Dann jede Location ticken (ERST EVTL. POSITIONSWECHSEL)
     */
}
