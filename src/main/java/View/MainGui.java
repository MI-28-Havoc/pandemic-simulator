package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import Model.Location;
import Model.PandemicDisease;
import Model.Person;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

import javax.swing.JTextPane;


public class MainGui extends JFrame{
	//Feld soll erstmal 6x6 sein
	int x = 10;
	int y = 10;

    ArrayList<Location> locations = new ArrayList<>();
	public MainGui(){
		buildGui();
		
		
	   
	   final JPanel panel_2 = new JPanel();
	   getContentPane().add(panel_2);
	   panel_2.setLayout(new GridLayout(x, y, 1, 1));
	   panel_2.setBackground(Color.black); //= Farbe der Cellborder
	  
	   for (int i = 1; i <= x; i++) {
           for (int j = 1; j <= y; j++) {
        	  Location loc = new Location();
        	  loc.setXGrid(j);
        	  loc.setYGrid(i);
        	  loc.setBackground(Color.white);
        	  panel_2.add(loc);
        	  loc.setLayout(null);
        	  locations.add(loc);
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
				//Person p = new Person();
				for(Location l : locations) {
					for (int i = 0; i < getRandomNumberInRange(0, 4); i++) {
						Person p = new Person();
						p.setPosX(l.getXGrid());
						p.setPosY(l.getYGrid());
						l.presentPersons.add(p);
						int locX = l.getX()+getRandomNumberInRange(15,105);
						int locY = l.getY()+getRandomNumberInRange(35,50);
						p.setLocation(locX, locY);
						p.paintComponent(getGraphics(), locX, locY);
					}
					
				}
				ticktest();
				//pd.initialPaint(getGraphics());
			}
		});
	}
	
	public void ticktest() {
		// akzeptiert die Änderung von presentPersons in der aktuellen Location nicht !?
		for(Location l : locations) {
			ArrayList<Person> gonePersons = new ArrayList<>();
			if (!l.presentPersons.isEmpty()) {
			for (Person p: l.presentPersons) {
				int newX = p.getPosX() + getRandomNumberInRange(-1, 1);
				int newY = p.getPosY() + getRandomNumberInRange(-1, 1);
				if (0 < newX && newX <= this.x && 0 < newY && newY <= this.y && (newX != p.getX() || newY != p.getY())) {
					int deltaX = newX - p.getPosX();
					int deltaY = newY -p.getPosY();
					Optional<Location> test = locations.stream().filter(Location -> (newX == Location.getXGrid() && newY == Location.getYGrid())).findAny();
					gonePersons.add(p);
					p.setPosX(newX);
					p.setPosY(newY);
					test.get().presentPersons.add(p);
					//block von unten hier rein
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
	
	
	public void buildGui(){
		setTitle("Pandemic Simulator");
		setSize(1250,1000);
		setResizable(false);
	    setLocation(50, 10);
	    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(2,1,0,0));
	}
	
		JLabel caption = new JLabel("Pandemic Simulator");
		JButton start = new JButton("Start");
		
	
	public static void main(String[] args) {
		MainGui a = new MainGui();
		a.setVisible(true);
	}

	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}

