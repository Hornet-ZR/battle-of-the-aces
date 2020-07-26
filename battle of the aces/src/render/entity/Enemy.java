package render.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameEntity{
	public boolean shooting = false;
	public Enemy(Graphics2D g2, BufferedImage sprite) {
		super(g2, sprite);
	}
	
	public void shoot() {
		Random r = new Random();
		int shoot = r.nextInt(1000000000);
		if (shoot > 999000000) {
			shooting = true;
		}
	}
}
