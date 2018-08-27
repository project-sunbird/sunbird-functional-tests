package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;

public class UserNoteUtil {
  public static final String BT_CREATE_NOTE_TEMPLATE_DIR = "templates/user/note/create";
  private static final String TEST_CREATE_USER_NOTE_SUCCESS_WITH_COURSEID_AND_CONTENTID =
      "testCreateUserNoteSuccessWithCourseIdAndContentId";

  private static String getCreateNoteUrl(BaseCitrusTestRunner runner) {

    return runner.getLmsApiUriPath("/api/notes/v1/create", "/v1/note/create");
  }

  public static void createUserNote(
      BaseCitrusTestRunner runner, TestContext testContext, String templateDir, String testName) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getCreateNoteUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
    runner.http(
        builder ->
            TestActionUtil.getExtractFromResponseTestAction(
                testContext,
                builder,
                Constant.LMS_ENDPOINT,
                HttpStatus.OK,
                "$.result.id",
                "noteId"));

    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void createNote(BaseCitrusTestRunner runner, TestContext testContext) {
    UserUtil.getUserId(runner, testContext);
    runner.variable("userId", testContext.getVariable("userId"));
    String userName = UserUtil.getUserNameWithChannel(runner, testContext);
    runner.getAuthToken(
        runner, userName, Constant.PASSWORD, testContext.getVariable("userId"), true);
    UserNoteUtil.createUserNote(
        runner,
        testContext,
        BT_CREATE_NOTE_TEMPLATE_DIR,
        TEST_CREATE_USER_NOTE_SUCCESS_WITH_COURSEID_AND_CONTENTID);
  }
}
