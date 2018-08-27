package org.sunbird.integration.test.user.notification.email;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EmailNotificationTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_SUBJECT =
      "testEmailNotificationFailureWithoutSubject";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_BODY =
      "testEmailNotificationFailureWithoutBody";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_RECIPIENT =
      "testEmailNotificationFailureWithoutRecipient";

  public static final String TEMPLATE_DIR = "templates/user/notification/email";

  private String getSendEmailUrl() {
    return getLmsApiUriPath("/api/user/v1/notification/email", "/v1/notification/email");
  }

  @DataProvider(name = "emailNotificationFailureDataProvider")
  public Object[][] emailNotificationFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_SUBJECT, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_BODY, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_RECIPIENT, HttpStatus.BAD_REQUEST},
    };
  }

  @Test(dataProvider = "emailNotificationFailureDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testEmailNotificationFailure(String testName, HttpStatus httpStatusCode) {

    getTestCase().setName(testName);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSendEmailUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
