package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection {
	private List<Clip> selectedClips;
	
	public Selection() {
		this.selectedClips = new ArrayList<>();
	}
	
	/***
	 * Select only one object on the board
	 * @param board
	 * @param x
	 * @param y
	 */
	public void select(Board board, double x, double y) {
		if (board.getContents().size() > 0)
			selectedClips.clear();
			for (Clip c : board.getContents())
				if (c.isSelected(x, y)) {
					this.selectedClips.add(c);
					break;
				}
	}
	
	/***
	 * Select multiple objects on the board, if an object has already been clicked on, then remove it from the selection
	 * @param board
	 * @param x
	 * @param y
	 */
	public void toogleSelect(Board board, double x, double y) {
		if (board.getContents().size() > 0)
			for (Clip c : board.getContents())
				if (c.isSelected(x, y))
					if (!selectedClips.contains(c))
						this.selectedClips.add(c);
					else
						this.selectedClips.remove(c);
	}
	
	/***
	 * Remove all the clips from the selection
	 */
	public void clear() {
		if (selectedClips.size() > 0)
			selectedClips.clear();
	}
	
	/***
	 * Return the selection list
	 * @return
	 */
	public List<Clip> getContents() {
		return this.selectedClips;
	}
	
	/***
	 * Draw the englobing rectangle of each clip
	 * @param gc
	 */
	public void drawFeedback(GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		for (Clip c: selectedClips)
			gc.strokeRect(c.getLeft(), c.getTop(), c.getRight() - c.getLeft(), c.getBottom() - c.getTop());
	}
}
