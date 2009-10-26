package util;

import java.util.HashMap;

public class Utility {
	/**
	 * @return where clause as key value pair in hashmaps, these hashmaps are again encapsulated into 
	 * another hashmap whose key will be panelName
	 */
	public static HashMap<String, String> extractWhereClause(String str/*String whereStringOfPanel String panelName */){
		//"whereClausefrmRequest"
		
		String strWhereClause = str;
		String[] arWhere = strWhereClause.split("~#");
		HashMap<String,String> whereClausePart = new HashMap<String, String>();
		for(String strWhrKVpair:arWhere){
			String[] kvpair = strWhrKVpair.split("!");
			whereClausePart.put(kvpair[0], kvpair[1]);
		}
		return whereClausePart;
	}
	
	public static HashMap<String, String> extractKeyValPair(String str/*String whereStringOfPanel String panelName */){
		//"whereClausefrmRequest"
		
		String strWhereClause = str;
		String[] arWhere = strWhereClause.split("~#");
		HashMap<String,String> whereClausePart = new HashMap<String, String>();
		for(String strWhrKVpair:arWhere){
			String[] kvpair = strWhrKVpair.split("!");
			if(kvpair[0] !=null )
			whereClausePart.put(kvpair[0].toLowerCase(), kvpair[1]);
		}
		return whereClausePart;
	}
	
}
