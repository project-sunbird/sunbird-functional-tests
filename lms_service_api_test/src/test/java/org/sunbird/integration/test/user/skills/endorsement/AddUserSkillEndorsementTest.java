package org.sunbird.integration.test.user.skills.endorsement;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserSkillUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddUserSkillEndorsementTest extends BaseCitrusTestRunner {
  private static final String ADD_USER_SKILL_ENDORSEMENT_FAILURE_WITH_INVALID_USER_ID =
      "testAddUserSkillEndorsementFailureWithInvalidUserId";
  private static final String ADD_USER_SKILL_ENDORSEMENT_FAILURE_WITHOUT_SKILLS_NAME =
      "testAddUserSkillEndorsementFailureWithoutSkillName";
  private static final String ADD_USER_SKILL_ENDORSEMENT_FAILURE_WITHOUT_ENDORSED_USER_ID =
      "testAddUserSkillEndorsementFailureWithoutEndorsedUserId";
  private static final String ADD_USER_SKILL_ENDORSEMENT_FAILURE_SELF_ENDORSEMENT =
      "testAddUserSkillEndorsementFailureSelfEndorsement";
  private static final String ADD_USER_SKILL_ENDORSEMENT_SUCCESS =
      "testAddUserSkillEndorsementSuccess";
  private static final String TEMPLATE_DIR = "templates/user/skill/endorsement/add";

  private String getUpdateUserSkillUrl() {
    return getLmsApiUriPath("/api/user/v1/skill/endorse/add", "/v1/user/skill/endorse/add");
  }

  @DataProvider(name = "testaddUserSkillEndorsementFailureDataProvider")
  public Object[][] testaddUserSkillEndorsementFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        ADD_USER_SKILL_ENDORSEMENT_FAILURE_WITH_INVALID_USER_ID, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        ADD_USER_SKILL_ENDORSEMENT_FAILURE_WITHOUT_SKILLS_NAME, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        ADD_USER_SKILL_ENDORSEMENT_FAILURE_WITHOUT_ENDORSED_USER_ID, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        ADD_USER_SKILL_ENDORSEMENT_FAILURE_SELF_ENDORSEMENT, true, HttpStatus.BAD_REQUEST
      },
    };
  }

  @Test(dataProvider = "testaddUserSkillEndorsementFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testAddUserSkillEndorsementFailure(
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

  @DataProvider(name = "testAddUserSkillEndorsementSuccessDataProvider")
  public Object[][] testAddUserSkillEndorsementSuccessDataProvider() {

    return new Object[][] {new Object[] {ADD_USER_SKILL_ENDORSEMENT_SUCCESS, true, HttpStatus.OK}};
  }

  @Test(dataProvider = "testAddUserSkillEndorsementSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testAddUserSkillEndorsementSuccess(
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
    UserSkillUtil.createSkill(this, testContext);
    UserUtil.getUserId(this, testContext);
    variable("endorsedUserId", testContext.getVariable("endorsedUserId"));
    variable("userId", testContext.getVariable("userId"));
    getAuthToken(
        this,
        testContext.getVariable("userName"),
        Constant.PASSWORD,
        testContext.getVariable("userId"),
        true);
  }
}
