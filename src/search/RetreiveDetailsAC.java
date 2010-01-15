package search;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import businesslogic.BaseBL;

import com.opensymphony.xwork2.ActionSupport;

import dao.CrudDAO;

public class RetreiveDetailsAC extends ActionSupport implements ServletRequestAware{

	private InputStream inputStream;
	private String whereClause;
	private HttpServletRequest servletRequest;
	private String screenName = null;
	
    public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

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
    	
    	preRetreiveProcessBL(screenName);
    	String resultHtml = "No Data found";
    	if(whereClause != null || (!"".equals(whereClause)))
    		resultHtml  = retrive.doRetrieveData(screenName,whereClause);
    	 
    	postRetreiveProcessBL(screenName);
       // String resXML  = getResultXML (qry,metadata); 
        inputStream = new StringBufferInputStream(resultHtml);
    	//inputStream = new StringBufferInputStream("in view details");
        debug(1,"in view details");
        
        //clearing the where clause after use
        return SUCCESS;
    }
	
    public void preRetreiveProcessBL(String screenName) {
		Class aclass = null;
		CrudDAO cd = new CrudDAO();
		String businessLogic = cd.getBusinessLogicName(screenName);
		try {
			if (businessLogic != null && !"".equals(businessLogic)) {
				aclass = Class.forName(businessLogic);
				BaseBL basebl = (BaseBL) aclass.newInstance();
				Map buslogHm = new HashMap();

				Map map = servletRequest.getParameterMap();
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Entry n = (Entry) iter.next();
					String key = n.getKey().toString();
					String values[] = (String[]) n.getValue();
					buslogHm.put(key, values);
				}
				HashMap retBLhm = null;
				retBLhm = basebl.preRetreiveProcessBL(buslogHm);
			}
		} catch (Exception e) {
			debug(1,"Businesslogic not found");
			e.printStackTrace();
		}
		
	}



	public void postRetreiveProcessBL(String screenName) {
		Class aclass = null;
		CrudDAO cd = new CrudDAO();
		String businessLogic = cd.getBusinessLogicName(screenName);
		try {
			if (businessLogic != null && !"".equals(businessLogic)) {
				aclass = Class.forName(businessLogic);
				BaseBL basebl = (BaseBL) aclass.newInstance();
				Map buslogHm = new HashMap();

				Map map = servletRequest.getParameterMap();
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Entry n = (Entry) iter.next();
					String key = n.getKey().toString();
					String values[] = (String[]) n.getValue();
					buslogHm.put(key, values);
				}
				HashMap retBLhm = null;
				retBLhm = basebl.postRetreiveProcessBL(buslogHm);
			}
		} catch (Exception e) {
			debug(1,"Businesslogic not found");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		servletRequest = request;
		
	}



}
