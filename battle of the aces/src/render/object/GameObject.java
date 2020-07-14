package render.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class GameObject extends JComponent{
	private Graphics2D g2;
	private BufferedImage sprite = null;
	protected int width, height, x, y;
	
	public GameObject(Graphics2D g2, BufferedImage sprite) {
		this.g2 = g2;
		this.sprite = sprite;
	}
	
	public void draw() {
		g2.drawImage(sprite, x, y, width, height, this);
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
}
