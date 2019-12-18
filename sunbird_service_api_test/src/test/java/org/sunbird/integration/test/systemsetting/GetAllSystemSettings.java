package org.sunbird.integration.test.systemsetting;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.SystemSettingUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetAllSystemSettings extends BaseCitrusTestRunner {

  public static final String TEST_NAME_GET_ALL_SYSTEM_SETTINGS_SUCCESS =
      "testGetAllSystemSettingsSuccess";
  public static final String TEMPLATE_DIR = "templates/systemsetting/list";

  private String getGetAllSystemSettingsUrl() {
    return getLmsApiUriPath("/api/data/v1/system/settings/list", "/v1/system/settings/list");
  }

  @DataProvider(name = "getAllSystemSettingsDataProvider")
  public Object[][] getAllSystemSettingsDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_GET_ALL_SYSTEM_SETTINGS_SUCCESS, true, HttpStatus.OK}
    };
  }

  @Test(dataProvider = "getAllSystemSettingsDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testGetAllSystemSettings(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getGetAllSystemSettingsUrl(),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest() {
    getAuthToken(this, true);
    SystemSettingUtil.getSystemSetting(this, testContext);
  }
}
