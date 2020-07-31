package render.imageUtil;

import java.io.InputStream;

public class ResLoader {
	
	public InputStream load(String path) {
		InputStream stream = this.getClass().getResourceAsStream(path);
		if (stream == null) stream = this.getClass().getResourceAsStream("/"+path); 
		return stream;
	}
} 
