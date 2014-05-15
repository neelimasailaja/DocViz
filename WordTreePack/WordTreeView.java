package WordTreePack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import HTrail.Painting;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.io.File;

import java.io.IOException;

import java.io.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.management.modelmbean.XMLParseException;
import javax.swing.*;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import WordTreePack.WordTreePanel.TreeStats;


public class WordTreeView extends JPanel implements MouseListener,MouseMotionListener
{

	private static final long serialVersionUID = 11;
	public WordTreePanel wordTreePanel;
	public JScrollPane wordTreeScroller;
	public JScrollPane Tab2Scroller;
	public JPanel synpane;
	private String text;
	private String textLowerCase;
	private int push=0;
	public Painting p=new Painting(this);
	public static JPanel p1=new JPanel();
	public static String filename;
	public static Integer lf;
	public JPanel tab1= new JPanel();
	public JPanel tab2= new JPanel();
	public static HashMap<String, Integer> counts;
	public static String presentword=" ";
	public JLabel presentquery;
	//public static String[] docarray = [" ", " ", " ", " "];
	String	docarray[] =
		{
			"1",
			"1",
			"1",
			"1"
		};
	public JList listboxdoc = new JList(docarray);
	
	public WordTreeView(String text, String textLowerCase, String query, StringBuilder sb1,ArrayList<String> al1,TreeSet<String> set1,ArrayList<String> sent1, HashMap<String, Integer> counts1)
	{
		//filename=filen;
		synpane=new synList(al1,set1,sent1,this,counts1);
		this.counts=counts1;
		setPreferredSize(new Dimension(1024, 768));
		//System.out.println("Speedtest");
		//System.out.flush();
		if(text == null || text.equals("") || textLowerCase == null || textLowerCase.equals(""))
		{
			{
				
		//StringBuilder sb1 = new StringBuilder();
		/*
		try { 

	 	    DocumentBuilderFactory docBuilderFactory1 = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder1 = docBuilderFactory1.newDocumentBuilder();
	            Document doc1 = docBuilder1.parse (new File(filename));

	            // normalize text representation
	            doc1.getDocumentElement ().normalize ();
	            System.out.println ("Root element of the doc is " + 
	                 doc1.getDocumentElement().getNodeName());


	            NodeList listOfPersons1 = doc1.getElementsByTagName("document");
	            int totalPersons1 = listOfPersons1.getLength();
	            System.out.println("Total no of people : " + totalPersons1);

	            for(int s=0; s<listOfPersons1.getLength() ; s++){


	                org.w3c.dom.Node firstPersonNode1 = listOfPersons1.item(s);
	                if(firstPersonNode1.getNodeType() == Node.ELEMENT_NODE){


	                    Element firstPersonElement1 = (Element)firstPersonNode1;

	                    //-------
	                      NodeList firstNameList1 = firstPersonElement1.getElementsByTagName("docText");

	                    Element firstNameElement1 = (Element)firstNameList1.item(0);

	                    NodeList textFNList1 = firstNameElement1.getChildNodes();
	                    String text1 = textFNList1.item(0).getNodeValue().trim();
	                    String[] words1 = text1.split("[ \n\t\r,;:(){}]");
			            for (int i = 0; i < words1.length; i++) {
			            	if ((words1[i].length() > 0)&&(words1[i] != null)&&(words1[i].trim().length() != 0))
	                               sb1.append(words1[i]+" ");	
			            }
	                }
	            }
		}catch (IOException ex){ 
			ex.printStackTrace(); 
		}

			catch (SAXParseException err) {
	        System.out.println ("** Parsing error" + ", line " 
	             + err.getLineNumber () + ", uri " + err.getSystemId ());
	        System.out.println(" " + err.getMessage ());

	        }catch (SAXException e) {
	        Exception x = e.getException ();
	        ((x == null) ? e : x).printStackTrace ();

	        }catch (Throwable t) {
	        t.printStackTrace ();
	        }
		
				String s = sb1.toString();
				sb1.append(s);
				*/
				this.text = sb1.toString();
			}
			
			
			this.textLowerCase = this.text.toLowerCase();
		}
		else
		{
			this.text = text;
			this.textLowerCase = textLowerCase;
		}
		Box horizontalBox1 = Box.createVerticalBox();
		Box horizontalBox11 = Box.createHorizontalBox();
		Box horizontalBox12 = Box.createHorizontalBox();
		wordTreePanel = new WordTreePanel(this);
		wordTreePanel.setPreferredSize(new Dimension(500,300));
		wordTreeScroller = new JScrollPane(wordTreePanel);
		wordTreeScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		wordTreeScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		wordTreeScroller.setPreferredSize(new Dimension(500,300));
		synpane.setPreferredSize(new Dimension(300,620));
		//p1.setPreferredSize(new Dimension(400,20));
		p.setPreferredSize(new Dimension(200,300));
		p.addMouseListener(this);
		p.addMouseMotionListener(this);
		synpane.setBorder(BorderFactory.createTitledBorder("Related Words"));
		JPanel sample=new JPanel();
		JPanel sample1=new JPanel();
		presentquery = new JLabel("Present query : "+presentword);
		//sample.setPreferredSize(new Dimension(40,600));
		sample1.setPreferredSize(new Dimension(100,600));
		//horizontalBox12.add(p);
		//horizontalBox1.add(horizontalBox12);
		//horizontalBox1.add(p1);
		//horizontalBox1.add(wordTreeScroller);
		JPanel sample2=new JPanel();
		//sample2.add(horizontalBox1);
		JTabbedPane jtp1 = new JTabbedPane();
		jtp1.addTab("HISTORY", p);
		jtp1.addTab("VIZ", wordTreeScroller);
		//jtp1.addTab("DOC", );
		//sample.add(sample1);
		//sample.add(presentquery);
		sample.add(synpane);
		sample.setPreferredSize(new Dimension(500,620));
		//horizontalBox11.add(sample1);
		//horizontalBox11.add(synpane);
		//sample2.setLayout(new BorderLayout());
		//sample2.setPreferredSize(new Dimension(500,10));
		sample2.add(presentquery);
		horizontalBox1.add(sample2);
		horizontalBox1.add(sample);
		//horizontalBox11.add(presentquery);
		horizontalBox11.add(horizontalBox1);
		horizontalBox11.add(jtp1);
		tab1.add(horizontalBox11);
		tab2.add(listboxdoc);
		Tab2Scroller=new JScrollPane(tab2);
		Tab2Scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Tab2Scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		Tab2Scroller.setPreferredSize(new Dimension(500,300));
		//JTabbedPane jtp = new JTabbedPane();
		//jtp.addTab("VIZ", tab1);
		jtp1.addTab("DOC", Tab2Scroller);
		//sample1.add(comp);
		//jtp.addTab("DOC", Tab2Scroller);
		this.add(tab1);

		if(query != null && !query.equals(""))
		{

			query(push,query,lf);
			getRootPane().validate();
			getRootPane().repaint();
		}
	}
	
	public WordTreeView(StringBuilder sb1,ArrayList<String> al1,TreeSet<String> set1,ArrayList<String> sent1,HashMap<String, Integer> counts1)
	{    
		this( null, null, null,sb1,al1,set1,sent1,counts1);
		//System.out.println(filename1);
		
		
	}

    public void setfilename(String filename1)
    {
    	filename=filename1;
    }
		
	public void resetScroller()
	{
		wordTreeScroller.setViewportView(null);
		wordTreeScroller.setViewportView(wordTreePanel);
	}
	
	protected int getMaxTreeWidth()
	{
		return wordTreeScroller.getViewport().getHeight() / wordTreePanel.getLeafHeight();
	}
	
	protected int getMinTreeHeight()
	{
		return wordTreeScroller.getViewport().getHeight();
	}
	
	protected int getMinTreeWidth()
	{
		return wordTreeScroller.getViewport().getWidth();
	}
	
	protected void clearView()
	{
		wordTreePanel.clear();
	}
    
	
	protected void loadBookmark(Reader in) throws IOException, ParserConfigurationException, SAXException, XMLParseException
	{
		Scanner scan = new Scanner(in);
		if(scan.hasNextLine())
		{
			String q = scan.nextLine();
			query(push, q,lf);
	
		}		
		scan.close();
	}
	
	public TreeStats query(Integer push,String query,Integer lf)
	{
		//System.out.println(query+" "+query+" "+query);
		//displaydocs(docs);
		TreeStats ts = wordTreePanel.query(push, text, textLowerCase, query,lf);
		presentword= ts.query;
		System.out.println("Presentword :"+ts.query);
		System.out.println("Presentword :"+presentword);
		presentquery.setText("Present Query : "+presentword);
		presentquery.setText("Present Query : "+presentword);
		presentquery.validate();
		presentquery.repaint();
		this.validate();
		this.repaint();
		getRootPane().validate();
		getRootPane().repaint();
		return ts;
	}
	
	public TreeStats query(Integer push,String query,Integer lf, HashMap<Integer,String> docs)
	{
		//System.out.println(query+" "+query+" "+query);
		displaydocs(docs);
		TreeStats ts = wordTreePanel.query(push, text, textLowerCase, query,lf);
		presentword= ts.query;
		System.out.println("Presentword :"+ts.query);
		System.out.println("Presentword :"+presentword);
		presentquery.setText("Present Query : "+presentword);
		presentquery.validate();
		presentquery.repaint();
		this.validate();
		this.repaint();
		getRootPane().validate();
		getRootPane().repaint();
		return ts;
	}
    
	public void displaydocs(HashMap<Integer,String> docs)
	{
		  ArrayList<String> tempdoc = new ArrayList<String>();
		 for(Entry e : docs.entrySet()){        
	           tempdoc.add((String)e.getValue());
	            }
		 listboxdoc.setListData(tempdoc.toArray());
		 Tab2Scroller.validate();
		 Tab2Scroller.repaint();
	}
	
	public void relwclick(String sel)
	{
		this.query(0,sel,0);
	}

		
	public JPanel laf(WordTreeView wtv){
		
        JPanel wt = new JPanel();
        wt.add(wtv);
        return(wt);
		
	}
	
public static void main(String[] args) throws IOException
	{
	}

@Override
public void mouseClicked(MouseEvent evt) {
	// TODO Auto-generated method stub
	if(evt.getButton() == MouseEvent.BUTTON3){
		p.setNote("");
		p.requestFocus();
	
	}
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent evt) {
	// TODO Auto-generated method stub
	p.mousePressed(evt);
}

@Override
public void mouseReleased(MouseEvent evt) {
	// TODO Auto-generated method stub
	p.mouseReleased(evt);
}

@Override
public void mouseDragged(MouseEvent evt) {
	// TODO Auto-generated method stub
p.mouseDragged(evt);
}

@Override
public void mouseMoved(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

}