package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.sunbird.common.util.EndpointConfig.TestGlobalProperty;

public class IssuerUtil {

  public static String getCreateIssuerUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/badging/v1/issuer/create", "/v1/issuer/create");
  }

  public static String getDeleteIssuerUrl(BaseCitrusTestRunner runner, String pathParam) {
    return runner.getLmsApiUriPath("/api/badging/v1/issuer/delete", "/v1/issuer/delete", pathParam);
  }

  public static void createIssuer(
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
                getCreateIssuerUrl(runner),
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
                "$.result.issuerId",
                Constant.EXTRACT_VAR_ISSUER_ID));
  }

  public static void deleteIssuer(
      BaseCitrusTestRunner runner,
      TestContext testContext,
      TestGlobalProperty config,
      String issuerId) {
    runner.http(
        builder ->
            TestActionUtil.getDeleteRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                null,
                null,
                getDeleteIssuerUrl(runner, issuerId),
                null,
                null,
                null));
  }
}
