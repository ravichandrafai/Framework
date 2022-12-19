package org.fai.db;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToJavaConverter {
	public static void main(String args[]) throws Exception{
		JsonToJavaConverter converter = new JsonToJavaConverter();
		String jsonStr =  converter.getJsonString();  
		//converting JSON String to Java object
		SortedMap<Integer, String> orderMap = converter.stringToMap(jsonStr);
		System.out.println(orderMap);
		System.out.println(orderMap.get(1));
}
	
	public String getJsonString() throws Exception{
		com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();  
		File fileObj = new File("D:\\sts-workspace\\BetterReadsApp\\src\\main\\resources\\order.json");  
		Map<String, Object> orderComponents = mapper.readValue( fileObj, 
				new TypeReference<Map<String, Object>>() {  
        });   
		return orderComponents.get("order").toString();
	}
	
	public String getJsonString(String str) throws Exception{
		com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();  
		Map<String, Object> orderComponents = mapper.readValue( str, 
				new TypeReference<Map<String, Object>>() {  
        });   
		return orderComponents.get("order").toString();
	}
	
	public SortedMap<Integer, String> stringToMap(String strArg) throws Exception{
		System.out.println(strArg);
		strArg = strArg.substring(1, strArg.lastIndexOf('}'));
		Map<Integer, String> reconstructedMap = Arrays.stream(strArg.split(","))
	            .map(s -> s.split("="))
	            .collect(Collectors.toMap(s -> new Integer(s[1].trim()), s -> s[0].trim()));
		return new TreeMap<Integer, String>(reconstructedMap);
	}

}

