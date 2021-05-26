package org.sunbird.integration.test.page.section;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReadPageSectionTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_READ_PAGE_SECTIONS_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testReadPageSectionFailureWithoutAccessToken";

  public static final String TEST_NAME_READ_PAGE_SECTIONS_FAILURE_WITH_INVALID_ID =
      "testReadPageSectionFailureWithInvalidId";

  public static final String TEMPLATE_DIR = "templates/page/read";

  private String getReadPageSectionUrl(String pathParam) {
    return getLmsApiUriPath("/api/data/v1/page/section/read", "/v1/page/section/read", pathParam);
  }

  @DataProvider(name = "readPageSectionFailureDataProvider")
  public Object[][] readPageSectionFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_READ_PAGE_SECTIONS_FAILURE_WITHOUT_ACCESS_TOKEN,
        false,
        HttpStatus.UNAUTHORIZED,
        "/invalid"
      },
      /*
      * JIRA - SB-5675. Once fixed enable this line
      *
      * new Object[] {
        TEST_NAME_READ_PAGE_SECTIONS_FAILURE_WITH_INVALID_ID, true, HttpStatus.NOT_FOUND, "/invalid"
      },*/
    };
  }

  @Test(dataProvider = "readPageSectionFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode", "pathParam"})
  @CitrusTest
  public void testReadPageSectionFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode, String pathParam) {
    getAuthToken(this, isAuthRequired);
    getTestCase().setName(testName);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getReadPageSectionUrl(pathParam),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
