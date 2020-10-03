package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import static Controller.PandemicController.*;

import Controller.PandemicController;
import Controller.Time;
import Model.Location;
import Model.PandemicDisease;
import Model.Person;

import javax.swing.ImageIcon;


public class MainGui extends JFrame implements ComponentListener {
	int x = 10;
	int y = 10;
	public final JPanel grid;
	public final JPanel overview;

	public MainGui(){
		buildGui();
	   
	   	grid = new JPanel();
	   	getContentPane().add(grid);
	   	grid.setLayout(new GridLayout(x, y, 1, 1));
	   	grid.setBackground(Color.black); //= Farbe der Cellborder
	   	//panel_2.addComponentListener(this);
	  
	   for (int i = 1; i <= x; i++) {
           for (int j = 1; j <= y; j++) {
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
	   
	   PandemicDisease pd = new PandemicDisease();
	   
	   JPanel valuesAndButtons = new JPanel();
	   valuesAndButtons.setLayout(new GridLayout(2, 1));

	   //START
	   JPanel buttons = new JPanel();
	   buttons.setLayout(new GridLayout(2,1));
	   buttons.add(start);
	   buttons.add(reset);
	   //ENDE

	   valuesAndButtons.add(buttons);
	   overview.add(valuesAndButtons);
	   
	   JPanel numbers = new JPanel();
	   numbers.setLayout(new GridLayout(4,2));
	   valuesAndButtons.add(numbers);
	   numbers.addComponentListener(this);

	   ImageIcon alive = new ImageIcon("src/main/resources/alive.png");
		alive.setImage(alive.getImage().getScaledInstance(27,27, Image.SCALE_DEFAULT));	// 13,13 fï¿½r das Grid oben
		JLabel lblAlive = new JLabel (alive);
		lblAlive.setText("Gesund:");

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


		numbers.add(lblAlive);
		numbers.add(lblAliveValue);

		numbers.add(lblInfected);
		numbers.add(lblInfectedValue);

		numbers.add(lblRecovered);
		numbers.add(lblRecoveredValue);

		numbers.add(lblDead);
		numbers.add(lblDeadValue);

	   start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread appThread = new Thread() {
					public void run() {
						while (amountInfected != 0) {
							relocatePersons();
							tick();
							try {
								SwingUtilities.invokeAndWait(() -> PandemicController.refreshGrid());
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
						System.out.println("Finished on " + Thread.currentThread() + ", Tag: "+ Time.getCurrentDay());
						//PandemicController.refreshGrid();
						for(Location aLocation: locations) {
							aLocation.revalidate();
							aLocation.repaint();
						}
					}
				};
				appThread.start();
				PandemicController.refreshGrid();
			}
		});

		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread appThread = new Thread() {
					public void run() {
						if (amountInfected == 0) {
							PandemicController.resetSim();
						}

						System.out.println("Simulation resetted");
					}
				};
				appThread.start();
			}
		});
	}


	
	public void relocatePersons() {
		// akzeptiert die Änderung von presentPersons in der aktuellen Location nicht !?
		//alle Locations iterieren und für jede Person darin eine neue Location ermitteln...
		for(Location l : locations) {
			ArrayList<Person> gonePersons = new ArrayList<>();
			if (!l.presentPersons.isEmpty()) {
				for (Person p: l.presentPersons) {
					//int newX = p.getPosX() + getRandomNumberInRange(-1, 1);
					//int newY = p.getPosY() + getRandomNumberInRange(-1, 1);
					int newXGrid = l.getXGrid() + getRandomNumberInRange(-1, 1);
					int newYGrid = l.getYGrid() + getRandomNumberInRange(-1, 1);
					if (0 < newXGrid && newXGrid <= this.x && 0 < newYGrid && newYGrid <= this.y && (newXGrid != l.getXGrid() || newYGrid != l.getYGrid())) {
						int directionX = newXGrid - l.getXGrid();
						int directionY = newYGrid - l.getYGrid();
						Optional<Location> newLocation = locations.stream().filter(Location -> (newXGrid == Location.getXGrid() && newYGrid == Location.getYGrid())).findAny();
						gonePersons.add(p);

						newLocation.get().presentPersons.add(p);
						//START Block von unten:
						int destX = p.getPosX() + 125 * directionX;
						int destY = p.getPosY() + 49 * directionY;
						p.setPosX(destX);
						p.setPosY(destY);
						p.revalidate();
						p.repaint();
						/*TEST START
						Graphics2D g = (Graphics2D) instance.getGraphics();
						g.setColor(Color.magenta);
						g.fill(new Ellipse2D.Double(destX, destY, CIRCLE_WIDTH, CIRCLE_HEIGHT));

						p.setBounds(destX, destY, CIRCLE_WIDTH, CIRCLE_HEIGHT);
						//TEST ENDE */
						/*

						int destX = p.getX() + 49 * deltaX;
						int destY = p.getY() + 125 * deltaY;
						while (destX != p.getX() || destY != p.getY()) {
							int currentX = p.getX();
							int currentY = p.getY();
							if (destX < currentX && destY < currentY) {
								currentX -= 1;
								currentY -= 1;
								p.setLocation(currentX, currentY);

							} else if (destX > currentX && destY > currentY) {
								currentX += 1;
								currentY += 1;
								//p.setLocation(currentX, currentY);
								p.paint(currentX, currentY);

							} else if (destY < currentY && destX > currentX) {
								currentY -= 1;
								currentX += 1;
								//p.setLocation(currentX, currentY);
								p.paint(currentX, currentY);

							} else if (destY > currentY && destX < currentX) {
								currentY += 1;
								currentX -= 1;
								//p.setLocation(currentX, currentY);
								p.paint(currentX, currentY);

							} else if (destY == currentY && destX < currentX) {
								currentX -= 1;
								//p.setLocation(currentX, currentY);
								p.paint(currentX, currentY);

							} else if (destY == currentY && destX > currentX) {
								currentX += 1;
								//p.setLocation(currentX, currentY);
								p.paint(currentX, currentY);

							} else if (destY > currentY && destX == currentX) {
								currentY += 1;
								//p.setLocation(currentX, currentY);
								p.paint(currentX, currentY);

							} else if (destY < currentY && destX == currentX) {
								currentY -= 1;
								//p.setLocation(currentX, currentY);
								p.paint(currentX, currentY);

							}
							//p.repaint();
						}
						*/
						//ENDE Block von unten:
					}

				}
				//p.paintComponent(getGraphics(), l.getX()+getRandomNumberInRange(15,105), l.getY()+getRandomNumberInRange(35,50));
			}
			
			l.presentPersons.removeAll(gonePersons);
		}
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
		
	public static MainGui instance;
	public static PandemicController controller;
	public JLabel lblAliveValue = new JLabel ();
	public JLabel lblInfectedValue = new JLabel ();
	public JLabel lblDeadValue = new JLabel ();
	public JLabel lblRecoveredValue = new JLabel ();

	public static void main(String[] args) {
		if (instance == null) {
			instance = new MainGui();
			instance.setVisible(true);
			controller = new PandemicController();
		}
		SwingUtilities.invokeLater(() -> PandemicController.refreshGrid());
		PandemicController.spawnPersons();
		PandemicController.setPatientZero();
	}

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		SwingUtilities.invokeLater(() -> {
			//PandemicController.spawnPersons();
			//PandemicController.setPatientZero();
		}
		);

	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}
}

