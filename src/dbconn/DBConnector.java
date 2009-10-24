package dbconn;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.sun.rowset.CachedRowSetImpl;


public class DBConnector { 
private void log(String s){
	//System.out.println(s);
}
private Connection getConnection()
{
	 
	
	Connection conn = null;
	try
    {
        String userName = "ams";
        String password = "ams";
      // String driverName ="com.mysql.jdbc.Driver";
        String driverName ="oracle.jdbc.OracleDriver";
      // String url = "jdbc:mysql://localhost:3306/ams";
        String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
      /*  
        //Main xml config to get hibernate config file path
       Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("src/cbrca/databaseconfig.xml"));
       //hibernate config file 
       Document doc2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("src/"+doc.getElementsByTagName("HibernateConfigFile").item(0).getTextContent()));
       
       NodeList nl = doc2.getDocumentElement().getElementsByTagName("property");
      
        for(int i=0;i<nl.getLength();i++){
        	if("connection.driver_class".equals(nl.item(i).getAttributes().getNamedItem("name").getNodeValue())) 
        	driverName = nl.item(i).getTextContent();
        	
        	if("connection.url".equals(nl.item(i).getAttributes().getNamedItem("name").getNodeValue())) 
        	url = nl.item(i).getTextContent();
        	
        	if("connection.username".equals(nl.item(i).getAttributes().getNamedItem("name").getNodeValue())) 
        	userName = nl.item(i).getTextContent();
        	
        	if("connection.password".equals(nl.item(i).getAttributes().getNamedItem("name").getNodeValue())) 
        	password = nl.item(i).getTextContent();
        
        }
       */
        Class.forName (driverName).newInstance ();
        conn = DriverManager.getConnection (url, userName, password);
        
        if(conn == null){
        	System.err.print("Some thing wrong with connecting with database!");
        }
        
        log ("Database connection established");
        //CachedRowSet crs;
        
    }
    catch (Exception e)
    {
        System.err.println ("Cannot connect to database server:"+e);
    }
    
    return conn;
}

public CachedRowSet executeQuery(String qry) throws SQLException{
	CachedRowSetImpl crs;
	Connection conn =null;
	try {
		
		conn = getConnection();
		Statement stmt = conn.createStatement(); 
		log(qry);
        ResultSet rs =  stmt.executeQuery(qry);
        
         
        crs = new CachedRowSetImpl();
        crs.populate(rs); 
        rs.close(); 
        stmt.close();
        
         
	} catch (SQLException e) {
		e.printStackTrace();
		throw e;
	}
	finally
    {
        if (conn != null)
        {
            try
            {
                conn.close ();
                conn =null;
               log ("Database connection terminated");
            }
            catch (Exception e) {
            e.printStackTrace();	
            /* ignore close errors */ }
        }
    }
	
	return crs;
}

public int executeUpdate(String qry) throws SQLException{
	Connection conn =null;
	int retval =0;
	try {
		
		conn = getConnection();
		Statement stmt = conn.createStatement(); 
        retval  = stmt.executeUpdate(qry);
	}catch(SQLException  e){
		e.printStackTrace(); 
		throw e;
	}
	finally
    {
        if (conn != null)
        {
            try
            {
                conn.close ();
                //log ("Database connection terminated");
            }
            catch (Exception e) { /* ignore close errors */ }
        }
    }
	return retval;
}

	/*
	 * testing 
	 */
	public static void main(String[] args) {
		try{
		CachedRowSet crs = new DBConnector().executeQuery("select empid,empname from request");
		while(crs.next()){
	        System.out.print("Name:"+crs.getString("empid"));
	        System.out.print(",Breanch:"+crs.getString("empname"));	     
	        }
		crs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
