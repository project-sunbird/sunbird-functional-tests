package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.common.BaseCitrusTestRunner;
import org.sunbird.common.EndpointConfig;

import java.util.Map;
import java.util.UUID;

/**
 * This Util Class holds methods to Create Content for TOC API's Test Cases.
 * @author Gauraw
 */
public class TOCUtil {

    private static final String CONTENT_CREATE_URL = "/content/v1/create";
    private static final String CONTENT_UPDATE_HIERARCHY_URL = "/course/v1/hierarchy/update";
    private static final String TOC_TEMPLATE_DIR = "templates/textbook/toc";
    private static final String CREATE_TEST_TEXTBOOK = "createTestTextbookSuccess";
    private static final String CREATE_TEST_TEXTBOOK_WITH_CHILDREN = "createTestTextbookWithChildrenSuccess";
    private static final String CREATE_TEST_RESOURCE_CONTENT = "createTestResourceContentSuccess";
    private static EndpointConfig.TestGlobalProperty config = new EndpointConfig().initGlobalValues();

    private static String textbookId = "";
    private static String textbookUnitId = UUID.randomUUID().toString();

    public static String createTextbook(BaseCitrusTestRunner runner, TestContext testContext) {
        return createContent(runner, testContext, CREATE_TEST_TEXTBOOK);
    }

    public static String createTextbookWithChildren(BaseCitrusTestRunner runner, TestContext testContext) {
        textbookId = createContent(runner, testContext, CREATE_TEST_TEXTBOOK);
        runner.variable("textbookId", textbookId);
        updateContentHierarchy(runner, testContext, CREATE_TEST_TEXTBOOK_WITH_CHILDREN);
        return textbookId;
    }

    public static String createResourceContent(BaseCitrusTestRunner runner, TestContext testContext) {
        return createContent(runner, testContext, CREATE_TEST_RESOURCE_CONTENT);
    }

    private static String createContent(BaseCitrusTestRunner runner, TestContext testContext, String testName) {
        String contentId = "";
        runner.http(
                builder ->
                        TestActionUtil.getPostRequestTestAction(
                                builder,
                                Constant.CONTENT_STORE_ENDPOINT,
                                TOC_TEMPLATE_DIR,
                                testName,
                                CONTENT_CREATE_URL,
                                Constant.REQUEST_JSON,
                                MediaType.APPLICATION_JSON.toString(),
                                getHeaders()));
        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                Constant.CONTENT_STORE_ENDPOINT,
                                HttpStatus.OK,
                                "$.result.content_id",
                                "contentId"));
        contentId = testContext.getVariable("contentId");
        runner.sleep(Constant.ES_SYNC_WAIT_TIME);
        return contentId;
    }

    private static void updateContentHierarchy(BaseCitrusTestRunner runner, TestContext testContext, String testName) {
        runner.http(
                builder ->
                        TestActionUtil.getPatchRequestTestAction(
                                builder,
                                Constant.CONTENT_STORE_ENDPOINT,
                                TOC_TEMPLATE_DIR,
                                testName,
                                CONTENT_UPDATE_HIERARCHY_URL,
                                Constant.REQUEST_JSON,
                                MediaType.APPLICATION_JSON.toString(),
                                getHeaders()));
        runner.http(
                builder ->
                        TestActionUtil.getResponseTestAction(
                                builder,
                                Constant.CONTENT_STORE_ENDPOINT,
                                "testUpdateCourseSuccess",
                                HttpStatus.OK));
    }

    private static Map<String, Object> getHeaders() {
        Map<String, Object> headers = TestActionUtil.getHeaders(true);
        headers.put(Constant.X_CHANNEL_ID,config.getSunbirdDefaultChannel());
        headers.put(Constant.AUTHORIZATION, Constant.BEARER + System.getenv("content_store_api_key"));
        return headers;
    }
}
