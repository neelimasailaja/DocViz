package ControlPanel;

import WordTreePack.WordTreeView;
import WordTreePack.WordTree;
import WordTreePack.WordTreePanel;
import HTrail.LinkedListIterator;
import HTrail.LinkedList1;
import HTrail.ListNode;
import HTrail.Painting;
import java.util.*;
import java.io.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import javax.swing.event.*;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.FilenameFilter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;
public class VizControl extends JFrame{

	public static TreeSet<String> set = new TreeSet<String>();
public static ArrayList<String> al = new ArrayList<String>();
public static ArrayList<String> al1 = new ArrayList<String>();
public static ArrayList<String> al2 = new ArrayList<String>();
public static ArrayList<String> al3 = new ArrayList<String>();
//static TreeSet<String> sets = new TreeSet<String>();
static VizControl.ListExample searchr ;
Listsort searchr1 = new Listsort();
public static String[] filename;
public static String s;
public static StringBuilder sb1 = new StringBuilder();
public static ArrayList<String> sent = new ArrayList<String>();
public static String label = "name";
public static String presentquery1;
public static String[] sort1word = new String[50];
public static String[] sort1o = new String[50];
public static String[] sort1wordtemp = new String[50];
public static Integer[] sort1otemp = new Integer[50];
public static String[] sort2word1 = new String[50];
public static String[] sort2o = new String[50];
public static String[] sort3word1 = new String[50];
public static String[] sort3o = new String[50];
public static Integer biwordcount=0;
public static Integer uniwordcount=0;
public static Integer triwordcount=0;
public static structure[] strtry1;
public static sort2[] sorta1;
public static sort2[] sorta3;
public static String[] sort1res;
public static String temp=null; 
static SortedSet<String>  result;
public static HashMap<String, Integer> counttemp;
String listData[];
String listData1[];
String sets[];
public static int card = 0;
String search = new String();
ArrayList<String> alres1 = new ArrayList<String>();
ArrayList<String> alres2 = new ArrayList<String>();
//ArrayList<String> alsrch = new ArrayList<String>();
public static Set<String> stopset;
//public static TreeTry<String> tree;
//public static TreeTry<String> tree2;
public static JPanel canvas = new JPanel();
//public static Painting p = new Painting();
public static ArrayList<String> forwt = new ArrayList<String>();
public static WordTreeView wtv;
public static int filetype, doccount=0;
public static int flength;
public static int totaldocs;
public static Vector v=new Vector();
public static Vector v1=new Vector();
public static Vector v2=new Vector();
public static Vector v3=new Vector();
public static Vector v4=new Vector();
public static Vector v5=new Vector();
public static Vector v6=new Vector();
public static Vector v7=new Vector();
public static Vector v8=new Vector();
public static Map<String, Integer> wordsperdoc = new HashMap<String, Integer>();
public static Map<Integer,HashMap<String, Integer>> docs = new HashMap<Integer,HashMap<String, Integer>>();
public static Map<Integer,HashMap<String, Integer>> docs1 = new HashMap<Integer,HashMap<String, Integer>>();
public static Map<Integer,HashMap<String, Integer>> docs3 = new HashMap<Integer,HashMap<String, Integer>>();
public static Map<Integer,String> fulldocs = new HashMap<Integer,String>();
public static HashMap<Integer,String> senddocs1 = new HashMap<Integer,String>();
public static HashMap<Integer,String> senddocs2 = new HashMap<Integer,String>();
public static HashMap<Integer,String> senddocs3 = new HashMap<Integer,String>();
VizControl(String[] filename1,int flength1) {
	flength=flength1;
	System.out.println(flength);
	filename=filename1;
	if(filename[0].endsWith(".txt"))
		filetype=0;
	else if(filename[0].endsWith(".xml"))
		filetype=1;
	for(int i=0;i<flength;i++)
		System.out.println(filename[i]+filename[i]+filetype);
	//OutputStream os = new FileOutputStream(filename);
	//OutputStreamWriter bufferedWriter = new OutputStreamWriter(os, "UTF8");
	makestopwords();
getContents();
searchr= new ListExample();
wtv= new WordTreeView(sb1,al,set,sent,counttemp);
JPanel toppane = new JPanel();
JPanel controlpane = new JPanel();
JPanel hola = new JPanel();
JPanel hola1 = new JPanel();
JPanel synpane = new JPanel();
Box horizontalBox = Box.createVerticalBox();
Box horizontalBox1 = Box.createHorizontalBox();
this.setLayout(new FlowLayout());
hola1.add(searchr1.laf1());
hola.add(searchr.laf());
horizontalBox.add(hola);
horizontalBox.add(hola1);
canvas.setPreferredSize(new Dimension(1000,820));
canvas.setLayout(new BorderLayout());
JPanel gap=new JPanel();
gap.setPreferredSize(new Dimension(150,800));
controlpane.add(horizontalBox);
horizontalBox1.add(controlpane);
//horizontalBox1.add(gap);
canvas.add(wtv.laf(wtv),BorderLayout.CENTER);
horizontalBox1.add(canvas);
//horizontalBox1.add(synpane);


toppane.add(horizontalBox1);
add(toppane);
		 this.pack();
		setVisible(true);	  
}


public void makestopwords()
{
	//URL fileURL = this.getClass().getResource("/Users/neelima/stopWords.txt"); 
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
	    
	/*
	 try
     {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream("stopWords.txt");
        try (DataInputStream in = new DataInputStream(fstream)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
            // Read Lines
                stopset.add(strLine);
            }
        }
            
     }
     catch (Exception e)
     {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
     } 
*/
}

static class ListExample
		extends 	JPanel implements KeyListener, FocusListener
 {
	 String labelText =
		      "<html><FONT COLOR=#333366><B>DOCUMENT TOPIC VIEWER</B></FONT></html>";
	 String labelText1 ="<html><FONT COLOR=#333366><I>Total no: of documents :<I>"+totaldocs+"</FONT></html>";
	
	 JLabel heading=new JLabel(labelText);
	JLabel heading1=new JLabel(labelText1);
	
	//synpane.setBorder(BorderFactory.createTitledBorder("Related Words"));
		JPanel		topPanel = new JPanel();
	
		//JPanel hw=new Jpanel();
	
		
		
JPanel		temptopPanel = new JPanel();
	JList		listbox;
JTextField searchbar = new JTextField(20);
JList listbox31;
	public ListExample()
	{
		   System.out.println("Total no of people : " + totaldocs);

		// Create a panel to hold all other components
		topPanel.setLayout( new BorderLayout() );
		temptopPanel.setLayout( new BorderLayout() );
		

		// Create some items to add to the list
		String	listData[] =
		{
			"",
			"",
			"",
			""
		};

		// Create a new listbox control
		listbox = new JList( listData );
listbox.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
boolean adjust = evt.getValueIsAdjusting();
if(!adjust){
       String sel = listbox.getSelectedValue().toString();

wtv.query(0,sel,0);
//wtv.resetScroller();
canvas.validate();
canvas.repaint();
//alsrch.clear();
//forwt.clear();
}
      }
    });

searchbar.addKeyListener(this);
searchbar.addFocusListener(this);
searchbar.setFocusable(true);

		temptopPanel.add( listbox, BorderLayout.CENTER );
JScrollPane jscrlpLabel3 = new JScrollPane(temptopPanel);
jscrlpLabel3.setPreferredSize(new Dimension(100, 200));
//topPanel.add( jscrlpLabel3, BorderLayout.SOUTH);	
topPanel.add( heading, BorderLayout.NORTH);
topPanel.add( heading1, BorderLayout.CENTER);
topPanel.add( searchbar, BorderLayout.SOUTH );	
}




	public void actionPerformed(ActionEvent evt){
String string1;
string1 = searchbar.getText();
result = set.subSet( string1, string1 + Character.MAX_VALUE ); 
for (Object element: result)
			System.out.println(element.toString() + " ");

Object[] objArray = result.toArray();
for (Object obj: objArray)
			System.out.println(obj);
listbox.setListData(objArray);
topPanel.validate();
topPanel.repaint();
JFrame newFrame = new JFrame("Sentences");
JPanel pop = new JPanel();
listbox31 = new JList(result.toArray());
listbox31.addListSelectionListener(new ListSelectionListener() {
    public void valueChanged(ListSelectionEvent evt) {
boolean adjust = evt.getValueIsAdjusting();
if(!adjust){
     String sel = listbox31.getSelectedValue().toString();
     int doccnt=0;
     for(Entry e : docs.entrySet())
     {
     	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
     	tempmap=(HashMap<String, Integer>) e.getValue();
     	if(tempmap.containsKey(sel))
     		{
     		
     		String doctemp = fulldocs.get(doccnt);
     		senddocs3.put(doccnt,doctemp);
     		
     		}
     	doccnt++;
     	//System.out.println("DOC DOC"+docnmbr);
     	//System.out.flush();
     }
    
    
wtv.query(0,sel,0,senddocs3);
senddocs3.clear();
//wtv.resetScroller();
canvas.validate();
canvas.repaint();
//alsrch.clear();
//forwt.clear();
}
    }
  });
pop.setLayout( new BorderLayout() );
pop.add(listbox31,BorderLayout.CENTER );
JScrollPane jscrlpLabel = new JScrollPane(pop);
jscrlpLabel.setPreferredSize(new Dimension(200, 500));
newFrame.add(jscrlpLabel);
newFrame.pack();
newFrame.setVisible(true);
}



	public JPanel laf()
{
 return topPanel;
}




	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getKeyCode()==KeyEvent.VK_SHIFT) {
			String string1;
			string1 = searchbar.getText();
			result = set.subSet( string1, string1 + Character.MAX_VALUE ); 
			for (Object element: result)
						System.out.println(element.toString() + " ");

			Object[] objArray = result.toArray();
			for (Object obj: objArray)
						System.out.println(obj);
			listbox.setListData(objArray);
			topPanel.validate();
			topPanel.repaint();
			JFrame newFrame = new JFrame("Sentences");
			JPanel pop = new JPanel();
			listbox31 = new JList(result.toArray());
			listbox31.addListSelectionListener(new ListSelectionListener() {
			    public void valueChanged(ListSelectionEvent evt) {
			boolean adjust = evt.getValueIsAdjusting();
			if(!adjust){
			     String sel = listbox31.getSelectedValue().toString();
			     int doccnt=0;
			     for(Entry e : docs.entrySet())
			     {
			     	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
			     	tempmap=(HashMap<String, Integer>) e.getValue();
			     	if(tempmap.containsKey(sel))
			     		{
			     		
			     		String doctemp = fulldocs.get(doccnt);
			     		senddocs3.put(doccnt,doctemp);
			     		
			     		}
			     	doccnt++;
			     	//System.out.println("DOC DOC"+docnmbr);
			     	//System.out.flush();
			     }
			    
			    
			wtv.query(0,sel,0,senddocs3);
			senddocs3.clear();
			//wtv.resetScroller();
			canvas.validate();
			canvas.repaint();
			//alsrch.clear();
			//forwt.clear();
			}
			    }
			  });
			pop.setLayout( new BorderLayout() );
			pop.add(listbox31,BorderLayout.CENTER );
			JScrollPane jscrlpLabel = new JScrollPane(pop);
			jscrlpLabel.setPreferredSize(new Dimension(200, 500));
			newFrame.add(jscrlpLabel);
			newFrame.pack();
			newFrame.setVisible(true);

			
		}
		
		else if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			String string1;
			string1 = searchbar.getText();
			if(set.contains(string1))
			{
				String sel=string1;
				  int doccnt=0;
				     for(Entry e1 : docs.entrySet())
				     {
				     	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
				     	tempmap=(HashMap<String, Integer>) e1.getValue();
				     	if(tempmap.containsKey(sel))
				     		{
				     		
				     		String doctemp = fulldocs.get(doccnt);
				     		senddocs3.put(doccnt,doctemp);
				     		
				     		}
				     	doccnt++;
				     	//System.out.println("DOC DOC"+docnmbr);
				     	//System.out.flush();
				     }
				     wtv.query(0,sel,0,senddocs3);
						senddocs3.clear();
						//wtv.resetScroller();
						canvas.validate();
						canvas.repaint();
				
			}

			
		}
		
	}




	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}


class Listsort
		extends 	JPanel 
 {
		JPanel		topPanel1 = new JPanel();
JPanel		topPaneltop = new JPanel();
JPanel		topPanel2 = new JPanel();
JPanel		topPanel3 = new JPanel();
JPanel		temptopPanel1 = new JPanel();
JPanel		temptopPanel2 = new JPanel();
JPanel		temptopPanel3 = new JPanel();
Box horizontalBox = Box.createVerticalBox();
	JList		listbox1;
JList		listbox2;
JList		listbox21;
JList		listbox23;
JList		listbox31;
JList		listbox32;
JList		listbox3;
JList		listbox3f;
JList		listbox3d;
String labelword =
"<html><FONT COLOR=#333366>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Word</FONT></html>";
String labeloc =
"<html><FONT COLOR=#333366>&nbsp;&nbsp;Occ/Doc</FONT></html>";
String labeldoc =
"<html><FONT COLOR=#333366>&nbsp;&nbsp;Doc</FONT></html>";
JLabel headingw=new JLabel(labelword);
JLabel headingo=new JLabel(labeloc);
JLabel headingd=new JLabel(labeldoc);
Box hBox = Box.createHorizontalBox();
Box hBox1 = Box.createHorizontalBox();
Box hBox2 = Box.createHorizontalBox();
Box hBox3 = Box.createHorizontalBox();

	public Listsort()
	{		
		topPanel1.setLayout( new BorderLayout() );
topPanel2.setLayout( new BorderLayout() );
topPaneltop.setLayout( new BorderLayout() );
		

		// Create a new listbox control
		listbox1 = new JList(v);
listbox1.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
boolean adjust = evt.getValueIsAdjusting();
if(!adjust){
       String sel = listbox1.getSelectedValue().toString();
      
        System.out.println(sel);
        int doccnt=0;
        for(Entry e : docs.entrySet())
        {
        	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
        	tempmap=(HashMap<String, Integer>) e.getValue();
        	if(tempmap.containsKey(sel))
        		{
        		
        		String doctemp = fulldocs.get(doccnt);
        		senddocs1.put(doccnt,doctemp);
        		
        		}
        	doccnt++;
        	//System.out.println("DOC DOC"+docnmbr);
        	//System.out.flush();
        }
wtv.query(0,sel,0,senddocs1);
senddocs1.clear();
canvas.validate();
canvas.repaint();


alres1.clear();
forwt.clear();
}
      }
    });



listbox2 = new JList(v4);
listbox32 = new JList(v5);
listbox1.addKeyListener(new KeyAdapter(){ 
	// What to do when a key is pressed? 
	public void keyPressed(KeyEvent ke) { 
		// If user presses Delete key, 
		//if(ke.getKeyCode()==KeyEvent.VK_DELETE) { 
			// Remove the selected item 
		
			
		v.remove(listbox1.getSelectedValue());
		v4.removeElementAt(listbox1.getSelectedIndex());
		v5.removeElementAt(listbox1.getSelectedIndex());
		listbox2.setListData(v4);
		listbox2.validate();
		listbox2.repaint();
		listbox32.setListData(v5);
		listbox32.validate();
		listbox32.repaint();
		System.out.println("After del uni"+uniwordcount);
		//v1.remove(v1.get(listbox1.getSelectedIndex()));
		//v1.add(strtry1[uniwordcount].ln);
		if(stopset.contains(strtry1[uniwordcount].word)==true)
		while(stopset.contains(strtry1[uniwordcount].word)==true){
			uniwordcount++;
		}
		
		int docnmbr=0;
		for(Entry e : docs.entrySet())
		{
			HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
			tempmap=(HashMap<String, Integer>) e.getValue();
			if(tempmap.containsKey(strtry1[uniwordcount].word))
				
				docnmbr++;
				
			//System.out.println("DOC DOC"+docnmbr);
			//System.out.flush();
		}
		
		strtry1[uniwordcount].putdoc(docnmbr);
		v.add(strtry1[uniwordcount].word);
		v4.add(strtry1[uniwordcount].ln);
		v5.add(strtry1[uniwordcount].doc);
		uniwordcount++;
			System.out.println("After del"+uniwordcount);
			System.out.println("After del"+v1.get(listbox1.getSelectedIndex()));
			// Now set the updated vector (updated items) 
			//uniwordcount++;
			listbox1.setListData(v);
			listbox1.validate();
			listbox1.repaint();
			listbox2.setListData(v4);
			listbox2.validate();
			listbox2.repaint();
			listbox32.setListData(v5);
			listbox32.validate();
			listbox32.repaint();
			}

}); 

hBox.add(headingw);
hBox.add(headingo);
//hBox.add(headingd);
hBox1.add(listbox1);
hBox1.add(listbox2);
hBox1.add(listbox32);
topPanel1.add( hBox, BorderLayout.NORTH );
temptopPanel1.add( hBox1, BorderLayout.LINE_START );

//temptopPanel1.add( listbox2, BorderLayout.CENTER );
//temptopPanel1.add( listbox32, BorderLayout.LINE_END );
JScrollPane jscrlpLabel1 = new JScrollPane(temptopPanel1);
jscrlpLabel1.setPreferredSize(new Dimension(250, 200));
topPanel1.add( jscrlpLabel1, BorderLayout.CENTER );


topPanel1.setPreferredSize(new Dimension(250,200));

topPanel2.setPreferredSize(new Dimension(250,200));

topPanel3.setPreferredSize(new Dimension(250,200));
horizontalBox.add(topPanel1);
listbox21 = new JList( v1);

listbox21.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
boolean adjust = evt.getValueIsAdjusting();
if(!adjust){
       String sel = listbox21.getSelectedValue().toString().trim();
String[] words11 = sel.split("[ \n\t\r.,;:!?(){}]");
String query = words11[0]+" "+words11[1];
int doccnt=0;
for(Entry e : docs1.entrySet())
{
	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
	tempmap=(HashMap<String, Integer>) e.getValue();
	if(tempmap.containsKey(query))
		{
		
		String doctemp = fulldocs.get(doccnt);
		senddocs2.put(doccnt,doctemp);
		
		}
	doccnt++;
	//System.out.println("DOC DOC"+docnmbr);
	//System.out.flush();
}
wtv.query(0,query,0,senddocs2);
senddocs2.clear();
canvas.validate();
canvas.repaint();

}
      }
    });
listbox23 = new JList( v2);
listbox31 = new JList( v3);
//Add key listener to JList jl 
listbox21.addKeyListener(new KeyAdapter(){ 
	// What to do when a key is pressed? 
	public void keyPressed(KeyEvent ke) { 
		// If user presses Delete key, 
		//if(ke.getKeyCode()==KeyEvent.VK_DELETE) { 
			// Remove the selected item 
		
			
		v1.remove(listbox21.getSelectedValue());
		v2.removeElementAt(listbox21.getSelectedIndex());
		v3.removeElementAt(listbox21.getSelectedIndex());
		if(!((stopset.contains(sorta1[biwordcount].worda)!=true)&&(stopset.contains(sorta1[biwordcount].wordb)!=true)&&((sorta1[biwordcount].worda.length()>1)&&(sorta1[biwordcount].wordb.length()>1))))	
		while(!((stopset.contains(sorta1[biwordcount].worda)!=true)&&(stopset.contains(sorta1[biwordcount].wordb)!=true)&&((sorta1[biwordcount].worda.length()>1)&&(sorta1[biwordcount].wordb.length()>1))))
			{
				biwordcount++;
			}
				int docnmbr=0;
		for(Entry e : docs1.entrySet())
		{
			HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
			tempmap=(HashMap<String, Integer>) e.getValue();
			
			if(tempmap.containsKey((String)sorta1[biwordcount].worda+" "+(String)sorta1[biwordcount].wordb))
			
				docnmbr++;
				
			//System.out.println("DOC DOC"+docnmbr);
			//System.out.flush();
			

		}
		sorta1[biwordcount].putdoc(docnmbr);
		v1.add((String)sorta1[biwordcount].worda+" "+(String)sorta1[biwordcount].wordb);
		v2.add(sorta1[biwordcount].occ);
		v3.add(sorta1[biwordcount].docnmbr);
		listbox23.setListData(v2);
		listbox23.validate();
		listbox23.repaint();
		listbox31.setListData(v3);
		listbox31.validate();
		listbox31.repaint();
		biwordcount++;
			System.out.println("After del"+listbox21.getSelectedIndex());
			System.out.println("After del"+v1.get(listbox21.getSelectedIndex()));
			// Now set the updated vector (updated items) 
			//uniwordcount++;
			listbox21.setListData(v1);
			listbox21.validate();
			listbox21.repaint();
			listbox23.setListData(v2);
			listbox23.validate();
			listbox23.repaint();
			listbox31.setListData(v3);
			listbox31.validate();
			listbox31.repaint();
			}

}); 
hBox2.add(listbox21);
hBox2.add(listbox23);
hBox2.add(listbox31);

listbox3 = new JList(v6);
listbox3f = new JList(v7);
listbox3d = new JList(v8);

listbox3.addListSelectionListener(new ListSelectionListener() {
    public void valueChanged(ListSelectionEvent evt) {
boolean adjust = evt.getValueIsAdjusting();
if(!adjust){
     String sel = listbox3.getSelectedValue().toString().trim();
String[] words11 = sel.split("[ \n\t\r.,;:!?(){}]");
String query = words11[0]+" "+words11[1]+" "+words11[2];
int doccnt=0;
for(Entry e : docs3.entrySet())
{
	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
	tempmap=(HashMap<String, Integer>) e.getValue();
	if(tempmap.containsKey(query))
		{
		
		String doctemp = fulldocs.get(doccnt);
		senddocs3.put(doccnt,doctemp);
		
		}
	doccnt++;
	//System.out.println("DOC DOC"+docnmbr);
	//System.out.flush();
}
wtv.query(0,query,0,senddocs3);
senddocs3.clear();
canvas.validate();
canvas.repaint();

}
    }
  });
listbox3.addKeyListener(new KeyAdapter(){ 

	public void keyPressed(KeyEvent ke) { 		
			
		v6.remove(listbox3.getSelectedValue());
		v7.removeElementAt(listbox3.getSelectedIndex());
		v8.removeElementAt(listbox3.getSelectedIndex());
if(!((stopset.contains(sorta3[triwordcount].worda)!=true)&&(stopset.contains(sorta3[triwordcount].wordb)!=true)&&((sorta3[triwordcount].worda.length()>1)&&(sorta3[triwordcount].wordb.length()>1))))	
		while(!((stopset.contains(sorta3[triwordcount].worda)!=true)&&(stopset.contains(sorta3[triwordcount].wordb)!=true)&&((sorta3[triwordcount].worda.length()>1)&&(sorta3[triwordcount].wordb.length()>1))))
			{
				triwordcount++;
			}

			int docnmbr=0;
		for(Entry e : docs3.entrySet())
		{
			HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
			tempmap=(HashMap<String, Integer>) e.getValue();
			
			if(tempmap.containsKey((String)sorta3[triwordcount].worda+" "+(String)sorta3[triwordcount].wordb))
			
				docnmbr++;
				
			//System.out.println("DOC DOC"+docnmbr);
			//System.out.flush();
			

		}

sorta3[triwordcount].putdoc(docnmbr);
		v6.add((String)sorta3[triwordcount].worda+" "+(String)sorta3[triwordcount].wordb);
		v7.add(sorta3[triwordcount].occ);
		v8.add(sorta3[triwordcount].docnmbr);

		listbox3f.setListData(v7);
		listbox3f.validate();
		listbox3f.repaint();
		listbox3d.setListData(v8);
		listbox3d.validate();
		listbox3d.repaint();
	triwordcount++;
			listbox3.setListData(v6);
			listbox3.validate();
			listbox3.repaint();
			listbox3f.setListData(v7);
			listbox3f.validate();
			listbox3f.repaint();
			listbox3d.setListData(v8);
			listbox3d.validate();
			listbox3d.repaint();
			}

}); 
hBox3.add(listbox3);
hBox3.add(listbox3f);
hBox3.add(listbox3d);
temptopPanel2.add(hBox2, BorderLayout.LINE_START );
temptopPanel3.add(hBox3, BorderLayout.LINE_START );

//temptopPanel2.add( listbox23, BorderLayout.CENTER );
//temptopPanel2.add( listbox31, BorderLayout.LINE_END );
JScrollPane jscrlpLabel2 = new JScrollPane(temptopPanel2);
jscrlpLabel2.setPreferredSize(new Dimension(250, 200));
topPanel2.add( jscrlpLabel2, BorderLayout.CENTER );
JScrollPane jscrlpLabel3 = new JScrollPane(temptopPanel3);
jscrlpLabel3.setPreferredSize(new Dimension(250, 200));
topPanel3.add( jscrlpLabel3, BorderLayout.CENTER );
horizontalBox.add(topPanel2);
horizontalBox.add(topPanel3);
topPaneltop.add(horizontalBox);
	
}

public JPanel laf1()
{
return topPaneltop;
}




}



class structure1{

String word1;
int ln1;

public structure1(String word2, int ln2)
{

this.word1=word2;
this.ln1=ln2;
}

}




	public static void getContents() {

 StringBuffer contents = new StringBuffer(); 
ArrayList<String> str = new ArrayList<String>();
ArrayList<structure> strtry = new ArrayList<structure>();

String text = new String();
System.out.println(filetype);

if(filetype==1)
{
	System.out.println("Inside!");
try { 

 	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(filename[0]));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            System.out.println ("Root element of the doc is " + 
                 doc.getDocumentElement().getNodeName());


            NodeList listOfPersons = doc.getElementsByTagName("document");
            int totalPersons = listOfPersons.getLength();
            System.out.println("Total no of people : " + totalPersons);
            totaldocs=listOfPersons.getLength();
            System.out.println("Total no of people : " + totaldocs);
            //searchr.validate();
            //searchr.repaint();
            for(int s=0; s<listOfPersons.getLength() ; s++)
            {

            	
                Node firstPersonNode = listOfPersons.item(s);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                {


                    Element firstPersonElement = (Element)firstPersonNode;

                    //-------
                      NodeList firstNameList = firstPersonElement.getElementsByTagName("docText");

                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                     text = textFNList.item(0).getNodeValue().trim();
                     String[] sentences = text.split("\\.");
                     
                     for (int i = 0; i < sentences.length; i++)
         			{
                    	 sent.add(sentences[i].toString().toLowerCase());
         			}
String[] words = text.split("[ \n\t\r.,;:!?(){}]");
HashMap<String, Integer>counttemp = new HashMap<String, Integer>();
HashMap<String, Integer>counttemp1 = new HashMap<String, Integer>();
HashMap<String, Integer>counttemp2 = new HashMap<String, Integer>();
		for (int i = 0; i < words.length; i++) 
		{
			String value = words[i].toLowerCase();
			String value1 = words[i].trim();
			
			int alphanum=0;
			int alphanum1=0;
			int alphanum2=0;
			Pattern ALPHANUMERIC = Pattern.compile("[A-Za-z0-9]+");
			if (words[i].length() > 0)
			{
				Matcher m = ALPHANUMERIC.matcher(value1);
				if(m.matches()==false)
				alphanum++;
			}          
			if(alphanum==0)
                                {al.add(value);
				set.add(value);
				al1.add(value);
				}
			if(i<words.length-1)
			{
			String value2 = words[i+1].toLowerCase();
			String value3 = words[i+1].trim();
			if ((words[i].length() > 0)&&(words[i+1].length() > 0))
			{
				Matcher m = ALPHANUMERIC.matcher(value1);
				Matcher m1 = ALPHANUMERIC.matcher(value3);
				if((m.matches()==false)&&(m1.matches()==false))
				alphanum1++;
			}          
			if(alphanum1==0)
                                {al2.add(value+" "+value2);
				
				}
			}
			if(i<words.length-2)
			{
			String value2 = words[i+1].toLowerCase();
			String value21 = words[i+1].trim();
			String value3 = words[i+2].toLowerCase();
			String value31 = words[i+2].trim();
			if ((words[i].length() > 0)&&(words[i+1].length() > 0)&&(words[i+2].length() > 0))
			{
				Matcher m = ALPHANUMERIC.matcher(value1);
				Matcher m1 = ALPHANUMERIC.matcher(value21);
				Matcher m2 = ALPHANUMERIC.matcher(value31);
				if((m.matches()==false)&&(m1.matches()==false)&&(m2.matches()==false))
				alphanum2++;
			}          
			if(alphanum2==0)
                                {al3.add(value+" "+value2+" "+value3);
				
				}
			}
				
		}
		String[] words1 = text.split("[ \n\t\r,;:(){}]");
		
		for (int i = 0; i < words1.length; i++) 
		{
			if ((words1[i] != null)&&(words1[i].trim().length() != 0))
			{
                sb1.append(words1[i]+" ");

			}
			
		}
		for(String s1 : al1){
	        if(!counttemp.containsKey(s1)){
	            counttemp.put(s1, 1);
	        }
	        else{
	            counttemp.put(s1, counttemp.get(s1) + 1);
	        }

		}
		docs.put(doccount, counttemp);
		for(String s1 : al2){
	        if(!counttemp1.containsKey(s1)){
	            counttemp1.put(s1, 1);
	        }
	        else{
	            counttemp1.put(s1, counttemp1.get(s1) + 1);
	        }

		}
		docs1.put(doccount, counttemp1);
		for(String s1 : al3){
	        if(!counttemp2.containsKey(s1)){
	            counttemp2.put(s1, 1);
	        }
	        else{
	            counttemp2.put(s1, counttemp2.get(s1) + 1);
	        }

		}
		docs3.put(doccount, counttemp2);
		//System.out.println("Checking docs3"+docs3.get(doccount));
		fulldocs.put(doccount, text);
		doccount++;
		al1.clear();
		al2.clear();
		al3.clear();
}
}

}
catch (IOException ex){ 
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
            
}    

else if(filetype==0)
{
	for(int fi=0;fi<flength;fi++)
	{	
	File f = new File(filename[fi]);
		
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
		text=sb.toString();
		
	
	 String[] sentences = text.split("\\.");
     
	  for (int i = 0; i < sentences.length; i++)
		{
      	 sent.add(sentences[i].toString().toLowerCase());
		}
	  String[] words = text.split("[ \n\t\r.,;:!?(){}]");
	  HashMap<String, Integer>counttemp = new HashMap<String, Integer>();
	  HashMap<String, Integer>counttemp1 = new HashMap<String, Integer>();
	  HashMap<String, Integer>counttemp2 = new HashMap<String, Integer>();
	  for (int i = 0; i < words.length; i++) 
	  {
	  String value = words[i].toLowerCase();
	  String value1 = words[i].trim();

	  int alphanum=0;
	  int alphanum1=0;
	  int alphanum2=0;
	  Pattern ALPHANUMERIC = Pattern.compile("[A-Za-z0-9]+");
	  if (words[i].length() > 0)
	  {
	  	Matcher m = ALPHANUMERIC.matcher(value1);
	  	if(m.matches()==false)
	  	alphanum++;
	  }          
	  if(alphanum==0)
	                    {al.add(value);
	  	set.add(value);
	  	al1.add(value);
	  	}
	  if(i<words.length-1)
	  {
	  String value2 = words[i+1].toLowerCase();
	  String value3 = words[i+1].trim();
	  if ((words[i].length() > 0)&&(words[i+1].length() > 0))
	  {
	  	Matcher m = ALPHANUMERIC.matcher(value1);
	  	Matcher m1 = ALPHANUMERIC.matcher(value3);
	  	if((m.matches()==false)&&(m1.matches()==false))
	  	alphanum1++;
	  }          
	  if(alphanum1==0)
	                    {al2.add(value+" "+value2);
	  	
	  	}
	  }
	  if(i<words.length-2)
	  {
	  String value2 = words[i+1].toLowerCase();
	  String value21 = words[i+1].trim();
	  String value3 = words[i+2].toLowerCase();
	  String value31 = words[i+2].trim();
	  if ((words[i].length() > 0)&&(words[i+1].length() > 0)&&(words[i+2].length() > 0))
	  {
	  	Matcher m = ALPHANUMERIC.matcher(value1);
	  	Matcher m1 = ALPHANUMERIC.matcher(value21);
	  	Matcher m2 = ALPHANUMERIC.matcher(value31);
	  	if((m.matches()==false)&&(m1.matches()==false)&&(m2.matches()==false))
	  	alphanum2++;
	  }          
	  if(alphanum2==0)
	                    {al3.add(value+" "+value2+" "+value3);
	  	
	  	}
	  }
	  	
	  }
	  String[] words1 = text.split("[ \n\t\r,;:(){}]");

	  for (int i = 0; i < words1.length; i++) 
	  {
	  if ((words1[i] != null)&&(words1[i].trim().length() != 0))
	  {
	    sb1.append(words1[i]+" ");

	  }

	  }
	  for(String s1 : al1){
	  if(!counttemp.containsKey(s1)){
	    counttemp.put(s1, 1);
	  }
	  else{
	    counttemp.put(s1, counttemp.get(s1) + 1);
	  }

	  }
	  docs.put(doccount, counttemp);
	  for(String s1 : al2){
	  if(!counttemp1.containsKey(s1)){
	    counttemp1.put(s1, 1);
	  }
	  else{
	    counttemp1.put(s1, counttemp1.get(s1) + 1);
	  }

	  }
	  docs1.put(doccount, counttemp1);
	  for(String s1 : al3){
	  if(!counttemp2.containsKey(s1)){
	    counttemp2.put(s1, 1);
	  }
	  else{
	    counttemp2.put(s1, counttemp2.get(s1) + 1);
	  }

	  }
	  docs3.put(doccount, counttemp2);
	  //System.out.println("Checking docs3"+docs3.get(doccount));
	  fulldocs.put(doccount, text);
	  doccount++;
	  al1.clear();
	  al2.clear();
	  al3.clear();

}
            System.out.println(docs);
int k=0;
//System.out.println(docs1.get(1));
//Iterator itr = al.iterator();

}

counttemp = new HashMap<String, Integer>();
System.out.println(al);
	for(String s : al){
		int docnmbr=0;
        if(!counttemp.containsKey(s)){
            counttemp.put(s, 1);
        }
        else{
            counttemp.put(s, counttemp.get(s) + 1);
        }

	}
	System.out.println(counttemp);
	int n=0;
	/*
int n=0;
int count=0;

for ( int g=0;g<k;g++)
{
for( int y=0;y<al.size();y++)
{

if(al.get(y).equals(str.get(g)))
++count;
}
strtry.add(new structure(str.get(g),count));
n++;
count=0;
}
*/
	
	for(Entry f : counttemp.entrySet()){
		//Integer temp = countdoc.get((String)f.getKey());
		strtry.add(new structure((String)f.getKey(),(Integer)f.getValue()));
		//sorta.add(new sort2((String)e.getKey(),(String)f.getKey(),(Integer)f.getValue()));
		//ko++;
		n++;
		            }
int gi=0;
int temp=n;
Object[] strtrytemp=strtry.toArray();
strtry1 = new structure[n];
System.arraycopy(strtrytemp, 0, strtry1, 0, n);

Arrays.sort(strtry1);
int gio=0;
while(gio<50)
{
	if(!((stopset.contains(strtry1[uniwordcount].word)!=true)&&((strtry1[uniwordcount].word.length()>1))))	
		while(!((stopset.contains(strtry1[uniwordcount].word)!=true)&&((strtry1[uniwordcount].word.length()>1))))	{
			uniwordcount++;
		    gi++;
		}
if(uniwordcount<n){
sort1word[gio]=strtry1[gi].word;

int docnmbr=0;
int doccnt=0;
for(Entry e : docs.entrySet())
{
	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
	tempmap=(HashMap<String, Integer>) e.getValue();
	if(tempmap.containsKey(strtry1[gi].word))
		
		docnmbr++;
		
	//System.out.println("DOC DOC"+docnmbr);
	//System.out.flush();
}
strtry1[gi].putdoc(docnmbr);
sort1o[gio]=strtry1[gi].ln+"/"+strtry1[gi].doc;
v.add(strtry1[gi].word);
v4.add(strtry1[gi].ln);
v5.add(strtry1[gi].doc);
//v1.add(strtry1[gi].ln+"/"+strtry1[gi].doc);
//System.out.println(sort1word[gio]+"   "+sort1o[gio]);


gio++;
}
	gi++;
	uniwordcount++;
}


ArrayList<sort2> sorta = new ArrayList<sort2>();
 ArrayList<String> inputWords = new ArrayList<String>();
  HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
int ko=0;
        for(int i = 0; i < al.size() - 1; i++){

            String thisWord = al.get(i);
            String nextWord = al.get(i+1);

            if(!result.containsKey(thisWord)){
                result.put(thisWord, new ArrayList<String>());
            }
            result.get(thisWord).add(nextWord);
        }
  for(Entry e : result.entrySet()){
            Map<String, Integer>count1 = new HashMap<String, Integer>();
            List<String> words = (List)e.getValue();
            for(String s : words){
                if(!count1.containsKey(s)){
                    count1.put(s, 1);
                }
                else{
                    count1.put(s, count1.get(s) + 1);
                }
            }
            for(Entry f : count1.entrySet()){

sorta.add(new sort2((String)e.getKey(),(String)f.getKey(),(Integer)f.getValue()));
ko++;
            }
        }
  Object[] sortatemp=sorta.toArray();
sorta1 = new sort2[ko];
System.arraycopy(sortatemp, 0, sorta1, 0, ko);
Arrays.sort(sorta1);
//for(int a=0;a<ko;a++);
//System.out.println(sorta1[a].worda+" "+sorta1[a].wordb+" "+sorta1[a].occ);
int rio=0;
int ri=0;
while(rio<50)
{
	if(!((stopset.contains(sorta1[ri].worda)!=true)&&(stopset.contains(sorta1[ri].wordb)!=true)&&((sorta1[ri].worda.length()>1)&&(sorta1[ri].wordb.length()>1))))	
		while(!((stopset.contains(sorta1[ri].worda)!=true)&&(stopset.contains(sorta1[ri].wordb)!=true)&&((sorta1[ri].worda.length()>1)&&(sorta1[ri].wordb.length()>1))))	
			{
				ri++;
				biwordcount++;
			}
	
{
sort2word1[rio]=(String)sorta1[ri].worda+" "+(String)sorta1[ri].wordb;

int docnmbr=0;
for(Entry e : docs1.entrySet())
{
	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
	tempmap=(HashMap<String, Integer>) e.getValue();
	
	if(tempmap.containsKey(sort2word1[rio]))
	
		docnmbr++;
		
	//System.out.println("DOC DOC"+docnmbr);
	//System.out.flush();
	

}
sorta1[ri].putdoc(docnmbr);
sort2o[rio]=sorta1[ri].occ+"/"+sorta1[ri].docnmbr;
v1.add((String)sorta1[ri].worda+" "+(String)sorta1[ri].wordb);
v2.add(sorta1[ri].occ);
v3.add(sorta1[ri].docnmbr);

System.out.println(sort2word1[rio]+" "+sort2o[rio]);
rio++;
}
ri++;
biwordcount++;
}

ArrayList<sort2> sort3 = new ArrayList<sort2>();
ArrayList<String> inputWords3 = new ArrayList<String>();
 HashMap<String, ArrayList<String>> result3 = new HashMap<String, ArrayList<String>>();
int ko3=0;
       for(int i = 0; i < al.size() - 2; i++){
            if((al.get(i).trim().length()>1)&&(al.get(i+1).trim().length()>1)&&(al.get(i+2).trim().length()>1)&&(!stopset.contains(al.get(i)))&&(!stopset.contains(al.get(i+1)))&&(!stopset.contains(al.get(i+2))))
            {
            	String thisWord = al.get(i)+" "+al.get(i+1);
           String nextWord = al.get(i+2);
            
           if(!result3.containsKey(thisWord)){
               result3.put(thisWord, new ArrayList<String>());
           }
           result3.get(thisWord).add(nextWord);
            }
            
       }
 for(Entry e : result3.entrySet()){
           Map<String, Integer>count3 = new HashMap<String, Integer>();
           List<String> words3 = (List)e.getValue();
           for(String s : words3){
               if(!count3.containsKey(s)){
                   count3.put(s, 1);
               }
               else{
                   count3.put(s, count3.get(s) + 1);
               }
           }
           for(Entry f : count3.entrySet()){

sort3.add(new sort2((String)e.getKey(),(String)f.getKey(),(Integer)f.getValue()));
ko3++;
           }
       }
 Object[] sortatemp3=sort3.toArray();
sorta3 = new sort2[ko3];
System.arraycopy(sortatemp3, 0, sorta3, 0, ko3);
Arrays.sort(sorta3);
/*
for(int ktry=0;ktry<ko3;ktry++)
{
	System.out.println("Trigram:"+" "+sorta3[ktry].worda+" "+sorta3[ktry].wordb+" "+sorta3[ktry].occ);
}
*/
int rio3=0;
int ri3=0;
int ub =0;
if(ko3<50)
	ub=ko3;
else
	ub=50;
while(rio3<ub)
{
	if(!((stopset.contains(sorta3[ri3].worda)!=true)&&(stopset.contains(sorta3[ri3].wordb)!=true)&&((sorta3[ri3].worda.trim().length()>1)&&(sorta3[ri3].wordb.trim().length()>1))))	
		while(!((stopset.contains(sorta3[ri3].worda)!=true)&&(stopset.contains(sorta3[ri3].wordb)!=true)&&((sorta3[ri3].worda.trim().length()>1)&&(sorta3[ri3].wordb.trim().length()>1))))	
			{
				ri3++;
				triwordcount++;
			}
	
{
sort3word1[rio3]=(String)sorta3[ri3].worda+" "+(String)sorta3[ri3].wordb;

int docnmbr3=0;
for(Entry e : docs3.entrySet())
{
	HashMap<String,Integer> tempmap=new HashMap<String,Integer>();
	tempmap=(HashMap<String, Integer>) e.getValue();
	
	if(tempmap.containsKey(sort3word1[rio3]))
	
		docnmbr3++;
		
	//System.out.println("DOC DOC"+docnmbr);
	//System.out.flush();
	

}
sorta3[ri3].putdoc(docnmbr3);
sort3o[rio3]=sorta3[ri3].occ+"/"+sorta3[ri3].docnmbr;
v6.add((String)sorta3[ri3].worda+" "+(String)sorta3[ri3].wordb);
v7.add(sorta3[ri3].occ);
v8.add(sorta3[ri3].docnmbr);

System.out.println(sort3word1[rio3]+" "+sort3o[rio3]);
rio3++;
}
ri3++;
triwordcount++;
}
s = sb1.toString();
sb1.append(s);

}


public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException
{
JFileChooser fileChooser = new JFileChooser();
fileChooser.setMultiSelectionEnabled(true);
fileChooser.showOpenDialog(null);
//File f = fileChooser.getSelectedFile();  


File[] files = fileChooser.getSelectedFiles();
String[] fileName=new String[3200]; 
for(int i=0;i<files.length;i++)
{
	String fileNametemp = files[i].getAbsolutePath();
fileName[i] =fileNametemp;
}
//System.out.println(fileName+"    "+fileName);
VizControl tryViz = new VizControl(fileName,files.length);
 
}
}



