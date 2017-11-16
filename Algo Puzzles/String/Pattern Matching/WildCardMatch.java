import java.util.Arrays;

public class WildCardMatch {
	
	//main for testing
	public static void main(String args[]) {
		String str = "baaabab";
//        String pattern = "*****ba*****ab";
//         String pattern = "ba*****ab";
//         String pattern = "ba*ab";
//         String pattern = "a*ab";
//         String pattern = "a*****ab";
        // String pattern = "*a*****ab";
//         String pattern = "ba*ab****";
//         String pattern = "****";
//         String pattern = "*";
//         String pattern = "aa?ab";
//         String pattern = "b*b";
        // String pattern = "a*a";
//         String pattern = "baaabab";
//         String pattern = "?baaabab";
         String pattern = "*baaaba*";
        System.out.println(strMatch(str,pattern));
	}
	static boolean strMatch(String str, String pattern) {
		int n = str.length();
		int m = pattern.length();
		// empty pattern can only match empty str
		if (m==0) return n==0;
		boolean[][] table = new boolean[n+1][m+1];
		// initialize the table to be false
		for(int i=0; i<=n; i++)
			Arrays.fill(table[i], false);
		//only empty str is matched by empty pattern, so set table[0][0] true and keep other false
		table[0][0] =true;
		//if the str is empty table[0][j] = table[0][j-1] iff the j-th pattern is *
		for(int i =1; i<=m; i++) {
			if(pattern.charAt(i-1) == '*')
				table[0][i] = table[0][i-1]; // keep false otherwise
		}
		// fill the table bottom up
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=m; j++) {
				if (pattern.charAt(j-1) == '*') 
					// the star can either match nothing or matching anything (when it keeps and decrease the str)
					table[i][j] = table[i][j-1] || table[i-1][j]; // this is the most important part
				else if (pattern.charAt(j-1) == '?' || str.charAt(i-1) == pattern.charAt(j-1))
					table[i][j] = table[i-1][j-1];
				else // when the pattern is a letter that doesn't match
					table[i][j] =false;				
			}
		}
		return table[n][m];
	}
}
