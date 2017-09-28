import java.util.Scanner;
import java.util.*;
public class DirectedCycle {
	boolean hasCycle;
	boolean[] onStack; //is the point on stack now? if the point is revisited and is onStack then a directed cycle! otherwise not a cycle
	ArrayList<Integer>[] adj;
	boolean[] visited;
	int[] edgeTo; // Edge set to be back tracked
	ArrayList<Integer> cycle;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		DirectedCycle dc = new DirectedCycle();
		dc.adj = new ArrayList[n];
		for(int i=0; i<n; i++) 
			dc.adj[i] = new ArrayList<Integer>();
		for(int i=0; i<m; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			dc.adj[a-1].add(b);
		}
		dc.onStack = new boolean[n];
		dc.visited = new boolean[n];
		dc.edgeTo = new int[n];
		dc.cycle = new ArrayList<Integer>();
		for(int i=1; i<=n; i++) {
			if(dc.hasCycle)
				break;
			if(!dc.visited[i-1]) 
				dc.dfs(i);		
		}
		
		if(!dc.hasCycle)
			System.out.println("this directed graph doesn't have any cycle");
		else {
			for(int i=dc.cycle.size()-1; i>=0; i--)
				System.out.print(dc.cycle.get(i)+" ");
		}
//			System.out.println(dc.cycle);
	}
	
	public void dfs(int v) {
		visited[v-1] = true;
		onStack[v-1] = true;
		for(int w: adj[v-1]) {
			if(hasCycle)
				return;
			if(!visited[w-1]) {
				edgeTo[w-1] = v;
				dfs(w);
			}
			else if (onStack[w-1]) { // w is visited and is currently on stack! a directed cycle!
				hasCycle = true;
				for(int i = v; i!=w; i = edgeTo[i-1]) 
					cycle.add(i);
				cycle.add(w);
			}
		} // after loop through all the branch from v, will be off stack!
		onStack[v-1] = false;
		
	}
}
