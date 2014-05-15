package HTrail;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class LinkedListIterator {
    LinkedListIterator( ListNode theNode ) {
        current = theNode;
    }

    public boolean isValid( ) {
        return current != null;
    }
    
    public Object retrieve( ) {
        return isValid( ) ? current.element : null;
    }
    
    public ListNode advance( ) {
        if( isValid( ) )
            current = current.next;
         return current;
    }
    public ListNode goback( ) {
        if( isValid( ) )
            current = current.before;
         return current;
    }  
    
    public Ellipse2D.Double getEllipse( ) {
        return isValid( ) ? current.circle : null;
    }
    
    public Rectangle getRect( ) {
        return isValid( ) ? current.rect : null;
    }
    public Integer retrieveid( ) {
        return isValid( ) ? current.id : null;
    }
    public int hasRect() {
        return current.hasrect;
       }
    public int hasText() {
        return current.hastext;
       }
    
    ListNode current;   
}
