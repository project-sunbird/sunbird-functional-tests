package org.sunbird.integration.test.course.enrollment;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.common.action.CourseEnrollmentUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.common.util.PropertiesReader;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EnrollCourseTest extends BaseCitrusTestRunner {

  private CourseBatchUtil courseBatchUtil = new CourseBatchUtil();
  public static final String TEST_NAME_ENROLL_COURSE_FAILURE_WITHOUT_COURSE_ID =
      "testEnrollCourseFailureWithoutCourseId";
  public static final String TEST_NAME_ENROLL_COURSE_FAILURE_WITHOUT_BATCH_ID =
      "testEnrollCourseFailureWithoutBatchId";
  public static final String TEST_NAME_ENROLL_COURSE_FAILURE_WITHOUT_USER_ID =
      "testEnrollCourseFailureWithoutUserId";
  public static final String TEST_NAME_ENROLL_COURSE_FAILURE_INVITE_ONLY_BATCH =
      "testEnrollCourseFailureForInviteOnlyBatch";
  public static final String TEST_NAME_ENROLL_COURSE_FAILURE_INVALID_COURSE_ID =
      "testEnrollCourseFailureForInvalidCourseId";
  public static final String TEST_NAME_ENROLL_COURSE_FAILURE_INVALID_USER_ID =
      "testEnrollCourseFailureForInvalidUserId";
  public static final String TEST_NAME_ENROLL_COURSE_FAILURE_INVALID_BATCH_ID =
      "testEnrollCourseFailureForInvalidBatchId";
  public static final String TEST_NAME_ENROLL_COURSE_FAILURE_FOR_ENROLLED_USER =
      "testEnrollCourseFailureForEnrolledUser";
  public static final String TEST_NAME_ENROLL_COURSE_SUCCESS_FOR_UNENROLLED_USER =
      "testEnrollCourseSuccessForUnenrolledUser";
  public static final String TEST_NAME_ENROLL_COURSE_SUCCESS = "testEnrollCourseSuccess";

  public static final String TEMPLATE_DIR = "templates/course/batch/enroll";
  private static String courseBatchId = "FT_Course_Batch_Id" + Instant.now().getEpochSecond();
  private static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

  private String getEnrollCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/enrol", "/v1/course/enroll");
  }

  @DataProvider(name = "enrollCourseDataProviderFailure")
  public Object[][] enrollCourseDataProviderFailure() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_ENROLL_COURSE_FAILURE_WITHOUT_COURSE_ID,
        false,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ENROLL_COURSE_FAILURE_WITHOUT_BATCH_ID,
        false,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ENROLL_COURSE_FAILURE_WITHOUT_USER_ID,
        false,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ENROLL_COURSE_FAILURE_INVITE_ONLY_BATCH,
        true,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ENROLL_COURSE_FAILURE_INVALID_COURSE_ID,
        true,
        true,
        true,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ENROLL_COURSE_FAILURE_INVALID_USER_ID,
        true,
        true,
        true,
        false,
        HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_NAME_ENROLL_COURSE_FAILURE_INVALID_BATCH_ID,
        true,
        true,
        true,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ENROLL_COURSE_FAILURE_FOR_ENROLLED_USER,
        true,
        true,
        true,
        true,
        HttpStatus.BAD_REQUEST
      },
    };
  }

  @Test(dataProvider = "enrollCourseDataProviderFailure")
  @CitrusParameters({
    "testName",
    "canCreateUser",
    "canCreateCourseBatch",
    "isOpenBatch",
    "canEnroll",
    "httpStatusCode"
  })
  @CitrusTest
  public void testEnrollCourseFailure(
      String testName,
      boolean canCreateUser,
      boolean canCreateCourseBatch,
      boolean isOpenBatch,
      boolean canEnroll,
      HttpStatus httpStatusCode) {
    beforeTest(testName, canCreateUser, canCreateCourseBatch, isOpenBatch, canEnroll, false);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getEnrollCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "enrollCourseDataProviderSuccess")
  public Object[][] enrollCourseDataProviderSuccess() {
    return new Object[][] {
      new Object[] {TEST_NAME_ENROLL_COURSE_SUCCESS, false, false},
      new Object[] {TEST_NAME_ENROLL_COURSE_SUCCESS_FOR_UNENROLLED_USER, true, true},
    };
  }

  @Test(dataProvider = "enrollCourseDataProviderSuccess")
  @CitrusParameters({"testName", "canEnroll", "canUnenroll"})
  @CitrusTest
  public void testEnrollCourseSuccess(String testName, boolean canEnroll, boolean canUnenroll) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
    beforeTest(testName, true, true, true, canEnroll, canUnenroll);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getEnrollCourseBatchUrl(),
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
      variable("endDate",calculateDate(4));
      String courseId = PropertiesReader.getInstance().getPropertyFromFile("sunbird_course_id");
      variable("courseId", courseId);
      if (isOpenBatch) {
        courseBatchId = courseBatchUtil.getOpenCourseBatchId(this, testContext);
      } else {
        courseBatchId = courseBatchUtil.getInviteOnlyCourseBatchId(this, testContext);
      }
      variable("batchId", courseBatchId);
    }
    if (canCreateUser) {
      String userId = PropertiesReader.getInstance().getPropertyFromFile("sunbird_user_id");
      variable("userId", userId);
    }
    if (canEnroll) {
      CourseEnrollmentUtil.enrollCourse(this, testContext, config);
    }

    if (canUnenroll) {
      CourseEnrollmentUtil.unenrollCourse(this, testContext, config);
    }
  }

  private String calculateDate(int dayOffset) {

    Calendar calender = Calendar.getInstance();
    calender.add(Calendar.DAY_OF_MONTH, dayOffset);
    return format.format(calender.getTime());
  }
}
