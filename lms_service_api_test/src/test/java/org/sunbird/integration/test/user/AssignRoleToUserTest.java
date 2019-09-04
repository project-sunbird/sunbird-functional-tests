package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AssignRoleToUserTest extends BaseCitrusTestRunner {

  private static final String TEST_ASSIGN_ROLE_USER_FAILURE_WITH_INVALID_USER_ID =
      "testAssignRoleUserFailureWithInvalidUserId";
  private static final String TEST_ASSIGN_ROLE_USER_FAILURE_WITH_INVALID_ORG_ID =
      "testAssignRoleUserFailureWithInvalidOrgId";

  private static final String TEST_ASSIGN_ROLE_USER_FAILURE_WITH_INVALID_ROLE =
      "testAssignRoleUserFailureWithInvalidRole";
  private static final String TEST_ASSIGN_ROLE_USER_FAILURE_WITH_USER_NOT_ADDED_TO_ORG =
      "testAssignRoleUserFailureWithUserNotAddedToOrg";
  private static final String TEST_ASSIGN_ROLE_USER_SUCCESS_WITH_USER_ALREADY_ADDED_TO_ORG =
      "testAssignRoleUserFailureWithUserAlreadyAddedToOrg";

  public static final String TEMPLATE_DIR = "templates/user/role";

  public static final String TEMPLATE_ORG_DIR = "templates/user/org";
  private static final String TEST_ASSIGN_USER_TO_ORG_SUCCESS = "testAssignUserToOrgSuccess";

  private String getAssignRoleToUserUrl() {
    return getLmsApiUriPath("/api/user/v1/role/assign", "/v1/user/assign/role");
  }

  @DataProvider(name = "assignRoleToUserFailureDataProvider")
  public Object[][] assignRoleToUserFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_ASSIGN_ROLE_USER_FAILURE_WITH_USER_NOT_ADDED_TO_ORG, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {TEST_ASSIGN_ROLE_USER_FAILURE_WITH_INVALID_ROLE, true, HttpStatus.BAD_REQUEST},
      new Object[] {
        TEST_ASSIGN_ROLE_USER_FAILURE_WITH_INVALID_USER_ID, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {TEST_ASSIGN_ROLE_USER_FAILURE_WITH_INVALID_ORG_ID, true, HttpStatus.NOT_FOUND},
    };
  }

  @Test(dataProvider = "assignRoleToUserFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testAssignRoleToUserFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    createUser();
    createOrg();
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getAssignRoleToUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test()
  @CitrusTest
  public void testAssignRoleToUserSuccessWithUserAlreadyBelongToOrg() {
    getTestCase().setName(TEST_ASSIGN_ROLE_USER_SUCCESS_WITH_USER_ALREADY_ADDED_TO_ORG);
    getAuthToken(this, true);
    addUserToOrg();
    performPostTest(
        this,
        TEMPLATE_DIR,
        TEST_ASSIGN_ROLE_USER_SUCCESS_WITH_USER_ALREADY_ADDED_TO_ORG,
        getAssignRoleToUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void createUser() {
    UserUtil.getUserId(this, testContext);
    variable("userId", testContext.getVariable("userId"));
  }

  private void createOrg() {
    variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
    OrgUtil.getRootOrgId(this, testContext);
    variable("organisationId", testContext.getVariable("organisationId"));
  }

  private void addUserToOrg() {
    createOrg();
    createUser();
    variable("userId", testContext.getVariable("userId"));
    variable("organisationId", testContext.getVariable("organisationId"));
    OrgUtil.addUserToOrg(this, TEMPLATE_ORG_DIR, TEST_ASSIGN_USER_TO_ORG_SUCCESS);
  }
}
