package search;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.CachedRowSet;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import dbconn.DBConnector;

public class SearchList extends ActionSupport {

	private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }
    public String createSearchQuery(HashMap metadata){
    	String searchQueryWhere = "";
    	String splWhereClause = "";
    	String searchQuery = "";
    	String joiner = " WHERE ";
    	HttpServletRequest request =  ServletActionContext.getRequest();
        DBConnector db = new DBConnector();
    	String scrname = request.getParameter("screenName");
        String panelName = request.getParameter("panelName");
        
    	Map<String,String> map = new HashMap(request.getParameterMap());
        map.remove(scrname);
        map.remove(panelName);
        
        Iterator itr = map.keySet().iterator();
        
       //System.out.println(map);
        while(itr.hasNext()){
        	String fname = (String) itr.next();
        	 String val = request.getParameter(fname);
        	String SQL = 
            	"select dbcol,datatype from panel_fields where  scr_name='"+scrname+"' and " +
            			"panel_name='"+panelName+"' " +
            			"and fname='"+fname+"'";
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
	        		if(!"".equals(searchQueryWhere)){
	        			joiner = " AND ";
	        		}
	        		searchQueryWhere +=joiner+dbcol+" like '%"+val+"%'";
	        	}
	        	crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        
        /////////////////////
        String SQL = 
        	"select relatedpanel,splwhereclause from screen_panel where SORTORDER =1 and scr_name='"+scrname+"'";
        String relatedPanel = "";
        try {
			CachedRowSet crs = db.executeQuery(SQL);
			if(crs.next()){
				relatedPanel = crs.getString("relatedpanel");
				splWhereClause = crs.getString("splwhereclause");
			}
			crs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String SQL1 = 
			"select TABLE_NAME from screen_panel where   scr_name='"+scrname+"' and panel_name='"+relatedPanel+"'";
		 String tableName = "";
        try {
			CachedRowSet crs = db.executeQuery(SQL1);
			while(crs.next()){
				tableName = crs.getString("TABLE_NAME");
			}
			crs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String SQL2 = 
			"select lblname,fname,idname,dbcol,datatype,classname,prkey from panel_fields where  scr_name='"+scrname+"' and panel_name='"+relatedPanel+"'";
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
		
		searchQuery = "SELECT "+searchQuery + " FROM "+tableName+ searchQueryWhere ;
		
		if(null != splWhereClause && !"".equalsIgnoreCase(splWhereClause)){
			searchQuery+= joiner + splWhereClause;
		}
		
    	return searchQuery;
    }
    
    
    private String getResultXML(String query, HashMap metadata){
    	  String html = "";
    	  String tableHeader = "";
    	  String data = "";
        try {
			DBConnector db = new DBConnector();
			
			CachedRowSet crs = db.executeQuery(query);
			boolean firstItr=true;
				while(crs.next()){ 
					
					html += "\n<tr >";
					if(firstItr){
						tableHeader += "\n<tr >";
					}
					Iterator itr = metadata.keySet().iterator();
					 while (itr.hasNext()) {
						String fname = (String) itr.next();
						ListAttribute ls = (ListAttribute) metadata.get(fname);
						data  = crs.getString(fname);
						System.out.println(crs.getString(fname));
						if(firstItr){
							tableHeader += "<th><div id="+fname+" style='display:none'>"+ls+"</div>"+ls.getLblname()+"</th>";
							
						}
						 
						html += "<td id="+fname+"> "+data+"</td>";
							
					}if(firstItr){
					 tableHeader += "</tr>";
					 firstItr= false;
					}
					 html += "</tr>";
				
				}
				html = "<table border=1>"+tableHeader+html+"</table>";
			crs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    	
    	
    	return html;
    }
    public String execute() throws Exception {
    	HashMap metadata = new HashMap();
    	//inputStream = new StringBufferInputStream("Hello World! This is a text string response from a Struts 2 Action.");
        String qry  = createSearchQuery(metadata);
        //System.out.println(qry);
        String resXML  = getResultXML (qry,metadata); 
        inputStream = new StringBufferInputStream(resXML);
        
        return SUCCESS;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
