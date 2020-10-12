package Controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import View.MainGui;
import lombok.Data;

import static View.MainGui.instance;

public class SimulatorConfig {
    //PROB = Wahrscheinlichkeit, Angabe in Prozent
    //Demografische Eigenschaften
    public static int AMOUNT_OF_PERSONS = 50;
    public static double PROB_PERSON_IS_IN_RISK_GROUP = 0.1;            //Gilt fÃ¼r alle Personen
    public static int AGE_FOR_RISK_GROUP = 65;                          //Ã¼ber 100 setzen wenn keine Risikoalter gewÃ¼nscht ist

    //Pandemie Eigenschaften
    public static int MAX_INCUBATION_PERIOD_DAYS = 14;                  //Max. Inkubationszeit
    public static int MIN_INCUBATION_PERIOD_DAYS = 3;                   //Min. Inkubationszeit
    public static double PROB_BASIC_FATALITY = 0.2;                     //0 = nicht tÃ¶dlich
    public static double PROB_BASIC_INFECTIVITY = 0.5;                  //0 = nicht ansteckend
    public static int MAX_ILLNESS_PERIOD_DAYS = 3;                      //Erholung/Tod frÃ¼hestens nach mind. x Tagen
    public static int MIN_ILLNESS_PERIOD_DAYS = 10;                     //Erholung/Tod spÃ¤testens nach max. x Tagen
    public static boolean IS_INFECTIOUS_IN_INCUBATION_PERIOD = true;    //False = Kann erst nach der Inkubationszeit andere Personen anstecken
    public static double PROB_OF_MOVE = 0.4;                            //0 = bewegt sich nicht

    //Darstellung
    public static int CIRCLE_HEIGHT = 10;
    public static int CIRCLE_WIDTH = 10;
    
    public static void configSettings(){		
		JFrame frameSettings=new JFrame();
		frameSettings.setTitle("Einstellungen");
		frameSettings.setSize(750,900);
		frameSettings.setLocation(250,50);
		frameSettings.setResizable(false);
		frameSettings.getContentPane().setLayout(new GridLayout(0, 2));
		frameSettings.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put( new Integer( 0 ), new JLabel("0.0") );
		labelTable.put( new Integer( 5 ), new JLabel("0.5") );
		labelTable.put( new Integer( 10 ), new JLabel("1.0") );
		
		JLabel amountPersonLabel = new JLabel("Anzahl an möglichen Personen pro Feld:");
		JSlider amountPersons = new JSlider(JSlider.HORIZONTAL, 5, 75, AMOUNT_OF_PERSONS);
		   amountPersons.setMajorTickSpacing(10);
		   amountPersons.setMinorTickSpacing(5);
		   amountPersons.setPaintTicks(true);
		   amountPersons.setPaintLabels(true);
		   frameSettings.getContentPane().add(amountPersonLabel);   
		   frameSettings.getContentPane().add(amountPersons);
		   
	   JLabel probRiskGroupLabel = new JLabel("Wahrscheinlichkeit, dass eine Person in der Risikogruppe ist:");
		JSlider probRiskGroup = new JSlider(JSlider.HORIZONTAL, 0, 10, (int)(PROB_PERSON_IS_IN_RISK_GROUP * 10));
		probRiskGroup.setMajorTickSpacing(5);
		probRiskGroup.setMinorTickSpacing(1);
		probRiskGroup.setLabelTable( labelTable );
		probRiskGroup.setPaintTicks(true);
		probRiskGroup.setPaintLabels(true);
		frameSettings.getContentPane().add(probRiskGroupLabel);   
		frameSettings.getContentPane().add(probRiskGroup);
		
		JLabel ageRiskGroupLabel = new JLabel("Ab diesem Alter befindet sich eine Person in der Risikogruppe: (bei über 100 keine)");
		JSlider ageRiskGroup = new JSlider(JSlider.HORIZONTAL, 20, 105, AGE_FOR_RISK_GROUP);
		ageRiskGroup.setMajorTickSpacing(20);
		ageRiskGroup.setMinorTickSpacing(5);
		ageRiskGroup.setPaintTicks(true);
		ageRiskGroup.setPaintLabels(true);
		frameSettings.getContentPane().add(ageRiskGroupLabel);   
		frameSettings.getContentPane().add(ageRiskGroup);
		
		JLabel incubationPeriodLabel = new JLabel("Unterste und oberste Grenze der möglichen Inkubationszeit:");
		JPanel rangeSlider = new JPanel(new GridLayout(1,2,2,0));
		JSlider minIncubationPeriod = new JSlider(JSlider.HORIZONTAL, 1, 10, MIN_INCUBATION_PERIOD_DAYS);
		minIncubationPeriod.setMajorTickSpacing(2);
		minIncubationPeriod.setMinorTickSpacing(1);
		minIncubationPeriod.setPaintTicks(true);
		minIncubationPeriod.setPaintLabels(true);
		JSlider maxIncubationPeriod = new JSlider(JSlider.HORIZONTAL, 10, 20, MAX_INCUBATION_PERIOD_DAYS);
		maxIncubationPeriod.setMajorTickSpacing(2);
		maxIncubationPeriod.setMinorTickSpacing(1);
		maxIncubationPeriod.setPaintTicks(true);
		maxIncubationPeriod.setPaintLabels(true);
		rangeSlider.add(minIncubationPeriod);
		rangeSlider.add(maxIncubationPeriod);
		frameSettings.getContentPane().add(incubationPeriodLabel);   
		frameSettings.getContentPane().add(rangeSlider);
		
	 JLabel probFatalityLabel = new JLabel("Wahrscheinlichkeit, mit der eine Person am Virus stirbt:");
		JSlider probFatality = new JSlider(JSlider.HORIZONTAL, 0, 10, (int)(PROB_BASIC_FATALITY * 10));
		probFatality.setMajorTickSpacing(5);
		probFatality.setMinorTickSpacing(1);
		probFatality.setLabelTable( labelTable );
		probFatality.setPaintTicks(true);
		probFatality.setPaintLabels(true);
		frameSettings.getContentPane().add(probFatalityLabel);   
		frameSettings.getContentPane().add(probFatality);
	
	 JLabel probInfectivityLabel = new JLabel("Wahrscheinlichkeit, mit der sich eine Person ansteckt:");
		JSlider probInfectivity = new JSlider(JSlider.HORIZONTAL, 0, 10, (int)(PROB_BASIC_INFECTIVITY * 10));
		probInfectivity.setMajorTickSpacing(5);
		probInfectivity.setMinorTickSpacing(1);
		probInfectivity.setLabelTable( labelTable );
		probInfectivity.setPaintTicks(true);
		probInfectivity.setPaintLabels(true);
		frameSettings.getContentPane().add(probInfectivityLabel);   
		frameSettings.getContentPane().add(probInfectivity);
			
		JLabel illnessPeriodLabel = new JLabel("Unterste und oberste Grenze der möglichen Genesungszeit:");
		JPanel rangeSliderIllness = new JPanel(new GridLayout(1,2,2,0));
		JSlider maxIllnessPeriod = new JSlider(JSlider.HORIZONTAL, 0, 6, MAX_ILLNESS_PERIOD_DAYS);
		maxIllnessPeriod.setMajorTickSpacing(2);
		maxIllnessPeriod.setMinorTickSpacing(1);
		maxIllnessPeriod.setPaintTicks(true);
		maxIllnessPeriod.setPaintLabels(true);
		JSlider minIllnessPeriod = new JSlider(JSlider.HORIZONTAL, 6, 12, MIN_ILLNESS_PERIOD_DAYS);
		minIllnessPeriod.setMajorTickSpacing(2);
		minIllnessPeriod.setMinorTickSpacing(1);
		minIllnessPeriod.setPaintTicks(true);
		minIllnessPeriod.setPaintLabels(true);
		rangeSliderIllness.add(maxIllnessPeriod);
		rangeSliderIllness.add(minIllnessPeriod);
		frameSettings.getContentPane().add(illnessPeriodLabel);   
		frameSettings.getContentPane().add(rangeSliderIllness);
		
		JLabel probMoveLabel = new JLabel("Wahrscheinlichkeit, dass eine Person sich an einem Tag bewegt:");
		JSlider probMove = new JSlider(JSlider.HORIZONTAL, 0, 10, (int)(PROB_OF_MOVE * 10));
		probRiskGroup.setMajorTickSpacing(5);
		probRiskGroup.setMinorTickSpacing(1);
		probRiskGroup.setLabelTable( labelTable );
		probRiskGroup.setPaintTicks(true);//not showing
		probRiskGroup.setPaintLabels(true);
		frameSettings.getContentPane().add(probMoveLabel);   
		frameSettings.getContentPane().add(probMove);
		
		JCheckBox infectiousInIncubationPeriod = new JCheckBox("Ansteckend in der Inkubationszeit", IS_INFECTIOUS_IN_INCUBATION_PERIOD);
		frameSettings.getContentPane().add(infectiousInIncubationPeriod);   
		JLabel fillCell = new JLabel();
		frameSettings.getContentPane().add(fillCell); 
		
		JButton save = new JButton("Speichern");
		JButton dismiss = new JButton("Abbrechen");
		
		frameSettings.add(save,-1);
		frameSettings.add(dismiss, -1);
		frameSettings.setVisible(true);
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double riskGroup = probRiskGroup.getValue();
				double fatality = probFatality.getValue();
				double infectivity = probInfectivity.getValue();
				double move = probMove.getValue();
				AMOUNT_OF_PERSONS = amountPersons.getValue();
			    PROB_PERSON_IS_IN_RISK_GROUP = riskGroup / 10;           
			    AGE_FOR_RISK_GROUP = ageRiskGroup.getValue();                          

			    MAX_INCUBATION_PERIOD_DAYS = maxIncubationPeriod.getValue();                  
			    MIN_INCUBATION_PERIOD_DAYS = minIncubationPeriod.getValue();                  
			    PROB_BASIC_FATALITY = fatality / 10;                     
			    PROB_BASIC_INFECTIVITY =  infectivity / 10;                 
			    MAX_ILLNESS_PERIOD_DAYS = maxIllnessPeriod.getValue();                    
			    MIN_ILLNESS_PERIOD_DAYS = minIllnessPeriod.getValue();                    
			    IS_INFECTIOUS_IN_INCUBATION_PERIOD = infectiousInIncubationPeriod.isSelected();   
			    PROB_OF_MOVE = move / 10;                           
				
				instance.setEnabled(true);
				instance.log("Einstellungen gespeichert!");
				frameSettings.dispose();
			}
		});
		
		dismiss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instance.log("Einstellungen verworfen!");
				frameSettings.dispose();
				instance.setEnabled(true);
			}
		});
    }
}