package org.sunbird.integration.test.geolocation;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.time.Instant;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateGeolocationTest extends BaseCitrusTestRunner {

  private static final String TEST_NAME_CREATE_GEOLOCATION_FAILURE_WITHOUT_ROOT_ORG_ID =
      "testCreateGeolocationFailureWithoutRootOrgId";
  private static final String TEST_NAME_CREATE_GEOLOCATION_FAILURE_WITH_EMPTY_ROOT_ORG_ID =
      "testCreateGeolocationFailureWithEmptyRootOrgId";
  private static final String TEST_NAME_CREATE_GEOLOCATION_FAILURE_WITH_INVALID_ROOT_ORG_ID =
      "testCreateGeolocationFailureWithInvalidRootOrgId";

  private static final String TEST_NAME_CREATE_GEOLOCATION_SUCCESS_WITH_ROOT_ORG_ID =
      "testCreateGeolocationSuccessWithRootOrgId";
  private static final String TEST_NAME_CREATE_GEOLOCATION_SUCCESS_WITH_SUB_ORG_ID =
      "testCreateGeolocationSuccessWithSubOrgId";

  private static final String TEMPLATE_DIR = "templates/geolocation/create";

  private String getCreateGeolocationUrl() {
    return getLmsApiUriPath("/api/org/v1/location/create", "/v1/notification/location/create");
  }

  @DataProvider(name = "createGeolocationDataProviderFailure")
  public Object[][] createGeolocationDataProviderFailure() {
    return new Object[][] {
      new Object[] {TEST_NAME_CREATE_GEOLOCATION_FAILURE_WITHOUT_ROOT_ORG_ID},
      new Object[] {TEST_NAME_CREATE_GEOLOCATION_FAILURE_WITH_EMPTY_ROOT_ORG_ID},
      new Object[] {TEST_NAME_CREATE_GEOLOCATION_FAILURE_WITH_INVALID_ROOT_ORG_ID}
    };
  }

  @DataProvider(name = "createGeolocationDataProviderSuccess")
  public Object[][] createGeolocationDataProviderSuccess() {
    return new Object[][] {
      new Object[] {TEST_NAME_CREATE_GEOLOCATION_SUCCESS_WITH_ROOT_ORG_ID, true},
      new Object[] {TEST_NAME_CREATE_GEOLOCATION_SUCCESS_WITH_SUB_ORG_ID, false}
    };
  }

  @Test(dataProvider = "createGeolocationDataProviderSuccess")
  @CitrusParameters({"testName", "canCreateRootOrg"})
  @CitrusTest
  public void testCreateGeolocationSuccess(String testName, boolean canCreateRootOrg) {
    beforeTest(testName, canCreateRootOrg);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateGeolocationUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "createGeolocationDataProviderFailure")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testCreateGeolocationFailure(String testName) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateGeolocationUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }

  private void beforeTest(String testName, boolean canCreateRootOrg) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
    if (canCreateRootOrg) {
      variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
      OrgUtil.getRootOrgId(this, testContext);
    } else {
      variable("externalId", "FT_ExternalId_" + Instant.now().getEpochSecond());
      variable("channel", OrgUtil.getDefaultSunbirdRootOrg());
      OrgUtil.createSubOrgId(this, testContext);
    }
  }
}
