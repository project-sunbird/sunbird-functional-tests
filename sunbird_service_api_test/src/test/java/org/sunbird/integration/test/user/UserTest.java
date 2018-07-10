package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.builder.HttpClientActionBuilder.HttpClientReceiveActionBuilder;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.CitrusParameters;
import com.consol.citrus.validation.json.JsonMappingValidationCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.response.ResponseCode;
import org.sunbird.common.util.Constant;
import org.sunbird.common.util.Util;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This class will have all functional test cases regarding create/update user, get user details ,
 * generate user auth key.
 *
 * @author Manzarul
 */
public class UserTest extends TestNGCitrusTestDesigner {

  private static String userId = null;
  public static volatile String user_auth_token = null;
  private static volatile String admin_token = null;
  public static Map<String, List<String>> deletedRecordsMap = new HashMap<String, List<String>>();
  private static final String CREATE_USER_URI = "/api/user/v1/create";
  private static final String CREATE_USER_LOCAL_URI = "/v1/user/create";
  private static final String UPDATE_USER_URI = "/api/user/v1/update";
  private static final String UPDATE_USER_LOCAL_URI = "/v1/user/update";
  private static final String GET_USER_BY_LOGINID_URI = "/api/user/v1/profile/read";
  private static final String GET_USER_BY_LOGINID_LOCAL_URI = "/v1/user/getuser";
  private static final String GET_USER_BY_ID_URI = "/api/user/v1/read/";
  private static final String GET_USER_BY_ID_LOCAL_URI = "/v1/user/read/";
  private static final String GET_USER_BY_LOGINID_REQ_SUB_PATH = "/loginid/request/";
  private static final String GET_USER_BY_LOGINID_RESP_SUB_PATH = "/loginid/response/";
  private static volatile String USER_NAME = "userName";
  private static String externalId = String.valueOf(System.currentTimeMillis());
  private static String provider = String.valueOf(System.currentTimeMillis() + 10);
  private static TestGlobalProperty testGlobalProperty = new EndpointConfig().initGlobalValues();

  /**
   * User can define the api request and response json structure. first index is request json
   * object, second is response json object and third is test case name.
   *
   * @return
   */
  @DataProvider(name = "createUserDataProvider")
  public Object[][] createUserDataProvider() {
    return new Object[][] {
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION + "user_first_name_mandatory.json",
        Constant.USER_TEMPLATE_LOCATION + "user_first_name_mandatory_response.json",
        "firstNameMandatoryTest"
      },
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION + "user_name_mandatory.json",
        Constant.USER_TEMPLATE_LOCATION + "user_name_mandatory_response.json",
        "UserNameMandatory"
      },
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION + "user_invalid_role_type.json",
        Constant.USER_TEMPLATE_LOCATION + "user_invalid_role_type_response.json",
        "invalidRoleType"
      },
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION + "user_invalid_language_type.json",
        Constant.USER_TEMPLATE_LOCATION + "user_invalid_language_type_response.json",
        "invalidLanguageType"
      },
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION + "user_invalid_dob_format.json",
        Constant.USER_TEMPLATE_LOCATION + "user_invalid_dob_response.json",
        "invalidDobFormat"
      }
    };
  }

  @DataProvider(name = "createUserDynamicDataProvider")
  public Object[][] createUserDynamicJsonData() {
    return new Object[][] {
      new Object[] {createUserMap(), "usersuccessresponse.json", "createUser"},
      new Object[] {
        createUserWithDuplicateEmail(),
        Constant.USER_TEMPLATE_LOCATION + "user_duplicate_email_response.json",
        "duplicateEmailTest"
      },
      new Object[] {
        createUserWithDuplicateUserName(),
        Constant.USER_TEMPLATE_LOCATION + "user_username_exist_response.json",
        "duplicateUsernameTest"
      },
      new Object[] {
        createUserWithInvalidChannel(),
        Constant.USER_TEMPLATE_LOCATION + "invalid_channel_response.json",
        "invalidChannelTest"
      }
    };
  }

  @DataProvider(name = "updateUserDataProvider")
  public Object[][] updateUserDataProvider() {
    return new Object[][] {
      new Object[] {updateUserWithId(), "update_user_success_response.json", "updateUserWithId"},
      new Object[] {
        updateUserWithRegOrgId(),
        Constant.UPDATE_USER_TEMPLATE_LOCATION + "user_update_bad_request_response.json",
        "invalidRequestDataRegOrgIdTest"
      },
      new Object[] {
        updateUserWithRootOrgId(),
        Constant.UPDATE_USER_TEMPLATE_LOCATION + "user_update_bad_request_response.json",
        "invalidRequestDataRootOrgIdTest"
      },
      new Object[] {
        updateUserWithChannel(),
        Constant.UPDATE_USER_TEMPLATE_LOCATION + "user_update_bad_request_response.json",
        "invalidRequestDataChannelTest"
      }
    };
  }

  @DataProvider(name = "getUserByLoginIdFailure")
  public Object[][] getUserByLoginIdFailure() {
    return new Object[][] {
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION
            + GET_USER_BY_LOGINID_REQ_SUB_PATH
            + "invalid_user_login_id.json",
        Constant.USER_TEMPLATE_LOCATION
            + GET_USER_BY_LOGINID_RESP_SUB_PATH
            + "invalid_user_login_type_response.json",
        "invalidLoginIdTest"
      },
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION
            + GET_USER_BY_LOGINID_REQ_SUB_PATH
            + "user_empty_loginid.json",
        Constant.USER_TEMPLATE_LOCATION
            + GET_USER_BY_LOGINID_RESP_SUB_PATH
            + "user_empty_loginid_response.json",
        "emptyLoginIdTest"
      },
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION
            + GET_USER_BY_LOGINID_REQ_SUB_PATH
            + "user_invalid_loginid_request_key.json",
        Constant.USER_TEMPLATE_LOCATION
            + GET_USER_BY_LOGINID_RESP_SUB_PATH
            + "user_empty_loginid_response.json",
        "invalidLoginIdAttributeTest"
      }
    };
  }

  @DataProvider(name = "getUserByLoginIdSuccess")
  public Object[][] userByLoginIdSuccess() {
    return new Object[][] {
      new Object[] {
        Constant.USER_TEMPLATE_LOCATION
            + GET_USER_BY_LOGINID_REQ_SUB_PATH
            + "valid_user_login_id.json",
        Constant.USER_TEMPLATE_LOCATION
            + GET_USER_BY_LOGINID_RESP_SUB_PATH
            + "user_empty_loginid_response.json",
        "getUserByLoginIdSuccess"
      }
    };
  }

  @Autowired private HttpClient restTestClient;
  @Autowired private TestGlobalProperty initGlobalValues;
  @Autowired private Util util;
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
        .post(util.getUriBasedOnHost(CREATE_USER_URI, CREATE_USER_LOCAL_URI))
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
    }
  }

  /**
   * Test create user for negative scenario.
   *
   * @param requestJson
   * @param responseJson
   * @param testName
   */
  @Test(dataProvider = "createUserDataProvider")
  @CitrusParameters({"requestJson", "responseJson", "testName"})
  @CitrusTest
  public void testCreateUserFailure(String requestJson, String responseJson, String testName) {
    getTestCase().setName(testName);
    http()
        .client(restTestClient)
        .send()
        .post(util.getUriBasedOnHost(CREATE_USER_URI, CREATE_USER_LOCAL_URI))
        .contentType(Constant.CONTENT_TYPE_APPLICATION_JSON)
        .header(Constant.AUTHORIZATION, Constant.BEARER + initGlobalValues.getApiKey())
        .payload(new ClassPathResource(requestJson));
    http()
        .client(restTestClient)
        .receive()
        .response(HttpStatus.BAD_REQUEST)
        .payload(new ClassPathResource(responseJson));
  }

  /**
   * Test for get user by login id negative scenario.
   *
   * @param requestJson
   * @param responseJson
   * @param testName
   */
  @Test(dataProvider = "getUserByLoginIdFailure")
  @CitrusParameters({"requestJson", "responseJson", "testName"})
  @CitrusTest
  public void testGetUserByLoginIdFailure(
      String requestJson, String responseJson, String testName) {
    getTestCase().setName(testName);
    http()
        .client(restTestClient)
        .send()
        .post(util.getUriBasedOnHost(GET_USER_BY_LOGINID_URI, GET_USER_BY_LOGINID_LOCAL_URI))
        .contentType(Constant.CONTENT_TYPE_APPLICATION_JSON)
        .header(Constant.AUTHORIZATION, Constant.BEARER + initGlobalValues.getApiKey())
        .payload(new ClassPathResource(requestJson));
    http()
        .client(restTestClient)
        .receive()
        .response(HttpStatus.BAD_REQUEST)
        .payload(new ClassPathResource(responseJson));
  }

  /**
   * Test for get user by login id success scenario.
   *
   * @param requestJson
   * @param responseJson
   * @param testName
   */
  @Test(
    dataProvider = "getUserByLoginIdSuccess",
    dependsOnMethods = {"testCreateUser"}
  )
  @CitrusParameters({"requestJson", "responseJson", "testName"})
  @CitrusTest
  public void testGetUserByLoginIdSuccess(
      String requestJson, String responseJson, String testName) {
    getTestCase().setName(testName);
    variable("loginIdval", USER_NAME + "@" + initGlobalValues.getSunbirdDefaultChannel());
    System.out.println(
        "User login id value is set :"
            + USER_NAME
            + "@"
            + initGlobalValues.getSunbirdDefaultChannel());
    http()
        .client(restTestClient)
        .send()
        .post(util.getUriBasedOnHost(GET_USER_BY_LOGINID_URI, GET_USER_BY_LOGINID_LOCAL_URI))
        .contentType(Constant.CONTENT_TYPE_APPLICATION_JSON)
        .header(Constant.AUTHORIZATION, Constant.BEARER + initGlobalValues.getApiKey())
        .payload(new ClassPathResource(requestJson));
    http().client(restTestClient).receive().response(HttpStatus.OK);
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

  @Test()
  @CitrusTest
  /**
   * Key cloak admin token generation is required , because on sunbird dev server after creating
   * user , user have to login first then only his/her account will be active. so we need to disable
   * that option for created user only. That option can be disable using keycloak admin auth token.
   * So this method will generate auth token and that token will be used in
   * **updateUserRequiredLoginActionTest** method.
   */
  public void getAdminAuthToken() {
    http()
        .client(restTestClient)
        .send()
        .post("/auth/realms/master" + "/protocol/openid-connect/token")
        .contentType("application/x-www-form-urlencoded")
        .payload(
            "client_id=admin-cli&username="
                + initGlobalValues.getKeycloakAdminUser()
                + "&password="
                + initGlobalValues.getKeycloakAdminPass()
                + "&grant_type=password");
    http()
        .client(restTestClient)
        .receive()
        .response(HttpStatus.OK)
        .validationCallback(
            new JsonMappingValidationCallback<Map>(Map.class, objectMapper) {
              @Override
              public void validate(Map response, Map<String, Object> headers, TestContext context) {
                Assert.assertNotNull(response.get("access_token"));
                admin_token = (String) response.get("access_token");
              }
            });
  }

  @Test(dependsOnMethods = {"testCreateUser", "getAdminAuthToken"})
  @CitrusTest
  /**
   * This method will disable user required action change password under keyCloak. after disabling
   * that , we can generate newly created user auth token.
   */
  public void updateUserRequiredLoginActionTest() {
    http()
        .client(restTestClient)
        .send()
        .put("/auth/admin/realms/" + initGlobalValues.getRelam() + "/users/" + userId)
        .header(Constant.AUTHORIZATION, Constant.BEARER + admin_token)
        .contentType(Constant.CONTENT_TYPE_APPLICATION_JSON)
        .payload("{\"requiredActions\":[]}");
    http().client(restTestClient).receive().response(HttpStatus.NO_CONTENT);
  }

  @Test(dependsOnMethods = {"updateUserRequiredLoginActionTest"})
  @CitrusTest
  public void getAuthToken() {
    if (StringUtils.isEmpty(user_auth_token)) {
      http()
          .client(restTestClient)
          .send()
          .post("/auth/realms/" + initGlobalValues.getRelam() + "/protocol/openid-connect/token")
          .contentType("application/x-www-form-urlencoded")
          .payload(
              "client_id="
                  + initGlobalValues.getClientId()
                  + "&username="
                  + USER_NAME
                  + "@"
                  + initGlobalValues.getSunbirdDefaultChannel()
                  + "&password=password&grant_type=password");
      http()
          .client(restTestClient)
          .receive()
          .response(HttpStatus.OK)
          .validationCallback(
              new JsonMappingValidationCallback<Map>(Map.class, objectMapper) {
                @Override
                public void validate(
                    Map response, Map<String, Object> headers, TestContext context) {
                  Assert.assertNotNull(response.get("access_token"));
                  user_auth_token = (String) response.get("access_token");
                }
              });
    }
  }

  @Test(
    dataProvider = "updateUserDataProvider",
    dependsOnMethods = {"testCreateUser", "getAuthToken"}
  )
  @CitrusParameters({"requestJson", "responseJson", "testName"})
  @CitrusTest
  public void testUpdateUser(String requestJson, String responseJson, String testName) {
    getTestCase().setName(testName);
    http()
        .client(restTestClient)
        .send()
        .patch(util.getUriBasedOnHost(UPDATE_USER_URI, UPDATE_USER_LOCAL_URI))
        .contentType(Constant.CONTENT_TYPE_APPLICATION_JSON)
        .header(Constant.AUTHORIZATION, Constant.BEARER + initGlobalValues.getApiKey())
        .payload(requestJson)
        .header(Constant.X_AUTHENTICATED_USER_TOKEN, user_auth_token);
    if (!"update_user_success_response.json".equals(responseJson)) {
      http()
          .client(restTestClient)
          .receive()
          .response(HttpStatus.BAD_REQUEST)
          .payload(new ClassPathResource(responseJson));
    } else {
      HttpClientReceiveActionBuilder response = http().client(restTestClient).receive();
      handleUserUpdateResponse(response);
    }
  }

  @Test(dependsOnMethods = {"getAuthToken"})
  @CitrusTest
  public void getUserTest() {
    http()
        .client(restTestClient)
        .send()
        .get(
            util.getUriBasedOnHost(GET_USER_BY_ID_URI, GET_USER_BY_ID_LOCAL_URI)
                + userId
                + "?Fields=completeness,missingFields,topic")
        .accept(Constant.CONTENT_TYPE_APPLICATION_JSON)
        .header(Constant.AUTHORIZATION, Constant.BEARER + initGlobalValues.getApiKey())
        .contentType(Constant.CONTENT_TYPE_APPLICATION_JSON)
        .header(Constant.X_AUTHENTICATED_USER_TOKEN, user_auth_token);
    http()
        .client(restTestClient)
        .receive()
        .response(HttpStatus.OK)
        .validationCallback(
            new JsonMappingValidationCallback<Response>(Response.class, objectMapper) {
              @Override
              public void validate(
                  Response response, Map<String, Object> headers, TestContext context) {
                Assert.assertNotNull(response.getId());
                Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);
              }
            });
  }

  /**
   * This method will handle response for update user.
   *
   * @param response HttpClientReceiveActionBuilder
   */
  private void handleUserUpdateResponse(HttpClientReceiveActionBuilder response) {
    response
        .response(HttpStatus.OK)
        .validationCallback(
            new JsonMappingValidationCallback<Response>(Response.class, objectMapper) {
              @Override
              public void validate(
                  Response response, Map<String, Object> headers, TestContext context) {
                Assert.assertNotNull(response.getId());
                Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);
                Assert.assertNotNull(response.getResult().get(Constant.RESPONSE));
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

  private String createUserWithDuplicateExtIdAndProvider() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    innerMap.put(
        Constant.EMAIL, Constant.USER_NAME_PREFIX + UUID.randomUUID().toString() + "@gmail.com");
    innerMap.put(Constant.USER_NAME, Constant.USER_NAME_PREFIX + UUID.randomUUID().toString());
    requestMap.put(Constant.REQUEST, innerMap);
    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String createUserWithInvalidChannel() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    innerMap.put(
        Constant.EMAIL, Constant.USER_NAME_PREFIX + UUID.randomUUID().toString() + "@gmail.com");
    innerMap.put(Constant.USER_NAME, Constant.USER_NAME_PREFIX + UUID.randomUUID().toString());
    innerMap.put(Constant.CHANNEL, "functionalTest#Invalid$Channel@1235123");
    requestMap.put(Constant.REQUEST, innerMap);
    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String createUserWithDuplicateUserName() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    innerMap.put(
        Constant.EMAIL, Constant.USER_NAME_PREFIX + UUID.randomUUID().toString() + "@gmail.com");
    requestMap.put(Constant.REQUEST, innerMap);
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
    innerMap.put(Constant.USER_NAME, Constant.USER_NAME_PREFIX + UUID.randomUUID().toString());
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
    innerMap.put(Constant.FIRST_NAME, Constant.FUNCTIONAL_TEST_DATA_PREFIX + "first_Name_pw12401");
    innerMap.put(Constant.LAST_NAME, Constant.FUNCTIONAL_TEST_DATA_PREFIX + "lastName");
    innerMap.put(Constant.PASSWORD, "password");
    innerMap.put(Constant.CHANNEL, testGlobalProperty.getSunbirdDefaultChannel());
    USER_NAME = Constant.USER_NAME_PREFIX + EndpointConfig.val;
    String email = Constant.USER_NAME_PREFIX + EndpointConfig.val + "@gmail.com";
    innerMap.put(Constant.USER_NAME, USER_NAME);
    innerMap.put(Constant.EMAIL, email);
    return innerMap;
  }

  private String updateUserWithId() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    innerMap.put(Constant.LAST_NAME, Constant.FUNCTIONAL_TEST_DATA_PREFIX + "lastName_updated");
    innerMap.put(Constant.ID, userId);
    innerMap.put(Constant.USER_ID, userId);
    innerMap.remove(Constant.USER_NAME);
    innerMap.remove(Constant.CHANNEL);
    requestMap.put(Constant.REQUEST, innerMap);

    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String updateUserWithRegOrgId() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    innerMap.put(Constant.LAST_NAME, Constant.FUNCTIONAL_TEST_DATA_PREFIX + "lastName_updated");
    innerMap.put(Constant.ID, userId);
    innerMap.put(Constant.USER_ID, userId);
    innerMap.put(Constant.REG_ORG_ID, "regOrgId");
    requestMap.put(Constant.REQUEST, innerMap);

    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String updateUserWithRootOrgId() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    innerMap.put(Constant.LAST_NAME, Constant.FUNCTIONAL_TEST_DATA_PREFIX + "lastName_updated");
    innerMap.put(Constant.ID, userId);
    innerMap.put(Constant.USER_ID, userId);
    innerMap.put(Constant.ROOT_ORG_ID, "rootOrgId");
    requestMap.put(Constant.REQUEST, innerMap);

    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String updateUserWithChannel() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    innerMap.put(Constant.LAST_NAME, Constant.FUNCTIONAL_TEST_DATA_PREFIX + "lastName_updated");
    innerMap.put(Constant.ID, userId);
    innerMap.put(Constant.USER_ID, userId);
    innerMap.put(Constant.CHANNEL, "channel");
    requestMap.put(Constant.REQUEST, innerMap);

    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String updateUserWithExtIdAndProvider() {
    Map<String, Object> requestMap = new HashMap<>();
    Map<String, Object> innerMap = createUserInnerMap();
    innerMap.put(
        Constant.LAST_NAME,
        Constant.FUNCTIONAL_TEST_DATA_PREFIX + "lastName_updated_without_userid");
    innerMap.remove(Constant.USER_NAME);
    innerMap.put(Constant.EXTERNAL_ID, externalId);
    innerMap.put(Constant.PROVIDER, provider);
    requestMap.put(Constant.REQUEST, innerMap);

    try {
      return objectMapper.writeValueAsString(requestMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
