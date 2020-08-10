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
		
		int xoff = (m.w.getWidth()-m.width)/2;
		int yoff = (m.w.getHeight()-m.height)/2;
		
		if (renderer.choosingPlayer) {
			if (mx >= 0+xoff && mx <= 100+xoff) {
				if (my >= 500+yoff && my <= 600+yoff) {
					if (renderer.playerSpriteChosenX > 1) {
						renderer.playerSpriteChosenX--;
					}
					back = true;
				}
			}
			if (mx > 300+xoff && mx < 400+xoff) {
				if (my >= 500+yoff && my <= 600+yoff) {
					back = false;
					renderer.playerSpriteChosenX++;
				}
			}
		}else if (renderer.choosingEnemy) {
			if (mx >= 0+xoff && mx <= 100+xoff) {
				if (my >= 500+yoff && my <= 600+yoff) {
					if (renderer.enemySpriteChosenX > 1) {
						renderer.enemySpriteChosenX--;
					}
					back = true;
				}
			}
			if (mx > 300+xoff && mx < 400+xoff) {
				if (my >= 500+yoff && my <= 600+yoff) {
					back = false;
					renderer.enemySpriteChosenX++;
				}
			}
		}
		if (renderer.choosingServer) {
			if (mx > 200+xoff) {
				if (my >= 65+yoff && my <= 115+yoff) {
					renderer.choosingServerIP = true;
					renderer.choosingServerPort = false;
				}
				if (my >= 165+yoff && my <= 215+yoff) {
					renderer.choosingServerPort = true;
					renderer.choosingServerIP = false;
				}
			}
			if (!renderer.username.equals("")) {
				if (mx >= 455+xoff && mx <= 730+xoff) {
					if (my >= 465+yoff && my <= 515+yoff) {
						renderer.keyXpress = 3;
					}
				}
			}
		}
		if (renderer.gameStarted == false && renderer.introStart == false) {
			if (mx >= 450+xoff && mx <= 600+xoff) {
				if (my >= 590+yoff && my <= 640+yoff) {
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
