package org.sunbird.integration.test.badge;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.IssuerUtil;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateBadgeClassTest extends BaseCitrusTestRunner {
  private static final String BT_TEST_NAME_CREATE_ISSUER_SUCCESS = "testCreateIssuerSuccess";
  private static final String BT_CREATE_ISSUER_TEMPLATE_DIR = "templates/badge/issuer/create";

  private static final String TEST_NAME_CREATE_BADGE_CLASS_SUCCESS_WITH_TYPE_USER =
      "testCreateBadgeClassSuccessWithTypeUser";
  private static final String
      TEST_NAME_CREATE_BADGE_CLASS_SUCCESS_WITH_TYPE_USER_AND_MULTIPLE_ROLES =
          "testCreateBadgeClassSuccessWithTypeUserAndMultipleRoles";

  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_ISSUER_ID =
      "testCreateBadgeClassFailureWithoutIssuerId";
  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_NAME =
      "testCreateBadgeClassFailureWithoutName";
  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_CRITERIA =
      "testCreateBadgeClassFailureWithoutCriteria";
  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_DESCRIPTION =
      "testCreateBadgeClassFailureWithoutDescription";
  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_ROOT_ORG_ID =
      "testCreateBadgeClassFailureWithoutRootOrgId";
  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_ROLES =
      "testCreateBadgeClassFailureWithoutRoles";
  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_TYPE =
      "testCreateBadgeClassFailureWithoutType";
  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_SUBTYPE =
      "testCreateBadgeClassFailureWithoutSubtype";

  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITH_INVALID_ROOT_ORG_ID =
      "testCreateBadgeClassFailureWithInvalidRootOrgId";
  private static final String TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITH_INVALID_ROLES =
      "testCreateBadgeClassFailureWithInvalidRoles";

  private static final String TEMPLATE_DIR = "templates/badge/class/create";

  private String getCreateBadgeClassUrl() {
    return getLmsApiUriPath("/api/badging/v1/issuer/badge/create", "/v1/issuer/badge/create");
  }

  @DataProvider(name = "createBadgeClassDataProviderSuccess")
  public Object[][] createBadgeClassDataProviderSuccess() {
    return new Object[][] {
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_SUCCESS_WITH_TYPE_USER},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_SUCCESS_WITH_TYPE_USER_AND_MULTIPLE_ROLES}
    };
  }

  @DataProvider(name = "createBadgeClassDataProviderFailure")
  public Object[][] createBadgeClassDataProviderFailure() {
    return new Object[][] {
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_ISSUER_ID, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_NAME, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_CRITERIA, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_DESCRIPTION, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_ROOT_ORG_ID, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_ROLES, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_TYPE, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITHOUT_SUBTYPE, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITH_INVALID_ROOT_ORG_ID, false},
      new Object[] {TEST_NAME_CREATE_BADGE_CLASS_FAILURE_WITH_INVALID_ROLES, true}
    };
  }

 // @Test(dataProvider = "createBadgeClassDataProviderSuccess")
  @CitrusParameters({"testName"})
 // @CitrusTest
  public void testCreateBadgeClassSuccess(String testName) {
    beforeTest(true, true);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateBadgeClassUrl(),
        REQUEST_FORM_DATA,
        null,
        false,
        HttpStatus.OK,
        RESPONSE_JSON);
    afterTest(true);
  }

  //@Test(dataProvider = "createBadgeClassDataProviderFailure")
  @CitrusParameters({"testName", "canCreateOrg"})
 // @CitrusTest
  public void testCreateBadgeClassFailure(String testName, Boolean canCreateOrg) {
    beforeTest(canCreateOrg, false);
    performMultipartTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateBadgeClassUrl(),
        REQUEST_FORM_DATA,
        null,
        false,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }

  private void beforeTest(Boolean canCreateOrg, Boolean canCreateIssuer) {
    if (canCreateOrg) {
      getAuthToken(this, true);
      variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
      OrgUtil.getRootOrgId(this, testContext);
    }
    if (canCreateIssuer) {
      IssuerUtil.createIssuer(
          this,
          testContext,
          config,
          BT_CREATE_ISSUER_TEMPLATE_DIR,
          BT_TEST_NAME_CREATE_ISSUER_SUCCESS,
          HttpStatus.OK);
    }
  }

  private void afterTest(boolean isIssuerCreated) {
    if (isIssuerCreated)
      IssuerUtil.deleteIssuer(
          this,
          testContext,
          config,
          (String) testContext.getVariables().get(Constant.EXTRACT_VAR_ISSUER_ID));
  }
}
