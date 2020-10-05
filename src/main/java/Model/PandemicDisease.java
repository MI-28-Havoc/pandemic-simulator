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
	
	//Darstellungsmaßstab 
	private int pixelPerValue;
	private int rolftest;
	
	public int getPixelPerValue() {
		return pixelPerValue;
	}

	public void setPixelPerValue(int pixelPerValue) {
		this.pixelPerValue = pixelPerValue;
	}

	public PandemicDisease(int pixelPerValue) {
		//this.model = model;
		this.pixelPerValue = pixelPerValue;
	}

	@Override
	public void paintComponent(Graphics oldG) {
		double x, y;
	    Graphics2D g = (Graphics2D)oldG;
	    
	    
	    //Achsenkreuz
	    g.setPaint(Color.BLACK);
	    g.drawLine(70,930,500,930); //X
	    g.drawLine(70,930,70,500); //Y
	    //g.setFont(new Font("Arial", Font.BOLD,20));  //Attribut, Größe in [pt]
	    //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.75f));
	    for (int i=1; i<25; i++) {
	    	g.drawLine(i*pixelPerValue+70, 920, i*pixelPerValue+70,940 ); //X div
	    	g.drawLine(60,930 - i*pixelPerValue,80,930-i*pixelPerValue); //Y div	
	    }
	    
	    
	    //Formel
	    //g.drawString("f(x) = "+model.getA()+"*x^3 + "+model.getB()+"*x^2 + "+model.getC()+"*x + "+model.getD(),10,30);
	    
	    
	    
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
