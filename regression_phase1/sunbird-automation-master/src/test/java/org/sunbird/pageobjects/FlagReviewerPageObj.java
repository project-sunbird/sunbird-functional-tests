package org.sunbird.pageobjects;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.security.auth.login.FailedLoginException;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.page.FlagReviewerPage;
import org.sunbird.page.PublicUserPage;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;

public class FlagReviewerPageObj extends BaseTest
{
	FlagReviewerPage flagReviewerpage = PageFactory.initElements(driver, FlagReviewerPage.class);
	PublicUserPage publicUserPage = PageFactory.initElements(driver, PublicUserPage.class);
	CreatorUserPage creatorPage = PageFactory.initElements(driver, CreatorUserPage.class);
	List <TestDataForSunbird> objListOFTestDataForSunbird1= null ;
	Random rand = new Random();
	Actions action = new Actions(driver);
	JavascriptExecutor js = (JavascriptExecutor)driver;


	public void flaggedUpForReview()
	{	

		//Click a random course from My courses section
		publicUserPage.headerProfile.click();

		/*int randomCourse=rand.nextInt(flagReviewerpage.courseMyCourses1.size());
		System.out.println("clicking on Course no: "+randomCourse+", and size is :"+flagReviewerpage.courseMyCourses1.size());
		//GenericFunctions.waitTillTheElementIsVisibleAndClickable(flagReviewerpage.courseMyCourses1.get(randomCourse));
		String clickCourse=flagReviewerpage.courseMyCourses1.get(randomCourse).getText();
		System.out.println("Course  :"+clickCourse); 	
		flagReviewerpage.courseMyCourses1.get(randomCourse).click();
		GenericFunctions.waitWebDriver(4000);*/

		publicUserPage.searchBar.sendKeys(SEARCH_COURSES);
		publicUserPage.clickSearch.click();
		GenericFunctions.waitWebDriver(3000);
		int randomCourse=rand.nextInt(flagReviewerpage.coursesSearched.size());
		flagReviewerpage.coursesSearched.get(randomCourse).click();
		String getCourseName=flagReviewerpage.clikedCourseContentName.getText();
		GenericFunctions.waitWebDriver(3000);


		//GenericFunctions.waitForElementToAppear(flagReviewerpage.flagIcon);
		flagReviewerpage.flagIcon.click();
		GenericFunctions.waitWebDriver(2000);
		randomReasonForFlag();
		GenericFunctions.waitWebDriver(3000);
		//Search and click on a random Content from Popular Books section

	}
	public String genericContentSearch()
	{
		GenericFunctions.waitForElementToAppear(creatorPage.headerLibrary);
		publicUserPage.headerLibrary.click();
		publicUserPage.searchBar.sendKeys(SEARCH_LIBRARIES);
		GenericFunctions.waitForElementToAppear(flagReviewerpage.popularBooksHeader);
		String contentName=flagReviewerpage.contentsSearched.getText();
		flagReviewerpage.contentsSearched.click();

		System.out.println(contentName);
		//flagReviewerpage.contentsSearched.get(randomContent).click();
		//String getContentName=flagReviewerpage.clikedCourseContentName.getText();
		GenericFunctions.waitForElementToAppear(flagReviewerpage.flagIcon);
		flagReviewerpage.flagIcon.click();
		GenericFunctions.waitWebDriver(2000);
		randomReasonForFlag();
		return contentName;

	}
	public void randomReasonForFlag()
	{
		//Select a random reject reason for a flag
		int randomreason=rand.nextInt(flagReviewerpage.flagReason.size());
		GenericFunctions.waitWebDriver(3000);
		//GenericFunctions.waitForElements(flagReviewerpage.flagReason);
		flagReviewerpage.flagReason.get(randomreason).click();
		//Give a random comment for the comment box
		String randcourse = (flagReviewerpage.randomComments[rand.nextInt(flagReviewerpage.randomComments.length)]);
		flagReviewerpage.addComment.sendKeys(randcourse);
		GenericFunctions.waitWebDriver(2000);
		flagReviewerpage.submitButton.click();
	}

	public void flagged_UpforReview()
	{
		publicUserPage.headerProfile.click();
		GenericFunctions.waitForElementToAppear(creatorPage.workSpace);
		creatorPage.workSpace.click();
		GenericFunctions.waitWebDriver(2000);
		flagReviewerpage.flaggedContent.click();	
		System.out.println(driver.getCurrentUrl()+"Reviwer has FLAGGED bucket access");
		GenericFunctions.waitWebDriver(1000);
		flagReviewerpage.upForReview.click();
		GenericFunctions.waitWebDriver(2000);
		System.out.println(driver.getCurrentUrl()+"Reviewer has UP FOR REVIEW bucket access");
	}

	public String courseSearchAndFlagIt()
	{
		GenericFunctions.waitForElementToAppear(publicUserPage.headerCourses);
		publicUserPage.headerCourses.click();
		GenericFunctions.waitForElementToAppear(flagReviewerpage.latestCourses);
		int courseSize = flagReviewerpage.courseMy.size();
		int i=0;
		String elementArray[]=new String[courseSize];
		for(WebElement ele:flagReviewerpage.courseMy)
		{
			GenericFunctions.waitWebDriver(2000);
			if(ele.isDisplayed())
			{
				String eleText=ele.getText();
				System.out.println(eleText);
				elementArray[i]=eleText;
				i++;
				GenericFunctions.waitWebDriver(2000);
			}
			else
			{
				GenericFunctions.waitWebDriver(2000);
				flagReviewerpage.myCourseNextuBtton.click();
				GenericFunctions.waitWebDriver(2000);
				elementArray[i]=flagReviewerpage.courseMyFifth.getText();
			}
		}


		//Adding all the values from the araay to ArrayList except NULL
		//	List<String> values = new ArrayList<String>();
		/*for(String data:elementArray)
		{
			if(data!=null){
				values.add(data);
			}
		}

		//Trimming all the null values and adding only Non null values to String array
		String [] trimmedArray= values.toArray(new String[values.size()]);
		for(String data:trimmedArray)
		{
			System.out.println(data+" ");
		}*/

		for(String data:elementArray)
		{
			System.out.println(data+" ");
		}


		String randomCourseSearch = (elementArray[new Random().nextInt(elementArray.length)]);
		System.out.println("----------------------------");
		System.out.println(randomCourseSearch);
		System.out.println("----------------------------");
		//To click on a any random course from the course

		/*for(int c=0;c<=elementArray.length;c++)
		{




		}*/

		try
		{
			publicUserPage.searchBar.clear();
			publicUserPage.searchBar.sendKeys(randomCourseSearch);
			publicUserPage.clickSearch.click();
			GenericFunctions.waitWebDriver(3000);
			if(randomCourseSearch.equalsIgnoreCase(publicUserPage.courseToBeClicked.getText()))
			{
				publicUserPage.courseToBeClicked.click();
				GenericFunctions.waitWebDriver(2000);
				if(flagReviewerpage.flagIcon.isDisplayed())
				{
					GenericFunctions.waitWebDriver(2000);
					flagReviewerpage.flagIcon.click();
					randomReasonForFlag();
					System.out.println("Flagged the Course");
				}
				else
				{
					driver.navigate().back();
					publicUserPage.searchBar.clear();
					GenericFunctions.waitWebDriver(2000);
					String randomCourseSearch1 = (elementArray[new Random().nextInt(elementArray.length)]);
					publicUserPage.searchBar.sendKeys(randomCourseSearch1);
					publicUserPage.courseToBeClicked.click();
					GenericFunctions.waitForElementToAppear(flagReviewerpage.flagIcon);
					flagReviewerpage.flagIcon.click();
					randomReasonForFlag();
					System.out.println("Flagged the Course");

				}
			}

			/*int randomValue = rand.nextInt(flagReviewerpage.courseList.size()); //Getting a random value that is between 0 and (list's size)-1
			// flagReviewerpage.courseList.get(randomValue).click(); 
			GenericFunctions.waitWebDriver(2000);
			int width = flagReviewerpage.courseList.get(randomValue).getSize().getWidth();
			action.moveToElement(flagReviewerpage.courseList.get(randomValue)).moveByOffset((width/2)-2, 0).click().perform();
			 */

		}
		catch(Exception e)
		{
			System.out.println( "exception occured on searching the course- " + e);
		}


		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(2000);
		try
		{
			System.out.println(flagReviewerpage.courseFlaggedMessage.getText());
		}
		catch(Exception e)
		{
			System.out.println("Exception occured while capturing message"+e);
		}
		return randomCourseSearch;

		/*	Random rand = new Random();
		   string locator = string.Format("//div/div[2]/div[{0}]/input", rand.Next(5)); 
		   // Where 5 is the number of the elements in the list
		   driver.FindElement(By.Xpath(locator)).Click();*/

	}	


	public void clickCourses()
	{
		GenericFunctions.waitForElementToAppear(publicUserPage.headerCourses);
		publicUserPage.headerCourses.click();
		GenericFunctions.waitForElementToAppear(flagReviewerpage.latestCourses);
		//int c=0;

		GenericFunctions.waitForElementToAppear(flagReviewerpage.latestCourses);
		int courseSize = flagReviewerpage.courseMy.size();
		int i=0;
		String elementArray[]=new String[courseSize];
		for(WebElement ele:flagReviewerpage.courseMy)
		{


		}

	}

	public void acceptDiscardFlag(String acceptDiscard)
	{
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitForElementToAppear(creatorPage.workSpace);
		creatorPage.workSpace.click();
		GenericFunctions.waitForElementToAppear(flagReviewerpage.flagged);
		flagReviewerpage.flagged.click();
		try
		{
			GenericFunctions.waitWebDriver(2000);
			System.out.println(creatorPage.getCourseName.getText());
			creatorPage.getCourseName.click();
			GenericFunctions.waitWebDriver(1500);		
			GenericFunctions.WaitForFrameAndSwitchToIt(creatorPage.iFrame);
			GenericFunctions.waitWebDriver(3500);


			if(acceptDiscard.equalsIgnoreCase("accept"))
			{
				GenericFunctions.waitWebDriver(2000);
				flagReviewerpage.acceptGreyButton.click();
			}
			else if(acceptDiscard.equalsIgnoreCase("discard"))
			{
					GenericFunctions.waitWebDriver(2000);
					flagReviewerpage.discardGreyButton.click();
					GenericFunctions.waitWebDriver(2000);
			}


		}
		catch(Exception e)
		{
			System.out.println("Exception in Accpeting/Rejecting the flag "+e);
		}
	}

	public void acceptDiscardBlue(String acceptDiscard)
	{
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitForElementToAppear(creatorPage.workSpace);
		creatorPage.workSpace.click();
		GenericFunctions.waitForElementToAppear(flagReviewerpage.flagged);
		flagReviewerpage.flagged.click();
		try
		{
			GenericFunctions.waitWebDriver(2000);
			System.out.println(creatorPage.getCourseName.getText());
			creatorPage.getCourseName.click();
			/*GenericFunctions.waitWebDriver(1500);		
			GenericFunctions.WaitForFrameAndSwitchToIt(creatorPage.iFrame);
			GenericFunctions.waitWebDriver(3500);
			 */
			GenericFunctions.waitWebDriver(3500);
			if(acceptDiscard.equalsIgnoreCase("accept"))
			{
				GenericFunctions.waitForElementToAppearOnScreen(flagReviewerpage.acceptBlueButton);
				GenericFunctions.scrollToElement(flagReviewerpage.acceptBlueButton);
				flagReviewerpage.acceptBlueButton.click();
				GenericFunctions.waitWebDriver(1500);
				System.out.println("Accepted the flag");
				//flagReviewerpage.acceptGreyButton.isDisplayed();
			}
			else if(acceptDiscard.equalsIgnoreCase("discard"))
			{
				GenericFunctions.waitForElementToAppearOnScreen(flagReviewerpage.discardBlueButton);
				GenericFunctions.scrollToElement(flagReviewerpage.discardBlueButton);
				flagReviewerpage.discardBlueButton.click();
				System.out.println("Rejected the flag");
				//flagReviewerpage.acceptGreyButton.isDisplayed();
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in accepting / Rejecting flag in Blue button ");
		}
	}

	public String flagCourseContent()
	{
		objListOFTestDataForSunbird1 = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		GenericFunctions.waitTillTheElementIsVisibleAndClickable(creatorPage.headerCourse);
		creatorPage.headerCourse.click();
		GenericFunctions.waitWebDriver(2000);
		/*publicUserPage.searchBar.clear();
		publicUserPage.searchBar.sendKeys(objListOFTestDataForSunbird1.get(0).getCourseName()+"C");
		publicUserPage.clickSearch.click();*/
		GenericFunctions.waitForElementToAppear(flagReviewerpage.myCourses);
		GenericFunctions.waitWebDriver(3000);
		String courseName=flagReviewerpage.courseMyCourses2.getText();
		flagReviewerpage.courseMyCourses2.click();

		/*int i=0;
		while()
		{
		WebElement courseName=flagReviewerpage.courseMyCourses1.get(i);
		courseName.click();
		if(flagReviewerpage.courseFlaggedMessage.isDisplayed())
		{
			GenericFunctions.waitWebDriver(2000);
			creatorPage.headerCourse.click();
			GenericFunctions.waitForElementToAppear(flagReviewerpage.myCourses);
			flagReviewerpage.courseMyCourses1.get(i+1);
		}*/
		GenericFunctions.waitForElementToAppear(flagReviewerpage.flagIcon);
		//if(flagReviewerpage.flagIcon.isDisplayed())

		GenericFunctions.waitWebDriver(2000);
		flagReviewerpage.flagIcon.click();
		randomReasonForFlag();
		System.out.println("Flagged the Course");
		return courseName;
	}

	public String checkCourseInCourses(String courseToSearch)
	{
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitForElementToAppear(creatorPage.headerCourse);
		creatorPage.headerCourse.click();
		publicUserPage.searchBar.click();
		publicUserPage.searchBar.sendKeys(courseToSearch);
		publicUserPage.clickSearch.click();
		//GenericFunctions.waitForElementToAppear(flagReviewerpage.searchedCourse);
		GenericFunctions.waitWebDriver(3000);
		String courseName=flagReviewerpage.searchedCourse.getText();
		System.out.println(courseName);
		return courseName;

	}

	public String checkContentInLib()
	{
		GenericFunctions.waitForElementToAppear(creatorPage.headerLibrary);
		publicUserPage.headerLibrary.click();
		GenericFunctions.waitForElementToAppear(flagReviewerpage.popularBooksHeader);

		/*publicUserPage.searchBar.sendKeys(SEARCH_LIBRARIES);
		publicUserPage.clickSearch.click();
		 */
		String contentName=flagReviewerpage.contentsSearched.getText();
		GenericFunctions.waitWebDriver(2000);
		System.out.println(contentName);
		return contentName;
	}

	public void searchAndFlagResource(String resourceToSearch) 
	{
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.waitTillTheElementIsVisibleAndClickable(creatorPage.searchInput);
		creatorPage.searchInput.sendKeys(resourceToSearch);
		creatorPage.searchIcon.click();
		GenericFunctions.waitWebDriver(2000);
		creatorPage.getCourseName.click();
		GenericFunctions.waitForElementToAppear(flagReviewerpage.flagIcon);
		flagReviewerpage.flagIcon.click();
		GenericFunctions.waitWebDriver(2000);
		randomReasonForFlag();
		System.out.println("Flagged Resource is "+resourceToSearch);
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
	}
	
	public void editAndSubmitContent()
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird=null;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");

		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitForElementToAppear(creatorPage.workSpace);
		creatorPage.workSpace.click();
		GenericFunctions.waitWebDriver(2000);
		creatorPage.drafts.click();
		String contentToAssert = objListOFTestDataForSunbird.get(6).getCourseName()+GenericFunctions.readFromNotepad(".//resourceNumbers.txt").toString();
		System.out.println(creatorPage.getCourseName.getText());
		if(contentToAssert.startsWith("Automation Resource"))//)
		//if(contentToAssert.contains(creatorPage.getCourseName.getText()))
		{
			creatorPage.getCourseName.click();
			//Adding a SLide so that it gets edited
			//GenericFunctions.waitForElementToAppear(flagReviewerpage.contentTitleTopLeft);
			GenericFunctions.waitWebDriver(6500);
			GenericFunctions.WaitForFrameAndSwitchToIt(creatorPage.iFrame);
			GenericFunctions.waitWebDriver(7000);
			flagReviewerpage.addSlide.click();
			GenericFunctions.waitWebDriver(2500);
			creatorPage.saveCourse.click();
			GenericFunctions.waitWebDriver(2500);
			creatorPage.closeButton.click();
			GenericFunctions.waitWebDriver(3000);
			creatorPage.sendForReview.click();
			GenericFunctions.waitWebDriver(3000);
			creatorPage.saveButton.click();
			GenericFunctions.waitWebDriver(6500);
			GenericFunctions.refreshWebPage();
		}
		
		
		
		
		
		else
		{
			System.out.println(contentToAssert +"Course is not found in Drafts to Edit");
		}
		
	}
	
	public String publishRequestChanges(String publishRequest)
	{
		String contentNumber="";
		List <TestDataForSunbird> objListOFTestDataForSunbird=null;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitForElementToAppear(creatorPage.workSpace);
		creatorPage.workSpace.click();
		GenericFunctions.waitWebDriver(1500);
		creatorPage.upForReview.click();
		contentNumber = objListOFTestDataForSunbird.get(6).getCourseName()+GenericFunctions.readFromNotepad(".//resourceNumbers.txt").toString();
		creatorPage.searchForReview.click();
		creatorPage.searchForReview.sendKeys(contentNumber);
		creatorPage.searchIcon.click();
		GenericFunctions.waitWebDriver(3500);
		creatorPage.searchedContentForPublish.click();
		GenericFunctions.waitWebDriver(3500);
		if(publishRequest.equalsIgnoreCase("publish"))
		{
			GenericFunctions.waitForElementToAppear(creatorPage.publishResource);
			GenericFunctions.scrollToElement(creatorPage.publishResource);
			creatorPage.publishResource.click();
			GenericFunctions.waitWebDriver(4000);
			for(int i=0;i<creatorPage.checkbox.size();i++)	
			{
				creatorPage.checkbox.get(i).click();
			}
			System.out.println("Checked all CBs");
			GenericFunctions.waitWebDriver(4000);	
			GenericFunctions.waitForElementToAppear(creatorPage.popupPublishButton);
			creatorPage.popupPublishButton.click();
			GenericFunctions.waitWebDriver(3000);
			GenericFunctions.refreshWebPage();
			GenericFunctions.waitWebDriver(3000);
			System.out.println("Content published sucessfully");
		}
		else if(publishRequest.contains("request"))
		{
			GenericFunctions.waitForElementToAppear(creatorPage.clickRequestChanges);
			GenericFunctions.scrollToElement(creatorPage.clickRequestChanges);
			GenericFunctions.waitWebDriver(2500);
			//Giving reasons 
			creatorPage.clickRequestChanges.click();
			//GenericFunctions.waitForElementToAppear(creatorPage.rejectReason1);
			GenericFunctions.waitWebDriver(2500);
			creatorPage.rejectReason1.click();
			creatorPage.rejectReason2.click();
			creatorPage.rejectReason3.click();
			String rejectReason = (REVIEW_COMMENTS[new Random().nextInt(REVIEW_COMMENTS.length)]);
			creatorPage.reviewComments.click();
			creatorPage.reviewComments.sendKeys(rejectReason);
			creatorPage.requestChangesButton.click();
			GenericFunctions.waitWebDriver(3000);
			GenericFunctions.refreshWebPage();
			GenericFunctions.waitWebDriver(3000);
			System.out.println("Content requested for changes sucessfully");
		}
		return contentNumber;
		
	}
	
	public void recheckInDrafts(String contentToCheckDrafts)
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird=null;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");

		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitForElementToAppear(creatorPage.workSpace);
		creatorPage.workSpace.click();
		GenericFunctions.waitWebDriver(2000);
		creatorPage.drafts.click();
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.waitForElementToAppearOnScreen(creatorPage.getCourseName);
		creatorPage.getCourseName.click();
		//Adding a SLide so that it gets edited
		//GenericFunctions.waitForElementToAppear(flagReviewerpage.contentTitleTopLeft);
		GenericFunctions.waitWebDriver(6500);
		GenericFunctions.WaitForFrameAndSwitchToIt(creatorPage.iFrame);
		GenericFunctions.waitWebDriver(7000);
		if(contentToCheckDrafts.equalsIgnoreCase(flagReviewerpage.contentTitleTopLeft.getText()))
		{
			System.out.println("Content is present in Drafts");
		}
		else 
		{
			System.out.println("Content is not present in Drafts");
		}
		creatorPage.editorCloseIcon.click();
		GenericFunctions.waitWebDriver(2000);
	}
	
	


}
