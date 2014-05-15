package ControlPanel;

public class countstruct implements Comparable{

		public String word;
		public int ln;
		public int doc;
		public int mastercount;
		double compmetric;

		public countstruct(String word1, int ln1, int count)
		{

		this.word=word1;
		this.ln=ln1;
		this.mastercount=count;
		this.compmetric=0.5+(0.5*(ln/mastercount));
		}
       
		public void putdoc(int doc1)
		{

		this.doc=doc1;
		}
		
		public int compareTo(Object obj)
		{
			countstruct tmp = (countstruct)obj;
		if(this.compmetric > tmp.compmetric)
		{
		return -1;
		} 
		else if(this.compmetric < tmp.compmetric)
		{
		return 1;
		}
		return 0; 
		}

		 public String getName() {
		        return this.word;
		    }

		 public String toString(){
		        return this.word+"--"+this.compmetric;
		    }



		}


