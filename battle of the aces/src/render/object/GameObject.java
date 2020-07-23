package render.object;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class GameObject extends JComponent{
	private Graphics2D g2;
	private BufferedImage sprite = null;
	protected double width, height, x, y, angle, velx, vely;
	protected AffineTransform aft;
	
	public GameObject(Graphics2D g2, BufferedImage sprite) {
		this.g2 = g2;
		this.sprite = sprite;
	}
	
	public void draw() {
		aft = AffineTransform.getTranslateInstance(x,y);
		aft.rotate(Math.toRadians(angle),sprite.getWidth()/2,sprite.getHeight()/2);
		g2.drawImage(sprite, aft, this);
	}
	
	public void tick() {
		
	}
	
	public void directional_tick(double angle, double speed, double sx, double sy) {
		velx = ((Math.cos(((angle/180)* Math.PI))*speed));
	    vely = ((Math.sin(((angle/180)* Math.PI))*speed));
	    x += sx + velx;
	    y += sy + vely;
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public double getOWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getOHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getOX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getOY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
