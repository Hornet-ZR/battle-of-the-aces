package render.soundUtil;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class soundLoader {
	
	public void playSound(Clip c) {
		new Thread(()->{
			c.start();
			try {
				Thread.sleep(c.getMicrosecondLength()/1000);
			}catch(Exception e) {
				
			}
		}).start();
		
	}
	
	public Clip loadSound(File file) {
		Clip clip = null;

		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			
		}catch(Exception e) {
			
		}
		
		return clip;
	}
}
