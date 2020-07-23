package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import render.Renderer;


public class main extends Canvas implements Runnable{
	private Thread mainLoop;
	private double game_version = 0.1;
	private boolean running = false;
	private boolean UPDATE = false;
	private double frame_cap = 1.0/60.0;
	private int width = 900, height = 700;
	
	private Renderer renderer = new Renderer();
	private Window w;
	
	public int fps = 0;
	
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
		double now = 0;
		double last = System.nanoTime() / 1000000000.0;
		double passed = 0;
		double unporcessed = 0;
		long timer = System.currentTimeMillis();
		int pre_fps = 0;
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
				//pre_fps++;
			}
//			if (System.currentTimeMillis() - timer > 1000) {
//				timer += 1000;
//				fps = pre_fps;
//				pre_fps = 0;
//			}
			if (UPDATE) {
				update();
			}else {
				try {
					Thread.sleep(1);
				}catch (Exception e) {
					
				}
			}
		}
	}
	
	public void update() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		if (renderer.gameStarted == false) g.setColor(Color.BLACK);
		if (renderer.gameStarted == true) g.setColor(Color.CYAN);
		if (renderer.introStart == true) g.setColor(Color.CYAN);
		g.fillRect(0, 0, width, height);
		
		if (renderer.gameStarted == true) {	
			renderer.gameInit();
			
//			renderer.createClouds(g);
//			renderer.renderClouds(g);
			
			renderer.renderUI(g);
			renderer.renderClouds(g);
			g.translate((int)-renderer.player.getPX()+renderer.player.getWidth()+50,(int)-renderer.player.getPY()+renderer.player.getHeight()-50);
			
			renderer.render(g);
			
			g.translate((int)renderer.player.getPX()+renderer.player.getWidth()+50,(int)renderer.player.getPY()+renderer.player.getHeight()-50);
		}else if (renderer.gameStarted == false) {
			renderer.render(g);
		}
		bs.show();
		g.dispose();
	}
	public static void main (String args[]){
		main m = new main();
		m.init();
	}

}