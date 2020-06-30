package org.sunbird.kp.test.util;

import com.consol.citrus.context.TestContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.AppConfig;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.common.TestActionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LicenseUtil {

    private static final String API_KEY = AppConfig.config.getString("kp_api_key");
    private static final Boolean IS_USER_AUTH_REQUIRED = AppConfig.config.getBoolean("user_auth_enable");
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static List<String> CS_API_LIST = AppConfig.config.getStringList("cs_api_list");

    private static final String LICENSE_PAYLOAD_DIR = "templates/payload/license";


    //static payload
    private static final String CREATE_LICENSE_EXPECT_200 = "createLicenseExpect200";
    private static final String UPDATE_LICENSE_EXPECT_200 = "updateLicenseExpect200";
    private static final String RETIRE_LICENSE_EXPECT_200 = "retireLicenseExpect200";
    private static final String READ_LICENSE_EXPECT_200 = "readLicenseExpect200";


    public static Map<String, Object> createLicense(BaseCitrusTestRunner runner, String testName, Map<String, Object> headers) {
        runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
        Map<String, Object> data = new HashMap<String, Object>();
        final String updatedTestName = StringUtils.isBlank(testName) ? CREATE_LICENSE_EXPECT_200 : testName;
        if (StringUtils.isNotBlank(updatedTestName)) {
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    getEndPoint(APIUrl.CREATE_LICENSE),
                                    LICENSE_PAYLOAD_DIR,
                                    updatedTestName,
                                    APIUrl.CREATE_LICENSE,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }
        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                runner.testContext,
                                builder,
                                getEndPoint(APIUrl.CREATE_LICENSE),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(runner.testContext);

        if (MapUtils.isNotEmpty(result)) {
            data.put("identifier", result.get("node_id"));
        }
        runner.variable("identifier", result.get("node_id"));
        return data;
    }

    public static Map<String, Object> updateLicense(BaseCitrusTestRunner runner, String testName, String identifier, Map<String, Object> headers) {
        final String url = APIUrl.UPDATE_LICENSE + identifier;
        final TestContext testContext = runner.testContext;
        final String updatedTestName = StringUtils.isBlank(testName) ? UPDATE_LICENSE_EXPECT_200 : testName;
        if (StringUtils.isNotBlank(updatedTestName)) {
            runner.getTestCase().setName(updatedTestName);
            runner.http(
                    builder ->
                            TestActionUtil.processPatchRequest(
                                    builder,
                                    getEndPoint(APIUrl.UPDATE_LICENSE),
                                    LICENSE_PAYLOAD_DIR,
                                    updatedTestName,
                                    url,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(APIUrl.UPDATE_LICENSE),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(testContext);
        return new HashMap<String, Object>() {{
            put("identifier", identifier);
        }};
    }

    public static Map<String, Object> readLicense(BaseCitrusTestRunner runner, String identifier, String fields) {
        String url = APIUrl.READ_LICENSE + identifier;
        if (StringUtils.isNotBlank(fields))
            url = url + "?fields=" + fields.trim();
        final String reqUrl = url;
        if (StringUtils.isNotBlank(identifier)) {
            runner.getTestCase().setName(READ_LICENSE_EXPECT_200);
            runner.http(
                    builder ->
                            TestActionUtil.processGetRequest(
                                    builder,
                                    getEndPoint(APIUrl.READ_LICENSE),
                                    READ_LICENSE_EXPECT_200,
                                    reqUrl,
                                    getHeaders(null)
                            ));
            runner.http(
                    builder ->
                            TestActionUtil.getExtractFromResponseTestAction(
                                    runner.testContext,
                                    builder,
                                    getEndPoint(APIUrl.READ_LICENSE),
                                    HttpStatus.OK,
                                    "$.result",
                                    "result"));
            getResult(runner.testContext);
        }
        return null;
    }

    public static Map<String, Object> retireLicense(BaseCitrusTestRunner runner, String identifier, Map<String, Object> headers) {
        final String url = APIUrl.RETIRE_LICENSE + identifier;
        runner.getTestCase().setName(RETIRE_LICENSE_EXPECT_200);
        runner.http(
                builder ->
                        TestActionUtil.processDeleteRequest(
                                builder,
                                getEndPoint(APIUrl.RETIRE_LICENSE),
                                LICENSE_PAYLOAD_DIR,
                                RETIRE_LICENSE_EXPECT_200,
                                url,
                                Constant.REQUEST_JSON,
                                Constant.CONTENT_TYPE_APPLICATION_JSON,
                                getHeaders(headers)));

        runner.variable("identifier", identifier);
        runner.http(
                builder ->
                        TestActionUtil.getResponse(builder,
                                getEndPoint(APIUrl.RETIRE_LICENSE),
                                LICENSE_PAYLOAD_DIR,
                                RETIRE_LICENSE_EXPECT_200,
                                HttpStatus.OK,
                                Constant.RESPONSE_JSON,
                                null));
        return new HashMap<String, Object>() {{
            put("identifier", identifier);
        }};
    }

    private static Map<String, Object> getHeaders(Map<String, Object> additionalHeaders) {
        Map<String, Object> headers = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(additionalHeaders))
            headers.putAll(additionalHeaders);

        headers.put(Constant.AUTHORIZATION, Constant.BEARER + API_KEY);
        headers.put(Constant.X_CONSUMER_ID, UUID.randomUUID().toString());

        if (IS_USER_AUTH_REQUIRED)
            headers.put(Constant.X_AUTHENTICATED_USER_TOKEN, "${accessToken}");
        return headers;
    }

    /**
     * This Method Returns Result Map
     *
     * @param testContext
     * @return
     */
    private static Map<String, Object> getResult(TestContext testContext) {
        Map<String, Object> result = null;
        try {
            result = objectMapper.readValue(testContext.getVariable("result"), new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            System.out.println("Exception Occurred While parsing context variable : " + e);
        }
        return result;
    }

    private static String getEndPoint(String reqUrl) {
        return CS_API_LIST.contains(reqUrl) ? Constant.KP_CONTENT_SERVICE_ENDPOINT : Constant.KP_ENDPOINT;
    }
}
