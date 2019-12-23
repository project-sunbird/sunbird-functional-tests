package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.util.CollectionUtil;
import org.sunbird.kp.test.util.CollectionUtilPayload;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.TestSetupUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Integration Test Cases for Get Hierarchy API
 *
 * @author Neha Verma
 *
 * Number of testcases for get : 16
 * Last Count Update: 17-12-2019
 */
public class getHierarchyTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/hierarchy/get";
    private static final String MODE = "?mode=";
    private String identifier;
    private static Map<String, String> dirIdMap = new HashMap<>();

//    @AfterClass
//    public static void populateAssertionData() {
//        TestSetupUtil.createDirectoriesForTestCases(dirIdMap, "response.json", TEMPLATE_DIR);
//    }

    @Test(dataProvider = "getHierarchyWithValidRequest")
    @CitrusParameters ({"testName", "workFlowStatus", "payload", "collectionType", "resourceCount", "publish"})
    @CitrusTest
    public void getHierarchyWithValidRequest(String testName, String workFlowStatus, String payload, String collectionType, Integer resourceCount, Boolean publish) {
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection(workFlowStatus, this, new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/vnd.ekstep.ecml-archive");
        identifier = (String) collectionMap.get("content_id");
        if(publish){
            ContentUtil.publishContent(this, null, "public", identifier, null);
        }
        this.variable("rootId", identifier);
        //dirIdMap.put(testName, identifier);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.READ_CONTENT_HIERARCHY + identifier,
                null,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "getHierarchyWithInvalidRequest")
    @CitrusParameters ({"testName", "workFlowStatus", "payload", "collectionType", "resourceCount", "rootId"})
    @CitrusTest
    public void getHierarchyWithInvalidRequest(String testName, String workFlowStatus, String payload, String collectionType, Integer resourceCount, String rootId) {
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection(workFlowStatus, this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/vnd.ekstep.ecml-archive");
        identifier = (String) collectionMap.get("content_id");
        ContentUtil.publishContent(this, null, "public", identifier, null);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.READ_CONTENT_HIERARCHY + rootId,
                null,
                HttpStatus.NOT_FOUND,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "getHierarchyWithMode")
    @CitrusParameters ({"testName", "workFlowStatus", "payload", "collectionType", "resourceCount", "mode"})
    @CitrusTest
    public void getHierarchyWithMode(String testName, String workFlowStatus, String payload, String collectionType, Integer resourceCount, String mode) {
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection(workFlowStatus, this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/vnd.ekstep.ecml-archive");
        identifier = (String) collectionMap.get("content_id");
        this.variable("rootId", identifier);
        //dirIdMap.put(testName, identifier);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.READ_CONTENT_HIERARCHY + identifier + MODE + mode,
                null,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "getHierarchyWithDifferentStatusWithoutMode")
    @CitrusParameters ({"testName", "workFlowStatus", "payload", "collectionType", "resourceCount"})
    @CitrusTest
    public void getHierarchyWithDifferentStatusWithoutMode(String testName, String workFlowStatus, String payload, String collectionType, Integer resourceCount) {
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollectionWithoutUpdateHierarchy(workFlowStatus, this,null, collectionType, 0, resourceCount, "application/vnd.ekstep.ecml-archive");
        identifier = (String) collectionMap.get("content_id");
        this.variable("rootId", identifier);
        //dirIdMap.put(testName, identifier);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.READ_CONTENT_HIERARCHY + identifier,
                null,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "getHierarchyWithDifferentStatusWithMode")
    @CitrusParameters ({"testName", "workFlowStatus", "payload", "collectionType", "resourceCount", "mode"})
    @CitrusTest
    public void getHierarchyWithDifferentStatusWithMode(String testName, String workFlowStatus, String payload, String collectionType, Integer resourceCount, String mode) {
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollectionWithoutUpdateHierarchy(workFlowStatus, this,null, collectionType, 0, resourceCount, "application/vnd.ekstep.ecml-archive");
        identifier = (String) collectionMap.get("content_id");
        this.variable("rootId", identifier);
        //dirIdMap.put(testName, identifier);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.READ_CONTENT_HIERARCHY + identifier + MODE + mode,
                null,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @DataProvider
    public static Object[][] getHierarchyWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_WITH_VALID_IDENTIFIER, "collectionUnitsInLive", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, true
                },
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_AFTER_UPDATE, "collectionUnitsInDraft", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, true
                },
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_AFTER_REVIEW, "collectionReview", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, false
                },
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_AFTER_RETIRE, "collectionRetire", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, false
                }

        };
    }

    @DataProvider
    public static Object[][] getHierarchyWithInvalidRequest() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_WITH_INVALID_IDENTIFIER, "collectionUnitsInLive", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, "KP_FT_9876543"
                }
        };
    }

    @DataProvider
    public static Object[][] getHierarchyWithMode() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_WITH_EDIT_MODE, "collectionUnitsInDraft", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, "edit"
                },
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_WITH_EMPTY_MODE, "collectionUnitsInLive", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, ""
                },
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_WITH_INVALID_MODE, "collectionUnitsInLive", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, "abc"
                },
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_WITH_NULL_MODE, "collectionUnitsInLive", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_AFTER_UPDATE_WITH_MODE, "collectionUnitsInDraft", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, "edit"
                },
                 new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_AFTER_REVIEW_WITH_MODE, "collectionReview", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, "edit"
                },
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_AFTER_RETIRE_WITH_MODE, "collectionRetire", CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, "textBook", 1, "edit"
                }
        };
    }

    @DataProvider
    public static Object[][] getHierarchyWithDifferentStatusWithoutMode() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_BEFORE_UPDATE, "collectionCreate", null, "textBook", 1
                }
        };
    }

    @DataProvider
    public static Object[][] getHierarchyWithDifferentStatusWithMode() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_GET_HIERARCHY_BEFORE_UPDATE_WITH_MODE, "collectionCreate", null, "textBook", 1, "edit"
                }
        };
    }
}
