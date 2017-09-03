import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Arrays;
/*
 * the key idea for this problem:
 * 	1. the parameter t has no use, can just be incorporated into initial x or y value
 * 	2. the set of final position is the same as if no points ever collide
 * 	3. so the final position is just a permutation of the asked answer, we just need to find the mapping function/logic
 * 	4. only the points with the same sum of original x and y position will ever collide and thus giving influence to their final position
 * 	5. we conclude that by grouping based on sum of initial x and y, the set of final position for a given group is the set of F.P. as if no collide occurs for that group
 *  6. after any collision, the order of x-y value will not change!!!!
 */
public class CF431B {
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		int n = sc.nextInt();
		int w = sc.nextInt();
		int h = sc.nextInt();
		int[][] origin = new int[n][3]; // an array of n 3-dimension vectors (point index, original x-position, original y-position)
		int[][] end = new int[n][3]; // an array of n 3-dimension vectors (sum of original x and y position, final x-position, final y -position)
		for(int i = 0; i<n; i++) {
			int g = sc.nextInt();
			int p = sc.nextInt();
			int t = sc.nextInt();
			if (g == 1) {
				origin[i] = new int[]{i,p,-t};
				end[i] = new int[]{p-t,p,h};
			} else if (g == 2) {
				origin[i] = new int[]{i,-t,p};
				end[i] = new int[]{p-t,w,p};
			}
		}
		// since only the point with the same sum of original x and y position will ever collide
		// we sort both the end array and the origin array first/primarily on sum of origin x and y to guarantee same group mapped to same group
		// the value of x-y is sorted secondly because points within each group will keep x-y order after any collision
		Arrays.sort(origin, new Comparator<int[]>() { 
			public int compare(int[] a, int[] b) {
				if(a[1]+a[2] > b[1]+b[2])
					return 1;
				else if(a[1]+a[2] < b[1]+b[2])
					return -1;
				else if (a[1]-a[2] > b[1] -b[2])
					return 1;
				else if (a[1]-a[2] < b[1] -b[2])
					return -1;
				else return 0;							
			}
		});
		
		Arrays.sort(end, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				if(a[0] > b[0])
					return 1;
				else if(a[0] < b[0])
					return -1;
				else if(a[1] - a[2] > b[1] - b[2])
					return 1;
				else if(a[1] -a[2] < b[1] - b[2])
					return -1;
				else return 0;
			}
		});
		// finally, after sorting end and origin, the two points have the same position in end and origin are exactly the same points after all these collisions
		// so we just map these two points to get its index from origin vectors
		// used the index to fill the corresponding returned array
		int[][] re = new int[n][2];
		for(int i = 0; i < n; i++) {
			int index = origin[i][0];
			re[index] = new int[]{end[i][1], end[i][2]};
		}
		for(int[] out: re) {
			System.out.println(out[0]+ " "+ out[1]);
		}
	}
}
