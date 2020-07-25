package render.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import render.Renderer;

public class KeyEvents implements KeyListener{
	public boolean upArrow = false, keySpace = false, keyX = false, keyD = false, rightArrow = false, leftArrow = false, keyZ = false;
	private Renderer renderer;
	
	public void init(Renderer r) {
		this.renderer = r;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upArrow = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			keyZ = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_X) {
			keyX = true;
		}
		
		if (renderer.gameStarted) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightArrow = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				keySpace = true;
			}
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
		
		if (e.getKeyCode() == KeyEvent.VK_X) {
			keyX = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keySpace = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		if (renderer.choosingServer) {
			if (renderer.choosingServerIP) {
				renderer.ip += e.getKeyChar();
			}
			if (renderer.choosingServerPort) {
				renderer.port += e.getKeyChar();
			}
		}
	}

}
