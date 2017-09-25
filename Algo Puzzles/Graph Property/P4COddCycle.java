import java.util.Scanner;
import java.util.*;

public class P4COddCycle {
	boolean flag = false; //bipartite or not
	ArrayList<Integer> setA = new ArrayList<Integer>();
	ArrayList<Integer> setB = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		ArrayList<Integer>[] adj = new ArrayList[n]; // note the construction of a list of ArrayList
		P4COddCycle oc = new P4COddCycle();
		
		for(int v=0; v<n; v++) {
			adj[v] = new ArrayList<Integer>();
		}
		for(int i=0; i<m; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			adj[a-1].add(b);
			adj[b-1].add(a);
		}
		boolean[] visited = new boolean[n];
		boolean[] color = new boolean[n];

		
		for(int i=1; i<=n; i++) {
			if(oc.flag == true)
				break;
			if(!visited[i-1]) {
				oc.dfs(i,visited,color,adj);
			}
		}
		if(oc.flag)
			System.out.println(-1);
		else 
			oc.dump();		
	}
	
	public void dfs(int v, boolean[] visited, boolean[] color, ArrayList<Integer>[] adj) {
		visited[v-1] = true;
		if(color[v-1])
			setB.add(v);
		else 
			setA.add(v);
		for(int w: adj[v-1]) {
			if(!visited[w-1]) {
				color[w-1] = !color[v-1];			
				dfs(w,visited,color,adj);
			}
			else if(color[w-1] == color[v-1]) { // w already visited and has the same color as v
				flag = true;
				return;
			}
		}
	}
	
	public void dump() {
		System.out.println(setA.size());
		for(int a: setA) 
			System.out.print(a+ " ");
		System.out.print("\n");
		System.out.println(setB.size());
		for(int b: setB) 
			System.out.print(b+" ");
	}
}
