package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public class UpdateContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/update";

    @Test(dataProvider = "updateValidResourceContent")
    @CitrusParameters({"testName", "mimeType", "workflow"})
    @CitrusTest
    public void testValidUpdateResourceContent(
            String testName, String mimeType, String workflow) {
        getAuthToken(this, Constant.CREATOR);
        Map<String, Object> map = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null);
        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_CONTENT + contentId, null,
                REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_CONTENT + contentId,
                null, HttpStatus.OK, null, VALIDATE_JSON);
    }

    @Test(dataProvider = "updateInvalidResourceContent")
    @CitrusParameters({"testName", "mimeType", "workflow"})
    @CitrusTest
    public void testInvalidUpdateResourceContent(
            String testName, String mimeType, String workflow) {
        getAuthToken(this, Constant.CREATOR);
        Map<String, Object> map = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null);
        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_CONTENT + contentId, null,
                REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.BAD_REQUEST, null, RESPONSE_JSON);
    }

    @Test(dataProvider = "updateNotFoundResourceContent")
    @CitrusParameters({"testName", "mimeType", "needImage", "workflow"})
    @CitrusTest
    public void testNotFoundUpdateResourceContent(
            String testName, String mimeType, Boolean needImage, String workflow) {
        getAuthToken(this, Constant.CREATOR);
        Map<String, Object> map = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null);
        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        contentId = needImage ? contentId + IMAGE_SUFFIX : contentId;
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_CONTENT + contentId, null,
                REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND, null, RESPONSE_JSON
        );
    }

    @Test(dataProvider = "updateImageResourceContent")
    @CitrusParameters({"testName", "mimeType", "workflow"})
    @CitrusTest
    public void testImageUpdateResourceContent(
            String testName, String mimeType, String workflow) {
        getAuthToken(this, Constant.CREATOR);
        Map<String, Object> map = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null);
        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_CONTENT + contentId, null,
                REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_CONTENT + contentId,
                null, HttpStatus.OK, null, "validateLive.json");
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_CONTENT + contentId + "?mode=edit",
                null, HttpStatus.OK, null, "validateImage.json");
    }

    @Test(dataProvider = "updateEcmlResource")
    @CitrusParameters({"testName", "mimeType", "workflow"})
    @CitrusTest
    public void testEcmlUpdateResourceContent(
            String testName, String mimeType, String workflow) {
        getAuthToken(this, Constant.CREATOR);
        Map<String, Object> map = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null);
        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_CONTENT + contentId, null,
                REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_CONTENT + contentId,
                null, HttpStatus.OK, null, "validateLive.json");
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_CONTENT + contentId + "?mode=edit",
                null, HttpStatus.OK, null, "validateImage.json");
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_CONTENT + contentId + "?mode=edit&fields=body",
                null, HttpStatus.OK, null, "validateFields.json");
    }

    @Test
    @CitrusTest
    public void testUpdateContentHavingMetadataResources() throws Exception {
        String CREATE_RESOURCE_CONTENT_WITH_RESOURCES = "{\n" + "\"request\": {\n" + "\"content\": {\n" + "\"identifier\": \"KP_FT_"+System.currentTimeMillis()+"\",\n" + "\"name\": \"KP Integration Test Content\",\n" + "\"code\": \"kp.ft.resource.pdf\",\n" + "\"mimeType\": \"application/pdf\",\n" + "\"contentType\": \"Resource\",\n" + "\"resources\": [\n" + "\"Speaker\",\n" + "\"Microphone\"\n" + "]\n" + "}\n" + "}\n" +"}";
        getAuthToken(this, null);
        Map<String, Object> map = ContentUtil.createResourceContent(this, CREATE_RESOURCE_CONTENT_WITH_RESOURCES, null, null);
        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        performPatchTest(this, TEMPLATE_DIR, ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_RESOURCES, APIUrl.UPDATE_CONTENT + contentId, null,
                REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_RESOURCES, APIUrl.READ_CONTENT + contentId,
                null, HttpStatus.OK, null, VALIDATE_JSON);
    }

    @Test
    @CitrusTest
    public void testUpdateContentHavingMetadataContentCredits() throws Exception {
        String CREATE_RESOURCE_CONTENT_WITH_CONTENTCREDITS = "{\n" + "\"request\": {\n" + "\"content\": {\n" + "\"identifier\": \"KP_FT_"+System.currentTimeMillis()+"\",\n" + "\"name\": \"KP Integration Test Content\",\n" + "\"code\": \"kp.ft.resource.pdf\",\n" + "\"mimeType\": \"application/pdf\",\n" + "\"contentType\": \"Resource\",\n" + "\"contentCredits\": [\n" + "{\n" + "\"id\": \"12345\",\n" + "\"name\": \"user1\",\n" + "\"type\": \"user\"\n" + "}\n" + "]\n" + "}\n" + "}\n" +"}";
        getAuthToken(this, null);
        Map<String, Object> map = ContentUtil.createResourceContent(this, CREATE_RESOURCE_CONTENT_WITH_CONTENTCREDITS, null, null);
        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        performPatchTest(this, TEMPLATE_DIR, ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_CONTENTCREDITS, APIUrl.UPDATE_CONTENT + contentId, null,
                REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_CONTENTCREDITS, APIUrl.READ_CONTENT + contentId,
                null, HttpStatus.OK, null, VALIDATE_JSON);
    }

    @DataProvider(name = "updateValidResourceContent")
    public Object[][] updateValidResourceContent() {
        return new Object[][]{
                //Valid Requests (200) are here
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST, "application/pdf", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_ECML_CONTENT_WITH_VALID_REQUEST, "application/vnd.ekstep.ecml-archive", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_HTML_CONTENT_WITH_VALID_REQUEST, "application/vnd.ekstep.html-archive", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_H5P_CONTENT_WITH_VALID_REQUEST, "application/vnd.ekstep.h5p-archive", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_YOUTUBE_CONTENT_WITH_VALID_REQUEST, "video/x-youtube", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_NEW_FRAMEWORK, "application/pdf", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_STATUS_REVIEW, "application/pdf", "contentInReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_STATUS_FLAGREVIEW, "application/pdf", "contentInFlagReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_WITH_VALID_ECML_BODY, "application/pdf", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_VALID_LICENSE, "application/pdf", "contentInDraft"
                }
        };
    }

    @DataProvider(name = "updateInvalidResourceContent")
    public Object[][] updateInvalidResourceContent() {
        return new Object[][]{
                // TODO: Uncomment in KP-2.0
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_MIMETYPE, "application/pdf", "contentInDraft"
//                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_MEDIATYPE, "application/pdf", "contentInDraft"
//                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_CONTENT_TYPE, "application/pdf", "contentInDraft"
//                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_CONTENT_STATUS_FLAGGED, "application/pdf", "contentInFlagged"
//                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_BLANK_VERSION_KEY, "application/pdf", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_INVALID_VERSION_KEY, "application/pdf", "contentInDraft"
                },
                /*new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_CHANGED_STATUS, "application/pdf", "contentInDraft"
                },*/
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_INVALID_METADATA, "application/pdf", "contentInDraft"
                },

                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_INVALID_CONTENT_TYPE, "application/pdf", "contentInDraft"
                },
                /*new Object[]{
                        ContentV3Scenario.TEST_UPDATE_WITH_SYSTEM_PROPERTY, "application/pdf", "contentInDraft"
                },*/
                // TODO: Uncomment in KP-2.0
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_CONTENT_IN_RETIRED, "application/pdf", "contentRetired"
//                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_WITH_DIALCODES, "application/pdf", "contentInDraft"
//                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_WITH_RESERVED_DIALCODES, "application/pdf", "contentInDraft"
//                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_WITH_INVALID_FRAMEWORK, "application/pdf", "contentInDraft"
//                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_WITH_CORRECT_IDENTIFIER_IN_REQUEST, "application/pdf", "contentInDraft"
//                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_WITH_INCORRECT_IDENTIFIER_IN_REQUEST, "application/pdf", "contentInDraft"
//                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_WITH_INVALID_FORMAT_RESERVED_DIALCODES, "application/pdf", "contentInDraft"
                },
                // TODO: Uncomment in KP-2.0
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_WITH_INVALID_ECML_BODY, "application/pdf", "contentInDraft"
//                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_INVALID_LICENSE_TYPE, "application/pdf", "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_INVALID_LICENSE_VALUE, "application/pdf", "contentInDraft"
                }
        };
    }

    @DataProvider(name = "updateNotFoundResourceContent")
    public Object[][] updateNotFoundResourceContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_NOT_FOUND_REQUEST, null, false, "contentInDraft"
                },
//
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_WITH_IMAGE_ID, "application/pdf", true, "contentInDraft"
//                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_AFTER_DISCARD, "application/pdf", false, "contentDiscarded"
                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_FOR_PUBLISHED_CONTENT_WITH_IMAGE_ID, "application/pdf", true, "contentInLive"
//                },
        };
    }

    @DataProvider(name = "updateImageResourceContent")
    public Object[][] updateImageResourceContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_IN_LIVE, "application/pdf", "contentInLive"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_IN_LIVE_WITH_IMAGE, "application/pdf", "contentInLiveImageDraft"
                }
        };
    }

    @DataProvider(name = "updateEcmlResource")
    public Object[][] updateEcmlResource(){
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_ECML_BODY_FOR_PUBLISHED_CONTENT, "application/vnd.ekstep.ecml-archive", "contentInLive"
                }
        };
    }

}
