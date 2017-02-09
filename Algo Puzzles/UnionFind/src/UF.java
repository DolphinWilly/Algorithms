
public class UF {
	private int[] parent;
	private int[] height; 
	private int count;
	/**
	 * initialize the Graph by viewing it as n separate points
	 * each node is a root of itself at the beginning
	 * @param n
	 */
	public UF (int n) {
		count = n;
		parent = new int[n];
		height = new int[n];
		for(int i = 0; i <= n-1; i++) {
			parent[i] = i;
			height[i] = 0;			
		}
	}
	
	/**
	 * return the number of trees in the forest
	 * @return
	 */
	public int count() {
		return count;
	}
	
	/**
	 * return the root of a given vertex v;
	 * maintaining the invariant that the parents of all the vertices
	 * are some nodes above the vertex unless the vertex is the root when the parent of root is root itself
	 * (we may find that the parent relation is a well-found relation with all roots of the forests as a base case
	 * in the sense that there is no infinite descending series)
	 * @param v
	 * @return
	 */
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
	
	/**
	 *  return whether vertex v is connected vertex w
	 * @param v
	 * @param w
	 * @return
	 */
	public boolean connected (int v, int w) {
		return find(v) == find(w);
	}
	
	/**
	 *  union the vertex v and vertex w by combining their root
	 *  if v and w is not connected
	 * @param v
	 * @param w
	 */
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
		
		count--;
	}
}
