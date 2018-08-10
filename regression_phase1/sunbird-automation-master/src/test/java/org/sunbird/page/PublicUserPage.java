package org.sunbird.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;


public class PublicUserPage 
{
	WebDriver driver;

	public  PublicUserPage(WebDriver driver)
	{ 
		this.driver=driver; 
	}

	@FindBy(xpath="//input[@id='password-new']")
	public WebElement newPassword;
	
	@FindBy(xpath="//span[@class='kc-feedback-text']")
	public WebElement passwordMessage;

	@FindBy(xpath="//input[@id='password-confirm']")
	public WebElement confirmPassword;

	@FindBy(xpath="//input[@name='username']")
	public WebElement enterUsername;

	@FindBy(xpath="//a[.='Forgot Password?']")
	public WebElement forgotPasswordLink;
	
	@FindBy(xpath="//button[contains(text(),'Submit')]")
	public WebElement forgotSubmit;
	
	@FindBy(xpath="//form[@id='kc-totp-login-form']/..//lable")
	public WebElement getMailConfirmation;
	
	@FindBy(xpath="//a[@id='backToApplication']")
	public WebElement backToApplicationLink;
	
	@FindBy(xpath="//span[.='user_not_found']")
	public WebElement userNotFound;
	
	@FindBy(xpath="//a[contains(text(),'Home')]")
	public WebElement headerHome;
	
	@FindBy(xpath="//a[contains(text(),'Courses')]")
	public WebElement headerCourses;
	
	@FindBy(xpath="//a[contains(text(),'Library')]")
	public WebElement headerLibrary;
	
	@FindBy(xpath="//a[contains(text(),'Groups')]")
	public WebElement headerGroups;
	
	//Added on 18/07/2018
	//Adding Test case 4
	
	@FindBy(xpath="//span[@class='ui header accordian-heading']")
	public WebElement toDo;
	
	@FindBy(xpath="//span[@class='todolabel ui red circular inline label ']")
	public WebElement toDoCount;
	
	@FindBy(xpath="//p[@class='serch-allresult']")
	public WebElement showResults;
	
//	@FindBy(xpath="//div//a[5][contains(text(),'Profile')]")

//	Updated the xpath of Header Profile on June 18,2018
	@FindBy(xpath="//div[@class='ui secondary pointing menu']//a[contains(text(),'Profile')]")
	//Updated on June 15 
//	@FindBy(xpath="//div//a[5][contains(text(),'Profile')]")
	public WebElement headerProfile;
	
	@FindBy(xpath="//div[.='Announcements']")
	public WebElement announcements;
	
	@FindBy(xpath="//input[@placeholder='Search'][@id='keyword']")
	public WebElement searchBar;
	
	@FindBy(xpath="//i[@class='circular search link icon']")
	public WebElement clickSearch;
	
	@FindBy(xpath="//p//span[contains(text(),'for ')]")
	public WebElement searchedInputResult;
	
	@FindBy(xpath="//a[@class='ui black right ribbon label']")
	public WebElement searchedResults;
	
	@FindBy(xpath="//i[@class='icon filter']")
	public WebElement filterIcon;
	
	@FindBy(xpath="//sui-multi-select[@id='subject']//i[@class='dropdown icon']")
	public WebElement clickFilterSubject;
	
	@FindBy(xpath="//sui-multi-select[@id='subject']//div//span[.='English']")
	public WebElement selectFilterSubject;
	
	@FindBy(xpath="//sui-multi-select[@id='board']//i[@class='dropdown icon']")
	public WebElement clickFilterBoard;
	
	
	@FindBy(xpath="//sui-multi-select[@id='gradeLevel']//i[@class='dropdown icon']")
	public WebElement clickFilterClass;
	
	@FindBy(xpath="//sui-multi-select[@id='gradeLevel']/..//span[.='Class 1']")
	public WebElement selectFilterClass;
	
	@FindBy(xpath="//sui-multi-select[@id='medium']//i[@class='dropdown icon']")
	public WebElement clickFilterMedium;
	
	@FindBy(xpath="//sui-multi-select[@id='contentType']")
	public WebElement clickContentTypes;
	
	@FindBy(xpath="//input[contains(text(),'Concepts ')]")
	public WebElement clickConcepts;
	

	@FindBy(xpath="//div//span[.='CBSE']")
	public WebElement selectFilterBoard;
	
	@FindBy(xpath="//sui-multi-select[@id='medium']//div//span[.='English']")
	public WebElement selectFilterMedium;
	
	@FindBy(xpath="//div[@class='ui search form']//input[@type='text'][@placeholder='Search']")
	public WebElement clickSearchConcepts;
	
	/*@FindBy(xpath="//a[@class='name'][text()='D.Ed._']")
	public WebElement clickSearchResult;
	
	@FindBy(xpath="//a[@class='name'][text()='D.Ed._Social_']")
	public WebElement clickSearaResult1;*/
	
	@FindBy(xpath="//a[.='Done']")
	public WebElement clickDone;
	
	@FindBy(xpath="//a[.='APPLY']")
	public WebElement clickApply;
	
	@FindBy(xpath="(//a[@class='ui label margin-top--5'])[5]")
	public WebElement contentType;
	
	@FindBy(xpath="//sui-multi-select[@id='gradeLevel']//i[@class='dropdown icon']")
	public WebElement clickGradeFilter;
	
	@FindBy(xpath="//sui-select-option//span[.='Grade 2']")
	public WebElement selectFilterGrade;
	
	@FindBy(xpath="//span[.='No results found']")
	public WebElement noResults;
	
	@FindBy(xpath="//i[@class='delete icon']")
	public List<WebElement> clearAllFilters;
	
	@FindBy(xpath="//i[@class='edit icon']")
	public WebElement imageEditIcon;
	
	@FindBy(xpath="//span[.='Contributions']")
	public WebElement profileContributions;
	
	@FindBy(xpath="//span[.=' Summary ']/../a[.='EDIT']")
	public WebElement summaryEditButton;
	
	@FindBy(tagName="textarea")
	public WebElement summaryText;
	
	@FindBy(tagName="button")
	public WebElement summarySave;//button[.='SAVE ']
	
	@FindBy(xpath="//span[.=' Experience ']/../a[.='ADD']")
	public WebElement experienceAddButton;
	
	@FindBy(name="jobName")
	public WebElement eOccupationField;
	
	@FindBy(xpath="//input[@formcontrolname='role']")
	public WebElement eDesignationField;
	
	@FindBy(xpath="//input[@formcontrolname='orgName']")
	public WebElement eOrganizationField;
	
	@FindBy(xpath="//sui-multi-select[@formcontrolname='subject']")
	public WebElement eClickSubjectDropdown;
	
	@FindBy(xpath="//sui-select-option//span[.='Assamese']")
	public WebElement eSelectSubjectDropdown;
	

	//@FindBy(xpath="//input[@formcontrolname='isCurrentJob']/../label[.='Yes']")
	@FindBy(xpath="(//input[@formcontrolname='isCurrentJob'])[1]")
	public WebElement eSelectRadioButton;
	
	//@FindBy(xpath="html/body/sui-popup/div/sui-datepicker/sui-calendar-date-view/table/tbody/tr[4]/td[1]")
	@FindBy(linkText="18")
	public WebElement eSelectJoinDate;
	
	@FindBy(xpath="//input[@formcontrolname='joiningDate']")
	public WebElement eClickJoinDate;
	
	@FindBy(xpath="//p[.='permanent']/../div[@class='description']")
	public WebElement checkAddressStatus;
	
	@FindBy(xpath="//span[.='Address ']/../a[.='EDIT']")
	public WebElement addressEditButton;
	
	@FindBy(xpath="//input[@id='addTypeCurrent']")
	public WebElement selectCurrentRadioButton;
	
	@FindBy(xpath="//input[@name='addressLine1']")
	public WebElement addressLine1;
	
	@FindBy(xpath="//input[@name='addressLine2']")
	public WebElement addressLine2;
	
	@FindBy(xpath="//input[@name='city']")
	public WebElement aCity;
	
	@FindBy(xpath="//input[@name='state']")
	public WebElement aState;
	
	@FindBy(xpath="//input[@name='country']")
	public WebElement aCountry;
	
	@FindBy(xpath="//input[@name='zipcode']")
	public WebElement aZipcode;
	
	@FindBy(xpath="//span[.='Education ']/../a[.='ADD']")
	public WebElement educationEditButton;
	
	@FindBy(xpath="//input[@name='degree']")
	public WebElement eDegree;
	
	@FindBy(xpath="//input[@name='percentage']")
	public WebElement ePercentage;
	
	@FindBy(xpath="//input[@name='grade']")
	public WebElement eGrade;
	
	@FindBy(xpath="//input[@name='name']")
	public WebElement eInstitution;
	
	@FindBy(xpath="//input[@name='boardOrUniversity']")
	public WebElement eBoard;
	
	@FindBy(xpath="//input[@name='yearOfPassing']")
	public WebElement eYOP;
	
	@FindBy(xpath="//span[.='Skill Tags ']/../a[.='ADD']")
	public WebElement skillAddButton;
	
	@FindBy(xpath="//span[.='Add Skills']")
	public WebElement addSkillsHeader;
	
	@FindBy(xpath="//div[@id='addSkill']")
	public WebElement addSkills;
	
	@FindBy(xpath="//sui-multi-select[@name='skills']")
	public WebElement selectSkills;
	
	@FindBy(xpath="//button[contains(text(),'FINISH')]")
	public WebElement finishButton;
	
	@FindBy(xpath="//span[.=' Additional Information']/../a[.='EDIT']")
	public WebElement additionalInfoEdit;
	
	@FindBy(xpath="//input[@name='firstName']")
	public WebElement aFirstName;
	
	@FindBy(xpath="//input[@name='lastName']")
	public WebElement aLastName;
	
	@FindBy(xpath="//input[@name='location']")
	public WebElement aLocation;
	
	@FindBy(xpath="//span[.='Skill Tags ']/..//div//i[@class='lock icon']")
	public WebElement skillLockIcon;
	
	@FindBy(xpath="//div[@class='text']//span[.='All']")
	public WebElement headerDropdown;
	
	@FindBy(xpath="//sui-select-option//span[.='Users']")
	public WebElement headerDropdownUsers;
	
	/*@FindBy(xpath="//a[.='TestFlagReviewer  N']")
	public WebElement searchedEndorseUser;
	*/
	
	@FindBy(xpath="//span[.='Skill Tags ']/..//div//i[@class='unlock alternate icon']")
	public WebElement SkillUnlockIcon;
	
	@FindBy(xpath="//a[contains(text(),'+1')]")
	public WebElement endorsementIcon;
	
	@FindBy(xpath="//span[contains(text(),'Skill Tags')]")
	public WebElement skillTag;
	
	@FindBy(xpath="//div[@class='detail']//span[2]")
	public List<WebElement> eachSkillTagSize;
	
	@FindBy(xpath="//div[@class='cardImageText center aligned ']//span[contains(text(),'Course')]")
	public 	List<WebElement> searchedCourses;
	
	@FindBy(xpath="//span[@class='sliderCardHeading text-cencapitalize']")
	public WebElement searchedCourses1;
	
	@FindBy(xpath="//div[@class='cardImageText center aligned ']//span")
	public WebElement courseToBeClicked;
	
	//Added for Test case 8 -18/07/2018
	@FindBy(xpath="//div[@class='no-result-text']")
	public WebElement noResultsFound;
	
	//Added for Test case 13 - 20/07/2018
	@FindBy(xpath="//a[.='Last']")
	public WebElement lastButton;
	
	//Added for test case 14 - 20/07/2018
	@FindBy(xpath="//div[@class='sectionHeading header']")
	public WebElement getUsername;
	
	@FindBy(xpath="//a[@class='header ']")
	public WebElement getResultsUsername;
	
	@FindBy(xpath="//span[@class='cursor-pointer']")
	public WebElement viewMoreSkills;
	
	@FindBy(xpath="//div[@class='detail']//span[2]")
	public WebElement endorsementCount;
	
	@FindBy(xpath="//div[@class='addition item selected']")
	public WebElement addSkill;
	
	//Added for Test case 7-20/07/2018
	
	@FindBy(xpath="//span[.='Resume']")
	public WebElement courseResumeButton;
}	



