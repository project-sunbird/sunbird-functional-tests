package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import java.time.Instant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;

public class OrgUtil {

	private static String rootOrgId = null;

	private static final String rootOrgChannel = "FT_Org_Channel_" + Instant.now().getEpochSecond();
	private static final String rootOrgExternalId =
			"FT_Org_External_" + Instant.now().getEpochSecond();
	private static final String rootOrgProviderId =
			"FT_Org_Provider_" + Instant.now().getEpochSecond();

	public static String getRootOrgChannel() {
		return rootOrgChannel;
	}

	public static String getRootOrgExternalId() {
		return rootOrgExternalId;
	}

	public static String getRootOrgProviderId() {
		return rootOrgProviderId;
	}
	
	public static String getCreateOrgUrl(BaseCitrusTestRunner runner) {
		return runner.getLmsApiUriPath("/api/org/v1/create", "/v1/org/create");
	}

	public static String getAddUserToOrgUrl(BaseCitrusTestRunner runner) {
		return runner.getLmsApiUriPath("/api/org/v1/member/add", "/v1/org/member/add");
	}

	public static void createOrg(
			BaseCitrusTestRunner runner,
			TestContext testContext,
			String templateDir,
			String testName,
			HttpStatus responseCode) {
		runner.http(
				builder ->
				TestActionUtil.getPostRequestTestAction(
						builder,
						Constant.LMS_ENDPOINT,
						templateDir,
						testName,
						getCreateOrgUrl(runner),
						Constant.REQUEST_JSON,
						MediaType.APPLICATION_JSON.toString(),
						TestActionUtil.getHeaders(true)));
		runner.http(
				builder ->
				TestActionUtil.getExtractFromResponseTestAction(
						testContext,
						builder,
						Constant.LMS_ENDPOINT,
						responseCode,
						"$.result.organisationId",
						"organisationId"));
		runner.sleep(Constant.ES_SYNC_WAIT_TIME);
	}

	public static void addUserToOrg(
			BaseCitrusTestRunner runner, String templateDir, String testName) {
		runner.http(
				builder ->
				TestActionUtil.getPostRequestTestAction(
						builder,
						Constant.LMS_ENDPOINT,
						templateDir,
						testName,
						getAddUserToOrgUrl(runner),
						Constant.REQUEST_JSON,
						MediaType.APPLICATION_JSON.toString(),
						TestActionUtil.getHeaders(true)));
	}


	public static String getRootOrgId(BaseCitrusTestRunner runner, TestContext testContext) {

		if (StringUtils.isBlank(rootOrgId)) {
			createOrg(
					runner,
					testContext,
					"templates/organisation/create",
					"testCreateRootOrgSuccess",
					HttpStatus.OK);
			rootOrgId = testContext.getVariable("organisationId");     
		} else {
			testContext.setVariable("organisationId", rootOrgId);
		}
		return rootOrgId;
	}

	public static String createSubOrgId(BaseCitrusTestRunner runner, TestContext testContext) {
		
		createOrg(
				runner,
				testContext,
				"templates/organisation/create",
				"testCreateSubOrgSuccess",
				HttpStatus.OK);

		return rootOrgId;
	}

}
