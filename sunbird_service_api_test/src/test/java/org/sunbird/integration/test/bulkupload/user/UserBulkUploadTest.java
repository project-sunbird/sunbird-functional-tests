package org.sunbird.integration.test.bulkupload.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserBulkUploadTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testUserBulkUploadFailureWithoutAccessToken";
  public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_ORG_DETAIL =
      "testUserBulkUploadFailureWithoutOrgDetails";
  public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_INVALID_ORG_ID =
      "testUserBulkUploadFailureWithInvalidOrgId";
  public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_CSV_FILE =
      "testUserBulkUploadFailureWithoutCsvFile";
  public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_EMPTY_CSV_FILE =
      "testUserBulkUploadFailureWithEmptyCsvFile";
  public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_COLUMN_HEADER_IN_CSV_FILE =
      "testUserBulkUploadFailureWithoutColumnHeaderInCsvFile";
  public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_INVALID_COLUMN =
      "testUserBulkUploadFailureWithInvalidColumn";
  public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_EXCEEDING_FILE_SIZE =
      "testUserBulkUploadFailureWithExceedingFileSize";

  public static final String TEST_NAME_USER_BULK_UPLOAD_SUCCESS_WITH_ORG_ID =
      "testUserBulkUploadSuccessWithOrgId";
  public static final String TEST_NAME_USER_BULK_UPLOAD_SUCCESS_WITH_PROVIDER_AND_EXTERNAL_ID =
      "testUserBulkUploadSuccessWithProviderAndExternalId";

  private static String ROOT_ORG_ID = null;
  private static String SUB_ORG_ID = null;
  public static final String TEMPLATE_DIR = "templates/bulkupload/user";

  private String getUserBulkUploadUrl() {
    return getLmsApiUriPath("/api/user/v1/upload", "/v1/user/upload");
  }

  @DataProvider(name = "userBulkUploadFailureDataProvider")
  public Object[][] userBulkUploadFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_ACCESS_TOKEN,
        HttpStatus.UNAUTHORIZED,
        false,
        false,
        false
      },
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_ORG_DETAIL,
        HttpStatus.BAD_REQUEST,
        true,
        false,
        false
      },
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_INVALID_ORG_ID,
        HttpStatus.BAD_REQUEST,
        true,
        false,
        false
      },
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_CSV_FILE,
        HttpStatus.BAD_REQUEST,
        true,
        false,
        false
      },
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_EMPTY_CSV_FILE,
        HttpStatus.BAD_REQUEST,
        true,
        true,
        false
      },
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_COLUMN_HEADER_IN_CSV_FILE,
        HttpStatus.BAD_REQUEST,
        true,
        true,
        false
      },
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_INVALID_COLUMN,
        HttpStatus.BAD_REQUEST,
        true,
        true,
        false
      },
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_EXCEEDING_FILE_SIZE,
        HttpStatus.BAD_REQUEST,
        true,
        true,
        false
      },
    };
  }

  @Test(dataProvider = "userBulkUploadFailureDataProvider")
  @CitrusParameters({
    "testName",
    "httpStatusCode",
    "isAuthRequired",
    "canCreateOrg",
    "canCreateSubOrg"
  })
  @CitrusTest
  public void testUserBulkUploadFailure(
      String testName,
      HttpStatus httpStatusCode,
      boolean isAuthRequired,
      boolean canCreateOrg,
      boolean canCreateSubOrg) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
    beforeTest(canCreateOrg, canCreateSubOrg);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUserBulkUploadUrl(),
        REQUEST_FORM_DATA,
        null,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "userBulkUploadSuccessDataProvider")
  public Object[][] userBulkUploadSuccessDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_SUCCESS_WITH_ORG_ID, HttpStatus.OK, true, true, false
      },
      new Object[] {
        TEST_NAME_USER_BULK_UPLOAD_SUCCESS_WITH_PROVIDER_AND_EXTERNAL_ID,
        HttpStatus.OK,
        true,
        true,
        true
      },
    };
  }

  @Test(dataProvider = "userBulkUploadSuccessDataProvider")
  @CitrusParameters({
    "testName",
    "httpStatusCode",
    "isAuthRequired",
    "canCreateOrg",
    "canCreateSubOrg"
  })
  @CitrusTest
  public void testUserBulkUploadSuccess(
      String testName,
      HttpStatus httpStatusCode,
      boolean isAuthRequired,
      boolean canCreateOrg,
      boolean canCreateSubOrg) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
    beforeTest(canCreateOrg, canCreateSubOrg);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUserBulkUploadUrl(),
        REQUEST_FORM_DATA,
        null,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest(boolean canCreateOrg, boolean canCreateSubOrg) {
    if (canCreateOrg) {
      if (ROOT_ORG_ID == null) {
        variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
        ROOT_ORG_ID = OrgUtil.getRootOrgId(this, testContext);
      } else {
        testContext.setVariable("organisationId", ROOT_ORG_ID);
      }
    }
    if (canCreateSubOrg) {
      if (SUB_ORG_ID == null) {
        String externalId = OrgUtil.getRootOrgChannel();
        String provider = OrgUtil.getRootOrgChannel();
        variable("externalId", externalId);
        variable("provider", provider);

        SUB_ORG_ID = OrgUtil.createSubOrgId(this, testContext);

        testContext.setVariable("organisationId", ROOT_ORG_ID);
        testContext.setVariable("externalId", externalId);
        testContext.setVariable("provider", provider);
      }
    }
  }
}
