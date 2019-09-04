package org.sunbird.integration.test.user.notes;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserNoteUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchUserNoteTest extends BaseCitrusTestRunner {

  private static final String TEST_SEARCH_USER_NOTE_FAILURE_WITH_INVALID_USERID =
      "testSearchUserNoteFailureWithInvalidUserId";
  private static final String TEST_UPDATE_USER_NOTE_SUCCESS = "testSearchUserNoteSuccess";

  public static final String TEMPLATE_DIR = "templates/user/note/search";

  private String getSearchNoteUrl() {
    return getLmsApiUriPath("/api/notes/v1/search", "/v1/note/search");
  }

  @DataProvider(name = "searchUserNoteFailureDataProvider")
  public Object[][] searchUserNoteFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_SEARCH_USER_NOTE_FAILURE_WITH_INVALID_USERID, true, HttpStatus.UNAUTHORIZED
      }
    };
  }

  @DataProvider(name = "searchUserNoteSuccessDataProvider")
  public Object[][] searchUserNoteSuccessDataProvider() {
    return new Object[][] {new Object[] {TEST_UPDATE_USER_NOTE_SUCCESS, true, HttpStatus.OK}};
  }

  @Test(dataProvider = "searchUserNoteFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testSearchUserNoteFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    getAuthToken(this, isAuthRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSearchNoteUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "searchUserNoteSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testSearchUserNoteSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSearchNoteUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  void beforeTest() {
    UserNoteUtil.createNote(this, testContext);
  }
}
