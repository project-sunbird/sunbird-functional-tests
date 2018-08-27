package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateCourseBatchTest extends BaseCitrusTestRunner {

  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
      "testUpdateCourseBatchFailureWithoutAuthToken";
  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_BATCHID =
      "testUpdateCourseBatchFailureWithInvalidId";

  public static final String TEMPLATE_DIR = "templates/course/batch/update";
  public static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

  private String getUpdateCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/batch/update", "/v1/course/batch/update");
  }

  @DataProvider(name = "updateCourseBatchDataFailureProvider")
  public Object[][] updateCourseBatchDataFailureProvider() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN, false, HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_BATCHID, true, HttpStatus.BAD_REQUEST
      },
    };
  }

  @Test(dataProvider = "updateCourseBatchDataFailureProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateCourseBatchFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    variable("startDate", TODAY_DATE);
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
