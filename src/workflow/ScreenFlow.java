package workflow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import businesslogic.BaseBL;

public class ScreenFlow {
	boolean initialized = false;
	public ScreenFlow() {
		super();
		if(!initialized)init();
	}
	private static HashMap<String,String> workflowlocationcache = null; 
	
	private Document parserXML(String file) throws SAXException, IOException, ParserConfigurationException
	{
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
	}
 
private void init(){
	if(initialized)return;
	initialized = true;
	URL  st =  ScreenFlow.class.getResource("/scrflowxml/screenflowfactory.xml");
	Document doc = null;
	workflowlocationcache = new HashMap<String, String>();
	try {
		doc = parserXML(st.getFile());
		
	 	NodeList nl = doc.getElementsByTagName("screenflow");
          for(int i = 0 ;i<nl.getLength();i++){
        	  Node node = nl.item(i);
        	  if(node.getNodeType() == Document.ELEMENT_NODE){
        		   Element elm = (Element) node;
        		   String key = elm.getAttribute("key");
        		   String val = elm.getAttribute("value");
        		   workflowlocationcache.put(key	, val);
        	  }
          }
  
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
	}
}

public ArrayList<String> getNextActions(String scrFlowName, String currentAction){
	ArrayList<String> retar = new ArrayList<String>();
	String workflowFile = 	ScreenFlow.workflowlocationcache.get(scrFlowName);
	String transitTo ="";
	try {
		URL url = ScreenFlow.class.getResource(workflowFile);
		if(url == null) throw new IOException("File not found");
		Document doc = parserXML(url.getFile());
		NodeList nl = doc.getElementsByTagName("state");
			if(currentAction==null || "".equals(currentAction)){
				NodeList nlstart = doc.getElementsByTagName("start-state");
				Element firstNode = (Element)nlstart.item(0);
				retar.add(firstNode.getAttribute("name"));
				/* NodeList descNodes = firstNode.getChildNodes();
				  for(int i=0;i<descNodes.getLength();i++){
					  if(descNodes.item(i).getNodeType() == Document.ELEMENT_NODE){
						  Element desElm = (Element) descNodes.item(i);
						  if(desElm.getNodeName().equals("description")){
							  if(desElm.getTextContent() != null  &&   desElm.getTextContent().length() > 1){
								  System.out.println(desElm.getTextContent().trim());
							  retar.add (desElm.getTextContent().trim());
							  }
							  
						  }
					  }
				  }*/
			}else{ //if already start state
				NodeList nlstart = doc.getElementsByTagName("start-state");
				Element firstNode = (Element)nlstart.item(0);
				Element currentElement = null;
				boolean found = false; 
				 if(currentAction.equals(firstNode.getAttribute("name"))){
					 currentElement = firstNode;
					 found=true;
				 }
				 
				 
				
				XPath xpath = XPathFactory.newInstance().newXPath();
				if( !found ){ 
					NodeList nlstate = (NodeList) xpath.evaluate("process-definition/state", doc, XPathConstants.NODESET);
					//parsing all the states amd match names with  currentAction
					for(int i =0 ;i<nlstate.getLength();i++){
						Node node =  nlstate.item(i);
						if(node.getNodeType() == Document.ELEMENT_NODE){
							Element elm = (Element) node;
							if(currentAction.equals(elm.getAttribute("name"))){
								currentElement = elm;
								 found=true; break;
							}
						}
					}
				}
				
				if( !found ){ 
					NodeList nlstate = (NodeList) xpath.evaluate("process-definition/task-node", doc, XPathConstants.NODESET);
					for(int i =0 ;i<nlstate.getLength();i++){
						Node node =  nlstate.item(i);
						if(node.getNodeType() == Document.ELEMENT_NODE){
							Element elm = (Element) node;
							if(currentAction.equals(elm.getAttribute("name"))){
								currentElement = elm;
								 found=true; break;
							}
						}
					}
				}
				
				if( !found ){ 
					NodeList nlstate = (NodeList) xpath.evaluate("process-definition/fork", doc, XPathConstants.NODESET);
					for(int i =0 ;i<nlstate.getLength();i++){
						Node node =  nlstate.item(i);
						if(node.getNodeType() == Document.ELEMENT_NODE){
							Element elm = (Element) node;
							if(currentAction.equals(elm.getAttribute("name"))){
								currentElement = elm;
								 found=true; break;
							}
						}
					}
				}
				
				//decision is very important will work on it later to implement handlers
				if( !found ){ 
					NodeList nlstate = (NodeList) xpath.evaluate("process-definition/decision", doc, XPathConstants.NODESET);
					for(int i =0 ;i<nlstate.getLength();i++){
						Node node =  nlstate.item(i);
						if(node.getNodeType() == Document.ELEMENT_NODE){
							Element elm = (Element) node;
							if(currentAction.equals(elm.getAttribute("name"))){
								currentElement = elm;
								 found=true; break;
							}
						}
					}
				}
				
				 if( !found ){
					 NodeList nlstate = doc.getElementsByTagName("node");
					 for(int i =0 ;i<nlstate.getLength();i++){
							Node node =  nlstate.item(i);
							if(node.getNodeType() == Document.ELEMENT_NODE){
								Element elm = (Element) node;
								if(currentAction.equals(elm.getAttribute("name"))){
									currentElement = elm;
									 found=true; break;
								}
							}
						}
					 
				 }
				 
				 if( !found ){ 
						NodeList nlstate = (NodeList) xpath.evaluate("process-definition/join", doc, XPathConstants.NODESET);
						for(int i =0 ;i<nlstate.getLength();i++){
							Node node =  nlstate.item(i);
							if(node.getNodeType() == Document.ELEMENT_NODE){
								Element elm = (Element) node;
								if(currentAction.equals(elm.getAttribute("name"))){
									currentElement = elm;
									 found=true; break;
								}
							}
						}
					}
				 
				 if( !found ){
					 retar.add("Element Not Found");
					 throw new Exception("element not found");
				 }
				  NodeList descNodes = currentElement.getChildNodes();
				  for(int i=0;i<descNodes.getLength();i++){
					  if(descNodes.item(i).getNodeType() == Document.ELEMENT_NODE){
						  Element desElm = (Element) descNodes.item(i);
						  if(desElm.getNodeName().equals("transition")){
							  if(desElm.getAttribute("to") != null  &&   desElm.getAttribute("to").length() > 1){
								  transitTo = desElm.getAttribute("to");
								  retar.add (transitTo.trim());
								  
							  }
							  
						  }
					  }
				  }
				      
			}
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
	} catch (XPathExpressionException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return retar;
}


public String getActionScreenName(String scrFlowName,String currentAction){
	String workflowFile = 	ScreenFlow.workflowlocationcache.get(scrFlowName);
	String transitTo ="";
	String screenName = "";
	boolean  found = false;
	try {
		URL url = ScreenFlow.class.getResource(workflowFile);
		if(url == null) throw new IOException("File not found");
		Document doc = parserXML(url.getFile());
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodelist = (NodeList) xpath.evaluate("/process-definition/start-state[@name=\""+currentAction+"\"]/description", doc, XPathConstants.NODESET);
		
		if(nodelist == null || nodelist.getLength() <1 ){
			nodelist = (NodeList) xpath.evaluate("/process-definition/state[@name=\""+currentAction+"\"]/description", doc, XPathConstants.NODESET);
		}
		if(nodelist == null || nodelist.getLength() ==0){
			nodelist = (NodeList) xpath.evaluate("/process-definition/task-node[@name=\""+currentAction+"\"]/description", doc, XPathConstants.NODESET);
		} 
		if(nodelist == null || nodelist.getLength() <1){
			nodelist = (NodeList) xpath.evaluate("/process-definition/fork[@name=\""+currentAction+"\"]/description", doc, XPathConstants.NODESET);
		} 
		if(nodelist == null || nodelist.getLength() <1){
			nodelist = (NodeList) xpath.evaluate("/process-definition/decision[@name=\""+currentAction+"\"]/description", doc, XPathConstants.NODESET);
		} 
		if(nodelist == null || nodelist.getLength() <1){
			nodelist = (NodeList) xpath.evaluate("/process-definition/join[@name=\""+currentAction+"\"]/description", doc, XPathConstants.NODESET);
		}
		
		if(nodelist != null ){
			found = true;
			
			Node descnode = nodelist.item(0);
			if(descnode != null){
				screenName = descnode.getTextContent();
				screenName = screenName.trim();
			}else{
				screenName = "no screen mapped";	
			}
		}
		
	}catch(IOException e){
		e.printStackTrace();
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
	} catch (XPathExpressionException e) {
		e.printStackTrace();
	}
	
	return screenName;
}
	

	public String getBusinessLogic(String flowName, String currentAction) {
		String workflowFile = 	ScreenFlow.workflowlocationcache.get(flowName);
		String transitTo ="";
		String businessLogic = "";
		boolean  found = false;
		try {
			URL url = ScreenFlow.class.getResource(workflowFile);
			if(url == null) throw new IOException("File not found");
			Document doc = parserXML(url.getFile());
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodelist = (NodeList) xpath.evaluate("/process-definition/start-state[@name=\""+currentAction+"\"]/event/action", doc, XPathConstants.NODESET);
			if(nodelist == null || nodelist.getLength() <1){
				nodelist = (NodeList) xpath.evaluate("/process-definition/task-node[@name=\""+currentAction+"\"]/event/action", doc, XPathConstants.NODESET);
			}
			if(nodelist == null || nodelist.getLength() <1){
				nodelist = (NodeList) xpath.evaluate("/process-definition/fork[@name=\""+currentAction+"\"]/event/action", doc, XPathConstants.NODESET);
			}
			if(nodelist == null || nodelist.getLength() <1){
				nodelist = (NodeList) xpath.evaluate("/process-definition/decision[@name=\""+currentAction+"\"]/event/action", doc, XPathConstants.NODESET);
			}
			if(nodelist == null || nodelist.getLength() <1){
				nodelist = (NodeList) xpath.evaluate("/process-definition/join[@name=\""+currentAction+"\"]/event/action", doc, XPathConstants.NODESET);
			}

			if(nodelist != null ){
				found = true;
				Element descnode = (Element) nodelist.item(0);
				if(descnode != null){
					businessLogic =  descnode.getAttribute("class");
					businessLogic = businessLogic.trim();
				}else{
					businessLogic = "";	
				}
			}
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return businessLogic;
	}
	
	/**
	 * @param args
	 * @throws ClassNotFoundException just before class loading
	 * @throws IllegalAccessException  while class loading
	 * @throws InstantiationException while class was instanciation
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ScreenFlow scf = new ScreenFlow();
		scf.init();
		System.out.println("NextAction:"+scf.getNextActions("loginflow", "join1"));
		System.out.println("Current Screen Name:"+scf.getActionScreenName("loginflow", "join1"));
		System.out.println("BusinessLogic:"+scf.getBusinessLogic("loginflow", "start"));
		String className = scf.getBusinessLogic("loginflow", "start");
		Class aclass = Class.forName(className);
		BaseBL basebl = (BaseBL) aclass.newInstance();
		basebl.processRequest(null);
		System.out.println();
	}

}
