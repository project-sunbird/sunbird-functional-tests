package org.sunbird.integration.test.bulkupload.batchenrollment;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BatchEnrolmentBulkUploadTest extends BaseCitrusTestRunner {

  private static final String TEMPLATE_DIR = "templates/bulkupload/batchenrolment";
  private static final String BATCH_ENROLMENT_SERVER_URI = "/api/course/v1/batch/bulk/enrollment";
  private static final String BATCH_ENROLMENT_LOCAL_URI = "/v1/batch/bulk/enrollment";

  @DataProvider(name = "createBatchEnrolmentBulkUploadSuccessDataProvider")
  public Object[][] createBatchEnrolmentBulkUploadSuccessDataProvider() {
    return new Object[][] {new Object[] {"testBatchEnrolmentBulkUploadSuccess", HttpStatus.OK}};
  }

  @DataProvider(name = "createBatchEnrolmentBulkUploadFailureDataProvider")
  public Object[][] createBatchEnrolmentBulkUploadFailureDataProvider() {
    return new Object[][] {
      new Object[] {"testBatchEnrolmentBulkUploadFailureWithInvalidColumn", HttpStatus.BAD_REQUEST},
      new Object[] {"testBatchEnrolmentBulkUploadFailureWithEmptyCsvFile", HttpStatus.BAD_REQUEST},
      new Object[] {
        "testBatchEnrolmentBulkUploadFailureWithoutCsvFile", HttpStatus.INTERNAL_SERVER_ERROR
      }
    };
  }

  @Test(dataProvider = "createBatchEnrolmentBulkUploadSuccessDataProvider")
  @CitrusParameters({"testName", "status"})
  @CitrusTest
  public void testBatchEnrolmentBulkUploadSuccess(String testName, HttpStatus status) {
    getAuthToken(this, true);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getBatchBulkEnrolmentUrl(),
        REQUEST_FORM_DATA,
        null,
        true,
        status,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "createBatchEnrolmentBulkUploadFailureDataProvider")
  @CitrusParameters({"testName", "status"})
  @CitrusTest
  public void testBatchEnrolmentBulkUploadFailure(String testName, HttpStatus status) {
    getAuthToken(this, true);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getBatchBulkEnrolmentUrl(),
        REQUEST_FORM_DATA,
        null,
        true,
        status,
        RESPONSE_JSON);
  }

  private String getBatchBulkEnrolmentUrl() {
    return getLmsApiUriPath(BATCH_ENROLMENT_SERVER_URI, BATCH_ENROLMENT_LOCAL_URI);
  }
}
