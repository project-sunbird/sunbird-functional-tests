package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddUserToCourseBatch extends BaseCitrusTestRunner {

  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITH_INVALID_BATCHID =
      "testAddUserToCourseBatchFailureWithInvalidBatchId";
  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
      "testAddUserToCourseBatchFailureWithoutAuthToken";
  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITHOUT_USER_IDS =
      "testAddUserToCourseBatchFailureWithoutUserIds";
  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_OPEN_BATCH =
      "testAddUserToCourseBatchFailureOpenBatch";
  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_INVITE_ONLY_BATCH_WITHOUT_ORG_ID =
      "testAddUserToCourseBatchFailureInviteOnlyBatchWithoutOrgId";

  public static final String TEMPLATE_DIR = "templates/course/batch/addUser";
  private static String courseBatchId = UUID.randomUUID().toString();
  private static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

  private String getAddUserToBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/batch/user/add", "/v1/course/batch/users/add/");
  }

  @DataProvider(name = "addUserToCourseBatchDataFailureProvider")
  public Object[][] addUserToCourseBatchDataFailureProvider() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITHOUT_AUTH_TOKEN,
        false,
        false,
        false,
        HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITH_INVALID_BATCHID,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITHOUT_USER_IDS,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_OPEN_BATCH, true, true, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_INVITE_ONLY_BATCH_WITHOUT_ORG_ID,
        true,
        true,
        false,
        HttpStatus.NOT_FOUND
      },
    };
  }

  @Test(dataProvider = "addUserToCourseBatchDataFailureProvider")
  @CitrusParameters({
    "testName",
    "isAuthRequired",
    "canCreateCourseBatch",
    "isOpenBatch",
    "httpStatusCode"
  })
  @CitrusTest
  public void testAddUserToCourseBatchFailure(
      String testName,
      boolean isAuthRequired,
      boolean canCreateCourseBatch,
      boolean isOpenBatch,
      HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    beforeTest(canCreateCourseBatch, isOpenBatch);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getAddUserToBatchUrl() + courseBatchId,
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest(boolean canCreateCourseBatch, boolean isOpenBatch) {
    if (canCreateCourseBatch) {
      variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
      variable("resourceId", ContentStoreUtil.getResourceId());
      variable("startDate", TODAY_DATE);
      String courseId = ContentStoreUtil.getCourseId(this, testContext);
      variable("courseId", courseId);
      if (isOpenBatch) {
        courseBatchId = CourseBatchUtil.getOpenCourseBatchId(this, testContext);
      } else {
        courseBatchId = CourseBatchUtil.getInviteOnlyCourseBatchId(this, testContext, "", "");
      }
    }
  }
}
