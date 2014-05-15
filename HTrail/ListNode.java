package HTrail;

import java.awt.geom.Ellipse2D;
import java.awt.Rectangle;
import java.util.*;
import java.lang.*;


public class ListNode {
	
    public ListNode( Object theElement ) {
        this( theElement, null,null );
    }
    
    public ListNode( Object theElement, ListNode n , ListNode n1) {
        element = theElement;
        next    = n;
        before=n1;
    }
    
    public Object   element;
    public Ellipse2D.Double circle = new Ellipse2D.Double();
    public Rectangle rect;
    public ListNode next;
    public ListNode before;
	public int id;
	public int x;
	public int y;
	public int hasrect=0;
	public int rx;
	public int ry;
	public int rh;
	public String note="Note : ";
	public int hastext=0;
	public int tx;
	public int ty;
}