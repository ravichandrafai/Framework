package org.fai.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fai.constants.FrameworkConstants;

public class GetTaxanomyDetails {

	
	public static List<Map<String,String>> getTaxDetails(){
		
        
		
		List<Map<String,String>> listTests = new ArrayList<>();
		
		try(FileInputStream inputStream = new FileInputStream(FrameworkConstants.getExcelFilePath());
				XSSFWorkbook workbook=new XSSFWorkbook(inputStream);) {

			//Creating a Sheet object using the sheet Name
			XSSFSheet sheet=workbook.getSheet(FrameworkConstants.getTaxonomyDetailsSheetName());

			Map<String, String>  mapTaxonomyDetails = null;

			int lastRowNum;
			int lastColunNum;
			
			lastRowNum = sheet.getLastRowNum();
			lastColunNum = sheet.getRow(0).getLastCellNum();

			for(int i=1;i<=lastRowNum;i++){
				mapTaxonomyDetails = new HashMap<>();
				for(int j=0;j<lastColunNum;j++) {
					String key = sheet.getRow(0).getCell(j).getStringCellValue();
					String value = sheet.getRow(i).getCell(j).getStringCellValue();
					mapTaxonomyDetails.put(key, value);

				}
				listTests.add(mapTaxonomyDetails);
			}
			
		}catch(FileNotFoundException fe) {
			throw new RuntimeException("Excel file is not found");
		}catch(IOException ie) {
			throw new RuntimeException("Exception occured while reading the excel file");
		}
			return listTests;	 
	}
}