package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static Controller.PandemicController.locations;
import static Controller.PandemicController.spawnPersons;

import Controller.PandemicController;
import Model.Location;
import Model.PandemicDisease;
import Model.Person;

import javax.swing.ImageIcon;


public class MainGui extends JFrame implements ComponentListener{
	//Feld soll erstmal 6x6 sein
	int x = 10;
	int y = 10;

	public MainGui(){
		buildGui();
	   
	   final JPanel panel_2 = new JPanel();
	   getContentPane().add(panel_2);
	   panel_2.setLayout(new GridLayout(x, y, 1, 1));
	   panel_2.setBackground(Color.black); //= Farbe der Cellborder
	   //panel_2.addComponentListener(this);
	  
	   for (int i = 1; i <= x; i++) {
           for (int j = 1; j <= y; j++) {
        	  Location loc = new Location();
        	  loc.setXGrid(j);
        	  loc.setYGrid(i);
        	  loc.setBackground(Color.white);
        	  panel_2.add(loc);
        	  loc.setLayout(null);
        	  PandemicController.locations.add(loc);
           }
       }
	   
	   final JPanel graph = new JPanel();
	   graph.setLayout(null);
	   graph.setBackground(Color.lightGray);
	   
	   
	   final JPanel overview = new JPanel();
	   getContentPane().add(overview);
	   overview.setLayout(new GridLayout(1,2,0,0));
	   overview.add(graph);
	   
	   PandemicDisease pd = new PandemicDisease();
	   
	   JPanel values = new JPanel();
	   values.setLayout(new GridLayout(2, 1));
	   values.add(start);
	   overview.add(values);
	   
	   JPanel numbers = new JPanel();
	   numbers.setLayout(new GridLayout(3,1));
	   values.add(numbers);
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

		numbers.add(lblAlive);
		numbers.add(lblInfected);
		numbers.add(lblDead);
	  
	   start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spawnPersons();
				//Personen initial spawnen...

				/*MOVED ZU PANDEMICCONTROLLER
				for(Location l : locations) {
					for (int i = 0; i < getRandomNumberInRange(0, 4); i++) {
						Person p = new Person(getGraphics());
						p.setPosX(l.getXGrid());
						p.setPosY(l.getYGrid());
						l.presentPersons.add(p);
						int locX = l.getX()+getRandomNumberInRange(15,105);
						int locY = l.getY()+getRandomNumberInRange(35,50);
						p.setLocation(locX, locY);
						p.paintComponent(locX, locY);
					}

				}*/
				//ticktest();
				//pd.initialPaint(getGraphics());
			}
		});
	}
	
	public void ticktest() {
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
						Optional<Location> test = locations.stream().filter(Location -> (newXGrid == Location.getXGrid() && newYGrid == Location.getYGrid())).findAny();
						gonePersons.add(p);

						test.get().presentPersons.add(p);
						//START Block von unten:
						int destX = p.getPosX() + 49 * directionX;
						int destY = p.getPosY() + 125 * directionY;

						int currentX = p.getPosX();
						int currentY = p.getPosY();

						while (destX != currentX || destY != currentY) {

							if (destX < currentX && destY < currentY) {
								currentX -= 1;
								currentY -= 1;
								//p.setLocation(currentX, currentY);
								p.paint(currentX, currentY);

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
						//ENDE Block von unten:
					}

				}
				//p.paintComponent(getGraphics(), l.getX()+getRandomNumberInRange(15,105), l.getY()+getRandomNumberInRange(35,50));
			}
			
			l.presentPersons.removeAll(gonePersons);
		}
	}
	
	
//Block von unten:
	/*int destX = p.getX() + 49 * deltaX;
	int destY = p.getY() + 125 * deltaY;
	while (destX != p.getX() || destY != p.getY()) {
		int currentX = p.getX();
		int currentY = p.getY();
		if (destX < currentX && destY < currentY) {
			currentX -= 1;
			currentY -= 1;
			p.setLocation(currentX, currentY);
			
		}
		else if(destX > currentX && destY > currentY) {
			currentX += 1;
			currentY += 1;
			p.setLocation(currentX, currentY);
			
		}
		else if(destY < currentY && destX > currentX) {
			currentY -= 1;
			currentX += 1;
			p.setLocation(currentX, currentY);
			
		}
		else if(destY > currentY && destX < currentX ) {
			currentY += 1;
			currentX -= 1;
			p.setLocation(currentX, currentY);
			
		}
		else if(destY == currentY && destX < currentX ) {
			currentX -= 1;
			p.setLocation(currentX, currentY);
			
		}
		else if(destY == currentY && destX > currentX ) {
			currentX += 1;
			p.setLocation(currentX, currentY);
			
		}
		else if(destY > currentY && destX == currentX ) {
			currentY += 1;
			p.setLocation(currentX, currentY);
			
		}
		else if(destY < currentY && destX == currentX ) {
			currentY -= 1;
			p.setLocation(currentX, currentY);
			
		}
		p.repaint();
	}*/
	
	
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
		
	public static MainGui instance;
	public static PandemicController controller;
	public static void main(String[] args) {
		instance = new MainGui();
		instance.setVisible(true);
		controller = new PandemicController();
		//PandemicController.spawnPersons();
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
		System.out.println("Ja1");
		PandemicController.spawnPersons();
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		System.out.println("Ja2");
		PandemicController.spawnPersons();
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		System.out.println("Ja3");
		PandemicController.spawnPersons();
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		System.out.println("Ja4");
		PandemicController.spawnPersons();
		
	}
}

