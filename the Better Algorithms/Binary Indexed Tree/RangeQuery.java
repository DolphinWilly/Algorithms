
/*
 *  Assume we have an array with index 1,2,3,4, ...,n, we want to the following query
 *  1. to add element in [l,r] with value v
 *  2. to return the sum of element in (l,r]
 *  
 *  well to do this, we need two BITs, one with its cumulative sum corresponding to a single element in the array
 *  so that we only need to update a constant (actually two) number of index for range update,
 *  and the other with its i-th cumulative sum corresponding to the difference between sum of the first i elements in the array and
 *  i*(the i-th element) in the array
 */
public class RangeQuery {
	int n; // the number of element in the array
	BIT tree1; // use its sum for a single element in the array
	BIT tree2; // use its sum (together with tree1) for calculating sum of the first i element in the array
	public RangeQuery(int n) {
		this.n = n;
		tree1 = new BIT(n);
		tree2 = new BIT(n);
	}
	
	/*
	 * Query to add value val to elements in [l,r] 
	 * l>=1 since the array begins with index 1
	 * takes log time
	 */
	public void updateRange(int l, int r, int val) {
		//update tree1 for single element's value update
		tree1.update(l, val);
		tree1.update(r+1, -val); // don't need to worry about overflow of r+1, update will ignore that if r+1 > n
		
		//update tree2 for sum update
		tree2.update(l, (l-1)*val);
		tree2.update(r+1, val*(-r));
	}
	
	/*
	 * get the sum of the first idx elements in the array
	 */
	public int sum(int idx) {
		return tree1.sum(idx)*idx - tree2.sum(idx);
	}
	
	/*
	 * get the sum of the elements with index in the range (l,r]
	 */
	public int rangeSum(int l, int r) {
		return sum(r) - sum(l);
	}
	
	/*
	 * return the element at index idx
	 */
	public int getElement(int idx) {
		return tree1.sum(idx);
	}
	public static void main(String args[]) {
		//let's test the code
		RangeQuery rq1 = new RangeQuery(10);
		rq1.updateRange(2, 4, 1);
		rq1.updateRange(3, 6, 2);
		rq1.updateRange(5, 8, 3);
		rq1.updateRange(9, 10, 4);
		for(int i=1; i<=10; i++) {
			System.out.println("element "+rq1.getElement(i));
			System.out.println("sum "+ rq1.sum(i));
		}
		System.out.println(rq1.rangeSum(0, 5));
		System.out.println(rq1.rangeSum(3, 9));
		System.out.println(rq1.rangeSum(5, 10));
	}
}
