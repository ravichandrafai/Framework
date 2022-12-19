package org.fai.db;

	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.fai.constants.FrameworkConstants;
import org.fai.enums.PropertyEnums;
import org.fai.utils.DecodeUtils;
import org.fai.utils.ReadProperties;
import org.json.simple.parser.ParseException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

	
public class DBConnection {
	
	private static Connection con;
	private static Session session;
	
	  private static void doSshTunnel( String strSshUser, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
	  {
	    final JSch jsch = new JSch();
	    String privateKeyName = ReadProperties.get(PropertyEnums.PRIVATEKEY);
	    String privateKeyPath = FrameworkConstants.getDBKeyPath();
	    String identity = privateKeyPath+privateKeyName;
	    jsch.addIdentity(identity);
	    session = jsch.getSession( strSshUser, strSshHost, 22 );
	    final Properties config = new Properties();
	    config.put("StrictHostKeyChecking", "no" );
	    session.setConfig( config );
	    session.connect();
	    session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	     
	  }
	   
	  public static Connection connectDatabase() throws SQLException, ParseException, ClassNotFoundException, JSchException
	  {
		  String strSshUser = ReadProperties.get(PropertyEnums.SSHUSER);                
	      String strSshHost = ReadProperties.get(PropertyEnums.SSHHOST);         
	      int nSshPort = Integer.valueOf(ReadProperties.get(PropertyEnums.SSHPORT));
	      String strRemoteHost = ReadProperties.get(PropertyEnums.REMOTEHOST);   
	      int nLocalPort = Integer.valueOf(ReadProperties.get(PropertyEnums.LOCALPORT));   
	      int nRemotePort = Integer.valueOf(ReadProperties.get(PropertyEnums.REMOTEPORT));   
	      String strDbUser = ReadProperties.get(PropertyEnums.DBUSER);   
	      String strencryptedDbPassword = ReadProperties.get(PropertyEnums.DBPASSWORD);   
	      String decryptedPassword = DecodeUtils.getDecodedString(strencryptedDbPassword);
	      String dbName = ReadProperties.get(PropertyEnums.DBNAME);   
	      String JDBCURL = "jdbc:postgresql://localhost"+":"+nLocalPort+"/"+dbName;
	      DBConnection.doSshTunnel(strSshUser,strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
		      Class.forName("org.postgresql.Driver");
		      con = DriverManager.getConnection(JDBCURL, strDbUser, decryptedPassword);
			return con;
  
	  }
	  public static void disconnectTunnel() {
		  
		  if (session != null) {
	            session.disconnect();
	        }
	  }
	  
}

