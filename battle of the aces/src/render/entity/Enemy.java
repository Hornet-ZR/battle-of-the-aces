package render.entity;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Enemy extends GameEntity{

	public Enemy(Graphics2D g2, BufferedImage sprite) {
		super(g2, sprite);
	}
}
