package Model;

import java.util.ArrayList;
import static Controller.SimulatorConfig.*;

public class Location {
    ArrayList<Person> presentPersons = new ArrayList<>();
    public void spread () {
        int infectiousPersons = 0;
        for(Person aPerson: presentPersons) {
            if (IS_INFECTIOUS_IN_INCUBATION_PERIOD) { //Wenn Erreger auch während Inkubation ansteckend ist...
                if (aPerson.isInfected()) {
                    infectiousPersons++;
                }
            }
            else {
                if (aPerson.isInfected() && aPerson.illnessBegun) {
                    infectiousPersons++;
                }
            }
        }
        if (infectiousPersons > 0) {
            double probInfectionAtThisLocation = PROB_BASIC_INFECTIVITY;
            //Formel für Infektiosität bei bspw. 3 erkrankten Personen an einer Location: BASIS_WERT + (BASIS_WERT/2) + (BASIS_WERT/3)
            for (int i=1;i<infectiousPersons;i++) {
                probInfectionAtThisLocation += (PROB_BASIC_INFECTIVITY/i+1);
            }

            double finalProbInfectionAtThisLocation = probInfectionAtThisLocation; //Ist nötig, da in Lambda-Expressions nur final-Variablen erlaubt sind
            presentPersons.stream().filter(aPerson->!aPerson.isInfected()).
                    forEach(
                            aPerson -> {
                                if(Math.random() < finalProbInfectionAtThisLocation) {
                                    aPerson.infect();
                                }
                            }
                    );
        }
    }
    public boolean containsPersons() {
        return (presentPersons.size()>0);
    }
}
