package org.sunbird.common.util;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.builder.HttpClientActionBuilder;
import com.consol.citrus.dsl.builder.HttpClientRequestActionBuilder;
import com.consol.citrus.dsl.builder.HttpClientActionBuilder.HttpClientReceiveActionBuilder;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.validation.json.JsonMappingValidationCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.response.ResponseCode;
import org.sunbird.integration.test.common.BaseCitrusTest;
import org.sunbird.integration.test.user.EndpointConfig;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;
import org.testng.Assert;

/**
 * Helper class for performing HTTP related APIs.
 *
 * @author Manzarul, B Vinaya Kumar
 */
public class HttpUtil extends BaseCitrusTest {

	@Autowired private HttpClient restTestClient;
	@Autowired private HttpClient keycloakTestClient;
	@Autowired private TestGlobalProperty initGlobalValues;
	String userId;
	String token;
	private ObjectMapper objectMapper = new ObjectMapper();
 /**
   * This method is written for deleting test data from elastic search.
   *
   * @param url String complete url including the id of the element need to be deleted.
   * @return boolean true if deleted else false;
   */
  public static boolean doDeleteOperation(String url) {
    boolean deleteResponse = true;
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpDelete httpDelete = new HttpDelete(url);
      System.out.println("Executing request " + httpDelete.getRequestLine());
      // Create a custom response handler
      ResponseHandler<String> responseHandler =
          response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
              HttpEntity entity = response.getEntity();
              return entity != null ? EntityUtils.toString(entity) : null;
            } else {
              throw new ClientProtocolException("Unexpected response status: " + status);
            }
          };
      String responseBody = httpclient.execute(httpDelete, responseHandler);
      System.out.println(responseBody);
    } catch (Exception e) {
      deleteResponse = false;
      e.printStackTrace();
    }
    return deleteResponse;
  }

  /**
   * Send multipart HTTP post request with form data.
   *
   * @param httpClientActionBuilder HTTP client action builder to use for sending the request.
   * @param config Configuration (e.g. API key) used in sending HTTP request
   * @param url HTTP URL to use in the request
   * @param formDataFile File path containing each form parameter in a new line in format
   *     (key=value)
   * @param formDataFileFolderPath Folder path containing multipart file resource
   */
  public void multipartPost(
      HttpClientActionBuilder httpClientActionBuilder,
      TestGlobalProperty config,
      String url,
      String formDataFile,
      String formDataFileFolderPath) {
    multipartPost(httpClientActionBuilder, config, url, formDataFile, formDataFileFolderPath, null);
  }

  public void multipartPost(
      HttpClientActionBuilder httpClientActionBuilder,
      TestGlobalProperty config,
      String url,
      String formDataFile,
      String formDataFileFolderPath,
      Map<String, Object> headers) {
    MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

    try (Scanner scanner =
        new Scanner(new File(getClass().getClassLoader().getResource(formDataFile).getFile()))) {

      while (scanner.hasNext()) {
        String[] param = scanner.nextLine().split(Constant.EQUAL_SIGN);
        if (param != null && param.length == 2) {
          if (param[0].equalsIgnoreCase(Constant.MULTIPART_FILE_NAME)) {
            formData.add(
                Constant.MULTIPART_FILE_NAME,
                new ClassPathResource(formDataFileFolderPath + "/" + param[1]));
          } else {
            formData.add(param[0], param[1]);
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    HttpClientRequestActionBuilder actionBuilder =
        httpClientActionBuilder
            .send()
            .post(url)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .header(Constant.AUTHORIZATION, Constant.BEARER + config.getApiKey());

    if (null != headers) {
      actionBuilder = addHeaders(actionBuilder, headers);
    }
    actionBuilder.payload(formData);
  }

  private HttpClientRequestActionBuilder addHeaders(
      HttpClientRequestActionBuilder actionBuilder, Map<String, Object> headers) {
    if (headers != null) {
      for (Map.Entry<String, Object> entry : headers.entrySet()) {
        actionBuilder = actionBuilder.header(entry.getKey(), entry.getValue());
      }
    }
    return actionBuilder;
  }
  
  
  public  Map<String,String> createUserAndGetToken () {
	  Map<String,String> map = new HashMap<String, String>();
	  Map<String, Object> userRequestMap  = createUserMap();
	  Map<String, Object> innerMap = (Map<String, Object>)userRequestMap.get(Constant.REQUEST);
	  map.put(Constant.EMAIL, (String)innerMap.get(Constant.EMAIL));
	  try {
		http().client(restTestClient)
		  .send()
		  .post("/api/user/v1/signup")
		  .contentType(Constant.CONTENT_TYPE_APPLICATION_JSON)
		  .header(Constant.AUTHORIZATION, Constant.BEARER + initGlobalValues.getApiKey())
		  .payload(objectMapper.writeValueAsString(userRequestMap));
		
		HttpClientReceiveActionBuilder response = http().client(restTestClient).receive();
	      handleUserCreationResponse(response,(String)innerMap.get(Constant.EMAIL));
	     } catch (JsonProcessingException e) {
		   e.printStackTrace();
	    }
	  map.put(Constant.USER_ID, userId);
	  map.put("token", token);
	  return null;
	  
  }
  
  
  /**
   * This method will handle response for create user.
   *
   * @param response HttpClientReceiveActionBuilder
   */
	private void handleUserCreationResponse(HttpClientReceiveActionBuilder response,String email) {
		response.response(HttpStatus.OK)
				.validationCallback(new JsonMappingValidationCallback<Response>(Response.class, objectMapper) {
					@Override
					public void validate(Response response, Map<String, Object> headers, TestContext context) {
						Assert.assertNotNull(response.getId());
						Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);
						Assert.assertNotNull(response.getResult().get("response"));
						userId = (String) response.getResult().get("userId");
						Assert.assertNotNull(userId);
						getAuthToken(email, "Password1@");
					}
				});
	}

  
	
	
	
	public void getAuthToken(String email,String password) {
	    http()
	        .client(keycloakTestClient)
	        .send()
	        .post("/realms/" + initGlobalValues.getRelam() + "/protocol/openid-connect/token")
	        .contentType("application/x-www-form-urlencoded")
	        .payload(
	            "client_id=admin-cli&username="
	                + email
	                + "&password="
	                + password
	                + "&grant_type=password");
	    http()
	        .client(keycloakTestClient)
	        .receive()
	        .response(HttpStatus.OK)
	        .validationCallback(
	            new JsonMappingValidationCallback<Map>(Map.class, objectMapper) {
	              @Override
	              public void validate(Map response, Map<String, Object> headers, TestContext context) {
	                Assert.assertNotNull(response.get("access_token"));
	                token = (String) response.get("access_token");
	              }
	            });
	  }

	
	
  private Map<String, Object> createUserMap() { 
	    Map<String, Object> requestMap = new HashMap<>();
	    requestMap.put(Constant.REQUEST, createUserInnerMap());
	    return requestMap;
	  }

	  private static Map<String, Object> createUserInnerMap() {
	    Map<String, Object> innerMap = new HashMap<>();
	    innerMap.put(Constant.FIRST_NAME, "ft_first_Name");
	    innerMap.put("password", "Password1@");
	    String email = Constant.USER_NAME_PREFIX + UUID.randomUUID().toString() + "@gmail.com";
	    innerMap.put(Constant.EMAIL, email);
	    innerMap.put(Constant.EMAIL_VERIFIED, true);
	    return innerMap;
	  }

  
}
