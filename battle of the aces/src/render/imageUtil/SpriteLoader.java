package render.imageUtil;

import java.awt.image.BufferedImage;

public class SpriteLoader {
	public BufferedImage loadGUISprite(BufferedImage imgIn, int x, int y) {
		BufferedImage sprite = imgIn.getSubimage((x*342)-342, (y*342)-342, 342, 342);
		return sprite;
	}
	public BufferedImage loadPlayerSprite(BufferedImage imgIn, int x, int y) {
		BufferedImage sprite = imgIn.getSubimage((x*282)-282, (y*214)-214, 282, 214);
		return sprite;
	}
	public BufferedImage loadEnemySprite(BufferedImage imgIn, int x, int y) {
		BufferedImage sprite = imgIn.getSubimage((x*342)-342, (y*342)-342, 342, 342);
		return sprite;
	}
}
