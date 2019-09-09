package org.sunbird.integration.test.user.notes;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserNoteUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateUserNoteTest extends BaseCitrusTestRunner {

  private static final String TEST_UPDATE_USER_NOTE_FAILURE_WITH_INVALID_NOTEID =
      "testUpdateUserNoteFailureWithInvalidNoteId";
  private static final String TEST_UPDATE_USER_NOTE_FAILURE_WITH_INVALID_USERID =
      "testUpdateUserNoteFailureWithInvalidUserId";
  private static final String TEST_UPDATE_USER_NOTE_SUCCESS = "testUpdateUserNoteSuccess";
  public static final String TEMPLATE_DIR = "templates/user/note/update";

  private String getUpdateNoteUrl(String pathParam) {
    return getLmsApiUriPath("/api/notes/v1/update", "/v1/note/update", pathParam);
  }

  @DataProvider(name = "updateUserNoteFailureDataProvider")
  public Object[][] updateUserNoteFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_UPDATE_USER_NOTE_FAILURE_WITH_INVALID_USERID, true, HttpStatus.FORBIDDEN
      }
    };
  }

  @DataProvider(name = "updateUserNoteSuccessDataProvider")
  public Object[][] updateUserNoteSuccessDataProvider() {
    return new Object[][] {new Object[] {TEST_UPDATE_USER_NOTE_SUCCESS, true, HttpStatus.OK}};
  }

  @Test(dataProvider = "updateUserNoteFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateUserNoteFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    getAuthToken(this, isAuthRequired);
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateNoteUrl(testContext.getVariable("noteId")),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "updateUserNoteSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateUserNoteSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateNoteUrl(testContext.getVariable("noteId")),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test()
  @CitrusTest
  public void testUpdateUserNoteFailureWithInvalidNoteId() {
    getTestCase().setName(TEST_UPDATE_USER_NOTE_FAILURE_WITH_INVALID_NOTEID);
    beforeTest();
    performPatchTest(
        this,
        TEMPLATE_DIR,
        TEST_UPDATE_USER_NOTE_FAILURE_WITH_INVALID_NOTEID,
        getUpdateNoteUrl("InvalidNoteId"),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.UNAUTHORIZED,
        RESPONSE_JSON);
  }

  void beforeTest() {
    UserNoteUtil.createNote(this, testContext);
  }
}
