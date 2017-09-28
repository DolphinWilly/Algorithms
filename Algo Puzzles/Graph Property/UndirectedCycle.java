import java.util.Scanner;
import java.util.*;
/*
 *  find a cycle of length at least 3 in an undirected simple graph
 */
public class UndirectedCycle {
	// non-static field for every instance of graph
	ArrayList<Integer> cycle;
	int[] edgeTo; // for back track purpose to find the cycle
	boolean[] visited;
	ArrayList<Integer>[] adj;
	boolean hasCycle;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		// initialize the new graph
		UndirectedCycle uc = new UndirectedCycle();
		uc.hasCycle = false;
		uc.cycle = new ArrayList<Integer>();
		int n = sc.nextInt();
		int m = sc.nextInt();
		uc.edgeTo = new int[n];
		uc.visited = new boolean[n];
		uc.adj = new ArrayList[n];
		for(int i=0; i<n; i++) {
			uc.adj[i] = new ArrayList<Integer>();
		}
		for(int i=0; i<m; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			uc.adj[a-1].add(b);
			uc.adj[b-1].add(a);
			
		}
		// now we try to find cycle using the helper function dfs
		for(int v=1; v<=n; v++) {
			if(uc.hasCycle)
				break;
			if(!uc.visited[v-1])
				uc.dfs(-1, v);
		}
		
		if(!uc.hasCycle)
			System.out.println("the undirected graph doesn't have cycle");
		else
			System.out.println(uc.cycle);
	}
	
	/*
	 *  u is the vertex that we don't want to visit starting from v so that we can get rid of single-edge-cycle
	 *  if v is the starting point then just set u as -1
	 *  after cycle found, dfs add the vertices to cycle and mark flag true
	 */
	public void dfs(int u, int v) {
		visited[v-1] = true;
		for(int w: adj[v-1]) {
			// note this shortcut need to be put inside the for loop!! so that if a cycle found all effort need to be cut
			if(hasCycle) // we just need to find one cycle so shortcut!
				return;
			if(!visited[w-1]) {
				edgeTo[w-1] = v;
				dfs(v,w);
			}
			else if(w!=u) { // w is visited and w is not equal to u; thus a cycle is found
				hasCycle = true;
				for(int i=v; i!=w; i=edgeTo[i-1]) {
					cycle.add(i);
				}
				cycle.add(w);
//				return; // cycle already found
			}
		}
	}
}
