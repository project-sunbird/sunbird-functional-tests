package org.sunbird.integration.test.systemsetting;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.SystemSettingUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetSystemSettingTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_GET_SYSTEM_SETTING_SUCCESS_WITHOUT_TOKEN =
      "testGetSystemSettingSuccessWithoutAuthToken";
  public static final String TEST_NAME_GET_SYSTEM_SETTING_FAILURE_WITH_INVALID_FIELD =
      "testGetSystemSettingFailureWithInvalidField";
  public static final String TEST_NAME_GET_SYSTEM_SETTING_SUCCESS = "testGetSystemSettingSuccess";

  public static final String TEMPLATE_DIR = "templates/systemsetting/get";

  private String getGetSystemSettingUrl(String field) {
    return getLmsApiUriPath("/api/data/v1/system/settings/get", "/v1/system/settings/get", field);
  }

  @DataProvider(name = "getSystemSettingDataProvider")
  public Object[][] getSystemSettingDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_GET_SYSTEM_SETTING_SUCCESS_WITHOUT_TOKEN,
        "uniqueField",
        false,
        HttpStatus.OK
      },
      new Object[] {
        TEST_NAME_GET_SYSTEM_SETTING_FAILURE_WITH_INVALID_FIELD,
        "invalid",
        true,
        HttpStatus.NOT_FOUND
      },
      new Object[] {TEST_NAME_GET_SYSTEM_SETTING_SUCCESS, "uniqueField", true, HttpStatus.OK}
    };
  }

  @Test(dataProvider = "getSystemSettingDataProvider")
  @CitrusParameters({"testName", "field", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testGetSystemSetting(
      String testName, String field, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getGetSystemSettingUrl(field),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest() {
    getAuthToken(this, true);
    SystemSettingUtil.getSystemSetting(this, testContext);
  }
}
