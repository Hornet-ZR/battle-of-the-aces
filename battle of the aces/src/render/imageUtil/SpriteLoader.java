package render.imageUtil;

import java.awt.image.BufferedImage;

public class SpriteLoader {
	public BufferedImage loadGUISprite(BufferedImage imgIn, int x, int y) {
		BufferedImage sprite = imgIn.getSubimage((x*256)-256, (y*342)-342, 256, 342);
		return sprite;
	}
	public BufferedImage loadPlayerSprite(BufferedImage imgIn, int x, int y) {
		BufferedImage sprite = imgIn.getSubimage((x*214)-214, (y*282)-282, 214, 282);
		return sprite;
	}
	public BufferedImage loadEnemySprite(BufferedImage imgIn, int x, int y) {
		BufferedImage sprite = imgIn.getSubimage((x*214)-214, (y*282)-282, 214, 282);
		return sprite;
	}
}
