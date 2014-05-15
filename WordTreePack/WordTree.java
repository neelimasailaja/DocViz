package WordTreePack;


import java.io.IOException;
import java.lang.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;


import WordTreePack.WordTreePanel.NodeBoxPair;


public class WordTree
{
	private static final int maxNumWordsPerSegment = 50;
	
	private static final char[] punctuation = ".!?,-:;\"'#$%&()*+/<=>@[]\\^_`{|}~".toCharArray();
	
	private String text, query, textLowerCase, queryLowerCase;
	private Integer flag;
	private Hashtable<String, Integer> frequencyCache;
	private Node root;
	private int matchCount;
	
	private WordTree(Integer flag, String text, String query, String textLowerCase, String queryLowerCase, Node root)
	{
		query = query.trim();
		this.flag=flag;
		this.text = text;
		this.query = query;
		this.textLowerCase = textLowerCase;
		this.queryLowerCase = queryLowerCase;
		frequencyCache = new Hashtable<String, Integer>();
		this.root = root;
	}

	public WordTree(Integer flag, String text, String textLowerCase, String query)
	{	
		this(flag, text, query, textLowerCase, query.toLowerCase(), null);
		
		if(!this.query.equals(""))
			constructTree();
	}

	public WordTree(Integer flag1,WordTree tree, Node newRoot)
	{
		this(flag1,tree.text, tree.query, tree.textLowerCase, tree.queryLowerCase, newRoot);
		this.matchCount = tree.matchCount;
	}
	
	private void constructTree()
	{
		LinkedList<Segment> queryOccurrencesLL = new LinkedList<Segment>();
		int currentIndex = 0;
		while((currentIndex = textLowerCase.indexOf(queryLowerCase, currentIndex)) >= 0)
		{
			char c1 = currentIndex > 0 ? textLowerCase.charAt(currentIndex-1) : ' ';
			char c2 = textLowerCase.charAt(currentIndex + queryLowerCase.length());
			if( (isWhiteSpace(c1) || isPunctuation(c1)) && 
				(isWhiteSpace(c2) || isPunctuation(c2)) )
			{
				queryOccurrencesLL.add(new Segment(currentIndex + query.length()));
			}
			currentIndex += query.length();
		}

		matchCount = queryOccurrencesLL.size();
		if(queryOccurrencesLL.size() > 0)
		{
			Segment[] queryOccurrences = queryOccurrencesLL.toArray(new Segment[0]);
			for(Segment s : queryOccurrences)
			{
				int numWords = 0;
				char c = text.charAt(s.start);
				int i = s.start;
				while(!isEndOfSegment(c)&&((i+1)<text.length()-300))
				{
					if(numWords > maxNumWordsPerSegment)
					{
						s.end-=2;
						break;
					}
					
					if(c == ' ')
						numWords++;
					i++;
					s.end++;
					//System.out.println("Text.length :"+text.length());
					//System.out.println("i value :"+i+1);
					c = text.charAt(i);
					
				}
				s.end++;
			}
			
			Arrays.sort(queryOccurrences);
			int s = textLowerCase.indexOf(queryLowerCase);
			root = new Node(s,s + queryLowerCase.length(), false);
			consolidate(root, queryOccurrences, 0, queryOccurrences.length);
			fixNodes(root);
			root.calculateNumLeaves();
			root.calculatePercentages(root.getNumLeaves());
		}
	}

	private void consolidate(Node parent, Segment[] segments, int start, int end)//, String tabs)
	{
		if(end - start == 1) //base case: consolidating one branch
		{
			if(segments[start].length() > 0)
			{
				Node n = new Node(segments[start], true);
				parent.addChild(n);
			}
			
			segments[start] = null;
			return;
		}
		
		int i = start;
		Segment currentToken  = segments[i].getNextToken();
		if(currentToken == null) //this level is out of tokens
		{
			
			for(; i < end; i++)
				segments[i] = null;
			return;
		}
	
		Node n = new Node(currentToken, false);
		while(i < end)
		{
			Segment s = segments[i];
			Segment token = s.consumeNextToken();
			

			if(!currentToken.equals(token) && token != null)
			{
				parent.addChild(n);
				consolidate(n, segments, start, i);
				start = i;
				currentToken = token;
				n = new Node(token, false);
			}
			i++;
		}
		
		parent.addChild(n);

			consolidate(n, segments, start, i);	
	}

	private void fixNodes(Node n)
	{
		List<Node> children = n.getChildren();
		if(children != null)
		{
			if(children.size() == 1)
			{
				Segment nextSegment = children.get(0).text;
				
				int spaceCount = 0;
				int i = nextSegment.start-1;
				while(isWhiteSpace(text.charAt(i)))
				{
						spaceCount++;
						i--;
				}
				
				nextSegment.start -= (n.text.length()+spaceCount);
				n.text = nextSegment;
				
				n.children = children.get(0).children;
				if(n.children != null)
					for(Node node : n.children)
						node.setParent(n);
				fixNodes(n);
			}
			else
			{
				for(Node node : children)
					fixNodes(node);
			}
		}
	}
	
	public WordTree prune(double percent)
	{
		if(root == null)
			return this;
		
		return prune((int)(root.getNumLeaves() * percent));
	}
	
	public WordTree prune(int maxWidth)
	{
		if(root == null) //empty tree
			return this;
		return prune(maxWidth, root);
	}
	
	public WordTree prune(int maxWidth, Node node)
	{
		Node newRoot = new Node(node);
		newRoot.setUnprunedOriginal(node);
		
		WordTree newTree = new WordTree(this.flag, this.text, this.query, this.textLowerCase, this.queryLowerCase, newRoot);
		
		newTree.matchCount = this.matchCount;
		
		prune(node, newRoot, maxWidth, "");

		newRoot.calculateNumLeaves();
		fixNodes(newRoot);
		return newTree;
	}
	
	private void prune(Node oldNode, Node newNode, int maxWidth, String tabs)
	{
		List<Node> oldChildren = oldNode.getChildren();
		newNode.setUnprunedOriginal(oldNode);
		if(oldChildren == null)
		{
			return;
		}
		
		Collections.sort(oldChildren);
		
		int i = 0;
		for(Node currentOldChild : oldChildren)
		{	
			int newWidth = (int)(currentOldChild.getPercentage() * maxWidth +1);
			//System.out.println(tabs + i + ", " + newWidth + ": " + currentOldChild);
			
			Node currentNewChild = new Node(currentOldChild);
			
			prune(currentOldChild, currentNewChild, newWidth, tabs + "    ");
			int added = currentNewChild.calculateNumLeaves();
			i += added;
			if(i > maxWidth)
			{
				i -= added;
				break;
			}
			newNode.addChild(currentNewChild);
		}
		return;
	}
	
	public void sortByFrequency()
	{
		sortByFrequency(root);
	}
	
	private void sortByFrequency(Node n)
	{
		List<Node> nodes = n.getChildren();
		if(nodes == null)
			return;
		
		Collections.sort(nodes);
		
		for(Node n2 : n.getChildren())
			sortByFrequency(n2);
	}

	public int getFrequency(String s)
	{
		if(frequencyCache.containsKey(s))
			return frequencyCache.get(s).intValue();
		
		int frequency = 0;
		int currentIndex = 0;
		s = s.toLowerCase();
		while((currentIndex = textLowerCase.indexOf(s, currentIndex)) >= 0)
		{
			frequency++;
			currentIndex += s.length();
		}
		
		frequencyCache.put(s, new Integer(frequency));
		
		return frequency;
	}
	
	public Node getRoot()
	{
		return root;
	}
	
	public int getNumLeaves()
	{
		return root != null ? root.getNumLeaves() : 0;
	}
	
	public String getQuery()
	{
		return query;
	}

	public int getMatchCount()
	{
		return matchCount;
	}
	
	private static boolean isEndOfSegment(char c)
	{
		return c == '.' || c == '?' || c == '!';
	}
	
	public static boolean isWhiteSpace(char c)
	{
		return c == ' ' || c == '\t' || c == '\n';
	}

	public static boolean isPunctuation(char c)
	{
		for(char p : punctuation)
			if(c == p)
				return true;
		return false;
	}
	
	public class Node implements Comparable<Node>
	{
		private Segment text; //the node's text as a segment
		
		private String phrase;
		
		private List<Node> children;
		private Node parent;
	
		private Node unprunedOriginal;
		
		private int numLeaves = 0;

		private NodeBoxPair nodeBoundingBox;
	
		private double percentage;
		
		private Node(boolean isLeaf)
		{
			if(!isLeaf)
				children = new LinkedList<Node>();
		}
		
		public Node(int textStart, int textEnd, boolean isLeaf)
		{
			this(isLeaf);
			text = new Segment(textStart, textEnd);
		}
		
		public Node(Segment s, boolean isLeaf)
		{
			this(isLeaf);
			text = new Segment(s);
		}
		
		public Node(Node n)
		{
			this(n.isLeaf());
			text = new Segment(n.getTextSegment());
		}
		
		public int compareTo(Node n)
		{
			if(this.percentage > n.percentage)
				return -1;
			else if(this.percentage < n.percentage)
				return 1;
			else
				return 0;
		}
		
		private int calculateNumLeaves()
		{
			if(children == null)
			{
				numLeaves = 1;
				return numLeaves;
			}
			
			numLeaves = 0;
			for(Node n : children)
				numLeaves += n.calculateNumLeaves();
			if(numLeaves == 0) 
				numLeaves = 1;
			return numLeaves;
		}
		
		private void calculatePercentages(int width)
		{
			percentage = ((double)numLeaves) / width;
			
			if(children != null)
			{
				for(Node n : children)
				{
					n.calculatePercentages(numLeaves);
				}
			}
		}
		
		public int getNumLeaves()
		{
			return numLeaves;
		}
	
		public double getPercentage()
		{
			return percentage;
		}
		
		public Segment getTextSegment()
		{
			return text;
		}
		
		public void addChild(Node n)
		{
			this.children.add(n);
			n.setParent(this);
		}
		
		public List<Node> getChildren()
		{
			return children;
		}

		public void setChildren(List<Node> children)
		{
			this.children = children;
		}
		
		public boolean isLeaf()
		{
			return children == null;
		}
		
		public String toString()
		{
			if(phrase != null)
				return phrase;
			return this.text.toString();
		}

		public Node getUnprunedOriginal()
		{
			if(unprunedOriginal == null)
				return this;
			
			return unprunedOriginal;
		}

		public void setUnprunedOriginal(Node unprunedOriginal)
		{
			this.unprunedOriginal = unprunedOriginal;
		}

		public Node getParent()
		{
			return parent;
		}
		
		public void setParent(Node parent)
		{
			this.parent = parent;
		}
		
		public void setPhrase(String phrase)
		{
			this.phrase = phrase;
		}
		public String getPhrase()
		{
			return phrase;	
		}

		public NodeBoxPair getBoundingBox()
		{
			return nodeBoundingBox;
		}

		public void setNodeBoundingBox(NodeBoxPair nodeBoundingBox)
		{
			this.nodeBoundingBox = nodeBoundingBox;
		}
	}

	public class Segment implements Comparable<Segment>
	{
		private int start, end;
	
		public Segment(int start, int end)
		{
			this.start = start;
			this.end = end;
		}
		
		public Segment(int start)
		{
			this(start, start);
		}
		
		public Segment(Segment s)
		{
			this(s.start, s.end);
		}
		
		public Segment()
		{
			this(0, 0);
		}
		
		public Segment consumeNextToken()
		{
			Segment t = getNextToken(start);
			if(t == null)
				return null;
			start += t.length() + (t.start - this.start);
			return t;
		}
		
		public Segment getNextToken()
		{
			return getNextToken(start);
		}
		
		public Segment getNextToken(int start)
		{
			if(this.length() == 0)
				return null;
			
			int i = start;
			char c = text.charAt(i);
			Segment token = new Segment();

			while(isWhiteSpace(c))
			{
				if(i >= end)
					return null;
				i++;
				c = text.charAt(i);
			}
			
			token.start = i;
			
			do{
				i++;
				c = text.charAt(i);
			}while(!isWhiteSpace(c) && !isPunctuation(c) && i < end);
			
			token.end = i;
			
			return token;
		}
		
		public int length()
		{
			return this.end - this.start;
		}
		
		public String toString()
		{
			return text.substring(start, end);//.trim();
		}

		public boolean equals(Object o)
		{
			if(o == null)
				return false;
			
			if(o instanceof String)
				return this.toString().equals(o);
			
			Segment s = (Segment)o;
			if(this.length() != s.length())
				return false;
			else
				return compareTo(s) == 0;
		}
		
		public int compareTo(Segment t2)
		{
			int i1 = this.start;
			int i2 = t2.start;
			
			while(i1 < this.end && i2 < t2.end)
			{
				if(textLowerCase.charAt(i1) != textLowerCase.charAt(i2))
				{
					if(textLowerCase.charAt(i1) < textLowerCase.charAt(i2))
						return -1;
					else
						return 1;
				}
				i1++;
				i2++;
			}
			
			if(this.length() > t2.length())
				return -1; //shorter one first;
			else if(this.length() < t2.length())
				return 1;
			else
				return 0;
		}
	}
	
	public static void main(String[] args) throws IOException
	{
	}
}
