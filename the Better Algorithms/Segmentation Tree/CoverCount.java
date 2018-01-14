import java.util.*; // solved by Weiyu Wang @Cornell
public class CoverCount {
	// oh you want to get info, a brief info? why not just look at each lazy? :)
//	int[] a; //  the array that saves the color // we actually don't need the original array
	int[] lazy;
	HashSet<Integer> colors;
	
	public CoverCount() {
		lazy = new int[40000000];
		colors = new HashSet<Integer>();
	}
	
	// pass the information on the node with index id to its children
	public void shift(int id) {
		if(lazy[id]!=0) {
			lazy[2*id] = lazy[id];
			lazy[2*id+1] = lazy[id];
			lazy[id] = 0;
		}
	}
	
	// note: to update [x,y] you need to pass in [x,y+1)
	public void upd(int x, int y, int color, int id, int l, int r) { // paint [x,y) with color 
		if(x>=r || y<=l) return;
		if(x<=l && r<=y) {
			lazy[id] = color; // use lazy to indicate this node is painted entirely with a certain color
							  // it is ok that lazy already has a color, so that we can update it with a new color, this is the essence of laziness
			return;
		}
		int mid = (l+r)/2;
		shift(id); // so we need to pass this node info to its children before visiting each children with the new color
		upd(x,y,color,2*id,l,mid);
		upd(x,y,color,2*id+1,mid,r);
	}
	
	// retain the total number of color remained in the whole interval
	public void count(int id, int l, int r) {
		if(lazy[id]!=0) {
			colors.add(lazy[id]);
			return; // since the whole interval is the same color lazy[id], no need to go down
		}
		if(r-l<2)
			return; // reach the leaf, stop counting; this is necessary since some leaf has no color, i.e. with color 0
		int mid =  (l+r)/2;
		count(2*id,l,mid);
		count(2*id+1,mid,r);	
	}
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for(int i=1; i<=t; i++) {
			CoverCount test = new CoverCount();
			int q = sc.nextInt();
			for(int j=1; j<=q; j++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				test.upd(x, y+1, j, 1, 1, 10000001);
			}
			test.count(1, 1, 10000001);
			System.out.println(test.colors.size());
		}
	}
	
}
