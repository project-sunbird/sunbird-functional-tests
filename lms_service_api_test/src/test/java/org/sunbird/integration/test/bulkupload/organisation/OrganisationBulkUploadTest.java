package org.sunbird.integration.test.bulkupload.organisation;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrganisationBulkUploadTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testOrgBulkUploadFailureWithoutAccessToken";
  public static final String TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITHOUT_CSV_FILE =
      "testOrgBulkUploadFailureWithoutCsvFile";
  public static final String TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITH_EMPTY_CSV_FILE =
      "testOrgBulkUploadFailureWithEmptyCsvFile";
  public static final String TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITH_INVALID_COLUMN =
      "testOrgBulkUploadFailureWithInvalidColumn";
  public static final String TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITHOUT_COLUMN_HEADER_IN_CSV_FILE =
      "testOrgBulkUploadFailureWithoutColumnHeaderInCsvFile";
  public static final String TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITH_EXCEEDING_FILE_SIZE =
      "testOrgBulkUploadFailureWithExceedingFileSize";

  public static final String TEST_NAME_ORG_BULK_UPLOAD_SUCCESS = "testOrgBulkUploadSuccess";
  public static final String TEMPLATE_DIR = "templates/bulkupload/organisation";

  private String getOrgBulkUploadUrl() {
    return getLmsApiUriPath("/api/org/v1/upload", "/v1/org/upload");
  }

  @DataProvider(name = "orgBulkUploadFailureDataProvider")
  public Object[][] orgBulkUploadFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITHOUT_ACCESS_TOKEN, HttpStatus.UNAUTHORIZED, false
      },
      new Object[] {
        TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITHOUT_CSV_FILE, HttpStatus.BAD_REQUEST, true
      },
      new Object[] {
        TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITH_EMPTY_CSV_FILE, HttpStatus.BAD_REQUEST, true
      },
      new Object[] {
        TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITHOUT_COLUMN_HEADER_IN_CSV_FILE,
        HttpStatus.BAD_REQUEST,
        true
      },
      new Object[] {
        TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITH_INVALID_COLUMN, HttpStatus.BAD_REQUEST, true
      }
//      ,new Object[] {
//        TEST_NAME_ORG_BULK_UPLOAD_FAILURE_WITH_EXCEEDING_FILE_SIZE, HttpStatus.BAD_REQUEST, true
//      },
    };
  }

  @Test(dataProvider = "orgBulkUploadFailureDataProvider")
  @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired"})
  @CitrusTest
  public void testOrgBulkUploadFailure(
      String testName, HttpStatus httpStatusCode, boolean isAuthRequired) {
    getTestCase().setName(testName);
    getAuthToken(this, true);

    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOrgBulkUploadUrl(),
        REQUEST_FORM_DATA,
        null,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "orgBulkUploadSuccessDataProvider")
  public Object[][] orgBulkUploadSuccessDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_ORG_BULK_UPLOAD_SUCCESS, HttpStatus.OK, true},
    };
  }

  @Test(dataProvider = "orgBulkUploadSuccessDataProvider")
  @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired"})
  @CitrusTest
  public void testOrgBulkUploadSuccess(
      String testName, HttpStatus httpStatusCode, boolean isAuthRequired) {
    getTestCase().setName(testName);
    getAuthToken(this, true);

    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOrgBulkUploadUrl(),
        REQUEST_FORM_DATA,
        null,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
