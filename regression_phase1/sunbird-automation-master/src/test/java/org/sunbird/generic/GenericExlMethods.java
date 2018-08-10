package org.sunbird.generic;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import org.apache.log4j.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class GenericExlMethods {
	static Logger log = Logger.getLogger(GenericExlMethods.class.getName());

	/**
	 * Method Name : fetchTableHeaderColumn
	 * Purpose: This method retrieves test data from excel and stores in vector objects
	 * @param filePath -> Path of the excel document
	 * @param testDataSheetName -> Name of the excel sheet 
	 * @return None
	 */
	public static Vector<String> fetchTableHeaderColumn(String filePath, String testDataSheetName)	{
		log.info("Method: fetchData :: filePath = " + filePath 
				+ " test data sheetname = " + testDataSheetName);

		Vector<String> headers = new Vector<String>();

		File file = new File(filePath);
		Workbook workbook = null;  
		try {
			workbook = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			log.error("Method: fetchData :: BiffException occured = " + e.getMessage()); 
		} 
		catch (IOException e) {
			log.error("Method: fetchData :: IOException occured = " + e.getMessage());
		}

		try {
			Sheet sheet = workbook.getSheet(testDataSheetName);

			headers.clear();
			for (int i = 0; i < sheet.getColumns(); i++) { 
				Cell cell1 = sheet.getCell(i, 0); 
				headers.add(cell1.getContents()); 
			} 
		}
		catch(Exception e) {
			log.error("Method: fetchData :: exception = " + e);
		}
		return headers;
	}

	/**
	 * Method Name : fetchTableData
	 * Purpose: This method retrieves test data from excel and stores in vector objects
	 * @param filePath -> Path of the excel document
	 * @param testDataSheetName -> Name of the excel sheet 
	 * @return None
	 */
	public static Vector<String> fetchTableData(String filePath, 
			String testDataSheetName,
			int noOfRecordsToBeTestedConfig)	
	{

		Vector<String> data = new Vector<String>();
		log.info("Method: fetchData :: filePath = " + filePath + " test data sheetname = " + testDataSheetName);
		File file = new File(filePath);
		Workbook workbook = null;  
		try {
			workbook = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			log.error("Method: fetchData :: BiffException occured = " + e.getMessage()); 
		} 
		catch (IOException e) {
			log.error("Method: fetchData :: IOException occured = " + e.getMessage());
		}

		try {
			
			//1.here it reads entire all the columns of the excel sheet
			
			Sheet sheet = workbook.getSheet(testDataSheetName);

			data.clear(); //2.clears once all the cell are read and keeps only the row cells which we have given [Ex: get(0)]
			for (int j = 1; j < sheet.getRows() ; j ++)  {
				for (int i = 0; i < sheet.getColumns(); i++) {
					Cell cell = sheet.getCell(i, j);
					data.add(cell.getContents()); 
				}
			}
		}
		catch(Exception e) {
			log.error("Method: fetchData :: exception = " + e);
		}
		return data;
	}

}
