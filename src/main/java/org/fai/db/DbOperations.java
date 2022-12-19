package org.fai.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.json.simple.parser.ParseException;

import com.jcraft.jsch.JSchException;

public class DbOperations {

	static Connection con;
	static String configDetails ;
	static String optionalFields;
	static String category;
	static String subCategory;
	
	public static List<Map<String,String>> getFieldOrder(String customerName) {
		
		String query = "select mc.category_name ,fsm.customer_subcategory ,mc.configurations->>'order' as configurations,fsm.file_name_convention->>'field_names' as fieldnames,fsm.file_name_convention->>'seperators' as seperators,fsm.external_tags from master_category mc, fai_subcategory_mapping fsm  where fsm.customer_name =? and mc.customer_id =(select customer_id from customer_master cm where cm.\"CustomerName\"=?) and mc.category_name =fsm.customer_category and fsm.is_delete is null order by mc.category_name"; 
		ResultSet rs= getDataFromDB(customerName,query);
		List<Map<String,String>> listCatMaps = null;
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
		
		int coulumnCount = rsmd.getColumnCount();
		listCatMaps = new ArrayList<>();
		Map<String, String>  mapTestDetails = null;

		while (rs.next()) {
			mapTestDetails = new TreeMap<>();
			for(int i=1;i<=coulumnCount;i++) {
				String key = rsmd.getColumnName(i);
				String value = rs.getString(i);
				mapTestDetails.put(key, value);
				if(i==coulumnCount) {
					String allFields=stringToMap(mapTestDetails.get("configurations"),mapTestDetails.get("fieldnames"));
					mapTestDetails.put("fieldOrder", allFields);

				}
			}
			listCatMaps.add(mapTestDetails);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCatMaps;
	}
	private static ResultSet getDataFromDB(String customerName,String query) {
		
		
		ResultSet rs = null;
		try {
			con = DBConnection.connectDatabase();
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, customerName);
			preparedStatement.setString(2, customerName);
			rs = preparedStatement.executeQuery();


		}catch(SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			if(Objects.nonNull(con)) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			DBConnection.disconnectTunnel();
		}

		return rs;
	}
	private static String stringToMap(String allFilds, String optionalFields){
		Map<Integer, String> dbFieldOrderMap = null;
		String[] optionsFlds;
		allFilds = allFilds.substring(1, allFilds.lastIndexOf('}'));
		optionalFields = optionalFields.substring(1, optionalFields.lastIndexOf(']'));
		dbFieldOrderMap = Arrays.stream(allFilds.split(","))
				.map(s -> s.split(":"))
				.collect(Collectors.toMap(s -> new Integer(s[1].trim().replace("\"","")), s -> s[0].trim().replace("\"","")));
		optionsFlds =optionalFields.split(",");
		ArrayList<String> expFieldsList = new ArrayList<String>();
		ArrayList<String> finalOrderList = new ArrayList<String>();
		expFieldsList.add(dbFieldOrderMap.get(2).trim().replace("\"",""));
		expFieldsList.add(dbFieldOrderMap.get(3).trim().replace("\"",""));
		expFieldsList.add(dbFieldOrderMap.get(4).trim().replace("\"",""));
		expFieldsList.add(dbFieldOrderMap.get(5).trim().replace("\"",""));
		expFieldsList.add(dbFieldOrderMap.get(6).trim().replace("\"",""));
		for(String opFld:optionsFlds) {
			expFieldsList.add(opFld.trim().replace("\"",""));
		}
		Iterator<Entry<Integer, String>>
		iterator = dbFieldOrderMap.entrySet().iterator();
		while (iterator.hasNext()) {
			boolean isExists=false;
			Map.Entry<Integer, String>
			entry
			= iterator.next();
			String sss= entry.getValue();
			// Check if this value is the required value
			for(String s:expFieldsList) {

				if (sss.equals(s)) {
					isExists=true;
				}
			}
			if(isExists==false)
				iterator.remove();
		}
		iterator = dbFieldOrderMap.entrySet().iterator();
		while (iterator.hasNext()) {
			
			Map.Entry<Integer, String>
			entry
			= iterator.next();
			String fieldName= entry.getValue();
			finalOrderList.add(fieldName);
			
		}
		return finalOrderList.toString();
	}
	public static List<Map<String,String>> getFileNameFormat(String customerName) throws SQLException {
		String query = "select mc.category_name ,fsm.customer_subcategory ,fsm.file_name_convention->>'field_names' as fieldnames,fsm.file_name_convention->>'seperators' as seperators from master_category mc, fai_subcategory_mapping fsm  where fsm.customer_name =? and mc.customer_id =(select customer_id from customer_master cm where cm.\"CustomerName\"=?) and mc.category_name =fsm.customer_category and fsm.is_delete is null order by mc.category_name";
		ResultSet rs= getDataFromDB(customerName,query);
		ResultSetMetaData rsmd=rs.getMetaData();
		int coulumnCount = rsmd.getColumnCount();
		List<Map<String,String>> listCatMaps = new ArrayList<>();
		Map<String, String>  mapFileNameFormatDetails = null;

		while (rs.next()) {
			mapFileNameFormatDetails = new TreeMap<>();
			for(int i=1;i<=coulumnCount;i++) {
				String key = rsmd.getColumnName(i);
				String value = rs.getString(i);
				mapFileNameFormatDetails.put(key, value);
				if(i==coulumnCount) {
					List<String> fields=jsonToString(mapFileNameFormatDetails.get("fieldnames"));
					List<String> seperators = jsonToString(mapFileNameFormatDetails.get("seperators"));
					//String fielName = generateFileNameFormat(fields,seperators);
					mapFileNameFormatDetails.put("fileNameFields", fields.toString());
					mapFileNameFormatDetails.put("seperators", seperators.toString());

				}
			}
			listCatMaps.add(mapFileNameFormatDetails);
			
		}
		return listCatMaps;
	}
	private static List<String> jsonToString(String fileNameFields) {
		
		String[] optionsFlds;
		fileNameFields = fileNameFields.substring(1, fileNameFields.lastIndexOf(']'));
		optionsFlds =fileNameFields.split(",");
		ArrayList<String> expFieldsList = new ArrayList<String>();
		for(String opFld:optionsFlds) {
			String tmp = opFld.trim();
			expFieldsList.add(tmp);
		}
		return expFieldsList;
		
	}
	private static String generateFileNameFormat(List<String> fields,List<String> seperators) {
		String expFileNameFormat = "" ;
		for(int i=0;i<fields.size();i++) {
			expFileNameFormat=expFileNameFormat+fields.get(i)+seperators.get(i);
			
		}
		
		
		return expFileNameFormat;
	}
	
}
