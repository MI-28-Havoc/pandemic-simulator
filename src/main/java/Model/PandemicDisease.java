package Model;

import lombok.Data;
import static Controller.SimulatorConfig.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@Data
public class PandemicDisease extends JPanel{
    //Zum Daten sammeln....?
	
	public void initialPaint(Graphics oldG) {
	    Graphics2D g = (Graphics2D)oldG;
	    
	    //Achsenkreuz
	    g.setPaint(new Color(0,0,0));
	    //g.drawLine(x+10,y+10,500,y+10); //X
	    //g.drawLine(x+10,y+10,x+10,0); //Y
	    g.setFont(new Font("Arial", Font.BOLD,20));  //Attribut, Gr��e in [pt]
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.75f));
	    //Formel
	    //g.drawString("f(x) = "+model.getA()+"*x^3 + "+model.getB()+"*x^2 + "+model.getC()+"*x + "+model.getD(),10,30);
	    g.drawLine(50, 950, 550, 950);	//x-Achse
	    g.drawLine(50, 950, 50, 600);	//y-Achse
	    
	    
	    // Werte
	    //g.setPaint(new Color(255,0,0));
	    
	   // g.drawString("HalloWelt",100,100);
	   // g.drawLine(50,50+50*(int)(model.getA()),150,150);
	   // g.draw
	    /*int lastX = 0, lastY = 0;
	    //Funktion plotten
	    for(int i=-1000; i<=1000; i++) {
	    	x = i / 100.0;
	    	y = model.f(x);
	    	//g.fillOval((int)(150+x*50), (int)(150-y*50), 5, 5);
	    	if (i!=-1000)
	    		g.drawLine(lastX, lastY, (int)(150+x*pixelPerValue), (int)(150-y*pixelPerValue));
	    	lastX = (int)(150+x*pixelPerValue);
	    	lastY = (int)(150-y*pixelPerValue);
	    }*/
	  }
}
