import java.util.*;
public class MemorySegTree {
	final static int Q = 10;
	int Next_Free; // the original root started from 0, so the next-free-index at the very beginning is 1
	int n;
	int[] s; // node sum
	int[] L; // point to the index of left child of a given index
	int[] R; // point to the index of right child of a given index
	int[] a; // the original array
	
	public MemorySegTree(int[] a) {
		this.n = a.length;
		Next_Free = 1;
		this.s = new int[Q*n];
		this.L = new int[Q*n];
		this.R = new int[Q*n];
		this.a = a.clone();
		build(0,0,n);
	}
	
	public void build(int id, int l, int r) {
		if(r-l<2) { // [l,l+1)
			s[id] = a[l]; // id is the current node index
			return;
		}
		// need to split
		int mid = (l+r)/2;
		L[id] = Next_Free++;
		R[id] = Next_Free++; // assigning node index for the left and right child of node id, and we can thus find the index from L,R later
		build(L[id],l,mid);
		build(R[id],mid,r);
		s[id] = s[L[id]] + s[R[id]];
	}
	
	public int upd(int p, int v, int id, int l, int r) { // return the index of the updated root
		int ID = Next_Free++;
		if(r-l<2) {
			s[ID] = (a[p] +=v); 
			return ID;
		}
		int mid = (l+r)/2;
		L[ID] = L[id];
		R[ID] = R[id]; // in case we won't update the left or right child of the index id
		if(p < mid) {
			L[ID] = upd(p,v,L[ID],l,mid);
		} else {
			R[ID] = upd(p,v,R[ID],mid,r);
		}
		s[ID] = s[L[ID]]+s[R[ID]]; // update the sum of the root (note: this is crucial and the original tutorial doesn't include this code)
		return ID;
	}
	
	public int sum(int x, int y, int id, int l, int r) {
		if(x>=r || y<=l) return 0; // [x,y) out of range
		if(x<=l && r<=y) return s[id];
		int mid =(l+r)/2;
		return sum(x,y,L[id],l,mid) + sum(x,y,R[id],mid,r);
	}
	
	public static void main(String args[]) {
		int a[] = new int[] {1,2,3,4,5,6};
		MemorySegTree tree1 = new MemorySegTree(a);
		
		for(int i=0; i <6; i++) {
			System.out.println("the "+i+"-th item has value: " + tree1.sum(i,i+1,0,0,6));
		}
		
		int q = 10;
		int[] root = new int[q]; // root index of trees
		// now we start updating the array for q times
		root[0] = tree1.upd(0, 1, 0, 0, 6);
		root[1] = tree1.upd(1, 1, root[0], 0, 6);
		root[2] = tree1.upd(2, 1, root[1], 0, 6);
		root[3] = tree1.upd(3, 1, root[2], 0, 6);
		root[4] = tree1.upd(4, 1, root[3], 0, 6);
		root[5] = tree1.upd(5, 1, root[4], 0, 6);

//		System.out.println(tree1.sum(1, 2, root[1], 0, 6));

		for(int i=0; i<6;i++) {
//			System.out.println("the "+i+"-th item has value: " + tree1.sum(i,i+1,root[5],0,6));
			System.out.println(tree1.sum(1, 4, root[i], 0, 6));
//			System.out.println(tree1.sum(0, 6, root[i], 0, 6));

		}


	}
}
