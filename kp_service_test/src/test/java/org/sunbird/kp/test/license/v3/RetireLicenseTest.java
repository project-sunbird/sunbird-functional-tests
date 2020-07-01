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

public class RetireLicenseTest extends BaseCitrusTestRunner {
    private static final String TEMPLATE_DIR = "templates/license/v3/retire";
    // This map populates the id's and directories
    private static Map<String, String> dirIdMap = new HashMap<>();

    @Test(dataProvider = "retireLicenseWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testRetireResourceContentWithValidRequest(String testName) {
        String identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        this.variable("identifier", identifier);
        dirIdMap.put(testName, identifier);
        performDeleteTest(this, TEMPLATE_DIR, testName, APIUrl.RETIRE_LICENSE + identifier,
                null, REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
        performGetTest(this, TEMPLATE_DIR, testName, APIUrl.READ_LICENSE + identifier, null,
                HttpStatus.OK, null, VALIDATE_JSON);

    }

    @Test(dataProvider = "retireLicenseWithNotFoundRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testRetireResourceContentWithNotFoundRequest(String testName) {
        String identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        this.variable("identifier", identifier);
        dirIdMap.put(testName, identifier);
        performDeleteTest(this, TEMPLATE_DIR, testName, APIUrl.RETIRE_LICENSE + "invalid123",
                null, REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.NOT_FOUND, null, RESPONSE_JSON);
    }


    @DataProvider(name = "retireLicenseWithValidRequest")
    public Object[][] retireLicenseWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_RETIRE_LICENSE_WITH_VALID_REQUEST
                },
        };
    }

    @DataProvider(name = "retireLicenseWithNotFoundRequest")
    public Object[][] retireLicenseWithNotFoundRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_RETIRE_LICENSE_WITH_INVALID_IDENTIFIER
                },
        };
    }

}
