package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import static Controller.PandemicController.*;

import Controller.PandemicController;
import Controller.SimulatorConfig;
import Controller.Status;
import Controller.Time;
import Model.Location;


public class MainGui extends JFrame {
	public int gridCols = 10;
	public int gridRows = 10;
	public final JPanel grid;
	public final JPanel overview;
	public final JTextPane log;

	public MainGui(){
		buildGui();
	   
	   	grid = new JPanel();
	   	getContentPane().add(grid);
	   	grid.setLayout(new GridLayout(gridCols, gridRows, 1, 1));
	   	grid.setBackground(Color.black); //= Farbe der Cellborder
	  
	   for (int i = 1; i <= gridCols; i++) {
           for (int j = 1; j <= gridRows; j++) {
        	  Location loc = new Location();
        	  loc.setXGrid(j);
        	  loc.setYGrid(i);
        	  loc.setBackground(Color.white);
			   grid.add(loc);
        	  loc.setLayout(null);
        	  loc.setVisible(true);

        	  PandemicController.locations.add(loc);
           }
       }
	   
	   final JPanel graph = new JPanel();
	   graph.setLayout(null);
	   graph.setBackground(Color.lightGray);
	   
	   
	  
	   overview = new JPanel();
	   getContentPane().add(overview);
	   overview.setLayout(new GridLayout(1,2,0,0));
	   overview.add(graph);
	   
	   	   
	   JPanel valuesAndButtonsContainer = new JPanel();
	   valuesAndButtonsContainer.setLayout(new GridLayout(2, 1));

	   JPanel buttons = new JPanel();
	   buttons.setLayout(new GridLayout(2,2));

	   JPanel timeSliderContainer = new JPanel();
	   timeSliderContainer.setLayout(new GridLayout(2,1));

	   JSlider dayDurationInMs = new JSlider(JSlider.HORIZONTAL, 500, 5000, Time.dayDurationInMs);
	   dayDurationInMs.setMajorTickSpacing(500);
	   dayDurationInMs.setMinorTickSpacing(100);
	   dayDurationInMs.setPaintTicks(true);
	   dayDurationInMs.setPaintLabels(true);

	   JLabel lblSlider = new JLabel ();
	   lblSlider.setHorizontalAlignment(SwingConstants.CENTER);
	   lblSlider.setText("Dauer eines Tages in ms:");
	   timeSliderContainer.add(lblSlider);
	   timeSliderContainer.add(dayDurationInMs);

	   JTextPane logPanel = new JTextPane();
	   logPanel.setFont(new Font("Arial", Font.PLAIN, 13));
	   logPanel.setEditable(false);
	   log = logPanel;

	   JScrollPane scrollPane = new JScrollPane(log);
	   scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	   scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	   scrollPane.setAutoscrolls(true);

	   JPanel startReset = new JPanel(new GridLayout(1,2));
	   startReset.add(start);
	   startReset.add(reset);
	   buttons.add(startReset);
	   buttons.add(settings);
	   buttons.add(timeSliderContainer);
	   buttons.add(scrollPane);

	   valuesAndButtonsContainer.add(buttons);
	   overview.add(valuesAndButtonsContainer);
	   
	   JPanel numberContainer = new JPanel();
	   numberContainer.setLayout(new GridLayout(4,2));
	   valuesAndButtonsContainer.add(numberContainer);

	   ImageIcon alive = new ImageIcon("src/main/resources/alive.png");
		alive.setImage(alive.getImage().getScaledInstance(27,27, Image.SCALE_DEFAULT));	
		JLabel lblAlive = new JLabel (alive);
		lblAlive.setText("Am Leben:");

		ImageIcon ill = new ImageIcon("src/main/resources/ill.png");
		ill.setImage(ill.getImage().getScaledInstance(29,29, Image.SCALE_DEFAULT));
		JLabel lblInfected = new JLabel (ill);
		lblInfected.setText("Infiziert:");

		ImageIcon dead = new ImageIcon("src/main/resources/fatal.png");
		dead.setImage(dead.getImage().getScaledInstance(29,29, Image.SCALE_DEFAULT));
		JLabel lblDead = new JLabel (dead);
		lblDead.setText("Verstorben:");

		ImageIcon recovered = new ImageIcon("src/main/resources/recovered1.png");
		recovered.setImage(recovered.getImage().getScaledInstance(29,29, Image.SCALE_DEFAULT));
		JLabel lblRecovered = new JLabel (recovered);
		lblRecovered.setText("Erholt/Immun:");

		lblAliveValue = new JLabel ();
		lblInfectedValue = new JLabel ();
		lblDeadValue = new JLabel ();
		lblRecoveredValue = new JLabel ();


		numberContainer.add(lblAlive);
		numberContainer.add(lblAliveValue);

		numberContainer.add(lblInfected);
		numberContainer.add(lblInfectedValue);

		numberContainer.add(lblRecovered);
		numberContainer.add(lblRecoveredValue);

		numberContainer.add(lblDead);
		numberContainer.add(lblDeadValue);

	   start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start.setEnabled(false);
				instance.log("Simulation gestartet!");
				PandemicController.status = Status.RUNNING;
				Thread appThread = new Thread() {
					public void run() {
						while (amountInfected != 0 && PandemicController.status == Status.RUNNING) {
							if (Time.nextTick()) {
								log("Tag "+Time.getCurrentDay());
								try {
									SwingUtilities.invokeAndWait(() -> {
										PandemicController.refreshGrid();
										PandemicController.refreshInfoPanel();
									});
								} catch (InterruptedException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}

								try {
									tick();
								}
								catch (java.util.ConcurrentModificationException e) {
									e.printStackTrace();
								}
							}
						}

						if (status == Status.RUNNING) {
							instance.log("Simulation beendet am Tag " + Time.getCurrentDay());
							PandemicController.status = Status.FINISHED;
						}
						PandemicController.refreshInfoPanel();
					}
				};
				appThread.start();
				PandemicController.status = Status.RUNNING;
			}
		});

		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Thread appThread = new Thread() {
					public void run() {
						PandemicController.status = Status.READY;
						PandemicController.resetSim();
						start.setEnabled(true);
						initialPaint(instance.getGraphics());
					}
				};
				appThread.start();
			}
		});
		
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				SimulatorConfig.configSettings();
			}
		});

		dayDurationInMs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					Time.dayDurationInMs = (int)source.getValue();
				}
			}
		});

	}
	
	public void buildGui() {
		setTitle("Pandemic Simulator");
		setSize(1250,1000);
		setResizable(false);
	    setLocation(50, 10);
	    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(2,1,0,0));
	}
	
	JLabel caption = new JLabel("Pandemic Simulator");
	JButton start = new JButton("Start");
	JButton reset = new JButton("Zuruecksetzen");
	JButton settings = new JButton("Einstellungen");
		
	public static MainGui instance;
	public static PandemicController controller;
	public JLabel lblAliveValue;
	public JLabel lblInfectedValue;
	public JLabel lblDeadValue;
	public JLabel lblRecoveredValue;

	public static void main(String[] args) {
		if (instance == null) {	
			instance = new MainGui();
			instance.setVisible(true);
			controller = new PandemicController();
		}
		PandemicController.spawnPersons();
		PandemicController.setPatientZero();
		PandemicController.status = Status.READY;
		instance.log("Simulation bereit!");
		PandemicController.initialPaint(instance.getGraphics());
		refreshInfoPanel();
	}

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.ints(min, (max + 1)).findFirst().getAsInt();
	}

	public void log(String s) {
		try {
			Document doc = log.getDocument();
			log.select(doc.getLength(), doc.getLength());
			doc.insertString(doc.getLength(), s+"\n", null);
			log.setAutoscrolls(true);
		} catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}
}

