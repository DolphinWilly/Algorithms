
public class WordDictionary {
	
	public static void main(String args[]) {
		WordDictionary test = new WordDictionary();
		test.addWord("car");
		test.addWord("caw");
		test.addWord("cbbbbbbbc");
		System.out.println(test.search("c.w"));
		System.out.println(test.search("c..w"));
		System.out.println(test.search("c.**w"));
		System.out.println(test.search("*"));
		System.out.println(test.search("*bc"));
		System.out.println(test.search("*b"));




	}
	Node root;
	/** Initialize your data structure here. */
    public WordDictionary() {
        root = new Node();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        Node current = root;
        for(int i=0; i< word.length(); i++) {
        	char c = word.charAt(i);
        	if (current.children[c-'a'] == null)
        		current.children[c-'a'] = new Node();
        	current = current.children[c-'a'];
        }
        current.isEnd = true;
        	
    }
    
    public boolean helperSearch(String word, Node node) {
    	if (node == null) return false;
    	if (word.equals("")) return node.isEnd; // so we throw the job of checking end at this beginning states
    	if (word.charAt(0) == '*') {
    		if(helperSearch(word.substring(1),node)) // the star matches empty
    			return true;
    		for(Node n: node.children) 
    			if(helperSearch(word,n)) 
    				return true;
    		return false;		
    	} else if (word.charAt(0) == '.') {
    		for(Node n: node.children)
    			if(helperSearch(word.substring(1), n))
    				return true;
    		return false;
    	} else { // now the first word is actually a letter
    		char c = word.charAt(0);
    		if(node.children[c-'a'] == null) return false;
    		else return helperSearch(word.substring(1),node.children[c-'a']);
    		
    	}
    }
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return helperSearch(word, root);
    }
    
    public class Node {
    	Node[] children;
    	boolean isEnd;
    	public Node() {
    		children = new Node[26];
    		isEnd = false;
    	}
    }
}
