package search;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.CachedRowSet;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import dao.CrudDAO;
import dbconn.DBConnector;

/**
 * RELATEDPANEL may be put in screen_panel
 * @author Add
 *
 */
public class SearchListAC extends ActionSupport {

	private void debug(int priority, String s){
		if(priority > 0){
			System.out.println("SearchListAC:"+s);
		}
	}
	private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }
    /**
     * Related Panel concept is used because some fields in main details table say reqdate can be actually 
     * two dates from and todate in search panel. 
     * @param metadata
     * @return
     */
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
        
       debug(1,map.toString());
        while(itr.hasNext()){
        	String fname = (String) itr.next();
        	 String val = request.getParameter(fname);
        	String SQL = 
            	"select dbcol,datatype from panel_fields where  scr_name='"+scrname+"' and " +
            			"panel_name='"+panelName+"' " +
            			"and fname='"+fname+"'";
        	debug(1,SQL);
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
        
        /////////////////////relatedpanel may be put in screen_panel ///////////////
        String SQL = 
        	"select relatedpanel from screen  where   scr_name='"+scrname+"'";
        debug(1,SQL);
        String relatedPanel = "";
        try {
			CachedRowSet crs = db.executeQuery(SQL);
			if(crs.next()){
				relatedPanel = crs.getString("relatedpanel");
				
			}
			crs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String SQL1 = 
			"select TABLE_NAME,splwhereclause from screen_panel where   scr_name='"+scrname+"' and panel_name='"+relatedPanel+"'";
		 String tableName = "";
        debug(1,SQL1);
		 try {
			CachedRowSet crs = db.executeQuery(SQL1);
			while(crs.next()){
				tableName = crs.getString("TABLE_NAME");
				splWhereClause = crs.getString("splwhereclause");
			}
			crs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String colquery ="";
		String SQL2 = 
			"select lblname,fname,idname,dbcol,datatype,classname,prkey,strquery from panel_fields where  scr_name='"+scrname+"' and panel_name='"+relatedPanel+"' order by ORDERNO";
		 debug(1,SQL2); 
		 
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
				colquery = crs.getString("strquery");
				
				metadata.put(crs.getString("idname"), ls) ;
				if(colquery !=null && colquery.length() > 1){
					searchQuery +="("+colquery+") "+crs.getString("fname")+",";
				}else{
				    searchQuery +=crs.getString("dbcol")+" "+crs.getString("idname")+",";
				}
				colquery = "";
			}
			if(searchQuery.length() > 0){
				searchQuery = searchQuery.substring(0, searchQuery.length()-1);
			}
			crs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		searchQuery = "SELECT "+searchQuery + " FROM "+tableName+ searchQueryWhere ;
		
		CrudDAO cd = new CrudDAO();
		String predefQuery = "";
		
		/*
		 * predefquery will contain only the query upto TABLE(+)
		 *   + searchQueryWhere + splWhereClause
		 *   searchQueryWhere already took care of joiner
		 */
		predefQuery = cd.findPreDefQuery(scrname, relatedPanel);
		if(predefQuery!=null && predefQuery.length() > 0 ){
			searchQuery =predefQuery+" "+searchQueryWhere;
			debug(1,"searching ffrom predef query");
		}
		
		
		if(null != splWhereClause && !"".equalsIgnoreCase(splWhereClause)){
			searchQuery+= joiner + splWhereClause;
		}
		
		
		debug(1,searchQuery);
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
						debug(0,crs.getString(fname));
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
				if(html== null || "".equals(html))
				{
					html="No data found";
				}
				html = "<table border=1>"+tableHeader+html+"</table>";
			crs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    	
    	
    	return html;
    }
    public String execute() throws Exception {
    	HashMap metadata = new LinkedHashMap();
    	//inputStream = new StringBufferInputStream("Hello World! This is a text string response from a Struts 2 Action.");
        String qry  = createSearchQuery(metadata);
        //debug(qry);
        String resXML  = getResultXML (qry,metadata); 
        inputStream = new StringBufferInputStream(resXML);
        
        return SUCCESS;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
