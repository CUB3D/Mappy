package call.mappy.pathfinding.heuristics;

import call.mappy.pathfinding.IPathing;
import call.mappy.pathfinding.IMapInfo;


public interface IHeuristic
{

	public float getCost(IMapInfo map, IPathing entity, int x, int y, int tx, int ty);
}
