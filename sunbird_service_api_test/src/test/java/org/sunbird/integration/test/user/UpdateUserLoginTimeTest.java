package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

public class UpdateUserLoginTimeTest extends BaseCitrusTestRunner {

    public static final String TEST_UPDATE_USER_LOGIN_TIME_FAILURE_WITHOUT_ACCESS_TOKEN =
            "testUpdateUserLoginTimeFailureWithoutAccessToken";

    public static final String TEST_UPDATE_USER_LOGIN_TIME_SUCCESS_WITH_INVALID_USER_ID =
            "testUpdateUserLoginTimeSuccessWithInvalidUserId";
    public static final String TEST_UPDATE_USER_LOGIN_TIME_SUCCESS_WITH_VALID_USER_ID =
            "testUpdateUserLoginTimeSuccessWithValidUserId";
    public static final String TEMPLATE_DIR = "templates/user/loginTime/update";

    private String getUpdaterUserLoginTimeUrl() {
        return getLmsApiUriPath("/api/user/v1/update/logintime", "/v1/user/update/logintime");
    }

    @DataProvider(name = "updateUserLoginTimeFailureDataProvider")
    public Object[][] updateUserLoginTimeFailureDataProvider() {

        return new Object[][]{
                new Object[]{
                        TEST_UPDATE_USER_LOGIN_TIME_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED
                }
        };
    }

    @Test(dataProvider = "updateUserLoginTimeFailureDataProvider")
    @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
    @CitrusTest
    public void testUserUpdateLoginTimeFailure(
            String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        performPatchTest(
                this,
                TEMPLATE_DIR,
                testName,
                getUpdaterUserLoginTimeUrl(),
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @DataProvider(name = "updateUserLoginTimeSuccessDataProvider")
    public Object[][] updateUserLoginTimeSuccessDataProvider() {

        return new Object[][]{
                new Object[]{TEST_UPDATE_USER_LOGIN_TIME_SUCCESS_WITH_VALID_USER_ID, true, HttpStatus.OK, true},
                new Object[]{TEST_UPDATE_USER_LOGIN_TIME_SUCCESS_WITH_INVALID_USER_ID, true, HttpStatus.OK, false},
        };
    }

    @Test(dataProvider = "updateUserLoginTimeSuccessDataProvider")
    @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode", "isUserIdReq"})
    @CitrusTest
    public void testUpdateUserLoginTimeSuccess(
            String testName, boolean isAuthRequired, HttpStatus httpStatusCode, boolean isUserIdReq) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        beforeTest(isUserIdReq);
        performPatchTest(
                this,
                TEMPLATE_DIR,
                testName,
                getUpdaterUserLoginTimeUrl(),
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    private void beforeTest(boolean isUserIdReq) {

        if (isUserIdReq) {
            UserUtil.getUserId(this, testContext);
            variable("userId", testContext.getVariable("userId"));
        } else {
            variable("userId", "INVALID");
        }
    }
}
