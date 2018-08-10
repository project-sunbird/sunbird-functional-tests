package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.FlagReviewerPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class FlagReviewer_TC03 extends BaseTest
{
	@Test
	public void flagReviewerTestCase03() throws Exception
	{
		SignUpObj signUpObj = new SignUpObj();
		FlagReviewerPageObj flagReviewer = new FlagReviewerPageObj();
		
		//Login as Public user
		signUpObj.userLogin(PUBLICUSER1);
		
		//search a course, a content and flag them
		String courseName=flagReviewer.flagCourseContent();
		System.out.println("course flagged: "+courseName);

		GenericFunctions.waitWebDriver(2000);
		
		String contentName=flagReviewer.genericContentSearch();
		System.out.println("content flagged: "+contentName);
		
		GenericFunctions.waitWebDriver(2000);
		//Logout as public user
		signUpObj.userLogout();
		
		
		//Login as Flag reviewer
		signUpObj.userLogin(FLAGREVIEWER);
		
		//Discard the flag of course 
		flagReviewer.acceptDiscardFlag(DISCARD);
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		//Discard the flag of content
		flagReviewer.acceptDiscardFlag(DISCARD);
		//String courseName="CourseAC0062";
		
		//Check the course in Courses
		String checkedCourse=flagReviewer.checkCourseInCourses(courseName);
		//String contentName="2303";
		
		//Check content in Library
		String checkedContent=flagReviewer.checkContentInLib();
		
		if(courseName.equalsIgnoreCase(checkedCourse)&&contentName.equalsIgnoreCase(checkedContent))
		{
			System.out.println("Course is correctly flagged, Discarded and checked");
			System.out.println("Content is correctly flagged, Discarded and checked");
		}
		else
		{
			System.out.println("Course/Content is not Correctly flagged,Discarded and checked");
		}
		
		//Logout as flag reviewer
		signUpObj.userLogout();
		
		
	}

}
