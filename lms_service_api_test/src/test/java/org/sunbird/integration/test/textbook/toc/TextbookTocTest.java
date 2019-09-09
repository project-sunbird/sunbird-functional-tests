package org.sunbird.integration.test.textbook.toc;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.TOCUtil;
import org.sunbird.common.action.TestActionUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Integration Test Cases for Textbook Toc Upload, Update & Download API's
 * @author Gauraw
 */
public class TextbookTocTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/textbook/toc";

    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_TEXTBOOK =
            "testTocUploadSuccessWithValidFileAndTextbook";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_URL_AND_TEXTBOOK =
            "testTocUploadSuccessWithValidFileUrlAndTextbook";
    private static final String TEST_TOC_UPDATE_WITH_VALID_FILE_URL_AND_TEXTBOOK =
            "testTocUpdateSuccessWithValidFileUrlAndTextbook";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_ID =
            "testTocUploadFailureWithValidFileAndInvalidTextbookId";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_MIME_TYPE =
            "testTocUploadFailureWithValidFileAndInvalidTextbookMimeType";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CONTENT_TYPE =
            "testTocUploadFailureWithValidFileAndInvalidTextbookContentType";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CHILDREN_EXISTS =
            "testTocUploadFailureWithValidFileAndInvalidTextbookChildrenExists";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_DATA =
            "testTocUploadFailureWithValidFileAndInvalidData";

    private static final String TEST_TOC_UPLOAD_BLANK_CSV_FILE =
            "testTocUploadFailureBlankCsvFile";
    private static final String TEST_TOC_UPLOAD_BLANK_CSV_FILE_NO_HEADER =
            "testTocUploadFailureBlankCsvFileNoHeader";
    private static final String TEST_TOC_UPLOAD_BLANK_CSV_FILE_HEADERS_ONLY =
            "testTocUploadFailureBlankCsvFileHeadersOnly";
    private static final String TEST_TOC_UPLOAD_CSV_ROWS_EXCEED =
            "testTocUploadFailureCsvRowsExceed";
    private static final String TEST_TOC_UPLOAD_CSV_DUPLICATE_ROWS =
            "testTocUploadFailureCsvDuplicateRows";
    private static final String TEST_TOC_UPLOAD_CSV_INVALID_TEXTBOOK_NAME =
            "testTocUploadFailureCsvInValidTextbookName";
    private static final String TEST_TOC_UPLOAD_CSV_REQUIRED_FIELD_HEADER_MISSING =
            "testTocUploadFailureCsvRequiredFieldHeaderMissing";
    private static final String TEST_TOC_UPLOAD_CSV_REQUIRED_FIELD_DATA_MISSING =
            "testTocUploadFailureCsvRequiredFieldDataMissing";

    private static final String TEST_TOC_DOWNLOAD_VALID_TEXTBOOK_ID =
            "testTocDownloadSuccessValidTextbookId";
    private static final String TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_ID =
            "testTocDownloadFailureInvalidTextbookId";
    private static final String TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_CONTENT_TYPE =
            "testTocDownloadFailureInvalidTextbookContentType";
    private static final String TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_MIME_TYPE =
            "testTocDownloadFailureInvalidTextbookMimeType";
    private static final String TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_CHILDREN_NOT_EXIST =
            "testTocDownloadFailureInvalidTextbookChildrenNotExist";


    @Test(dataProvider = "tocUploadSuccessDataProvider")
    @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired", "contentType"})
    @CitrusTest
    public void testTocUploadSuccess(
            String testName, HttpStatus httpStatusCode, boolean isAuthRequired, String contentType) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);

        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                getTocUploadUrl(getContentId(contentType)),
                REQUEST_FORM_DATA,
                getHeader(),
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @Test(dataProvider = "tocUploadFailureDataProvider")
    @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired", "contentType"})
    @CitrusTest
    public void testTocUploadFailure(
            String testName, HttpStatus httpStatusCode, boolean isAuthRequired, String contentType) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                getTocUploadUrl(getContentId(contentType)),
                REQUEST_FORM_DATA,
                getHeader(),
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @Test(dataProvider = "tocDownloadSuccessDataProvider")
    @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired", "contentType"})
    @CitrusTest
    public void testTocDownloadSuccess(String testName, HttpStatus httpStatusCode, boolean isAuthRequired, String contentType) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                getTocDownloadUrl(getContentId(contentType)),
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @Test(dataProvider = "tocDownloadFailureDataProvider")
    @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired", "contentType"})
    @CitrusTest
    public void testTocDownloadFailure(String testName, HttpStatus httpStatusCode, boolean isAuthRequired, String contentType) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                getTocDownloadUrl(getContentId(contentType)),
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @Test(dataProvider = "tocUpdateSuccessDataProvider")
    @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired"})
    @CitrusTest
    public void testTocUpdateSuccess(
            String testName, HttpStatus httpStatusCode, boolean isAuthRequired) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        Map<String, String> data = getTOCUrl(this, testContext);
        testContext.setVariable("tocFileUrl", data.getOrDefault("fileUrl", ""));
        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                getTocUploadUrl(data.getOrDefault("contentId", "")),
                REQUEST_FORM_DATA,
                getHeader(),
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @DataProvider(name = "tocUploadSuccessDataProvider")
    public Object[][] tocUploadSuccessDataProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_TEXTBOOK, HttpStatus.OK, true, "TextBook"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_URL_AND_TEXTBOOK, HttpStatus.OK, true, "TextBook"
                }
        };
    }

    @DataProvider(name = "tocUploadFailureDataProvider")
    public Object[][] tocUploadFailureDataProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_ID, HttpStatus.NOT_FOUND, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_MIME_TYPE, HttpStatus.BAD_REQUEST, true, "Resource"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CONTENT_TYPE, HttpStatus.BAD_REQUEST, true, "Resource"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CHILDREN_EXISTS, HttpStatus.BAD_REQUEST, true, "TextBookWithChildren"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_DATA, HttpStatus.BAD_REQUEST, true, "TextBook"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_BLANK_CSV_FILE, HttpStatus.INTERNAL_SERVER_ERROR, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_BLANK_CSV_FILE_NO_HEADER, HttpStatus.BAD_REQUEST, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_BLANK_CSV_FILE_HEADERS_ONLY, HttpStatus.BAD_REQUEST, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_ROWS_EXCEED, HttpStatus.BAD_REQUEST, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_DUPLICATE_ROWS, HttpStatus.BAD_REQUEST, true, "TextBook"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_INVALID_TEXTBOOK_NAME, HttpStatus.BAD_REQUEST, true, "TextBook"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_REQUIRED_FIELD_HEADER_MISSING, HttpStatus.BAD_REQUEST, true, "TextBook"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_REQUIRED_FIELD_DATA_MISSING, HttpStatus.BAD_REQUEST, true, "TextBook"
                }
        };

    }

    @DataProvider(name = "tocUpdateSuccessDataProvider")
    public Object[][] tocUpdateSuccessDataProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_TOC_UPDATE_WITH_VALID_FILE_URL_AND_TEXTBOOK, HttpStatus.OK, true
                }
        };
    }

    @DataProvider(name = "tocDownloadSuccessDataProvider")
    public Object[][] tocDownloadSuccessDataProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_TOC_DOWNLOAD_VALID_TEXTBOOK_ID, HttpStatus.OK, true, "TextBookWithChildren"
                }
        };
    }

    @DataProvider(name = "tocDownloadFailureDataProvider")
    public Object[][] tocDownloadFailureDataProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_ID, HttpStatus.NOT_FOUND, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_CONTENT_TYPE, HttpStatus.BAD_REQUEST, true, "Resource"
                },
                new Object[]{
                        TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_MIME_TYPE, HttpStatus.BAD_REQUEST, true, "Resource"
                },
                new Object[]{
                        TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_CHILDREN_NOT_EXIST, HttpStatus.BAD_REQUEST, true, "TextBook"
                }
        };
    }

    private String getTocUploadUrl(String contentId) {
        return getLmsApiUriPath("/api/textbook/v1/toc/upload", "/v1/textbook/toc/upload", contentId);
    }

    private String getTocDownloadUrl(String contentId) {
        return getLmsApiUriPath("/api/textbook/v1/toc/download", "/v1/textbook/toc/download", contentId);
    }

    private String getContentId(String contentType) {
        String contentId = "do_12345";
        if ("TextBook".equalsIgnoreCase(contentType))
            contentId = TOCUtil.createTextbook(this, testContext);
        else if ("Resource".equalsIgnoreCase(contentType))
            contentId = TOCUtil.createResourceContent(this, testContext);
        else if ("TextBookWithChildren".equalsIgnoreCase(contentType))
            contentId = TOCUtil.createTextbookWithChildren(this, testContext);
        return contentId;
    }

    private Map<String, String> getTOCUrl(BaseCitrusTestRunner runner, TestContext testContext) {
        String textbookId = TOCUtil.createTextbookWithChildren(runner, testContext);
        String tocUrl = downloadTOC(runner, testContext, textbookId);
        Map<String, String> result = new HashMap<String, String>() {{
            put("contentId", textbookId);
            put("fileUrl", tocUrl);
        }};
        return result;
    }

    private String downloadTOC(BaseCitrusTestRunner runner, TestContext testContext, String textbookId) {
        String tocUrl = "";
        runner.http(
                builder ->
                        TestActionUtil.performGetTest(
                                builder,
                                LMS_ENDPOINT,
                                TEST_TOC_DOWNLOAD_VALID_TEXTBOOK_ID,
                                getTocDownloadUrl(textbookId),
                                TestActionUtil.getHeaders(true),
                                config));
        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                LMS_ENDPOINT,
                                HttpStatus.OK,
                                "$.result.textbook.tocUrl",
                                "tocUrl"));
        tocUrl = testContext.getVariable("tocUrl");
        runner.sleep(Constant.ES_SYNC_WAIT_TIME);
        return tocUrl;
    }

    private Map<String, Object> getHeader() {
        return new HashMap<String, Object>() {{
            put(Constant.X_CHANNEL_ID, config.getSunbirdDefaultChannel());
        }};
    }
}
