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
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import dbconn.DBConnector;

public class RetreiveDetails extends ActionSupport implements ServletRequestAware{

	private InputStream inputStream;
	private String panelFields1WhereClause;
	private HttpServletRequest servletRequest;
	
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public HttpServletRequest getServletRequest() {
		return servletRequest;
	}
 

	public String execute() throws Exception {
    	
    	HashMap metadata = new HashMap();
    	RetreiveData retrive = new RetreiveData();
    	
    	if(servletRequest!=null)
    	System.out.println("query string RD : " + servletRequest.getQueryString());
    	
    	HttpServletRequest request1 =  ServletActionContext.getRequest();
    	System.out.println(request1.getQueryString());
    	
    	
    	//panelFields1WhereClause = request1.getParameter("panelFields1WhereClause");
    	panelFields1WhereClause = request1.getParameter("amp;panelFields1WhereClause");
    	
    	System.out.println(panelFields1WhereClause);
    	String resultHtml = "No Data found";
    	if(panelFields1WhereClause != null || (!"".equals(panelFields1WhereClause)))
    		resultHtml  = retrive.doRetrieveData(panelFields1WhereClause);
    	    	
        System.out.println(panelFields1WhereClause);
       // String resXML  = getResultXML (qry,metadata); 
        inputStream = new StringBufferInputStream(resultHtml);
    	//inputStream = new StringBufferInputStream("in view details");
        System.out.println("in view details");
        
        //clearing the where clause after use
        return SUCCESS;
    }
	
    public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		servletRequest = request;
		
	}



}
