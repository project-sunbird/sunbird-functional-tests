package org.sunbird.integration.test.badge;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.BadgeAssertionUtil;
import org.sunbird.common.action.BadgeClassUtil;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.IssuerUtil;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RevokeBadgeAssertionTest extends BaseCitrusTestRunner {

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

  private static final String TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITH_INVALID_ASSERTION_ID =
      "testRevokeBadgeAssertionFailureWithInvalidAssertionId";
  private static final String TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITH_INVALID_RECIPIENT_ID =
      "testRevokeBadgeAssertionFailureWithInvalidRecipientId";
  private static final String TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITH_INVALID_RECIPIENT_TYPE =
      "testRevokeBadgeAssertionFailureWithInvalidRecipientType";
  private static final String TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITHOUT_ASSERTION_ID =
      "testRevokeBadgeAssertionFailureWithoutAssertionId";
  private static final String TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_ID =
      "testRevokeBadgeAssertionFailureWithoutRecipientId";
  private static final String TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_TYPE =
      "testRevokeBadgeAssertionFailureWithoutRecipientType";

  private static final String TEST_NAME_REVOKE_BADGE_ASSERTION_SUCCESS_USER_BADGE_TYPE =
      "testRevokeBadgeAssertionSuccessUserBadgeType";
  private static final String TEST_NAME_REVOKE_BADGE_ASSERTION_SUCCESS_CONTENT_BADGE_TYPE =
      "testRevokeBadgeAssertionSuccessContentBadgeType";

  private static final String TEMPLATE_DIR = "templates/badge/assertion/revoke";

  private String getRevokeBadgeAssertionUrl() {
    return getLmsApiUriPath(
        "/api/badging/v1/issuer/badge/assertion/delete", "/v1/issuer/badge/assertion/delete");
  }

  @DataProvider(name = "revokeBadgeAssertionDataProviderSuccess")
  public Object[][] revokeBadgeAssertionDataProviderSuccess() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_REVOKE_BADGE_ASSERTION_SUCCESS_USER_BADGE_TYPE,
        true,
        false,
        BT_TEST_NAME_CREATE_USER_BADGE_ASSERTION_SUCCESS,
        BT_CREATE_USER_BADGE_ASSERTION_TEMPLATE_DIR
      },
      new Object[] {
        TEST_NAME_REVOKE_BADGE_ASSERTION_SUCCESS_CONTENT_BADGE_TYPE,
        false,
        true,
        BT_TEST_NAME_CREATE_COURSE_BADGE_ASSERTION_SUCCESS,
        BT_CREATE_COURSE_BADGE_ASSERTION_TEMPLATE_DIR
      }
    };
  }

  @DataProvider(name = "revokeBadgeAssertionDataProviderFailure")
  public Object[][] revokeBadgeAssertionDataProviderFailure() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITH_INVALID_ASSERTION_ID,
        true,
        HttpStatus.NOT_FOUND
      },
      new Object[] {
        TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITH_INVALID_RECIPIENT_ID,
        false,
        HttpStatus.NOT_FOUND
      },
      new Object[] {
        TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITH_INVALID_RECIPIENT_TYPE,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITHOUT_ASSERTION_ID, false, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_ID, false, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_REVOKE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_TYPE,
        false,
        HttpStatus.BAD_REQUEST
      },
    };
  }

  @Test(dataProvider = "revokeBadgeAssertionDataProviderSuccess")
  @CitrusParameters({"testName", "canCreateUser", "canCreateContent", "testCase", "templateDir"})
  @CitrusTest
  public void testRevokeBadgeAssertionSuccess(
      String testName,
      Boolean canCreateUser,
      Boolean canCreateContent,
      String testCase,
      String templateDir) {
    beforeTest(testName, canCreateUser, canCreateContent, true, true, true, testCase, templateDir);
    performDeleteTest(
        this,
        TEMPLATE_DIR,
        testName,
        getRevokeBadgeAssertionUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        HttpStatus.OK,
        RESPONSE_JSON);
    afterTest(true, true);
  }

  @Test(dataProvider = "revokeBadgeAssertionDataProviderFailure")
  @CitrusParameters({"testName", "canCreateOrg", "httpStatus"})
  @CitrusTest
  public void testRevokeBadgeAssertionFailure(
      String testName, Boolean canCreateUser, HttpStatus httpStatus) {
    beforeTest(testName, canCreateUser, false, false, false, false, null, null);
    performDeleteTest(
        this,
        TEMPLATE_DIR,
        testName,
        getRevokeBadgeAssertionUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatus,
        RESPONSE_JSON);
  }

  private void beforeTest(
      String testName,
      Boolean canCreateUser,
      Boolean canCreateCourse,
      Boolean canCreateIssuer,
      Boolean canCreateBadge,
      Boolean canCreateBadgeAssertion,
      String testCase,
      String templateDir) {

    getTestCase().setName(testName);

    if (canCreateUser || canCreateCourse) getAuthToken(this, true);

    if (canCreateUser) UserUtil.getUserId(this, testContext);

    if (canCreateCourse) {
      variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
      variable("resourceId", ContentStoreUtil.getResourceId());
      String courseId = ContentStoreUtil.getCourseId(this, testContext);
      variable("courseId", courseId);
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

    if (canCreateBadge) {
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
    }

    if (canCreateBadgeAssertion) {
      BadgeAssertionUtil.createBadgeAssertion(
          this, testContext, config, templateDir, testCase, HttpStatus.OK);
    }
  }

  private void afterTest(boolean isBadgeClassCreated, boolean isIssuerCreated) {

    if (isBadgeClassCreated) {
      BadgeClassUtil.deleteBadgeClass(
          this,
          testContext,
          config,
          (String) testContext.getVariables().get(Constant.EXTRACT_VAR_BADGE_ID));
    }
    if (isIssuerCreated)
      IssuerUtil.deleteIssuer(
          this,
          testContext,
          config,
          (String) testContext.getVariables().get(Constant.EXTRACT_VAR_ISSUER_ID));
  }
}
