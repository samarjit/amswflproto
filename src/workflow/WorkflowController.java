package workflow;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.workflow.InvalidActionException;
import com.opensymphony.workflow.InvalidEntryStateException;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.WorkflowDescriptor;

/**
 * Servlet implementation class WorkflowController.
 * @Author: Samarjit
 * @version: 1.0
 */
public class WorkflowController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkflowController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("create")!=null){
			HttpSession session = request.getSession(true);
			//if( session.getAttribute("workflowSessName") == null || "".equals(session.getAttribute("workflowSessName")))
				if(request.getParameter("workflowSessName") != null && !"".equals(request.getParameter("workflowSessName")))
				{
					session.setAttribute("workflowSessName",request.getParameter("workflowSessName"));
				}
			    Workflow wf = new BasicWorkflow((String) session.getAttribute("workflowSessName"));
			    try {
					long id = wf.initialize("newwfl",0, null);
					System.out.println("Workflow Initialized:"+ Long.toString(id));
					session.setAttribute("wflId", id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				session.setAttribute("username", request.getParameter("workflowSessName"));
		}else if(request.getParameter("action") != null){
			HttpSession session = request.getSession(false);
			String wflSession = "";
			//if((String) session.getAttribute("workflowSessName") != null){wflSession = (String) session.getAttribute("workflowSessName");
			// System.out.print("Got form Session "+wflSession+"!");
			// 			 }
			 if(request.getParameter("workflowSessName") != null) wflSession = request.getParameter("workflowSessName") ;
			    Workflow wf = new BasicWorkflow(wflSession);
			    //id is wflId
			    long id = 0;
			    if(request.getParameter("id") != null) 
			    	id = Long.parseLong(request.getParameter("id"));
			    else if((String) session.getAttribute("wflId") != null) 
			        id = Long.parseLong((String) session.getAttribute("wflId"));

			    String doString = request.getParameter("do");
			    if (doString != null && !doString.equals("")) {
			        int action = Integer.parseInt(doString);
			        try {
						wf.doAction(id, action, Collections.EMPTY_MAP);
					} catch (InvalidInputException e) {
						e.printStackTrace();
					} catch (WorkflowException e) {
						e.printStackTrace();
					}
			    }
			    System.out.print("WflSession:"+wflSession+"  wflId:"+Long.toString(id)+ " do:"+doString+"  ");
			    HashMap hmActions= new HashMap();
			    int[] actions = wf.getAvailableActions(id, null);
			    WorkflowDescriptor wd =  wf.getWorkflowDescriptor(wf.getWorkflowName(id));
			    for (int i = 0; i < actions.length; i++) {
			        String name = wd.getAction(actions[i]).getName();
			        System.out.print(actions[i]+" "+name +" \n");
			        hmActions.put(actions[i], name);
			        //<a href="wflview.jsp?id=<%=id%>&do=<%= actions[i] %>"><%= name %></a>
			    }
			    
			    session.setAttribute("wflId",id);
			    session.setAttribute("workflowSessName",wflSession);
			    request.setAttribute("hmActions", hmActions);
		}
		String url =response.encodeURL("/wflsubmit.jsp"); 
		getServletConfig().getServletContext().getRequestDispatcher(url).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
