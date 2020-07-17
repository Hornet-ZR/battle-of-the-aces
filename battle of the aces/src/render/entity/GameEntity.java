package render.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class GameEntity extends JComponent{
	private Graphics2D g2;
	private BufferedImage sprite = null;
	protected int width, height, x, y;
	protected float direction, velx, vely;
	protected double speed;
	
	public GameEntity(Graphics2D g2, BufferedImage sprite) {
		this.g2 = g2;
		this.sprite = sprite;
	}
	
	public void draw() {
		AffineTransform aft = AffineTransform.getTranslateInstance(x,y);
		aft.rotate(Math.toRadians(direction),sprite.getWidth()/2,sprite.getHeight()/2);
		g2.drawImage(sprite, aft, this);
	}
	
	public void tick() {
		velx = (float) (Math.cos(((direction/180)* Math.PI))*speed);
	    vely =  (float) (Math.sin(((direction/180)* Math.PI))*speed);
	    x += Math.round(velx);
	    y += Math.round(vely);
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getDirection() {
		return direction;
	}

	public void setDirection(float direction) {
		this.direction = direction;
	}

	public float getVelx() {
		return velx;
	}

	public void setVelx(float velx) {
		this.velx = velx;
	}

	public float getVely() {
		return vely;
	}

	public void setVely(float vely) {
		this.vely = vely;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
}
