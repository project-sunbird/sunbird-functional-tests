package org.sunbird.integration.test.systemsetting;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateSystemSettingTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_CREATE_SYSTEM_SETTING_FAILURE_WITHOUT_TOKEN =
      "testCreateSystemSettingFailureWithoutAuthToken";

  public static final String TEST_NAME_CREATE_SYSTEM_SETTING_FAILURE_WITHOUT_ID =
      "testCreateSystemSettingFailureWithoutId";
  public static final String TEST_NAME_CREATE_SYSTEM_SETTING_FAILURE_WITHOUT_FIELD =
      "testCreateSystemSettingFailureWithoutField";
  public static final String TEST_NAME_CREATE_SYSTEM_SETTING_FAILURE_WITHOUT_VALUE =
      "testCreateSystemSettingFailureWithoutValue";
  public static final String TEST_NAME_CREATE_SYSTEM_SETTING_SUCCESS =
      "testCreateSystemSettingSuccess";
  public static String ID = "uniqueId";
  public static String FIELD = "uniqueField";

  public static final String TEMPLATE_DIR = "templates/systemsetting/create";

  private String getCreateSystemSettingUrl() {
    return getLmsApiUriPath("/api/data/v1/system/settings/set", "/v1/system/settings/set");
  }

  @DataProvider(name = "createSystemSettingDataProvider")
  public Object[][] createSystemSettingDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_CREATE_SYSTEM_SETTING_FAILURE_WITHOUT_TOKEN, false, HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_NAME_CREATE_SYSTEM_SETTING_FAILURE_WITHOUT_ID, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_CREATE_SYSTEM_SETTING_FAILURE_WITHOUT_FIELD, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_CREATE_SYSTEM_SETTING_FAILURE_WITHOUT_VALUE, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {TEST_NAME_CREATE_SYSTEM_SETTING_SUCCESS, true, HttpStatus.OK}
    };
  }

  @Test(dataProvider = "createSystemSettingDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testCreateSystemSetting(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest(isAuthRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateSystemSettingUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest(boolean isAuthRequired) {
    getAuthToken(this, isAuthRequired);
    variable("id", ID);
    variable("field", FIELD);
  }
}
