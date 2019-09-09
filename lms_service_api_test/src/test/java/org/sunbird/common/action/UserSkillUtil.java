package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;

public class UserSkillUtil {

  private static final String UPDATE_SKILL_TEMPLATE_DIR = "templates/user/skill/update";
  private static final String UPDATE_USER_SKILL_SUCCESS = "testUpdateUserSkillSuccess";

  private static String getUpdateUserSkillUrl(BaseCitrusTestRunner runner) {

    return runner.getLmsApiUriPath("/api/user/v1/skill/update", "/v1/user/skill/update");
  }

  public static void createUserSkill(
      BaseCitrusTestRunner runner, TestContext testContext, String templateDir, String testName) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getUpdateUserSkillUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void createSkill(BaseCitrusTestRunner runner, TestContext testContext) {
    if (StringUtils.isEmpty(testContext.getVariables().get("endorsedUserId"))) {
      UserUtil.getUserId(runner, testContext, "endorsedUserId");
      runner.variable("endorsedUserId", testContext.getVariable("endorsedUserId"));
      runner.variable("userId", testContext.getVariable("endorsedUserId"));
      String userName = testContext.getVariable("userName");
      runner.getAuthToken(
          runner, userName, Constant.PASSWORD, testContext.getVariable("endorsedUserId"), true);
      createUserSkill(runner, testContext, UPDATE_SKILL_TEMPLATE_DIR, UPDATE_USER_SKILL_SUCCESS);
    }
  }
}
