package render.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Enemy extends GameEntity{

	public Enemy(Graphics2D g2, BufferedImage sprite) {
		super(g2, sprite);
	}
	
	public void target(double tx, double ty) {
       double ax = tx - x;
       double ay = ty - y;
       
       double angle = Math.atan2(ax,ay);
       double angle2 = Math.toDegrees(angle);
   
       direction = angle2;
       System.out.println(angle2+" "+x+" "+y);
	}
}
