package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import Model.Location;
import Model.Person;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.JTextPane;



public class MainGui extends JFrame{
	//Feld soll erstmal 6x6 sein
	int x = 6;
	int y = 6;
	public MainGui(){
		buildGui();
		
	   
	   JPanel panel_2 = new JPanel();
	   getContentPane().add(panel_2);
	   panel_2.setLayout(new GridLayout(x, y, 0, 0));
	   
	   
	   for (int i = 0; i < x; i++) {
           for (int j = 0; j < y; j++) {
        	  Location loc = new Location();
        	  loc.setx(j);
        	  loc.sety(i);
        	  if(j % 2 == 0 && i % 2 == 0)
        		  loc.setBackground(Color.BLUE);
        	  else if(j % 2 == 0 && i % 2 != 0)
        		  loc.setBackground(Color.red); 
        	  else if(j % 2 != 0 && i % 2 != 0)
        		  loc.setBackground(Color.green); 
        	  else if(j % 2 != 0 && i % 2 == 0)
        		  loc.setBackground(Color.yellow); 
        	  panel_2.add(loc);
           }
       }
	   
	   JPanel panel_1 = new JPanel();
	   getContentPane().add(panel_1);
	   panel_1.add(start);
	  
	   start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Start Simulation
			}
		});
	   
	}
	
	public void buildGui(){
		setTitle("Pandemic Simulator");
		setSize(1250,1000);
		setResizable(false);
	    setLocation(50, 10);
	    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout());
	}
	
		JLabel caption = new JLabel("Pandemic Simulator");
		JButton start = new JButton("Start");
		
	
	public static void main(String[] args) {
		MainGui a = new MainGui();
		a.setVisible(true);
	}
}

