package org.sunbird.integration.test.user;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.CitrusParameters;

public class UserMigrate  extends BaseCitrusTestRunner{

	  @Autowired 
	  protected TestContext testContext; 
	  public static final String USER_MIGRATE_SERVER_URI= "/api/user/v1/migrate";
	  public static final String USER_MIGRATE_LOCAL_URI = "/v1/user/migrate";
	  public static final String TEMPLATE_DIR = "templates/user/migrate";
	  
	  @DataProvider(name = "userMigrateFailureDataProvider")
	  public Object[][] userMigrateFailureDataProvider() {
	    return new Object[][] {
	      new Object[] {"testUserMigrateFailureWithInvalidAction",true},
	      new Object[] {"testUserMigrateFailureWithInvalidUserId",true},
	      new Object[] {"testUserMigrateFailureWithOutUserToken",false},
	      new Object[] {"testUserMigrateAcceptFailureWithOutUserExtId",true},
	      new Object[] {"testUserMigrateAcceptFailureWithOutChannel",true}
	    };
	  }
	  
	  @DataProvider(name = "userMigrateFailureWithNewUserDataProvider")
	  public Object[][] userMigrateFailureWithNewUserDataProvider() {
	    return new Object[][] {
	      new Object[] {"testUserMigrateFailureWithValidAccountFeedEntryMissing",true,HttpStatus.OK},
	    };
	  }  
	  
	  

	  /**
	   * Test user migrate negative scenario.
	   * @param testName
	   */
	  @Test(dataProvider = "userMigrateFailureDataProvider")
	  @CitrusParameters({"testName","authRequired"})
	  @CitrusTest
	  public void testUserMigrateFailure(String testName,boolean authRequired) {
	    performPostTest(
	    	this,	
	        TEMPLATE_DIR,
	        testName,
	        getLmsApiUriPath(USER_MIGRATE_SERVER_URI, USER_MIGRATE_LOCAL_URI),
	        REQUEST_JSON,
	        MediaType.APPLICATION_JSON,
	        authRequired,
	        authRequired?HttpStatus.BAD_REQUEST:HttpStatus.UNAUTHORIZED,
	        RESPONSE_JSON
	        );
	  }

	  
	  @Test(dataProvider = "userMigrateFailureWithNewUserDataProvider")
	  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
	  @CitrusTest
	  public void testCreateUserNoteSuccess(
	      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
	    getTestCase().setName(testName);
	    beforeTest();
	    performPostTest(
	        this,
	        TEMPLATE_DIR,
	        testName,
	        getLmsApiUriPath(USER_MIGRATE_SERVER_URI, USER_MIGRATE_LOCAL_URI),
	        REQUEST_JSON,
	        MediaType.APPLICATION_JSON,
	        isAuthRequired,
	        httpStatusCode,
	        RESPONSE_JSON);
	  }

	  void beforeTest() {
	    UserUtil.createUserAndGetToken(this, testContext);
	  }
}
