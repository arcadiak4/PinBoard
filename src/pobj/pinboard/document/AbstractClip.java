package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractClip {
	protected double left;
	protected double top;
	protected double right;
	protected double bottom;
	protected Color color;
	
	public AbstractClip() {
		this.left = 0;
		this.top = 0;
		this.right = 0;
		this.bottom = 0;
		this.color = null;
	}
	/**
	 * Draw a clip on the board. We need first to clean the board.
	 * @param ctx
	 */
	abstract void draw(GraphicsContext ctx);
	
	/**
	 * Copy a clip
	 * @return
	 */
	abstract Clip copy();
	
	/**
	 * Set the coordinates of the clip
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	/**
	 * Move the clip but do not change its shape
	 * @param x
	 * @param y
	 */
	public void move(double x, double y) {
		this.left = this.left + x;
		this.right = this.right + x;
		this.top = this.top + y;
		this.bottom = this.bottom + y;
	}
	
	/**
	 * Set the color of the clip
	 * @param c
	 */
	public void setColor(Color c) {
		this.color = c;
	}
	
	/**
	 * Tells if the selected point is in the rectangle or not
	 * @return
	 */
	public boolean isSelected(double x, double y) {
		if (this.left <= x && x <= this.right)
			if (this.top <= y && y <= this.bottom)
				return true;
			else
			return false;
		return false;
	}
	
	// getters
	public Color getColor() { return this.color; }
	public double getLeft() { return this.left; }
	public double getTop() { return this.top; }
	public double getRight() { return this.right; }
	public double getBottom() { return this.bottom; }
}
