package org.sunbird.integration.test.bulkupload.location;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.annotation.CleanUp;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LocationBulkUploadTest extends BaseCitrusTestRunner {

  private static final String TEMPLATE_DIR = "templates/bulkupload/location";
  private static final String LOCATION_BULK_UPLOAD_SERVER_URI = "/api/data/v1/bulk/location/upload";
  private static final String LOCATION_BULK_UPLOAD_LOCAL_URI = "/v1/bulk/location/upload";

  @DataProvider(name = "stateBulkUploadSuccessDataProvider")
  public Object[][] stateBulkUploadSuccessDataProvider() {
    return new Object[][] {new Object[] {"testLocationBulkUploadOfStateTypeSuccess"}};
  }

  @DataProvider(name = "stateBulkUploadFailureDataProvider")
  public Object[][] stateBulkUploadFailureDataProvider() {
    return new Object[][] {
      new Object[] {
        "testLocationBulkUploadOfStateTypeFailureWithMissingMandatoryColumn", HttpStatus.BAD_REQUEST
      }
    };
  }

  @Test(dataProvider = "stateBulkUploadSuccessDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testLocationBulkUploadStateTypeSuccess(String testName) {
    getAuthToken(this, true);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getLocationBulkUploadUrl(),
        REQUEST_FORM_DATA,
        null,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "stateBulkUploadFailureDataProvider")
  @CitrusParameters({"testName", "status"})
  @CitrusTest
  public void testLocationBulkUploadStateTypeFailure(String testName, HttpStatus status) {
    getAuthToken(this, true);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getLocationBulkUploadUrl(),
        REQUEST_FORM_DATA,
        null,
        true,
        status,
        RESPONSE_JSON);
  }

  private String getLocationBulkUploadUrl() {
    return getLmsApiUriPath(LOCATION_BULK_UPLOAD_SERVER_URI, LOCATION_BULK_UPLOAD_LOCAL_URI);
  }

  @CleanUp
  /** Method to perform the cleanup after test suite completion. */
  public static void cleanUp() {}
}
