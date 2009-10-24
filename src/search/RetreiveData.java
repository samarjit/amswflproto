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
		
		String strWhereClause = "empid!0~#empname!sam samanta";
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
		String html = ""; //outer
		String htmlTemp = "";
		CachedRowSet crs = null;
		log("lstPanelName:"+lstPanelName);
		Iterator itrPanel = lstPanelName.iterator();
		
		//For every panel in the screen create an XML table 
		while (itrPanel.hasNext())
		{ 
			String panelName = (String) itrPanel.next();
			log("******** calling creteRetreiveQuery panel name#"+panelName+ " hmWhere:"+hmWhere);
		    //if you allocate the HashMap inside createRetrieveQuery1 then it returns null by the time it comes here
			metadata = new HashMap();
			//column metadata should get populated here
			String sg = createRetrieveQuery(metadata, "frmRequest",	panelName, hmWhere);
			log("Retrieve query:" + sg);
			
			String tableHeader = "No data found";
			
			if (sg != null && sg.length() > 0) 	{
				try {
					crs = cd.executeRetrieveQuery(sg);
					htmlTemp = "";
					boolean firstItr = true;
					String data = "";
					
					//Ideally this loop should run once in case of detail-data retrieval
					while (crs.next()) {
log("stupid!!!");
						htmlTemp += "\n<tr >";
						if (firstItr) {
							tableHeader = "\n<tr >";
						}

						if (metadata == null)
							throw new Exception(
									" Retreive Data : metadata null");
						Iterator itrmetadata = metadata.keySet().iterator();
						while (itrmetadata.hasNext()) {
							String fname = (String) itrmetadata.next();
							ListAttribute ls = (ListAttribute) metadata
									.get(fname);
							log("Fname=" + fname);
							data = crs.getString(fname);

							if (firstItr) {
								tableHeader += "<th><div id=" + fname
										+ " style='display:none'>" + ls
										+ "</div>" + ls.getLblname() + "</th>";
							}
							htmlTemp += "<td id=" + fname + "> " + data + "</td>";
						}
						if (firstItr) {
							tableHeader += "</tr>";
							firstItr = false;
						}
						htmlTemp += "</tr>";System.out.println(htmlTemp);
					} //while crs.next()
					
				} catch (Exception e) {
					e.printStackTrace();
					htmlTemp = "";
					tableHeader = "No data found";
				} finally {
					try {
						if(crs!=null)
						crs.close();
					} catch (Exception e) {
						log(e.getMessage());
						e.printStackTrace();
					}
				}
			} // if sg.length >0
			html += "<table border=1 id='"+panelName+"'>" + tableHeader + htmlTemp + "</table>\n";
			
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
	
	/**
	 * @param metadata [passed on]empty initially passed on to be filled createRetrieveQueryPart1 
	 * @param scrname [input]
	 * @param panelName [input]
	 * @param hmWherePanel [passed to createWhereClause]
	 * @return
	 */
	private String createRetrieveQuery(HashMap metadata,String scrname,String panelName, HashMap<String,String> hmWherePanel) {
		String searchQuery ="";
		
		
		CrudDAO cd = new CrudDAO();
		String qryPart1 = cd.createRetrieveQueryPart1(metadata,scrname,panelName);
		String tableName =  cd.findTableByPanels(scrname,panelName);
		String splWhereClause = cd.findSplWhereClsOfPanels(scrname,panelName);
		  
		String joiner = " WHERE ";
		if(splWhereClause != null && ! "".equals(splWhereClause) ){
			splWhereClause += joiner + splWhereClause+" ";
			joiner =" AND ";
		}else{
			splWhereClause =" ";
		}
		//process where clause
		String strWhereQuery  = cd.createWhereClause(joiner,scrname,panelName,hmWherePanel,true);
		System.out.println("strWhereQuery="+strWhereQuery+"table name:"+tableName);
		
		if(tableName!= null && tableName.length() >0 && strWhereQuery!=null && strWhereQuery.length()>0)
		searchQuery ="SELECT  "+qryPart1 +" FROM "+tableName+splWhereClause+strWhereQuery;
		else log("Incomplete query was:"+"SELECT  "+qryPart1 +" FROM "+tableName+splWhereClause+strWhereQuery);
		
		return searchQuery;
	}
	

}
