package org.sunbird.integration.test.user.notes;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserNoteUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DeleteUserNoteTest extends BaseCitrusTestRunner {
  private static final String TEST_DELETE_USER_NOTE_FAILURE_WITH_INVALID_USERID =
      "testDeleteUserNoteFailureWithInvalidUserId";

  private static final String TEST_DELETE_USER_NOTE_FAILURE_WITH_INVALID_NOTEID =
      "testDeleteUserNoteFailureWithInvalidNoteId";
  private static final String TEST_DELETE_USER_NOTE_SUCCESS = "testDeleteUserNoteSuccess";
  public static final String TEMPLATE_DIR = "templates/user/note/delete";

  private String getDeleteNoteUrl(String pathParam) {
    return getLmsApiUriPath("/api/notes/v1/delete", "/v1/note/delete", pathParam);
  }

  @DataProvider(name = "deleteUserNoteFailureDataProvider")
  public Object[][] deleteUserNoteFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_DELETE_USER_NOTE_FAILURE_WITH_INVALID_USERID, true, HttpStatus.FORBIDDEN}
    };
  }

  @DataProvider(name = "deleteUserNoteSuccessDataProvider")
  public Object[][] deleteUserNoteSuccessDataProvider() {
    return new Object[][] {
      new Object[] {TEST_DELETE_USER_NOTE_SUCCESS, true, HttpStatus.UNAUTHORIZED}
    };
  }

  @Test(dataProvider = "deleteUserNoteFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testDeleteUserNoteFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    getAuthToken(this, isAuthRequired);
    performDeleteTest(
        this,
        TEMPLATE_DIR,
        testName,
        getDeleteNoteUrl(testContext.getVariable("noteId")),
        null,
        MediaType.APPLICATION_JSON,
        true,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "deleteUserNoteSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testDeleteUserNoteSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performDeleteTest(
        this,
        TEMPLATE_DIR,
        testName,
        getDeleteNoteUrl(testContext.getVariable("noteId")),
        null,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  @Test()
  @CitrusTest
  public void testDeleteUserNoteFailureWithInvalidNoteId() {
    getTestCase().setName(TEST_DELETE_USER_NOTE_FAILURE_WITH_INVALID_NOTEID);
    //beforeTest();
    getAuthToken(this, true);
    performDeleteTest(
        this,
        TEMPLATE_DIR,
        TEST_DELETE_USER_NOTE_FAILURE_WITH_INVALID_NOTEID,
        getDeleteNoteUrl("InvalidNoteId"),
        null,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.UNAUTHORIZED,
        RESPONSE_JSON);
  }

  void beforeTest() {
    UserNoteUtil.createNote(this, testContext);
  }
}
