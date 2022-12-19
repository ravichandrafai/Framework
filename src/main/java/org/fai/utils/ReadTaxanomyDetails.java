package org.fai.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ReadTaxanomyDetails {

	static List<Map<String, String>> listOfAllTests = new ArrayList<>();
	static Map<String,String> map = new HashMap<>();
		
	public static Map<String,String> getExtractiondetails(String catName,String docTypeName) {
		
		if(listOfAllTests.isEmpty()) {
			listOfAllTests= GetTaxanomyDetails.getTaxDetails();
			}
			
			List<Map<String, String>> listOfExecutableTests= new ArrayList<>();
			
			for(int i=0; i<listOfAllTests.size();i++) {
				
				String expCatName = listOfAllTests.get(i).get("Customer Category");
				String expDocType = listOfAllTests.get(i).get("Customer SubCategory");
				
				if(catName.contains(expCatName)&& docTypeName.contains(expDocType)) {

					listOfExecutableTests.add(listOfAllTests.get(i));
					map = listOfAllTests.get(i);
				}

			}
			return map;
		
	}
	
	
}
