package render.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import render.Renderer;

public class MouseEvents implements MouseListener{
	private Renderer renderer = null;
	public boolean back = false;
	public void init(Renderer r) {
		this.renderer = r;
	}
	public void mouseClicked(MouseEvent e) {
		int mx,my;
		mx = e.getX();
		my = e.getY();
		if (renderer.choosingPlayer) {
			if (mx > 0 && mx < 100) {
				if (my > 500 && my < 600) {
					if (renderer.playerSpriteChosenX > 1) {
						renderer.playerSpriteChosenX--;
					}
					back = true;
				}
			}
			if (mx > 300 && mx < 400) {
				if (my > 500 && my < 600) {
					back = false;
					renderer.playerSpriteChosenX++;
				}
			}
		}else if (renderer.choosingEnemy) {
			if (mx > 0 && mx < 100) {
				if (my > 500 && my < 600) {
					if (renderer.enemySpriteChosenX > 1) {
						renderer.enemySpriteChosenX--;
					}
					back = true;
				}
			}
			if (mx > 300 && mx < 400) {
				if (my > 500 && my < 600) {
					back = false;
					renderer.enemySpriteChosenX++;
				}
			}
		}
		if (renderer.choosingServer) {
			if (mx > 225 && mx < 425) {
				if (my > 65 && my < 115) {
					renderer.choosingServerIP = true;
					renderer.choosingServerPort = false;
				}
				if (my > 165 && my < 215) {
					System.out.println("eeasd");
					renderer.choosingServerPort = true;
					renderer.choosingServerIP = false;
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
