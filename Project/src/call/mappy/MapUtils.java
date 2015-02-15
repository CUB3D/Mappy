package call.mappy;

import java.awt.Point;
import java.awt.Rectangle;

public class MapUtils
{
	public static Point getTileFromPixelLocation(Map map, Point pixelloc, int tileSize)
	{
		for(int x = 0; x < map.getWidth(); x++)
		{
			for(int y = 0; y < map.getHeight(); y++)
			{
				Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);

				if(rect.contains(pixelloc))
					return new Point(x, y);
			}
		}
		return null;
	}
}
