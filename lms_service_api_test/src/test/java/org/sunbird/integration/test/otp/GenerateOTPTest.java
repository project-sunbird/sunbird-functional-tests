/** */
package org.sunbird.integration.test.otp;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** @author Rahul Kumar */
public class GenerateOTPTest extends BaseCitrusTestRunner {

  public static final String TEST_GENERATE_OTP_FAILURE_WITH_INVALID_KEY =
      "testGenerateOtpFailureWithInavlidKey";
  public static final String TEST_GENERATE_OTP_FAILURE_WITHOUT_TYPE =
      "testGenerateOtpFailureWithoutType";
  public static final String TEST_GENERATE_OTP_FAILURE_WITH_INVALID_TYPE =
      "testGenerateOtpFailureWithInvalidType";
  public static final String TEST_GENERATE_OTP_FAILURE_WITH_INVALID_EMAIL =
      "testGenerateOtpFailureWithInvalidEmail";
  public static final String TEST_GENERATE_OTP_FAILURE_WITH_INVALID_PHONE =
      "testGenerateOtpFailureWithInvalidPhone";
  public static final String TEST_GENERATE_OTP_SUCCESS_WITH_EMAIL =
      "testGenerateOtpSuccessWithEmail";
  public static final String TEST_GENERATE_OTP_SUCCESS_WITH_PHONE =
      "testGenerateOtpSuccessWithPhone";

  public static final String TEMPLATE_DIR = "templates/otp/generate";

  private String getOTPGenerateUrl() {
    return getLmsApiUriPath("/api/otp/v1/generate", "v1/otp/generate");
  }

  @DataProvider(name = "generateOTPFailureDataProvider")
  public Object[][] generateOTPFailureDataProvider() {
    return new Object[][] {
      new Object[] {TEST_GENERATE_OTP_FAILURE_WITH_INVALID_KEY, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_GENERATE_OTP_FAILURE_WITHOUT_TYPE, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_GENERATE_OTP_FAILURE_WITH_INVALID_TYPE, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_GENERATE_OTP_FAILURE_WITH_INVALID_EMAIL, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_GENERATE_OTP_FAILURE_WITH_INVALID_PHONE, HttpStatus.BAD_REQUEST}
    };
  }

  @Test(dataProvider = "generateOTPFailureDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testGenerateOTPFailure(String testName, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOTPGenerateUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "generateOTPSuccessDataProvider")
  public Object[][] generateOTPSuccessDataProvider() {
    return new Object[][] {
      new Object[] {TEST_GENERATE_OTP_SUCCESS_WITH_EMAIL, HttpStatus.OK},
      new Object[] {TEST_GENERATE_OTP_SUCCESS_WITH_PHONE, HttpStatus.OK}
    };
  }

  @Test(dataProvider = "generateOTPSuccessDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testGenerateOTPSuccess(String testName, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOTPGenerateUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
