package org.sunbird.kp.test.license.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.LicenseUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Integration Test Cases for License Read API
 *
 * @author Neha Verma
 * <p>
 * Number of testcases for Create : 5
 * Last Count Update: 15-11-2019
 */

public class ReadLicenseTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/license/v3/read";
    private String identifier;

    @Test(dataProvider = "readLicenseWithValidRequest")
    @CitrusParameters({"testName", "fields_present", "fields_value"})
    @CitrusTest
    public void testReadLicenseWithValidRequest(String testName, Boolean fields_present, String fields_value) {
        identifier = "KP_FT_" + generateRandomDigits(9);
        identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        this.variable("identifier", identifier);
        getAuthToken(this, Constant.CREATOR);
        if (fields_present) {
            performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier + "?fields=" + fields_value, null,
                    HttpStatus.OK, null, RESPONSE_JSON);
        } else {
            performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier, null,
                    HttpStatus.OK, null, RESPONSE_JSON);
        }

    }

    @Test(dataProvider = "readLicenseWithNonFoundRequest")
    @CitrusParameters({"testName", "shouldRetire"})
    @CitrusTest
    public void testReadLicenseWithInvalidRequest(String testName, Boolean shouldRetire) {
        identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        this.variable("identifier", identifier);
        getAuthToken(this, Constant.CREATOR);
        if (shouldRetire) {
            LicenseUtil.retireLicense(this, identifier, null);
            performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier, null,
                    HttpStatus.OK, null, RESPONSE_JSON);
        } else
            performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + "invalid123", null,
                    HttpStatus.NOT_FOUND, null, RESPONSE_JSON);
    }

    @DataProvider(name = "readLicenseWithNonFoundRequest")
    public Object[][] readLicenseWithNonFoundRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_READ_LICENSE_WITH_INVALID_IDENTIFIER, false
                },
                new Object[]{
                        LicenseV3Scenario.TEST_READ_LICENSE_AFTER_RETIRE, true
                },
        };
    }

    @DataProvider(name = "readLicenseWithValidRequest")
    public Object[][] readLicenseWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_READ_LICENSE_WITH_VALID_IDENTIFIER, false, null
                },
                new Object[]{
                        LicenseV3Scenario.TEST_READ_LICENSE_WITH_FIELDS, true, "name"
                },
                new Object[]{
                        LicenseV3Scenario.TEST_READ_LICENSE_WITH_INVALID_FIELDS, true, "abc"
                }
        };
    }
}
