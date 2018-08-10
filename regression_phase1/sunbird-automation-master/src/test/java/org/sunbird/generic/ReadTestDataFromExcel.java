package org.sunbird.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.sunbird.testdata.TestDataForSunbird;

public class ReadTestDataFromExcel extends GetExcelFileData {

	static Logger log = Logger.getLogger(ReadTestDataFromExcel.class.getName());
	static Vector<String> headers = new Vector<String>();
	static Vector<String> data = new Vector<String>();
	static int noOfRecordsToBeTestedConfig =0, noOfRecordsToBeTested=0;

	public static List <TestDataForSunbird>  getTestDataForSunbird(String callingTestScript) 
	{
		List <TestDataForSunbird> objListOfTestDataForSunbird = new ArrayList<TestDataForSunbird>();
		int columnIndex = 0, sizeofHeaders = 0; 
		String columnName = "", dataValue = "";
		TestDataForSunbird objTDForSunBird = new TestDataForSunbird();
		String testDataPath = "", sheetName = "";
		try	{
			ReadTestDataFromExcel.getColumnNames();
			testDataPath = getTestDataPath(callingTestScript);
			System.out.println("Checking"+testDataPath);
			sheetName = getSheetName(callingTestScript);
			log.info("Method:getTestDataForSearch :: equipmentTestDataPath = " + testDataPath 
					+ " sheetname = " + sheetName
					+ " noOfRecordsToBeTestedConfig = " + noOfRecordsToBeTestedConfig);
			noOfRecordsToBeTestedConfig = 100;
			headers = GenericExlMethods.fetchTableHeaderColumn(testDataPath, sheetName);
			data = GenericExlMethods.fetchTableData(testDataPath, sheetName, noOfRecordsToBeTestedConfig);

			noOfRecordsToBeTested = data.size();
			sizeofHeaders = headers.size();

			log.debug("Method:getTestDataForSearch :: no of records to be tested = " + noOfRecordsToBeTested 
					+ "data.size() = " + data.size() + " headers.size() = " + headers.size()
					+ " sizeofHeaders = " + sizeofHeaders );

			if (noOfRecordsToBeTested > 0 && headers.size() > 0 && data.size() > 0 ) {
				for (int row = 0; row < noOfRecordsToBeTested ; row++)	{
					columnName = headers.get(columnIndex).toString().trim();
					dataValue = data.get(row).toString();
					if (columnName != null)		
					{

						if(columnName.equals(username.trim()))	
							objTDForSunBird.setUsername(dataValue);
						else if(columnName.equals(password.trim()))	
							objTDForSunBird.setPassword(dataValue);
						else if(columnName.equals(firstName.trim()))	
							objTDForSunBird.setFirstName(dataValue);	
						else if(columnName.equals(lastName.trim()))	
							objTDForSunBird.setLastName(dataValue);
						else if(columnName.equals(phone.trim()))	
							objTDForSunBird.setPhone(dataValue);
						else if(columnName.equals(email.trim()))	
							objTDForSunBird.setEmail(dataValue);			
						else if(columnName.equals(role.trim()))	
							objTDForSunBird.setRole(dataValue);
						else if(columnName.equals(addresslane1.trim()))
							objTDForSunBird.setAddressLane1(dataValue);
						else if(columnName.equals(addresslane2.trim()))
							objTDForSunBird.setAddressLane2(dataValue);
						else if(columnName.equals(city.trim()))
							objTDForSunBird.setCity(dataValue);
						else if(columnName.equals(state.trim()))
							objTDForSunBird.setState(dataValue);
						else if(columnName.equals(country.trim()))
							objTDForSunBird.setCountry(dataValue);
						else if(columnName.equals(pincode.trim()))
							objTDForSunBird.setPincode(dataValue);
						else if(columnName.equals(executeTest.trim()))	
							objTDForSunBird.setExecuteTest(dataValue);


						else if(columnName.equals(summary.trim()))	
							objTDForSunBird.setSummary(dataValue);
						else if(columnName.equals(occupation.trim()))	
							objTDForSunBird.setOccupation(dataValue);
						else if(columnName.equals(designation.trim()))	
							objTDForSunBird.setDesignation(dataValue);
						else if(columnName.equals(organization.trim()))	
							objTDForSunBird.setOrganization(dataValue);
						else if(columnName.equals(degree.trim()))	
							objTDForSunBird.setDegree(dataValue);
						else if(columnName.equals(percentage.trim()))	
							objTDForSunBird.setPercentage(dataValue);
						else if(columnName.equals(grade.trim()))	
							objTDForSunBird.setGrade(dataValue);
						else if(columnName.equals(institution.trim()))	
							objTDForSunBird.setInstitution(dataValue);
						else if(columnName.equals(board.trim()))	
							objTDForSunBird.setBoard(dataValue);
						else if(columnName.equals(skills.trim()))	
							objTDForSunBird.setSkills(dataValue);
						else if(columnName.equals(afirstName.trim()))	
							objTDForSunBird.setAfirstName(dataValue);
						else if(columnName.equals(alastName.trim()))	
							objTDForSunBird.setAlastName(dataValue);
						else if(columnName.equals(currentlocation.trim()))	
							objTDForSunBird.setCurrentlocation(dataValue);

						else if(columnName.equals(courseName.trim()))	
							objTDForSunBird.setCourseName(dataValue);
						else if(columnName.equals(courseDescription.trim()))	
							objTDForSunBird.setCourseDescription(dataValue);
						else if(columnName.equals(title.trim()))	
							objTDForSunBird.setTitle(dataValue);
						else if(columnName.equals(titleDescription.trim()))	
							objTDForSunBird.setTitleDescription(dataValue);
						


					}

					columnIndex = columnIndex + 1;

					if (columnIndex == sizeofHeaders)	{
						if ((objTDForSunBird.getExecuteTest().toLowerCase().contains("yes")) == true)
							objListOfTestDataForSunbird.add(objTDForSunBird);
						columnIndex = 0;
						objTDForSunBird = new TestDataForSunbird();					
					}
				} 

			} 

		}
		catch(Exception e) {
			log.error("Method:getTestDataForSearch :: exception = " + e.getMessage()  + e.getStackTrace());
			e.printStackTrace();
		}

		log.info("Method:getTestDataForSearch :: returning size of test records = " + objListOfTestDataForSunbird.size());	
		return objListOfTestDataForSunbird;
	}


}
