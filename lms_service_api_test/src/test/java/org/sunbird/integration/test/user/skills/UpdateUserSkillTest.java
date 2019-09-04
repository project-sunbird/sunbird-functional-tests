package org.sunbird.integration.test.user.skills;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateUserSkillTest extends BaseCitrusTestRunner {

  private static final String UPDATE_USER_SKILL_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testUpdateUserSkillFailureWithoutAccessToken";
  private static final String UPDATE_USER_SKILL_FAILURE_WITH_INVALID_USER_ID =
      "testUpdateUserSkillFailureWithInvalidUserId";
  private static final String UPDATE_USER_SKILL_FAILURE_WITHOUT_SKILLS_LIST =
      "testUpdateUserSkillFailureWithoutSkillsList";
  private static final String UPDATE_USER_SKILL_SUCCESS = "testUpdateUserSkillSuccess";
  private static final String UPDATE_USER_SKILL_SUCCESS_WITH_EMPTY_SKILLS_LIST =
      "testUpdateUserSkillSuccessWithEmptySkillsList";
  private static final String TEMPLATE_DIR = "templates/user/skill/update";

  private String getUpdateUserSkillUrl() {
    return getLmsApiUriPath("/api/user/v1/skill/update", "/v1/user/skill/update");
  }

  @DataProvider(name = "updateUserSkillFailureDataProvider")
  public Object[][] updateUserSkillFailureDataProvider() {

    return new Object[][] {
      new Object[] {UPDATE_USER_SKILL_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED},
      new Object[] {UPDATE_USER_SKILL_FAILURE_WITH_INVALID_USER_ID, true, HttpStatus.BAD_REQUEST},
      new Object[] {UPDATE_USER_SKILL_FAILURE_WITHOUT_SKILLS_LIST, true, HttpStatus.BAD_REQUEST},
    };
  }

  @Test(dataProvider = "updateUserSkillFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateUserSkillFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateUserSkillUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "updateUserSkillSuccessDataProvider")
  public Object[][] updateUserSkillSuccessDataProvider() {

    return new Object[][] {
      new Object[] {UPDATE_USER_SKILL_SUCCESS, true, HttpStatus.OK},
      new Object[] {UPDATE_USER_SKILL_SUCCESS_WITH_EMPTY_SKILLS_LIST, true, HttpStatus.OK},
    };
  }

  @Test(dataProvider = "updateUserSkillSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateUserSkillSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateUserSkillUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest() {
    UserUtil.getUserId(this, testContext);
    variable("userId", testContext.getVariable("userId"));
    String userName = testContext.getVariable("userName");
    getAuthToken(this, userName, Constant.PASSWORD, testContext.getVariable("userId"), true);
  }
}
