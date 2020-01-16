package org.sunbird.integration.test.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.response.ResponseCode;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTest;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.builder.HttpClientActionBuilder.HttpClientReceiveActionBuilder;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.CitrusParameters;
import com.consol.citrus.validation.json.JsonMappingValidationCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class will have all functional test cases regarding create/update user, get user details ,
 * generate user auth key.
 *
 * @author Manzarul
 */
public class UserSignupTest extends BaseCitrusTest {

  private static String userId = null;
  public static Map<String, List<String>> deletedRecordsMap = new HashMap<String, List<String>>();
  public static final String CREATE_USER_SERVER_URI = "/api/user/v1/signup";
  public static final String CREATE_USER_LOCAL_URI = "/v1/user/signup";
  public static final String TEMPLATE_DIR = "templates/user/signup";
  
  /**
   * User can define the api request and response json structure. first index is request json
   * object, second is response json object and third is test case name.
   *
   * @return
   */
  @DataProvider(name = "createUserDataProvider")
  public Object[][] createUserDataProvider() {
    return new Object[][] {
      new Object[] {"testUserSignupFailureWithoutFirstName"},
      new Object[] {"testSignupUserFailureWithInvalidEmail"},
      new Object[] {"testSignupUserFailureWithInvalidPhone"},
      new Object[] {"testSignupUserFailureWithPhoneWithoutPhoneVerified"},
    };
  }

  @DataProvider(name = "createUserDynamicDataProvider")
  public Object[][] createUserDynamicJsonData() {
    return new Object[][] {
      new Object[] {createUserMap(), "usersuccessresponse.json", "userSignUpSuccess"},
      new Object[] {
        createUserWithDuplicateEmail(),
        TEMPLATE_DIR + "/testSignupUserWithDuplicateEmail/response.json",
        "duplicateEmailSignUpTest" },
    };
  }

  @Autowired private HttpClient restTestClient;
  @Autowired private TestGlobalProperty initGlobalValues;
  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Test for create user.
   *
   * @param requestJson
   * @param responseJson
   * @param testName
   */
  @Test(dataProvider = "createUserDynamicDataProvider")
  @CitrusParameters({"requestJson", "responseJson", "testName"})
  @CitrusTest
  public void testCreateUser(String requestJson, String responseJson, String testName) {
    getTestCase().setName(testName);
    http()
        .client(restTestClient)
        .send()
        .post(getCreateUserUrl())
        .contentType(Constant.CONTENT_TYPE_APPLICATION_JSON)
        .header(Constant.AUTHORIZATION, Constant.BEARER + initGlobalValues.getApiKey())
        .payload(requestJson);
    if (!"usersuccessresponse.json".equals(responseJson)) {
      http()
          .client(restTestClient)
          .receive()
          .response(HttpStatus.BAD_REQUEST)
          .payload(new ClassPathResource(responseJson));
    } else {
      HttpClientReceiveActionBuilder response = http().client(restTestClient).receive();
      handleUserCreationResponse(response);
      this.sleep(Constant.ES_SYNC_WAIT_TIME);
    }
  }

  /**
   * Test create user for negative scenario.
   *
   * @param testName
   */
  @Test(dataProvider = "createUserDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testCreateUserFailure(String testName) {
    performPostTest(
        testName,
        TEMPLATE_DIR,
        getCreateUserUrl(),
        REQUEST_JSON,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON,
        false,
        MediaType.APPLICATION_JSON);
  }

  /**
   * This method will handle response for create user.
   *
   * @param response HttpClientReceiveActionBuilder
   */
  private void handleUserCreationResponse(HttpClientReceiveActionBuilder response) {
    response
        .response(HttpStatus.OK)
        .validationCallback(
            new JsonMappingValidationCallback<Response>(Response.class, objectMapper) {
              @Override
              public void validate(
                  Response response, Map<String, Object> headers, TestContext context) {
                Assert.assertNotNull(response.getId());
                Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);
                Assert.assertNotNull(response.getResult().get("response"));
                userId = (String) response.getResult().get("userId");
                Assert.assertNotNull(userId);
              }
            });
  }


  private String createUserMap() {
    Map<String, Object> requestMap = new HashMap<>();
    requestMap.put(Constant.REQUEST, createUserInnerMap());
    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String createUserWithDuplicateEmail() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    requestMap.put(Constant.REQUEST, innerMap);
    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static Map<String, Object> createUserInnerMap() {
    Map<String, Object> innerMap = new HashMap<>();
    innerMap.put(Constant.FIRST_NAME, "ft_first_Name");
    innerMap.put("password", "Password1@");
    String email = Constant.USER_NAME_PREFIX + EndpointConfig.val + "@gmail.com";
    innerMap.put(Constant.EMAIL, email);
    innerMap.put(Constant.EMAIL_VERIFIED, true);
    return innerMap;
  }

  private String getCreateUserUrl() {
    return getLmsApiUriPath(CREATE_USER_SERVER_URI, CREATE_USER_LOCAL_URI);
  }

}
