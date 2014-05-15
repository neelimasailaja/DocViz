package HTrail;

import java.awt.geom.*;
import java.awt.*;

public class LinkedList1 {
	int id=0;
   
    public LinkedList1( ) {
        header = new ListNode( null );
        header.id=0;
    }
    
 
    public boolean isEmpty( ) {
        return header.next == null;
    }
    

    public void makeEmpty( ) {
        header.next = null;
    }
    
  
    public LinkedListIterator zeroth( ) {
        return new LinkedListIterator( header );
    }
    
  
    public LinkedListIterator first( ) {
        return new LinkedListIterator( header.next );
    }
    
   
    public void insert( Object x, LinkedListIterator p ) {
        if( p != null && p.current != null )
            p.current.next = new ListNode( x, p.current.next,p.current );
        p.current.next.id=id++;
    }
    
  
    public LinkedListIterator find( Object x ) {
        ListNode itr = header.next;
        
        while( itr != null && !itr.element.equals( x ) )
            itr = itr.next;
        
        return new LinkedListIterator( itr );
    }
    
 
    public LinkedListIterator findPrevious( Object x ) {
        ListNode itr = header;
        
        while( itr.next != null && !itr.next.element.equals( x ) )
            itr = itr.next;
        
        return new LinkedListIterator( itr );
    }
    public String findPrev( Object x ) {
        ListNode itr = header;
        
        while( itr.next != null && !itr.next.element.equals( x ) )
            itr = itr.next;
        
        return itr.element.toString();
    } 
 
    public void remove( Object x ) {
        LinkedListIterator p = findPrevious( x );
        
        if( p.current.next != null )
            p.current.next = p.current.next.next;  
    }
    
    public static void printList( LinkedList1 theList ) {
        if( theList.isEmpty( ) )
            System.out.print( "Empty list" );
        else {
            LinkedListIterator itr = theList.first( );
            for( ; itr.isValid( ); itr.advance( ) )
                System.out.print( itr.retrieve( ) + " " );
        }
        
        System.out.println( );
    }
    
    private ListNode header;

    public static int listSize( LinkedList1 theList ) {
        LinkedListIterator itr;
        int size = 0;
        
        for( itr = theList.first(); itr.isValid(); itr.advance() )
            size++;
        
        return size;
    }
    
    public static void addEllipse( LinkedList1 theList, Ellipse2D.Double el, LinkedListIterator itr) {
        
        itr.current.circle=el;
       // itr.current.x=x1;
       // itr.current.y=y1;

       }
  public static Rectangle getRect( LinkedList1 theList, LinkedListIterator itr) {
        
        return itr.current.rect=new Rectangle(itr.current.rx,itr.current.ry,80,itr.current.rh);
       // itr.current.x=x1;
       // itr.current.y=y1;

       }
  public static Rectangle getfRect( LinkedList1 theList, LinkedListIterator itr) {
      
      return itr.current.rect=new Rectangle(itr.current.rx,itr.current.ry,80,itr.current.rh);
     // itr.current.x=x1;
     // itr.current.y=y1;

     }
  public static void setRect( LinkedList1 theList, LinkedListIterator itr, Integer h) {
	  itr.current.rh=h;
       itr.current.rect=new Rectangle(itr.current.rx,itr.current.ry,80,h);
     // itr.current.x=x1;
     // itr.current.y=y1;

     }
  public static String getText( LinkedList1 theList, LinkedListIterator itr) {
      
      return itr.current.note;
      //=new Rectangle(itr.current.rx,itr.current.ry,60,40);
     // itr.current.x=x1;
     // itr.current.y=y1;

     }
  public static void addCoord( LinkedList1 theList, LinkedListIterator itr,Integer x1,Integer y1) {
        
        //itr.current.circle=el;
        itr.current.x=x1;
        itr.current.y=y1;

       } 

}
