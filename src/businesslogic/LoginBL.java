package businesslogic;

import java.util.HashMap;
import java.util.Map;

public class LoginBL implements BaseBL {

 
	/**
	 * For testing
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public HashMap processRequest(Map hm) {
		String s[]= (String[]) hm.get("username");
		   
		 
		return null;
	}

	@Override
	public HashMap activitySubmit(Map hm) {
		 
		return null;
	}

	@Override
	public HashMap postRetreiveProcessBL(Map buslogHm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap preRetreiveProcessBL(Map buslogHm) {
		// TODO Auto-generated method stub
		return null;
	}

}
