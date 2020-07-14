package render.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class GameEntity extends JComponent{
	private Graphics2D g2;
	private BufferedImage sprite = null;
	protected int x, y, velx, vely, width, height;
	
	
	public GameEntity(Graphics2D g2, BufferedImage sprite) {
		this.g2 = g2;
		this.sprite = sprite;
	}
	
	public void draw() {
		g2.drawImage(sprite, x, y, width, height, this);
	}
	
	public void tick() {
		x += velx;
		y += vely;
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
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
	
	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}

	public int getVely() {
		return vely;
	}

	public void setVely(int vely) {
		this.vely = vely;
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
}
