package render.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class GameEntity extends JComponent{
	protected Graphics2D g2;
	protected BufferedImage sprite = null;
	protected AffineTransform aft;
	protected int width, height;
	protected double direction, velx, vely, x, y, health = 76;
	protected double speed;
	protected String name;
	
	public GameEntity(Graphics2D g2, BufferedImage sprite) {
		this.g2 = g2;
		this.sprite = sprite;
	}
	
	public void draw() {
		aft = AffineTransform.getTranslateInstance(x,y);
		aft.rotate(Math.toRadians(direction),sprite.getWidth()/2,sprite.getHeight()/2);
		g2.drawImage(sprite, aft, this);
	}
	
	public void tick() {
		velx = ((Math.cos(((direction/180)* Math.PI))*speed));
	    vely = ((Math.sin(((direction/180)* Math.PI))*speed));
	    x += velx;
	    y += vely;
	}
	
	public double target(double tx, double ty, double tw, double th) {
		double ax=0, ay=0, n; 
		ax = (this.x+this.width - (tx+tw));
		ay = (this.y+this.height - (ty+th));
	    
	    double angle = 0;
	    angle = Math.atan2(-ay,-ax);
	    double angle2 = Math.toDegrees(angle);
	    
	    n = angle2;
	    
	    return n;
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

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getVelx() {
		return velx;
	}

	public void setVelx(double velx) {
		this.velx = velx;
	}

	public double getVely() {
		return vely;
	}

	public void setVely(double vely) {
		this.vely = vely;
	}

	public double getPX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getPY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getSpeed() {
		return speed;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rectangle bounds() {
		Rectangle rect = new Rectangle();
		rect.setBounds((int)x-width/2, (int)y-height/2, (int)width, (int)height);
		return rect;
	}
	
	public Rectangle barrier_bounds() {
		Rectangle rect = new Rectangle();
		int pix_add_value = 200;
		rect.setBounds((int)x-width/2-pix_add_value, (int)y-height/2-pix_add_value, (int)width+pix_add_value, (int)height+pix_add_value);
		return rect;
	}
	public Rectangle render_bounds() {
		Rectangle rect = new Rectangle();
		rect.setBounds((int)x-300, (int)y-200, 900, 700);
		return rect;
	}
	
}
