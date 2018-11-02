package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.TestActionUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UnblockUserTest extends BaseCitrusTestRunner {

  public static final String TEST_UNBLOCK_USER_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testUnblockUserFailureWithoutAccessToken";
  public static final String TEST_UNBLOCK_USER_FAILURE_WITH_INVALID_USERID =
      "testUnblockUserFailureWithInvalidUserId";
  public static final String TEST_UNBLOCK_USER_SUCCESS_WITH_VALID_USERID =
      "testUnblockUserSuccessWithValidUserId";

  public static final String TEST_UNBLOCK_UNBLOCKED_USER_FAILURE_WITH_VALID_USERID =
      "testUnblockUnblockedUserFailureWithValidUserId";

  public static final String TEST_UNBLOCK_USER_GET_SUCCESS_WITH_VALID_USERID =
      "testUnblockUserGetSuccessWithValidUserId";

  public static final String TEMPLATE_DIR_BLOCK = "templates/user/block";
  public static final String TEST_BLOCK_USER_SUCCESS_WITH_VALID_USERID =
      "testBlockUserSuccessWithValidUserId";
  private static final String GET_USER_BY_ID_SERVER_URI = "/api/user/v1/read";
  private static final String GET_USER_BY_ID_LOCAL_URI = "/v1/user/read";

  private String getUnblockUserUrl() {
    return getLmsApiUriPath("/api/user/v1/unblock", "/v1/user/unblock");
  }

  public static final String TEMPLATE_DIR = "templates/user/unblock";

  @DataProvider(name = "unblockUserFailureDataProvider")
  public Object[][] unblockUserFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_UNBLOCK_USER_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED},
      new Object[] {TEST_UNBLOCK_USER_FAILURE_WITH_INVALID_USERID, true, HttpStatus.INTERNAL_SERVER_ERROR},
    };
  }

  @DataProvider(name = "unblockUserSuccessDataProvider")
  public Object[][] unblockUserSuccessDataProvider() {

    return new Object[][] {
      new Object[] {TEST_UNBLOCK_USER_SUCCESS_WITH_VALID_USERID, true, HttpStatus.INTERNAL_SERVER_ERROR},
    };
  }

  @Test(dataProvider = "unblockUserFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUnblockUserFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getAuthToken(this, isAuthRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUnblockUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "unblockUserSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUnblockUserSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest(false);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUnblockUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test()
  @CitrusTest
  public void testUnblockUnblockUserByUserIdFailure() {
    getTestCase().setName(TEST_UNBLOCK_UNBLOCKED_USER_FAILURE_WITH_VALID_USERID);
    beforeTest(true);
    performPostTest(
        this,
        TEMPLATE_DIR,
        TEST_UNBLOCK_UNBLOCKED_USER_FAILURE_WITH_VALID_USERID,
        getUnblockUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.INTERNAL_SERVER_ERROR,
        RESPONSE_JSON);
  }

  @Test()
  @CitrusTest
  public void testGetUnblockUserByUserIdSuccess() {
    getTestCase().setName(TEST_UNBLOCK_USER_GET_SUCCESS_WITH_VALID_USERID);

    beforeTest(true);
    performGetTest(
        this,
        TEMPLATE_DIR,
        TEST_UNBLOCK_USER_GET_SUCCESS_WITH_VALID_USERID,
        getLmsApiUriPath(
            GET_USER_BY_ID_SERVER_URI,
            GET_USER_BY_ID_LOCAL_URI,
            TestActionUtil.getVariable(testContext, "userId")),
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void beforeTest(boolean isUnblock) {
    getAuthToken(this, true);
    UserUtil.getUserId(this, testContext);
    variable("userId", testContext.getVariable("userId"));
    UserUtil.blockUser(this, TEMPLATE_DIR_BLOCK, TEST_BLOCK_USER_SUCCESS_WITH_VALID_USERID);
    if (isUnblock)
      UserUtil.unblockUser(this, TEMPLATE_DIR, TEST_UNBLOCK_USER_SUCCESS_WITH_VALID_USERID);
  }
}
