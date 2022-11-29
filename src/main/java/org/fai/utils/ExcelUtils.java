package org.fai.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fai.constants.FrameworkConstants;
import org.fai.exceptions.FrameworkException;
import org.fai.exceptions.InvalidPathForExcelException;
import org.fai.reports.FrameworkLogger;

/**
 * Utility class to read or write to excel.
 * 
 * 
 * @see org.fai.listeners.MethodInterceptor
 * @see DataProviderUtils
 */
public final class ExcelUtils {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private ExcelUtils() {}

	/**
	 * Encapsulates all the value from the mentioned excel sheet and store it in
	 * List holding HashMaps. Key for the map in the column header in the excel sheet.
	 * 
	 * 
	 * @param sheetname Excel sheetname to read the value from
	 * @return List where each index holds a map and Each map holds the details about the test
	 */
	public static List<Map<String,String>> getTestDetails(String sheetName){

		FrameworkLogger.logTrace("inside getTestDetails method of ExcelUtils");
		List<Map<String,String>> listTests = new ArrayList<>();
		
		try(FileInputStream inputStream = new FileInputStream(FrameworkConstants.getExcelFilePath());
				XSSFWorkbook workbook=new XSSFWorkbook(inputStream);) {

			//Creating a Sheet object using the sheet Name
			XSSFSheet sheet=workbook.getSheet(sheetName);

			Map<String, String>  mapTestDetails = null;

			int lastRowNum;
			int lastColunNum;
			
			lastRowNum = sheet.getLastRowNum();
			lastColunNum = sheet.getRow(0).getLastCellNum();

			for(int i=1;i<=lastRowNum;i++){
				mapTestDetails = new HashMap<>();
				for(int j=0;j<lastColunNum;j++) {
					String key = sheet.getRow(0).getCell(j).getStringCellValue();
					String value = sheet.getRow(i).getCell(j).getStringCellValue();
					mapTestDetails.put(key, value);

				}
				listTests.add(mapTestDetails);
			}
			
		}catch(FileNotFoundException fe) {
			throw new InvalidPathForExcelException("Excel file is not found");
		}catch(IOException ie) {
			throw new FrameworkException("Exception occured while reading the excel file");
		}
		FrameworkLogger.logTrace("end of getTestDetails method of ExcelUtils");
		return listTests;



	}
	
	public static void updateTestStatus(String testName, String testStatus) {

		FrameworkLogger.logTrace("inside getTestDetails method of ExcelUtils");

		try(FileInputStream inputStream = new FileInputStream(FrameworkConstants.getExcelFilePath());
				XSSFWorkbook workbook=new XSSFWorkbook(inputStream);) {

			//Creating a Sheet object using the sheet Name
			XSSFSheet sheet=workbook.getSheet(FrameworkConstants.getTestDataSheetName());

			XSSFCellStyle style=workbook.createCellStyle();
            XSSFRow row;
			XSSFCell cell;
			
			int lastRowNum;
			int lastColunNum;
			
			int testRowNo = 0;
			lastRowNum = sheet.getLastRowNum();
			lastColunNum = sheet.getRow(0).getLastCellNum();

			for(int i=1;i<=lastRowNum;i++){
				row = sheet.getRow(i);
				testRowNo = testRowNo +1;
				String cellValue= row.getCell(0).getStringCellValue();
				String exeStatus = row.getCell(1).getStringCellValue();
				cell = sheet.getRow(0).createCell(lastColunNum+1);
				cell.setCellValue("status");
				cell.setCellStyle(style);
				 Font font = workbook.createFont();  
				 font.setFontName("Calibri");
				 font.setFontHeightInPoints((short)22);
				 font.setColor(IndexedColors.WHITE.getIndex());
				 style.setFont(font);
				 cell.setCellStyle(style);
				if(cellValue.equalsIgnoreCase(testName) && exeStatus.equalsIgnoreCase("yes"))
				{
					
					cell = row.createCell(lastColunNum+1);
					if(testStatus.equalsIgnoreCase("pass")) {
						cell.setCellValue(testStatus);
						style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			            style.setFont(font);
			            sheet.getRow(i).getCell(lastColunNum+1).setCellStyle(style);
			            
						
					}else {
						cell.setCellValue(testStatus);
						style.setFillBackgroundColor(IndexedColors.RED.getIndex());
			            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			            style.setFont(font);
			            sheet.getRow(i).getCell(lastColunNum+1).setCellStyle(style);
						
					}
				}	
			}
			File outPutFIle = new File(FrameworkConstants.getTestResultPath()+"TestResult.xlsx");
			FileOutputStream file = new FileOutputStream(outPutFIle);
            workbook.write(file);
            file.close();
		}catch(FileNotFoundException fe) {
			throw new InvalidPathForExcelException("Excel file is not found");
		}catch(IOException ie) {
			throw new FrameworkException("Exception occured while reading the excel file");
		}
		FrameworkLogger.logTrace("end of getTestDetails method of ExcelUtils");


	}
	
	
}
