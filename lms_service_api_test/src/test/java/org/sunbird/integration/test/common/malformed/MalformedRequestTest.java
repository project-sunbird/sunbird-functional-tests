package org.sunbird.integration.test.common.malformed;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This class will have all functional test cases regarding testing malformed request input for all
 * common APIs
 *
 * @author Karthik
 */
public class MalformedRequestTest extends BaseCitrusTestRunner {

  private static final String CREATE_ORG_SERVER_URI = "/api/org/v1/create";
  private static final String CREATE_ORG_LOCAL_URI = "/v1/org/create";
  private static final String CREATE_COURSE_BATCH_SERVER_URI = "/api/course/v1/batch/create";
  private static final String CREATE_COURSE_BATCH_LOCAL_URI = "/v1/course/batch/create";
  private static final String CREATE_PAGE_SERVER_URI = "/api/data/v1/page/create";
  private static final String CREATE_PAGE_LOCAL_URI = "/v1/page/create";
  private static final String CREATE_USER_NOTES_SERVER_URI = "/api/notes/v1/create";
  private static final String CREATE_USER_NOTES_LOCAL_URI = "/v1/note/create";
  public static final String TEMPLATE_DIR = "templates/common/malformed";
  private static final String CREATE_USER_SERVER_URI = "/api/user/v1/create";
  private static final String CREATE_USER_LOCAL_URI = "/v1/user/create";

  @DataProvider(name = "createRequestDataProvider")
  public Object[][] createRequestDataProvider() {
    return new Object[][] {
      new Object[] {
        CREATE_USER_SERVER_URI, CREATE_USER_LOCAL_URI, "userCreateFailureWithoutContentType", null
      },
      new Object[] {
        CREATE_PAGE_SERVER_URI, CREATE_PAGE_LOCAL_URI, "pageCreateFailureWithoutContentType", null
      },
      new Object[] {
        CREATE_USER_NOTES_SERVER_URI,
        CREATE_USER_NOTES_LOCAL_URI,
        "notesCreateFailureWithoutContentType",
        null
      },
      new Object[] {
        CREATE_ORG_SERVER_URI, CREATE_ORG_LOCAL_URI, "orgCreateFailureWithoutContentType", null
      },
      new Object[] {
        CREATE_COURSE_BATCH_SERVER_URI,
        CREATE_COURSE_BATCH_LOCAL_URI,
        "batchCreateFailureWithoutContentType",
        null
      },
      new Object[] {
        CREATE_USER_SERVER_URI,
        CREATE_USER_LOCAL_URI,
        "userCreateFailureWithInvalidContentType",
        Constant.CONTENT_TYPE_APPLICATION_JSON_LD
      },
      new Object[] {
        CREATE_PAGE_SERVER_URI,
        CREATE_PAGE_LOCAL_URI,
        "pageCreateFailureWithInvalidContentType",
        Constant.CONTENT_TYPE_APPLICATION_JSON_LD
      },
      new Object[] {
        CREATE_USER_NOTES_SERVER_URI,
        CREATE_USER_NOTES_LOCAL_URI,
        "notesCreateFailureWithInvalidContentType",
        Constant.CONTENT_TYPE_APPLICATION_JSON_LD
      },
      new Object[] {
        CREATE_ORG_SERVER_URI,
        CREATE_ORG_LOCAL_URI,
        "orgCreateFailureWithInvalidContentType",
        Constant.CONTENT_TYPE_APPLICATION_JSON_LD
      },
      new Object[] {
        CREATE_COURSE_BATCH_SERVER_URI,
        CREATE_COURSE_BATCH_LOCAL_URI,
        "batchCreateFailureWithInvalidContentType",
        Constant.CONTENT_TYPE_APPLICATION_JSON_LD
      }
    };
  }

  @Test(dataProvider = "createRequestDataProvider")
  @CitrusParameters({"apiGatewayUriPath", "localUriPath", "testName", "contentType"})
  @CitrusTest
  public void testRequestWithoutContentType(
      String apiGatewayUriPath, String localUriPath, String testName, String contentType) {
    getAuthToken(this, true);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getLmsApiUriPath(apiGatewayUriPath, localUriPath),
        REQUEST_JSON,
        contentType,
        true,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }
}
