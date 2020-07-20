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
		if (renderer.choosingPlayer || renderer.choosingEnemy) {
			if (mx > 0 && mx < 100) {
				if (my > 500 && my < 600) {
					if (renderer.playerSpriteChosenX > 1) {
						renderer.playerSpriteChosenX--;
						renderer.planeIndex--;
					}
					back = true;
				}
			}
			if (mx > 300 && mx < 400) {
				if (my > 500 && my < 600) {
					renderer.playerSpriteChosenX++;
					back = false;
					renderer.planeIndex++;
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
