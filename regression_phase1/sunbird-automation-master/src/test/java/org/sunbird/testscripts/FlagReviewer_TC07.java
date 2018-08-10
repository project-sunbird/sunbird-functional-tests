package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.FlagReviewerPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class FlagReviewer_TC07 extends BaseTest
{
	@Test
	public void flagReviewerTestCase07() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		
		SignUpObj signUpObj = new SignUpObj();
		FlagReviewerPageObj flagReviewer = new FlagReviewerPageObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		
		
	}
}
