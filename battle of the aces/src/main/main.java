package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.*;

import Server.Client;
import Server.Server;
import render.Renderer;


public class main extends Canvas implements Runnable{
	private Thread mainLoop;
	private JFrame frame;
	private boolean UPDATE = false;
	private boolean running = true;
	private double frame_cap = 1.0/60.0;
	public int width = 900, height = 700;
	private String game_version = "0.9.0";
	
	public Window w;
	private Renderer renderer = new Renderer();
	
	public int fps = 0;
	public Client client;
	public Server server;
	
	public void init() {
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		w = new Window(this, width, height, "Battle of The Aces [BETA "+game_version+"]", true);
		mainLoop = new Thread(this);
		mainLoop.run();
	}
	
	public void run() {
		renderer.init(this);
		running = true;
		loop();
	}
	
	public void loop() {
		try {
			double now = 0;
			double last = System.nanoTime() / 1000000000.0;
			double passed = 0;
			double unporcessed = 0;
			
			while (true) {
				UPDATE = false;
				now = System.nanoTime() / 1000000000.0;
				passed += now - last;
				last = now;
				unporcessed += passed;
				
				if (w.closing && renderer.isMultiplayer) {
					server.clients.remove(client);
				}
				
				while (unporcessed >= frame_cap) {
					unporcessed -= frame_cap;
					UPDATE = true;
					update();
				}
				
				if (UPDATE) {
					update();
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
		
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		if (renderer.gameStarted == false) g.setColor(Color.BLACK);
		if (renderer.gameStarted == true) g.setColor(new Color(0,0,255));
		if (renderer.introStart == true) g.setColor(new Color(0,0,255));
		g.fillRect(0, 0, w.getWidth(), w.getHeight());
		
		if (renderer.gameStarted == true) {	
			renderer.gameInit();
			
			renderer.createClouds(g);
			
			renderer.renderClouds(g);
			
			renderer.renderUI(g);
			
			g.translate(-(int)renderer.player.getPX()+renderer.player.getWidth()+50+(w.getWidth()-width)/2,-(int)renderer.player.getPY()+renderer.player.getHeight()-50+(w.getHeight()-height)/2);
			
			renderer.render(g);
			
			if (!renderer.clearingSmokeThread) {
				renderer.clearingSmokeThread = true;
				new Thread(()->{
					while (true) {
						try {
							Thread.sleep(100);
						}catch(Exception e) {
							
						}
						
						renderer.smokel.clear();
						renderer.enemy_smokel.clear();
					}
				}).start();
			}
			
			renderer.createPlayerSmoke(g);
			renderer.createEnemySmoke(g);
			renderer.renderSmoke(g);
			
			g.translate((int)renderer.player.getPX()+renderer.player.getWidth()+50+(w.getWidth()-width)/2,(int)renderer.player.getPY()+renderer.player.getHeight()-50+(w.getHeight()-height)/2);
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
	
	public void create_server() {
		if (server == null && renderer.showingMenu) {
			new Thread(()->{
				server = new Server();
				server.start();
			}).start();
			
			JFrame frame = new JFrame("Server info");
			JPanel panel = new JPanel();
			JLabel server_status = new JLabel();
			Font font = new Font("Arial",Font.PLAIN,24);
			
			frame.setSize(500,500);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			if (server.server.isBound()) {
				server_status.setText("Server is bound, server running");
			}else {
				server_status.setText("Server error");
			}
			
			panel.setBackground(Color.BLACK);
			
			server_status.setFont(font);
			server_status.setForeground(Color.WHITE);
			
			panel.add(server_status);
			frame.add(panel);
			
			frame.invalidate();
			frame.revalidate();
			frame.repaint();
		}
	}
	
	public void reload_server() {
		server = new Server();
		server.start();
		frame.invalidate();
		frame.revalidate();
		frame.repaint();
	}
	
	public static void main(String args[]){
		main m = new main();
		m.init();
	}

}