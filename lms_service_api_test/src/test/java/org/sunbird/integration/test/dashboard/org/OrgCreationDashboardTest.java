package org.sunbird.integration.test.dashboard.org;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrgCreationDashboardTest extends BaseCitrusTestRunner {

  private static final String TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testOrgCreationDashboardFailureWithoutAccessToken";
  private static final String TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_ORG_ID =
      "testOrgCreationDashboardFailureWithInvalidOrgId";
  private static final String TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD =
      "testOrgCreationDashboardFailureWithInvalidTimePeriod";

  private static final String TEST_NAME_ORG_CREATION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID =
      "testOrgCreationDashboardSuccessWithValidOrgId";

  private static String INVALID_ROOT_ORG_ID = "invalidRootOrg1";

  private static final String TEMPLATE_DIR = "templates/dashboard/org/creation";

  private static String rootOrgId = null;

  private String getOrgCreationUrl(Boolean useValidOrgId, String pathParam) {
    String pathWithOrgId;
    if (useValidOrgId) {
      pathWithOrgId = rootOrgId + pathParam;
    } else {
      pathWithOrgId = INVALID_ROOT_ORG_ID + pathParam;
    }
    return getLmsApiUriPath(
        "/api/dashboard/v1/creation/org", "/v1/dashboard/creation/org", pathWithOrgId);
  }

  @DataProvider(name = "orgCreationFailureDataProvider")
  public Object[][] orgCreationFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITHOUT_ACCESS_TOKEN,
        false,
        HttpStatus.UNAUTHORIZED,
        false,
        ""
      },
      new Object[] {
        TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_ORG_ID,
        true,
        HttpStatus.BAD_REQUEST,
        false,
        "?period=1d"
      },
      new Object[] {
        TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD,
        true,
        HttpStatus.BAD_REQUEST,
        true,
        "?period=1"
      }
    };
  }

  @Test(dataProvider = "orgCreationFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode", "canCreateOrg", "pathParam"})
  @CitrusTest
  public void testOrgCreationFailure(
      String testName,
      boolean isAuthRequired,
      HttpStatus httpStatusCode,
      boolean canCreateOrg,
      String pathParam) {
    getTestCase().setName(testName);

    beforeTest(canCreateOrg, isAuthRequired);

    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOrgCreationUrl(canCreateOrg, pathParam),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "orgCreationSuccessDataProvider")
  public Object[][] orgCreationSuccessDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_ORG_CREATION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID,
        true,
        HttpStatus.OK,
        true,
        "?period=7d"
      },
    };
  }

  @Test(dataProvider = "orgCreationSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode", "canCreateOrg", "pathParam"})
  @CitrusTest
  public void testOrgCreationSuccess(
      String testName,
      boolean isAuthRequired,
      HttpStatus httpStatusCode,
      boolean canCreateOrg,
      String pathParam) {
    getTestCase().setName(testName);

    beforeTest(canCreateOrg, isAuthRequired);

    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOrgCreationUrl(canCreateOrg, pathParam),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest(boolean canCreateOrg, boolean isAuthRequired) {
    getAuthToken(this, isAuthRequired);
    variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
    variable("rootOrgExternalId", OrgUtil.getRootOrgExternalId());

    if (canCreateOrg) {
      if (rootOrgId == null) {
        rootOrgId = OrgUtil.getRootOrgId(this, testContext);
      } else {
        testContext.setVariable("organisationId", rootOrgId);
      }
    }
  }
}
