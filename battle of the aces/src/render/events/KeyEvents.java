package render.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEvents implements KeyListener{
	public boolean upArrow = false, keyD = false, rightArrow = false, leftArrow = false, keyZ = false;
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upArrow = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			keyZ = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightArrow = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftArrow = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyD = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upArrow = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightArrow = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftArrow = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyD = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			keyZ = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
