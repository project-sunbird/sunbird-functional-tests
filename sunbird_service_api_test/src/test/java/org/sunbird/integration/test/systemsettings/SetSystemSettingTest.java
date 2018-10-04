package org.sunbird.integration.test.systemsettings;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SetSystemSettingTest extends BaseCitrusTestRunner {

  // failure
  public static final String TEST_NAME_SET_SYSTEM_SETTING_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testSetSystemSettingFailureWithoutAccessToken";
  public static final String TEST_NAME_SET_SYSTEM_SETTING_FAILURE_WITHOUT_ID =
      "testSetSystemSettingFailureWithoutId";
  public static final String TEST_NAME_SET_SYSTEM_SETTING_FAILURE_WITHOUT_FIELD =
      "testSetSystemSettingFailureWithoutField";
  public static final String TEST_NAME_SET_SYSTEM_SETTING_FAILURE_WITHOUT_VALUE =
      "testSetSystemSettingFailureWithoutValue";

  // success
  public static final String TEST_NAME_SET_SYSTEM_SETTING_SUCCESS_WITH_VALID_VALUE =
      "testSetSyatemSettingSuccessWithValidValue";

  public static final String TEMPLATE_DIR = "templates/user/skill/add";

  private String getAddUserSkillUrl() {
    return getLmsApiUriPath("/api/user/v1/skill/add", "/v1/user/skill/add");
  }

  @DataProvider(name = "addUserSkillFailureDataProvider")
  public Object[][] addUserSkillFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_SET_SYSTEM_SETTING_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED
      },
      new Object[] {TEST_NAME_SET_SYSTEM_SETTING_FAILURE_WITHOUT_ID, true, HttpStatus.BAD_REQUEST},
      new Object[] {
        TEST_NAME_SET_SYSTEM_SETTING_FAILURE_WITHOUT_FIELD, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_SET_SYSTEM_SETTING_FAILURE_WITHOUT_VALUE, true, HttpStatus.BAD_REQUEST
      }
    };
  }

  @Test(dataProvider = "addUserSkillFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testAddUserSkillFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getAddUserSkillUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  // ===

  @DataProvider(name = "addUserSkillSuccessDataProvider")
  public Object[][] addUserSkillSuccessDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_SET_SYSTEM_SETTING_SUCCESS_WITH_VALID_VALUE, true, HttpStatus.OK}
    };
  }

  @Test(dataProvider = "addUserSkillSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testAddUserSkillSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getAddUserSkillUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
