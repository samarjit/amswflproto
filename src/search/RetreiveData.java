package search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

import dao.CrudDAO;
import dbconn.DBConnector;




public class RetreiveData {
	
	public void log(String s){
		System.out.println(s);
	}
	/**
	 * @return where clause as key value pair in hashmaps, these hashmaps are again encapsulated into 
	 * another hashmap whose key will be panelName
	 */
	public HashMap<String, String> extractWhereClause(/*String whereStringOfPanel String panelName */){
		//"whereClausefrmRequest"
		
		String strWhereClause = "empid!0~#empname!sam";
		String[] arWhere = strWhereClause.split("~#");
		HashMap<String,String> whereClausePart = new HashMap<String, String>();
		for(String strWhrKVpair:arWhere){
			String[] kvpair = strWhrKVpair.split("!");
			whereClausePart.put(kvpair[0], kvpair[1]);
		}
		return whereClausePart;
	}
	
	public void doRetrieveData() throws Exception{
		CrudDAO cd = new CrudDAO();
		HashMap metadata = null;
		List <String> lstPanelName = cd.findPanelByScrname("frmRequest");
		HashMap<String, String> hmWhere = extractWhereClause(/*String whereStringOfPanel, String panelName */);
		String html = "";
		CachedRowSet crs = null;
		Iterator itrPanel = lstPanelName.iterator();
		while (itrPanel.hasNext())
		{   metadata = null;
			String panelName = (String) itrPanel.next();
			log("******** calling creteRetreiveQuery using:metadata:"+metadata+ " panel name:"+panelName+ " hmWhere:"+hmWhere);
			String sg = createRetrieveQuery(metadata, "frmRequest",	panelName, hmWhere);
	//		log("Select query:" + sg);
			
			String tableHeader = "";
			try {
	//			crs = cd.executeRetrieveQuery(sg);
				html = "";
				boolean firstItr = true;
				String data = "";
//				while (crs.next()) {
//
//					html += "\n<tr >";
//					if (firstItr) {
//						tableHeader += "\n<tr >";
//					}
//					
//					if(metadata == null)throw new Exception(" Retreive Data : metadata null");
//					Iterator itrmetadata = metadata.keySet().iterator();
//					while (itrmetadata.hasNext()) {
//						String fname = (String) itrmetadata.next();
//						ListAttribute ls = (ListAttribute) metadata.get(fname);
//						log("Fname="+fname);
//						data = crs.getString(fname);
//
//						if (firstItr) {
//							tableHeader += "<th><div id=" + fname
//									+ " style='display:none'>" + ls + "</div>"
//									+ ls.getLblname() + "</th>";
//						}
//						html += "<td id=" + fname + "> " + data + "</td>";
//					}
//					if (firstItr) {
//						tableHeader += "</tr>";
//						firstItr = false;
//					}
//					html += "</tr>";
//				}
			} catch (Exception e) {
				e.printStackTrace();
				tableHeader = "No data found";
			}finally{
				
			}
			
			html += "<table border=1>" + tableHeader + html + "</table>";
			
		}
		try {
			if(crs!=null)
			crs.close();
		} catch (Exception e) {
			log(e.getMessage());
			e.printStackTrace();
		}
		log(html);
	}
	
	/**
	 * Expecting whereClause[PanelName]=field!value~#field1!value1
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		RetreiveData rd = new RetreiveData();
		rd.doRetrieveData();
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
