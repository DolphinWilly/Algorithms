
public class Trie {
	
	//main for testing purpose
	public static void main(String args[]) {
		Trie test = new Trie();
		test.insert("abc");
		test.insert("abd");
		test.insert("bde");		
		test.insert("bdeasdf");

		System.out.println(test.search("abd"));
		System.out.println(test.startsWith("b"));
		System.out.println(test.startsWith("ab"));
		System.out.println(test.startsWith("abc"));
		System.out.println(test.startsWith("aa"));
		System.out.println(test.search("bdea"));
		System.out.println(test.search("bdeasdf"));
		System.out.println(test.search("bd..sdf"));
		System.out.println(test.search("bd.sdf"));
		System.out.println(test.search(".b."));
		System.out.println(test.search(".b.d"));
		System.out.println(test.startsWith("....a"));

	}
	
	private TrieNode root;
	
	public Trie() {
		// initialize the root
		root = new TrieNode();
	}
	
	// insert a word into Trie
	public void insert(String word) {
		// the tracking node
		TrieNode node = root;
		for(int i=0; i< word.length(); i++) {
			char c = word.charAt(i);
			// if the current char c is not in the children, create the child that corresponds to it and put it in
			if (!node.contains(c))
				node.put(c, new TrieNode());
			// down track the node and get ready to see the next char
			node = node.get(c);
		}
		//now this node corresponds to the end of a word
		node.setEnd();
	}
	
	// helper function of searching prefix
	public TrieNode searchPrefix(String word, TrieNode node) {
//		// the tracking node
//		TrieNode node = root;
//		for(int i=0;  i< word.length(); i++) {
//			char c = word.charAt(i);
//			
//			if (node.contains(c))
//				// proceed to the next node for next search char
//				node = node.get(c);
//			else 
//				return null;						
//		}
//		return node;
		// we need a recursive style to generalize the problem
		if(node == null) return null;
		if(word.equals("")) return node;
		char c = word.charAt(0);
		if(c == '.') {
			for(TrieNode child: node.children) {
				if(searchPrefix(word.substring(1), child) !=null)
					return searchPrefix(word.substring(1),child);
			}
			return null;
		} else {
			if (node.contains(c))
				return searchPrefix(word.substring(1),node.get(c));
			else 
				return null;
		}
	}
	
	// search for the exact word
	public boolean search(String word) {
		TrieNode node = searchPrefix(word,root);
		return node!= null && node.isEnd(); // have to be the end in order to be a word added before
	}
	
	// search if there is word with the prefix
	public boolean startsWith(String word) {
		TrieNode node = searchPrefix(word,root);
		return node!=null;
	}
	// TrieNode
	public class TrieNode {
		public TrieNode[] children;
		private final int R = 26;
		private boolean isEnd;
		
		public TrieNode() {
			children = new TrieNode[R];
		}
		
		public boolean contains(char ch) {
			// if the the corresponding child is null, we need to start a new prefix branch
			return children[ch-'a']!= null;
		}
		
		// get the child with char ch
		public TrieNode get(char ch) {
			return children[ch-'a'];
		}
		
		// put TrieNode node into the place corresponding to char
		public void put(char ch, TrieNode node) {
			children[ch-'a'] = node;
		}
		
		public void setEnd() {
			isEnd = true;
		}
		
		public boolean isEnd() {
			return isEnd;
		}
 		
	}
}
