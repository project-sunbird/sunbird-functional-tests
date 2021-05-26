package org.sunbird.integration.test.page.section;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdatePageSectionTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_UPDATE_PAGE_SECTION_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testUpdatePageSectionFailureWithoutAccessToken";

  public static final String TEMPLATE_DIR = "templates/page/section/update";

  private String getUpdatePageSectionUrl() {

    return getLmsApiUriPath("/api/data/v1/page/section/update", "/v1/page/section/update");
  }

  @DataProvider(name = "updatePageSectionFailureDataProvider")
  public Object[][] updatePageSectionFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_UPDATE_PAGE_SECTION_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED
      },
    };
  }

  @Test(dataProvider = "updatePageSectionFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdatePageSectionFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getAuthToken(this, isAuthRequired);
    getTestCase().setName(testName);
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdatePageSectionUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
