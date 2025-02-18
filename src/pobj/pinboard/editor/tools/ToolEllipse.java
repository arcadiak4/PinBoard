package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;

public class ToolEllipse implements Tool {
	private Clip clipEllipse;
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {			
		this.clipEllipse = new ClipEllipse(e.getX(), e.getY(), 0, 0, Color.RED);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		double current_left = this.clipEllipse.getLeft();
		double current_top = this.clipEllipse.getTop();
		this.clipEllipse.setGeometry(current_left, current_top, e.getX(), e.getY());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		double current_left = this.clipEllipse.getLeft();
		double current_top = this.clipEllipse.getTop();
		
		// right bottom to left top
		if (e.getX() < current_left && e.getY() < current_top) {
			this.clipEllipse.setGeometry(e.getX(), e.getY(), current_left, current_top);
		}
		// right top to left bottom
		else if (e.getX() < current_left && e.getY() > current_top) {
			this.clipEllipse.setGeometry(e.getX(), current_top, current_left, e.getY());
		}
		// left bottom to right top
		else if (e.getX() > current_left && e.getY() < current_top) {
			this.clipEllipse.setGeometry(current_left, e.getY(), e.getX(), current_top);
		}
		// left top to right bottom
		else
		{
			this.clipEllipse.setGeometry(current_left, current_top, e.getX(), e.getY());			
		}
		i.getBoard().addClip(clipEllipse);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.setStroke(Color.RED);
		
		// TODO faire tous les cas
		// right bottom to left top
		// right top to left bottom
		// left bottom to right top
		// left top to right bottom
		gc.strokeOval(this.clipEllipse.getLeft(), this.clipEllipse.getTop(), this.clipEllipse.getRight() - this.clipEllipse.getLeft(), this.clipEllipse.getBottom() - this.clipEllipse.getTop());
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Filled ellipse tool";
	}

	public Clip getClipEllipse() {
		return clipEllipse;
	}

}
