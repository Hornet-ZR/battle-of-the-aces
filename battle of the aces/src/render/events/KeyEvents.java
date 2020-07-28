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
		if (renderer.choosingServer) {
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && renderer.ip.length() > 0 && renderer.choosingServerIP == true) {
				renderer.ip = renderer.ip.substring(0, renderer.ip.length() - 1);
			}else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && renderer.username.length() > 0 && renderer.choosingServerPort == true) {
				renderer.username = renderer.username.substring(0, renderer.username.length() - 1);
			}
			
			if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
				if (renderer.choosingServerIP) {
					renderer.ip += e.getKeyChar();
				}
				if (renderer.choosingServerPort) {
					renderer.username += e.getKeyChar();
				}
			}
		}
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
		
	}

}
