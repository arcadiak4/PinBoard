package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolRect implements Tool {
	private Clip clipRect;
	private double currentX;
	private double currentY;
	private double deltaX;
	private double deltaY;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		this.currentX = e.getX();
		this.currentY = e.getY();
		this.clipRect = new ClipRect(this.currentX, this.currentY, 0, 0, Color.BLUE);		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		this.clipRect.setGeometry(this.currentX, this.currentY, e.getX(), e.getY());		
		this.deltaX = e.getX() - this.currentX;
		this.deltaY = e.getY() - this.currentY;
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// right bottom to left top
		if (this.deltaX < 0 && this.deltaY < 0) {
			this.clipRect.setGeometry(e.getX(), e.getY(), this.currentX, this.currentY);
		}
		// right top to left bottom
		else if (this.deltaX < 0 && this.deltaY > 0) {
			this.clipRect.setGeometry(e.getX(), this.currentY, this.currentX, e.getY());
		}
		// left bottom to right top
		else if (this.deltaX > 0 && this.deltaY < 0) {
			this.clipRect.setGeometry(this.currentX, e.getY(), e.getX(), this.currentY);
		}
		// left top to right bottom
		else {
			this.clipRect.setGeometry(this.currentX, this.currentY, e.getX(), e.getY());			
		}
		
		i.getBoard().addClip(clipRect);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.setStroke(Color.BLUE);
		
		// right bottom to left top
		if (this.deltaX < 0 && this.deltaY < 0) {
			gc.strokeRect(this.clipRect.getRight(), this.clipRect.getBottom(), this.clipRect.getLeft() - this.clipRect.getRight(), this.clipRect.getTop() - this.clipRect.getBottom());		
		}
		// right top to left bottom
		else if (this.deltaX < 0 && this.deltaY > 0) {
			gc.strokeRect(this.clipRect.getRight(), this.clipRect.getTop(), this.clipRect.getLeft() - this.clipRect.getRight(), this.clipRect.getBottom() - this.clipRect.getTop());			
		}
		// left bottom to right top
		else if (this.deltaX > 0 && this.deltaY < 0) {
			gc.strokeRect(this.clipRect.getLeft(), this.clipRect.getBottom(), this.clipRect.getRight() - this.clipRect.getLeft(), this.clipRect.getTop() - this.clipRect.getBottom());			
		}
		// left top to right bottom
		else {
			gc.strokeRect(this.clipRect.getLeft(), this.clipRect.getTop(), this.clipRect.getRight() - this.clipRect.getLeft(), this.clipRect.getBottom() - this.clipRect.getTop());			
		}
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Filled rectangle tool";
	}

	public Clip getClipRect() {
		return clipRect;
	}

}
