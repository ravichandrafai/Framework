package org.fai.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fai.constants.FrameworkConstants;

public class GetDateFormatsFromExcel {
	
	public static void main(String[] ags) {
		
		getDateFormats();
	}

public static List<String> getDateFormats(){
		
        
		
		List<String> listDates = new ArrayList<>();
		
		try(FileInputStream inputStream = new FileInputStream(FrameworkConstants.getExcelFilePath());
				XSSFWorkbook workbook=new XSSFWorkbook(inputStream);) {

			//Creating a Sheet object using the sheet Name
			XSSFSheet sheet=workbook.getSheet(FrameworkConstants.getDateFormatsSheetName());

			int lastRowNum;
			
			
			lastRowNum = sheet.getLastRowNum();
			

			for(int i=1;i<=lastRowNum;i++){
						String dateFormat = sheet.getRow(i).getCell(1).getStringCellValue();
						listDates.add(dateFormat);
					

				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			return listDates;	 
			
	}
	
}
