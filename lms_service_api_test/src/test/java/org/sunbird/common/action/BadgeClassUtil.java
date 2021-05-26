package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;

public class BadgeClassUtil {

  public static String getBadgeClassIssuerUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath(
        "/api/badging/v1/issuer/badge/create", "/v1/issuer/badge/create");
  }

  public static String deleteBadgeClassUrl(BaseCitrusTestRunner runner, String pathParam) {
    return runner.getLmsApiUriPath(
        "/api/badging/v1/issuer/badge/delete", "/v1/issuer/badge/delete", pathParam);
  }

  public static void createBadgeClass(
      BaseCitrusTestRunner runner,
      TestContext testContext,
      TestGlobalProperty config,
      String templateDir,
      String testName,
      HttpStatus responseCode) {
    runner.http(
        builder ->
            TestActionUtil.getMultipartRequestTestAction(
                testContext,
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getBadgeClassIssuerUrl(runner),
                Constant.REQUEST_FORM_DATA,
                null,
                runner.getClass().getClassLoader(),
                config));
    runner.http(
        builder ->
            TestActionUtil.getExtractFromResponseTestAction(
                testContext,
                builder,
                Constant.LMS_ENDPOINT,
                responseCode,
                "$.result.badgeId",
                Constant.EXTRACT_VAR_BADGE_ID));
    runner.variable("badgeId", testContext.getVariable(Constant.EXTRACT_VAR_BADGE_ID));
  }

  public static void deleteBadgeClass(
      BaseCitrusTestRunner runner,
      TestContext testContext,
      TestGlobalProperty config,
      String badgeId) {
    runner.http(
        builder ->
            TestActionUtil.getDeleteRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                null,
                null,
                deleteBadgeClassUrl(runner, badgeId),
                null,
                null,
                null));
  }
}
