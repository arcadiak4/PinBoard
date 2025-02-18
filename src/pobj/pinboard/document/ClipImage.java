package pobj.pinboard.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClipImage extends AbstractClip implements Clip {
	private File filename;
	private Image img;
	
	public ClipImage(double left, double top, File filename) throws FileNotFoundException {
		this.left = left;
		this.top = top;
		
		this.filename = filename;
		Image img = new Image(new FileInputStream(filename.getAbsolutePath()));
		this.img = img;
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.drawImage(this.img, this.left, this.top);
	}

	@Override
	public Clip copy() {
		try {
			return new ClipImage(this.left, this.top, this.filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Image getImg() {
		return img;
	}

	public File getFilename() {
		return filename;
	}

}
