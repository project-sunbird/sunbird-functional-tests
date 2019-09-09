package org.sunbird.integration.test.otp;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OtpVerificationTest extends BaseCitrusTestRunner {

  public static final String TEST_VERIFY_OTP_FAILURE_WITHOUT_PHONE_KEY =
      "testVerifyOtpFailureWithoutPhoneKey";

  public static final String TEST_VERIFY_OTP_FAILURE_WITH_INVALID_PHONE_NUMBER =
      "testVerifyOtpFailureWithInvalidPhonenNumber";

  public static final String TEST_VERIFY_OTP_FAILURE_WITHOUT_EMAIL_KEY =
      "testVerifyOtpFailureWithoutEmailKey";

  public static final String TEST_VERIFY_OTP_FAILURE_WITH_INVALID_EMAIL =
      "testVerifyOtpFailureWithInvalidEmail";

  public static final String TEST_VERIFY_OTP_FAILURE_WITHOUT_OTP = "testVerifyOtpFailureWithoutOtp";

  public static final String TEST_VERIFY_OTP_FAILURE_WITHOUT_TYPE =
      "testVerifyOtpFailureWithoutType";

  public static final String TEST_VERIFY_OTP_FAILURE_WITH_INVALID_TYPE =
      "testVerifyOtpFailureWithInvalidType";

  public static final String TEMPLATE_DIR = "templates/otp/verify";

  private String getOtpVerifyUrl() {
    return getLmsApiUriPath("/api/otp/v1/verify", "v1/otp/verify");
  }

  @DataProvider(name = "otpVerifyFailureDataProvider")
  public Object[][] otpVerifyFailureDataProvider() {
    return new Object[][] {
      new Object[] {TEST_VERIFY_OTP_FAILURE_WITHOUT_PHONE_KEY},
      new Object[] {TEST_VERIFY_OTP_FAILURE_WITH_INVALID_PHONE_NUMBER},
      new Object[] {TEST_VERIFY_OTP_FAILURE_WITHOUT_EMAIL_KEY},
      new Object[] {TEST_VERIFY_OTP_FAILURE_WITH_INVALID_EMAIL},
      new Object[] {TEST_VERIFY_OTP_FAILURE_WITHOUT_OTP},
      new Object[] {TEST_VERIFY_OTP_FAILURE_WITHOUT_TYPE},
      new Object[] {TEST_VERIFY_OTP_FAILURE_WITH_INVALID_TYPE},
    };
  }

  @Test(dataProvider = "otpVerifyFailureDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testOtpVerifyFailure(String testName) {
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOtpVerifyUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }
}
