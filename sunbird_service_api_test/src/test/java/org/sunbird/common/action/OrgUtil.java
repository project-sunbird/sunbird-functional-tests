package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import java.time.Instant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.sunbird.integration.test.user.EndpointConfig;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;

public class OrgUtil {
	private static TestGlobalProperty testGlobalProperty = new EndpointConfig().initGlobalValues();

  private static String rootOrgId = null;
  private static String subOrgId = null;

  private static final String rootOrgChannel = "FT_Org_Channel_" + Instant.now().getEpochSecond();
  private static final String rootOrgExternalId =
      "FT_Org_External_" + Instant.now().getEpochSecond();
  private static final String rootOrgProviderId =
      "FT_Org_Provider_" + Instant.now().getEpochSecond();

  private static final String sunbirdDefaultOrg = testGlobalProperty.getSunbirdDefaultChannel();

  public static String getDefaultSunbirdRootOrg() {
    return sunbirdDefaultOrg;
  }

  public static String getRootOrgChannel() {
    return rootOrgChannel;
  }

  public static String getRootOrgExternalId() {
    return rootOrgExternalId;
  }

  public static String getRootOrgProviderId() {
    return rootOrgProviderId;
  }

  public static String getCreateOrgUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/org/v1/create", "/v1/org/create");
  }

  public static String getAddUserToOrgUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/org/v1/member/add", "/v1/org/member/add");
  }

  public static String getSearchOrgUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/org/v1/search", "/v1/org/search");
  }

  public static void createOrg(
      BaseCitrusTestRunner runner,
      TestContext testContext,
      String templateDir,
      String testName,
      String extractVariableName,
      HttpStatus responseCode) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getCreateOrgUrl(runner),
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
                "$.result.organisationId",
                extractVariableName));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void addUserToOrg(
      BaseCitrusTestRunner runner, String templateDir, String testName) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getAddUserToOrgUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void searchOrg(
      BaseCitrusTestRunner runner,
      TestContext testContext,
      String templateDir,
      String testName,
      String extractVariableName,
      HttpStatus responseCode) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getSearchOrgUrl(runner),
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
                "$.result.response.content[*].rootOrgId",
                extractVariableName));
  }

  public static String getRootOrgId(BaseCitrusTestRunner runner, TestContext testContext) {

    if (StringUtils.isBlank(rootOrgId)) {
      createOrg(
          runner,
          testContext,
          "templates/organisation/create",
          "testCreateRootOrgSuccess",
          Constant.EXTRACT_VAR_ROOT_ORG_ID,
          HttpStatus.OK);
      rootOrgId = testContext.getVariable(Constant.EXTRACT_VAR_ROOT_ORG_ID);
    } else {
      testContext.setVariable(Constant.EXTRACT_VAR_ROOT_ORG_ID, rootOrgId);
    }
    runner.variable(Constant.EXTRACT_VAR_ROOT_ORG_ID, rootOrgId);
    return rootOrgId;
  }

  public static String createSubOrgId(BaseCitrusTestRunner runner, TestContext testContext) {

    createOrg(
        runner,
        testContext,
        "templates/organisation/create",
        "testCreateSubOrgSuccess",
        Constant.EXTRACT_VAR_SUB_ORG_ID,
        HttpStatus.OK);
    subOrgId = testContext.getVariable(Constant.EXTRACT_VAR_SUB_ORG_ID);
    return subOrgId;
  }

  public static String getSearchOrgId(
      BaseCitrusTestRunner runner, TestContext testContext, String channel) {

    runner.variable("channel", System.getenv("sunbird_default_channel"));
    String searchedOrgId = null;
    searchOrg(
        runner,
        testContext,
        "templates/organisation/search",
        "testSearchRootOrgIdSuccessFilterByChannel",
        Constant.EXTRACT_VAR_SEARCH_ROOT_ORG_ID,
        HttpStatus.OK);
    searchedOrgId = testContext.getVariable(Constant.EXTRACT_VAR_SEARCH_ROOT_ORG_ID);
    return searchedOrgId;
  }
}
