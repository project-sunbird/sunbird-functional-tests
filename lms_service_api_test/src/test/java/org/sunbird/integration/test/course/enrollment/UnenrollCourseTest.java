package org.sunbird.integration.test.course.enrollment;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.common.action.CourseEnrollmentUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UnenrollCourseTest extends BaseCitrusTestRunner {
  private CourseBatchUtil courseBatchUtil = new CourseBatchUtil();
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_COURSE_ID =
      "testUnenrollCourseFailureWithoutCourseId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_BATCH_ID =
      "testUnenrollCourseFailureWithoutBatchId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_USER_ID =
      "testUnenrollCourseFailureWithoutUserId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_INVITE_ONLY_BATCH =
      "testUnenrollCourseFailureForInviteOnlyBatch";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_COURSE_ID =
      "testUnenrollCourseFailureForInvalidCourseId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_USER_ID =
      "testUnenrollCourseFailureForInvalidUserId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_BATCH_ID =
      "testUnenrollCourseFailureForInvalidBatchId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_FOR_NOT_ENROLLED_USER =
      "testUnenrollCourseFailureForNotEnrolledUser";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_FOR_UNENROLLED_USER =
      "testUnenrollCourseFailureForUnenrolledUser";
  public static final String TEST_NAME_UNENROLL_COURSE_SUCCESS = "testUnenrollCourseSuccess";

  public static final String TEMPLATE_DIR = "templates/course/batch/unenroll";
  private static String courseBatchId = "FT_Course_Batch_Id" + Instant.now().getEpochSecond();
  private static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

  private String getUnenrollCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/unenrol", "/v1/course/unenroll");
  }

  @DataProvider(name = "unenrollCourseDataProviderFailure")
  public Object[][] unenrollCourseDataProviderFailure() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_COURSE_ID,
        false,
        false,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_BATCH_ID,
        false,
        false,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_USER_ID,
        false,
        false,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_INVITE_ONLY_BATCH,
        true,
        true,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_COURSE_ID,
        true,
        true,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_USER_ID,
        true,
        true,
        true,
        false,
        false,
        HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_BATCH_ID,
        true,
        true,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_FOR_NOT_ENROLLED_USER,
        true,
        true,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_FOR_UNENROLLED_USER,
        true,
        true,
        true,
        true,
        true,
        HttpStatus.BAD_REQUEST
      },
    };
  }

  @Test(dataProvider = "unenrollCourseDataProviderFailure")
  @CitrusParameters({
    "testName",
    "canCreateUser",
    "canCreateCourseBatch",
    "isOpenBatch",
    "canEnroll",
    "canUnenroll",
    "httpStatusCode"
  })
  @CitrusTest
  public void testUnenrollCourseFailure(
      String testName,
      boolean canCreateUser,
      boolean canCreateCourseBatch,
      boolean isOpenBatch,
      boolean canEnroll,
      boolean canUnenroll,
      HttpStatus httpStatusCode) {
    beforeTest(testName, canCreateUser, canCreateCourseBatch, isOpenBatch, canEnroll, canUnenroll);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUnenrollCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "unenrollCourseDataProviderSuccess")
  public Object[][] unenrollCourseDataProviderSuccess() {
    return new Object[][] {
      new Object[] {TEST_NAME_UNENROLL_COURSE_SUCCESS},
    };
  }

  @Test(dataProvider = "unenrollCourseDataProviderSuccess")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testUnenrollCourseSuccess(String testName) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
    beforeTest(testName, true, true, true, true, false);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUnenrollCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void beforeTest(
      String testName,
      boolean canCreateUser,
      boolean canCreateCourseBatch,
      boolean isOpenBatch,
      boolean canEnroll,
      boolean canUnenroll) {
    getTestCase().setName(testName);
    getAuthToken(this, true);

    if (canCreateCourseBatch) {
      variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
      variable("resourceId", ContentStoreUtil.getResourceId());
      variable("startDate", TODAY_DATE);
      String courseId = ContentStoreUtil.getCourseId(this, testContext);
      variable("courseId", courseId);
      if (isOpenBatch) {
        courseBatchId = courseBatchUtil.getOpenCourseBatchId(this, testContext);
      } else {
        courseBatchId = courseBatchUtil.getInviteOnlyCourseBatchId(this, testContext);
      }
      variable("batchId", courseBatchId);
    }

    if (canCreateUser) {
      testContext.setVariable(Constant.USER_ID, "");
      UserUtil.createUserAndGetToken(this, testContext);
    }
    if (canEnroll) {
      CourseEnrollmentUtil.enrollCourse(this, testContext, config);
    }

    if (canUnenroll) {
      CourseEnrollmentUtil.unenrollCourse(this, testContext, config);
    }
  }
}
