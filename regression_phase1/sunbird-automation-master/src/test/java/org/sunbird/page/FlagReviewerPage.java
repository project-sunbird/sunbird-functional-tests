package org.sunbird.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class FlagReviewerPage 
{
	WebDriver driver;

	public  FlagReviewerPage(WebDriver driver)
	{ 
		this.driver=driver; 
	}

	@FindAll(@FindBy(xpath="//h4[contains(text(),'Latest Courses')]/..//div[@class='cardImageText center aligned ']//span"))
	public List<WebElement> courseList;
	
	@FindBy(xpath="//h4[contains(text(),'My Courses')]/..//div[@class='cardImageText center aligned ']//span")
	public List<WebElement> courseMy;
	
	@FindBy(xpath="(//h4[contains(text(),'My Courses')]/..//div[@class='cardImageText center aligned ']//span)[5]")
	public WebElement courseMyFifth;
	
	@FindBy(xpath="//h4[contains(text(),'My Courses')]")
	public WebElement myCourses;
	
	@FindBy(xpath="//h4[contains(text(),'Latest Courses')]")
	public WebElement latestCourses;
	
	@FindBy(xpath="//h4[contains(text(),'Courses')]/..//div[@class='cardImageText center aligned ']//span")
	public List<WebElement> courseMyCourses1;
	
	@FindBy(xpath="//h4[contains(text(),'Courses')]/..//div[@class='cardImageText center aligned ']//span")
	public WebElement courseMyCourses2;
	
	@FindBy(xpath="//h4[contains(text(),'My Courses')]/..//span[@class='sliderCardHeading text-cencapitalize']")
	public WebElement courseLatestCourses;
	
	@FindBy(xpath="//h4[contains(text(),'My Courses')]/..//button[@class='slick-next slick-arrow']")
	public WebElement myCourseNextuBtton;
	
	@FindBy(xpath="//h4[contains(text(),'Latest Courses')]/..//button[@class='slick-next slick-arrow']")
	public WebElement latestCourseNextuBtton;
		
	@FindBy(xpath="//i[@class='ui large blue flag outline icon']")
	public WebElement flagIcon;
	
	@FindBy(xpath="//input[@type='radio']")
	public List<WebElement> flagReason;
	
	@FindBy(name="addYourComment")
	public WebElement addComment;
	
	public String [] randomComments = new String[]{"Not a proper course","Invalid course","no course contents","Empty course"};
	
	@FindBy(xpath="//button[contains(text(),'SUBMIT')]")
	public WebElement submitButton;
	
	//Updated on 9/07/2018
	@FindBy(xpath="//div[@class='cardImageText center aligned ']//span")
	//public List<WebElement> contentsSearched;
	public WebElement contentsSearched;
	
	@FindBy(xpath="//span[@class='ui HomeAccordianHeading left floated header']")
	public WebElement clikedCourseContentName;
	
	@FindBy(xpath="//h4[contains(text(),'Popular Books')]")
	public WebElement popularBooksHeader;
	
	@FindBy(xpath="//div[@class='cardImageText center aligned ']//span")
	public List<WebElement> coursesSearched;
	
	@FindBy(xpath="//button[contains(text(),'DISCARD')]")
	public WebElement discardButton;
	
	@FindBy(xpath="//div[@class='publicmenusection ui secondary vertical menu']//a[9]")
	public WebElement flaggedContent;
	
	@FindBy(xpath="//div[@class='publicmenusection ui secondary vertical menu']//a[7]")
	public WebElement upForReview;
	
	@FindBy(xpath="(//div[@class='ui link cards margin-right-20 margin-top-15 margin-bottom-0'])[position()=2]")
	public WebElement clickFlagContent;
	
	@FindBy(xpath="//p[.='Loading Editor...']")
	public WebElement loadingSymbol;
	
	//Adding additional elements for Test case 2
	
	@FindBy(xpath="//div//h6[@class='ui small negative message']")
	public WebElement courseFlaggedMessage;
	
	@FindBy(xpath="//a[contains(text(),'Flagged')]")
	public WebElement flagged;
	
	@FindBy(xpath="//button[contains(text(),'ACCEPT')]")
	public WebElement acceptBlueButton;
	
	@FindBy(xpath="//span[.='Accept']")
	public WebElement acceptGreyButton;
	
	@FindBy(xpath="//span[.='Discard']")
	public WebElement discardGreyButton;
	
	@FindBy(xpath="//button[contains(text(),'DISCARD')]")
	public WebElement discardBlueButton;
	
	@FindBy(xpath="//button//span[.='Resume']")
	public WebElement courseResume;
	
	@FindBy(xpath="//div[@class='cardImageText center aligned ']//span")
	public WebElement searchedCourse;
	
	//Adding elements for Test case 4
	
	@FindBy(xpath="//a[@data-content='Add Slide']")
	public WebElement addSlide;
	
	@FindBy(xpath="//label[@class='content-title popup-item ng-binding ng-scope']")
	public WebElement contentTitleTopLeft;
}
