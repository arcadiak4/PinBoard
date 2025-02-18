package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipRect extends AbstractClip implements Clip {
	
	// constructor
	public ClipRect(double left, double top, double right, double bottom, Color color) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		// draw the englobing rectangle
		//ctx.strokeRect(0, 0, this.right + this.left, this.bottom + this.top);
		// draw the rectangle
		ctx.setFill(this.color);
		ctx.fillRect(this.left, this.top, this.right - this.left, this.bottom - this.top);
	}

	// copy the rectangle
	@Override
	public Clip copy() {
		return new ClipRect(this.left, this.top, this.right, this.bottom, this.color);
	}
}
