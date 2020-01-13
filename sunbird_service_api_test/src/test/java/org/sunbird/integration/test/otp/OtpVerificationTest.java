package org.sunbird.integration.test.otp;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;

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
  
  public static final String TEST_VERIFY_OTP_FAILURE_WITH_INVALID_OTP =
	      "testVerifyOtpFailreWithInvalidOtp";

  public static final String TEST_GENERATE_OTP_SUCCESS_WITH_PHONE =
	      "testGenerateOtpSuccessWithPhone2";
  

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
  
  @DataProvider(name = "otpVerifyAttemptCountFailureDataProvider")
  public Object[][] otpVerifyAttemptCountFailureDataProvider() {
    return new Object[][] {
      new Object[] {TEST_VERIFY_OTP_FAILURE_WITH_INVALID_OTP},
    };
  }
  
  
  @DataProvider(name = "generateOTPSuccessDataProvider")
  public Object[][] generateOTPSuccessDataProvider() {
    return new Object[][] {
      new Object[] {TEST_GENERATE_OTP_SUCCESS_WITH_PHONE, HttpStatus.OK}
    };
    
  }
  private String getOTPGenerateUrl() {
	    return getLmsApiUriPath("/api/otp/v1/generate", "v1/otp/generate");
	  }
 
  @Test(dataProvider = "generateOTPSuccessDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testGenerateOTPSuccess(String testName, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    performPostTest(
        this,
        "templates/otp/generate",
        testName,
        getOTPGenerateUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dependsOnMethods = {"testGenerateOTPSuccess"},dataProvider = "otpVerifyAttemptCountFailureDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testOtpVerifyAttemptCountFailure(String testName) {
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
