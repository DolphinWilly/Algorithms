import java.io.IOException;
import java.io.InputStream;
import java.util.*;
public class PropTree {
	int[] l;
	int[] r;
	boolean[] marked;
	boolean[] parity;
	ArrayList<Integer>[] edges;
	int counter;
	
	// the inner BIT data
	int[] trueBIT;
	int[] falseBIT;
	
	public PropTree(ArrayList<Integer>[] edges) {
		this.edges = edges;
		this.l = new int[edges.length];
		this.r = new int[edges.length];
		this.marked = new boolean[edges.length];
		this.parity =  new boolean[edges.length];
		this.counter = 1;
		// initialize the inner BIT fields
		this.trueBIT = new int[edges.length];
		this.falseBIT = new int[edges.length];
	}
	
	public void build(int index, boolean color) {
		if(marked[index]) return;
		l[index] = counter++;
		parity[index] = color;
		marked[index] = true;
		// propagating to children: only two purposes: 1. parity 2. index range
		for(Integer u: edges[index]) {
			build(u,!color);
		}
		r[index] = counter - 1; // the current counter value is for the next left range, so this right range should be one less
	}
	
	// the inner BIT method
	
	public void Tupdate(int idx, int val) {
		while(idx <= trueBIT.length-1) {
			trueBIT[idx] += val;
			idx += idx&-idx;
		}
	}
	
	public void Fupdate(int idx, int val) {
		while(idx <= falseBIT.length-1) {
			falseBIT[idx] += val;
			idx += idx&-idx;
		}
	}
	
	public void TrangeUpdate(int l, int r, int val) {
		Tupdate(l,val);
		Tupdate(r+1,-val);
	}
	
	public void FrangeUpdate(int l, int r, int val) {
		Fupdate(l,val);
		Fupdate(r+1,-val);
	}
	
	public int Tsum(int idx) {
		int sum = 0;
		while(idx > 0) {
			sum += trueBIT[idx];
			idx -= idx&-idx;
		}
		return sum;
	}
	
	public int Fsum(int idx) {
		int sum = 0;
		while(idx > 0) {
			sum += falseBIT[idx];
			idx -= idx&-idx;
		}
		return sum;
	}
	
	public static void main(String args[]) {
		//Scanner sc = new Scanner(System.in);
		FastScanner sc = new FastScanner(System.in);

		int n = sc.nextInt();
		int m = sc.nextInt();
		int[] val = new int[n];
		for(int i=0; i<n; i++) {
			val[i] = sc.nextInt();
		}
		ArrayList<Integer>[] edges = new ArrayList[n+1];
		for(int i=1; i<=n; i++) edges[i] = new ArrayList<Integer>();
		for(int i=0; i<n-1; i++) {
			int v = sc.nextInt();
			int u = sc.nextInt();
			edges[v].add(u);
			edges[u].add(v);
		}
		PropTree pTree = new PropTree(edges);
		pTree.build(1, true); // since 1 is the root
		//initialization
		for(int i=1; i<=n; i++) {
			// even if on single point, we need range update on that single point
			// since we treat the cumulative sum until a point as its value
			int idx = pTree.l[i];
			pTree.TrangeUpdate(idx, idx, val[i-1]);
			pTree.FrangeUpdate(idx, idx, val[i-1]);
		}
		for(int i=1; i<=m; i++) {
			int type = sc.nextInt();
			int x = sc.nextInt();
			int left = pTree.l[x];
			int right = pTree.r[x];
			if(type == 2) {
				if(pTree.parity[x]) { // true parity
					System.out.println(pTree.Tsum(left));
				} else { // false parity
					System.out.println(pTree.Fsum(left));
				}
			} else if(type == 1) {
				int v = sc.nextInt();

				if(pTree.parity[x]) { // true parity
					pTree.TrangeUpdate(left, right, v);
					pTree.FrangeUpdate(left, right, -v);
				} else { // false parity
					pTree.TrangeUpdate(left, right, -v);
					pTree.FrangeUpdate(left, right, v);
				}
			}
		}
 	}
}

class FastScanner{
    private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    
    public FastScanner(InputStream stream)
    {
        this.stream = stream;
    }
    
    int read()
    {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars){
            curChar = 0;
            try{
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }
    
    boolean isSpaceChar(int c)
    {
        return c==' '||c=='\n'||c=='\r'||c=='\t'||c==-1;
    }
    
    boolean isEndline(int c)
    {
        return c=='\n'||c=='\r'||c==-1;
    }
    
    int nextInt()
    {
        return Integer.parseInt(next());
    }
    
    long nextLong()
    {
        return Long.parseLong(next());
    }
    
    double nextDouble()
    {
        return Double.parseDouble(next());
    }
    
    String next(){
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do{
            res.appendCodePoint(c);
            c = read();
        }while(!isSpaceChar(c));
        return res.toString();
    }
    
    String nextLine(){
        int c = read();
        while (isEndline(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do{
            res.appendCodePoint(c);
            c = read();
        }while(!isEndline(c));
        return res.toString();
    }
}
