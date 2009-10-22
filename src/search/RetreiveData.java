package search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

import dao.CrudDAO;
import dbconn.DBConnector;




public class RetreiveData {
	
	public HashMap<String, HashMap<String, String>> extractWhereClause(){
		//"whereClausefrmRequest"
		HashMap<String, HashMap<String,String>> whereClause= new HashMap<String, HashMap<String,String>>();
		String strWhereClause = "empid!10~#empname!samarjit";
		String[] arWhere = strWhereClause.split("~#");
		HashMap<String,String> whereClausePart = new HashMap<String, String>();
		for(String strWhrKVpair:arWhere){
			String[] kvpair = strWhrKVpair.split("!");
			whereClausePart.put(kvpair[0], kvpair[1]);
			
			whereClause.put("frmRequest", whereClausePart);
		}
		return whereClause;
	}
	 
	/**
	 * Expecting whereClause[PanelName]=field!value~#field1!value1
	 * @param args
	 */
	public static void main(String[] args) {
		
		RetreiveData rd = new RetreiveData();
		CrudDAO cd = new CrudDAO();
		List <String> lstPanelName = cd.findPanelByScrname("frmRequest");
		
	    	HashMap metadata = new HashMap();
	    	HashMap<String, HashMap<String, String>> hmWhere = rd.extractWhereClause();
		String sg =	rd.createRetrieveQuery(metadata , "frmRequest",lstPanelName.get(0),hmWhere.get("frmRequest"));
		System.out.println(sg);
	} 
	private String createRetrieveQuery(HashMap metadata,String scrname,String panelName, HashMap<String,String> hmWherePanel) {
		String searchQuery ="SELECT ";
		String joiner = " WHERE ";
		
		CrudDAO cd = new CrudDAO();
		searchQuery += cd.createRetrieveQueryPart1(metadata,scrname,panelName);
		searchQuery += " FROM "+ cd.findTableByPanels(scrname,panelName);
		String splWhereClause = cd.findSplWhereClsOfPanels(scrname,panelName);
		
		if(splWhereClause != null && ! "".equals(splWhereClause) ){
			searchQuery += joiner + splWhereClause;
			joiner =" AND ";
		}
		//process where clause
		String strWhereQuery = cd.createWhereClause(joiner,scrname,panelName,hmWherePanel);
		searchQuery += strWhereQuery;
		return searchQuery;
	}
	

}
