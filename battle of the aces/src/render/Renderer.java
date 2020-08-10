package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.main;
import render.entity.Enemy;
import render.entity.Player;
import render.events.KeyEvents;
import render.events.MouseEvents;
import render.imageUtil.ResLoader;
import render.imageUtil.SpriteLoader;
import render.object.Bullets;
import render.object.Cloud;

public class Renderer extends JPanel{
	private static final long serialVersionUID = 1L;

	private Graphics2D g2;
	private Graphics g;
	
	//Main vars
	private main m;
	private String[] arrayData = {};
	
	//General menu stuff
	public boolean isMultiplayer = false;
	public String ip = "";
	public String username = "";
	public int keyXpress = 0;
	public int keyZpress = 0;
	private int plane_preview_x = 100;
	private int plane_preview_y = 100;
	
	//Multiplayer stuff
	private String data = "";
	
	//Player stuff
	private int player_width = 250;
	private int player_height = 250;
	private int player_start_x = 550;
	private int player_start_y = 450;
	private int intro_runway_x = 950;
	private double intro_plane_width = 50;
	private double intro_plane_height = 50;
	private double intro_plane_x = 450;
	private double intro_plane_y = 250;
	private double angle = 0;
	public int playerSpriteChosenX = 1;
	public int playerSpriteChosenY = 1;
	public int playerPlaneIndex = 1;
	
	//Enemy Stuff
	private int enemy_width = 250;
	private int enemy_height = 250;
	private int enemy_start_x = 450;
	private int enemy_start_y = 350;
	public int enemySpriteChosenX = 1;
	public int enemySpriteChosenY = 1;
	public int enemyPlaneIndex = 1;
	
	//Classes
	private KeyEvents keyEvent = new KeyEvents();
	private MouseEvents mouseEvent = new MouseEvents();
	private SpriteLoader spriteLoader = new SpriteLoader();
	private ResLoader loader = new ResLoader();
	//Entities
	public Player player;
	public Enemy enemy;
	//Objects
	public Cloud cloud;
	public Cloud smoke;
	public Bullets bullet;
	//Images / files
	private BufferedImage playerSprites;
	private BufferedImage enemySprites;
	private BufferedImage guiSprites;
	private BufferedImage objectSprites;
	private BufferedImage intro_runway;
	private BufferedImage arrow_to_enemy;
	private Image explosion;
	//Game settings
	public boolean gameStarted = false;
	public boolean introStart = false;
	public boolean choosingPlayer = false;
	public boolean choosingEnemy = false;
	public boolean choosingServer = false;
	public boolean choosingServerIP = false;
	public boolean choosingServerPort = false;
	public boolean connectingToServer = false;
	public boolean showingMenu = true;
	public boolean showingControls = false;
	public boolean clearingSmokeThread = false;
	public ArrayList<Cloud> smokel = new ArrayList<Cloud>();
	public ArrayList<Cloud> enemy_smokel = new ArrayList<Cloud>();
	private int enemySmokeLevel = 0;
	private int playerSmokeLevel = 0;
	private boolean showMainScreen = true;
	private boolean introDone = false;
	private boolean startedIntroThread1 = false;
	private boolean startedIntroThread2 = false;
	private boolean playerWon = false;
	private boolean enemyWon = false;
	private boolean explosionStarted = false;
	private BufferedImage playerSSprite = null;
	private BufferedImage enemySSprite = null;
	private BufferedImage enemySSSprite = null;
	private BufferedImage cloudSprite = null;
	private BufferedImage smokeSprite = null;
	private BufferedImage bulletSprite = null;
	private ArrayList<Cloud> clouds = new ArrayList<Cloud>();
	private ArrayList<Cloud> new_clouds = new ArrayList<Cloud>();
	private ArrayList<Cloud> out_clouds = new ArrayList<Cloud>();
	private ArrayList<Cloud> new_smokel = new ArrayList<Cloud>();
	private ArrayList<Cloud> new_enemy_smokel = new ArrayList<Cloud>();
	private ArrayList<Bullets> bullets = new ArrayList<Bullets>();
	private ArrayList<Bullets> next_bullets = new ArrayList<Bullets>();
	private ArrayList<Bullets> enemy_bullets = new ArrayList<Bullets>();
	private ArrayList<Bullets> enemy_next_bullets = new ArrayList<Bullets>();
	
	public void init(main m) {
		this.m = m;
		m.addKeyListener(keyEvent);
		mouseEvent.init(this,m);
		keyEvent.init(this, m);
		m.addMouseListener(mouseEvent);

		try {
			playerSprites = ImageIO.read(loader.load("res/playerSprites.png"));
			enemySprites = ImageIO.read(loader.load("res/enemySprites.png"));
			guiSprites = ImageIO.read(loader.load("res/guiSprites.png"));
			intro_runway = ImageIO.read(loader.load("res/runway.png"));
			objectSprites = ImageIO.read(loader.load("res/objectSprites.png"));
			explosion = Toolkit.getDefaultToolkit().getImage("src/res/explosion.gif");
		}catch (Exception e) {
			System.out.println("Resource loading error");
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.g2 = g2;
		this.g = g;
		
		if (showMainScreen) {
			if (showingMenu) {
				choosingServer = false;
				choosingServerIP = false;
				choosingServerPort = false;
				choosingPlayer = false;
				choosingEnemy = false;
			}
			
			g2.translate((m.w.getWidth()-m.width)/2, (m.w.getHeight()-m.height)/2);
			
			renderMainScreen();
			
			g2.translate(-(m.w.getWidth()-m.width)/2, -(m.w.getHeight()-m.height)/2);
		}
		
		if (gameStarted) {
			introStart = false;
			showMainScreen = false;
			showingMenu = false;
			
			gameInit();

			renderBullets();
			
			renderEnemy();
			renderPlayer();
			
			if (enemy != null && !isMultiplayer) {
				if (player.barrier_bounds().intersects(enemy.barrier_bounds())) {
					enemy.setSpeed(0.2);
				}else if (!player.barrier_bounds().intersects(enemy.barrier_bounds())) {
					enemy.setSpeed(0.5);
				}
			}
			
			if (player.getHealth() == 75) 
				playerSmokeLevel = 1;
			else if (player.getHealth() == 50) 
				playerSmokeLevel = 2;
			else if (player.getHealth() == 25) 
				playerSmokeLevel = 3;

			if (enemy.getHealth() == 75) 
				enemySmokeLevel = 1;
			else if (enemy.getHealth() == 50) 
				enemySmokeLevel = 2;
			else if (enemy.getHealth() == 25) 
				enemySmokeLevel = 3;
			
			if (!isMultiplayer) {
				if (keyEvent.keySpace) {
					introStart = false;
					introDone = false;
					showingMenu = false;
					showMainScreen = true;
					gameStarted = false;
					explosionStarted = false;
				}
				
				if (enemy.getHealth() <= 0 && !(enemyWon || playerWon)) {
					g2.drawImage(explosion, (int)enemy.getPX()-enemy.getWidth()/2, (int)enemy.getPY()-enemy.getHeight()/2, 400, 400, this);
					new Thread(()->{
						explosionStarted = true;
						
						enemy.setVelx(0);
						enemy.setVely(0);
						
						try {
							Thread.sleep(1500);
						}catch(Exception e) {
							
						}
						
						playerWon = true;
					}).start();
				}
				
				if (player.getHealth() <= 0 && !(enemyWon || playerWon)) {
					g2.drawImage(explosion, (int)player.getPX()-player.getWidth()/2, (int)player.getPY()-player.getHeight()/2, 400, 400, this);			
					new Thread(()->{
						explosionStarted = true;
						
						player.setVelx(0);
						player.setVely(0);
						
						try {
							Thread.sleep(1500);
						}catch(Exception e) {
							
						}
						
						enemyWon = true;
					}).start();
				}
			}else {
				if (keyEvent.keyEscape) {
					introStart = false;
					introDone = false;
					showingMenu = false;
					showMainScreen = true;
					gameStarted = false;
					explosionStarted = false;
					
					try {
						m.server.clients.remove(m.client.socket);
						m.client.socket.close();
					}catch (Exception e) {
						
					}
				}
			}
			
			double radius = 0, angle = 0;
			radius = 250;
			angle = -player.target(enemy.getPX(), enemy.getPY(), enemy.getWidth(), enemy.getHeight());
			
			double x = 0, y = 0;
			x = ((player.getPX()) + radius*Math.cos(angle * Math.PI / 180));
			y = ((player.getPY()) + radius*Math.sin(-angle * Math.PI / 180));
			
			AffineTransform arrow_pos = AffineTransform.getTranslateInstance(x, y);
			arrow_pos.rotate(-Math.toRadians(angle), arrow_to_enemy.getWidth()/2, arrow_to_enemy.getHeight()/2);
			g2.drawImage(arrow_to_enemy, arrow_pos, this);
		
		}
		
		if ((playerWon || enemyWon) && isMultiplayer) {
			introStart = false;
			introDone = false;
			showingMenu = false;
			showMainScreen = true;
			gameStarted = false;
			explosionStarted = false;
			try {
				m.client.socket.close();
				m.client = null;
				m.server.server.close();
				m.server = null;
				m.reload_server();
			}catch (Exception e) {
				
			}
		}else if (playerWon || enemyWon) {
			introStart = false;
			introDone = false;
			showingMenu = false;
			showMainScreen = true;
			gameStarted = false;
			explosionStarted = false;
		}
		
		if (!gameStarted && introStart == false) {
			if (keyEvent.keyZ == true && keyXpress == 0) {
				keyEvent.keyZ = false;
				isMultiplayer = false;
				keyZpress++;
			}else if (keyEvent.keyX == true && keyZpress == 0 && keyXpress != 2) {
				keyEvent.keyX = false;
				isMultiplayer = true;
				keyXpress++;
			}
			
			if ((keyZpress + keyXpress) == 0 && !showingControls) {
				choosingPlayer = false;
				choosingEnemy = false;
				showingMenu = true;
			}
			
			switch (keyZpress) {
			case 1:
				showingMenu = false;
				choosingPlayer = true;
				break;
				
			case 2:
				choosingPlayer = false;
				choosingEnemy = true;
				break;
				
			case 3:
				choosingEnemy = false;
				introStart = true;
				break;
				
			case 4:
				enemyWon = false;
				playerWon = false;
				playerSmokeLevel = 0;
				enemySmokeLevel = 0;
				intro_plane_width = 50;
				intro_plane_height = 50;
				intro_plane_x = 450;
				intro_plane_y = 250;
				intro_runway_x = 950;
				startedIntroThread1 = false;
				startedIntroThread2 = false;
				player = null;
				enemy = null;
				showingMenu = true;
				keyZpress = 0;
				explosion.flush();
				break;
			}
			
			switch (keyXpress) {
			case 1:
				showingMenu = false;
				choosingPlayer = true;
				break;
				
			case 2:
				choosingPlayer = false;
				choosingServer = true;
				break;
				
			case 3:
				choosingServer = false;
				connectingToServer = true;
				introStart = true;
				keyXpress++;
				break;
				
			case 5:
				enemyWon = false;
				playerWon = false;
				playerSmokeLevel = 0;
				enemySmokeLevel = 0;
				intro_plane_width = 50;
				intro_plane_height = 50;
				intro_plane_x = 450;
				intro_plane_y = 250;
				intro_runway_x = 950;
				startedIntroThread1 = false;
				startedIntroThread2 = false;
				player = null;
				enemy = null;
				showingMenu = true;
				keyXpress = 1;
				explosion.flush();
				break;
			}
		}
		if (introDone == true && gameStarted == false) {
			playerSSprite = spriteLoader.loadPlayerSprite(playerSprites, playerSpriteChosenX, playerSpriteChosenY);
			enemySSprite = spriteLoader.loadEnemySprite(enemySprites, enemySpriteChosenX, enemySpriteChosenY);
			cloudSprite = spriteLoader.loadObjectSprite(objectSprites, 1, 1);
			bulletSprite = spriteLoader.loadObjectSprite(objectSprites, 2, 1);
			arrow_to_enemy = spriteLoader.loadGUISprite(guiSprites, 2, 1);
			smokeSprite = spriteLoader.loadObjectSprite(objectSprites, 3, 1);
			introDone = false;
			introStart = false;
			gameStarted = true;
		}
	}
	
	public void renderUI(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("HEALTH: "+(int)player.getHealth(), 50, 50);
		if (!isMultiplayer && enemy != null)
			g.drawString("ENEMY'S HEALTH: "+(int)enemy.getHealth(), m.w.getWidth()-g.getFontMetrics().stringWidth(String.valueOf("ENEMY'S HEALTH: "+(int)enemy.getHealth()))-50, 50);
	}
	
	public void renderMainScreen() {
		if (isMultiplayer == false) {
			if (playerWon) {
				introStart = false;
				BufferedImage plane_image = spriteLoader.loadPlayerSprite(playerSprites, playerSpriteChosenX, playerSpriteChosenY);
				AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
				pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
				g2.drawImage(plane_image, pos, this);
				
				g2.setColor(Color.BLACK);
				g2.setFont(new Font("Arial",Font.BOLD,48));
				g2.drawString("You won!", 100, 500);
				g2.drawString("Press \"Z\" to go to main menu.", 150, 50);
				g2.drawString("Your remaining health: "+(int)player.getHealth(), 100, 550);
				g2.drawString("Enemy's remaining health: "+(int)enemy.getHealth(), 100, 600);
			}else if (enemyWon) {
				introStart = false;
				BufferedImage plane_image = spriteLoader.loadPlayerSprite(playerSprites, playerSpriteChosenX, playerSpriteChosenY);
				AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
				pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
				g2.drawImage(plane_image, pos, this);
				
				g2.setColor(Color.BLACK);
				g2.setFont(new Font("Arial",Font.BOLD,48));
				g2.drawString("Enemy won", 100, 500);
				g2.drawString("Press \"Z\" to go to main menu.", 150, 50);
				g2.drawString("Your remaining health: "+(int)player.getHealth(), 100, 550);
				g2.drawString("Enemy's remaining health: "+(int)enemy.getHealth(), 100, 600);
			}
			
			if (choosingPlayer) {
				g2.setColor(Color.RED);
				g2.setFont(new Font("Arial",Font.BOLD,30));
				g2.drawString("Choose your player", 190, 50);
				g2.drawString("Press Z to choose enemy", 500, 400);
				
				
				if (playerSprites == null) 
					return;
				
				if (playerSpriteChosenX > 10) { 
					playerSpriteChosenX = 1;
					playerSpriteChosenY++;
				}
				
				if (mouseEvent.back == true && playerSpriteChosenY > 1 && playerSpriteChosenX == 1) {
					mouseEvent.back = false;
					playerSpriteChosenY--;
					playerSpriteChosenX = 10;
				}
				
				playerPlaneIndex = 10*(playerSpriteChosenY-1)+playerSpriteChosenX;
				
				BufferedImage plane_image = spriteLoader.loadPlayerSprite(playerSprites, playerSpriteChosenX, playerSpriteChosenY);
				AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
				pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
				g2.drawImage(plane_image, pos, this);
				
				g2.setColor(Color.WHITE);
				g2.drawOval(0, 500, 100, 100);
				g2.drawOval(300, 500, 100, 100);
				g2.setFont(new Font("Arial",Font.BOLD,22));
				g2.drawString("Previous",5, 560);
				g2.drawString("Next",325, 560);
				g2.drawString("Plane "+playerPlaneIndex, 165, 560);
				
			}else if (choosingEnemy) {
				g2.setColor(Color.RED);
				g2.setFont(new Font("Arial",Font.BOLD,30));
				g2.drawString("Choose your enemy", 190, 50);
				g2.drawString("Press Z to start", 500, 400);
				
				
				if (enemySprites == null)
					return;
				
				
				if (enemySpriteChosenX > 10) { 
					enemySpriteChosenX = 1;
					enemySpriteChosenY++;
				}
				
				if (mouseEvent.back == true && enemySpriteChosenY > 1 && enemySpriteChosenX == 1) {
					mouseEvent.back = false;
					enemySpriteChosenY--;
					enemySpriteChosenX = 10;
				}
				
				enemyPlaneIndex = 10*(enemySpriteChosenY-1)+enemySpriteChosenX;
				
				BufferedImage plane_image = spriteLoader.loadEnemySprite(enemySprites, enemySpriteChosenX, enemySpriteChosenY);
				AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
				pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
				g2.drawImage(plane_image, pos, this);
				
				g2.setColor(Color.WHITE);
				g2.drawOval(0, 500, 100, 100);
				g2.drawOval(300, 500, 100, 100);
				g2.setFont(new Font("Arial",Font.BOLD,22));
				g2.drawString("Previous",5, 560);
				g2.drawString("Next",325, 560);
				g2.drawString("Plane "+enemyPlaneIndex, 165, 560);
			}
		}else if (isMultiplayer) {
			if (playerWon) {
				introStart = false;
				BufferedImage plane_image = spriteLoader.loadPlayerSprite(playerSprites, playerSpriteChosenX, playerSpriteChosenY);
				AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
				pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
				g2.drawImage(plane_image, pos, this);
				
				g2.setColor(Color.WHITE);
				g2.setFont(new Font("Arial",Font.BOLD,48));
				g2.drawString("You won!", 100, 500);
				g2.drawString("Press \"X\" to choose player.", 150, 50);
				g2.drawString("Your remaining health: "+(int)player.getHealth(), 10, 550);
				g2.drawString(enemy.getName()+"'s remaining health: "+(int)enemy.getHealth(), 10, 600);
			}else if (enemyWon) {
				introStart = false;
				BufferedImage plane_image = spriteLoader.loadPlayerSprite(playerSprites, playerSpriteChosenX, playerSpriteChosenY);
				AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
				pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
				g2.drawImage(plane_image, pos, this);
				
				g2.setColor(Color.WHITE);
				g2.setFont(new Font("Arial",Font.BOLD,48));
				g2.drawString(enemy.getName()+" won", 100, 500);
				g2.drawString("Press \"X\" to choose player.", 150, 50);
				g2.drawString("Your remaining health: "+(int)player.getHealth(), 10, 550);
				g2.drawString(enemy.getName()+"'s remaining health: "+(int)enemy.getHealth(), 10, 600);
			}
			
			if (choosingPlayer) {
				g2.setColor(Color.RED);
				g2.setFont(new Font("Arial",Font.BOLD,30));
				g2.drawString("Choose your player", 190, 50);
				g2.drawString("Press X to choose server", 500, 400);
				
				
				if (playerSprites == null) 
					return;
				
				if (playerSpriteChosenX > 10) { 
					playerSpriteChosenX = 1;
					playerSpriteChosenY++;
				}
				
				if (mouseEvent.back == true && playerSpriteChosenY > 1 && playerSpriteChosenX == 1) {
					mouseEvent.back = false;
					playerSpriteChosenY--;
					playerSpriteChosenX = 10;
				}
				
				playerPlaneIndex = 10*(playerSpriteChosenY-1)+playerSpriteChosenX;
				
				BufferedImage plane_image = spriteLoader.loadPlayerSprite(playerSprites, playerSpriteChosenX, playerSpriteChosenY);
				AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
				pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
				g2.drawImage(plane_image, pos, this);
				
				g2.setColor(Color.WHITE);
				g2.drawOval(0, 500, 100, 100);
				g2.drawOval(300, 500, 100, 100);
				g2.setFont(new Font("Arial",Font.BOLD,22));
				g2.drawString("Previous",5, 560);
				g2.drawString("Next",325, 560);
				g2.drawString("Plane "+playerPlaneIndex, 165, 560);
				
			}else if (choosingServer) {
				g2.setColor(Color.WHITE);
				g2.setFont(new Font("Arial",Font.BOLD,22));
				g2.drawString("Server IP: "+ip, 100, 100);
				g2.drawString("Username: "+username, 100, 200);
				
				if (choosingServerIP) g2.drawRect(200, 65, 1000, 50);
				if (choosingServerPort) g2.drawRect(210, 165, 1000, 50);
				
				if (!username.equals("")) { 
					g2.drawRect(455, 465, 275, 50);
					g2.drawString("Connect to server", 500, 500);
				}
				
			}
			
		}
		
		if (showingMenu) {
			g2.setColor(Color.RED);
			g2.setFont(new Font("Arial",Font.PLAIN,30));
			g2.drawString("DOGFIGHTER 8.0: Battle of the aces", 190, 50);
			g2.drawString("Press Z to choose player", 500, 400);
			g2.drawString("Press X to choose player (Multiplayer)", 320, 500);
			g2.drawString("Press C to toggle controls", 490, 600);
			
			if (guiSprites == null)
				return;
			
			BufferedImage plane_image = spriteLoader.loadGUISprite(guiSprites, 1, 1);
			AffineTransform pos = AffineTransform.getTranslateInstance(plane_preview_x, plane_preview_y);
			pos.rotate(Math.toRadians(angle+=0.1),plane_image.getWidth()/2,plane_image.getHeight()/2);
			g2.drawImage(plane_image, pos, this);
		}
		
		if (introStart == true && !gameStarted) {
			choosingPlayer = false;
			choosingEnemy = false;
			showingMenu = false;
			renderIntro();
		}
		
		if (!introStart && !showingMenu && !playerWon && !enemyWon) {
			g2.drawRect(450, 590, 150, 50);
			g2.drawString("Back", 495, 625);
		}
		
		if (showingControls) {
			showingMenu = false;
			g2.setFont(new Font("Arial",Font.PLAIN,30));
			g2.setColor(Color.red);
			g2.drawString("X : Single Menu", 10, 50);
			g2.drawString("Z : Multiplayer Menu", 10, 100);
			g2.drawString("X : Toggle control menu", 10, 150);
			g2.drawString("Left + Right arrows : Player movement (Roatation left and right)", 10, 200);
			g2.drawString("Space : Shoot bullets", 10, 250);
			g2.drawString("W : Create server", 10, 300);
			g2.drawString("Escape : Leave multiplayer / singleplayer game (reccomended exit ", 10, 350);
			g2.drawString("method) ((Work in progress))", 10, 380);
		}
	}
	
	public void gameInit() {
		if (enemy_bullets.size() > 10) {
			enemy_next_bullets.clear();
			enemy_bullets.clear();
		}else {
			createEnemyBullets();	
		}
		
		if (bullets.size() > 10) {
			next_bullets.clear();
			bullets.clear();
		}else {
			createBullets();
		}
		
		createPlayer();
		createEnemy();
	}
	
	public void createPlayer() {
		if (player == null) {
			player = new Player(g2,playerSSprite);
			player.setWidth(player_width);
			player.setHeight(player_height);
			player.setX(player_start_x-player.getWidth());
			player.setY(player_start_y-player.getHeight());
		}else {
			double oldPX, oldPY, oldDir, oldVelX, oldVelY, oldHealth;
			oldPX = player.getPX();
			oldPY = player.getPY();
			oldVelX = player.getVelx();
			oldVelY = player.getVely();
			oldDir = player.getDirection();
			oldHealth = player.getHealth();
			player = null;
			player = new Player(g2,playerSSprite);
			player.setWidth(player_width);
			player.setHeight(player_height);
			player.setX(oldPX);
			player.setY(oldPY);
			player.setVelx(oldVelX);
			player.setVely(oldVelY);
			player.setDirection(oldDir);
			player.setHealth(oldHealth);
			player.setSpeed(0.5);
		}
		
		if (isMultiplayer) {
			if (bullets.size() > 1) {
				for (Bullets b : bullets) {
					data = String.valueOf(","+player.getPX()+","+player.getPY()+","+player.getDirection()+","+player.getHealth()+","+username+","+playerSpriteChosenX+","+playerSpriteChosenY+","+playerWon+","+b.getOX()+","+b.getOY()+","+b.getAngle());
				}
			}else {
				data = String.valueOf(","+player.getPX()+","+player.getPY()+","+player.getDirection()+","+player.getHealth()+","+username+","+playerSpriteChosenX+","+playerSpriteChosenY+","+playerWon);
			}
			
			if (enemy != null && enemy.getHealth() <= 0) {
				playerWon = true;
				introStart = false;
				introDone = false;
				showMainScreen = true;
				gameStarted = false;
				data = String.valueOf(true);
			}
			
			m.client.sendMessage(data);
		}
	}
	
	public void createEnemy() {
		if (!isMultiplayer) {
			if (enemy == null) {
				enemy = new Enemy(g2,enemySSprite);
				enemy.setWidth(enemy_width);
				enemy.setHeight(enemy_height);
				enemy.setX(enemy_start_x-enemy.getWidth());
				enemy.setY(enemy_start_y-enemy.getHeight());
			}else {
				double oldPX, oldPY, oldDir, oldVelX, oldVelY, oldSpeed, oldHealth;
				oldPX = enemy.getPX();
				oldPY = enemy.getPY();
				oldVelX = enemy.getVelx();
				oldVelY = enemy.getVely();
				oldDir = enemy.getDirection();
				oldSpeed = enemy.getSpeed();
				oldHealth = enemy.getHealth();
				enemy = null;
				enemy = new Enemy(g2,enemySSprite);
				enemy.setWidth(enemy_width);
				enemy.setHeight(enemy_height);
				enemy.setX(oldPX);
				enemy.setY(oldPY);
				enemy.setVelx(oldVelX);
				enemy.setVely(oldVelY);
				enemy.setDirection(oldDir);
				enemy.setHealth(oldHealth);
				enemy.setSpeed(oldSpeed);
			}
		}else {
			double nPX, nPY, nDir, nHealth;
			String name;
			String data =  m.client.readMessage();
			if (data.contains(",")) {
				arrayData = data.split(",",-1);
				
				if (arrayData.length == 9) {
					nPX = Double.valueOf(arrayData[1]);
					nPY = Double.valueOf(arrayData[2]);
					nDir = Double.valueOf(arrayData[3]);
					nHealth = Double.valueOf(arrayData[4]);
					name = arrayData[5];
					
					if (enemySSSprite == null)
						enemySSSprite = spriteLoader.loadEnemySprite(enemySprites, Integer.valueOf(arrayData[6]), Integer.valueOf(arrayData[7]));
					
					enemy = new Enemy(g2,enemySSSprite);
					enemy.setWidth(enemy_width);
					enemy.setHeight(enemy_height);
					enemy.setX(nPX);
					enemy.setY(nPY);
					enemy.setDirection(nDir);
					enemy.setHealth(nHealth);
					enemy.setName(name);
				}
				
				if (arrayData.length > 9) {
					nPX = Double.valueOf(arrayData[1]);
					nPY = Double.valueOf(arrayData[2]);
					nDir = Double.valueOf(arrayData[3]);
					nHealth = Double.valueOf(arrayData[4]);
					name = arrayData[5];
					
					if (enemySSSprite == null)
						enemySSSprite = spriteLoader.loadEnemySprite(enemySprites, Integer.valueOf(arrayData[6]), Integer.valueOf(arrayData[7]));
					
					enemy = new Enemy(g2,enemySSSprite);
					enemy.setWidth(enemy_width);
					enemy.setHeight(enemy_height);
					enemy.setX(nPX);
					enemy.setY(nPY);
					enemy.setDirection(nDir);
					enemy.setHealth(nHealth);
					enemy.setName(name);
					
					bullet = new Bullets(g2,bulletSprite);
					bullet.setX(Double.valueOf(arrayData[9]));
					bullet.setY(Double.valueOf(arrayData[10]));
					bullet.setAngle(Double.valueOf(arrayData[11]));
					bullet.setSpeed(2);
					enemy_bullets.add(bullet);
				}
			}
			
			if (data.equals("true") || data.equals("false")) {
				enemyWon = Boolean.valueOf(data);
			}
		}
	}
	
	public void createClouds(Graphics g) {
		for (int i = clouds.size(); i < 15*Math.round(((m.w.getWidth()+m.w.getHeight())/(m.width+m.height))); i++) {
			cloud = new Cloud((Graphics2D) g,cloudSprite);
			Random r = new Random();
			cloud.setX(r.nextInt(m.w.getWidth()));
			cloud.setY(r.nextInt(m.w.getHeight()));
			cloud.setWidth(r.nextInt(50));
			cloud.setHeight(r.nextInt(50));
			clouds.add(cloud);
		}
		
		for (Cloud cl : clouds) {
			cloud = new Cloud((Graphics2D) g,cloudSprite);
			cloud.setX(cl.getOX());
			cloud.setY(cl.getOY());
			cloud.setWidth(cl.getOWidth());
			cloud.setHeight(cl.getOHeight());
			cloud.setVelx(cl.getVelx());
			cloud.setVely(cl.getVely());
			new_clouds.add(cloud);
		}
		
		clouds.clear();
		
		for (Cloud cln : new_clouds) {
			clouds.add(cln);
		}

		new_clouds.clear();
	}
	
	public void createPlayerSmoke(Graphics g) {
		for (int i = smokel.size(); i < playerSmokeLevel; i++) {
			smoke = new Cloud((Graphics2D)g, smokeSprite);
			smoke.setWidth(500);
			smoke.setHeight(500);
			
			if (player != null) {
				smoke.setX(player.getPX());
				smoke.setY(player.getPY());
				smoke.setVelx(-player.getVelx());
				smoke.setVely(-player.getVely());
			}
			
			smokel.add(smoke);
		}
		
		if (smokel.size() <= 0) {
			for (int i = smokel.size(); i < playerSmokeLevel; i++) {
				smoke = new Cloud((Graphics2D)g, smokeSprite);
				smoke.setWidth(500);
				smoke.setHeight(500);
				
				if (player != null) {
					smoke.setX(player.getPX());
					smoke.setY(player.getPY());
					smoke.setVelx(-player.getVelx());
					smoke.setVely(-player.getVely());
				}
				
				smokel.add(smoke);
			}
		}else {
			for (int i = 0; i < smokel.size(); i++) {
				Cloud s = smokel.get(i);
				smoke = new Cloud((Graphics2D)g, smokeSprite);
				smoke.setX(s.getOX());
				smoke.setY(s.getOY());
				smoke.setWidth(s.getOWidth());
				smoke.setHeight(s.getOHeight());
				smoke.setVelx(s.getVelx());
				smoke.setVely(s.getVely());
				new_smokel.add(smoke);
			}
			
			smokel.clear();
			
			for (Cloud s : new_smokel) {
				smokel.add(s);
			}
			
			new_smokel.clear();
		}
	}
	
	public void createEnemySmoke(Graphics g) {
		for (int i = enemy_smokel.size(); i < enemySmokeLevel; i++) {
			smoke = new Cloud((Graphics2D)g, smokeSprite);
			smoke.setWidth(500);
			smoke.setHeight(500);
			
			if (player != null) {
				smoke.setX(enemy.getPX());
				smoke.setY(enemy.getPY());
				smoke.setVelx(-enemy.getVelx());
				smoke.setVely(-enemy.getVely());
			}
			
			enemy_smokel.add(smoke);
		}
		
		if (enemy_smokel.size() <= 0) {
			for (int i = smokel.size(); i < enemySmokeLevel; i++) {
				smoke = new Cloud((Graphics2D)g, smokeSprite);
				smoke.setWidth(500);
				smoke.setHeight(500);
				
				if (enemy != null) {
					smoke.setX(enemy.getPX());
					smoke.setY(enemy.getPY());
					smoke.setVelx(-enemy.getVelx());
					smoke.setVely(-enemy.getVely());
				}
				
				enemy_smokel.add(smoke);
			}
		}else {
			for (int i = 0; i < enemy_smokel.size(); i++) {
				Cloud s = enemy_smokel.get(i);
				smoke = new Cloud((Graphics2D)g, smokeSprite);
				smoke.setX(s.getOX());
				smoke.setY(s.getOY());
				smoke.setWidth(s.getOWidth());
				smoke.setHeight(s.getOHeight());
				smoke.setVelx(s.getVelx());
				smoke.setVely(s.getVely());
				new_enemy_smokel.add(smoke);
			}
			
			enemy_smokel.clear();
			
			for (Cloud s : new_enemy_smokel) {
				enemy_smokel.add(s);
			}
			
			new_enemy_smokel.clear();
		}
	}
	
	public void createBullets() {
		if (bullets.size() > 0) {
			double oldX,oldY,oldWidth,oldHeight,oldAngle;
			for (Bullets b : bullets) {
				if (b.isDead() == false) {
					oldX = b.getOX();
					oldY = b.getOY();
					oldWidth = b.getOWidth();
					oldHeight = b.getOHeight();
					oldAngle = b.getAngle();
					bullet = new Bullets(g2,bulletSprite);
					bullet.setX(oldX);
					bullet.setY(oldY);
					bullet.setAngle(oldAngle);
					bullet.setWidth(oldWidth);
					bullet.setHeight(oldHeight);
					bullet.setSpeed(2);
					next_bullets.add(bullet);
				}
			}
			
			bullets.clear();
			
			for (Bullets b : next_bullets)
				bullets.add(b);
			
			next_bullets.clear();
		}
	}
	
	public void createEnemyBullets() {
		if (enemy_bullets.size() > 0) {
			double oldX,oldY,oldWidth,oldHeight,oldAngle;
			for (Bullets b : enemy_bullets) {
				if (b.isDead() == false) {
					oldX = b.getOX();
					oldY = b.getOY();
					oldWidth = b.getOWidth();
					oldHeight = b.getOHeight();
					oldAngle = b.getAngle();
					bullet = new Bullets(g2,bulletSprite);
					bullet.setX(oldX);
					bullet.setY(oldY);
					bullet.setAngle(oldAngle);
					bullet.setWidth(oldWidth);
					bullet.setHeight(oldHeight);
					bullet.setSpeed(2);
					enemy_next_bullets.add(bullet);
				}
			}
			
			enemy_bullets.clear();
			
			for (Bullets b : enemy_next_bullets)
				enemy_bullets.add(b);
			
			enemy_next_bullets.clear();
		}
	}
	
	public void renderIntro() {
		BufferedImage plane_image = spriteLoader.loadPlayerSprite(playerSprites, playerSpriteChosenX, playerSpriteChosenY);
		AffineTransform runway_transform = AffineTransform.getTranslateInstance(intro_runway_x, 225);
		
		runway_transform.rotate(Math.toRadians(90));
		g2.drawImage(intro_runway, runway_transform, this);
		g2.drawImage(plane_image, (int)Math.round(intro_plane_x), (int)Math.round(intro_plane_y), (int)Math.round(intro_plane_width), (int)Math.round(intro_plane_height), this);
		
		final Thread gainAltitude = new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (intro_plane_width < 214) {
						intro_plane_width++;
						intro_plane_x-=1;
						
						try {
							Thread.sleep(1);
						}catch(Exception e) {
							
						}
					}
					if (intro_plane_height < 282) {
						intro_plane_height+=1.4;
						intro_plane_y-=0.4;
						
						try {
							Thread.sleep(1);
						}catch(Exception e) {
							
						}
					}
					if (intro_plane_width >= 214 && intro_plane_height >= 282) {
						try {
							Thread.sleep(1000);
						}catch(Exception e) {
							
						}
						
						introDone = true;
						introStart = false;
						break;
					}
				}
			}
		});
		
		Thread increaseX = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1);
						intro_runway_x--;
						
						if (intro_runway_x <= -600 && startedIntroThread2 == false) {
							startedIntroThread2 = true;
							gainAltitude.start();
							break;
						}
					}catch(Exception e) {
						
					}
				}
			}
		});
		
		if (startedIntroThread1 == false) {
			startedIntroThread1 = true;
			increaseX.start();
		}
	}
	
	public void renderClouds(Graphics g) {
		for (Cloud cl : clouds) {
			if (cl.getOX() > -200 && cl.getOX() < m.w.getWidth() && cl.getOY() > -200 && cl.getOY() < m.w.getHeight()) {
				cl.setVelx(-player.getVelx());
				cl.setVely(-player.getVely());
				
				cl.gtick();
				cl.draw();
			}else {
				out_clouds.add(cl);
			}
			
		}
		
		for (Cloud oc : out_clouds) {
			clouds.remove(oc);
		}
		
		out_clouds.clear();
	}
	
	public void renderSmoke(Graphics g) {
		if (!playerWon && !enemyWon) {
			for (int i = 0; i < smokel.size(); i++) {
				Cloud smoke = smokel.get(i);
				smoke.gtick();
				smoke.draw();
			}
			
			for (int i = 0; i < enemy_smokel.size(); i++) {
				Cloud smoke = enemy_smokel.get(i);
				smoke.gtick();
				smoke.draw();		
			}
		}
	}
	
	public void renderBullets() {
		for (Bullets bullet : bullets) {
			if (bullet.isDead() == false) {
				bullet.btick();
				bullet.draw();	
				
				if (bullet.oBounds().intersects(enemy.bounds())) {
					enemy.setHealth(enemy.getHealth()-1);
					bullet.setDead(true);
				}
			}
			
			
			this.remove(bullet);
		}
		
		for (Bullets bullet : enemy_bullets) {
			if (bullet.isDead() == false) {
				bullet.btick();
				bullet.draw();	
				
				if (bullet.oBounds().intersects(player.bounds())) {
					player.setHealth(player.getHealth()-1);
					bullet.setDead(true);
				}
			}
			
			
			this.remove(bullet);
		}
	}
	
	public void renderPlayer() {
		this.add(player);
		
		double turnSpeed = 0.25;
		
		if (keyEvent.leftArrow == true) {
			double newDir = player.getDirection()-turnSpeed;
			player.setDirection(newDir);
		}
		
		if (keyEvent.rightArrow == true) {
			double newDir = player.getDirection()+turnSpeed;
			player.setDirection(newDir);
		}
		
		if (keyEvent.keySpace == true) {
			keyEvent.keySpace = false;
			bullet = new Bullets(g2,bulletSprite);
			bullet.setX(player.getPX());
			bullet.setY(player.getPY());
			bullet.setAngle(player.getDirection());
			bullet.setWidth(50);
			bullet.setHeight(50);
			bullet.setSpeed(2);
			bullets.add(bullet);
		}
		
		if (Math.abs(player.getDirection()) >= 360.0f) {
			player.setDirection(0);
		}
		
		player.tick();
		
		if (!enemyWon && !explosionStarted)
			player.draw();
		
		this.remove(player);
	}
	
	public void renderEnemy() {
		if (isMultiplayer) {
			g.setFont(new Font("Arial",Font.PLAIN,48));
			int tw = g.getFontMetrics().stringWidth(enemy.getName());		
			g2.drawString(enemy.getName(), (int)enemy.getPX()+enemy.getWidth()/2-tw/2, (int)enemy.getPY()-50);
			enemy.draw();
		}else {
			this.add(enemy);
			
			if (Math.abs(enemy.getDirection()) >= 360.0f) {
				enemy.setDirection(0);
			}
			
			if (player != null && !explosionStarted)
				enemy.setDirection(enemy.target(player.getPX(),player.getPY(),player.getWidth(),player.getHeight()));
			
			enemy.shoot();
			if (enemy.shooting == true && !explosionStarted) {
				enemy.shooting = false;
				bullet = new Bullets(g2,bulletSprite);
				bullet.setX(enemy.getPX());
				bullet.setY(enemy.getPY());
				bullet.setAngle(enemy.getDirection());
				bullet.setWidth(250);
				bullet.setHeight(250);
				bullet.setSpeed(2);
				enemy_bullets.add(bullet);
			}
			
			enemy.tick();
			
			if (!playerWon && !explosionStarted)
				enemy.draw();
			
			this.remove(enemy);
		}
	}
	
}
