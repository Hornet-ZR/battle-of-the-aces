package render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Renderer extends JPanel{
	private Graphics2D g2;
	
	
	//Player images / files
	private File playerSpritesF;
	private BufferedImage playerSprites;
	
	public void init() {
		//Set variables
		try {
			playerSpritesF = new File(this.getClass().getResource("res/playerSprites").toURI());
			playerSprites = ImageIO.read(playerSpritesF);
		}catch (Exception e) {
			
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.g2 = g2;
		
		
	}
	
	public void renderPlayer() {
		
	}
	
	public void renderEnemy() {
		
	}
	
	public void renderMap() {
		
	}
}
