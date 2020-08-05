package render.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.main;
import render.Renderer;

public class KeyEvents implements KeyListener{
	public boolean keySpace = false, keyX = false, keyD = false, rightArrow = false, leftArrow = false, keyZ = false;
	private Renderer renderer;
	private main main;
	
	public void init(Renderer r, main m) {
		this.renderer = r;
		this.main = m;
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
				if (renderer.choosingServerPort && renderer.username.length() <= 15) {
					renderer.username += e.getKeyChar();
				}
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_C) {
			renderer.showingControls = !renderer.showingControls;
		}

		if (e.getKeyCode() == KeyEvent.VK_Z) {
			keyZ = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_X) {
			keyX = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_W && main.server == null && renderer.showingMenu) {
			main.create_server();
		}
		
		if (renderer.gameStarted) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				rightArrow = true;
				break;
				
			case KeyEvent.VK_SPACE:
				keySpace = true;
				break;
				
			case KeyEvent.VK_LEFT:
				leftArrow = true;
				break;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyD = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyD = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			keyZ = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_X) {
			keyX = false;
		}
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			rightArrow = false;
			break;
			
		case KeyEvent.VK_SPACE:
			keySpace = false;
			break;
			
		case KeyEvent.VK_LEFT:
			leftArrow = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
