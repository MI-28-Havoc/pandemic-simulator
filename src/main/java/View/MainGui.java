package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;

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
        	  loc.setLayout(new GridLayout());
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
		alive.setImage(alive.getImage().getScaledInstance(27,27, Image.SCALE_DEFAULT));	// 13,13 für das Grid oben
		JLabel xml = new JLabel (alive);
		ImageIcon ill = new ImageIcon("src/main/resources/ill.png");
		ill.setImage(ill.getImage().getScaledInstance(29,29, Image.SCALE_DEFAULT));
		JLabel xml2 = new JLabel (ill);
		ImageIcon dead = new ImageIcon("src/main/resources/fatal.png");
		dead.setImage(dead.getImage().getScaledInstance(29,29, Image.SCALE_DEFAULT));
		JLabel xml3 = new JLabel (dead);
		numbers.add(xml);
		numbers.add(xml2);
		numbers.add(xml3);
	  
	   start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Person p = new Person();
				for(Location l : locations) {
					if (l.getXGrid() == p.getPosX() && l.getYGrid() == p.getPosY()) {
						l.presentPersons.add(p);
						ImageIcon alive = new ImageIcon("src/main/resources/alive.png");
						alive.setImage(alive.getImage().getScaledInstance(13,13, Image.SCALE_DEFAULT));	// 13,13 für das Grid oben
						JLabel testLabel = new JLabel (alive);
						l.add(testLabel);
					}
					
					
				}
				pd.initialPaint(getGraphics());
			}
		});
	   
	   
	   
	   
	   
	}
	
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
}

