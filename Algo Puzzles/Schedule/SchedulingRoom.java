import java.util.*;

public class SchedulingRoom {
	
	public static void main(String args[]) {
		int[] start = new int[] {1,2,3,8};
		int[]  end = new int[] {4,6,6,9};
		int[] start2 = new int[] {1,2,5,8};
		int[]  end2 = new int[] {4,6,6,9};
		System.out.println(room(start, end));
		System.out.println(room(start2, end2));

	}
	
	public static int room(int[] start, int[] end) {
		int n = start.length;
		int[][] mixed = new int[2*n][2];
		for(int i=0; i<2*n;i++) {
			if(i<n) {
				// assume point overlapping can happens in a single room, so the second entry in start is larger than end
				mixed[i] = new int[] {start[i],1};
			} else {
				mixed[i] = new int[] {end[i-n],0};
			}
		}
		Comparator<int[]> c = new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				if(a[0] >b[0])
					return 1;
				else if(a[0]<b[0])
					return -1;
				else if(a[1] >b[1])
					return 1;
				else if (a[1] < b[1])
					return -1;
				return 0;
			}
		};
		Arrays.sort(mixed,c);
		int sum = 0;
		int max = 0;
		for(int[] m: mixed) {
			if(m[1] == 1)
				sum++;
			else
				sum--;
//			System.out.println(sum);
			max = Math.max(sum, max);
		}
		return max;
		
	}
}
