package ControlPanel;

public class structure implements Comparable{

		public String word;
		public int ln;
		public int doc;

		public structure(String word1, int ln1, int doc1)
		{

		this.word=word1;
		this.ln=ln1;
		this.doc=doc1;
		}
		
		public structure(String word1, int ln1)
		{

		this.word=word1;
		this.ln=ln1;
		//this.doc=doc1;
		}
       
		public void putdoc(int doc1)
		{

		this.doc=doc1;
		}
		
		public int compareTo(Object obj)
		{
		structure tmp = (structure)obj;
		if(this.ln > tmp.ln)
		{
		return -1;
		} 
		else if(this.ln < tmp.ln)
		{
		return 1;
		}
		return 0; 
		}

		 public String getName() {
		        return this.word;
		    }

		 public String toString(){
		        return this.word+"--"+this.ln;
		    }



		}


