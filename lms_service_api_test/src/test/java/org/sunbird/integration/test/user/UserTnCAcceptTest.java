package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.TestActionUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** Created by rajatgupta on 20/12/18. */
public class UserTnCAcceptTest extends BaseCitrusTestRunner {
  public static final String TEST_USER_TNC_ACCEPT_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testAcceptUserTNCFailureWithoutAccessToken";
  public static final String TEST_USER_TNC_ACCEPT_FAILURE_WITH_INVALID_VERSION =
      "testAcceptUserTNCFailureWithInvalidVersion";
  public static final String TEST_USER_TNC_ACCEPT_SUCCESS_WITH_VALID_VERSION =
      "testAcceptUserTNCSuccessWithValidVersion";
  public static final String TEST_USER_TNC_ACCEPT_SUCCESS_ALREADY_ACCEPTED =
      "testAcceptUserTNCFailureAlreadyAccepted";
  public static final String TEMPLATE_DIR = "templates/user/tnc";

  private String getUserTnCAcceptUrl() {
    return getLmsApiUriPath("/api/user/v1/tnc/accept", "/v1/user/tnc/accept");
  }

  @DataProvider(name = "updateAcceptUserTNCFailureDataProvider")
  public Object[][] updateAcceptUserTNCFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_USER_TNC_ACCEPT_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED
      },
      new Object[] {TEST_USER_TNC_ACCEPT_FAILURE_WITH_INVALID_VERSION, true, HttpStatus.BAD_REQUEST}
    };
  }

  @DataProvider(name = "updateAcceptUserTNCSuccessDataProvider")
  public Object[][] updateAcceptUserTNCSuccessDataProvider() {

    return new Object[][] {
      new Object[] {TEST_USER_TNC_ACCEPT_SUCCESS_WITH_VALID_VERSION, true, HttpStatus.OK},
      new Object[] {TEST_USER_TNC_ACCEPT_SUCCESS_ALREADY_ACCEPTED, true, HttpStatus.OK}
    };
  }

  @Test(dataProvider = "updateAcceptUserTNCFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testAcceptUserTNCFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest(isAuthRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUserTnCAcceptUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "updateAcceptUserTNCSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testAcceptUserTNCSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest(isAuthRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUserTnCAcceptUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest(boolean isAuthRequired) {
    UserUtil.getUserId(this, testContext);
    variable("userId", TestActionUtil.getVariable(testContext, "userId"));
    getAuthToken(
        this,
        testContext.getVariable("userName"),
        Constant.PASSWORD,
        testContext.getVariable("userId"),
        isAuthRequired);
  }
}
