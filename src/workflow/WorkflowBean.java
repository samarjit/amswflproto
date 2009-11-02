package workflow;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.opensymphony.workflow.InvalidActionException;
import com.opensymphony.workflow.InvalidEntryStateException;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.config.DefaultConfiguration;
import com.opensymphony.workflow.loader.StepDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.Step;

import dao.WorkflowDAO;



public class  WorkflowBean
{
	private void log(String s){
		System.out.println(s);
	}
	  Workflow workflow =  null;
	long workflowId = 0;
	public void printState(){
		WorkflowDescriptor wd =
			workflow.getWorkflowDescriptor(workflow.getWorkflowName(workflowId));
		
		List steps = workflow.getCurrentSteps(workflowId);
		System.out.println("Expected number of current steps: " + steps.size());
		System.out.print("current step   ");
		for (Iterator iterator = steps.iterator(); iterator.hasNext();)
		{
		Step step = (Step) iterator.next();
		StepDescriptor sd = wd.getStep(step.getStepId());
		System.out.print("#"  + sd.getId() +" step Name:"+ sd.getName()+" ");
		}
		int[] availableActions = workflow.getAvailableActions(workflowId,null);
		//verify we only have one available action
		
			for (int i = 0; i < availableActions.length; i++)
			{
			int name = wd.getAction(availableActions[i]).getId();
			System.out.print("  Action:"+name);
			}
			System.out.println();
		
	}
	
	public String getNewApplicationId(){
		WorkflowDAO wflDAO = new WorkflowDAO();
		return wflDAO.getNewAppId();
		
	}
	
	public String getSuitableWorkflowName(String activity){
		WorkflowDAO wflDAO = new WorkflowDAO();
		return wflDAO.getSuitableWorkflowName(activity);
	}
	/**
	 * @param wlfSessionNameAppId is appId
	 * @param wflName newwfl based on which workflow to choose
	 * @param propSet parameter to workflow
	 * @return workflow id
	 */
	public long workflowInit(String wlfSessionNameAppId, String wflName,HashMap propSet){
		Workflow wf = new BasicWorkflow(wlfSessionNameAppId);
		long id = 0;
		try {
			
			 id = wf.initialize(wflName,0, null);
		} catch (InvalidActionException e) {
			e.printStackTrace();
		} catch (InvalidRoleException e) {
			e.printStackTrace();
		} catch (InvalidInputException e) {
			e.printStackTrace();
		} catch (InvalidEntryStateException e) {
			e.printStackTrace();
		} catch (WorkflowException e) {
			e.printStackTrace();
		}
		return id;
	}
	public static void main(String[] args) throws InvalidInputException, WorkflowException 
	{
		new WorkflowBean().execueWorkflow();
/*HashMap inputs = new HashMap();
inputs.put("docTitle", "title");
long id = Long.parseLong("1");
wf.doAction(id, 1, inputs);*/
		System.out.println("Hello World!");
	}
	private void execueWorkflow() throws InvalidInputException, WorkflowException {
		 workflow = new BasicWorkflow("testuser");
			DefaultConfiguration config = new DefaultConfiguration();
			workflow.setConfiguration(config);
				//////////
			HashMap inputs = new HashMap();
			inputs.put("docTitle", "some_title");
		 
			//////////// 
			workflowId = workflow.initialize("newwfl", 0, null);
			printState();
			
			workflow.doAction(workflowId, 6, null);
			printState();  
			
		
			workflow.doAction(workflowId,35,null );
			printState();

			workflow.doAction(workflowId, 63, null);
			printState();	
			 
			
				
			workflow.doAction(workflowId, 59, null);
			
			printState(); 
				
			workflow.doAction(workflowId, 63, null);
			printState();	 
		
	}

	public void createApplicationWfl(String userid, long id, String appid,String status) {
		WorkflowDAO wflDAO = new WorkflowDAO();
		wflDAO.createApplication( userid,  id, appid, status);
		
	}

	public String getScreenId(String activityname) {
		WorkflowDAO wflDAO = new WorkflowDAO();
		return wflDAO.getScreenId( activityname);
	}
}