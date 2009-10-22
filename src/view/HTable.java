package view;

import java.util.ArrayList;
import java.util.Arrays;

public class HTable {
	
	private int row;
	private int col;
	private ArrayList<ArrayList<String>> tdata;

	public HTable(int row, int col) {
		
			tdata = new ArrayList<ArrayList<String>>();
			ArrayList<String> trow = null;
			for(int i =0 ; i<row;i++){
				String [] a = new String [col];
				trow = new ArrayList<String>(Arrays.asList(a));
				this.tdata.add(trow);
			}
			//System.out.println("allocated size:"+tdata.size()+ " "+tdata.get(0).size());
			this.row = row;
			this.col = col;
			
		}
	public void add(int row,int col, String val){
		//System.out.println("adding:"+row+","+col+" val:"+val);
		ArrayList<String> trow = tdata.get(row);
		String data = trow.get(col);
		if(data == null)data="";
		data += val;
		trow.set(col, data);
	}

	public String toString(){
		String tstring ="";
		for(int i =0 ; i<row;i++){
			ArrayList<String> trow = tdata.get(i);
			tstring +="<TR>";
			for(int j=0; j< col; j++){
				tstring += "<TD >"+((null != trow.get(j))?trow.get(j):"&nbsp;")+"</TD>";
				
			}
			tstring +="</TR>\n";
		}
		tstring ="<TABLE border=1>"+tstring +"</TABLE>";;
		return tstring;
	}


	}