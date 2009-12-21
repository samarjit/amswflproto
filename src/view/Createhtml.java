package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import dbconn.DBConnector;




public class Createhtml {
	private String datatype ;
	private String dbcol ;
	private String fname ;
	private String idname ;
	private String lblname ;
	private String ncol ;
	private String nrow ;
	private String panel_name ;
	private String strquery ;
	private String scr_Name ;
	private String validation ;
	private String classname;
	private String htmlelm;
	private String BUTTONPANEL = "buttonPanel";
	private int  COL1ENTRY1  = 1; 
	private int  COL2ENTRY1  = 2; 
	
	public List<String> getPanels(String screenName){
		String SQL = 
			"SELECT panel_name   FROM  screen_panel where scr_name='"+screenName+"' order by SORTORDER ASC";
		DBConnector db = new DBConnector();
		List<String> panelNames = new ArrayList<String>();
		try {
			CachedRowSet crs = db.executeQuery(SQL);
			while(crs.next()){
				panelNames.add( crs.getString("panel_name"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 	panelNames;
	}
	
	public String getTemplateName(String screenName){
		String SQL = 
			"SELECT template_name   FROM  screen  where   scr_name='"+screenName+"'";
		//System.out.println(SQL);
		String templName = "";
		DBConnector db = new DBConnector();
		try {
			CachedRowSet crs = db.executeQuery(SQL);
			while(crs.next()){
				templName =  crs.getString("template_name");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 	templName;
	}
	
	public String makehtml(String screenName, String panelName){
		String elmStr="";
		String panelCssClassName = "";
		HTable htable =null;
		DBConnector db = new DBConnector();
		//1: two rows per entry for label field type, 2: for one column per entry
		int panelType = COL2ENTRY1; 
		
		String paneltypeSQL = 
			"select paneltype, CSS_CLASS from screen_panel where panel_name = '"+panelName+"' and scr_name='"+screenName+"'";

		//System.out.println(paneltypeSQL);
		try{
			CachedRowSet crs = db.executeQuery(paneltypeSQL);
			if(crs.next()){
				String panel  = crs.getString("paneltype");
				panelCssClassName = crs.getString("CSS_CLASS");
				if(null!=panel && !"".equalsIgnoreCase(panel))
				     panelType = Integer.parseInt(panel);  //else paneltype =  COL2ENTRY1;
				else {
					System.out.println("Going with default panel type");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		String SQL = 
			"SELECT scr_Name, panel_name, lblname, fname, idname, datatype," +
					" dbcol, validation, strquery , nrow, ncol,classname,htmlelm FROM  panel_fields where scr_name='"+screenName+"' AND panel_name='"+panelName+"' order by orderNo";
		//System.out.println(SQL);
		
		try {
			CachedRowSet crs = db.executeQuery(SQL);
			int maxrows=0,xrow=0;
			int maxcols=0,xcol=0;
			while(crs.next()){
				ncol = crs.getString("ncol");
				if(ncol !=null )
					xcol = Integer.parseInt(ncol);
				
				nrow = crs.getString("nrow");
				if(nrow != null)
					xrow = Integer.parseInt(nrow);
				
				if( maxcols < xcol)maxcols = xcol;
				if( maxrows < xrow)maxrows = xrow;
				
				
			}
			crs.absolute(-1); //resetting crs to start point to parse it again
			
			System.out.println("panelType:"+panelType);
			if(panelType == COL2ENTRY1)
				htable = new HTable((maxrows+1),(maxcols+1)*2);
			else if(panelType == COL1ENTRY1){
				htable = new HTable((maxrows+1),(maxcols+1));
			}
			ncol ="0";
			nrow="0";
			htable.setTableId(panelName);
			
			htable.setCssClassName(panelCssClassName);
			while(crs.next()){
				
			
				datatype = crs.getString("datatype");
				dbcol = crs.getString("dbcol");
				fname = crs.getString("fname");
				idname = crs.getString("idname");
				lblname = crs.getString("lblname");
				ncol = crs.getString("ncol");
				nrow = crs.getString("nrow");
				panel_name = crs.getString("panel_name");
				strquery = crs.getString("strquery");
				scr_Name = crs.getString("scr_Name");
				validation = crs.getString("validation");
				classname = crs.getString("classname");
				htmlelm = crs.getString("htmlelm");
				//System.out.println(panel_name+" :" + nrow + " "+ncol);
				if("TEXTBOX".equalsIgnoreCase(htmlelm)){
					elmStr = lblname;
					htable.add(Integer.parseInt(nrow), Integer.parseInt(ncol)*2, elmStr);
					elmStr = "<input type=\"text\" name='"+fname+"' id='"+idname+"' value='' />";
					htable.add(Integer.parseInt(nrow), Integer.parseInt(ncol)*2+1, elmStr);
				}
				if("LABEL".equalsIgnoreCase(htmlelm)){
					elmStr = lblname;
					htable.add(Integer.parseInt(nrow), Integer.parseInt(ncol)*2, elmStr);
					elmStr = "";
					htable.add(Integer.parseInt(nrow), Integer.parseInt(ncol)*2+1, elmStr);
				}
				if(htmlelm == null || "".equalsIgnoreCase(htmlelm)){
					elmStr = lblname;
					htable.add(Integer.parseInt(nrow), Integer.parseInt(ncol)*2, elmStr);
					elmStr = " name='"+fname+"' id='"+idname+"#"+dbcol+"' ";
					htable.add(Integer.parseInt(nrow), Integer.parseInt(ncol)*2+1, elmStr);
				}
				
				if("CHECKBOX".equalsIgnoreCase(htmlelm)){
					
				}
				if("RADIO".equalsIgnoreCase(htmlelm)){
					
				}
				if("TEXTAREA".equalsIgnoreCase(htmlelm)){
					
				}
				if("BUTTON".equalsIgnoreCase(htmlelm)){
					elmStr = lblname;
				//	htable.add(Integer.parseInt(nrow), Integer.parseInt(ncol)*2, "");
				//	elmStr = "<input type=\"button\" name='"+fname+"' id='"+idname+"' value='"+lblname+"'  "+validation+" />";
				//	elmStr = "<div class=\"clear\" "+validation+"><a href=\"#\" class=\"button\" name='"+fname+"' id='"+idname+"'     ><SPAN>"+lblname+"</SPAN></a></div>";
					elmStr = "<button "+validation+" class=\"button\" name='"+fname+"' id='"+idname+"'     >"+lblname+"</button>";
					htable.add(Integer.parseInt(nrow), Integer.parseInt(ncol), elmStr);
				}
			
			}
			//System.out.println(htable.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
return (String)htable.toString();
	} 
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Createhtml htmlc = new Createhtml();
		htmlc.makehtml("frmRequest","panelFields");

	}

	public String getJsCsshtml(String screenName) {
		 String jsStr="";
		 String cssStr = "";
		 String[] arjsStr = {};
		 String[] arcssStr = {};
		 String htmlStr = "";
//		 String SQL = "select jsname,cssname from screen_panel where SORTORDER =1  and scr_name='"+screenName+"'";
		 String SQL = "select jsname,cssname from screen  where   scr_name='"+screenName+"'";

		 DBConnector db = new DBConnector();
		 try {
			CachedRowSet crs = db.executeQuery(SQL);
			while(crs.next()){
				jsStr = crs.getString("jsname");
				cssStr = crs.getString("cssname");
			}
			if(jsStr!=null && !"".equals(jsStr))
			arjsStr = jsStr.split(",");
			
			if(cssStr != null && !"".equals(cssStr))
			arcssStr = cssStr.split(",");
			for(String s :arjsStr){
				htmlStr +=	"<script language=\"JavaScript\" src=\"js/"+s+"\"></script>\n";
			}
			for(String s :arcssStr){
				htmlStr +=  "<LINK href=\"css/"+s+"\"  rel=\"stylesheet\" type=\"text/css\">\n";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 
		return htmlStr;
	}

}
