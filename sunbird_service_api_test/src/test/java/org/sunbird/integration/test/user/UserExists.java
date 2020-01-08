package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserExists extends BaseCitrusTestRunner {
  private static final String TEST_USER_EXISITS_FAILURE_WITH_INVALID_EMAIL =
      "testUserExistsWithInvalidEmail";
  private static final String TEST_USER_EXISTS_FAILURE_WITH_INVALID_PHONE =
      "testUserExistsWithInvalidPhone";
  private static final String TEST_USER_EXISTS_SUCCESS_WITHOUT_TOKEN =
      "testUserExistsSuccessWithoutToken";
  private static final String TEST_USER_EXISTS_SUCCESS_WITH_PHONE =
      "testUserExistsSuccessWithPhone";
  private static final String TEST_USER_EXISTS_SUCCESS_WITH_EMAIL =
      "testUserExistsSuccessWithEmail";
  public static final String TEMPLATE_DIR = "templates/user/exists";

  private String getUserByKeyUrl(String idType, String id) {
    return getLmsApiUriPath("/api/user/v1/exists", "/v1/user/exists", idType, id);
  }

  @DataProvider(name = "getUserByKeyFailureDataProvider")
  public Object[][] getUserByKeyFailureDataProvider() {

    return new Object[][] {
      new Object[] {
    		  TEST_USER_EXISITS_FAILURE_WITH_INVALID_EMAIL,
        true,
        HttpStatus.BAD_REQUEST,
        "email",
        "invalidEmail"
      },
      new Object[] {
        TEST_USER_EXISTS_FAILURE_WITH_INVALID_PHONE,
        true,
        HttpStatus.BAD_REQUEST,
        "phone",
        "invalidPhone"
      }
    };
  }

  @DataProvider(name = "getUserByKeySuccessDataProvider")
  public Object[][] getUserByKeySuccessDataProvider() {

    return new Object[][] {
      new Object[] {TEST_USER_EXISTS_SUCCESS_WITHOUT_TOKEN, false, HttpStatus.OK, "email"},
      new Object[] {TEST_USER_EXISTS_SUCCESS_WITH_PHONE, true, HttpStatus.OK, "phone"},
      new Object[] {TEST_USER_EXISTS_SUCCESS_WITH_EMAIL, true, HttpStatus.OK, "email"},
    };
  }

  @Test(dataProvider = "getUserByKeyFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode", "idType", "id"})
  @CitrusTest
  public void testGetUserByKeyFailure(
      String testName,
      boolean isAuthRequired,
      HttpStatus httpStatusCode,
      String idType,
      String id) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    beforeTest();
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUserByKeyUrl(idType, id),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "getUserByKeySuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode", "idType"})
  @CitrusTest
  public void testGetUserByKeySuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode, String idType) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    beforeTest();
    String id = testContext.getVariable(idType);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUserByKeyUrl(idType, id),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  void beforeTest() {
    if (StringUtils.isBlank((String) testContext.getVariables().get(Constant.USER_ID))) {
      UserUtil.getUserWithEmailAndPhone(this, testContext, Constant.USER_ID);
    } else {
      variable("phone", testContext.getVariable("phone"));
      variable("email", testContext.getVariable("email"));

      testContext.setVariable(
          "loginId",
          testContext.getVariable("userName") + "@" + testContext.getVariable("channel"));
    }
  }
}
