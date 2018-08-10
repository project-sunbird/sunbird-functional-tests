package org.sunbird.integration.test.bulkupload.location;

import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;

public class LocationBulkUploadTest extends BaseCitrusTestRunner {

	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITHOUT_TYPE =
			"testLocationBulkUploadFailureWithoutType";
	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITHOUT_CSV_FILE =
			"testLocationBulkUploadFailureWithoutCsvFile";
	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITHOUT_CODE_COLUMN =
			"testLocationBulkUploadFailureWithoutCodeColumn";
	
	
	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_SUCCESS_WITH_STATE_TYPE =
			"testLocationBulkUploadSuccessWithStateType";
	
	/*
	 * Need to work on this - JIRA Request - SB-6272
	 * 
	 */
	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITH_EMPTY_CSV_FILE =
			"testLocationBulkUploadFailureWithEmptyCsvFile";	
	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITHOUT_COLUMN_HEADER_IN_CSV_FILE =
			"testLocationBulkUploadFailureWithoutColumnHeaderInCsvFile";
	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITH_INVALID_COLUMN =
			"testLocationBulkUploadFailureWithInvalidColumn";
	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITH_INVALID_TYPE =
			"testLocationBulkUploadFailureWithInvalidType";
	public static final String TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITH_EXCEEDING_FILE_SIZE =
			"testLocationBulkUploadFailureWithExceedingFileSize";
	
	
	public static final String TEMPLATE_DIR = "templates/bulkupload/location";

	private String getLocationBulkUploadUrl() {
		return getLmsApiUriPath("/api/data/v1/bulk/location/upload", "/v1/bulk/location/upload");
	}

	@DataProvider(name = "locationBulkUploadFailureDataProvider")
	public Object[][] locationBulkUploadFailureDataProvider() {

		return new Object[][] {
			new Object[] {TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITHOUT_TYPE, HttpStatus.BAD_REQUEST},
			new Object[] {TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITHOUT_CSV_FILE, HttpStatus.BAD_REQUEST},
			new Object[] {TEST_NAME_LOCATION_BULK_UPLOAD_FAILURE_WITHOUT_CODE_COLUMN, HttpStatus.BAD_REQUEST},			
		};
	}

	@Test(dataProvider = "locationBulkUploadFailureDataProvider")
	@CitrusParameters({"testName", "httpStatusCode"})
	@CitrusTest
	public void testLocationBulkUploadFailure(String testName, HttpStatus httpStatusCode) {
		getTestCase().setName(testName);
		performMultipartTest(
				this,
				TEMPLATE_DIR,
				testName,
				getLocationBulkUploadUrl(),
				REQUEST_FORM_DATA,
				null,
				false,
				httpStatusCode,
				RESPONSE_JSON);
	}

	@DataProvider(name = "locationBulkUploadSuccessDataProvider")
	public Object[][] locationBulkUploadSuccessDataProvider() {

		return new Object[][] {
			new Object[] {TEST_NAME_LOCATION_BULK_UPLOAD_SUCCESS_WITH_STATE_TYPE,HttpStatus.OK},
			
		};
	}

	@Test(dataProvider = "locationBulkUploadSuccessDataProvider")
	@CitrusParameters({"testName","httpStatusCode"})
	@CitrusTest
	public void testLocationBulkUploadSuccess(String testName, HttpStatus httpStatusCode) {
		getTestCase().setName(testName);
			performMultipartTest(
				this,
				TEMPLATE_DIR,
				testName,
				getLocationBulkUploadUrl(),
				REQUEST_FORM_DATA,
				null,
				false,
				httpStatusCode,
				RESPONSE_JSON);
	}
}
