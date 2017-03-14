import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework
{
	int n; // number of men (women)

	int MenPrefs[][]; // preference list of men (n*n)
	int WomenPrefs[][]; // preference list of women (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	{
		int man; // man's number
		int woman; // woman's number

		public MatchedPair(int Man,int Woman)
		{
			man=Man;
			woman=Woman;
		}

		public MatchedPair()
		{
		}
	}

	// reading the input
	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();

			String [] parts = text.split(" ");
			n=Integer.parseInt(parts[0]);

			MenPrefs=new int[n][n];
			WomenPrefs=new int[n][n];

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] mList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					MenPrefs[i][j]=Integer.parseInt(mList[j]);
				}
			}

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] wList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					WomenPrefs[i][j]=Integer.parseInt(wList[j]);
				}
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");

			for(int i=0;i<MatchedPairsList.size();i++)
			{
				writer.println(MatchedPairsList.get(i).man+" "+MatchedPairsList.get(i).woman);
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Framework(String []Args)
	{
		input(Args[0]);

		MatchedPairsList=new ArrayList<MatchedPair>(); // you should put the final stable matching in this array list

		/* NOTE
		 * if you want to declare that man x and woman y will get matched in the matching, you can
		 * write a code similar to what follows:
		 * MatchedPair pair=new MatchedPair(x,y);
		 * MatchedPairsList.add(pair);
		*/

		//YOUR CODE STARTS HERE
		//wa-hoo need a women's preference list with man's actual rank to compare man w.r.t a woman in O(1)
		int Ranking[][] = new int[n][n];
		for(int i = 0; i<n; i++) {
			// get the ith woman's preference list
			int[] pref = WomenPrefs[i];
			//j is the actual ranking
			for(int j = 1; j<= n; j++) {
				Ranking[i][pref[j-1]] = j;
			}
		}
		
		//hey, we initialize the engagement list
		for(int i = 0; i<n; i++) {
			MatchedPair pair = new MatchedPair(-1,i);
			MatchedPairsList.add(pair);
		}
		// wa-hoo, we actually need a list to keep track of the next woman a given man will propose next! just the index of woman will do!
		int[] MenNext = new int[n];
		for(int i = 0; i < n; i++) {
			MenNext[i] = 0;
		}
		//hey, create a stack of freeMan, using int to denote each man
		Stack<Integer> freeMan = new Stack<Integer>();
		//initialize the freeMan stack
		for(int i = 0; i<n; i++) {
			freeMan.push(i);
		}
		//the real loop that asks the freeMan in the stack to propose to the next woman until there is no freeMan in the Stack
		while(!freeMan.empty()) {
			// get the proposing man
			int m = freeMan.pop();
			// get the woman
			MatchedPair pair = MatchedPairsList.get(MenPrefs[m][MenNext[m]]);
			if(pair.man == -1) {
				MatchedPairsList.set(pair.woman, new MatchedPair(m,pair.woman));
			}
			else if(Ranking[pair.woman][pair.man] > Ranking[pair.woman][m]) {
				MenNext[pair.man] = MenNext[pair.man] + 1;
				freeMan.push(pair.man);
				MatchedPairsList.set(pair.woman, new MatchedPair(m,pair.woman));
			}else {
				MenNext[m] = MenNext[m] + 1;
				freeMan.push(m);
			}
		}
		//YOUR CODE ENDS HERE

		output(Args[1]);
	}

	public static void main(String [] Args) // Strings in Args are the name of the input file followed by the name of the output file
	{
		new Framework(Args);
	}
}
