import java.util.*;
public class sumTree {
	int n; // the tree store range [0,n)
	int[] nodeSum; // the root has index 1
	int[] lazy; // lazy update information for each node;
	int[] a; // the original array
	
	public sumTree(int[] a) {
		this.n = a.length;
		nodeSum = new int[4*n];
		lazy = new int[4*n];
		this.a = a.clone();
		build(1,0,n); // the root has index 1, and the range of item is [0,n)
	}
	
	//[l,r)
	public void build(int id, int l, int r) {
		if(r-l<2) { // the interval only has one item, [l,l+1=r)
			nodeSum[id] = a[l];
			return; // reaching a leaf
		}
		int mid = (l+r)/2;
		build(id*2, l,mid);
		build(id*2+1,mid,r);
		nodeSum[id] = nodeSum[id*2] + nodeSum[id*2+1];
	}
	
	public void upd(int id, int l, int r, int x) {
		lazy[id] += x; // save the update information to lazy; we only to pass this information when needed!
		nodeSum[id] += (r-l)*x; // update the current node but not yet its children	
	}
	
	// pass the information stored in lazy[id] to its children
	public void shift(int id, int l, int r) {
		int mid = (l+r)/2;
		upd(id*2,l,mid,lazy[id]);
		upd(id*2+1,mid,r,lazy[id]);
		lazy[id] = 0; // already pass the information to children, so clear the lazy
	}
	
	public void increase(int x, int y, int v, int id, int l, int r) { // for the first round id=1, l=0, r=n
		if(x>=r || y<=l) return; // out of bound
		if(x<=l && r<=y) { // [x,y) completely includes [l,r)
			upd(id,l,r,v);
			return; // we are done;
		}
		// neither completely outside or inside so we need to go down a level, before goind down we need to pass the lazy info to children
		shift(id,l,r);
		int mid = (l+r)/2;
		increase(x,y,v, id*2,l,mid);
		increase(x,y,v,id*2+1,mid,r);
		nodeSum[id] = nodeSum[id*2] + nodeSum[id*2+1]; // we need to update the node when its children has been updated
	}
	
	public int sum(int x, int y, int id, int l, int r) {
		if(x>=r || y<=l) return 0; // out of bound
		if(x<=l && r<=y) { // [x,y) completely includes [l,r)
			return nodeSum[id];
		}
		shift(id,l,r); // pass lazy information to children because we need children! but done need to update nodeSum since we didn't change anything
		int mid = (l+r)/2;
		return sum(x,y,id*2,l,mid) + sum(x,y,id*2+1,mid,r);
	}
	
	public static void main(String args[]) {
		//preTest
		sumTree tree1 = new sumTree(new int[] {1,2,3,4,5,6});
		System.out.println(tree1.sum(2,5,1,0,6));
		System.out.println(tree1.sum(0,4,1,0,6));
		tree1.increase(1, 4, 2, 1, 0, 6);
		System.out.println(tree1.sum(2,5,1,0,6));
		System.out.println(tree1.sum(0,4,1,0,6));
		tree1.increase(0, 2, 1, 1, 0, 6);
		System.out.println(tree1.sum(2,5,1,0,6));
		System.out.println(tree1.sum(0,4,1,0,6));
	}
}
