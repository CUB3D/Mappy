package call.mappy.pathfinding.heuristics;

import call.mappy.pathfinding.IPathing;
import call.mappy.pathfinding.IMapInfo;

public class ClosestHeuristic implements IHeuristic
{
	public float getCost(IMapInfo map, IPathing entity, int x, int y, int tx, int ty)
	{		
		float dx = tx - x;
		float dy = ty - y;
		
		float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		
		return result;
	}

}
