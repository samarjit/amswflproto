package workflow;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.config.DefaultConfiguration;
import com.opensymphony.workflow.loader.StepDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.Step;



public class  test
{
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
		System.out.print(""  + sd.getId() +" step Name:"+ sd.getName());
		}
		int[] availableActions = workflow.getAvailableActions(workflowId);
		//verify we only have one available action
		
			for (int i = 0; i < availableActions.length; i++)
			{
			int name = wd.getAction(availableActions[i]).getId();
			System.out.print("  Action:"+name);
			}
			System.out.println();
		
	}
	public static void main(String[] args) throws InvalidInputException, WorkflowException 
	{
		new test().execueWorkflow();
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
			workflowId = workflow.initialize("dm", 0, null);
			printState();
			
			workflow.doAction(workflowId, 5, null);
			printState();  
			
		
			workflow.doAction(workflowId, 7,inputs );
			printState();

			workflow.doAction(workflowId, 7, null);
			printState();	
			 
			
				
			workflow.doAction(workflowId, 12, null);
			
			printState(); 
				
			workflow.doAction(workflowId, 14, null);
			printState();	 
		
	}
}