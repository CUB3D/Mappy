package call.mappy;

public class Map
{
	private int[] map;
	private int[] data;
	private int width;
	
	public Map(int[] map, int[] data, int width)
	{
		this.map = map;
		this.data = data;
		this.width = width;
	}
	
	public Map(int width, int height)
	{
		this.map = new int[width * height];
		this.data = new int[width * height];
		this.width = width;
	}
	
	public void setTileID(int x, int y, int id)
	{
		this.map[x + y * width] = id;
	}
	
	public void setData(int x, int y, int b)
	{
		this.data[x + y * width] = b;
	}

	public int getTileID(int x, int y)
	{
		return this.map[x + y * width];
	}
	
	public int getData(int x, int y)
	{
		return this.data[x + y * width];
	}
	
	public int[] getTileArray()
	{
		return this.map;
	}
	
	public int[] getData()
	{
		return this.data;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return getTileArray().length / getWidth();
	}
}
