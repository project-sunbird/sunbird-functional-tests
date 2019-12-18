package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.sunbird.integration.test.user.EndpointConfig;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;

public class UserUtil {

	private static TestGlobalProperty testGlobalProperty = new EndpointConfig().initGlobalValues();
	
  public static String getCreateUserUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/user/v1/create", "/v1/user/create");
  }

  public static final String TEMPLATE_DIR_USER_CREATE = "templates/user/create";
  public static final String TEMPLATE_DIR_USER_CREATE_TEST_CASE = "testCreateUserSuccess";
  public static final String TEMPLATE_DIR_USER_CREATE_TEST_CASE_WITH_EMAIL_AND_PHONE =
      "testCreateUserSuccessWithEmailAndPhone";

  private static String getBlockUserUrl(BaseCitrusTestRunner runner) {

    return runner.getLmsApiUriPath("/api/user/v1/block", "/v1/user/block");
  }

  private static String getUnblockUserUrl(BaseCitrusTestRunner runner) {

    return runner.getLmsApiUriPath("/api/user/v1/unblock", "/v1/user/unblock");
  }

  private static String getUserProfileVisibilityUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath(
        "/api/user/v1/profile/visibility", "/v1/user/profile/visibility");
  }

  public static void createUser(
      BaseCitrusTestRunner runner,
      TestContext testContext,
      String templateDir,
      String testName,
      String variable) {

    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getCreateUserUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(false)));

    runner.http(
        builder ->
            TestActionUtil.getExtractFromResponseTestAction(
                testContext,
                builder,
                Constant.LMS_ENDPOINT,
                HttpStatus.OK,
                "$.result.userId",
                variable));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void blockUser(BaseCitrusTestRunner runner, String templateDir, String testName) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getBlockUserUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void unblockUser(BaseCitrusTestRunner runner, String templateDir, String testName) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getUnblockUserUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void getUserId(BaseCitrusTestRunner runner, TestContext testContext) {
    getUserId(runner, testContext, Constant.USER_ID);
  }

  public static void getUserId(
      BaseCitrusTestRunner runner, TestContext testContext, String variable) {
    if (StringUtils.isBlank((String) testContext.getVariables().get(variable))) {
      getUser(runner, testContext, variable);
    } else {
      runner.variable(variable, (String) testContext.getVariables().get(variable));
    }
  }

  public static void getUser(
      BaseCitrusTestRunner runner, TestContext testContext, String extractVariableName) {
    String userName = Constant.USER_NAME_PREFIX + UUID.randomUUID().toString();
    testContext.setVariable("userName", userName);
    runner.variable("username", userName);
    runner.variable("channel", testGlobalProperty.getChannel());
    UserUtil.createUser(
        runner,
        testContext,
        TEMPLATE_DIR_USER_CREATE,
        TEMPLATE_DIR_USER_CREATE_TEST_CASE,
        extractVariableName);
  }

  public static void getUserWithEmailAndPhone(
      BaseCitrusTestRunner runner, TestContext testContext, String extractVariableName) {
    String userName = Constant.USER_NAME_PREFIX + UUID.randomUUID().toString();
    testContext.setVariable("userName", userName);
    runner.variable("username", userName);
    runner.variable("channel", testGlobalProperty.getChannel());
    testContext.setVariable("channel", testGlobalProperty.getChannel());
    String email = Constant.USER_NAME_PREFIX + UUID.randomUUID().toString() + "@gmail.com";
    runner.variable("email", email);
    testContext.setVariable("email", email);
    Random random = new Random();
    String phoneNumber = "78" + (random.nextInt((99999999 - 10000000) + 1) + 10000000);
    runner.variable("phone", phoneNumber);
    testContext.setVariable("phone", phoneNumber);
    UserUtil.createUser(
        runner,
        testContext,
        TEMPLATE_DIR_USER_CREATE,
        TEMPLATE_DIR_USER_CREATE_TEST_CASE_WITH_EMAIL_AND_PHONE,
        extractVariableName);
  }

  public static void setProfileVisibilityPrivate(
      BaseCitrusTestRunner runner, String templateDir, String testName) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getUserProfileVisibilityUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
  }

  public static void createUserAndGetToken(BaseCitrusTestRunner runner, TestContext testContext) {
    getUserId(runner, testContext);
    runner.variable("userId", testContext.getVariable("userId"));
    String userName = testContext.getVariable("userName");
    runner.getAuthToken(
        runner, userName, Constant.PASSWORD, testContext.getVariable("userId"), true);
  }
}
