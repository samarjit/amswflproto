package businesslogic;

import java.util.HashMap;
import java.util.Map;

public class RequestBL implements BaseBL{

	@Override
	public HashMap activitySubmit(Map hm) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void debug(int priotiry, String s){
		if(priotiry > 0)
			System.out.println("RequestBL:"+s);
	}
	
	@Override
	public HashMap postRetreiveProcessBL(Map buslogHm) {
		debug(1,"Post Request Business logic");
		return (HashMap) buslogHm;
	}

	@Override
	public HashMap preRetreiveProcessBL(Map buslogHm) {
		debug(1,"Pre Request Business logic");
		return (HashMap) buslogHm;
	}

	@Override
	public HashMap processRequest(Map buslogHm) {
		// TODO Auto-generated method stub
		return null;
	}

}
