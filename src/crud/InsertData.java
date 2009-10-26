package crud;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import util.Utility;
import dao.CrudDAO;

public class InsertData {
	public void log(String s){
		System.out.println(s);
	}
	/**
	 * main function is used only for testing doDelete() is the method that does 
	 * really works
	 * @param args
	 */
	public static void main(String[] args) {
		InsertData dd = new InsertData ();
		dd.doInsert();

	}
	public void doInsert(){
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
			String sg = createInsertQuery(metadata, scrName,	panelName, hmWhere);
			log("Insert query:" + sg);
			
		}
	}
	
	public String createInsertQuery(HashMap metadata,String scrname,String panelName, HashMap<String,String> hmWherePanel) {
		String updateQuery = "";
		String joiner = " WHERE ";
		CrudDAO cd = new CrudDAO();
	 
		HashMap insertMap = Utility.extractKeyValPair("empid!0~#empname!sam samanta");
		HashMap qryPart1 = cd.createInsertQueryPart1(metadata,scrname,panelName,insertMap );
		String tableName =  cd.findTableByPanels(scrname,panelName);
		String splWhereClause = cd.findSplWhereClsOfPanels(scrname,panelName);
		  
		
		if(splWhereClause != null && ! "".equals(splWhereClause) ){
			splWhereClause += joiner + splWhereClause+" ";
			joiner =" AND ";
		}else{
			splWhereClause =" ";
		}
		//process where clause
		
		System.out.println("table name:"+tableName);
		
		if(tableName!= null && tableName.length() >0 && qryPart1 !=null && qryPart1.size() > 0  && qryPart1.get("valuestr") != null){
			updateQuery ="INSERT INTO "+tableName+"("+qryPart1.get("dbcolstr")+") VALUES ("+qryPart1.get("valuestr")+")"; 
		}else {
			log("Incomplete query was:"+"INSERT INTO "+tableName+"("+qryPart1.get("dbcolstr")+") VALUES ("+qryPart1.get("valuestr")+")");
		}
		return updateQuery;
	}
}
