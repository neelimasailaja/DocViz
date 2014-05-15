package HTrail;

import java.awt.Rectangle;

public class Note {

	String text;
	int id;
	public Rectangle rect;
	int x=100;
	int y=50;
	int h=30;
	
	Note(String text1,int x1, int y1){
		this.text=text1;
		this.x=x1;
		this.y=y1;
		this.rect= new Rectangle(x1,y1,80,30);
	}
	
	int getId(Note note1){
		return this.id;
	}
	Rectangle getRect(Note note1){
		return this.rect=new Rectangle(this.x,this.y,80,this.h);
	}
	Rectangle getSimpleRect(Note note1){
		return this.rect;
	}
	String getText(Note note1){
		return this.text;
	}
	void setRect(int x1,int y1,int h){
		this.rect=new Rectangle(x1,y1,80,h);
	}
}
