package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.main;
import render.events.KeyEvents;
import render.imageUtil.SpriteLoader;

public class Renderer extends JPanel{
	private Graphics2D g2;
	
	private boolean showMainScreen = true;
	private boolean choosingPlayer = false;
	private boolean choosingEnemy = false;
	private boolean showingMenu = true;
	private int keyZpress = 0;
	private int plane_preview_x = 100;
	private int plane_preview_y = 100;
	//Classes
	private KeyEvents keyEvent = new KeyEvents();
	private SpriteLoader spriteLoader = new SpriteLoader();
	
	//Images / files
	private File playerSpritesF;
	private BufferedImage playerSprites;
	private File guiSpritesF;
	private BufferedImage guiSprites;
	private double angle = 0;
	
	public void init(main m) {
		m.addKeyListener(keyEvent);
		//Set variables
		try {
			playerSpritesF = new File(this.getClass().getResource("res/playerSprites.png").toURI());
			playerSprites = ImageIO.read(playerSpritesF);
			
			guiSpritesF = new File(this.getClass().getResource("res/guiSprites.png").toURI());
			guiSprites = ImageIO.read(guiSpritesF);
		}catch (Exception e) {
			
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.g2 = g2;
		if (showMainScreen == true) {
			renderMainScreen();
			if (keyEvent.keyZ == true) {
				keyZpress++;
				try {
					Thread.sleep(100);
				}catch(Exception e) {
					
				}
			}
			if (keyZpress == 1) {
				showingMenu = false;
				choosingPlayer = true;
			}
			if (keyZpress == 2) {
				choosingPlayer = false;
				choosingEnemy = true;
			}
			if (keyZpress == 3) {
				choosingEnemy = false;
			}
			
		}
		
	}
	
	public void renderMainScreen() {
		g2.setColor(Color.WHITE);
		g2.drawOval(0, 500, 100, 100);
		g2.drawOval(300, 500, 100, 100);
		if (showingMenu == true) {
			g2.setColor(Color.RED);
			g2.setFont(new Font("Arial",Font.BOLD,30));
			g2.drawString("DOGFIGHTER 8.0: Battle of the aces", 190, 50);
			g2.drawString("Press Z to choose player", 500, 400);
			
			
			if (guiSprites == null) {
				return;
			}
			
			BufferedImage plane_image = spriteLoader.loadGUISprite(guiSprites, 1, 1);
			AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
			pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
			g2.drawImage(plane_image, pos, this);
		}else if (showingMenu == false && choosingPlayer == true) {
			g2.setColor(Color.RED);
			g2.setFont(new Font("Arial",Font.BOLD,30));
			g2.drawString("Choose your player", 190, 50);
			g2.drawString("Press Z to choose enemy", 500, 400);
			
			
			if (playerSprites == null) {
				return;
			}
			
			int chosenPlayerIndex = 1;
			BufferedImage plane_image = spriteLoader.loadPlayerSprite(playerSprites, 1, 1);
			AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
			pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
			g2.drawImage(plane_image, pos, this);
		}else if (showingMenu == false && choosingPlayer == false && choosingEnemy == true) {
			g2.setColor(Color.RED);
			g2.setFont(new Font("Arial",Font.BOLD,30));
			g2.drawString("Choose your enemy", 190, 50);
			g2.drawString("Press Z to start", 500, 400);
			
			
			if (guiSprites == null) {
				return;
			}
			
			BufferedImage plane_image = spriteLoader.loadGUISprite(guiSprites, 1, 1);
			AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
			pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
			g2.drawImage(plane_image, pos, this);
		}
		
	}
	
	public void renderPlayer() {
		
	}
	
	public void renderEnemy() {
		
	}
	
	public void renderMap() {
		
	}
}
