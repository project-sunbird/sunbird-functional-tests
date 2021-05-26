package org.sunbird.integration.test.course.batch;

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
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.PropertiesReader;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateCourseBatchTest extends BaseCitrusTestRunner {
  private CourseBatchUtil courseBatchUtil = new CourseBatchUtil();
  private static final String TEST_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
      "testUpdateCourseBatchFailureWithoutAuthToken";
  private static final String TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_BATCHID =
      "testUpdateCourseBatchFailureWithInvalidId";
  private static final String
      TEST_UPDATE_COURSE_BATCH_FAILURE_INVITE_ONLY_BATCH_WITH_INVALID_MENTOR =
          "testUpdateCourseBatchFailureInviteOnlyBatchWithInvalidMentor";
  private static final String TEST_UPDATE_COURSE_BATCH_FAILURE_OPEN_BATCH_WITH_INVALID_MENTOR =
      "testUpdateCourseBatchFailureOpenBatchWithInvalidMentor";
  private static final String TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_PARTICIPANTS =
      "testUpdateCourseBatchFailureWithParticipants";
  private static final String
      TEST_UPDATE_COURSE_BATCH_FAILURE_INVITE_ONLY_BATCH_WITH_INVALID_CREATED_FOR =
          "testUpdateCourseBatchFailureInviteOnlyBatchWithInvalidCreatedFor";
  public static final String TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_END_DATE_BEFORE_START_DATE =
          "testUpdateCourseBatchFailureEndDateBeforeStartDate";
  public static final String TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_ENROLLMENT_END_DATE_BEFORE_START_DATE =
          "testUpdateCourseBatchFailureEnrollmentEndDateBeforeStartDate";
  public static final String TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_ENROLLMENT_END_DATE_AFTER_END_DATE =
          "testUpdateCourseBatchFailureEnrollmentEndDateAfterEndDate";
  private static final String
      TEST_UPDATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_BATCH_WITH_VALID_MENTORS =
          "testUpdateCourseBatchSuccessInviteOnlyWithValidMentors";
  private static final String TEST_UPDATE_COURSE_BATCH_SUCCESS_OPEN_BATCH_WITH_VALID_MENTORS =
      "testUpdateCourseBatchSuccessOpenBatchWithValidMentors";
  private static final String
      TEST_UPDATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_BATCH_WITH_VALID_CREATED_FOR =
          "testUpdateCourseBatchSuccessInviteOnlyWithValidCreatedFor";

  private static String courseBatchId = "FT_Course_Batch_Id" + Instant.now().getEpochSecond();
  public static final String TEMPLATE_DIR = "templates/course/batch/update";
  public static final String START_DATE = calculateDate(3);
  public static final String END_DATE = calculateDate(8);
  public static final String FUTURE_START_DATE = calculateDate(5);
  private String getUpdateCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/batch/update", "/v1/course/batch/update");
  }

  @DataProvider(name = "updateCourseBatchDataFailureProvider")
  public Object[][] updateCourseBatchDataFailureProvider() {
    return new Object[][] {
      new Object[] {
        TEST_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN,
        false,
        false,
        false,
        HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_BATCHID,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_UPDATE_COURSE_BATCH_FAILURE_INVITE_ONLY_BATCH_WITH_INVALID_MENTOR,
        true,
        true,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_UPDATE_COURSE_BATCH_FAILURE_INVITE_ONLY_BATCH_WITH_INVALID_CREATED_FOR,
        true,
        true,
        false,
        HttpStatus.BAD_REQUEST
      },
            new Object[] {
                    TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_ENROLLMENT_END_DATE_AFTER_END_DATE,
                    true,
                    true,
                    false,
                    HttpStatus.BAD_REQUEST
            },
            new Object[] {
                    TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_ENROLLMENT_END_DATE_BEFORE_START_DATE,
                    true,
                    true,
                    false,
                    HttpStatus.BAD_REQUEST
            },
      new Object[] {
        TEST_UPDATE_COURSE_BATCH_FAILURE_OPEN_BATCH_WITH_INVALID_MENTOR,
        true,
        true,
        true,
        HttpStatus.BAD_REQUEST
      },
            new Object[] {
                    TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_END_DATE_BEFORE_START_DATE,
                    true,
                    true,
                    true,
                    HttpStatus.BAD_REQUEST
            },
      new Object[] {
              TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_PARTICIPANTS,
        true,
        true,
        false,
        HttpStatus.BAD_REQUEST
      },
    };
  }

  @DataProvider(name = "updateCourseBatchDataSuccessProvider")
  public Object[][] updateCourseBatchDataSuccessProvider() {
    return new Object[][] {
      new Object[] {
        TEST_UPDATE_COURSE_BATCH_SUCCESS_OPEN_BATCH_WITH_VALID_MENTORS, true, true, HttpStatus.OK
      },
      new Object[] {
        TEST_UPDATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_BATCH_WITH_VALID_MENTORS,
        true,
        false,
        HttpStatus.OK
      },
      new Object[] {
        TEST_UPDATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_BATCH_WITH_VALID_CREATED_FOR,
        true,
        false,
        HttpStatus.OK
      }
    };
  }

  @Test(dataProvider = "updateCourseBatchDataFailureProvider")
  @CitrusParameters({
    "testName",
    "isAuthRequired",
    "isCreateCourseRequired",
    "isOpenBatch",
    "httpStatusCode"
  })
  @CitrusTest
  public void testUpdateCourseBatchFailure(
      String testName,
      boolean isAuthRequired,
      boolean isCreateCourseRequired,
      boolean isOpenBatch,
      HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
      beforeTest(isOpenBatch);
    variable("startDate", START_DATE);
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

  @Test(dataProvider = "updateCourseBatchDataSuccessProvider")
  @CitrusParameters({"testName", "isAuthRequired", "isOpenBatch", "httpStatusCode"})
  @CitrusTest
  public void testUpdateCourseBatchSuccess(
      String testName, boolean isAuthRequired, boolean isOpenBatch, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest(isOpenBatch);
    variable("startDate", START_DATE);
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

  private void beforeTest(boolean isOpenBatch) {
    getAuthToken(this, true);
    String orgId= PropertiesReader.getInstance().getPropertyFromFile("sunbird_org_id");
    String courseId = PropertiesReader.getInstance().getPropertyFromFile("sunbird_course_id");
    String userId = PropertiesReader.getInstance().getPropertyFromFile("sunbird_user_id");
    variable("organisationId", orgId);
    variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
    variable("resourceId", ContentStoreUtil.getResourceId());
    variable("startDate", START_DATE);
    variable("courseId", courseId);
    variable("userId", userId);
    variable("endDate", END_DATE);
    variable("futureStartDate",FUTURE_START_DATE);

    variable("batchId", "");
    if (isOpenBatch) {
      courseBatchId = courseBatchUtil.getOpenCourseBatchId(this, testContext);
    } else {
      courseBatchId = courseBatchUtil.getInviteOnlyCourseBatchId(this, testContext);
    }
    variable("batchId", courseBatchId);
  }

  private static String calculateDate(int dayOffset) {

    Calendar calender = Calendar.getInstance();
    calender.add(Calendar.DAY_OF_MONTH, dayOffset);
    return new SimpleDateFormat("yyyy-MM-dd").format(calender.getTime());
  }
}
