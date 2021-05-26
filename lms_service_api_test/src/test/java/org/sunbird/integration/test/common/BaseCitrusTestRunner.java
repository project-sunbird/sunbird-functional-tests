package org.sunbird.integration.test.common;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.TestActionUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.common.util.PropertiesReader;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;

public class BaseCitrusTestRunner extends TestNGCitrusTestRunner {

  @Autowired protected TestGlobalProperty config;
  @Autowired protected TestContext testContext;
  public static final String REQUEST_FORM_DATA = "request.params";
  public static final String REQUEST_JSON = "request.json";
  public static final String RESPONSE_JSON = "response.json";

  public static final String LMS_ENDPOINT = "restTestClient";
  public static final String KEYCLOAK_ENDPOINT = "keycloakTestClient";

  public static Map<String, List<String>> deletedRecordsMap = new HashMap<String, List<String>>();
  public static Map<String, List<String>> toDeleteCassandraRecordsMap =
      new HashMap<String, List<String>>();
  public static Map<String, List<String>> toDeleteEsRecordsMap =
      new HashMap<String, List<String>>();

  public BaseCitrusTestRunner() {}

  public void performMultipartTest(
      TestNGCitrusTestRunner runner,
      String templateDir,
      String testName,
      String requestUrl,
      String requestFile,
      Map<String, Object> requestHeaders,
      Boolean isAuthRequired,
      HttpStatus responseCode,
      String responseJson) {
    getTestCase().setName(testName);
    runner.http(
        builder ->
            TestActionUtil.getMultipartRequestTestAction(
                testContext,
                builder,
                LMS_ENDPOINT,
                templateDir,
                testName,
                requestUrl,
                requestFile,
                TestActionUtil.getHeaders(isAuthRequired, requestHeaders),
                runner.getClass().getClassLoader(),
                config));
    runner.http(
        builder ->
            TestActionUtil.getResponseTestAction(
                builder, LMS_ENDPOINT, templateDir, testName, responseCode, responseJson));
  }

  public void performPostTest(
      TestNGCitrusTestRunner runner,
      String templateDir,
      String testName,
      String requestUrl,
      String requestJson,
      String contentType,
      boolean isAuthRequired,
      HttpStatus responseCode,
      String responseJson) {
    getTestCase().setName(testName);
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                LMS_ENDPOINT,
                templateDir,
                testName,
                requestUrl,
                requestJson,
                contentType,
                TestActionUtil.getHeaders(isAuthRequired)));

    runner.http(
        builder ->
            TestActionUtil.getResponseTestAction(
                builder, LMS_ENDPOINT, templateDir, testName, responseCode, responseJson));
  }

  public void performPatchTest(
      TestNGCitrusTestRunner runner,
      String templateDir,
      String testName,
      String requestUrl,
      String requestJson,
      String contentType,
      boolean isAuthRequired,
      HttpStatus responseCode,
      String responseJson) {
    getTestCase().setName(testName);
    runner.http(
        builder ->
            TestActionUtil.getPatchRequestTestAction(
                builder,
                LMS_ENDPOINT,
                templateDir,
                testName,
                requestUrl,
                requestJson,
                contentType,
                TestActionUtil.getHeaders(isAuthRequired)));
    runner.http(
        builder ->
            TestActionUtil.getResponseTestAction(
                builder, LMS_ENDPOINT, templateDir, testName, responseCode, responseJson));
  }

  public void performDeleteTest(
      TestNGCitrusTestRunner runner,
      String templateDir,
      String testName,
      String requestUrl,
      String requestJson,
      String contentType,
      boolean isAuthRequired,
      HttpStatus responseCode,
      String responseJson) {
    getTestCase().setName(testName);
    runner.http(
        builder ->
            TestActionUtil.getDeleteRequestTestAction(
                builder,
                LMS_ENDPOINT,
                templateDir,
                testName,
                requestUrl,
                requestJson,
                contentType,
                TestActionUtil.getHeaders(isAuthRequired)));
    runner.http(
        builder ->
            TestActionUtil.getResponseTestAction(
                builder, LMS_ENDPOINT, templateDir, testName, responseCode, responseJson));
  }

  public void getAuthToken(TestNGCitrusTestRunner runner, Boolean isAuthRequired) {

    if (isAuthRequired) {
      runner.http(builder -> TestActionUtil.getTokenRequestTestAction(builder, KEYCLOAK_ENDPOINT));
      runner.http(builder -> TestActionUtil.getTokenResponseTestAction(builder, KEYCLOAK_ENDPOINT));
    }
  }

  public void getAuthToken(
      TestNGCitrusTestRunner runner,
      String userName,
      String password,
      String userId,
      boolean isUserAuthRequired) {

    if (isUserAuthRequired) {
      getUserAuthToken(runner, config.getKeycloakAdminUser(), config.getKeycloakAdminPass());
      updateUserRequiredLoginActionTest(runner, userId);
      getUserAuthToken(runner, userName, password);
    }
  }

  private void getUserAuthToken(TestNGCitrusTestRunner runner, String userName, String password) {
    runner.http(
        builder ->
            TestActionUtil.getTokenRequestTestAction(
                builder, KEYCLOAK_ENDPOINT, userName, password));
    runner.http(builder -> TestActionUtil.getTokenResponseTestAction(builder, KEYCLOAK_ENDPOINT));
  }

  private void updateUserRequiredLoginActionTest(TestNGCitrusTestRunner runner, String userId) {
    String url = "/admin/realms/" + PropertiesReader.getInstance().getPropertyFromFile("sunbird_sso_realm") + "/users/" + userId;
    String payLoad = "{\"requiredActions\":[]}";
    HashMap<String, Object> headers = new HashMap<>();
    headers.put(Constant.AUTHORIZATION, Constant.BEARER + "${accessToken}");
    runner.http(
        builder ->
            TestActionUtil.getPutRequestTestAction(
                builder, KEYCLOAK_ENDPOINT, url, headers, payLoad));
  }

  public void performGetTest(
      TestNGCitrusTestRunner runner,
      String templateDir,
      String testName,
      String requestUrl,
      Boolean isAuthRequired,
      HttpStatus responseCode,
      String responseJson) {
    getTestCase().setName(testName);
    getAuthToken(runner, isAuthRequired);
    runner.http(
        builder ->
            TestActionUtil.performGetTest(
                builder,
                LMS_ENDPOINT,
                testName,
                requestUrl,
                TestActionUtil.getHeaders(isAuthRequired),
                config));
    runner.http(
        builder ->
            TestActionUtil.getResponseTestAction(
                builder, LMS_ENDPOINT, templateDir, testName, responseCode, responseJson));
  }

  public void performGetTest(
      TestNGCitrusTestRunner runner,
      String testName,
      String requestUrl,
      Boolean isAuthRequired,
      HttpStatus responseCode,
      String responseJson) {
    runner.http(
        builder ->
            TestActionUtil.performGetTest(
                builder,
                LMS_ENDPOINT,
                testName,
                requestUrl,
                TestActionUtil.getHeaders(isAuthRequired),
                config));
    runner.http(
        builder ->
            TestActionUtil.getResponseTestAction(builder, LMS_ENDPOINT, testName, responseCode));
  }

  public String getLmsApiUriPath(
      String apiGatewayUriPath, String localUriPath, String... pathParam) {
    String pathParams = "";

    for (int i = 0; i < pathParam.length; i++) {
      if (!pathParam[i].startsWith("/")) {
        pathParams += "/" + pathParam[i];
      } else {
        pathParams += pathParam[i];
      }
    }

    return (config.getLmsUrl().contains("localhost") || config.getLmsUrl().contains("11.2.0.9"))
        ? localUriPath + pathParams
        : apiGatewayUriPath + pathParams;
  }
}
