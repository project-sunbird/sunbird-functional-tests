package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;

public class BadgeAssertionUtil {

  public static String getCreateBadgeAssertionUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath(
        "/api/badging/v1/issuer/badge/assertion/create", "/v1/issuer/badge/assertion/create");
  }

  public static void createBadgeAssertion(
      BaseCitrusTestRunner runner,
      TestContext testContext,
      TestGlobalProperty config,
      String templateDir,
      String testName,
      HttpStatus responseCode) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getCreateBadgeAssertionUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
    runner.http(
        builder ->
            TestActionUtil.getExtractFromResponseTestAction(
                testContext,
                builder,
                Constant.LMS_ENDPOINT,
                responseCode,
                "$.result.assertionId",
                Constant.EXTRACT_VAR_ASSERTION_ID));
    runner.variable("assertionId", testContext.getVariable(Constant.EXTRACT_VAR_ASSERTION_ID));
  }
}
