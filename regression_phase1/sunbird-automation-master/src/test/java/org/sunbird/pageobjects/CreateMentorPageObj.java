package org.sunbird.pageobjects;



import java.awt.Robot;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.CreateMentorPage;
import org.sunbird.page.CreatorAnnouncementPage;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.page.PublicUserPage;
import org.sunbird.page.SignUp;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;


public class CreateMentorPageObj extends BaseTest{

	WebDriverWait wait = new WebDriverWait(driver,20);
	CreateMentorPage createMentorPage=PageFactory.initElements(driver, CreateMentorPage.class);
	CreatorAnnouncementPage createAnnouncementPage=PageFactory.initElements(driver, CreatorAnnouncementPage.class);
	PublicUserPage publicUserPage = PageFactory.initElements(driver, PublicUserPage.class);
	SignUp signUpPage=PageFactory.initElements(driver, SignUp.class);
	static Logger log = Logger.getLogger(CreatorAnnouncementPage.class.getName());
	CreatorUserPage createUserPage=PageFactory.initElements(driver, CreatorUserPage.class);
	Actions action = new Actions(driver);
	Random rand=new Random();
	//String announcementType="Order";
	List <TestDataForSunbird> objListOFTestDataForSunbird= null;
	String batchStatus="Previous Batches";
	String courseName;
	String startDate;
	String endDate;

	public void navigateToMyActivityInDropDownMenu() throws InterruptedException
	{
		try{
			GenericFunctions.waitWebDriver(1500);
			createUserPage.dropDown.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.myActivityInDropdown.click();	
			GenericFunctions.waitWebDriver(1500);
			createMentorPage.SearchCourseDropdown.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.firstCourseDropdownitem.click();
			createMentorPage.rightArrowIcon.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.leftArrowIcon.click();

		}
		catch(Exception e){
			log.error("Exception In the method navigateToMyActivityInDropDownMenu"+e.getMessage());
		}
	}
	public void navigateToCourseAndSearch(String courseName) throws InterruptedException
	{
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		try{

			GenericFunctions.waitWebDriver(1500);
			createUserPage.headerCourse.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.filterSearch.sendKeys(courseName);//objListOFTestDataForSunbird.get(0).getCourseName()+GenericFunctions.readFromNotepad(".//batchNumbers.txt").toUpperCase());
			createUserPage.searchIcon.click();
			GenericFunctions.waitWebDriver(2000);
			createMentorPage.courseImg1.click();
			GenericFunctions.waitWebDriver(3000);
		}
		catch(Exception e){
			log.error("Exception In the method navigateToCourseAndSearch"+e.getMessage());
		}
	}
	public void navigateToCourseAndSearchForOpenBatch(String courseName) throws InterruptedException
	{
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		try{

			GenericFunctions.waitWebDriver(1500);
			createUserPage.headerCourse.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.filterSearch.sendKeys(courseName);//objListOFTestDataForSunbird.get(0).getCourseName()+GenericFunctions.readFromNotepad(".//openBatchData.txt").toUpperCase());
			createUserPage.searchIcon.click();
			GenericFunctions.waitWebDriver(2000);
			createMentorPage.courseImg1.click();
			GenericFunctions.waitWebDriver(3000);
		}
		catch(Exception e){
			log.error("Exception In the method navigateToCourseAndSearchForOpenBatch"+e.getMessage());
		}
	}
	
	public void navigateToCourseAndSearchForOpenBatch() throws InterruptedException
	{
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		try{

			GenericFunctions.waitWebDriver(1500);
			createUserPage.headerCourse.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.filterSearch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());
			createUserPage.searchIcon.click();
			GenericFunctions.waitWebDriver(2000);
			createMentorPage.courseImg1.click();
			GenericFunctions.waitWebDriver(3000);
		}
		catch(Exception e){
			log.error("Exception In the method navigateToCourseAndSearchForOpenBatch"+e.getMessage());
		}
	}
	
	public void navigateToCourseSearchAndUpdate() throws InterruptedException
	{
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		String courseName="null";
		try{

			GenericFunctions.waitWebDriver(1500);
			createUserPage.headerCourse.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.filterSearch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());//+GenericFunctions.readFromNotepad(".//batchNumbers.txt").toUpperCase());
			createUserPage.searchIcon.click();
			GenericFunctions.waitWebDriver(2000);
			createMentorPage.courseImg1.click();
			GenericFunctions.waitWebDriver(3000);
			createMentorPage.editBatch.click();
			GenericFunctions.waitWebDriver(1000);
			GenericFunctions.switchToFrame(driver, createMentorPage.batchForm);
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.nameOfBatch.clear();
			createMentorPage.nameOfBatch.sendKeys(objListOFTestDataForSunbird.get(1).getCourseName()+ GenericFunctions.testDataIncrementer(".//batchName.txt"));
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.updateBatch.click();
		}
		catch(Exception e){
			log.error("Exception In the method navigateToCourseSearchAndUpdate"+e.getMessage());
		}
	}
	public void navigateToWorkspaceAndSelectBatches(String batchType) throws InterruptedException
	{
		try{
			GenericFunctions.waitWebDriver(1500);
			createUserPage.dropDown.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.workspaceInDropDown.click();	
			GenericFunctions.waitWebDriver(1500);
			createMentorPage.courseBatches.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.batchesDropDown.click();
			if(batchStatus.equalsIgnoreCase("Ongoing Batches")) {
				GenericFunctions.waitWebDriver(1000);
				createMentorPage.ongoingBatches.click();
				System.out.println("Ongoing batches");
			}else if(batchStatus.equalsIgnoreCase("Upcoming Batches"))
			{
				GenericFunctions.waitWebDriver(1000);
				createMentorPage.upcomingBatches.click();
				System.out.println("upcoming batches");
			}else if(batchStatus.equalsIgnoreCase("Previous Batches"))
			{
				GenericFunctions.waitWebDriver(1000);
				createMentorPage.previousBatches.click();
				System.out.println("Previous batches");
			}					
		}
		catch(Exception e){
			log.error("Exception In the method navigateToWorkspaceInDropDownMenu"+e.getMessage());
		}
	}

	public void checkForAuthenticUserAndEdit() throws InterruptedException
	{
		try{
			GenericFunctions generic = new GenericFunctions();
			CreateMentorPage createMentorPage=PageFactory.initElements(driver, CreateMentorPage.class);
			String personName = createMentorPage.profileNameInUserSearch.getText();
			System.out.println(personName);
			boolean elementStatus = generic.isElementPresent(createMentorPage.editIconInUserProfileSearch);	
			if(elementStatus == true)
			{

				System.out.println("authenticPerson");
				createMentorPage.editIconInUserProfileSearch.click();
				GenericFunctions.waitWebDriver(1000);
				for(int k=0;k<createMentorPage.selectRoleCheckboxL.size();k++) {
					if(createMentorPage.selectRoleCheckboxL.get(k).isEnabled()) {
						createMentorPage.selectRoleCheckboxL.get(k).click();
						break;
					}else {
						k++;
					}

				}
				createMentorPage.updateButtonInUserProfile.click();
				String msg = createMentorPage.successfulMessage.getText();
				System.out.println(msg);
			}	

		}
		catch(Exception e){
			log.error("Exception In the method checkForAuthenticUserAndEdit"+e.getMessage());
		}
	}

	public void checkForUnauthenticUserAndEdit() throws InterruptedException
	{
		try{
			GenericFunctions generic = new GenericFunctions();
			CreateMentorPage createMentorPage=PageFactory.initElements(driver, CreateMentorPage.class);
			String personName = createMentorPage.profileNameInUserSearch.getText();
			System.out.println(personName);
			boolean elementStatus = generic.isElementPresent(createMentorPage.editIconInUserProfileSearch);	
			if(elementStatus == true)
			{
				System.out.println("UnauthenticPerson" +"edit button is unavailable");

			}	

		}
		catch(Exception e){
			log.error("Exception In the method checkForUnauthenticUserAndEdit"+e.getMessage());
		}
	}

	public void enrollForOpenBatch() throws InterruptedException
	{
		try{

			
			if(createMentorPage.resumeCourse.isDisplayed())
			{
				System.out.println("User is already enrolled to the course");
			}
			else
			{
				createMentorPage.enrollForOpenBatch.click();
				GenericFunctions.waitWebDriver(1000);
				createMentorPage.enrollForCourse.click();
			}
		}
		catch(Exception e){
			log.error("Exception In the method enrollForOpenBatch"+e.getMessage());
		}
	}
	public void navigateToElement( )throws InterruptedException
	{

		//try {
		for (WebElement ele : createMentorPage.courseImg)
		{
			GenericFunctions.waitWebDriver(1000);
			//courseName = ele.getText();
			ele.click();
			//System.out.println(courseName);
			GenericFunctions.switchToFrame(driver, createMentorPage.batchForm);
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.calendarStartDate.click();
			startDate = createMentorPage.calendarStartDate.getText();
			System.out.println(startDate);
			createMentorPage.calendarEndDate.click();
			endDate = createMentorPage.calendarEndDate.getText();
			System.out.println(endDate);
			System.out.println("bingo");
			createMentorPage.closeButton.click();	
			//CreateMentorPageObj createMentorPageObj =new CreateMentorPageObj();
			GenericFunctions.waitWebDriver(1000);
			//					createMentorPage.batchesDropDown.click();
			//					GenericFunctions.waitWebDriver(1000);
			//					createMentorPage.previousBatches.click();
		}

		//}
		//catch(Exception e)
		//{
		//	log.error("Exception In the method navigateToElement"+e.getMessage());

		//}
	}

	public String createInviteOnlyBatch() throws InterruptedException, Exception
	{
		String savedCourseName = null;
		//Robot robot = new Robot();
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		try{

			GenericFunctions.waitWebDriver(1500);
			createUserPage.headerCourse.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.filterSearch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());//+GenericFunctions.readFromNotepad(".//batchNumbers.txt").toUpperCase());
			createUserPage.searchIcon.click();
			GenericFunctions.waitWebDriver(2000);
			savedCourseName=createMentorPage.getCourseName.getText();
			System.out.println(savedCourseName);
			//createMentorPage.courseImg1.click();
			GenericFunctions.waitWebDriver(2000);
			createMentorPage.getCourseName.click();
			GenericFunctions.waitWebDriver(3000);
			createMentorPage.addIcon.click();
			GenericFunctions.waitWebDriver(1000);
			GenericFunctions.switchToFrame(driver, createMentorPage.batchForm);
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.nameOfBatch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName()+ GenericFunctions.testDataIncrementer(".//batchName.txt"));
			createMentorPage.aboutBatch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());
			/*createMentorPage.startDate.sendKeys(objListOFTestDataForSunbird.get(8).getTitle());
				createMentorPage.endDate.sendKeys(objListOFTestDataForSunbird.get(8).getTitleDescription());*/

			//Added on 31/07/2018
			createMentorPage.startDate.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.startDateCalendar.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.endDate.sendKeys(objListOFTestDataForSunbird.get(8).getTitleDescription());
			createMentorPage.mentorsInBatch.click();
			GenericFunctions.waitWebDriver(1000);
			//createMentorPage.mentorsInBatch.sendKeys(objListOFTestDataForSunbird.get(9).getCourseName());
			createMentorPage.suborgMentor2InBatch.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.mentorDropdown.click();
			createMentorPage.membersInBatch.click();
			GenericFunctions.waitWebDriver(1000);
			//createMentorPage.membersInBatch.sendKeys("test batch person");
			//GenericFunctions.scrollToElement(createMentorPage.selectMemberInBatch);
			GenericFunctions.waitWebDriver(2000);
			createMentorPage.suborgUser6InBatch.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.memberDropdown.click();
			GenericFunctions.waitWebDriver(2000);
			createMentorPage.aboutBatch.click();
			GenericFunctions.waitWebDriver(3000);
			//GenericFunctions.waitWebDriver(1000);
			//				for(int i=1;i<=7;i++)
			//				{
			//					robot.keyPress(KeyEvent.VK_TAB);
			//					robot.keyRelease(KeyEvent.VK_TAB);
			//				}
			//				GenericFunctions.waitWebDriver(1000);
			//
			//				robot.keyPress(KeyEvent.VK_ENTER);
			//				GenericFunctions.waitWebDriver(1000);
			//				robot.keyRelease(KeyEvent.VK_ENTER);
			//				GenericFunctions.waitWebDriver(3000);

			createMentorPage.buttonCreate.click();
			createMentorPage.successfulMessage.click();
			System.out.println("Batch Created");

		}
		catch(Exception e){
			log.error("Exception In the method createInviteOnlyBatch"+e.getMessage());

		}
		return savedCourseName;

	}
	public void createOpenBatch() throws InterruptedException, Exception
	{
		Robot robot = new Robot();
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		try{

			//				GenericFunctions.waitWebDriver(1500);
			//				createUserPage.headerCourse.click();
			//				GenericFunctions.waitWebDriver(1000);
			//				createMentorPage.filterSearch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName()+GenericFunctions.readFromNotepad("./batchNumbers.txt").toUpperCase());
			//				createUserPage.searchIcon.click();
			//				GenericFunctions.waitWebDriver(2000);
			//				createMentorPage.courseImg1.click();
			//GenericFunctions.waitWebDriver(3000);
			createMentorPage.addIcon.click();
			GenericFunctions.waitWebDriver(1000);
			GenericFunctions.switchToFrame(driver, createMentorPage.batchForm);
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.nameOfBatch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName()+ GenericFunctions.testDataIncrementer(".//dikshadata.txt"));
			createMentorPage.aboutBatch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());
			createMentorPage.openBatch.click();
			createMentorPage.startDate.click(); //sendKeys(objListOFTestDataForSunbird.get(8).getTitle());
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.startDateCalendar.click(); //sendKeys(objListOFTestDataForSunbird.get(8).getTitle());
			createMentorPage.endDate.sendKeys(objListOFTestDataForSunbird.get(8).getTitleDescription());
			GenericFunctions.waitWebDriver(3000);
			//GenericFunctions.waitWebDriver(1000);
			//				for(int i=1;i<=7;i++)
			//				{
			//					robot.keyPress(KeyEvent.VK_TAB);
			//					robot.keyRelease(KeyEvent.VK_TAB);
			//				}
			//				GenericFunctions.waitWebDriver(1000);
			//
			//				robot.keyPress(KeyEvent.VK_ENTER);
			//				GenericFunctions.waitWebDriver(1000);
			//				robot.keyRelease(KeyEvent.VK_ENTER);
			//				GenericFunctions.waitWebDriver(3000);

			createMentorPage.buttonCreate.click();
			//createMentorPage.successfulMessage.click();
			System.out.println("Batch Created");

		}
		catch(Exception e){
			log.error("Exception In the method createOpenBatch"+e.getMessage());
		}
	}
	public void createInviteOnlyBatchForSuborg() throws InterruptedException, Exception
	{

		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		try{

			GenericFunctions.waitWebDriver(1500);
			createUserPage.headerCourse.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.filterSearch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());//+GenericFunctions.readFromNotepad(".//batchNumbers.txt").toUpperCase());
			createUserPage.searchIcon.click();
			GenericFunctions.waitWebDriver(2000);
			createMentorPage.courseImg1.click();
			GenericFunctions.waitWebDriver(3000);
			createMentorPage.addIcon.click();
			GenericFunctions.waitWebDriver(1000);
			GenericFunctions.switchToFrame(driver, createMentorPage.batchForm);
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.nameOfBatch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName()+ GenericFunctions.testDataIncrementer(".//batchName.txt"));
			createMentorPage.aboutBatch.sendKeys(objListOFTestDataForSunbird.get(0).getCourseName());
			createMentorPage.startDate.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.startDateCalendar.click();
			GenericFunctions.waitWebDriver(500);
			createMentorPage.endDate.sendKeys(objListOFTestDataForSunbird.get(8).getTitleDescription());
			createMentorPage.mentorsInBatch.click();
			GenericFunctions.waitWebDriver(1000);
			//createMentorPage.mentorsInBatch.sendKeys(objListOFTestDataForSunbird.get(9).getCourseName());
			createMentorPage.suborgMentor2InBatch.click();
			GenericFunctions.waitWebDriver(1000);
			createMentorPage.mentorDropdown.click();
			GenericFunctions.waitWebDriver(500);
			createMentorPage.membersInBatch.click();
			GenericFunctions.waitWebDriver(1000);
			//createMentorPage.membersInBatch.sendKeys("test batch person");
			//GenericFunctions.scrollToElement(createMentorPage.selectMemberInBatch);
			GenericFunctions.waitWebDriver(2000);
			GenericFunctions generic = new GenericFunctions();
			boolean elementPresent = generic.isElementPresent(createMentorPage.orgAdminInBatch);
			if(elementPresent==false)
			{
				System.out.println("Batch not Created");
			}
			else
			{
				System.out.println("Batch is created");
			}

		}
		catch(Exception e){
			log.error("Exception In the method createInviteOnlyBatch"+e.getMessage());
			System.out.println("Exception In the method createInviteOnlyBatch"+e.getMessage());
		}
	}
	
	public void viewCourseStats()
	{
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(2000);
		createMentorPage.viewCourseStat.click();
		GenericFunctions.waitWebDriver(2000);
	}
}



