package call.mappy.pathfinding;

import java.util.ArrayList;

import call.mappy.Map;
import call.mappy.pathfinding.heuristics.IHeuristic;

public class AStarPathFinder
{
	private ArrayList<Node> closed = new ArrayList<Node>();

	private NodeList open = new NodeList();

	private IMapInfo map;

	private int maxSearchDistance;

	private Node[][] nodes;

	private boolean allowDiagMovement;

	private IHeuristic heuristic;

	private Node current;

	private IPathing mover;

	private int sourceX;
	private int sourceY;

	private int distance;

	private Map tileMap;

	public AStarPathFinder(IMapInfo map, Map tileMap, int maxSearchDistance, boolean allowDiagMovement, IHeuristic heuristic)
	{
		this.heuristic = heuristic;
		this.map = map;
		this.maxSearchDistance = maxSearchDistance;
		this.allowDiagMovement = allowDiagMovement;
		this.tileMap = tileMap;


		nodes = new Node[tileMap.getWidth()][tileMap.getHeight()];

		for(int x = 0; x < tileMap.getWidth(); x++)
			for(int y = 0; y < tileMap.getHeight(); y++)
				nodes[x][y] = new Node(x,y);
	}


	public Path findPath(IPathing entity, int sx, int sy, int tx, int ty)
	{
		current = null;

		this.mover = entity;
		this.sourceX = tx;
		this.sourceY = ty;
		this.distance = 0;

		if (map.blocked(tx, ty))
			return null;


		for (int x=0;x<tileMap.getWidth();x++)
			for (int y=0;y<tileMap.getHeight();y++)
				nodes[x][y].reset();

		nodes[sx][sy].setCost(0);
		nodes[sx][sy].setDepth(0);

		closed.clear();
		open.clear();

		addToOpen(nodes[sx][sy]);

		nodes[tx][ty].parent = null;

		int maxDepth = 0;

		while ((maxDepth < maxSearchDistance) && (open.size() != 0))
		{
			int lx = sx;
			int ly = sy;

			if (current != null)
			{
				lx = current.getX();
				ly = current.getY();
			}

			current = getFirstInOpen();
			distance = current.getDepth();

			if (current == nodes[tx][ty])
				if (isValidLocation(mover, lx, ly, tx, ty))
					break;

			removeFromOpen(current);
			addToClosed(current);

			for(int x = -1; x < 2; x++)
				for(int y = -1; y < 2; y++)
				{
					if (x == 0 && y == 0)
						continue;

					if (!allowDiagMovement)
						if(x != 0 && y != 0)
							continue;

					int xp = x + current.getX();
					int yp = y + current.getY();

					if (isValidLocation(mover, current.getX(), current.getY(), xp, yp))
					{
						float nextStepCost = current.getCost() + getMovementCost(mover, current.getX(), current.getY(), xp, yp);
						Node neighbour = nodes[xp][yp];

						if (nextStepCost < neighbour.getCost())
						{
							if (inOpenList(neighbour))
								removeFromOpen(neighbour);

							if (inClosedList(neighbour))
								removeFromClosed(neighbour);
						}

						if (!inOpenList(neighbour) && !(inClosedList(neighbour)))
						{
							neighbour.setCost(nextStepCost);
							neighbour.setHeuristic(getHeuristicCost(mover, xp, yp, tx, ty));

							maxDepth = Math.max(maxDepth, neighbour.setParent(current));

							addToOpen(neighbour);
						} 
					}
				}
		}

		if (nodes[tx][ty].parent == null)
			return null;

		Path path = new Path();
		Node target = nodes[tx][ty];

		while (target != nodes[sx][sy])
		{
			path.prependStep(target.getX(), target.getY());
			target = target.parent;
		}

		path.prependStep(sx, sy);

		return path;
	}

	public int getCurrentX()
	{
		if (current == null)
			return -1;

		return current.getX();
	}

	public int getCurrentY()
	{
		if (current == null)
			return -1;

		return current.getY();
	}

	protected Node getFirstInOpen()
	{
		return (Node) open.first();
	}

	protected void addToOpen(Node node)
	{
		node.setOpen(true);
		open.add(node);
	}

	protected boolean inOpenList(Node node)
	{
		return node.isOpen();
	}

	protected void removeFromOpen(Node node)
	{
		node.setOpen(false);
		open.remove(node);
	}

	protected void addToClosed(Node node)
	{
		node.setClosed(true);
		closed.add(node);
	}

	protected boolean inClosedList(Node node)
	{
		return node.isClosed();
	}

	protected void removeFromClosed(Node node)
	{
		node.setClosed(false);
		closed.remove(node);
	}

	protected boolean isValidLocation(IPathing mover, int sx, int sy, int x, int y)
	{
		boolean invalid = (x < 0) || (y < 0) || (x >= tileMap.getWidth()) || (y >= tileMap.getHeight());

		if (!invalid && (sx != x || sy != y))
		{
			this.mover = mover;
			this.sourceX = sx;
			this.sourceY = sy;
			invalid = map.blocked(x, y);
		}

		return !invalid;
	}

	public float getMovementCost(IPathing mover, int sx, int sy, int tx, int ty)
	{
		this.mover = mover;
		this.sourceX = sx;
		this.sourceY = sy;

		return map.getCost(tx, ty);
	}


	public float getHeuristicCost(IPathing mover, int x, int y, int tx, int ty)
	{
		return heuristic.getCost(map, mover, x, y, tx, ty);
	}

	public IPathing getPathing()
	{
		return mover;
	}

	public int getSearchDistance()
	{
		return distance;
	}

	public int getSourceX()
	{
		return sourceX;
	}

	public int getSourceY()
	{
		return sourceY;
	}
}
