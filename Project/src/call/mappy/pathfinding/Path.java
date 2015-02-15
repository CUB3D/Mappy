package call.mappy.pathfinding;

import java.awt.Point;
import java.util.ArrayList;


public class Path
{
	private int pos;
	
	private ArrayList<Point> nodes = new ArrayList<Point>();

	public void prependStep(int x, int y)
	{
		nodes.add(0, new Point(x, y));
	}
	
	public Point getStep(int index)
	{
		return nodes.get(index);
	}
	
	public Point getNextStep()
	{
		return getStep(pos++);
	}
	
	public boolean hasNext()
	{
		return pos < nodes.size();
	}
	
	public ArrayList<Point> getPoints()
	{
		return nodes;
	}
	public void reset()
	{
		pos = 0;
	}
}
