package render.entity;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Enemy extends GameEntity{

	public Enemy(Graphics2D g2, BufferedImage sprite) {
		super(g2, sprite);
	}
	
	public void target(double tx, double ty, double tw, double th) {
       double ax = (x - tx-tw/2);
       double ay = (y - ty-th/2);
       
       double angle = 0;
       if (x > ax-100) angle = Math.atan2(-ay,-ax-100);
       else if (x < ax+100) angle = Math.atan2(-ay, -ax+100);
       double angle2 = Math.toDegrees(angle);
       
       direction = angle2;
	}
}
