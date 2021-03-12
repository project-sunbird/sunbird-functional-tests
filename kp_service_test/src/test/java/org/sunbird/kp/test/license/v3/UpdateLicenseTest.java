package org.sunbird.kp.test.license.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.util.LicenseUtil;
import org.sunbird.kp.test.util.TestSetupUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Integration Test Cases for Update License API
 *
 * @author rheashalomannfernandes
 *
 * Number of Test cases :
 * Last Updated the Test cases on:
 */
public class UpdateLicenseTest extends BaseCitrusTestRunner {
    private static final String TEMPLATE_DIR = "templates/license/v3/update";
    // This map populates the id's and directories
    private static Map<String, String> dirIdMap = new HashMap<>();

    @Test(dataProvider = "updateLicenseWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testUpdateResourceContentWithValidRequest(String testName) {
        String identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        this.variable("identifier", identifier);
        dirIdMap.put(testName, identifier);
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_LICENSE + identifier,
                null, REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier, null,
                HttpStatus.OK, null, VALIDATE_JSON);

    }

    @Test(dataProvider = "updateLicenseWithInvalidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testUpdateResourceContentWithInvalidRequest(String testName) {
        String identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        this.variable("identifier", identifier);
        dirIdMap.put(testName, identifier);
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_LICENSE + identifier,
                null, REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.BAD_REQUEST, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier, null,
                HttpStatus.OK, null, VALIDATE_JSON);
    }

    @Test(dataProvider = "updateLicenseWithNotFoundRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testUpdateResourceContentWithNotFoundRequest(String testName) {
        String identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        this.variable("identifier", identifier);
        dirIdMap.put(testName, identifier);
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_LICENSE + "invalid123",
                null, REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier, null,
                HttpStatus.OK, null, VALIDATE_JSON);
    }


    @DataProvider(name = "updateLicenseWithValidRequest")
    public Object[][] updateLicenseWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_VALID_REQUEST
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_NEW_METADATA
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_METADATA_NAME_CASE_MISMATCH
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_CODE
                },
        };
    }

    @DataProvider(name = "updateLicenseWithInvalidRequest")
    public Object[][] updateLicenseWithInvalidRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_STATUS
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_DATA_TYPE_MISMATCH
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_IDENTIFIER
                },
        };
    }

        @DataProvider(name = "updateLicenseWithNotFoundRequest")
        public Object[][] updateLicenseWithNotFoundRequest() {
            return new Object[][]{

                    new Object[]{
                            LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_INVALID_IDENTIFIER
                    },
            };
        }

}
