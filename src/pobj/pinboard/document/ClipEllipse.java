package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip implements Clip {

	// constructor
	public ClipEllipse(double left, double top, double right, double bottom, Color color) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(this.color);
		ctx.fillOval(this.left, this.top, this.right - this.left, this.bottom - this.top);
		//ctx.strokeOval(this.left, this.top, this.right - this.left, this.bottom - this.top);
	}
	
	@Override
	public Clip copy() {
		return new ClipEllipse(this.left, this.top, this.right, this.bottom, this.color);
	}
	
	@Override
	public boolean isSelected(double x, double y) {
		double cx = (this.left + this.right)/2;
		double cy = (this.top + this.bottom)/2;
		double rx = (this.right - this.left)/2;
		double ry = (this.bottom - this.top)/2;
		
		if ((Math.pow((x - cx)/ rx, 2) + Math.pow((y - cy)/ry, 2)) <= 1)
			return true;
		return false;
	}
}
