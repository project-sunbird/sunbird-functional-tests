package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.FlagReviewerPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class FlagReviewer_TC02 extends BaseTest
{
	@Test
	public void flagReviewerTestCase02() throws Exception
	{
		//Login as Public user
		SignUpObj signUpObj = new SignUpObj();
		FlagReviewerPageObj flagReviewer = new FlagReviewerPageObj();
		
		//Login as Public user
		signUpObj.userLogin(PUBLICUSER1);
		
		//Search a course and Flag it
		flagReviewer.courseSearchAndFlagIt();
		
		//Logout as Public user
		signUpObj.userLogout();
		
		//Login as FlagReviewer
		signUpObj.userLogin(FLAGREVIEWER);
		
		//Navigate to workspace, click Flagged and accept the flagged course
		flagReviewer.acceptDiscardFlag(ACCEPT);
		
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(2000);
		
		//Logout as Flag reviewer
		signUpObj.userLogout();
	}

}
