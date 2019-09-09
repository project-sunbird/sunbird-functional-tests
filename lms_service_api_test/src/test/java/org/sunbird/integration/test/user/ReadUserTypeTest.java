package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReadUserTypeTest extends BaseCitrusTestRunner {

    public static final String TEST_READ_USER_TYPE_FAILURE_WITHOUT_ACCESS_TOKEN =
            "testReadUserTypeFailureWithoutAccessToken";

    public static final String TEST_READ_USER_TYPE_SUCCESS_WITH_ACCESS_TOKEN =
            "testReadUserTypeSuccessWithAccessToken";

    public static final String TEMPLATE_DIR = "templates/user/type/read";

    private String getReadUserTypeUrl() {
        return getLmsApiUriPath("/api/user/v1/type/list", "/v1/user/type/list");
    }

    @DataProvider(name = "readUserTypeFailureDataProvider")
    public Object[][] readUserTypeFailureDataProvider() {

        return new Object[][] {
                new Object[] {
                        TEST_READ_USER_TYPE_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED
                }
        };
    }

    @DataProvider(name = "readUserTypeSuccessDataProvider")
    public Object[][] readUserTypeSuccessDataProvider() {
        return new Object[][] {
                new Object[] {TEST_READ_USER_TYPE_SUCCESS_WITH_ACCESS_TOKEN, true, HttpStatus.OK},
        };
    }

    @Test(dataProvider = "readUserTypeFailureDataProvider")
    @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
    @CitrusTest
    public void testReadUserTypeFailure(
            String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                getReadUserTypeUrl(),
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @Test(dataProvider = "readUserTypeSuccessDataProvider")
    @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
    @CitrusTest
    public void testReadUserTypeSuccess(
            String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                getReadUserTypeUrl(),
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }
}
