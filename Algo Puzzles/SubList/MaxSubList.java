
public class MaxSubList {
	int adjmax;
	int tempmax;
	public MaxSubList () {
		this.adjmax = 0;
		this.tempmax = 0;
	}
	public int maxSub(int[] l) {
		for(int i: l) {
			if(i >= 0) {
				adjmax += i;
			} else {
				tempmax = tempmax > adjmax ? tempmax : adjmax;
				if(i + adjmax > 0) {
					adjmax += i;					
				} else {
					adjmax = 0;
				}
			}
		}
		int r = tempmax > adjmax ? tempmax : adjmax;
		return r;
	}
}
