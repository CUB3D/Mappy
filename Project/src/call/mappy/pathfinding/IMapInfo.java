package call.mappy.pathfinding;

public interface IMapInfo
{
	public boolean blocked(int tx, int ty);
	

	public float getCost(int tx, int ty);
}
