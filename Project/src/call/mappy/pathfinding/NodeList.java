package call.mappy.pathfinding;

import java.util.LinkedList;
import java.util.List;

class NodeList
{
	private List<Node> list = new LinkedList<Node>();
	
	public Node first()
	{
		return list.get(0);
	}
	
	public void clear()
	{
		list.clear();
	}

	public void add(Node node)
	{
		for (int i=0; i < list.size();i++)
			if (list.get(i).compareTo(node) > 0)
			{
				list.add(i, node);
				break;
			}
			
		if (!list.contains(node))
			list.add(node);
	}
	
	public void remove(Node node)
	{
		list.remove(node);
	}
	
	public boolean contains(Node node)
	{
		return list.contains(node);
	}
	
	public List<Node> getList()
	{
		return list;
	}
	
	public int size()
	{
		return list.size();
	}
}