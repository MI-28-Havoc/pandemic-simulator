package Model;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import static Controller.SimulatorConfig.*;

public class Location extends JPanel{
    public ArrayList<Person> presentPersons = new ArrayList<>();
    private int xGrid;
	private int yGrid;

	 public void setXGrid(int i ){
	     xGrid = i;
	 }
	 public void setYGrid(int i ){
	     yGrid = i;
	 }
	 
	 public int getXGrid(){
	     return xGrid;
	 }
	 public int getYGrid(){
	     return yGrid;
	 }
    
	 public void spread () {
	        int infectiousPersons = 0;
	        for(Person aPerson: presentPersons) {
	            if (IS_INFECTIOUS_IN_INCUBATION_PERIOD) {
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
	            //Formel für Infektiosität bei bspw. 3 erkrankten Personen an einer Location: BASIC_RATE + (BASIC_RATE/2) + (BASIC_RATE/3)
	            for (int i=1;i<infectiousPersons;i++) {
	                probInfectionAtThisLocation += (PROB_BASIC_INFECTIVITY/i+1);
	            }

	            double finalProbInfectionAtThisLocation = probInfectionAtThisLocation; //Ist nötig, da in Lambda-Expressions nur final-Variablen erlaubt sind
	            for(Person aPerson : presentPersons.stream().filter(aPerson->!aPerson.isInfected()).collect(Collectors.toList())) {
	                if (Math.random() < finalProbInfectionAtThisLocation) {
	                    aPerson.infect();
	                }
	            }
	        }
	    }

	    public boolean containsPersons() {
		     return (presentPersons.size() > 0);
	    }
}
