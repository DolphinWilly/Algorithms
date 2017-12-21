import java.util.*;
/*
 * There is an array of n coins. Each coin is put face down on table. You have two queries:
 * 1. T i j (turn coins from index i to index j, include i-th and j-th coin – coin which was face down will be face up; coin which was face up will be face down)
 * 2. Q i (answer 0 if i-th coin is face down else answer 1)
 */
public class FlippingCoins {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// first two input is n, the number of coins, and m the total number of queries
		int n = sc.nextInt(); 
		int m = sc.nextInt();
		BIT tree1 = new BIT(n);
		for(int i=1; i<=m; i++) {
			int type = sc.nextInt(); // each query begin with a type, 1 means type 1 query and 2 means type 2 query
			if(type == 1) {
				int a =sc.nextInt();
				int b = sc.nextInt();
				tree1.update(a, 1);
				tree1.update(b+1, 1); // note if j+1 is greater than MAXVALUE, i.e. n, update will ignore it
			} else if (type == 2) {
				int c =sc.nextInt();
				int output = tree1.sum(c)%2 == 0? 0:1;
				System.out.println(output);
			}
		}
	}
}
