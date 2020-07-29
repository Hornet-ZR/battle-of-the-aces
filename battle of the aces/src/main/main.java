package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import Server.Client;
import Server.Server;
import render.Renderer;


public class main extends Canvas implements Runnable{
	private Thread mainLoop;
	private double game_version = 0.1;
	private boolean running = false;
	private boolean UPDATE = false;
	private boolean created_server = false;
	private boolean sending_data = false;
	private double frame_cap = 1.0/60.0;
	private int width = 900, height = 700;
	
	private Renderer renderer = new Renderer();
	private Window w;
	
	private Server server;
	
	public int fps = 0;
	public Client client;
	
	public void init() {
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		w = new Window(this,width,height,"Battle of The Aces "+game_version,false);
		mainLoop = new Thread(this);
		mainLoop.run();
	}
	
	public void run() {
		running = true;
		renderer.init(this);
		loop();
	}
	
	public void loop() {
		try {
			double now = 0;
			double last = System.nanoTime() / 1000000000.0;
			double passed = 0;
			double unporcessed = 0;
			while (running) {
				UPDATE = false;
				now = System.nanoTime() / 1000000000.0;
				passed += now - last;
				last = now;
				unporcessed += passed;
				while (unporcessed >= frame_cap) {
					unporcessed -= frame_cap;
					UPDATE = true;
					update();
				}
			
				if (UPDATE) {
					//update();
				}else {
					try {
						Thread.sleep(1);
					}catch (Exception e) {
						
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void update() {
		BufferStrategy bs = this.getBufferStrategy();
		
		Graphics g = null;
		Graphics2D g2 = null;
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		if (g == null) {
			g = bs.getDrawGraphics();
		}
		
		if (renderer.gameStarted == false) g.setColor(Color.BLACK);
		if (renderer.gameStarted == true) g.setColor(Color.CYAN);
		if (renderer.introStart == true) g.setColor(Color.CYAN);
		g.fillRect(0, 0, width, height);
		
		if (renderer.gameStarted == true) {	
			renderer.gameInit();
			
//			renderer.createClouds(g);
//			renderer.renderClouds(g);
			
			renderer.renderUI(g);
			g.translate((int)-renderer.player.getPX()+renderer.player.getWidth()+50,(int)-renderer.player.getPY()+renderer.player.getHeight()-50);
			
			renderer.render(g);
			
			g.translate((int)renderer.player.getPX()+renderer.player.getWidth()+50,(int)renderer.player.getPY()+renderer.player.getHeight()-50);
		}else if (renderer.gameStarted == false) {
			renderer.render(g);
		}

		if (renderer.isMultiplayer) {
			if (renderer.connectingToServer && client == null) {
				client = new Client(renderer.ip);
				client.start();
				renderer.connectingToServer = false;
			}

		}
		
		bs.show();
		g.dispose();
	}
	
	public void createServer() {
		server = new Server();
		server.start();
	}
	
	public static void main (String args[]){
		main m = new main();
		m.init();
	}

}