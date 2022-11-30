package org.fai.db;

	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

	
public class DBConnection {
	
	static Connection con;
	
		  private static void doSshTunnel( String strSshUser, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
		  {
		    final JSch jsch = new JSch();
		    
		    jsch.addIdentity("C:\\Ravi\\Work\\DbDetails\\DBKey\\bastion_private_keypair_staging.pem");
		    Session session = jsch.getSession( strSshUser, strSshHost, 22 );
		      final Properties config = new Properties();
		    config.put("StrictHostKeyChecking", "no" );
		    session.setConfig( config );
		     
		    session.connect();
		    session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
		  }
		   
		  public static void main(String[] args) throws SQLException, ParseException
		  {
			  String config = null;
		    try
		    {
		      String strSshUser = "ubuntu";                  // SSH loging username
		      String strSshHost = "52.8.166.35";          // hostname or ip or SSH server
		      int nSshPort = 22;                                    // remote SSH host port number
		      String strRemoteHost = "prod-aurora-tf-test.cluster-cezsr0pb70ij.us-west-1.rds.amazonaws.com";  // hostname or ip of your database server
		      int nLocalPort = 5432;                                // local port number use to bind SSH tunnel
		      int nRemotePort = 5432;                               // remote port number of your database 
		      String strDbUser = "ravichandra";                    // database loging username
		      String strDbPassword = "ravichandra@1234";                    // database login password
		     
		      String dbName = "xtract";
		      String JDBCURL = "jdbc:postgresql://"+strRemoteHost+":"+nLocalPort+"/"+dbName;
		      String query = "select category_name ,customer_id ,configurations  from master_category where category_name =? and customer_id =?";
		     
		      DBConnection.doSshTunnel(strSshUser,strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
		  	         
		      Class.forName("org.postgresql.Driver");
		      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/xtract", strDbUser, strDbPassword);
		      
		      PreparedStatement preparedStatement = con.prepareStatement(query);
		      preparedStatement.setString(1, "WCAB");
		      preparedStatement.setInt(2, 471);
		      ResultSet rs = preparedStatement.executeQuery();
		      
		      while (rs.next()) {
	                String catname= rs.getString("category_name");
	                int id = rs.getInt("customer_id");
	               config = rs.getString("configurations");
	                
	               System.out.println(catname + "," + id + "," + config);
	                
	            }
		     
		     // Get JSON object from JSON array.
				JSONObject jsonObject = new JSONObject(config);
				System.out.println("Second object:"+jsonObject.getJSONObject("order"));
				System.out.println("Second object:"+jsonObject.getJSONObject("naming_convention"));
		      	      
		    }
		    catch( Exception e )
		    {
		      e.printStackTrace();
		    }
		    finally
		    {
		      System.exit(0);
		      con.close();
		    }
		  
}
}

