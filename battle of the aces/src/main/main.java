package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import render.Renderer;


public class main extends Canvas implements Runnable{
	private Thread mainLoop;
	private boolean running = false;
	private boolean UPDATE = false;
	private double frame_cap = 1.0/120.0;
	private int width = 900, height = 700;
	
	private Renderer renderer = new Renderer();
	private Window w;
	
	public void init() {
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		w = new Window(this,width,height,"Plane Fight",false);
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
		else if (renderer.gameStarted == true) g.setColor(Color.CYAN);
		
		g.fillRect(0, 0, width, height);
		
		if (renderer.gameStarted == true) {	
			renderer.gameInit();
			g.translate(-renderer.player.getX()+renderer.player.getWidth()+100,-renderer.player.getY()+renderer.player.getHeight());
			
			renderer.render(g);
			
			g.translate(renderer.player.getX()+renderer.player.getWidth()+100,renderer.player.getY()+renderer.player.getHeight());
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