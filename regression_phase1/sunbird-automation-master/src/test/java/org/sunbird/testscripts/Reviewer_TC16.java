package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Reviewer_TC16 extends BaseTest
{
	@Test
	public void testCase16() throws Exception
	{	
		/*Creator_TC02 creatorTestcase2 = new Creator_TC02();
		creatorTestcase2.testCase02();	*/	
		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		
		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		//Login as Creator	
		signupObj.userLogin(CREATOR);

		//Go to workspace , create a book,submit for reviewer
		creatorUserPageObj.navigateToWorkspace(BOOK);

		//Navigate to workspace and Create new Book
		creatorUserPageObj.createBook(objListOFTestDataForSunbird);

		//Save and Send for Review
		creatorUserPageObj.saveAndSendBookForReview(objListOFTestDataForSunbird);
		
		//Logout as content Creator
		signupObj.userLogout();
		
		//Login as Book reviewer
		signupObj.userLogin(BOOKREVIEWER);
		
		//Verify only books are there for reviewing
		creatorUserPageObj.verifyOnlyBooksPresent();
	}

}
