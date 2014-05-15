package ControlPanel;

public class sort3 implements Comparable{

String worda;
String wordb;
String wordc;
int occ;
int docnmbr;

sort3(String worda, String wordb, String wordc, Integer occ){
this.worda=worda;
this.wordb=wordb;
this.wordc=wordc;
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