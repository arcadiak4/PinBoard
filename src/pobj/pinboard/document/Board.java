package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board implements Clip {
	private List<Clip> lclips;
	
	// constructor
	public Board() {
		lclips = new ArrayList<Clip>();
	}
	
	// returns the list of elements on the board
	public List<Clip> getContents() {
		return lclips;
	}
	
	// add a clip
	public void addClip(Clip clip) {
		lclips.add(clip);
	}
	
	public void addClip(List<Clip> clip) {
		lclips.addAll(clip);
//		for(Clip c : clip)
//			lclips.add(c);
	}
	
	// remove a clip
	public void removeClip(Clip clip) {
		lclips.remove(clip);
	}
	
	public void removeClip(List<Clip> clip) {
		lclips.removeAll(clip);
	}
	
	// draw the content of the board
	@Override
	public void draw(GraphicsContext ctx) {
		//ClipRect rec = new ClipRect(0, 0, ctx.getCanvas().getWidth(), ctx.getCanvas().getHeight(), Color.WHITE);
		ctx.setFill(Color.WHITE);
		ctx.fillRect(0, 0, ctx.getCanvas().getWidth(), ctx.getCanvas().getHeight());
		if (lclips.size() > 0)
			for (Clip c : lclips)
				c.draw(ctx);
	}

	@Override
	public double getTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBottom() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clip copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
