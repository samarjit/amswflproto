package search;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URLDecoder;
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

public class RetreiveDetailsAC extends ActionSupport implements ServletRequestAware{

	private InputStream inputStream;
	private String whereClause;
	private HttpServletRequest servletRequest;
	
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public HttpServletRequest getServletRequest() {
		return servletRequest;
	}
 
	private void debug( int priority,String s){
		if(priority > 0)
		System.out.println("RetreiveDetailsAC:"+s);
	}
	 
	
	public String execute() throws Exception {
    	
    	HashMap metadata = new HashMap();
    	RetreiveData retrive = new RetreiveData();
    	
    	if(servletRequest!=null)
    	debug(0,"query string RD : " + servletRequest.getQueryString());
    	
    	HttpServletRequest request1 =  ServletActionContext.getRequest();
    	debug(0,request1.getQueryString());
    	
    	
    	//panelFields1WhereClause = request1.getParameter("panelFields1WhereClause");
    	URLDecoder decoder =  new URLDecoder();
    	whereClause = decoder.decode(request1.getParameter("whereClause"));
    	
    	debug(0,whereClause);
    	String resultHtml = "No Data found";
    	if(whereClause != null || (!"".equals(whereClause)))
    		resultHtml  = retrive.doRetrieveData(whereClause);
    	   
       // String resXML  = getResultXML (qry,metadata); 
        inputStream = new StringBufferInputStream(resultHtml);
    	//inputStream = new StringBufferInputStream("in view details");
        debug(0,"in view details");
        
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
