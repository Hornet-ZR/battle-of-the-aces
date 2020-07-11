package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Renderer extends JPanel{
	private Graphics2D g2;
	
	private boolean chosenPlayer = false;
	private boolean chosenEnemy = false;
	private boolean showingMenu = true;
	
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
		
		if (showingMenu == false) {
			if (chosenPlayer == false) {
				
			}
		}else if (showingMenu == true){
			renderMainScreen();
		}
		
	}
	
	public void renderMainScreen() {
		g2.setColor(Color.RED);
		g2.setFont(new Font("Arial",Font.BOLD,30));
		g2.drawString("DOGFIGHTER 8.0: Battle of the aces", 190, 50);
		g2.drawString("Press Z", 500, 400);
		
		// Rotating plane 
	}
	
	public void renderPlayer() {
		
	}
	
	public void renderEnemy() {
		
	}
	
	public void renderMap() {
		
	}
}
