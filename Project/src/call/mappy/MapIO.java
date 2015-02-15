package call.mappy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class MapIO
{
	public static final String MAPPY_STANDARD_HEADER = "MAPPY1.4";
	
	public static Map readImageMap(File f, java.util.Map<Integer, Integer> colMap) throws IOException, FileNotFoundException
	{
		return readImageMap(new FileInputStream(f), colMap);
	}

	public static Map readImageMap(InputStream i, java.util.Map<Integer, Integer> colMap) throws IOException
	{	
		BufferedImage img = ImageIO.read(i);

		Map map = new Map(img.getWidth(), img.getHeight());

		for(int x = 0; x < img.getWidth(); x++)
			for(int y = 0; y < img.getHeight(); y++)
			{
				int col = img.getRGB(x, y);

				int data = (col << 24) & 0xFF;

				map.setTileID(x, y, colMap.get(col));
				map.setData(x, y, data);
			}

		return map;
	}

	public static Map readMap(File f) throws IOException, FileNotFoundException
	{
		return readMap(new FileInputStream(f));
	}

	public static Map readMap(InputStream loc) throws IOException
	{
		ObjectInputStream stream = new ObjectInputStream(loc);

		String type = stream.readUTF();

		if(type.equalsIgnoreCase(MAPPY_STANDARD_HEADER))
		{
			int width = stream.readInt();
			int height = stream.readInt();

			int size = width * height;

			int[] tiles = new int[size];
			int[] data = new int[size];

			for(int i = 0; i < size; i++)
			{
				tiles[i] = stream.readInt();
				data[i] = stream.readInt();
			}

			stream.close();

			return new Map(tiles, data, width);
		}
		else
			throw new RuntimeException("Incorrect file header");
	}


	public static void saveMap(Map map, OutputStream loc) throws IOException
	{
		ObjectOutputStream stream = new ObjectOutputStream(loc);

		stream.writeUTF(MAPPY_STANDARD_HEADER);

		int[] tiles = map.getTileArray();
		int[] data = map.getData();

		stream.write(map.getWidth());
		stream.writeInt(map.getHeight());

		for(int i = 0; i < tiles.length; i++)
		{
			stream.writeInt(tiles[i]);
			stream.writeInt(data[i]);
		}


		stream.flush();
		stream.close();
	}
}