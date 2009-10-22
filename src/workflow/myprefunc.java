package workflow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;

public class myprefunc implements FunctionProvider{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(Map arg0, Map arg1, PropertySet arg2)
			throws WorkflowException {
	Iterator it =	arg0.keySet().iterator();
	for(;it.hasNext();){
		String key = (String)it.next();
		System.out.println("arg0:key="+key+" value=" + arg0.get(key));
	}
	 it =	arg1.keySet().iterator();
	for(;it.hasNext();){
		String key = (String)it.next();
		System.out.println("arg1:key="+key+" value=" + arg1.get(key));
	}
	System.out.println("from my function:"+arg2.getString("memory"));
	 it =	arg2.getKeys().iterator();
	for(;it.hasNext();){
		String key = (String)it.next();
		System.out.println("arg2:key="+key+" value=" + arg2.getString(key));
	}
		System.out.println(" ******** printing from my function");
		
	}

}
