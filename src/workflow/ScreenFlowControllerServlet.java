package workflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.UserDTO;

import businesslogic.BaseBL;

/**
 * Servlet implementation class ScreenFlowControllerServlet
 */
public class ScreenFlowControllerServlet extends HttpServlet {
	private void debug(String s){
		System.out.println(s);
	}
	private static final long serialVersionUID = 1L;
    private ScreenFlow scrfl=null;   
    
    public ScreenFlowControllerServlet() {
        super();
        scrfl = new ScreenFlow();
    }

	//currentPageName
    //flowname
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageaction = request.getParameter("currentaction");
		String flowName = request.getParameter("screenflowname");
		String businessLogic = scrfl.getBusinessLogic(flowName,pageaction);
		Class aclass = null;
		String url = "";
		try {
			UserDTO usr = new UserDTO();
			HttpSession session = request.getSession(true);
			if(session.getAttribute("userSessionData") ==null){
				if( request.getParameter("username") == null ){
					  
					 System.out.println("Error loggin in");
				}else{
					usr.setUserid(request.getParameter("username"));
					session.setAttribute("userSessionData", usr);
				}
			}
			if (businessLogic != null && !"".equals(businessLogic)) {
				aclass = Class.forName(businessLogic);
				BaseBL basebl = (BaseBL) aclass.newInstance();
				Map  buslogHm = new HashMap ();
				 
				Map map = request.getParameterMap();
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
				Entry n = (Entry)iter.next();
				String key = n.getKey().toString();
				String values[] = (String[]) n.getValue();
				buslogHm.put(key,values);
				} 
				HashMap  retBLhm = null;
				retBLhm = basebl.processRequest(buslogHm);
			}
		ArrayList<String> nextaction = scrfl.getNextActions("loginflow", pageaction);
		ScrFlowNode scrflow = scrfl.populateScrFlowNode(flowName, pageaction);
		
		//Currently no decision making is supported
		url = scrflow.getDescription();
		 
		} catch (ClassNotFoundException e) {
			debug(this.getServletName()+" "+e.toString());
			e.printStackTrace();
		} catch (InstantiationException e) {
			debug(this.getServletName()+" "+e.toString());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			debug(this.getServletName()+" "+e.toString());
			e.printStackTrace();
		}
		url = request.getContextPath() + "/"+ url;
		response.sendRedirect(url) ;
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
