package Model;

import java.util.ArrayList;
import static Controller.SimulatorConfig.*;

public class Location {
    ArrayList<Person> presentPersons = new ArrayList<>();
    public void spread () {
        int infectiousPersons = 0;
        for(Person aPerson: presentPersons) {
            if (IS_INFECTIOUS_IN_INCUBATION_PERIOD) {
                if (aPerson.isInfected()) {
                    infectiousPersons++;
                }
            }
            else {
                if (aPerson.isInfected() && aPerson.pastIncubationPeriod) {
                    infectiousPersons++;
                }
            }
        }
        if (infectiousPersons > 0) {
            double probInfectionAtThisLocation = PROB_BASIC_INFECTIVITY;
            //Formel für Infektiosität bei bspw. 3 erkrankten Personen an einer Location: BASIC_RATE + (BASIC_RATE/2) + (BASIC_RATE/3)
            for (int i=1;i<infectiousPersons;i++) {
                probInfectionAtThisLocation += (PROB_BASIC_INFECTIVITY/i+1);
            }

            double finalProbInfectionAtThisLocation = probInfectionAtThisLocation; //Ist nötig, da in Lambda-Expressions nur final-Variablen erlaubt sind
            presentPersons.stream().filter(aPerson->!aPerson.isInfected()).
                    forEach(
                            aPerson -> aPerson.setInfected((Math.random() < finalProbInfectionAtThisLocation))
                    );
        }
    }
}
