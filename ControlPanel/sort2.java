package ControlPanel;

public class sort2 implements Comparable{

String worda;
String wordb;
int occ;
int docnmbr;

sort2(String worda, String wordb, Integer occ){
this.worda=worda;
this.wordb=wordb;
this.occ=occ;
}

public void putdoc(int doc1)
{

this.docnmbr=doc1;
}

public int compareTo(Object obj)
{
sort2 tmp = (sort2)obj;
if(this.occ > tmp.occ)
{
return -1;
} 
else if(this.occ < tmp.occ)
{
return 1;
}
return 0; 
}


}