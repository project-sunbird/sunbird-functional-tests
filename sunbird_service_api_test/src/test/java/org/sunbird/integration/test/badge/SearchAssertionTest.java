package org.sunbird.integration.test.badge;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchAssertionTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_SEARCH_ASSERTION_FAILURE_WITHOUT_FILTER =
      "testSearchAssertionFailureWithoutFilter";

  public static final String TEMPLATE_DIR = "templates/badge/assertion/search";

  private String getSearchIssuerUrl() {

    return getLmsApiUriPath(
        "/api/badging/v1/issuer/badge/assertion/search", "/v1/issuer/badge/assertion/search");
  }

  @DataProvider(name = "searchAssertionFailureDataProvider")
  public Object[][] searchAssertionFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_SEARCH_ASSERTION_FAILURE_WITHOUT_FILTER, HttpStatus.BAD_REQUEST},
    };
  }

  @Test(dataProvider = "searchAssertionFailureDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testSearchAssertionFailure(String testName, HttpStatus httpStatusCode) {

    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSearchIssuerUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
