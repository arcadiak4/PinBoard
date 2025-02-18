package pobj.pinboard.editor.tools;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;

public class ToolImage implements Tool {
	private ClipImage clipImage;
	
	public ToolImage(File filename) throws FileNotFoundException
	{
		this.clipImage = new ClipImage(0, 0, filename);
	}
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		this.clipImage.setGeometry(e.getX(), e.getY(), 0, 0);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		this.clipImage.setGeometry(e.getX(), e.getY(), 0, 0);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		i.getBoard().addClip(clipImage);		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.drawImage(this.clipImage.getImg(), this.clipImage.getLeft(), this.clipImage.getTop());
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Image";
	}

}
