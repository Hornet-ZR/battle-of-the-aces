package render.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.main;
import render.Renderer;

public class MouseEvents implements MouseListener{
	private Renderer renderer = null;
	private main m = null;
	public boolean back = false;
	public void init(Renderer r, main m) {
		this.renderer = r;
		this.m = m;
	}
	public void mouseClicked(MouseEvent e) {
		int mx,my;
		mx = e.getX();
		my = e.getY();
		
		if (renderer.choosingPlayer) {
			if (mx >= 0+(m.w.getWidth()-m.width) && mx <= 100+(m.w.getWidth()-m.width)) {
				if (my+(m.w.getHeight()-m.height) >= 500 && my <= 600+(m.w.getHeight()-m.height)) {
					if (renderer.playerSpriteChosenX > 1) {
						renderer.playerSpriteChosenX--;
					}
					back = true;
				}
			}
			if (mx > 300+(m.w.getWidth()-m.width) && mx < 400+(m.w.getWidth()-m.width)) {
				if (my >= 500+(m.w.getHeight()-m.height) && my <= 600+(m.w.getHeight()-m.height)) {
					back = false;
					renderer.playerSpriteChosenX++;
				}
			}
		}else if (renderer.choosingEnemy) {
			if (mx >= 0+(m.w.getWidth()-m.width)/2 && mx <= 100+(m.w.getWidth()-m.width)/2) {
				if (my >= 500+(m.w.getHeight()-m.height)/2 && my <= 600+(m.w.getHeight()-m.height)/2) {
					if (renderer.enemySpriteChosenX > 1) {
						renderer.enemySpriteChosenX--;
					}
					back = true;
				}
			}
			if (mx > 300+(m.w.getWidth()-m.width)/2 && mx < 400+(m.w.getWidth()-m.width)) {
				if (my >= 500+(m.w.getHeight()-m.height)/2 && my <= 600+(m.w.getHeight()-m.height)/2) {
					back = false;
					renderer.enemySpriteChosenX++;
				}
			}
		}
		if (renderer.choosingServer) {
			if (mx > 200+(m.w.getWidth()-m.width)) {
				if (my >= 65+(m.w.getHeight()-m.height)/2 && my <= 115+(m.w.getHeight()-m.height)/2) {
					renderer.choosingServerIP = true;
					renderer.choosingServerPort = false;
				}
				if (my >= 165+(m.w.getHeight()-m.height)/2 && my <= 215+(m.w.getHeight()-m.height)/2) {
					renderer.choosingServerPort = true;
					renderer.choosingServerIP = false;
				}
			}
			if (!renderer.username.equals("")) {
				if (mx >= 455+(m.w.getWidth()-m.width)/2 && mx <= 730+(m.w.getWidth()-m.width)/2) {
					if (my >= 465+(m.w.getHeight()-m.height)/2 && my <= 515+(m.w.getHeight()-m.height)/2) {
						renderer.keyXpress = 3;
					}
				}
			}
		}
		if (renderer.gameStarted == false && renderer.introStart == false) {
			if (mx >= 450+(m.w.getWidth()-m.width)/2 && mx <= 600+(m.w.getWidth()-m.width)/2) {
				if (my >= 590+(m.w.getHeight()-m.height)/2 && my <= 640+(m.w.getHeight()-m.height)/2) {
					if (renderer.keyXpress > 0) {
						renderer.keyXpress--;
					}
					if (renderer.keyZpress > 0) {
						renderer.keyZpress--;
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

}
