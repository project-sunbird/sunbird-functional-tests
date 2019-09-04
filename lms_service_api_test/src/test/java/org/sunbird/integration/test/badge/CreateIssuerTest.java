package org.sunbird.integration.test.badge;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateIssuerTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_CREATE_ISSUER_SUCCESS = "testCreateIssuerSuccess";
  public static final String TEST_NAME_CREATE_ISSUER_SUCCESS_WITH_IMAGE =
      "testCreateIssuerSuccessWithImage";
  public static final String TEST_NAME_CREATE_ISSUER_FAILURE_WITHOUT_NAME =
      "testCreateIssuerFailureWithoutName";
  public static final String TEST_NAME_CREATE_ISSUER_FAILURE_WITHOUT_DESCRIPTION =
      "testCreateIssuerFailureWithoutDescription";
  public static final String TEST_NAME_CREATE_ISSUER_FAILURE_WITHOUT_URL =
      "testCreateIssuerFailureWithoutUrl";
  public static final String TEST_NAME_CREATE_ISSUER_FAILURE_WITH_INVALID_URL =
      "testCreateIssuerFailureWithInvalidUrl";
  public static final String TEST_NAME_CREATE_ISSUER_FAILURE_WITHOUT_EMAIL =
      "testCreateIssuerFailureWithoutEmail";
  public static final String TEST_NAME_CREATE_ISSUER_FAILURE_WITH_INVALID_EMAIL =
      "testCreateIssuerFailureWithInvalidEmail";

  public static final String TEMPLATE_DIR = "templates/badge/issuer/create";
  @Autowired private TestGlobalProperty initGlobalValues;

  private String getCreateIssuerUrl() {
    return initGlobalValues.getLmsUrl().contains("localhost")
        ? "/v1/issuer/create"
        : "/api/badging/v1/issuer/create";
  }

  @DataProvider(name = "createIssuerDataProviderSuccess")
  public Object[][] createIssuerDataProviderSuccess() {
    return new Object[][] {
      new Object[] {TEST_NAME_CREATE_ISSUER_SUCCESS},
      new Object[] {TEST_NAME_CREATE_ISSUER_SUCCESS_WITH_IMAGE}
    };
  }

  @DataProvider(name = "createIssuerDataProviderFailure")
  public Object[][] createIssuerDataProviderFailure() {
    return new Object[][] {
      new Object[] {TEST_NAME_CREATE_ISSUER_FAILURE_WITHOUT_NAME},
      new Object[] {TEST_NAME_CREATE_ISSUER_FAILURE_WITHOUT_DESCRIPTION},
      new Object[] {TEST_NAME_CREATE_ISSUER_FAILURE_WITHOUT_URL},
      new Object[] {TEST_NAME_CREATE_ISSUER_FAILURE_WITH_INVALID_URL},
      new Object[] {TEST_NAME_CREATE_ISSUER_FAILURE_WITHOUT_EMAIL},
      new Object[] {TEST_NAME_CREATE_ISSUER_FAILURE_WITH_INVALID_EMAIL}
    };
  }

  @Test(dataProvider = "createIssuerDataProviderSuccess")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testCreateIssuerSuccess(String testName) {
    System.out.println("initGlobalValues = " + initGlobalValues);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateIssuerUrl(),
        REQUEST_FORM_DATA,
        null,
        false,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "createIssuerDataProviderFailure")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testCreateIssuerFailure(String testName) {
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateIssuerUrl(),
        REQUEST_FORM_DATA,
        null,
        false,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }
}
