import java.util.*;

public class StronglyConnectedComponents {
	int n; // the number of vertex in the directed graph
	int count;
	int stackTrack; // mean the vertex is the stackTrack th to be visited on the DFS stack
	boolean[] visited;
	int[] low; // if the vertex is on stack this means the lowest rank the vertex can ever visit on stack; if the vertex is popped from
			   // the dfs Stack, set this to be n to mean the largest rank can ever be in a dfs stack
	int[] id; // which strongly connected components does this vertex belong to
	Stack<Integer> stack; // the dfs stack 
	ArrayList<Integer>[] adj;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		StronglyConnectedComponents scc = new StronglyConnectedComponents();
		scc.n = n;
		scc.visited = new boolean[n];
		scc.low = new int[n];
		scc.id  = new int[n];
		scc.stack = new Stack<Integer>();
		scc.adj = new ArrayList[n];
		for(int i=0; i<n; i++)
			scc.adj[i] = new ArrayList<Integer>();
		for(int i=0; i<m; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			scc.adj[a-1].add(b);
		}
		for(int i=1; i<=n; i++) {
			if(!scc.visited[i-1])
				scc.dfs(i);
		}
		ArrayList<Integer>[] components = new ArrayList[scc.count];
		for(int i=0; i<scc.count;i++)
			components[i] = new ArrayList<Integer>();
		for(int i=1; i<=n; i++) 
			components[scc.id[i-1]].add(i);
		
			
		System.out.println("there are "+scc.count+" strongly connected components in the directed graph");
		System.out.println("they are:");
		for(ArrayList<Integer> a: components)
			System.out.println(a);
		
	}
	
	public void dfs(int v) {
		stack.push(v);
		low[v-1] = stackTrack++;
		visited[v-1] = true;
		int min = low[v-1]; // the lowest stack track number vertex v can visit (through itself or its stack children)
		for(int w: adj[v-1]) {
			if(!visited[w-1]) 
				dfs(w);
			if(low[w-1] < min) // if w is already visited, then compare with the low of w, see if w visits any vertex of smaller stack track number
				min = low[w-1];
			
		} // end of looping the neighbor 
		if(min < low[v-1]) {
			low[v-1] = min;
			return;
		}
		// so now min = low[v-1], means the stack child of v didn't visit any vertex that has a smaller dfs track number than v, pop v from the stack!
		int u; // the index vertex for the following loop
		do{
			u = stack.pop();
			id[u-1] = count;
			low[u-1] = n; // set the low[] of u after u is popped
		} while(u!=v);
		count++; // if we reach here means we have popped the stack to create a new strongly connected component
		
		
	}
}
