package org.sunbird.integration.test.dashboard.org;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrgConsumptionDashboardTest extends BaseCitrusTestRunner {

  private static final String TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testOrgConsumptionDashboardFailureWithoutAccessToken";
  private static final String TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITH_INVALID_ORG_ID =
      "testOrgConsumptionDashboardFailureWithInvalidOrgId";
  private static final String TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD =
      "testOrgConsumptionDashboardFailureWithInvalidTimePeriod";

  private static final String TEST_NAME_ORG_CONSUMPTION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID =
      "testOrgConsumptionDashboardSuccessWithValidOrgId";

  private static String INVALID_ROOT_ORG_ID = "invalidRootOrg1";

  private static final String TEMPLATE_DIR = "templates/dashboard/org/consumption";

  private static String rootOrgId;

  private String getOrgConsumptionUrl(Boolean useValidOrgId, String pathParam) {
    String pathWithOrgId;
    if (useValidOrgId) {
      pathWithOrgId = rootOrgId + pathParam;
    } else {
      pathWithOrgId = INVALID_ROOT_ORG_ID + pathParam;
    }
    return getLmsApiUriPath(
        "/api/dashboard/v1/consumption/org", "/v1/dashboard/consumption/org", pathWithOrgId);
  }

  @DataProvider(name = "orgConsumptionDashboardFailureDataProvider")
  public Object[][] orgConsumptionDashboardFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITHOUT_ACCESS_TOKEN,
        false,
        HttpStatus.UNAUTHORIZED,
        false,
        ""
      },
      new Object[] {
        TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITH_INVALID_ORG_ID,
        true,
        HttpStatus.BAD_REQUEST,
        false,
        "?period=1d"
      },
      new Object[] {
        TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD,
        true,
        HttpStatus.BAD_REQUEST,
        true,
        "?period=1"
      }
    };
  }

  @Test(dataProvider = "orgConsumptionDashboardFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode", "canCreateOrg", "pathParam"})
  @CitrusTest
  public void testOrgConsumptionFailure(
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
        getOrgConsumptionUrl(canCreateOrg, pathParam),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "orgConsumptionDashboardSuccessDataProvider")
  public Object[][] orgConsumptionDashboardSuccessDataProvider() {

    return new Object[][] {
//      new Object[] {
//        TEST_NAME_ORG_CONSUMPTION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID,
//        true,
//        HttpStatus.OK,
//        true,
//        "?period=7d"
//      },
    };
  }

  @Test(dataProvider = "orgConsumptionDashboardSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode", "canCreateOrg", "pathParam"})
  @CitrusTest
  public void testOrgConsumptionSuccess(
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
        getOrgConsumptionUrl(canCreateOrg, pathParam),
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
