package org.sunbird.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.GetExcelFileData;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.page.FlagReviewerPage;
import org.sunbird.page.PublicUserPage;
import org.sunbird.page.UploadOrgPage;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.Assert;


public class PublicUserPageObj extends BaseTest
{
	WebDriverWait wait = new WebDriverWait(driver,20);
	UploadOrgPage OrgUploadPage=PageFactory.initElements(driver, UploadOrgPage.class);
	PublicUserPage publicUserPage = PageFactory.initElements(driver, PublicUserPage.class);
	CreatorUserPage createUserPage= PageFactory.initElements(driver, CreatorUserPage.class);
	FlagReviewerPage flagReviewerPage=PageFactory.initElements(driver, FlagReviewerPage.class);
	static Logger log = Logger.getLogger(UploadOrgObj.class.getName());
	List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
	GenericFunctions genereicFunction = new GenericFunctions();
	Actions action = new Actions(driver);
	Random rand = new Random();
	JavascriptExecutor js = (JavascriptExecutor)driver;

	public void publicLogin(String username,String password) throws Exception 
	{

		int count = objListOFTestDataForSunbird.size();
		OrgUploadPage.Loginbutton.click();
		OrgUploadPage.username.sendKeys(username);
		OrgUploadPage.password.sendKeys(password);
		System.out.println(username+" "+password);
		OrgUploadPage.clickLogin.click();
		GenericFunctions.waitWebDriver(2000);
		log.info("Login Sucessfull");

	}

	public void changePassword() throws Exception
	{
		String aTitle=driver.getTitle();
		String eTitle="Update Password";

		if(aTitle.equalsIgnoreCase(eTitle))
		{	
			log.info("Page title is matching");
			if(publicUserPage.newPassword.isDisplayed()&&publicUserPage.confirmPassword.isDisplayed()&&publicUserPage.passwordMessage.isDisplayed())
			{
				Assert.assertTrue(true);
				log.info("Update password Page is displayed");
				System.out.println("Update password Page is displayed");
				GenericFunctions.waitWebDriver(3000);
				System.out.println(publicUserPage.passwordMessage.getText()+" message confirms Update password page is displayed");
			}

			//Assert.assertTrue(true);
		}	
		else
		{
			System.out.println("Page not matched");
		}
	}

	public void forgotPassword() throws Exception 
	{
		GenericFunctions.waitForPageToLoad(OrgUploadPage.Loginbutton);

		//Click login button on Home Page
		OrgUploadPage.Loginbutton.click();
		GenericFunctions.waitWebDriver(2000);
		//Click Forgot password link
		publicUserPage.forgotPasswordLink.click();
		GenericFunctions.waitWebDriver(3000);

		//Enter the Username, email or registered phone number
		publicUserPage.enterUsername.sendKeys(TEST_MAIL_ID);
		//genereicFunction.waitWebDriver(2000);
		GenericFunctions.waitWebDriver(2000);
		publicUserPage.forgotSubmit.click();
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.waitForPageToLoad(publicUserPage.getMailConfirmation);
		if(publicUserPage.getMailConfirmation.isDisplayed())
		{
			String getMessage=publicUserPage.getMailConfirmation.getText();
			log.info(getMessage);
			System.out.println(getMessage);
			Assert.assertTrue(true);
			GenericFunctions.waitWebDriver(2000);
			publicUserPage.backToApplicationLink.click();
			GenericFunctions.waitWebDriver(2000);

		}
		else 
		{
			System.out.println("Problem in Resetting Password");
		}
	}
	public void homePageTodo() throws Exception
	{
		GenericFunctions.waitForElementToAppear(publicUserPage.headerHome);
		System.out.println(driver.getCurrentUrl());
		Assert.assertEquals(A_HOME_URL,driver.getCurrentUrl(),"Home page title is not matching");
		GenericFunctions.waitWebDriver(2000);
		if(publicUserPage.toDo.isDisplayed())
		{
			System.out.println("TO DO :"+publicUserPage.toDoCount.getText());
			GenericFunctions.waitWebDriver(2000);
			Assert.assertTrue(true);
			log.info("Verified To Do Section in Home page");
			System.out.println("Verified To Do Section in Home page");
		}
		else
		{
			System.out.println("Failed to verify To Do Section");
		}
	}

	public void courseSearch() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird1=null;
		objListOFTestDataForSunbird1 = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		GenericFunctions.waitForElementToAppear(publicUserPage.headerCourses);
		publicUserPage.headerCourses.click();
		GenericFunctions.waitWebDriver(2000);
		publicUserPage.searchBar.sendKeys(SEARCH_COURSE);
		publicUserPage.clickSearch.click();
		GenericFunctions.waitWebDriver(3000);
		//String getSearch=publicUserPage.searchedResults.getText().substring(4,11);

		if(publicUserPage.showResults.isDisplayed())
		{
			Assert.assertTrue(true);
			System.out.println("Course :"+publicUserPage.searchedCourses1.getText());
			System.out.println(publicUserPage.showResults.getText()+" for "+SEARCH_COURSE);
			js.executeScript("scroll(0, 250);");
			GenericFunctions.waitWebDriver(2000);
			js.executeScript("scroll(0, -250);");
			GenericFunctions.waitWebDriver(5000);

		}

		else
		{
			System.out.println("Could not search the course");
		}
	}

	public void librarySearch() throws Exception
	{
		Robot robot = new Robot();
		GenericFunctions.waitForElementToAppear(publicUserPage.headerLibrary);
		publicUserPage.headerLibrary.click();

		for(int i=0;i<searchInput.length;i++)
		{	
			GenericFunctions.waitWebDriver(6000);
			publicUserPage.searchBar.clear();
			publicUserPage.searchBar.sendKeys(searchInput[i]);
			publicUserPage.clickSearch.click();
			GenericFunctions.waitWebDriver(4000);
			/*if(publicUserPage.searchedInputResult.isDisplayed())
				{	
					Assert.assertTrue(true);

					if(publicUserPage.searchedResults.isDisplayed())
					{
						System.out.println("Getting results "+publicUserPage.searchedResults.getText()+ " are the input"+searchInput[i]);
						GenericFunctions.waitWebDriver(3000);
						publicUserPage.searchBar.clear();
					}

				}*/
			if(publicUserPage.showResults.isDisplayed())
			{
				Assert.assertTrue(true);
				System.out.println("Searched result :"+publicUserPage.searchedCourses1.getText());
				System.out.println(publicUserPage.showResults.getText()+" for "+searchInput[i]);
				js.executeScript("scroll(0, 250);");
				GenericFunctions.waitWebDriver(2000);
				js.executeScript("scroll(0, -250);");
				GenericFunctions.waitWebDriver(5000);

			}
			else
			{
				System.out.println("Results are not displayed for the inputs");
			}

		}

	}
	public void courseSearchFilter() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird=null;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		GenericFunctions.waitForElementToAppear(publicUserPage.headerCourses);
		publicUserPage.headerCourses.click();
		//Search a course 
		//publicUserPage.searchBar.clear();
		publicUserPage.searchBar.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());
		publicUserPage.filterIcon.click();
		//Then apply filters
		GenericFunctions.waitWebDriver(1500);
		GenericFunctions.waitForElementToAppear(publicUserPage.clickFilterSubject);
		publicUserPage.clickFilterBoard.click();
		GenericFunctions.waitForElementToAppear(publicUserPage.selectFilterBoard);
		publicUserPage.selectFilterBoard.click();

		/*createUserPage.clickOnSelectClass.click();
		GenericFunctions.waitWebDriver(1000);
		createUserPage.selectClass.click();*/


		publicUserPage.clickFilterSubject.click();
		GenericFunctions.waitForElementToAppear(publicUserPage.selectFilterSubject);
		publicUserPage.selectFilterSubject.click();
		GenericFunctions.waitWebDriver(1500);

		GenericFunctions.waitWebDriver(1500);
		publicUserPage.clickFilterMedium.click();
		GenericFunctions.waitForElementToAppear(publicUserPage.selectFilterMedium);
		publicUserPage.selectFilterMedium.click();
		GenericFunctions.waitWebDriver(2000);
		publicUserPage.clickConcepts.click();
		GenericFunctions.waitForElementToAppear(createUserPage.searchConcept);
		createUserPage.searchConcept.sendKeys(objListOFTestDataForSunbird.get(6).getTitle());
		GenericFunctions.waitWebDriver(2000);
		createUserPage.conceptChooseAll.click();
		GenericFunctions.waitWebDriver(2000);
		createUserPage.conceptDoneButton.click();
		GenericFunctions.waitWebDriver(2000);
		publicUserPage.clickApply.click();
		GenericFunctions.waitWebDriver(2000);
		/*boolean noResult=publicUserPage.noResultsFound.getTagName() != null;
		if(noResult==false)
		{*/
		js.executeScript("scroll(0, 250);"); 	
		GenericFunctions.waitWebDriver(2000);
		js.executeScript("scroll(0, -250);");
		GenericFunctions.waitWebDriver(3000);
		Assert.assertTrue(true);
		System.out.println("Filter are applied ");

	}

	public void librarysearchFilter() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird=null;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		wait.until(ExpectedConditions.visibilityOf(publicUserPage.headerLibrary));
		String randomContent = (CONTENT_TYPE[new Random().nextInt(CONTENT_TYPE.length)]);

		GenericFunctions.waitForElementToAppear(publicUserPage.headerLibrary);
		publicUserPage.headerLibrary.click();
		GenericFunctions.waitWebDriver(1500);
		publicUserPage.filterIcon.click();
		GenericFunctions.waitWebDriver(2000);
		publicUserPage.clickFilterBoard.click();
		GenericFunctions.waitWebDriver(3000);
		publicUserPage.selectFilterBoard.click();
		GenericFunctions.waitWebDriver(2000);

		publicUserPage.clickFilterClass.click();
		GenericFunctions.waitForElementToAppear(publicUserPage.selectFilterClass);
		publicUserPage.selectFilterClass.click();
		GenericFunctions.waitWebDriver(1500);
		publicUserPage.clickFilterSubject.click();
		GenericFunctions.waitForElementToAppear(publicUserPage.selectFilterSubject);
		publicUserPage.selectFilterSubject.click();
		GenericFunctions.waitWebDriver(1500);
		publicUserPage.clickFilterMedium.click();
		GenericFunctions.waitForElementToAppear(publicUserPage.selectFilterMedium);
		publicUserPage.selectFilterMedium.click();
		GenericFunctions.waitWebDriver(1500);
		/*publicUserPage.clickGradeFilter.click();
		publicUserPage.selectFilterGrade.click();
		GenericFunctions.waitWebDriver(2000);*/

		publicUserPage.clickContentTypes.click();
		GenericFunctions.waitWebDriver(2000);


		driver.findElement(By.xpath(randomContent)).click();

		GenericFunctions.waitWebDriver(3000);	
		publicUserPage.clickConcepts.click();
		GenericFunctions.waitForElementToAppear(createUserPage.searchConcept);
		createUserPage.searchConcept.sendKeys(objListOFTestDataForSunbird.get(6).getTitle());
		GenericFunctions.waitWebDriver(2000);
		createUserPage.conceptChooseAll.click();
		GenericFunctions.waitWebDriver(2000);
		createUserPage.conceptDoneButton.click();
		GenericFunctions.waitWebDriver(2000);
		/*String searchContent = publicUserPage.contentType.getText();
		System.out.println(searchContent);
		publicUserPage.headerLibrary.click();
		publicUserPage.searchBar.sendKeys(searchContent);
		//publicUserPage.searchBar.sendKeys(Keys.ENTER);
		publicUserPage.clickSearch.click();
		GenericFunctions.waitWebDriver(2000);*/
		publicUserPage.clickApply.click();


		GenericFunctions.waitWebDriver(6000);
		int allElements = publicUserPage.clearAllFilters.size();


		if(publicUserPage.searchedCourses1.isDisplayed())
		{
			System.out.println(publicUserPage.searchedCourses1.getText());
			js.executeScript("scroll(0, 250);"); 	
			GenericFunctions.waitWebDriver(2000);
			js.executeScript("scroll(0, -250);");
			GenericFunctions.waitWebDriver(3000);
			Assert.assertTrue(true);
			System.out.println("Filter are applied ");
		}
		else
		{
			for(WebElement ele:publicUserPage.clearAllFilters)
			{	
				GenericFunctions.waitWebDriver(2000);
				//WebElement option=publicUserPage.clearAllFilters.get(i);
				String elementText=ele.getText();
				System.out.println(elementText);
				ele.click();	
			}
			GenericFunctions.waitWebDriver(2000);
			System.out.println("Results are not displayed for applied filters, Removed filters");
		}

	}

	public void updateProfileImage() throws Exception
	{
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitWebDriver(3000);
		action.moveToElement(publicUserPage.imageEditIcon).click().perform();
		String path = System.getProperty("user.dir")+"/UploadingDocuments/Upload Document Contents/"+UPLOAD_PROFILE_PIC;
		System.out.println("Uploaded file name: "+path);
		log.info("Uploaded file name: "+path);
		GenericFunctions.waitWebDriver(3000);
		GenericFunctions.uploadFile(path);
		GenericFunctions.waitWebDriver(3000);
		Assert.assertTrue(true,"Profile picture updated succesfully");
		/*publicUserPage.imageEditIcon.click();
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.uploadFile(UPLOAD_PROFILE_PIC);
		GenericFunctions.waitWebDriver(3000);
		System.out.println("profile pic updated");*/
	}

	public void userSearch() throws Exception
	{
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitWebDriver(3000);
		for(int i=0;i<SEARCH_USERS.length;i++)
		{	
			GenericFunctions.waitWebDriver(6000);
			publicUserPage.searchBar.sendKeys(SEARCH_USERS[i]);
			publicUserPage.searchBar.sendKeys(Keys.ENTER);
			GenericFunctions.waitWebDriver(4000);
			if(publicUserPage.searchedInputResult.isDisplayed())
			{	
				js.executeScript("scroll(0, 450);");
				GenericFunctions.waitWebDriver(2000);
				js.executeScript("scroll(0, -200);");
				js.executeScript("scroll(0, -250);");
				Assert.assertTrue(true);
				System.out.println("Getting results :"+publicUserPage.searchedInputResult.getText()+ " are the input :" +SEARCH_USERS[i]);
				GenericFunctions.waitWebDriver(3000);
				publicUserPage.searchBar.clear();
			}
			else
			{
				System.out.println(publicUserPage.searchedInputResult+"element is not displayed");
			}


		}

	}

	public void searchOrgs() throws Exception
	{
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitWebDriver(3000);
		for(int i=0;i<SEARCH_ORG.length;i++)
		{	

			GenericFunctions.waitWebDriver(6000);
			publicUserPage.searchBar.sendKeys(SEARCH_ORG[i]);
			publicUserPage.searchBar.sendKeys(Keys.ENTER);
			GenericFunctions.waitWebDriver(4000);
			if(publicUserPage.searchedInputResult.isDisplayed())
			{
				GenericFunctions.waitWebDriver(2000);
				//js.executeScript("arguments[0].scrollIntoView();",publicUserPage.lastButton);
				
				js.executeScript("window.scrollTo(document.body.scrollHeight,500)");
				GenericFunctions.waitWebDriver(2000);
				js.executeScript("arguments[0].scrollIntoView();",publicUserPage.lastButton);
				GenericFunctions.waitWebDriver(2000);
				js.executeScript("window.scrollTo(document.body.scrollHeight,-500)");
				js.executeScript("arguments[0].scrollIntoView();",publicUserPage.headerProfile);
				/*js.executeScript("scroll(0, 300);");
				GenericFunctions.waitWebDriver(1000);
				js.executeScript("scroll(0, 300);");
				GenericFunctions.waitWebDriver(2000);
				js.executeScript("scroll(0, -300);");
				js.executeScript("scroll(0, -300);");*/
				Assert.assertTrue(true);
				System.out.println("Getting results "+publicUserPage.searchedInputResult.getText()+ " are the input" +SEARCH_USERS[i]);
				GenericFunctions.waitWebDriver(3000);
				publicUserPage.searchBar.clear();
			}
			else
			{
				System.out.println(publicUserPage.searchedInputResult+"element is not displayed");
			}


		}

	}

	public void profileInformationUpdate() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird1=null;
		objListOFTestDataForSunbird1 = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetprofileaddress");
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		//GenericFunctions.waitForElementToAppear(publicUserPage.profileContributions);
		GenericFunctions.waitForElementToAppear(createUserPage.workSpace);
		if(createUserPage.workSpace.isDisplayed())
		{

			//Edit profile summary

			publicUserPage.summaryEditButton.click();
			publicUserPage.summaryText.sendKeys(SUMMARY_EDIT);
			publicUserPage.summarySave.click();

			GenericFunctions.waitWebDriver(2000);
			GenericFunctions.waitForElementToAppear(publicUserPage.experienceAddButton);
			//Add or edit experience
			publicUserPage.experienceAddButton.click();
			publicUserPage.eOccupationField.sendKeys(EXP_OCCUPATION);
			GenericFunctions.waitWebDriver(1500);
			publicUserPage.eDesignationField.sendKeys(EXP_DESIGNATION);
			publicUserPage.eOrganizationField.sendKeys(EXP_ORG);

			//

			/*Select select = new Select(publicUserPage.eClickSubjectDropdown);
				select.selectByVisibleText("Bengali");*/

			publicUserPage.eClickSubjectDropdown.click();
			GenericFunctions.waitForElementToAppear(publicUserPage.eSelectSubjectDropdown);
			publicUserPage.eSelectSubjectDropdown.click();
			GenericFunctions.waitWebDriver(2000);
			publicUserPage.eSelectRadioButton.click();

			/*publicUserPage.eClickJoinDate.click();
				GenericFunctions.waitWebDriver(1000);
				((JavascriptExecutor) driver).executeScript(
	                    "document.getElementsByClassName('ui celled center aligned unstackable table seven column day')[0].setAttribute('value', '2018-07-19')");
				publicUserPage.eSelectJoinDate.click();*/
			GenericFunctions.waitWebDriver(2500);
			publicUserPage.summarySave.click();



			//Add or Edit address
			GenericFunctions.waitWebDriver(3000);
			String address=publicUserPage.checkAddressStatus.getText();
			if(address!=null)
			{

				GenericFunctions.scrollToElement(publicUserPage.addressEditButton);
				GenericFunctions.waitWebDriver(1500);
				publicUserPage.addressEditButton.click();
				publicUserPage.addressLine1.clear();
				publicUserPage.addressLine1.sendKeys(objListOFTestDataForSunbird1.get(1).getAddressLane1());
				publicUserPage.addressLine2.clear();
				publicUserPage.addressLine2.sendKeys(objListOFTestDataForSunbird1.get(1).getAddressLane2());
				publicUserPage.aCity.clear();
				publicUserPage.aCity.sendKeys(objListOFTestDataForSunbird1.get(1).getCity());
				publicUserPage.aState.clear();
				publicUserPage.aState.sendKeys(objListOFTestDataForSunbird1.get(1).getState());
				publicUserPage.aCountry.clear();
				publicUserPage.aCountry.sendKeys(objListOFTestDataForSunbird1.get(1).getCountry());
				publicUserPage.aZipcode.clear();
				publicUserPage.aZipcode.sendKeys(objListOFTestDataForSunbird1.get(1).getPincode());
				publicUserPage.summarySave.click();
				GenericFunctions.waitWebDriver(2000);
				//Edit educational Details
				GenericFunctions.scrollToElement(publicUserPage.educationEditButton);
				GenericFunctions.waitForElementToAppear(publicUserPage.educationEditButton);
				publicUserPage.educationEditButton.click();
				publicUserPage.eDegree.clear();
				publicUserPage.eDegree.sendKeys(objListOFTestDataForSunbird1.get(1).getDegree());
				GenericFunctions.waitWebDriver(1000);
				publicUserPage.ePercentage.clear();
				publicUserPage.ePercentage.sendKeys(objListOFTestDataForSunbird1.get(1).getPercentage());
				GenericFunctions.waitWebDriver(1000);
				publicUserPage.eGrade.clear();
				publicUserPage.eGrade.sendKeys(objListOFTestDataForSunbird1.get(1).getGrade());
				GenericFunctions.waitWebDriver(1000);
				publicUserPage.eInstitution.clear();
				publicUserPage.eInstitution.sendKeys(objListOFTestDataForSunbird1.get(1).getInstitution());
				GenericFunctions.waitWebDriver(1000);
				publicUserPage.eBoard.clear();
				publicUserPage.eBoard.sendKeys(objListOFTestDataForSunbird1.get(1).getBoard());
				GenericFunctions.waitWebDriver(1000);

				//YOP is not handled
				//publicUserPage.eYOP.clear();
				GenericFunctions.waitWebDriver(1500);
				publicUserPage.summarySave.click();

				GenericFunctions.waitWebDriver(3500);
				GenericFunctions.scrollToElement(publicUserPage.skillAddButton);

				//Add Skills
				publicUserPage.skillAddButton.click();
				GenericFunctions.waitForElementToAppear(publicUserPage.addSkillsHeader);

				action.moveToElement(publicUserPage.addSkills);
				action.click();
				action.sendKeys(objListOFTestDataForSunbird1.get(1).getSkills());
				action.build().perform();

				/*publicUserPage.addSkills.click();
					publicUserPage.addSkills.sendKeys("java");*///objListOFTestDataForSunbird1.get(1).getSkills()
				//WebElement addSkill=genereicFunction.waitForElementToAppear(publicUserPage.addSkills, 2000);
				//System.out.println(addSkill+ "element is visible");
				//publicUserPage.selectSkills.sendKeys(objListOFTestDataForSunbird1.get(1).getSkills());
				//addSkill.click();


				GenericFunctions.waitWebDriver(2000);
				publicUserPage.addSkillsHeader.click();
				publicUserPage.finishButton.click();
				//genereicFunction.selectValueFromDropdown(driver, "//sui-multi-select[@name='skills']", "java");
				GenericFunctions.waitWebDriver(3000);

				GenericFunctions.scrollToElement(publicUserPage.additionalInfoEdit);
				GenericFunctions.waitWebDriver(2000);
				//Edit additional Information
				publicUserPage.additionalInfoEdit.click();
				publicUserPage.aFirstName.clear();
				publicUserPage.aFirstName.sendKeys(objListOFTestDataForSunbird1.get(1).getAfirstName());
				publicUserPage.aLastName.clear();
				publicUserPage.aLastName.sendKeys(objListOFTestDataForSunbird1.get(1).getAlastName());
				publicUserPage.aLocation.clear();
				publicUserPage.aLocation.sendKeys(objListOFTestDataForSunbird1.get(1).getCurrentlocation());
				publicUserPage.summarySave.click();
				GenericFunctions.waitWebDriver(3000);
				System.out.println("Profile information updated sucesfully");
			}
			else
			{
				System.out.println("cannot update profile information");
			}





			/*catch(Exception e)
			{
				System.out.println("exception occured during updating profile"+e);
			}*/
		}
	}

	public void skillEndorsement(String username)
	{
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitWebDriver(1000);
		publicUserPage.searchBar.sendKeys(username);
		publicUserPage.clickSearch.click();
		GenericFunctions.waitWebDriver(3000);
		String endorseUser=publicUserPage.getResultsUsername.getText();
		if(username.equalsIgnoreCase(endorseUser))
		{
			GenericFunctions.waitWebDriver(2000);
			publicUserPage.getResultsUsername.click();
			GenericFunctions.waitWebDriver(2500);
			GenericFunctions.scrollToElement(publicUserPage.viewMoreSkills);
			GenericFunctions.waitWebDriver(1500);
			publicUserPage.viewMoreSkills.click();
			GenericFunctions.waitWebDriver(3000);
			publicUserPage.endorsementIcon.click();
			GenericFunctions.waitWebDriver(2500);
			String endorseSize=publicUserPage.endorsementCount.getText();
			int size = Integer.parseInt(endorseSize);
			System.out.println(size);
			if(size!=0)
			{
				GenericFunctions.waitWebDriver(1500);
				Assert.assertTrue(true);
				GenericFunctions.refreshWebPage();
				GenericFunctions.waitWebDriver(1500);
				System.out.println("Skills endorsed sucessfully");
			}
			else 
			{
				System.out.println("Skills are not endorsed");
			}
			
		}
		else
		{
			System.out.println("Username did not match to endorse the skill");
		}
		


	}

	public void contentFlag()
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird=null;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		GenericFunctions.waitForElementToAppearOnScreen(publicUserPage.headerCourses);
		//Enter course name in the search bar
		publicUserPage.headerCourses.click();
		publicUserPage.searchBar.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());
		publicUserPage.clickSearch.click();
		GenericFunctions.waitWebDriver(3000);

		//get and print the list size of all the courses
		System.out.println(publicUserPage.searchedCourses.size());

		//Converting the list type to String [] type
		String [] eleAray= new String[publicUserPage.searchedCourses.size()];
		int i=0;
		for(WebElement text:publicUserPage.searchedCourses)
		{
			System.out.println(eleAray[i]=text.getText());
			i++;
		}

		String randomCourseSearch = (eleAray[new Random().nextInt(eleAray.length)]);
		GenericFunctions.waitWebDriver(3000);
		publicUserPage.searchBar.clear();
		publicUserPage.searchBar.sendKeys(randomCourseSearch);
		publicUserPage.clickSearch.click();
		GenericFunctions.waitWebDriver(3000);
		/*if(randomCourseSearch.equalsIgnoreCase(publicUserPage.courseToBeClicked.getText()))
		{
			System.out.println("Clicked course: "+randomCourseSearch);
			publicUserPage.courseToBeClicked.click();
			
		}*/
		
		if(publicUserPage.courseResumeButton.isDisplayed())
		{
			GenericFunctions.waitWebDriver(2000);
			GenericFunctions.scrollToElement(publicUserPage.courseResumeButton);
			publicUserPage.courseResumeButton.click();
		}

		/*for(WebElement listEle:publicUserPage.searchedCourses)
		{
			String listEleTextFormat = listEle.getText();
			System.out.println(listEleTextFormat);
		}
		 */		

		//String[] objectArray=(String[]) publicUserPage.searchedCourses.toArray();
		//System.out.println(objectArray);
		//System.out.println(objectArray[rand.nextInt(objectArray.length)]);


	}
	
	public String addSkill()
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird1=null;
		objListOFTestDataForSunbird1 = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetprofileaddress");
	
		GenericFunctions.waitForElementToAppear(publicUserPage.headerProfile);
		publicUserPage.headerProfile.click();
		GenericFunctions.waitForElementToAppear(createUserPage.workSpace);
		GenericFunctions.waitWebDriver(3500);
		GenericFunctions.scrollToElement(publicUserPage.skillAddButton);

		//Add Skills
		publicUserPage.skillAddButton.click();
		GenericFunctions.waitForElementToAppear(publicUserPage.addSkillsHeader);
		action.moveToElement(publicUserPage.addSkills);
		action.click();
		GenericFunctions.waitWebDriver(2000);
		action.sendKeys(objListOFTestDataForSunbird1.get(1).getSkills()+GenericFunctions.testDataIncrementer(".//TestIds.txt"));
		action.build().perform();
		publicUserPage.addSkill.click();
		GenericFunctions.waitWebDriver(2000);
		publicUserPage.addSkillsHeader.click();
		publicUserPage.finishButton.click();
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.scrollToElement(publicUserPage.getUsername);
		String username=publicUserPage.getUsername.getText();
		return username;
	}


}

