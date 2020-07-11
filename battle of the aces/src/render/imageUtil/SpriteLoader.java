package render.imageUtil;

import java.awt.image.BufferedImage;

public class SpriteLoader {
	public BufferedImage loadSprite(BufferedImage imgIn, int x, int y) {
		BufferedImage sprite = imgIn.getSubimage((x*342)-342, (y*342)-342, 342, 342);
		return sprite;
	}
}
