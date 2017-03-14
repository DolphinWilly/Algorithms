import java.util.*;
/**
 * There are n employees working in company "X" (let's number them from 1 to n for convenience). 
 * Initially the employees didn't have any relationships among each other. On each of m next days one of the following events took place:
 * either employee y became the boss of employee x (at that, employee x didn't have a boss before);
 * or employee x gets a packet of documents and signs them; then he gives the packet to his boss. 
 * The boss signs the documents and gives them to his boss and so on (the last person to sign the documents sends them to the archive);
 * or comes a request of type "determine whether employee x signs certain documents".
 * Your task is to write a program that will, given the events, answer the queries of the described type. 
 * At that, it is guaranteed that throughout the whole working time the company didn't have cyclic dependencies.
 * 
 * Input
 * The first line contains two integers n and m (1 ≤ n, m ≤ 105) — the number of employees and the number of events.
 * Each of the next m lines contains the description of one event (the events are given in the chronological order). 
 * The first number of the line determines the type of event t (1 ≤ t ≤ 3).
 * If t = 1, then next follow two integers x and y (1 ≤ x, y ≤ n) — numbers of the company employees.
 *  It is guaranteed that employee x doesn't have the boss currently.
 * If t = 2, then next follow integer x (1 ≤ x ≤ n) — the number of the employee who got a document packet.
 * If t = 3, then next follow two integers x and i (1 ≤ x ≤ n; 1 ≤ i ≤ [number of packets that have already been given]) 
 * — the employee and the number of the document packet for which you need to find out information. 
 * The document packets are numbered started from 1 in the chronological order.
 * It is guaranteed that the input has at least one query of the third type.
 * 
 * Output
 * For each query of the third type print "YES" if the employee signed the document package and "NO" otherwise.
 * Print all the words without the quotes.

 * @author Willy (Weiyu Wang)
 *
 *
 * It worths to think about what is the dynamic part and what is the static part of this problem:
 * Viewing employees as vertices and boss relation as an directed edge, we see whether two vertices are connected
 * (in the sense of an undirected graph) is dynamic, it changes over the query; but with this connection property we just need a static 
 * extra property to determine whether b is the box of a at a current point: whether in the final graph, b has a directed path to a
 */
public class InformationGraph {
	private int[] parent;
	private int[] height;
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in); 
		int n = sc.nextInt(); // num of people/vertices
		InformationGraph graph = new InformationGraph (n);
		int q = sc.nextInt(); // num of queries
		ArrayList<Boolean> connected = new ArrayList<Boolean>(); // dynamic property of connectedness at a given time
		ArrayList<int[]> judgePair = new ArrayList<int[]>();
		ArrayList<Integer> sign = new ArrayList<Integer> (); // which person receives the ith document
		for(int j=1; j<=q; j++) {
			int type = sc.nextInt();
			if(type == 1) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				graph.union(x, y);
			}
			else if(type == 2) {
				int x = sc.nextInt();
				sign.add(x);
			}
			else if(type == 3) {
				int x = sc.nextInt();
				int i = sc.nextInt();
				connected.add(graph.connected(sign.get(i-1), x));
				judgePair.add(new int[]{i-1,x});
			}
		}
		// now we have the connectedness info, and just need a static final graph to find directed path
		for(int j=0; j<connected.size(); j++) {
			if(!connected.get(j)) // not connected
				System.out.println("NO");
			else {
				
			}
		}
	}
	
	public InformationGraph (int n) {
		parent = new int[n];
		height = new int[n];
		for(int i=0; i<=n-1; i++) {
			parent[i] = i;
			height[i] = 0;
		}
	}
	
	public int find (int v) {
		assert v>=0 && v<=parent.length; // v is actually in the graph
		int root = v; // try to find the root of vertex v
		while(root!=parent[root]) 
			root = parent[root];
		// now assigning the parent of each vertex in the path to be the root
		while(v!=root) {
			int next = parent[v];
			parent[v] = root;
			v = next;
		}
		return root;
	}
	
	public boolean connected (int v, int w) {
		return find(v) == find(w);
	}
	
	public void union (int v, int w) {
		int rootv = find(v);
		int rootw = find(w);
		if (rootv == rootw)
			return; // v and w are already connected
		// append the shorter tree to the taller tree
		if(height[rootv] < height[rootw])
			parent[rootv] = rootw;
		else if(height[rootv] > height[rootw])
			parent[rootw] = rootv;
		else { // they are of equal height
			parent[rootw] = rootv;
			height[rootv] ++;
		}
		
	}
}
