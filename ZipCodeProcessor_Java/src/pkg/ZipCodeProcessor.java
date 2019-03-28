package pkg;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
public class ZipCodeProcessor {
	public static void main(String[] args) 
	{
		String input = "[94226,94399] [94200,94299] [94133,94133] [94133,94133] [94100,94199]";
		String individualRanges[] = (input.replace("] [",";").replace("[", "").replace("]", "")).split(";");
		SortedSet<Integer> start = new TreeSet<Integer>();
		SortedSet<Integer> end = new TreeSet<Integer>();
		String firstRange[] = individualRanges[0].split(",");
		start.add(Integer.valueOf(firstRange[0]));
		end.add(Integer.valueOf(firstRange[1]));
		for(int x=1; x<individualRanges.length;x++)
		{
			Iterator i = start.iterator(), j = end.iterator();
			String currentRange[] = individualRanges[x].split(",");
			int newStart = Integer.valueOf(currentRange[0]), newEnd = Integer.valueOf(currentRange[1]), count = 0;
			while (i.hasNext() && j.hasNext()) 
			{
				int currentStart = (int)i.next(), currentEnd = (int)j.next();
		        if(newStart >= currentStart && newEnd <= currentEnd)
		        	break;
		        else if(newStart < currentStart && newEnd < currentStart)
		        {
		        	start.add(newStart);
		        	end.add(newEnd);
		        	break;
		        }
		        else if(newStart < currentStart && newEnd <= currentEnd)
		        {
		        	start.remove(currentStart);
		        	start.add(newStart);
		        	break;
		        }
		        else if(newStart < currentStart && newEnd > currentEnd)
		        {
		        	start.remove(currentStart);
		        	end.remove(currentEnd);
		        	start.add(newStart);
		        	end.add(newEnd);
		        	break;
		        }
		        else if(newStart > currentEnd && newEnd > currentEnd)
		        {
		        	count++;
		        }
		    }
			if(count == start.size())
			{
	        	start.add(newStart);
	        	end.add(newEnd);
			}
		}
		Iterator i = start.iterator(), j = end.iterator();
		System.out.println("Output:");
		while (i.hasNext() && j.hasNext()) 
		{
	        System.out.print("["+i.next()+","+j.next()+"] ");
	    }
	}
}
