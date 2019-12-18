package org.sunbird.integration.test.badge;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.BadgeAssertionUtil;
import org.sunbird.common.action.BadgeClassUtil;
import org.sunbird.common.action.IssuerUtil;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.TestActionUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetBadgeAssertionTest extends BaseCitrusTestRunner {

  private static final String BT_TEST_NAME_CREATE_ISSUER_SUCCESS = "testCreateIssuerSuccess";
  private static final String BT_CREATE_ISSUER_TEMPLATE_DIR = "templates/badge/issuer/create";

  private static final String BT_TEST_NAME_CREATE_BADGE_CLASS_SUCCESS =
      "testCreateBadgeClassSuccessWithTypeUser";
  private static final String BT_CREATE_BADGE_CLASS_TEMPLATE_DIR = "templates/badge/class/create";

  private static final String BT_TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS =
      "testCreateBadgeAssertionSuccessUserWithoutEvidence";
  private static final String BT_CREATE_BADGE_ASSERTION_TEMPLATE_DIR =
      "templates/badge/assertion/create";

  private static final String TEST_NAME_GET_BADGE_ASSERTION_FAILURE_WITH_INVALID_ASSERTION_ID =
      "testGetBadgeAssertionFailureWithInvalidAssertionId";

  private static final String TEST_NAME_GET_BADGE_ASSERTION_SUCCESS_USER_BADGE_TYPE =
      "testGetBadgeAssertionSuccesUserBadgeType";

  private static final String TEMPLATE_DIR = "templates/badge/assertion/get";

  private String getReadBadgeAssertionUrl(String pathParam) {
    return getLmsApiUriPath(
        "/api/badging/v1/issuer/badge/assertion/read",
        "/v1/issuer/badge/assertion/read",
        pathParam);
  }

  @DataProvider(name = "getBadgeAssertionDataProviderSuccess")
  public Object[][] getBadgeAssertionDataProviderSuccess() {
    return new Object[][] {new Object[] {TEST_NAME_GET_BADGE_ASSERTION_SUCCESS_USER_BADGE_TYPE}};
  }

  @DataProvider(name = "getBadgeAssertionDataProviderFailure")
  public Object[][] getBadgeAssertionDataProviderFailure() {
    return new Object[][] {
      new Object[] {TEST_NAME_GET_BADGE_ASSERTION_FAILURE_WITH_INVALID_ASSERTION_ID, "invalid-id"}
    };
  }

  //@Test(dataProvider = "getBadgeAssertionDataProviderSuccess")
  @CitrusParameters({"testName"})
  //@CitrusTest
  public void testGetBadgeAssertionSuccess(String testName) {
    beforeTest(testName);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getReadBadgeAssertionUrl(TestActionUtil.getVariable(testContext, "assertionId")),
        false,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  //@Test(dataProvider = "getBadgeAssertionDataProviderFailure")
  @CitrusParameters({"testName", "pathParam"})
  //@CitrusTest
  public void testGetBadgeAssertionFailure(String testName, String pathParam) {
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getReadBadgeAssertionUrl(pathParam),
        false,
        HttpStatus.NOT_FOUND,
        RESPONSE_JSON);
  }

  private void beforeTest(String testName) {

    getTestCase().setName(testName);

    getAuthToken(this, true);
    UserUtil.getUserId(this, testContext);

    IssuerUtil.createIssuer(
        this,
        testContext,
        config,
        BT_CREATE_ISSUER_TEMPLATE_DIR,
        BT_TEST_NAME_CREATE_ISSUER_SUCCESS,
        HttpStatus.OK);

    String orgId =
        OrgUtil.getSearchOrgId(this, testContext, System.getenv("sunbird_default_channel"));
    variable("organisationId", orgId);
    BadgeClassUtil.createBadgeClass(
        this,
        testContext,
        config,
        BT_CREATE_BADGE_CLASS_TEMPLATE_DIR,
        BT_TEST_NAME_CREATE_BADGE_CLASS_SUCCESS,
        HttpStatus.OK);

    BadgeAssertionUtil.createBadgeAssertion(
        this,
        testContext,
        config,
        BT_CREATE_BADGE_ASSERTION_TEMPLATE_DIR,
        BT_TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS,
        HttpStatus.OK);
  }
}
