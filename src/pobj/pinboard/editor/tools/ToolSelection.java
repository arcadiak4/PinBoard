package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class ToolSelection implements Tool {
    private double currentX;
    private double currentY;

    @Override
    public void press(EditorInterface i, MouseEvent e) {
        this.currentX = e.getX();
        this.currentY = e.getY();
        
        if (e.isShiftDown()) {
            i.getSelection().toogleSelect(i.getBoard(), this.currentX, this.currentY);
        } else {
            i.getSelection().select(i.getBoard(), this.currentX, this.currentY);            
        }
    }

    @Override
    public void drag(EditorInterface i, MouseEvent e) {
        double deltaX = e.getX() - this.currentX;
        double deltaY = e.getY() - this.currentY;
        
        if (i.getSelection().getContents().size() > 0) {
            for (Clip c : i.getSelection().getContents()) {
                c.move(deltaX, deltaY);
            }
        }

        this.currentX = e.getX();
        this.currentY = e.getY();
    }

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		this.drag(i, e);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getSelection().drawFeedback(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Moving clip(s)";
	}

}
