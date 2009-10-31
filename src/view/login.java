package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Iterator;
import dao.WorkflowDAO;
import dto.UserDTO;

public class login extends ActionSupport implements ServletRequestAware{
	private void log(String s){
		System.out.println(s);
	}
	
	
	private HttpServletRequest request;
	private String username;
	private String userid;
	 
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String htmlStr;
	
	public String getHtmlStr() {
		return htmlStr;
	}

	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}

	public String execute(){
		UserDTO usr = new UserDTO();
		ArrayList<String> arwflId= null;
		HttpSession session = request.getSession(true);
		
		if( request.getParameter("username") == null ){
			 addActionError("Enter username!");
			return ERROR;
		}else{
			usr.setUserid(username);
			session.setAttribute("userSessionData", usr);
			
			WorkflowDAO wflDAO = new WorkflowDAO();
			HashMap<String, ArrayList<String>> hmwflsess = wflDAO.getAvailableAction(usr.getUserid(),null);
			
			Iterator<String> itr = hmwflsess.keySet().iterator();
			try {
				while(itr.hasNext()){
					String strwflsess = (String) itr.next(); //wfl session 
					htmlStr+="<br>"+strwflsess;
					arwflId = hmwflsess.get(strwflsess);
					Workflow wf = new BasicWorkflow(strwflsess);
					Iterator wflIdItr = arwflId.iterator();
					while(wflIdItr.hasNext()){
						String wflId = (String)wflIdItr.next(); //wfl id
						htmlStr+="|"+wflId+"|";
						if(wflId != null){
							long id = Long.parseLong(wflId);
						    int[] actions = wf.getAvailableActions(id, null); //actions
						    for(int s:actions){
						    	htmlStr +=  String.valueOf(s)+",";
						    }
						}
						
					}
				}
			} catch (Exception e) {
				log("login:"+e.getMessage());
			}
			System.out.println(htmlStr);
		}
		return SUCCESS;
	}

	 
	@Override
	public void setServletRequest(HttpServletRequest req) {
		request = req;
		
	}

}
