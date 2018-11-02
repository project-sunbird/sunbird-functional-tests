package org.sunbird.integration.test.org;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

public class AddUserToOrgTest extends BaseCitrusTestRunner {

  public static final String TEST_ADD_USER_TO_ORG_FAILURE_WITH_EMPTY_ROLE_ARRAY =
      "testAddUserToOrgSuccessWithEmptyRoleArray";

  public static final String TEST_NAME_ADD_USER_TO_ORG_FAILURE_WITH_INVALID_USER_ID =
      "testAddUserToOrgFailureWithInvalidUserId";
  public static final String TEST_NAME_ADD_USER_TO_ORG_FAILURE_WITH_INVALID_ORG_ID =
      "testAddUserToOrgFailureWithInvalidOrgId";

  public static final String TEMPLATE_DIR = "templates/organisation/user/add";

  private String getAddUserToOrgUrl() {
    return getLmsApiUriPath("/api/org/v1/member/add", "v1/org/member/add");
  }

  @DataProvider(name = "addUserToOrgDataProvider")
  public Object[][] addUserToOrgFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_ADD_USER_TO_ORG_FAILURE_WITH_EMPTY_ROLE_ARRAY, true, HttpStatus.OK},
      new Object[] {TEST_NAME_ADD_USER_TO_ORG_FAILURE_WITH_INVALID_USER_ID, false, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_NAME_ADD_USER_TO_ORG_FAILURE_WITH_INVALID_ORG_ID, false, HttpStatus.BAD_REQUEST}
    };
  }

  @Test(dataProvider = "addUserToOrgDataProvider")
  @CitrusParameters({"testName", "getUserId", "httpStatus"})
  @CitrusTest
  public void testAddUserToOrgFailure(String testName, boolean getUserId, HttpStatus httpStatus) {
    getAuthToken(this, true);
    beforeTest(getUserId);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getAddUserToOrgUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        httpStatus,
        RESPONSE_JSON);
  }

  void beforeTest(boolean getUserId) {

    if (getUserId) {
      UserUtil.getUserId(this, testContext);
      variable("userId", testContext.getVariable("userId"));
    } else {
      variable("userId", "citrus:concat('user_',citrus:randomNumber(10))");
    }
  }

}
