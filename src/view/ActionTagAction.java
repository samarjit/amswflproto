package view;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ActionTagAction extends ActionSupport {

	 public String execute() throws Exception {
	     return "done";
	 }

	 public String doDefault() throws Exception {
	     ServletActionContext.getRequest().setAttribute("stringByAction", "This is a String put in by the action's doDefault()");
	     return "done";
	 }
	}