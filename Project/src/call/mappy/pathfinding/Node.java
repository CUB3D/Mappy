package call.mappy.pathfinding;

class Node
{
	private int x;
	private int y;
	private float cost;
	public Node parent;
	private float heuristic;
	private int depth;
	private boolean open;
	private boolean closed;


	public Node(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int setParent(Node node)
	{
		this.depth = node.depth + 1;
		this.parent = node;

		return depth;
	}

	public int compareTo(Node node)
	{
		float f = heuristic + cost;
		float of = node.heuristic + node.cost;

		if (f < of)
			return -1;
		else
		{
			if(f > of)
				return 1;
			else
				return 0;
		}
	}

	public void setOpen(boolean open)
	{
		this.open = open;
	}

	public boolean isOpen()
	{
		return open;
	}

	public void setClosed(boolean closed)
	{
		this.closed = closed;
	}

	public boolean isClosed()
	{
		return closed;
	}

	public void reset()
	{
		closed = false;
		open = false;
		cost = 0;
		depth = 0;
	}
	
	public void setCost(float cost)
	{
		this.cost = cost;
	}
	
	public float getCost()
	{
		return cost;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setHeuristic(float heuristic)
	{
		this.heuristic = heuristic;
	}
	public float getHeuristic()
	{
		return heuristic;
	}
}