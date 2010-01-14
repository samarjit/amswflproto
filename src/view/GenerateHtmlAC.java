package view;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class GenerateHtmlAC extends ActionSupport{
public String user;
public String password;
public String dataPanel;
private String buttonPanel;
private String leftPanel;
private String bottomPanel;
private String topPanel;
private HashMap extraFields;
private String screenName;
private String errorMessage; 
private String cssname;
private String jsname;
private String panelFieldsWhereClause;

private void debug( int priority,String s){
//	if(priority > 0)
//	System.out.println("GenerateHtml:"+s);
}
private void debug(String s){
//	System.out.println("GenerateHtml:"+s);
}

public String getPanelFieldsWhereClause() {
	return panelFieldsWhereClause;
}

public void setPanelFieldsWhereClause(String panelFieldsWhereClause) {
	this.panelFieldsWhereClause = panelFieldsWhereClause;
}

public String getCssname() {
	return cssname;
}

public void setCssname(String cssname) {
	this.cssname = cssname;
}

public String getJsname() {
	return jsname;
}

public void setJsname(String jsname) {
	this.jsname = jsname;
}

public String getErrorMessage() {
	return errorMessage;
}

public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}

public String getScreenName() {
	return screenName;
}

public void setScreenName(String screenName) {
	this.screenName = screenName;
}

public HashMap getExtraFields() {
	return extraFields;
}

public void setExtraFields(HashMap extraFields) {
	this.extraFields = extraFields;
}

public String getButtonPanel() {
	return buttonPanel;
}

public void setButtonPanel(String buttonPanel) {
	this.buttonPanel = buttonPanel;
}

public String getLeftPanel() {
	return leftPanel;
}

public void setLeftPanel(String leftPanel) {
	this.leftPanel = leftPanel;
}

public String getBottomPanel() {
	return bottomPanel;
} 

public void setBottomPanel(String bottomPanel) {
	this.bottomPanel = bottomPanel;
}

public String getTopPanel() {
	return topPanel;
}

public void setTopPanel(String topPanel) {
	this.topPanel = topPanel;
}

public String getDataPanel() {
	return dataPanel;
}

public void setDataPanel(String part1) {
	this.dataPanel = part1;
}

	public String getUser() {
	return user;
}

public void setUser(String user) {
	this.user = user;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

	public static void main(String[] args) {
		 Createhtml htmlc = new Createhtml();
		 List<String> lstPanels =  htmlc.getPanels("frmRequest");
		 List<String> arPanelData = new ArrayList<String>();
		 for(String panelName:lstPanels){
			 arPanelData.add(htmlc.makehtml("frmRequest",panelName));
		 }
		System.out.println("Extrs fields:"+arPanelData);

	}

	public String execute() {
		  
		 String templateName;
		 Createhtml htmlc = new Createhtml();
		 
		 setJsname(htmlc.getJsCsshtml(screenName));
		 //setDataPanel(htmlc.makehtml("panelFields"));
		 //setButtonPanel(htmlc.makehtml("buttonPanel"));
		 List<String> lstPanels =  htmlc.getPanels(screenName);
		 LinkedHashMap<String,String> arPanelData = new LinkedHashMap<String, String>();
		 for(String panelName:lstPanels){
			debug(0,"##"+panelName);
			 arPanelData.put(panelName,htmlc.makehtml(screenName,panelName));
		 } 
		 templateName = htmlc.getTemplateName(screenName);
		 setExtraFields(arPanelData);
		//  System.out.println("Extrs fields:"+getExtraFields());
		 // setLeftPanel(leftPanel);
		// setTopPanel(topPanel);
		// setBottomPanel(bottomPanel);
		 
		 
		if(templateName.equalsIgnoreCase("")){
			setErrorMessage("The template was not found for this page!");
			templateName = "failure"; //redirects to index.jsp
		}
		
//		try {
//		 	workflow.main(null);
//		} catch (InvalidInputException e) {
//			e.printStackTrace();
//		} catch (WorkflowException e) {
//			e.printStackTrace();
//		}
		
		return templateName;
	}

}
