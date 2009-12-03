package crud;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.json.JSONException;

import util.Utility;
import dao.CrudDAO;

public class InsertData {
	public void debug(int priority, String s){
		if(priority >0)
		System.out.println(s);
	}
	/**
	 * main function is used only for testing doDelete() is the method that does 
	 * really works
	 * @param args
	 */
	
	public String doInsert(String screenName, String insertClause){
		CrudDAO cd = new CrudDAO();
		HashMap metadata = null;
		String scrName=screenName;
		List <String> lstPanelName = cd.findPanelByScrname(scrName);
		//HashMap<String, String> hmWhere = Utility.extractWhereClause( insertClause/*String whereStringOfPanel, String panelName */);
		String html = ""; //outer
		String htmlTemp = "";
		CachedRowSet crs = null;
		int insertResult = 0;
		debug(0, "lstPanelName:"+lstPanelName);
		Iterator itrPanel = lstPanelName.iterator();
		if (itrPanel.hasNext())
		{ 
			String panelName = (String) itrPanel.next();
			debug(0, "******** calling creteInsertQuery panel name#"+panelName + "insertClause" + insertClause);
		    //if you allocate the HashMap inside createRetrieveQuery1 then it returns null by the time it comes here
			metadata = new HashMap();
			//column metadata should get populated here
			String sg = createInsertQuery(metadata, scrName, panelName,insertClause );
			if(sg != null && !("".equals(sg))){
				try {
					insertResult  = cd.executeInsertQuery(sg);
					debug(1,"inserted successfully");
				} catch (Exception e) {
					debug(5,"Failed in insert");
					e.printStackTrace();
					insertResult  = -1;
				}
				debug(0, "Insert query:" + sg);
			}						
		}
		html = String.valueOf(insertResult);
		return html;
	}
	
	public String createInsertQuery(HashMap metadata,String scrname,String panelName, String insertClause) {
		String insertQry = "";
		
		CrudDAO cd = new CrudDAO();
	 
		HashMap insertMap = null;
		try {
			HashMap<String, HashMap<String,String>> keyvvaltemp = Utility.extractKeyValPair(insertClause);
			if(keyvvaltemp.containsKey(panelName))
				insertMap = keyvvaltemp.get(panelName);
			else
				return "";	
		} catch (JSONException e) {
			debug(5,"Failed to extract insertClause");
			e.printStackTrace();
		}
		
		HashMap qryPart1 = cd.createInsertQueryPart1(metadata,scrname,panelName,insertMap );
		String tableName =  cd.findTableByPanels(scrname,panelName);
		String splWhereClause = cd.findSplWhereClsOfPanels(scrname,panelName);
		  		
		debug(0,"table name:"+tableName);
		
		if(tableName!= null && tableName.length() >0 && qryPart1 !=null && qryPart1.size() > 0  && qryPart1.get("valuestr") != null){
			insertQry ="INSERT INTO "+tableName+"("+qryPart1.get("dbcolstr")+") VALUES ("+qryPart1.get("valuestr")+")"; 
		}else {
			debug(0, "Incomplete query was:"+"INSERT INTO "+tableName+"("+qryPart1.get("dbcolstr")+") VALUES ("+qryPart1.get("valuestr")+")");
		}
		return insertQry;
	}
}
