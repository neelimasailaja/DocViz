package WordTreePack;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import ControlPanel.countstruct;
import ControlPanel.structure;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.CircleLayout;
import prefuse.action.layout.RandomLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.*;
import prefuse.data.io.CSVTableReader;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
import prefuse.data.io.TableReader;
public class synList extends JPanel{
	
	public static ArrayList<String> al = new ArrayList<String>();
	public static TreeSet<String> set = new TreeSet<String>();
	public static String filename;
	public static WordTreeView wtv;
	public static HashMap<String, Integer> counts;
			JPanel		temptopPanel = new JPanel();
			JPanel		temptopPanel1 = new JPanel();
			JPanel testpanel= new JPanel();
			JPanel	    topPanel = new JPanel();
	JPanel		forceVis = new JPanel();
		JList		listbox;
		JList		listbox1;
		JList listbox2;
		JList listbox3;
		
JLabel heading;
JLabel heading1;
String glword;
ArrayList<String> sent = new ArrayList<String>();

String s1="Amendment";
String s2="energy";
		public synList(ArrayList<String> al1,TreeSet<String> set1,ArrayList<String> sent1,WordTreeView wtv1, HashMap<String, Integer> counts1)
		{
		//filename=filen;
			this.counts=counts1;
			wtv=wtv1;
		al=al1;
		set=set1;
		sent=sent1;
             heading=new JLabel("Related Words");
             heading1=new JLabel(" ");
			forceVis.setLayout( new BorderLayout() );
			temptopPanel.setLayout( new BorderLayout() );
			//createdoctree();
			String	listData[] =
			{
				"",
				"",
				"",
				""
			};
			Integer	listData1[] =
				{
					
				};
			String	listData2[] =
				{
					"",
					"",
					"",
					""
				};
				Integer	listData3[] =
					{ 
						
					};
				JLabel relw = new JLabel("WordNet");
				JLabel wordnet = new JLabel("Sentence");
			Box horizontalBox = Box.createHorizontalBox();
			Box horizontalBox1 = Box.createHorizontalBox();
			Box horizontalBoxtop = Box.createVerticalBox();
			Box horizontalBoxtop1 = Box.createVerticalBox();
			Box verticalBox = Box.createVerticalBox();
			listbox = new JList( listData );		
			listbox1 = new JList( listData1 );
			listbox2 = new JList( listData2 );		
			listbox3 = new JList( listData3 );
			listbox.setCellRenderer(new JavaLocationRenderer());
			JPanel gap = new JPanel();
			gap.setPreferredSize(new Dimension(100,10));
			horizontalBox.add(listbox1);
			horizontalBox.add(listbox);
			horizontalBox1.add(listbox3);
			horizontalBox1.add(listbox2);
			
			
			//verticalBox.add(horizontalBox);
			testpanel.add(horizontalBox1,BorderLayout.NORTH);
			temptopPanel.add(horizontalBox,BorderLayout.NORTH);
	JScrollPane jscrlpLabel3 = new JScrollPane(temptopPanel);
	jscrlpLabel3.setPreferredSize(new Dimension(110, 620));
	JScrollPane jscrlpLabel4 = new JScrollPane(testpanel);
	jscrlpLabel4.setPreferredSize(new Dimension(110, 620));

	horizontalBoxtop.add(relw);
	horizontalBoxtop.add(jscrlpLabel3);
	horizontalBoxtop1.add(wordnet);
	horizontalBoxtop1.add(jscrlpLabel4);
	this.add(horizontalBoxtop1);
	this.add(horizontalBoxtop);
	this.setPreferredSize(new Dimension(220, 620));
		
		}

public void refreshsyn(Object[] objArray, String word, HashSet<String> stopset, int check){
	String[] dispsyn=new String[objArray.length];
	//String[] stopwordsarray = stopwords.split("[ \n\t\r.,;:!?(){}]");
	String[] tempsplit=word.split(" ");
	//System.out.println("Length"+tempsplit.length);
	if(tempsplit.length==1)
	glword=tempsplit[0];
	else if(tempsplit.length==2)
	{
		glword=tempsplit[0]+" "+tempsplit[1];
	System.out.println("Length"+tempsplit.length);
	System.out.println("Word"+glword);
	}
	else if(tempsplit.length==3)
	{
		glword=tempsplit[0]+" "+tempsplit[1]+" "+tempsplit[2];
	System.out.println("Length"+tempsplit.length);
	System.out.println("Word"+glword);
	}
	int tempcount=0;
	if(check==0){
	for (Object element: objArray)
	{
		//System.out.println(element+" "+"Making array");
		if((set.contains(element))==true)
		{
			dispsyn[tempcount]=(String) element;
	tempcount++;
		}
	}
	
	ArrayList<String> wordlist= new ArrayList<String>();
	//structure[] strtryy1;
	for(int i=0;i<tempcount;i++)
	{
		int flag=0;
	  for(int j=i+1;j<tempcount;j++)
	  {
	   if(dispsyn[i].equals(dispsyn[j]))
	   {
	   flag++;
		 //System.out.println(dispsyn[i]+"FLAG="+flag);
	   }
	  }

	 if(flag==0)
	 {
	 wordlist.add(dispsyn[i]);
	 //System.out.println(dispsyn[i]+"DUP????");
	 }

	}
    
		//System.out.println(wordlist);
		int count=0;
		int n=0;
		ArrayList<countstruct> strtry = new ArrayList<countstruct>();
		String[] sort1word = new String[1000];
		Integer[] sort1o = new Integer[1000];
		
		
		HashMap<String, Integer>counttemprel = new HashMap<String, Integer>();
		for ( int g=0;g<wordlist.size();g++)
		{
			int sentcheck=0;
			
			for (int i = 0; i < sent.size(); i++)
			{
	    
				if((sent.get(i).contains(glword.toLowerCase()+" ")==true)||(sent.get(i).contains(glword.toLowerCase()+".")==true)||(sent.get(i).contains(glword.toLowerCase()+",")==true)||(sent.get(i).contains(glword.toLowerCase()+"?")==true)||(sent.get(i).contains(glword.toLowerCase()+"!")==true)||(sent.get(i).contains(glword.toLowerCase()+")")==true)||(sent.get(i).contains(glword.toLowerCase()+"(")==true)||(sent.get(i).contains(glword.toLowerCase()+"[")==true)||(sent.get(i).contains(glword.toLowerCase()+"]")==true)||(sent.get(i).contains(glword.toLowerCase()+"{")==true)||(sent.get(i).contains(glword.toLowerCase()+"}")==true)||(sent.get(i).contains(glword.toLowerCase()+"\n")==true)||(sent.get(i).contains(glword.toLowerCase()+"\t")==true)||(sent.get(i).contains(glword.toLowerCase()+"\r")==true)||(sent.get(i).contains(glword.toLowerCase()+";")==true)||(sent.get(i).contains(glword.toLowerCase()+":")==true)||(sent.get(i).contains(glword.toLowerCase()+"'")==true)||(sent.get(i).contains(glword.toLowerCase()+"-")==true))
				{
					if((sent.get(i).contains(wordlist.get(g).toLowerCase()+" ")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+".")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+",")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"?")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"!")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+")")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"(")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"[")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"]")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"{")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"}")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"\n")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"\t")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"\r")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+";")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+":")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"'")==true)||(sent.get(i).contains(wordlist.get(g).toLowerCase()+"-")==true))
				{
						if(!glword.toLowerCase().trim().equals(wordlist.get(g).toLowerCase().trim()))
						{
							if(((stopset.contains(wordlist.get(g))!=true)&&(counttemprel.containsKey(wordlist.get(g))!=true))){
							 //System.out.println("Count1"+value);
					            counttemprel.put(wordlist.get(g), 1);
					            sentcheck=1;
					        }
						 else if((stopset.contains(wordlist.get(g))!=true)&&(counttemprel.containsKey(wordlist.get(g))==true)){
					            counttemprel.put(wordlist.get(g), counttemprel.get(wordlist.get(g))+1);
					            //System.out.println("Count"+value);
					            sentcheck=1;
					        }
						}
				}
				}
			}
		if(counts.containsKey(wordlist.get(g)))
		{
			int sentcount=0;
         
		//if(al.get(y).equals(wordlist.get(g)))
		count=counts.get(wordlist.get(g));
		System.out.println("Word :" + wordlist.get(g));
		System.out.println("Same sent count :" + (Integer)counttemprel.get(wordlist.get(g)));
		System.out.println("Total count" +count);
		if(sentcheck==1)
       	 sentcount=(Integer)counttemprel.get(wordlist.get(g));
		
		strtry.add(new countstruct(wordlist.get(g),sentcount,count));
		//new countstruct((String)f.getKey(),(Integer)counttemprel.getValue(wordlist.get(g)),countrel)
		n++;
		}
		
		count=0;
		}
		int gi=0;
		int temp=n;
		Object[] strtrytemp=strtry.toArray();
		countstruct[] strtry1 = new countstruct[n];
		System.arraycopy(strtrytemp, 0, strtry1, 0, n);

		Arrays.sort(strtry1);
		for (int gio=0;gio<n;gio++)
		{
		
		 
			String tempstore=strtry1[gio].word;
		 sort1word[gi]=tempstore;
			sort1o[gi]=strtry1[gio].ln;
			gi++;
		}
		
		listbox1.setListData(sort1o);
		listbox.setListData(sort1word);
		
		listbox.validate();
		listbox.repaint();
		
		listbox1.validate();
		listbox1.repaint();
}
		
		ArrayList<String> relwords = new ArrayList<String>();
		HashMap<String, Integer>counttemp = new HashMap<String, Integer>();
		int tempp=0;
		countstruct[] strtryy1= new countstruct[2000];
		String[] sortword = new String[2000];
		Integer[] sorto = new Integer[2000];
		if(((tempsplit.length==1)||(tempsplit.length==2)||(tempsplit.length==3))&&(!stopset.contains(glword)))
		for (int i = 0; i < sent.size(); i++)
		{
    
			if((sent.get(i).contains(glword.toLowerCase()+" ")==true)||(sent.get(i).contains(glword.toLowerCase()+".")==true)||(sent.get(i).contains(glword.toLowerCase()+",")==true)||(sent.get(i).contains(glword.toLowerCase()+"?")==true)||(sent.get(i).contains(glword.toLowerCase()+"!")==true)||(sent.get(i).contains(glword.toLowerCase()+")")==true)||(sent.get(i).contains(glword.toLowerCase()+"(")==true)||(sent.get(i).contains(glword.toLowerCase()+"[")==true)||(sent.get(i).contains(glword.toLowerCase()+"]")==true)||(sent.get(i).contains(glword.toLowerCase()+"{")==true)||(sent.get(i).contains(glword.toLowerCase()+"}")==true)||(sent.get(i).contains(glword.toLowerCase()+"\n")==true)||(sent.get(i).contains(glword.toLowerCase()+"\t")==true)||(sent.get(i).contains(glword.toLowerCase()+"\r")==true)||(sent.get(i).contains(glword.toLowerCase()+";")==true)||(sent.get(i).contains(glword.toLowerCase()+":")==true)||(sent.get(i).contains(glword.toLowerCase()+"'")==true)||(sent.get(i).contains(glword.toLowerCase()+"-")==true))
			{
				//System.out.println("Word is in"+glword);
		//System.out.println(sent);
				String[] words = sent.get(i).split("[ \n\t\r.,;:!?(){}]");
				for (int j = 0; j < words.length; j++) 
				{
					String value = words[j].toLowerCase();
					String value1 = words[j].trim();
					int alphanum=0;
					int alphanum1=0;
					Pattern ALPHANUMERIC = Pattern.compile("[A-Za-z0-9]+");
					if (words[j].length() > 0)
					{
						Matcher m = ALPHANUMERIC.matcher(value1);
						if(m.matches()==false)
						alphanum++;
					}          
					
					if(alphanum==0)
		                                {
						 if(((stopset.contains(value)!=true)&&(counttemp.containsKey(value)!=true))&&(((tempsplit.length==1)&&(!tempsplit[0].equals(value)))||((tempsplit.length==2)&&(!tempsplit[0].equals(value))&&(!tempsplit[1].equals(value)))||((tempsplit.length==3)&&(!tempsplit[0].equals(value))&&(!tempsplit[1].equals(value))&&(!tempsplit[2].equals(value))))){
							 //System.out.println("Count1"+value);
					            counttemp.put(value, 1);
					        }
						 else if((stopset.contains(value)!=true)&&(counttemp.containsKey(value)==true)){
					            counttemp.put(value, counttemp.get(value)+1);
					            //System.out.println("Count"+value);
					        }
					        			
						}
					
				}
				ArrayList<countstruct> strtryy = new ArrayList<countstruct>();
				for(Entry f : counttemp.entrySet()){
					Integer countrel =0;
					if(counts.containsKey((String)f.getKey()))
					{

					//if(al.get(y).equals(wordlist.get(g)))
					countrel=counts.get((String)f.getKey());
					}
					strtryy.add(new countstruct((String)f.getKey(),(Integer)f.getValue(),countrel));
					tempp++;            }
				int gii=0;
				//List<String> list = ..;
				strtryy1 = strtryy.toArray(new countstruct[strtryy.size()]);
				//Object[] strtrytempp=strtryy.toArray();
				//structure[] strtryy1 = new structure[tempp];
				//System.arraycopy(strtrytempp, 0, strtryy1, 0, tempp);
				Arrays.sort(strtryy1);
				System.out.println(strtryy1);
				
				/*
				for(String s1 : relwords){
			        if(!counttemp.containsKey(s1)){
			            counttemp.put(s1, 1);
			        }
			        else{
			            counttemp.put(s1, counttemp.get(s1) + 1);
			        }

				}
				*/
			}
		}
		int templ;
		if (strtryy1.length<200)
			templ=strtryy1.length;
		else
			templ=200;
		//System.out.println("First Word"+strtryy1[0].word);
		if(((tempsplit.length==1)||(tempsplit.length==2)||(tempsplit.length==3))&&(!stopset.contains(glword)))
		{
			System.out.println("Inside loop");
		for(int a=0;a<templ;a++)
		{
			//System.out.println("Relwords :"+strtryy1[a].word+" "+strtryy1[a].ln);
			sortword[a]=strtryy1[a].word;
			sorto[a]=strtryy1[a].ln;
		}
		}
		
	listbox3.setListData(sorto);
	listbox2.setListData(sortword);
	
	listbox2.validate();
	listbox2.repaint();

	listbox3.validate();
	listbox3.repaint();
	
	
	//listbox1.addListSelectionListener(listSelectionListener);

	    MouseListener mouseListener = new MouseAdapter() {
	      public void mouseClicked(MouseEvent mouseEvent) {
	        JList theList = (JList) mouseEvent.getSource();
	        if (mouseEvent.getClickCount() == 2) {
	          int index = theList.locationToIndex(mouseEvent.getPoint());
	          if (index >= 0) {
	            Object o = theList.getModel().getElementAt(index);
	            System.out.println("Double-clicked on: " + o.toString());
	            wtv.query(0,o.toString(),1);
	        	getRootPane().validate();
	        	getRootPane().repaint();
	          }
	        }
	      }
	    };
	    listbox.addMouseListener(mouseListener);
	    /*
	listbox.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent evt) {
	boolean adjust = evt.getValueIsAdjusting();
	if(!adjust){
		System.out.println("Working");
	       String sel = listbox.getSelectedValue().toString();
	       //String temp[]=sel.split(" ");
	       System.out.println(sel);
	wtv.query(0,sel,1);
	getRootPane().validate();
	getRootPane().repaint();
	}
	      }
	    });
	*/
	this.validate();
	this.repaint();
}
public class JavaLocationRenderer extends DefaultListCellRenderer {
	  
	  public Component getListCellRendererComponent(JList list,
	                                                Object value,
	                                                int index,
	                                                boolean isSelected,
	                                                boolean hasFocus) {
	    JLabel label =
	      (JLabel)super.getListCellRendererComponent(list,
	                                                 value,
	                                                 index,
	                                                 isSelected,
	                                                 hasFocus);
	    
	    if (value instanceof String) {
	    	if(value.equals("")==false)
	    	{
	        String location = (String)value+" ";
	        for (int i = 0; i < sent.size(); i++)
				if((sent.get(i).contains(location.toLowerCase())==true)&&(sent.get(i).contains(glword.toLowerCase()+" ")==true))
			
			
	       
	       
	        	setBackground(Color.YELLOW);
	        
	        }}
	  
	    return this;
	  }
	}
	public static void main(String[] args) {

	}

}


