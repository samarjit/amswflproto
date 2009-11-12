package workflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String pageaction = request.getParameter("currentPageName");
		String flowName = request.getParameter("flowname");
		String businessLogic = scrfl.getBusinessLogic(flowName,pageaction);
		Class aclass = null;
		String url = "";
		try {
			if (businessLogic != null && !"".equals(businessLogic)) {
				aclass = Class.forName(businessLogic);
				BaseBL basebl = (BaseBL) aclass.newInstance();
				HashMap<String, String> buslogHm = new HashMap<String, String>();
				HashMap<String, String> retBLhm = null;
				retBLhm = basebl.processRequest(buslogHm);
			}
		ArrayList<String> nextaction = scrfl.getNextActions("loginflow", pageaction);
		//Currently no decision making is supported
		url = nextaction.get(0);
		//nextaction
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
