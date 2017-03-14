import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class FrameworkFlow
{
	public class Edge
	{
		int headNode;
		int tailNode;
		int capacity = 0;
		int flow = 0;
		Edge originalEdge;
		boolean isForwardEdge;
		
		public Edge( int tN, int hN, int c )
		{
			tailNode = tN;
			headNode = hN;
			capacity = c;
		}
	}
	
	int n;
	int s; // the number of node s 
	int t; // the number of node t
	Edge adjacencyList[][];
	// for every node numbered i adjacencyList[i][.] contains all edges that have node numbered i as its tail
	int maxCapacity;

	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();
			n=Integer.parseInt( text );
			s=Integer.parseInt( reader.readLine() );
			t=Integer.parseInt( reader.readLine() );
			adjacencyList = new Edge[n][];
			String [] parts;
			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				parts=text.split(" ");
				adjacencyList[i]=new Edge[parts.length/2];
				for (int j=0;j<parts.length/2;j++)
				{
					adjacencyList[i][j] = new Edge( i, Integer.parseInt(parts[j*2]), Integer.parseInt(parts[j*2+1]) );
					if ( maxCapacity < adjacencyList[i][j].capacity )
						maxCapacity = adjacencyList[i][j].capacity;
				}
			}
			reader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");
			
			writer.println( n );
			writer.println( s );
			writer.println( t );

			for (int i=0;i<n;i++)
			{
				for (int j=0;j<adjacencyList[i].length;j++)
	                writer.print( adjacencyList[i][j].headNode + " " + adjacencyList[i][j].flow + " " );
				writer.println();
			}

			writer.close();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
	public FrameworkFlow(String []Args)
	{
		input(Args[0]);
		
		//YOUR CODE GOES HERE
		ArrayList<ArrayList<Edge>> aj = new ArrayList<ArrayList<Edge>>();
		for(int i = 0; i<n; i++) {
			ArrayList<Edge> a = new ArrayList<Edge>();
			for(Edge e: adjacencyList[i]) {
				e.isForwardEdge = true;
				Edge backward = new Edge(e.headNode,e.tailNode,0);
				backward.isForwardEdge = false;
				backward.originalEdge = e;
				e.originalEdge = backward; // initialize the "originalEdge" of forward edge to be its potential backward
				a.add(e);
			}
			aj.add(a);
		}
		boolean hasPath = true;
		while(hasPath) {
			//Stack<Map.Entry<Integer, Integer>> path = new Stack<Map.Entry<Integer, Integer>>();
			// why don't we just save the edge?
			ArrayList<Edge> gPath = new ArrayList<Edge>();
			Stack<Integer> process = new Stack<Integer>();
			process.push(s);
			boolean marked[] = new boolean[n];
			marked[s] = true;
			while(true) {
				if(process.isEmpty()) {
					hasPath = false;
					System.out.println("no path");
					break;
				} else {
					int next = process.pop();
					for(Edge e: aj.get(next)) {
						if(!marked[e.headNode] && e.capacity >0) {
							marked[e.headNode] = true;
							process.push(e.headNode);
							//path.push(new AbstractMap.SimpleEntry(next, e.headNode));
							gPath.add(e);
							System.out.println("saved path: "+e.tailNode+ " "+e.headNode );
							if(e.headNode == t) 
								break;						
						}
					}
					if(marked[t])
						break;
				}
			} // end of finding path from s to t
			System.out.println("found path");
			if(hasPath) {
				ArrayList<Edge> ePath = new ArrayList<Edge>();
				int look = t;
				int choose = Integer.MAX_VALUE;
				for(int j = gPath.size()-1; j>=0; j--) {
					//Map.Entry<Integer, Integer> m = path.pop();
					Edge e = gPath.get(j);
					//if(m.getValue() == look) 
					if(e.headNode == look) {
						look = e.tailNode;
						ePath.add(e);
						choose = choose > e.capacity? e.capacity : choose;
					}
				}
				// now we have the min flow vector value on the path and the whole path
				// update the flow; update the capacity
				for(Edge e: ePath) {
					if(e.isForwardEdge) {
						e.flow += choose;
						e.capacity -= choose;
						Edge back = e.originalEdge;
						back.capacity += choose;
						ArrayList<Edge> add = aj.get(back.tailNode);
						add.add(back);
					} else { // e is backward edge
						e.flow -= choose;
						e.capacity -= choose;
						Edge forward = e.originalEdge;
						forward.capacity += choose;
						
					}
					
					
				}
				
			} // else !hasPath end of the loop: flow and Graph has been updated
		}
		
		//END OF YOUR CODE

		output(Args[1]);
	}

	public static void main(String [] Args) //Strings in Args are the name of the input file followed by the name of the output file
	{
		new FrameworkFlow(Args);
	}
}
