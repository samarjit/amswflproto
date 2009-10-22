package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import search.ListAttribute; 

import dbconn.DBConnector;

public class CrudDAO {
	
	public String findSplWhereClsOfPanels(String screenName,String panelName){
		String SQL = 
			"SELECT splwhereclause   FROM  screen_panel where scr_name='"+screenName+"' and panel_name='"+panelName+"' ";
		DBConnector db = new DBConnector();
		String tableNames = "";
		try {
			CachedRowSet crs = db.executeQuery(SQL);
			while(crs.next()){
				tableNames = crs.getString("splwhereclause");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 	tableNames;
	}
	
	public List<String> findPanelByScrname(String screenName){
		String SQL = 
			"SELECT panel_name   FROM  screen_panel where scr_name='"+screenName+"' order by SORTORDER ASC";
		DBConnector db = new DBConnector();
		List<String> panelNames = new ArrayList<String>();
		try {
			CachedRowSet crs = db.executeQuery(SQL);
			while(crs.next()){
				panelNames.add( crs.getString("panel_name"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 	panelNames;
	}
	
	
	
	public String findTableByPanels(String screenName,String panelName){
		String SQL = 
			"SELECT table_name   FROM  screen_panel where scr_name='"+screenName+"' and panel_name='"+panelName+"' ";
		DBConnector db = new DBConnector();
		String tableNames = "";
		try {
			CachedRowSet crs = db.executeQuery(SQL);
			while(crs.next()){
				tableNames = crs.getString("table_name");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 	tableNames;
	}
	
	public String createRetrieveQueryPart1(HashMap metadata,String scrname,String panelName) {
		DBConnector db = new DBConnector();
		String searchQuery = "";
		
		
		
		String SQL2 = 
			"select lblname,fname,idname,dbcol,datatype,classname,prkey from panel_fields where  scr_name='"+scrname+"' and panel_name='"+panelName+"'";
	//System.out.println(SQL2); 
		 try {
				CachedRowSet crs = db.executeQuery(SQL2);
			
				
				while(crs.next()){ 
					ListAttribute ls = new ListAttribute();
					ls.setClassname(crs.getString("classname"));
					ls.setDatatype(crs.getString("datatype"));
					ls.setDbcol(crs.getString("dbcol"));
					ls.setFname(crs.getString("fname"));
					ls.setIdname(crs.getString("idname"));
					ls.setLblname(crs.getString("lblname"));
					ls.setPrkey(crs.getString("prkey"));
					
					metadata.put(crs.getString("idname"), ls) ;
					
					searchQuery +=crs.getString("dbcol")+" "+crs.getString("idname")+",";
				}
				if(searchQuery.length() > 0){
					searchQuery = searchQuery.substring(0, searchQuery.length()-1);
				}
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return searchQuery;
	}

	public String createWhereClause(String joiner, String scrname,String panelName,HashMap<String, String> hmWherePanel) {
		String strWhereQuery = "";
		DBConnector db = new DBConnector(); 
		Iterator itr = hmWherePanel.keySet().iterator();
        
       
        while(itr.hasNext()){
        	String fname = (String) itr.next();
        	String val =  hmWherePanel.get(fname);
        	String SQL = 
            	"select dbcol,datatype from panel_fields where  scr_name='"+scrname+"' and " +
            			"panel_name='"+panelName+"' " +
            			"and UPPER(fname)=UPPER('"+fname+"')";
        	CachedRowSet crs;
			try {
				crs = db.executeQuery(SQL);
			
	        	if(crs.next())	        	{
	        		String dbcol = crs.getString("dbcol");
	        		String datatype = crs.getString("datatype");
	        		
	        		if(null != datatype && !"".equalsIgnoreCase(datatype)){
	        			if("DATE".equalsIgnoreCase(datatype)){
	        				//MySQL????
	        				//Oracle 
	        				
	        			}
	        		}
	        		if(!"".equals(strWhereQuery)){
	        			joiner = " AND ";
	        		}
	        		strWhereQuery +=joiner+dbcol+" like '%"+val+"%'";
	        	}
	        	crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		
		return strWhereQuery;
		 
		
	}

}
