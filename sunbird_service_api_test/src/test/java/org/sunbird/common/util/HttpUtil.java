package org.sunbird.common.util;

import com.consol.citrus.dsl.builder.HttpClientActionBuilder;
import com.consol.citrus.dsl.builder.HttpClientRequestActionBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.sunbird.integration.test.user.EndpointConfig.TestGlobalProperty;

/**
 * Helper class for performing HTTP related APIs.
 *
 * @author Manzarul, B Vinaya Kumar
 */
public class HttpUtil {
  private static ObjectMapper objectMapper = new ObjectMapper();
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

  public void post(
      HttpClientActionBuilder httpClientActionBuilder,
      String url,
      String contentType,
      String requestFilePath,
      Map<String, Object> headers) {

    contentType =
        StringUtils.isNotBlank(contentType) ? contentType : MediaType.APPLICATION_JSON.toString();

    HttpClientRequestActionBuilder actionBuilder =
        httpClientActionBuilder.send().post(url).contentType(contentType);

    addHeaders(actionBuilder, headers);

    actionBuilder.payload(requestFilePath);
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

  /**
   * This method is written for providing user auth token from key-cloak.
   *
   * @param url String complete of server.
   * @param userName user name for which we need to generate auth token.
   * @param password password of user.
   * @param clientId keycloak client id where user was created.
   * @return user authtoken
   */
  public static String getUserAuthToken(
      String url, String userName, String password, String clientId) {
    String token = null;
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpPost post = new HttpPost(url);
      List<NameValuePair> arguments = new ArrayList<>(3);
      arguments.add(new BasicNameValuePair("username", userName));
      arguments.add(new BasicNameValuePair("password", password));
      arguments.add(new BasicNameValuePair("client_id", clientId));
      arguments.add(new BasicNameValuePair("grant_type", "password"));
      post.setEntity(new UrlEncodedFormEntity(arguments));
      HttpResponse response = httpclient.execute(post);
      String val = EntityUtils.toString(response.getEntity());
      Map<String, String> map = objectMapper.readValue(val, Map.class);
      token = map.get("access_token");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return token;
  }
}
