package crud;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import util.Utility;
import dao.CrudDAO;

public class DeleteData {
	public void log(String s){
		System.out.println(s);
	}
	/**
	 * main function is used only for testing doDelete() is the method that does 
	 * really works
	 * @param args
	 */
	public static void main(String[] args) {
		DeleteData dd = new DeleteData();
		dd.doDelete();

	}
	public void doDelete(){
		CrudDAO cd = new CrudDAO();
		HashMap metadata = null;
		String scrName="frmRequest";
		List <String> lstPanelName = cd.findPanelByScrname(scrName);
		HashMap<String, String> hmWhere = Utility.extractWhereClause( "empid!0~#empname!sam samanta"/*String whereStringOfPanel, String panelName */);
		String html = ""; //outer
		String htmlTemp = "";
		CachedRowSet crs = null;
		log("lstPanelName:"+lstPanelName);
		Iterator itrPanel = lstPanelName.iterator();
		while (itrPanel.hasNext())
		{ 
			String panelName = (String) itrPanel.next();
			log("******** calling creteDeleteQuery panel name#"+panelName+ " hmWhere:"+hmWhere);
		    //if you allocate the HashMap inside createRetrieveQuery1 then it returns null by the time it comes here
			metadata = new HashMap();
			//column metadata should get populated here
			String sg = createDeleteQuery(metadata, scrName,	panelName, hmWhere);
			log("Delete query:" + sg);
			
		}
	}
	
	public String createDeleteQuery(HashMap metadata,String scrname,String panelName, HashMap<String,String> hmWherePanel) {
		String delQuery = "";
		String joiner = " WHERE ";
		
		CrudDAO cd = new CrudDAO();
		 
		//String qryPart1 = cd.createRetrieveQueryPart1(metadata,scrname,panelName);
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
		System.out.println("strWhereQuery="+strWhereQuery+"table name:"+tableName);
		
		if(tableName!= null && tableName.length() >0 && strWhereQuery!=null && strWhereQuery.length()>0)
		{
				delQuery ="DELETE  " + " FROM "+tableName+splWhereClause+strWhereQuery; 
		}
		else {
			log("Incomplete query was:"+"DELETE " + " FROM "+tableName+splWhereClause+strWhereQuery);
		}
		return delQuery;
	}
}
