package workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dto.ApplicationDTO;
import dto.UserDTO;

public class WorkflowAC extends ActionSupport implements SessionAware, RequestAware, ParameterAware{
	private void debug(int priority, String s){
		if(priority>-1){
			System.out.println("WorkflowAC:"+s);
		}
	}
private String forwardtourl;
private Map<String, Object> session;
private Map<String, Object> request;
private WorkflowBean wflBean;
private String redirecturl;
private String screenName;
private Map<String, String[]> parameter;

private String create;
private String activityname;

private String navigateto;

private String action;

private String wflid;
private String appid;
private String doString;


public String getCreate() {
	return create;
}

public void setCreate(String create) {
	this.create = create;
}

public String getActivityname() {
	return activityname;
}

public void setActivityname(String activityname) {
	this.activityname = activityname;
}

public String getScreenName() {
	return screenName;
}

public void setScreenName(String screenName) {
	this.screenName = screenName;
}

public String getRedirecturl() {
	return redirecturl;
}

public void setRedirecturl(String redirecturl) {
	this.redirecturl = redirecturl;
}

public WorkflowAC() {
	wflBean = new WorkflowBean();
}

public String getForwardtourl() {
	return forwardtourl;
}

public void setForwardtourl(String forwardtourl) {
	this.forwardtourl = forwardtourl;
}
/**
 * /workflow.action?activityname=CR&create=true
 * /workflow.action?action=true&doString="+actionid+"&wflid="+wflid+"&appid="+applicationid;
 */
public String execute1(){
	String returnStr=SUCCESS;
	UserDTO usrDTO = (UserDTO) session.get("userSessionData");	
	String url=""; 
	try {
		if (request.get("create") != null) {
			if (activityname != null && !"".equals(activityname)) {
				ApplicationDTO appdto = new ApplicationDTO();
				debug(0, "activityname:" + activityname);
				String appid = wflBean.getNewApplicationId();
				String WflName = wflBean.getSuitableWorkflowName(activityname);
				//wflid = wflBean.workflowInit(appid, WflName, null);
				ArrayList<String> hmActions = wflBean.getNextScrFlowActions(WflName, ""); //rest of the places wflid = WflName
				if ("".equals(url) && hmActions.size() > 0) {
					String actionname = (String) hmActions.get(0);
					url = wflBean.getScreenId(actionname);
//					appdto.setCurrentActionId(hmActions.get(actionname));//used by actionbutton 
//					appdto.setCurrentAction(actionname);
				}
				wflBean.createApplicationScrWfl(usrDTO.getUserid(),WflName, appid, "S", hmActions);//'S' for started
//				appdto.setWflactions(hmActions);
//				appdto.setWflid(wflid);
//				session.put("applicationDTO", appdto);
			}
		} else if (request.get("action") != null) {
			//uses appid, wflid, doString
//			ApplicationDTO appdto = (ApplicationDTO) session.get("applicationDTO");
//			if (appdto == null)
//				appdto = new ApplicationDTO();
//			if (appid != null && appid.length() > 0) {
//				appdto.setAppid(appid);
//			}
//			if (wflid != -1 && wflid > 0) {
//				appdto.setWflid(wflid);
//			}
//
//			String wflSession = appdto.getAppid();

			if (doString != null && !doString.equals("")) {
//				int actionid = Integer.parseInt(doString);
//				try {
//					wflBean.doAction(wflSession/*appid*/, wflid, actionid);
//
//				} catch (InvalidInputException e) {
//					e.printStackTrace();
//				} catch (WorkflowException e) {
//					e.printStackTrace();
//				}
				wflBean.changeStageApplicationScrWfl(usrDTO.getUserid(),wflid, appid, "C", doString);//'S' for started
			} else {
				debug(5, "WorkflowAC:doString is null");
			}
			
			debug(1, "AppId:" + appid+ "  screenflowid:" + wflid+ " doString:(expecting CreateRequest, RFQ etc..)" + doString + "  ");

			ArrayList<String> hmActions = wflBean.getNextScrFlowActions(wflid, doString);
			if ("".equals(url) && hmActions.size() > 0) {
				String actionname = (String) hmActions.get(0);
				url = wflBean.getScreenId(actionname);
//				appdto.setCurrentActionId(hmActions.get(actionname));//used by actionbutton 
//				appdto.setCurrentAction(actionname);
			}
			wflBean.updateApplicationScrWfl(usrDTO.getUserid(),wflid, appid, "S", hmActions);//'S' for started
//			appdto.setWflid(wflid);
//			appdto.setWflactions(hmActions);
//			session.put("applicationDTO", appdto);
		} else if (request.get("navigateto") != null) {
//			ApplicationDTO appdto = (ApplicationDTO) session.get("applicationDTO");
//			HashMap<String, Integer> hmactions = appdto.getWflactions();
			String pageName = navigateto;
			url = wflBean.getScreenId(pageName);
//			appdto.setCurrentAction(pageName);
//			appdto.setCurrentActionId(hmactions.get(pageName));
//			appdto.setWflid(wflid);
			
		}
	} catch (Exception e) {
		debug(5,"Some Error has occured:"+e.getMessage());
		e.printStackTrace();
		url="/pages/workflowerror.jsp";
	}
	redirecturl =  "/template1.action?screenName=frmRequestList";
	
	redirecturl =url;
	if("".equals(redirecturl))redirecturl ="/pages/workflowcompleted.jsp";
	returnStr = "flowcontroller";
	return returnStr;	
}



/**
 * /workflow.action?activityname=CR&create=true
 * /workflow.action?action=true&doString="+actionid+"&wflid="+wflid+"&appid="+applicationid;
 */
//Original using OSworkflow
	public String execute(){
		String returnStr=SUCCESS;
		UserDTO usrDTO = (UserDTO) session.get("userSessionData");	
		String url=""; 
		long worflowid = -1;
		if(wflid != null && !"".equals(wflid))
		worflowid = Long.parseLong(wflid);
		
		try {
			if (request.get("create") != null) {
				if (activityname != null && !"".equals(activityname)) {
					ApplicationDTO appdto = new ApplicationDTO();
					debug(0, "activityname:" + activityname);
					String appid = wflBean.getNewApplicationId();
					String WflName = wflBean.getSuitableWorkflowName(activityname);
					worflowid = wflBean.workflowInit(appid, WflName, null);
					HashMap<String, Integer> hmActions = wflBean.getAvailableActions(appid, worflowid);
					if ("".equals(url) && hmActions.size() > 0) {
						String actionname = (String) hmActions.keySet().toArray()[0];
						url = wflBean.getScreenId(actionname);
						appdto.setCurrentActionId(hmActions.get(actionname));//used by actionbutton 
						appdto.setCurrentAction(actionname);
					}
					wflBean.createApplicationWfl(usrDTO.getUserid(), worflowid,appid, "S", hmActions);//'S' for started
					appdto.setWflactions(hmActions);
					appdto.setWflid(worflowid);
					session.put("applicationDTO", appdto);
				}
			} else if (request.get("action") != null) {
				//uses appid, wflid, doString
				ApplicationDTO appdto = (ApplicationDTO) session.get("applicationDTO");
				if (appdto == null)
					appdto = new ApplicationDTO();
				if (appid != null && appid.length() > 0) {
					appdto.setAppid(appid);
				}
				if (worflowid != -1 && worflowid > 0) {
					appdto.setWflid(worflowid);
				}

				String wflSession = appdto.getAppid();

				if (doString != null && !doString.equals("")) {
					int actionid = Integer.parseInt(doString);
					try {
						wflBean.doAction(wflSession/*appid*/, worflowid, actionid);

					} catch (InvalidInputException e) {
						e.printStackTrace();
					} catch (WorkflowException e) {
						e.printStackTrace();
					}
					wflBean.changeStageApplicationWfl(usrDTO.getUserid(),worflowid, wflSession /*appid*/, "C", Integer.parseInt(doString));//'S' for started
				} else {
					debug(5, "WorkflowAC:doString is null");
				}
				
				debug(1, "WflSession:" + wflSession + "  wflId:" + worflowid+ " do:" + doString + "  ");

				HashMap<String, Integer> hmActions = wflBean.getAvailableActions(appid, worflowid);
				if ("".equals(url) && hmActions.size() > 0) {
					String actionname = (String) hmActions.keySet().toArray()[0];
					url = wflBean.getScreenId(actionname);
					appdto.setCurrentActionId(hmActions.get(actionname));//used by actionbutton 
					appdto.setCurrentAction(actionname);
				}
				wflBean.updateApplicationWfl(usrDTO.getUserid(), worflowid,wflSession/*appid*/, "S", hmActions);//'S' for started
				appdto.setWflid(worflowid);
				appdto.setWflactions(hmActions);
				session.put("applicationDTO", appdto);
			} else if (request.get("navigateto") != null) {
				ApplicationDTO appdto = (ApplicationDTO) session.get("applicationDTO");
				HashMap<String, Integer> hmactions = appdto.getWflactions();
				String pageName = navigateto;
				url = wflBean.getScreenId(pageName);
				appdto.setCurrentAction(pageName);
				appdto.setCurrentActionId(hmactions.get(pageName));
				appdto.setWflid(worflowid);
				
			}
		} catch (Exception e) {
			debug(5,"Some Error has occured:"+e.getMessage());
			e.printStackTrace();
		}
		redirecturl =  "/template1.action?screenName=frmRequestList";
		
		redirecturl =url;
		if("".equals(redirecturl))redirecturl ="/pages/workflowcompleted.jsp";
		returnStr = "flowcontroller";
		return returnStr;
	}

	public String getNavigateto() {
		return navigateto;
	}

	public void setNavigateto(String navigateto) {
		this.navigateto = navigateto;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getWflid() {
		return wflid;
	}

	public void setWflid(String wflid) {
		this.wflid = wflid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getDoString() {
		return doString;
	}

	public void setDoString(String doString) {
		this.doString = doString;
	}

	@Override
	public void setSession(Map<String, Object> sess) {
		session = sess;
	}

	@Override
	public void setRequest(Map<String, Object> req) {
		request = req;
	}

	@Override
	public void setParameters(Map<String, String[]> param) {
		parameter  = param;
		
	}
}
