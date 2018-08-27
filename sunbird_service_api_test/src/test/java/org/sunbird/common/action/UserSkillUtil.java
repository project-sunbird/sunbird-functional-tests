package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;

/** Created by rajatgupta on 24/08/18. */
public class UserSkillUtil {

  private static final String BT_CREATE_SKILL_TEMPLATE_DIR = "templates/user/skill/endorsement/add";
  private static final String ADD_USER_SKILL_SUCCESS = "btCreateSkill";

  private static String getUpdateUserSkill(BaseCitrusTestRunner runner) {

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
                getUpdateUserSkill(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void createSkill(BaseCitrusTestRunner runner, TestContext testContext) {
    UserUtil.getUserId(runner, testContext, "endorsedUserId");
    runner.variable("endorsedUserId", testContext.getVariable("endorsedUserId"));
    String userName = UserUtil.getUserNameWithChannel(runner, testContext);
    runner.getAuthToken(
        runner, userName, Constant.PASSWORD, testContext.getVariable("endorsedUserId"), true);
    createUserSkill(runner, testContext, BT_CREATE_SKILL_TEMPLATE_DIR, ADD_USER_SKILL_SUCCESS);
  }
}
