package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.FlagReviewerPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class FlagReviewer_TC01 extends BaseTest 
{
	@Test(priority=1)
	public void flagReviewerTestCase01() throws Exception
	{
		//Login as creator
		SignUpObj creatorObj = new SignUpObj();	
		creatorObj.userLogin(FLAGREVIEWER);
		
		//Search for a course and content flag them
		FlagReviewerPageObj flagReviewer = new FlagReviewerPageObj();
		flagReviewer.flagged_UpforReview();
	}

}
