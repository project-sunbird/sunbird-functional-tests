package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;

public class SystemSettingUtil {
  public static String ID = "uniqueId";
  public static String FIELD = "uniqueField";
  public static final String TEMPLATE_DIR = "templates/systemsetting/create";

  private static String getCreateSystemSettingUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/data/v1/system/settings/set", "/v1/system/settings/set");
  }

  public static void createSystemSetting(
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
                getCreateSystemSettingUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));

    runner.http(
        builder ->
            TestActionUtil.getExtractFromResponseTestAction(
                testContext,
                builder,
                Constant.LMS_ENDPOINT,
                HttpStatus.OK,
                "$.result.id",
                variable));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static String getSystemSetting(BaseCitrusTestRunner runner, TestContext testContext) {
    runner.variable("id", ID);
    runner.variable("field", FIELD);
    if (StringUtils.isEmpty(testContext.getVariables().get("systemSetting"))) {
      createSystemSetting(
          runner, testContext, TEMPLATE_DIR, "testCreateSystemSettingSuccess", "systemSetting");
    }
    return testContext.getVariable("systemSetting");
  }
}
