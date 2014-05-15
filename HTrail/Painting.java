package HTrail;
import java.awt.*;

import javax.swing.*;

import WordTreePack.WordTreeView;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
public class Painting extends JPanel implements MouseListener,MouseMotionListener, KeyListener, FocusListener {
	
	

       static LinkedList1         theList = new LinkedList1( );
       LinkedListIterator theItr;
   	Note tempnote=new Note("",0,0);
       Integer opaid;
       WordTreeView wtview;
       LinkedList1         revList = new LinkedList1( );
       BufferedImage bimage;
       public static int x1,y1,flag=0, rightclick=0,dragged=1, noteid=0,noteflag=0,notedrag=0,enterpressed=0;
       public static String prev,note="";
       ArrayList<Note> notearray= new ArrayList<Note>();
       public Painting(WordTreeView parent){
    	   setBackground(Color.white);
        wtview=parent;
        int i;
        String s;
        theItr = theList.zeroth( );
        theList.printList( theList );
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addFocusListener(this);
        this.setFocusable(true);
        this.setBorder(BorderFactory.createTitledBorder("History"));
  }

public void makeLL(String s1){
	if(flag==0)
	{
		theList.insert( s1, theItr );
		theItr.advance();
		theList.printList( theList );
		repaint();
		System.out.println("makeLL is working");
		flag=1;
		}
	else if(flag==1)
if(!s1.equals(theList.findPrev(theItr)))
{
theList.insert( s1, theItr );
theItr.advance();
theList.printList( theList );
repaint();
System.out.println("makeLL is working");
}
prev=s1;
}

public void paintComponent(Graphics g)
{
super.paintComponent(g);
Graphics2D g2 = (Graphics2D)g;
bimage = rdraw();

g2.drawImage(bimage, null, 0, 0);
}

public BufferedImage rdraw()
{
	
 BufferedImage bimage =
  new BufferedImage(1000, 500,
                    BufferedImage.TYPE_INT_ARGB);
y1=490;
Random rand = new Random(); 
    Graphics2D g2d = bimage.createGraphics();
    LinkedListIterator theItr1=theList.zeroth( );
    LinkedListIterator theItrlast;
    	theItr1.advance();
    theItrlast=theItr1;
    int newy,newh;;
    if(notearray.size()>=1)
    {
     for(int i=0;i<notearray.size();i++)    
    
	{
    	 Note tempn=notearray.get(i);
    		//g2d.setColor(Color.yellow);
    		//g2d.fill(notearray.get(i).rect);
    	 g2d.setColor(Color.black);
		String temp1=tempn.getText(tempn);
		newy = drawStringRect(g2d, tempn.x,tempn.y, tempn.x+80, tempn.y+20+90,1, temp1,tempn.h);
			notearray.get(i).setRect(notearray.get(i).x,notearray.get(i).y,newy-notearray.get(i).y);
			g2d.setColor(Color.yellow);
    		g2d.fill(notearray.get(i).rect);
    		g2d.setColor(Color.black);
    		drawStringRect(g2d, tempn.x,tempn.y, tempn.x+80, tempn.y+20+90,1, temp1,tempn.h);
		
		//Rectangle rect1=tempn.getRect(notearray.get(i));
		
	}
     }
if(theItr1.isValid()==false) 
{

}
else
{
	int prevx=theItr1.current.x, prevy=theItr1.current.y;
for(int i=0;i<theList.listSize( theList );i++)
{
	
	x1 = rand.nextInt(26) + 50; 
	if((theItr1.current.x==0)&&(theItr1.current.y==0))
	theList.addCoord(theList, theItr1,x1,y1);
	Ellipse2D.Double circle = new Ellipse2D.Double(theItr1.current.x,theItr1.current.y,10,10);
theList.addEllipse(theList, circle, theItr1);
g2d.setColor(((i%2)==0)?Color.black:Color.black);
if(theItr1.retrieveid()!=opaid)
	  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
else g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.9f));
//System.out.println(opaid+" "+opaid);
//System.out.println(theItr1.retrieveid());

g2d.fill(circle);
//System.out.println(theItr1.retrieve());
if((theItr1.current.x==0)&&(theItr1.current.y==0))
g2d.drawString(theItr1.retrieve().toString(),x1,y1-5);
else
g2d.drawString(theItr1.retrieve().toString(),theItr1.current.x,theItr1.current.y-5);
/*
	if(theItr1.hasRect()==1)
	{
	Rectangle rect=theList.getRect(theList,theItr1);
	//theList.addRect(theList, rect, theItr1);
	g2d.draw(rect);
	rightclick=0;
	}
*/
	if(theItr1.hasText()==1)
	{
		g2d.setColor(Color.yellow);
		g2d.fill(theList.getRect(theList,theItr1));
	//Rectangle rect=theList.getRect(theList,theItr1);
	String temp=theList.getText(theList,theItr1);
	//g2d.drawString(temp,theItr1.current.rx,theItr1.current.ry+20);
	g2d.setColor(Color.black);
	rightclick=0;
	newh=drawStringRect(g2d, theItr1.current.rx,theItr1.current.ry, theItr1.current.rx+80, theItr1.current.ry+20+90,1, temp,theItr1.current.rh);
	//theList.getRect(theList,theItr1,newh-theItr1.current.ry);
	g2d.setColor(Color.yellow);
	theList.setRect(theList,theItr1,newh-theItr1.current.ry);
	g2d.setColor(Color.black);
	newh=drawStringRect(g2d, theItr1.current.rx,theItr1.current.ry, theItr1.current.rx+80, theItr1.current.ry+20+90,1, temp,theItr1.current.rh);
	/*AttributedString as = new AttributedString(temp);
    as.addAttribute(TextAttribute.FOREGROUND, g2d.getPaint());
    as.addAttribute(TextAttribute.FONT, g2d.getFont());
    AttributedCharacterIterator aci = as.getIterator();
    FontRenderContext frc = new FontRenderContext(null, true, false);
    LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
    float width = 80;
    int y11=theItr1.current.ry;
    while (lbm.getPosition() < temp.length()) {
        TextLayout tl = lbm.nextLayout(width);
        y11 += tl.getAscent();
        getRootPane().validate();
        
        tl.draw(g2d, theItr1.current.rx, y11);
        
        y11 += tl.getDescent() + tl.getLeading() + (1 - 1.0f) * tl.getAscent();
        if (y11 > theItr1.current.ry+110) {
            break;
        }
    }
    */
    rightclick=0;
	
		System.out.println(theList.getText(theList,theItr1));
	}

/*
if(i==0)
{

	{
	prevx=theItr1.current.x;
	prevy=theItr1.current.y;
	}
}
*/
if(i!=0)
{
	g2d.setColor(Color.black);
	g2d.drawLine(prevx+5,prevy+5,theItr1.current.x+5,theItr1.current.y+5);
	prevx=theItr1.current.x;
	prevy=theItr1.current.y;
	
}
y1-=25;
theItr1.advance();

}

} 
return bimage;



}

public void setNote(String note1){
	note=note1;
}

	//LinkedListIterator theItrmouse=theList.zeroth( );
	LinkedListIterator theItrmouse1;
	int dragnote;
	public void mousePressed(MouseEvent evt)
	{
		int neel=0;
		int push=1;
		LinkedListIterator theItrmouse=theList.zeroth( );
while(theItrmouse.isValid()==true) 
{

if(theItrmouse.getEllipse().contains(evt.getPoint()))
{

neel++;
theItrmouse1=theItrmouse;
}
if(neel==0)
theItrmouse.advance();
else
{ getRootPane().validate();
getRootPane().repaint();
break;
}
}
		
if(neel==0){
	  if(notearray.size()>=1)
	    {
	     for(int i=0;i<notearray.size();i++)    
	    
		{
	    	 Note tempnote = notearray.get(i);
	    	 if(tempnote.getSimpleRect(tempnote).contains(evt.getPoint()))
	    	 {
	    		 dragnote=i;
	    		 dragged=2;
	    		 System.out.println("Pressed Dragged="+dragged);
	    	 }
		}
		}
}
		}
	//public void mouseClicked(MouseEvent evt) {}

	@Override
	public void mouseClicked(MouseEvent evt) {
		
		
		if(evt.getButton() == MouseEvent.BUTTON1){
			
			int neel=0;
			int push=1;
			LinkedListIterator theItrmouse=theList.zeroth( );
	while(theItrmouse.isValid()==true) 
	{

	if(theItrmouse.getEllipse().contains(evt.getPoint()))
	{
	//System.out.println(theItrmouse.retrieve().toString()+" "+theItrmouse.retrieve().toString()+" "+theItrmouse.retrieve().toString());
	opaid=theItrmouse.retrieveid();
	wtview.query(push,theItrmouse.retrieve().toString(),0);
	neel++;
	}
	
	if(neel==0)
	theItrmouse.advance();
	else
	{ getRootPane().validate();
	getRootPane().repaint();
	break;
	}
	}
			  }	
		  
		  if(evt.getButton() == MouseEvent.BUTTON3){
				int neel=0;
				int push=1;
				LinkedListIterator theItrmouse=theList.zeroth( );
		while(theItrmouse.isValid()==true) 
		{

		if(theItrmouse.getEllipse().contains(evt.getPoint()))
		{
			rightclick=1;
			//theItrmouse.current.note="";
			theItrmouse.current.hasrect=1;
			theItrmouse.current.hastext=1;
		//System.out.println(theItrmouse.retrieve().toString()+" "+theItrmouse.retrieve().toString()+" "+theItrmouse.retrieve().toString());
		opaid=theItrmouse.retrieveid();
		theItrmouse.current.rx=theItrmouse.current.x+20;
		theItrmouse.current.ry=theItrmouse.current.y;
		theItrmouse.current.rh=30;
		theItrmouse.current.rect=new Rectangle(theItrmouse.current.rx,theItrmouse.current.ry,80,30);
		neel++;
		noteflag=0;
		}
		
		if(neel==0)
		theItrmouse.advance();
		else
		{ getRootPane().validate();
		getRootPane().repaint();
		break;
		}
		}
		if(neel==0)
		{ 
			Note tempnote1= new Note("",0,0);
			tempnote1.text="Note : ";
			tempnote1.x=evt.getX();
			tempnote1.y=evt.getY();
			tempnote1.h=30;
			tempnote1.setRect(evt.getX(), evt.getY(),30);
			tempnote1.id=noteid++;
			notearray.add(tempnote1);
			noteflag=1;
			getRootPane().validate();
			getRootPane().repaint();
		}
		  }
		  
	}
	

	@Override
	public void mouseEntered(MouseEvent arg0) {
	
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	
		
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		//int xtemp=evt.getX();
		//int ytemp=evt.getY();
//System.out.println("Before"+evt.getX());
//System.out.println("Before"+evt.getY());
		if(	dragged==3)
		{
			notearray.get(dragnote).x=evt.getX();

			//System.out.println("Afterx"+evt.getX());
			//System.out.println("Afterx"+evt.getY());


			notearray.get(dragnote).y=evt.getY();
			dragged=0;
			System.out.println("Released Dragged="+dragged);

		}
		else if(dragged==1){
theItrmouse1.current.x=evt.getX();

//System.out.println("Afterx"+evt.getX());
//System.out.println("Afterx"+evt.getY());


theItrmouse1.current.y=evt.getY();
theItrmouse1.current.rx=evt.getX()+20;
theItrmouse1.current.ry=evt.getY();
dragged=0;
System.out.println("Released Dragged="+dragged);
		}
		
		getRootPane().validate();
		getRootPane().repaint();
	}

	//@Override
	/*
	public void mouseDragged(MouseEvent evt) {
		//int xtemp=evt.getX();
		//int ytemp=evt.getY();
		mouseMoved(evt);

	}
*/
	@Override
	public void mouseMoved(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent evt) {
	      char c = evt.getKeyChar();
	      //String s=new String();
	      if(noteflag!=1)
	      {
	      if ( c != KeyEvent.CHAR_UNDEFINED )
	      {
	    	  theItrmouse1.current.note = theItrmouse1.current.note + c;
	    		getRootPane().validate();
	    		getRootPane().repaint();
	              evt.consume();
	           } 
	      //System.out.println(note+"     "+"Keytypeworking");
	      }
	      else{
	    	  Note temp=notearray.get(notearray.size()-1);
	    	  if ( c != KeyEvent.CHAR_UNDEFINED )
		      {
		    	  
		    	  temp.text += c;
		    		getRootPane().validate();
		    		getRootPane().repaint();
		              evt.consume();
		           } System.out.println(tempnote.text+"     "+"Keytypeworking");
	      }
	      
	}

	public void mouseDragged(MouseEvent evt) {
		// TODO Auto-generated method stub
		if(	dragged==2)
		{
			notearray.get(dragnote).x=evt.getX();

			//System.out.println("Afterx"+evt.getX());
			//System.out.println("Afterx"+evt.getY());


			notearray.get(dragnote).y=evt.getY();
			dragged=3;
			getRootPane().validate();
			getRootPane().repaint();
			System.out.println("Dragged="+dragged);

		}
		else if (dragged==0)
		{
		theItrmouse1.current.x=evt.getX();
		theItrmouse1.current.y=evt.getY();
		theItrmouse1.current.rx=evt.getX()+20;
		theItrmouse1.current.ry=evt.getY();
		dragged=1;
		getRootPane().validate();
		getRootPane().repaint();
		System.out.println("Dragged="+dragged);
		}
		
	}
	
	private int drawStringRect(Graphics2D graphics, int x1, int y1, int x2, int y2, 
	        float interline, String txt, int h) {
	        AttributedString as = new AttributedString(txt);
	        as.addAttribute(TextAttribute.FOREGROUND, graphics.getPaint());
	        as.addAttribute(TextAttribute.FONT, graphics.getFont());
	        AttributedCharacterIterator aci = as.getIterator();
	        FontRenderContext frc = new FontRenderContext(null, true, false);
	        LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
	        float width = x2 - x1;
	        int ret=0;

	        while (lbm.getPosition() < txt.length()) {
	            TextLayout tl = lbm.nextLayout(width);
	            y1 += tl.getAscent();
	            getRootPane().validate();
	            tl.draw(graphics, x1, y1);
	            
	            y1 += tl.getDescent() + tl.getLeading() + (interline - 1.0f) * tl.getAscent();
	         
	            if (y1 > y2) {
	                return y1;
	            }
	           
	        }
			return y1;
	    }
	}





