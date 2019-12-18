package org.sunbird.integration.test.badge;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.BadgeAssertionUtil;
import org.sunbird.common.action.BadgeClassUtil;
import org.sunbird.common.action.IssuerUtil;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchAssertionTest extends BaseCitrusTestRunner {

  private static final String BT_TEST_NAME_CREATE_ISSUER_SUCCESS = "testCreateIssuerSuccess";
  private static final String BT_CREATE_ISSUER_TEMPLATE_DIR = "templates/badge/issuer/create";

  private static final String BT_TEST_NAME_CREATE_BADGE_CLASS_SUCCESS =
      "testCreateBadgeClassSuccessWithTypeUser";
  private static final String BT_CREATE_BADGE_CLASS_TEMPLATE_DIR = "templates/badge/class/create";

  private static final String BT_TEST_NAME_CREATE_USER_BADGE_ASSERTION_SUCCESS =
      "testCreateBadgeAssertionSuccessUserWithoutEvidence";
  private static final String BT_CREATE_USER_BADGE_ASSERTION_TEMPLATE_DIR =
      "templates/badge/assertion/create";

  private static final String BT_TEST_NAME_CREATE_COURSE_BADGE_ASSERTION_SUCCESS =
      "testCreateBadgeAssertionSuccessCourseWithoutEvidence";
  private static final String BT_CREATE_COURSE_BADGE_ASSERTION_TEMPLATE_DIR =
      "templates/badge/assertion/create";

  public static final String TEST_NAME_SEARCH_ASSERTION_FAILURE_WITHOUT_FILTER =
      "testSearchAssertionFailureWithoutFilter";

  public static final String
      TEST_NAME_SEARCH_ASSERTION_SUCCESS_WITH_FILTER_BY_ASSERTION_USER_BADGE_TYPE =
          "testSearchAssertionSuccessWithFilterByAssertionUserBadgeType";
  public static final String
      TEST_NAME_SEARCH_ASSERTION_SUCCESS_WITH_FILTER_BY_ASSERTION_CONTENT_BADGE_TYPE =
          "testSearchAssertionSuccessWithFilterByAssertionContentBadgeType";

  public static final String TEMPLATE_DIR = "templates/badge/assertion/search";

  private String getSearchIssuerUrl() {

    return getLmsApiUriPath(
        "/api/badging/v1/issuer/badge/assertion/search", "/v1/issuer/badge/assertion/search");
  }

  @DataProvider(name = "searchAssertionSuccessDataProvider")
  public Object[][] searchAssertionSuccessDataProvider() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_SEARCH_ASSERTION_SUCCESS_WITH_FILTER_BY_ASSERTION_USER_BADGE_TYPE,
        true,
        false,
        BT_TEST_NAME_CREATE_USER_BADGE_ASSERTION_SUCCESS,
        BT_CREATE_USER_BADGE_ASSERTION_TEMPLATE_DIR
      },
      new Object[] {
        TEST_NAME_SEARCH_ASSERTION_SUCCESS_WITH_FILTER_BY_ASSERTION_CONTENT_BADGE_TYPE,
        false,
        true,
        BT_TEST_NAME_CREATE_COURSE_BADGE_ASSERTION_SUCCESS,
        BT_CREATE_COURSE_BADGE_ASSERTION_TEMPLATE_DIR
      },
    };
  }

  @DataProvider(name = "searchAssertionFailureDataProvider")
  public Object[][] searchAssertionFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_SEARCH_ASSERTION_FAILURE_WITHOUT_FILTER, HttpStatus.BAD_REQUEST},
    };
  }

 // @Test(dataProvider = "searchAssertionFailureDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
 // @CitrusTest
  public void testSearchAssertionFailure(String testName, HttpStatus httpStatusCode) {

    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSearchIssuerUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }

 // @Test(dataProvider = "searchAssertionSuccessDataProvider")
  @CitrusParameters({"testName", "canCreateUser", "canCreateCourse", "testCase", "templateDir"})
 // @CitrusTest
  public void testSearchAssertionSuccess(
      String testName,
      Boolean canCreateUser,
      Boolean canCreateCourse,
      String testCase,
      String templateDir) {
    beforeTest(testName, canCreateUser, canCreateCourse, testCase, templateDir);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSearchIssuerUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void beforeTest(
      String testName,
      Boolean canCreateUser,
      Boolean canCreateCourse,
      String testCase,
      String templateDir) {

    getTestCase().setName(testName);
    getAuthToken(this, true);

    if (canCreateUser) {
      UserUtil.getUserId(this, testContext);
    }


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
        this, testContext, config, templateDir, testCase, HttpStatus.OK);
  }
}
