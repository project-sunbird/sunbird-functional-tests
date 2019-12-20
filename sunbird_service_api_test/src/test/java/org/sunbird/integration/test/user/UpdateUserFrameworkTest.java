package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateUserFrameworkTest extends BaseCitrusTestRunner {
  private static TestGlobalProperty testGlobalProperty = new EndpointConfig().initGlobalValues();
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_USER_ID =
      "testUpdateUserFrameworkFailureWithInvalidUserId";
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_EMPTY_USER_ID =
      "testUpdateUserFrameworkFailureWithEmptyUserId";
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_USER_ID =
      "testUpdateUserFrameworkFailureWithoutUserId";
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_BOARD =
      "testUpdateUserFrameworkFailureWithInvalidBoard";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_EMPTY_BOARD =
  //    "testUpdateUserFrameworkFailureWithEmptyBoard";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_BOARD =
  //    "testUpdateUserFrameworkFailureWithoutBoard";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_BOARD_DATA_TYPE =
  //    "testUpdateUserFrameworkFailureWithInvalidBoardDataType";
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_MEDIUM =
      "testUpdateUserFrameworkFailureWithInvalidMedium";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_EMPTY_MEDIUM =
  //    "testUpdateUserFrameworkFailureWithEmptyMedium";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_MEDIUM =
  //    "testUpdateUserFrameworkFailureWithoutMedium";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_MEDIUM_DATA_TYPE =
  //    "testUpdateUserFrameworkFailureWithInvalidMediumDataType";
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_GRADE_LEVEL =
      "testUpdateUserFrameworkFailureWithInvalidGradeLevel";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_EMPTY_GRADE_LEVEL =
  //    "testUpdateUserFrameworkFailureWithEmptyGradeLevel";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_GRADE_LEVEL =
  //    "testUpdateUserFrameworkFailureWithoutGradeLevel";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_GRADE_LEVEL_DATA_TYPE =
  //    "testUpdateUserFrameworkFailureWithInvalidGradeLevelDataType";
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_SUBJECT =
      "testUpdateUserFrameworkFailureWithInvalidSubject";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_SUBJECT_DATA_TYPE =
  //    "testUpdateUserFrameworkFailureWithInvalidSubjectDataType";
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_UNSUPPORTED_FIELD =
      "testUpdateUserFrameworkFailureWithUnsupportedField";
  public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_FRAMEWORKID =
      "testUpdateUserFrameworkFailureWithInvalidFrameworkId";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_FRAMEWORKID =
  //    "testUpdateUserFrameworkFailureWithoutFrameworkId";

  //public static final String TEST_UPDATE_USER_FRAMEWORK_SUCCESS_WITHOUT_SUBJECT =
  //    "testUpdateUserFrameworkSuccessWithoutSubject";
  //public static final String TEST_UPDATE_USER_FRAMEWORK_SUCCESS_WITH_EMPTY_SUBJECT =
  //    "testUpdateUserFrameworkSuccessWithEmptySubject";
  public static final String TEST_UPDATE_USER_FRAMEWORK_SUCCESS = "testUpdateUserFrameworkSuccess";

  public static final String TEMPLATE_DIR = "templates/user/update/framework";

  private String getUpdaterUserUrl() {
    return getLmsApiUriPath("/api/user/v1/update", "/v1/user/update");
  }

  @DataProvider(name = "updateUserFrameworkFailureDataProvider")
  public Object[][] updateUserFrameworkFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_USER_ID, HttpStatus.UNAUTHORIZED
      },
      new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_EMPTY_USER_ID, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_USER_ID, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_BOARD, HttpStatus.BAD_REQUEST},
      //new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_EMPTY_BOARD, HttpStatus.BAD_REQUEST},
      //new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_BOARD, HttpStatus.BAD_REQUEST},
      //new Object[] {
      //  TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_BOARD_DATA_TYPE, HttpStatus.BAD_REQUEST
      //},
      new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_MEDIUM, HttpStatus.BAD_REQUEST},
      //new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_EMPTY_MEDIUM, HttpStatus.BAD_REQUEST},
      //new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_MEDIUM, HttpStatus.BAD_REQUEST},
      //new Object[] {
      //  TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_MEDIUM_DATA_TYPE, HttpStatus.BAD_REQUEST
      //},
      new Object[] {
        TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_GRADE_LEVEL, HttpStatus.BAD_REQUEST
      },
      //new Object[] {
      //  TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_EMPTY_GRADE_LEVEL, HttpStatus.BAD_REQUEST
      //},
      //new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_GRADE_LEVEL, HttpStatus.BAD_REQUEST},
      //new Object[] {
      //  TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_GRADE_LEVEL_DATA_TYPE,
      //  HttpStatus.BAD_REQUEST
      //},
      new Object[] {
        TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_SUBJECT, HttpStatus.BAD_REQUEST
      },
      //new Object[] {
      //  TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_SUBJECT_DATA_TYPE, HttpStatus.BAD_REQUEST
      //},
      new Object[] {
        TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_UNSUPPORTED_FIELD, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITH_INVALID_FRAMEWORKID, HttpStatus.NOT_FOUND
      },
      //new Object[] {TEST_UPDATE_USER_FRAMEWORK_FAILURE_WITHOUT_FRAMEWORKID, HttpStatus.BAD_REQUEST},
    };
  }

  @DataProvider(name = "updateUserFrameworkSuccessDataProvider")
  public Object[][] updateUserFrameworkSuccessDataProvider() {

    return new Object[][] {
      //new Object[] {TEST_UPDATE_USER_FRAMEWORK_SUCCESS_WITHOUT_SUBJECT},
      //new Object[] {TEST_UPDATE_USER_FRAMEWORK_SUCCESS_WITH_EMPTY_SUBJECT},
      new Object[] {TEST_UPDATE_USER_FRAMEWORK_SUCCESS},
    };
  }

  @Test(dataProvider = "updateUserFrameworkFailureDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testUpdateUserFrameworkFailure(String testName, HttpStatus httpStatus) {
    getTestCase().setName(testName);
    beforeTest();
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdaterUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        httpStatus,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "updateUserFrameworkSuccessDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testUpdateUserFrameworkSuccess(String testName) {
    getTestCase().setName(testName);
    beforeTest();
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdaterUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void beforeTest() {
    UserUtil.createUserAndGetToken(this, testContext);
    variable("userId", testContext.getVariable("userId"));
    variable("board", testGlobalProperty.getBoard());
    variable("gradeLevel", testGlobalProperty.getGradeLevel());
    variable("medium", testGlobalProperty.getMedium());
    variable("subject", testGlobalProperty.getSubject());
    variable("frameworkId", testGlobalProperty.getFrameworkId());
  }
}
