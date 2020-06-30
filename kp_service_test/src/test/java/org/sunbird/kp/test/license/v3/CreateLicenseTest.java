package org.sunbird.kp.test.license.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.LicenseUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;

import javax.ws.rs.core.MediaType;

/**
 * Integration Test Cases for License Create API
 *
 * @author Neha Verma
 * <p>
 * Number of testcases for Create : 11
 * Last Count Update: 15-11-2019
 */

public class CreateLicenseTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/license/v3/create";
    private String identifier;

    @Test(dataProvider = "createLicenseWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testCreateLicenseWithValidRequest(String testName) {
        identifier = "kp_ft_" + generateRandomDigits(9);
        this.variable("identifier", identifier);
        getAuthToken(this, Constant.CREATOR);
        performPostTest(this, TEMPLATE_DIR, testName, APIUrl.CREATE_LICENSE, null,
                REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        //Read The Content And Validate
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier, null,
                HttpStatus.OK, null, VALIDATE_JSON);
    }

    @Test(dataProvider = "createLicenseWithInvalidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void createLicenseWithInvalidRequest(String testName) {
        identifier = "kp_ft_" + generateRandomDigits(9);
        this.variable("identifier", identifier);
        getAuthToken(this, Constant.CREATOR);
        performPostTest(this, TEMPLATE_DIR, testName, APIUrl.CREATE_LICENSE, null, REQUEST_JSON,
                MediaType.APPLICATION_JSON, HttpStatus.BAD_REQUEST, null, RESPONSE_JSON);
    }

    @Test(dataProvider = "createLicenseWithSluggifiedName")
    @CitrusParameters("testName")
    @CitrusTest
    public void createLicenseWithSluggifiedName(String testName) {
        identifier = "kp_ft_" + generateRandomDigits(9);
        this.variable("identifier", identifier);
        getAuthToken(this, Constant.CREATOR);
        performPostTest(this, TEMPLATE_DIR, testName, APIUrl.CREATE_LICENSE, null, REQUEST_JSON,
                MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        //Read The Content And Validate
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier, null,
                HttpStatus.OK, null, VALIDATE_JSON);
    }

    @Test(dataProvider = "createLicenseWithDuplicateIdentifier")
    @CitrusParameters("testName")
    @CitrusTest
    public void createLicenseWithDuplicateIdentifier(String testName) {
        identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        this.variable("identifier", identifier);
        getAuthToken(this, Constant.CREATOR);
        performPostTest(this, TEMPLATE_DIR, testName, APIUrl.CREATE_LICENSE, null, REQUEST_JSON,
                MediaType.APPLICATION_JSON, HttpStatus.BAD_REQUEST, null, RESPONSE_JSON);
    }

    @DataProvider(name = "createLicenseWithValidRequest")
    public Object[][] createLicenseWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITH_VALID_REQUEST
                },
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITH_NEW_METADATA
                },
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITHOUT_URL
                },
        };
    }

    @DataProvider(name = "createLicenseWithInvalidRequest")
    public Object[][] createLicenseWithInvalidRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITHOUT_NAME
                },
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITH_METADATA_NAME_CASE_MISMATCH
                },
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITH_DATA_TYPE_MISMATCH
                },
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITH_IDENTIFIER
                },
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITH_STATUS
                }
        };
    }

    @DataProvider(name = "createLicenseWithSluggifiedName")
    public Object[][] createLicenseWithSluggifiedName() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITH_SLUGGIFIED_NAME
                }
        };
    }

    @DataProvider(name = "createLicenseWithDuplicateIdentifier")
    public Object[][] createLicenseWithDuplicateIdentifier() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_CREATE_LICENSE_WITH_DUPLICATE_IDENTIFIER
                }
        };
    }

}
