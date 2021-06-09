package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.common.util.PropertiesReader;
import org.sunbird.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetCourseBatchTest extends BaseCitrusTestRunner {

  private CourseBatchUtil courseBatchUtil = new CourseBatchUtil();
  public static final String TEST_NAME_GET_COURSE_BATCH_SUCCESS_OPEN_BATCH_WITH_VALID_ID =
      "testGetCourseBatchSuccessOpenBatchWithValidId";
  public static final String TEST_NAME_GET_COURSE_BATCH_FAILURE_WITH_INVALID_ID =
      "testGetCourseBatchFailureWithInvalidId";
  public static final String TEST_NAME_GET_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
      "testGetCourseBatchFailureWithoutAuthToken";
  private static String courseBatchId = null;
  public static final String TEMPLATE_DIR = "templates/course/batch/read";

  private String getGetCourseBatchUrl(String courseBatchId) {
    return getLmsApiUriPath("/api/course/v1/batch/read", "/v1/course/batch/read", courseBatchId);
  }

  @DataProvider(name = "readCourseBatchDataFailureProvider")
  public Object[][] readCourseBatchDataFailureProvider() {
    return new Object[][] {
      new Object[] {TEST_NAME_GET_COURSE_BATCH_FAILURE_WITH_INVALID_ID, true, HttpStatus.OK},
      new Object[] {
        TEST_NAME_GET_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN, false, HttpStatus.UNAUTHORIZED
      }
    };
  }

  @Test(dataProvider = "readCourseBatchDataFailureProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testReadCourseBatchFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getAuthToken(this, isAuthRequired);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getGetCourseBatchUrl(UUID.randomUUID().toString()),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "readCourseBatchSuccessDataProvider")
  public Object[][] readCourseBatchSuccessDataProvider() {
    return new Object[][] {
      new Object[] {TEST_NAME_GET_COURSE_BATCH_SUCCESS_OPEN_BATCH_WITH_VALID_ID, true}
    };
  }

  @Test(dataProvider = "readCourseBatchSuccessDataProvider")
  @CitrusParameters({"testName", "isOpen"})
  @CitrusTest
  public void testReadCourseBatchSuccess(String testName, boolean isOpen) {
    getAuthToken(this, true);
    beforeTest(isOpen);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getGetCourseBatchUrl(courseBatchId),
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void beforeTest(boolean isOpen) {
    variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
    variable("resourceId", ContentStoreUtil.getResourceId());
    variable("startDate", CreateCourseBatchTest.TODAY_DATE);
    String courseId = System.getenv("sunbird_course_id");
    if (StringUtils.isBlank(courseId))
      courseId= PropertiesReader.getInstance().getProperty("sunbird_course_id");
    variable("courseId", courseId);
    System.out.println("niharika"+ courseId);
    courseBatchId = courseBatchUtil.getOpenCourseBatchId(this, testContext);
  }
}
