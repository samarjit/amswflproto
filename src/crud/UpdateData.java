package crud;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.json.JSONException;

import util.Utility;
import dao.CrudDAO;

public class UpdateData {
	private void debug(int priority, String s){
		if(priority > 1){
			System.out.println("UpdateData:"+s);
		}
	}
	/**
	 * main function is used only for testing doDelete() is the method that does 
	 * really works
	 * @param args
	 */
	
	public String doUpdate(String screenName, String insertClause, String whereclause) throws JSONException{
	 
		CrudDAO cd = new CrudDAO();
		HashMap metadata = null;
		String scrName=screenName;
		List <String> lstPanelName = cd.findPanelByScrname(scrName);
		HashMap<String, String> hmWhere = Utility.extractWhereClause(whereclause);
		String html = ""; //outer
		String htmlTemp = "";
		CachedRowSet crs = null;
		debug(0, "lstPanelName:"+lstPanelName);
		Iterator itrPanel = lstPanelName.iterator();
		int queryResult = 0;
		if (itrPanel.hasNext())
		{ 
			String panelName = (String) itrPanel.next();
			debug(0, "******** calling creteUpdateQuery panel name#"+panelName+ " hmWhere:"+hmWhere);
		    //if you allocate the HashMap inside createRetrieveQuery1 then it returns null by the time it comes here
			metadata = new HashMap();
			//column metadata should get populated here

			String sg = createUpdateQuery(metadata,scrName,panelName, insertClause, hmWhere);
			debug(2, "Update query:" + sg);
			if(sg != null && !("".equals(sg))){
				try {
					queryResult = cd.executeInsertQuery(sg);
				} catch (Exception e) {
					debug(5,"Failed in update");
					e.printStackTrace();
					queryResult = -1;
				}
				 
			}		
			
		}
		
		html = String.valueOf(queryResult);
		return html;
	}
	
	public String createUpdateQuery(HashMap metadata,String scrname,String panelName,String insertClause,HashMap<String,String> hmWherePanel) {
		
		 
		String updateQuery = "";
		String joiner = " WHERE ";
		CrudDAO cd = new CrudDAO();
	 
		HashMap updateMap=null;
		try {
			HashMap<String, HashMap<String,String>> keyvvaltemp = Utility.extractKeyValPair(insertClause);
			
			if(keyvvaltemp.containsKey(panelName))
				updateMap = keyvvaltemp.get(panelName);
			else
				return "";	
			debug(0, updateMap.toString());
		} catch (JSONException e) {
			System.out.println("Failed to extract update key val pair");
			e.printStackTrace();
		}
		String qryPart1 = cd.createUpdateQueryPart1(metadata,scrname,panelName,updateMap );
		String tableName =  cd.findTableByPanels(scrname,panelName);
		String splWhereClause = cd.findSplWhereClsOfPanels(scrname,panelName);
		  
		
		if(splWhereClause != null && ! "".equals(splWhereClause) ){
			splWhereClause += joiner + splWhereClause+" ";
			joiner =" AND ";
		}else{
			splWhereClause =" ";
		}
		//process where clause
		String strWhereQuery  = cd.createWhereClause(joiner,scrname,panelName,hmWherePanel,true);
		debug(0, "strWhereQuery= "+strWhereQuery+"table name:"+tableName+"query part "+qryPart1);
		
		if(tableName!= null && tableName.length() >0 && qryPart1 !=null && qryPart1.length() > 0 && strWhereQuery!=null && strWhereQuery.length()>0){
			//updateQuery ="UPDATE  SET " +qryPart1+ " FROM "+tableName+splWhereClause+strWhereQuery; 
			
			updateQuery ="UPDATE " +tableName+" SET " +qryPart1+"   "+splWhereClause+strWhereQuery; 
			
		}else {
			debug(1, "Incomplete query was:"+"UPDATE " +tableName+" SET " +qryPart1+"   "+splWhereClause+strWhereQuery);
		}
		return updateQuery;
	}
}
