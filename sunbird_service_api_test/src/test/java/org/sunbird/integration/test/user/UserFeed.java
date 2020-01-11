package org.sunbird.integration.test.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.sunbird.common.util.HttpUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.CitrusParameters;

public class UserFeed extends BaseCitrusTestRunner {

	@Autowired
	protected TestContext testContext;
	@Autowired
	protected HttpUtil httpUtil;
	public static final String USER_FEED_SERVER_URI = "/api/user/v1/feed";
	public static final String USER_FEED_LOCAL_URI = "/v1/user/feed";
	public static final String TEMPLATE_DIR = "templates/user/feed";

	@DataProvider(name = "userFeedFailureDataProvider")
	public Object[][] userFeedFailureDataProvider() {
		return new Object[][] { new Object[] { "testUserFeedFailureWithOutAuthToken", false },
				new Object[] { "testUserFeedFailureWithDifferentAuthToken", true }, };
	}

	@DataProvider(name = "userFeedSuccessDataProvider")
	public Object[][] userFeedSuccessDataProvider() {
		return new Object[][] { new Object[] { "testUserFeedSuccess" }};
	}

	/**
	 * Test user migrate negative scenario.
	 * 
	 * @param testName
	 */
	@Test(dataProvider = "userFeedFailureDataProvider")
	@CitrusParameters({ "testName", "authRequired" })
	@CitrusTest
	public void testUserMigrateFailure(String testName, boolean authRequired) {
		getTestCase().setName("testName");
		performGetTest(this, TEMPLATE_DIR, testName,
				getLmsApiUriPath(USER_FEED_SERVER_URI, USER_FEED_LOCAL_URI, "123444"), authRequired,
				HttpStatus.UNAUTHORIZED, RESPONSE_JSON);
	}

	/**
	 *
	 * @param requestJson
	 * @param responseJson
	 * @param testName
	 */
	@Test(dataProvider = "userFeedSuccessDataProvider")
	@CitrusParameters({ "testName" })
	@CitrusTest
	public void testUserFeed(String testName) {
		getTestCase().setName(testName);
		Map<String, String> userData = httpUtil.createUserAndGetToken();
		performGetTest(this, TEMPLATE_DIR, testName,
				getLmsApiUriPath(USER_FEED_SERVER_URI, USER_FEED_LOCAL_URI, userData.get("userId")),
				userData.get("token"), HttpStatus.OK, RESPONSE_JSON);

	}

}
