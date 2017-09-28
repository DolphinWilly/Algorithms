import java.util.*;
public class DirectedHamilton {
	HashSet<Integer>[] adj;
	boolean[] visited;
	boolean[] onStack;
	int postCounter; 
	int[] post;   // the numbering of a vertex in post order dfs, though has no use in this implementation/structure
	boolean hasCycle; 
	ArrayList<Integer> postOrder; // we note any reverse of postOrder of a dfs gives a topological order, just need to check consecutive points has a directed edge to prove uniqueness
	boolean unique = true; // the directed graph has a unique topological order
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		DirectedHamilton dh = new DirectedHamilton();
		dh.visited = new boolean[n];
		dh.onStack = new boolean[n];
		dh.post = new int[n];
		dh.adj = new HashSet[n];
		dh.postOrder = new ArrayList<Integer>();
		for(int i=0; i<n; i++)
			dh.adj[i] = new HashSet<Integer>();
		for(int i=0; i<m; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			dh.adj[a-1].add(b);
		}
		for(int i=1; i<=n; i++) {
			if(dh.hasCycle)
				break;
			if(!dh.visited[i-1]) 
				dh.dfs(i);
		}
		if(dh.hasCycle)
			System.out.println("this directed graph has cycle!");
		else if(!dh.unique)
			System.out.println("this directed graph has multiple topoligcal order");
		else {
			for(int i=dh.postOrder.size()-1; i>=0; i--)
				System.out.print(dh.postOrder.get(i)+" ");
		}	
	}
	
	public void dfs(int v) {
		visited[v-1] = true;
		onStack[v-1] = true;
		for(int w: adj[v-1]) {
			if(hasCycle)
				return;
			if(!visited[w-1]) 
				dfs(w);
			else if(onStack[w-1]) 
				hasCycle = true;		
		}
		onStack[v-1] = false;
		post[v-1] = postCounter++;
		postOrder.add(v);
		if(postCounter > 1) {
			int pre = postOrder.get(postCounter-2);
			if(!adj[v-1].contains(pre))
				unique = false;
		}		
	}
}
