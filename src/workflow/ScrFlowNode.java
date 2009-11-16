package workflow;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

public class ScrFlowNode implements Serializable{

private Node node;

private String eventexpression;
public String getEventexpression() {
	return eventexpression;
}
public void setEventexpression(String eventexpression) {
	this.eventexpression = eventexpression;
}

private String eventactions;
private String description;
private ArrayList transitionto;
private String decisionHandler;
private String decisionExpression;
public Node getNode() {
	return node;
}
public void setNode(Node node) {
	this.node = node;
}
public String getEventactions() {
	return eventactions;
}
public void setEventactions(String eventactions) {
	this.eventactions = eventactions;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public ArrayList getTransitionto() {
	return transitionto;
}
public void setTransitionto(ArrayList transitionto) {
	this.transitionto = transitionto;
}
public String getDecisionHandler() {
	return decisionHandler;
}
public void setDecisionHandler(String decisionHandler) {
	this.decisionHandler = decisionHandler;
}
public String getDecisionExpression() {
	return decisionExpression;
}
public void setDecisionExpression(String decisionExpression) {
	this.decisionExpression = decisionExpression;
}

public String toString(){
	return "Node:"+node+ ", "+"eventactions:"+eventactions
	+",eventexpression:"+eventexpression+", description:"+description+
	", transitionto:"+transitionto+", decisionHandler:"+decisionHandler+
	", decisionExpression:"+decisionExpression;
	
}
 
public String toXMLStr(){
	String xmlString = "";
	try {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(node );

		transformer.transform(source, result);

		xmlString = result.getWriter().toString();
		 
	} catch (TransformerException e) {
		e.printStackTrace();
	} 
	return xmlString;
}

}
