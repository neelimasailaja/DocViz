package WordTreePack;



import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.*;

import javax.swing.Timer;
import javax.swing.*;

import edu.smu.tspell.wordnet.AdjectiveSynset;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import WordTreePack.WordTree.Node;
import WordTreePack.WordTree;
import HTrail.LinkedListIterator;
import HTrail.LinkedList1;
import HTrail.ListNode;
import HTrail.Painting;

//import HistoryTrail.ListNode;

public class WordTreePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 12;
	private LinkedList<NodeBoxPair> nodeBoundingBoxes;
    private int flag=0;
    private int flag1=1,push=0;
	private int leafHeight = 16;
	private String text;
	private String text1;
	private String textLowerCase;
	private Node currentRoot;
	private NodeBoxPair selNBP = null;
	private NodeBoxPair clickedNBP = null;
	private NodeBoxPair rootNBP = null;
	public static HashSet<String> stopset;
	private BufferedImage bi;
	private WordTree unprunedTree;	
	private static WordTreeView wordTreeView;	
	private final double fontscale = 1;
	public static Painting p;
	public static JPanel p1=new JPanel();
	public static Integer lf;
	public static String presentword;
    
	private Font[] fonts =
	{
		new Font("Serif", Font.PLAIN, (int)( 1 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)( 2 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)( 3 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)( 4 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)( 5 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)( 6 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)( 7 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)( 8 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)( 9 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(10 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(11 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(12 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(13 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(14 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(15 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(16 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(17 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(18 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(19 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(20 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(21 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(22 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(23 * fontscale)),
		new Font("Serif", Font.PLAIN, (int)(24 * fontscale))
	};
	
	public WordTreePanel(WordTreeView parent)
	{
		setBackground(Color.white);
		this.wordTreeView = parent;
        makestopwords();
		nodeBoundingBoxes = new LinkedList<NodeBoxPair>();
		addMouseMotionListener(new SimpleListener());
		addMouseListener(new ClickListener());
		p= new Painting(wordTreeView);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		//this.add(p);
		p.setPreferredSize(new Dimension(200,1500));
		p.setMaximumSize(new Dimension(200,1500));
		this.setPreferredSize(new Dimension(700,400));
		this.setMaximumSize(new Dimension(700,400));
		//p.setBorder(BorderFactory.createTitledBorder("title"));
		p.setBackground(Color.white);
		this.setBackground(Color.white);
		redraw();
	}
	
	public void historypush(String s1){
		

        	//System.out.println(s1+s1+s1+s1+"historypush is working");
        	wordTreeView.p.makeLL(s1);
	}
	
	public TreeStats query(Integer push,String text, String textLowerCase, String query, Integer lf)
	{
		   
        this.lf=lf;
		this.text=text;
		this.text1=text;
		this.textLowerCase=textLowerCase;
		unprunedTree = new WordTree(flag,text, textLowerCase, query);
		int maxWidth = wordTreeView.getMaxTreeWidth();
		WordTree prunedTree = unprunedTree.prune(maxWidth);
		
		if(currentRoot != null)
		{
			
		}
		//theList.insert( query, theItr );
        //theList.printList( theList );
        //theItr.advance( );
		currentRoot = prunedTree.getRoot();
		if(push==0)
		{
		historypush(query);
		presentword=query;
		}
		//if(lf==0)
		synonyms(query);
		redraw();
		repaint();
		
		return new TreeStats();
	}
	
	private void synonyms(String word)
	{
		String wordForm = word;
		 StringBuffer syns = new StringBuffer(); 
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets(wordForm);
		Synset[] nsynsets = database.getSynsets(wordForm, SynsetType.NOUN); 
		Synset[] asynsets = database.getSynsets(wordForm, SynsetType.ADJECTIVE);
		//Synset[] asatsynsets = database.getSynsets(wordForm, SynsetType.ADJECTIVE_SATELLITE);
		//Synset[] advsynsets = database.getSynsets(wordForm, SynsetType.ADVERB);
		Synset[] vsynsets = database.getSynsets(wordForm, SynsetType.VERB);
       ArrayList<String> wordslist = new ArrayList<String>();
		if (synsets.length > 0)
		{
			NounSynset nsynsets1; 
			AdjectiveSynset asynsets1;
			//AdjectiveSatelliteSynset asatsynsets1;
			//AdverbSynset advsynsets1;
			VerbSynset vsynsets1;
			if (nsynsets.length > 0)
			{
				NounSynset[] hyponyms;
				NounSynset[] hypernyms;	
				NounSynset[] instancehyponyms;
				NounSynset[] instancehypernyms;
				NounSynset[] memberholonyms;
				NounSynset[] membermeronyms;
				NounSynset[] partmeronyms;
				NounSynset[] partholonyms;
				NounSynset[] substanceholonyms;
				NounSynset[] substancemeronyms;
				NounSynset[] topics;
				NounSynset[] fullhyponyms;
				
				for (int i = 0; i < synsets.length; i++)
				{
					String[] word1=synsets[i].getWordForms();
					for(int jt=0;jt<word1.length;jt++)
													{
					wordslist.add(word1[jt].toLowerCase());;
					//System.out.println("Synset"+i+" "+" : "+word1[jt]);
													}
				
				}
				
				for (int i = 0; i < nsynsets.length; i++)
				{
					nsynsets1=(NounSynset)(nsynsets[i]);
					
					//System.out.println();
					//System.out.println("Noun "+" "+nsynsets1);
					int count=0;
					hyponyms = nsynsets1.getHyponyms();
					String[] words= new String[100];
					if(hyponyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Hyponyms :");
						for(int j=0;j<hyponyms.length;j++)
						{
							String[] word1=hyponyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
							{
								wordslist.add(word1[jt].toLowerCase());
							//System.out.println("Hyponym"+j+" "+" : "+word1[jt]);
							}
							
						}
						fullhyponyms = new NounSynset[1000];
						for(int j=0;j<hyponyms.length;j++)
						{
							NounSynset temp =(NounSynset)(hyponyms[j]);
							NounSynset[] tempfullhyponyms= temp.getHyponyms();
							
							if(tempfullhyponyms.length>0)
							{
								//System.out.println(tempfullhyponyms.length);
								//System.out.println(tempfullhyponyms[0]);
							for(int jtemp=0;jtemp<tempfullhyponyms.length;jtemp++)
							{
								String[] word1=tempfullhyponyms[jtemp].getWordForms();
								fullhyponyms[count]=tempfullhyponyms[jtemp];
								for(int jt=0;jt<word1.length;jt++)
								{
										wordslist.add(word1[jt].toLowerCase());
										//System.out.println("FullHyponym"+j+" "+" : "+word1[jt]);
								}
								//System.out.println("Full Hyponym"+jtemp+" "+" : "+fullhyponyms[count]);
								count++;

							}
							}
							
						}
					}
					
					hypernyms = nsynsets1.getHypernyms();
					if(hypernyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Hypernyms :");
						for(int j=0;j<hypernyms.length;j++)
						{
							String[] word1=hypernyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Hypernym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Hypernym"+j+" "+" : "+hypernyms[j]);
							
						}
					}
					
					instancehyponyms = nsynsets1.getInstanceHyponyms();
					if(instancehyponyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Instance Hyponyms :");
						for(int j=0;j<instancehyponyms.length;j++)
						{
							String[] word1=instancehyponyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Instance Hyponym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Instance Hyponym"+j+" "+" : "+instancehyponyms[j]);
							
						}
					}
					
					instancehypernyms = nsynsets1.getInstanceHypernyms();
					if(instancehypernyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Instance Hypernyms :");
						for(int j=0;j<instancehypernyms.length;j++)
						{
							String[] word1=instancehypernyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Instance Hypernym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Instance Hypernym"+j+" "+" : "+instancehypernyms[j]);
							
						}
					}
					
					memberholonyms = nsynsets1.getMemberHolonyms();
					if(memberholonyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Member Holonyms :");
						for(int j=0;j<memberholonyms.length;j++)
						{
							String[] word1=memberholonyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Member Holonym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Member Holonym"+j+" "+" : "+memberholonyms[j]);
							
						}
					}
					
					membermeronyms = nsynsets1.getMemberMeronyms();
					if(membermeronyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Member Meronyms :");
						for(int j=0;j<membermeronyms.length;j++)
						{
							String[] word1=membermeronyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("HMember Meronym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Member Meronym"+j+" "+" : "+membermeronyms[j]);
							
						}
					}
					
					partmeronyms = nsynsets1.getPartMeronyms();
					if(partmeronyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Part Meronyms :");
						for(int j=0;j<partmeronyms.length;j++)
						{
							String[] word1=partmeronyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Part Meronym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Part Meronym"+j+" "+" : "+partmeronyms[j]);
							
						}
					}
					
					partholonyms = nsynsets1.getPartHolonyms();
					if(partholonyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Part Holonyms :");
						for(int j=0;j<partholonyms.length;j++)
						{
							String[] word1=partholonyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Part Holonym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Part Holonym"+j+" "+" : "+partholonyms[j]);
							
						}
					}
					
					substanceholonyms = nsynsets1.getSubstanceHolonyms();
					if(substanceholonyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Substance Holonyms :");
						for(int j=0;j<substanceholonyms.length;j++)
						{
							String[] word1=substanceholonyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Substance Holonym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Substance Holonym"+j+" "+" : "+substanceholonyms[j]);
							
						}
					}
					
					substancemeronyms = nsynsets1.getSubstanceMeronyms();
					if(substancemeronyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Substance Meronyms :");
						for(int j=0;j<substancemeronyms.length;j++)
						{
							String[] word1=substancemeronyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Substance Meronym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Substance Meronym"+j+" "+" : "+substancemeronyms[j]);
							
						}
					}
					
					topics = nsynsets1.getTopics();
					if(topics.length>0)
					{	
						//System.out.println();
						//System.out.println("Topics :");
						for(int j=0;j<topics.length;j++)
						{
							String[] word1=topics[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							System.out.println("Topic"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Topic"+j+" "+" : "+topics[j]);
							
						}
					}
				}
				}
			if (vsynsets.length > 0)
			{
				VerbSynset[] troponyms;
				VerbSynset[] hypernyms;	
				VerbSynset[] outcomes;
				VerbSynset[] verbgroup;
				
				for (int i = 0; i < vsynsets.length; i++)
				{
					vsynsets1=(VerbSynset)(vsynsets[i]);
					
					//System.out.println();
					//System.out.println("Verb "+" "+vsynsets1);
					
					troponyms = vsynsets1.getTroponyms();
					if(troponyms.length>0)
					{	
						System.out.println();
						System.out.println("Troponyms :");
						for(int j=0;j<troponyms.length;j++)
						{
							String[] word1=troponyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Troponym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Troponym"+j+" "+" : "+troponyms[j]);
							
						}
					}
					
					hypernyms = vsynsets1.getHypernyms();
					if(hypernyms.length>0)
					{	
						//System.out.println();
						//System.out.println("Hypernyms :");
						for(int j=0;j<hypernyms.length;j++)
						{
							String[] word1=hypernyms[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Hypernym"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Hypernym"+j+" "+" : "+hypernyms[j]);
							
						}
					}
					
					outcomes = vsynsets1.getOutcomes();
					if(outcomes.length>0)
					{	
						//System.out.println();
						//System.out.println("Outcomes :");
						for(int j=0;j<outcomes.length;j++)
						{
							String[] word1=outcomes[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Outcome"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Outcome"+j+" "+" : "+outcomes[j]);
							
						}
					}
					
					verbgroup = vsynsets1.getVerbGroup();
					if(verbgroup.length>0)
					{	
						//System.out.println();
						//System.out.println("Verb Group :");
						for(int j=0;j<verbgroup.length;j++)
						{
							String[] word1=verbgroup[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Verb Group"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Verb Group"+j+" "+" : "+verbgroup[j]);
							
						}
					}
				}
				
			}
			
			if (asynsets.length > 0)
			{
				AdjectiveSynset[] related;
				AdjectiveSynset[] similar;	
				
				for (int i = 0; i < asynsets.length; i++)
				{
					asynsets1=(AdjectiveSynset)(asynsets[i]);
					
					//System.out.println();
					//System.out.println("Adjective "+" "+asynsets1);
					
					related = asynsets1.getRelated();
					if(related.length>0)
					{	
						//System.out.println();
						//System.out.println("Related :");
						for(int j=0;j<related.length;j++)
						{
							String[] word1=related[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Related"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Related"+j+" "+" : "+related[j]);
							
						}
					}
					
					similar = asynsets1.getSimilar();
					if(similar.length>0)
					{	
						//System.out.println();
						//System.out.println("Similar :");
						for(int j=0;j<similar.length;j++)
						{
							String[] word1=similar[j].getWordForms();
							for(int jt=0;jt<word1.length;jt++)
															{
							wordslist.add(word1[jt].toLowerCase());;
							//System.out.println("Similar"+j+" "+" : "+word1[jt]);
															}
							//System.out.println("Similar"+j+" "+" : "+similar[j]);
							
						}
					
					}
					
				}
				//System.out.println(wordslist);
			}
			/*
			System.out.println("The following synsets contain '" +
					wordForm + "' or a possible base form " +
					"of that text:");
			for (int i = 0; i < synsets.length; i++)
			{
				System.out.println("");
				WordSense temp = new WordSense(wordForm,synsets[i]);
				Synset synsetsense = temp.getSynset();
				
				WordSense[] tr = synsets[i].getDerivationallyRelatedForms(wordForm); 
				for (int j = 0; j < tr.length; j++)
				{
					//syns.append(tr[j]);
					System.out.println("Derivationally Related words "+j+" : "+tr[j]);
				}
				//System.out.println("Printing synset of sense"+synsetsense);
				String[] wordForms = synsets[i].getWordForms();
				System.out.println();
				for (int j = 0; j < wordForms.length; j++)
				{
					syns.append(wordForms[j]);
					
					System.out.print((j > 0 ? ", " : "") +
							wordForms[j]);
				}
				syns.append(synsets[i].getDefinition());
				System.out.println(": " + synsets[i].getDefinition());
			}
			*/
			
			System.out.println(wordslist);
		}
		else
		{
			System.err.println("No synsets exist that contain " +
					"the word form '" + wordForm + "'");
		}
	
		if(lf==0)
		((synList) wordTreeView.synpane).refreshsyn(wordslist.toArray(),word,stopset,0);
		else if(lf==1)
			((synList) wordTreeView.synpane).refreshsyn(wordslist.toArray(),word,stopset,1);
	}
	
	public void makestopwords()
	{
		File f = new File("/Users/neelima/stopWords.txt");
		Scanner s;
		StringBuilder sb = new StringBuilder();
		try{
		s=new Scanner(f);

		while(s.hasNext())
		{
		sb.append(s.nextLine()+" ");
		}
		} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		String stopwords=sb.toString();
		sb.append(stopwords);
		stopwords=sb.toString();
		String[] stopwordsarray = stopwords.split("[ \n\t\r.,;:!?(){}]");
		  List<String> list = Arrays.asList(stopwordsarray);
		    stopset = new HashSet<String>(list);
	}
	
	private void attachNewTreeToRoot(Node node)
	{   
		node.setPhrase(null);
		String newText = node.toString(); 
		synonyms(newText);
		String newText1 = node.toString();
		Node n = node.getUnprunedOriginal();
		while((n = n.getParent()) != null)
		{
			newText1 = n.toString();
		}
		historypush(node.toString());
		//theList.insert( newText1, theItr );
        //theList.printList( theList );
        //theItr.advance( );
		unprunedTree = new WordTree(flag1,unprunedTree, node);
		int maxWidth = wordTreeView.getMaxTreeWidth();
		WordTree prunedTree = unprunedTree.prune(maxWidth);
		currentRoot = prunedTree.getRoot();
		redraw();
		repaint();
	}
	
	public void clear()
	{
		query(0,"", "", "",0);
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		{
			g.drawImage(bi, 0, 0, null);
			if(selNBP != null)
			{
				g.setColor(Color.GREEN);
				g.setFont(getFont(selNBP.getFontSize()));
				Point p = selNBP.getTextOrigin();
				g.drawString(selNBP.getNode().toString(), (int)p.getX(), (int)p.getY());
			}
		}
		
	}

	public void redraw()
	{
		selNBP = null;
		this.bi = null;
		this.bi = draw(currentRoot, nodeBoundingBoxes);
	}
	
	private static final int imgType = BufferedImage.TYPE_BYTE_GRAY;

	private BufferedImage draw(Node node, LinkedList<NodeBoxPair> nodeBoundingBoxes)
	{
		if(nodeBoundingBoxes != null)
			nodeBoundingBoxes.clear();
		
		BufferedImage bi;
		
		if(node == null)
		{
			bi = new BufferedImage(300, 100, imgType);
			Graphics g = bi.getGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
			g.setColor(Color.black);
			
			return bi;
		}
		
		int height = (node.getNumLeaves()) * leafHeight ;
		if(height < wordTreeView.getMinTreeHeight())
			height = wordTreeView.getMinTreeHeight();
		
		if(height <= leafHeight*4) height = 300; 
		int width;
		Graphics2D dummyG = new BufferedImage(1, 1, imgType).createGraphics();
		width = drawNode(dummyG, node, 3, height/2, 11, null);
		if(width < wordTreeView.getMinTreeWidth())
			width = wordTreeView.getMinTreeWidth();
		
		bi = null;
		System.gc();
		
		try{
			bi = new BufferedImage(width, height, imgType);
		}catch(OutOfMemoryError e)
		{
			System.err.printf("Can't create image buffer: out of memory. Size: %d, %d\n", 
					new Integer(width), new Integer(height));
			return bi;
		}
		
		
		Graphics2D g = (Graphics2D)bi.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

		g.setFont(getFont(12));
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		drawNode(g, node, 3, (height/2) - 100, 11, nodeBoundingBoxes);
		setPreferredSize(new Dimension(width, height*2));
		return bi;
	}
	
	
	
	

	private int drawNode(Graphics2D g, Node node, int x, int y, int currMinFontSize, LinkedList<NodeBoxPair> nodeBoundingBoxes)
	{
		int fontSize;
		if(node == currentRoot)
		{
			fontSize = 20;
		}
		else
		{
			if(!node.isLeaf())
			{
				int freq = node.getNumLeaves();
				fontSize = (int)(4 * Math.sqrt(freq) + currMinFontSize);
			}
			else
			{
				fontSize = currMinFontSize;
			}
		}
		g.setFont(getFont(fontSize));
		
		String s = node.toString();
		Rectangle2D strRect = g.getFontMetrics().getStringBounds(s, g);
		int textY = y + (int)(strRect.getHeight() / 2);
		g.drawString(s, x, textY+100);
		
		strRect.setRect(x, y+100 - (int)(strRect.getHeight() / 2), strRect.getWidth(), strRect.getHeight());
		NodeBoxPair nbp = new NodeBoxPair(node, strRect, new Point(x, textY), fontSize);
		if(nodeBoundingBoxes != null)
		{
			nodeBoundingBoxes.add(nbp);
			if(node == currentRoot)
			{
				rootNBP = nbp;
			}
		}
		
		int endX = x + (int)strRect.getWidth() + 5;
		int startY = y - (node.getNumLeaves() * leafHeight) / 2;

		if(node.getChildren() == null || node.getChildren().size() == 0)
			return endX;
		
		int maxWidth = 0;
		for(Node n : node.getChildren())
		{
			int childHeight = n.getNumLeaves() * leafHeight;
			
			int childY = startY + childHeight / 2;
			int levelGap = (int)Math.sqrt(node.getNumLeaves() * leafHeight) * 3 + 4;
			int childX = endX + levelGap + 5;
			
			g.setColor(Color.BLACK);
			drawCurve(g, endX, y+100, childX-5, childY+100, 0.5);
			g.setColor(Color.black);
			
			int newMinFontSize = currMinFontSize - 5;
			if(newMinFontSize < 6) currMinFontSize = 6;
			int width = drawNode(g, n, childX, childY, currMinFontSize, nodeBoundingBoxes);
			if(width > maxWidth)
				maxWidth = width;
			
			startY += childHeight;
		}
		return maxWidth;
	}
	
	private void drawCurve(Graphics2D g, int x1, int y1, int x2, int y2, double curvature)
	{	
		double ctrlx1 = x1 + (x2 - x1) * curvature;
		double ctrly1 = y1;
		
		double ctrlx2 = x2 - (x2 - x1) * curvature;
		double ctrly2 =  y2;
		
		CubicCurve2D.Double curve = new CubicCurve2D.Double(x1, y1, ctrlx1, ctrly1,
															ctrlx2, ctrly2, x2, y2);
		g.draw(curve);
	}

	
	private class SimpleListener implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent e)
		{		
			for(NodeBoxPair nbp : nodeBoundingBoxes)
			{
				if(nbp.getRect().contains(e.getPoint()))
				{
					selNBP = nbp;
					//repaint();
					return;
				}
			}
			if(selNBP != null)
			{
				selNBP = null;
				//repaint();
			}
		}
		
		public void mouseDragged(MouseEvent e)
		{}
	}
	
	private class ClickListener implements MouseListener
	{
		
		public void mousePressed(MouseEvent e)
		{
			
			
			if(selNBP != null)
			{
				clickedNBP = selNBP;
				
				if(selNBP.getNode() == currentRoot)
				{
					return;
				}
				
				Node clickedOriginal = clickedNBP.getNode().getUnprunedOriginal();
				WordTree newTree = unprunedTree.prune(wordTreeView.getMaxTreeWidth(), clickedOriginal);

				attachNewTreeToRoot(newTree.getRoot());
				redraw();
				repaint();

		
			}
		}
		
		
		public void mouseExited(MouseEvent e)
		{
			selNBP = null;
			repaint();
		}
		
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
	}

	private Font getFont(int size)
	{
		if(size >= fonts.length) 
			return fonts[fonts.length - 1];
		return fonts[size - 1];
	}
	
	public int getLeafHeight()
	{
		return leafHeight;
	}
	
	/**
	 * Associates a WordTree node with
	 * a bounding box for display,
	 * and for mouse events
	 *
	 */
	public static class NodeBoxPair
	{
		private Node node;
		private Rectangle2D rect;
		private Point textOrigin;
		private int fontSize;
		public NodeBoxPair(Node node, Rectangle2D rect, Point textOrigin, int fontSize)
		{
			this.node = node;
			this.rect = rect;
			this.textOrigin = textOrigin;
			this.fontSize = fontSize;
			node.setNodeBoundingBox(this);
		}
		public Node getNode()
		{
			return node;
		}
		public Rectangle2D getRect()
		{
			return rect;
		}
		public Point getTextOrigin()
		{
			return textOrigin;
		}
		public int getFontSize()
		{
			return fontSize;
		}
	}
	
	public class TreeStats
	{
		private int visible, branchTotal, treeTotal;
		public String query;

		public TreeStats()
		{
			this(currentRoot  != null ? currentRoot.getNumLeaves() : 0, 
				 currentRoot  != null ? currentRoot.getUnprunedOriginal().getNumLeaves() : 0, 
				 unprunedTree != null ? unprunedTree.getNumLeaves() : 0);
			this.query=presentword;
		}
		
		public TreeStats(int visible, int branchTotal, int treeTotal)
		{
			super();
			this.visible = visible;
			this.branchTotal = branchTotal;
			this.treeTotal = treeTotal;
		}

		public int getVisible()
		{
			return visible;
		}

		public int getBranchTotal()
		{
			return branchTotal;
		}
		
		public int getTreeTotal()
		{
			return treeTotal;
		}

		public double getPercent()
		{
			if(getBranchTotal() == 0)
				return 0;
			return ((double)getVisible()) / getBranchTotal();
		}
	}
	public static String getTestText() throws IOException
	{
		File f = new File("/Users/neelima/try.txt");
		Scanner scan = new Scanner(f);
		StringBuilder sb = new StringBuilder((int)f.length());
		while(scan.hasNext())
		{
			sb.append(scan.nextLine() + " ");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException
	{   
		
       /*
		String s = getTestText();
		System.out.println("done toString-ing");
		System.out.println("constructing word tree");
		WordTree wt = new WordTree(0,s,s.toLowerCase(),"god");
		wt.sortByFrequency();
		makeFrame(wt);
		*/
	}
	/*
	private static void makeFrame(WordTree wt)
	{
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(1200, 1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		WordTreePanel wtp = new WordTreePanel(null);
		//wtp.setWordTree(wt);
		JScrollPane jsp = new JScrollPane(wtp);
		frame.add(jsp);
		frame.pack();
		frame.setVisible(true);
		
	}*/
}
