
public class BIT {
	int MAXVALUE;
	int lastBit;
	int[] tree;
	
	public BIT(int max) {
		MAXVALUE = max;
		tree = new int[MAXVALUE+1]; //idx 0 is always 0, i.e. f[0] = tree[0] = 0
		int pow = (int)(Math.log(MAXVALUE)/Math.log(2));
		lastBit = (int)Math.pow(2, pow);
	}
	
	public int sum(int idx) {
		int sum = 0;
		while (idx > 0) {
			sum += tree[idx];
			idx -= (idx & -idx); // get rid of the last (least significant) 1 in binary representation of idx
		}
		return sum;
	}
	
	public void update(int idx, int val) {
		while(idx <= MAXVALUE) {
			tree[idx] += val;
			idx += (idx & -idx);
		}
	}
	
	/*
	 * 	read the frequency at a single index idx
	 */
	public int read(int idx) {
		return sum(idx) - sum(idx-1);
	}
	
	/*
	 *  read the frequency at a single index with improved algorithms
	 */
	public int fastRead(int idx) {
		int sum = tree[idx];
		if(idx > 0) {
			int z = idx - (idx & -idx);
			idx--;
			while(idx != z) {
				sum -= tree[idx];
				idx -= (idx & -idx);
			}
		}
		return sum;
	}
	
	/*
	 *  read the sum of all frequency in (idx1, idx2]
	 */
	public int interval_read(int idx1, int idx2) {
		if(idx1 >= idx2) return 0;
		return sum(idx2) - sum(idx1);
	}
	
	public void scale(int c) {
		for(int i=1; i<=MAXVALUE; i++) {
			tree[i] *= c;
		}
	}
	
	/*
	 * find the greatest index corresponding to the sum (cumulative frequency)
	 * return -1 if there doesn't exist one
	 * all the single frequency must be non-negative for this binary-search method to work!!!!
	 */
	public int find(int sum) {
		int idx = 0;
		int bitMask = lastBit;
		while(bitMask!=0) {
			int tIdx = idx + bitMask;
			if(tIdx<= MAXVALUE && sum >= tree[tIdx]) { // tIdx is the predicted tree index, we need to guarantee that it doesn't overflow
				sum -= tree[tIdx];
				idx = tIdx;
			}
			bitMask >>=1;			
		}
		if(sum !=0)
			return -1;
		else
			return idx;
	}
//	public static void main(String args[]) {
//		//let's test the code
//		BIT tree1 = new BIT(20);
//		for(int i=1; i<=10; i++) {
//			tree1.update(2*i-1, i);
//		}
//		for(int i=0; i<=20; i++) {
//			System.out.println(tree1.fastRead(i));
//			System.out.println(tree1.read(i));
//			System.out.println("sum "+ tree1.sum(i));
//		}
//		for(int i=2;i<=10;i++) {
//			System.out.println("interval sum "+ tree1.interval_read(1, 2*i-1));
//		}
//		for(int i=0; i<=20; i++) {
//			System.out.println("tree["+i+"] " +tree1.tree[i]);
//		}
//		for(int i=1; i<=10; i++) {
//			System.out.println("cumulative index "+ tree1.find(i*(i+1)/2));
//		}
//	}
}
