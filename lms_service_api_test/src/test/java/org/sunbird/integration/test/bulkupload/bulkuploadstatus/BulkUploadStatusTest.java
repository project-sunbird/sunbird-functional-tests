package org.sunbird.integration.test.bulkupload.bulkuploadstatus;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.BulkUploadUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BulkUploadStatusTest extends BaseCitrusTestRunner {

  private static final String TEMPLATE_DIR = "templates/bulkupload/status";
  private static final String BULK_UPLOAD_STATUS_SERVER_URI = "/api/data/v1/upload/status";
  private static final String BULK_UPLOAD_STATUS_LOCAL_URI = "/v1/upload/status";
  private static final String TEST_BULK_UPLOAD_STATUS_SUCCESS_WITH_VALID_PROCESS_ID =
      "testBulkUploadStatusSuccessWithValidProcessId";
  private static final String TEST_BULK_UPLOAD_STATUS_FAILURE_WITH_INVALID_PROCESS_ID =
      "testBulkUploadStatusFailureWithInvalidProcessId";

  @DataProvider(name = "bulkUploadStatusSuccessDataProvider")
  public Object[][] bulkUploadStatusSuccessDataProvider() {
    return new Object[][] {
      new Object[] {TEST_BULK_UPLOAD_STATUS_SUCCESS_WITH_VALID_PROCESS_ID, HttpStatus.OK}
    };
  }

  @DataProvider(name = "bulkUploadStatusDataFailureProvider")
  public Object[][] bulkUploadStatusDataFailureProvider() {
    return new Object[][] {
      new Object[] {TEST_BULK_UPLOAD_STATUS_FAILURE_WITH_INVALID_PROCESS_ID, HttpStatus.NOT_FOUND}
    };
  }

  @Test(dataProvider = "bulkUploadStatusSuccessDataProvider")
  @CitrusParameters({"testName", "status"})
  @CitrusTest
  public void testBulkUploadStatusSuccess(String testName, HttpStatus status) {
    getAuthToken(this, true);
    beforeTest();
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getBulkUploadStatusUrl(
            (String) testContext.getVariables().get(Constant.BULK_UPLOAD_PROCESS_ID)),
        true,
        status,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "bulkUploadStatusDataFailureProvider")
  @CitrusParameters({"testName", "status"})
  @CitrusTest
  public void testBulkUploadStatusFailure(String testName, HttpStatus status) {
    getAuthToken(this, true);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getBulkUploadStatusUrl("invalid-123"),
        true,
        status,
        RESPONSE_JSON);
  }

  private String getBulkUploadStatusUrl(String processId) {
    return getLmsApiUriPath(BULK_UPLOAD_STATUS_SERVER_URI, BULK_UPLOAD_STATUS_LOCAL_URI, processId);
  }

  private void beforeTest() {
    BulkUploadUtil.orgBulkUpload(
        this,
        testContext,
        "templates/bulkupload/organisation",
        "testOrgBulkUploadSuccess",
        HttpStatus.OK,
        config);
  }
}
