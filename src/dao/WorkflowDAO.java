package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.basic.BasicWorkflow;

import dbconn.DBConnector;

public class WorkflowDAO {
	private void log(String s){
		System.out.println(s);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public HashMap<String, ArrayList<String>> getAvailableAction(String userid,String applicationId) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		CachedRowSet crs2 = null;
		String SQL = "SELECT  " +
		"U.APPID, U.USERID, U.STATUS  " +
		"FROM  USER_WFLID_APPID U where U.STATUS='S' and U.USERID='"+userid+"'";
		
		String wflid="";
		String appid="";
		String SQL2="";
		ArrayList<String> arwflid= null;
		HashMap<String, ArrayList<String>> wflsesshm = new HashMap<String, ArrayList<String>>();
		
		try{
		//	crs = db.executeQuery(SQL);
			
		//	while(crs.next()){
				// appid= crs.getString("APPID");
				  
				
				SQL2 = "SELECT  " +
				"U.WFLID, U.USERID, U.STATUS, U.APPID " +
				"FROM  USER_WFLID_APPID U where U.STATUS='S' and U.USERID='"+userid+"'";
				if(applicationId != null && !"".equals(applicationId)){
					SQL2+= " AND U.APPID='"+applicationId+"'";
				}
				crs2= db.executeQuery(SQL2);
				
				while(crs2.next()){
					arwflid = new ArrayList<String>();
					appid = crs2.getString("APPID");
					
					arwflid.add(crs2.getString("WFLID"));
					if(appid!=null && !"".equals(appid))
					wflsesshm.put(appid,arwflid);
				}
				
			//}
			
			
		}catch(SQLException e){
			log("Workflow DAO:"+e.getMessage()+":"+SQL+"\n"+SQL2);
		}finally{
			try {
					if(crs!=null){
						crs.close();
					}
					if(crs2!=null){
						crs2.close();
					}
			}catch (SQLException e) {
				log(e.getMessage());
			}
		}
		return wflsesshm;
	}
	public String getNewAppId() {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String SQL = "select seqappid.nextval appid from dual ";
		String appid=""; 
		try {
				crs = db.executeQuery(SQL);
				while(crs.next()){
					appid=  crs.getString("appid");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(crs!=null)crs.close();
			}catch(SQLException e){
				log(e.getMessage());
			}
		}
		return appid;
	}
	public String getSuitableWorkflowName(String activity) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String SQL = "SELECT W.WFLXML, W.WFLNAME, W.ACTIVITY FROM  WFLNAME_ACTIVITY_MAP W where W.ACTIVITY='"+activity+"'";
		String wflName=""; 
		try {
				crs = db.executeQuery(SQL);
				while(crs.next()){
					wflName=  crs.getString("WFLNAME");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(crs!=null)crs.close();
			}catch(SQLException e){
				log(e.getMessage());
			}
		}
		return wflName;
	}
	public void createApplication(String userid, long id,String appid,String status) {
		DBConnector db = new DBConnector();
		int i =0;
		 String SQL = "INSERT INTO  USER_WFLID_APPID (WFLID, USERID, STATUS,APPID)  " +
		"VALUES ('" +String.valueOf(id)+"'"+
		" ,'" +userid+"'"+
		" ,'" +status+"'"+
		" ,'" +appid+"'"+
		" ) ";
 
		try {
				i = db.executeUpdate(SQL);
				 
		} catch (SQLException e) {
			log(e.getMessage());
		}finally{
			 
		}
	}
	public String getScreenId(String activityname) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String SQL = "SELECT W.SCREENID FROM WFLACTION_SCREEN_MAP W WHERE W.WFLACTIVITY='"+activityname+"'";
		String screenName=""; 
		try {
				crs = db.executeQuery(SQL);
				while(crs.next()){
					screenName=  crs.getString("SCREENID");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(crs!=null)crs.close();
			}catch(SQLException e){
				log(e.getMessage());
			}
		}
		return screenName;
	}
}
