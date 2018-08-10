package org.sunbird.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class CreatorAnnouncementPage {
	WebDriver driver;
	
	public CreatorAnnouncementPage(WebDriver driver)
	{
		this.driver=driver;
		
	}
	
	
	@FindBy(xpath="//a[text()='Announcement dashboard']")
	public WebElement announcement_Dashboard;
	
	@FindBy(xpath="//span[text()='CREATE ANNOUNCEMENT']")
	public WebElement create_Announcement;
	
	@FindBy(xpath="//input[@placeholder=\"Please enter relevant title for this announcement (Max: 100 chars)\"]")
	//@FindBy(xpath="//div[@class='create-ann-form-scroller']//input[@class='ng-dirty ng-valid ng-touched' and @formcontrolname='title']")
	public WebElement enterTitle;
	
	@FindBy(xpath="//input[@placeholder='Please enter organisation/department that has sent the announcement']")
	//@FindBy(xpath="//div[@class='create-ann-form-scroller']//input[@class='ng-dirty ng-valid ng-touched' and @formcontrolname='from']")
	public WebElement enterOrg;
	
	//@FindBy(xpath="//sui-select[@class='selection ui dropdown ng-pristine ng-invalid ng-touched']")
	@FindBy(xpath="//div[text()='Select announcement type']")
	public WebElement announcementType;
	
	@FindBy(xpath="//sui-select-option[@class='item']/span[text()='Order']")
	public WebElement announcementTypeOrder;
	
	@FindBy(xpath="//sui-select-option[@class='item']/span[text()='Circular']")
	public WebElement announcementTypeCircular;
	
	//@FindBy(xpath="(//input[@placeholder='Please enter description for this announcement (Max: 1200 chars)'])[3]")
	@FindBy(xpath="//input[@formcontrolname='description']")
	public WebElement enterDescription;
	
	@FindBy(xpath="//span[@class='cursor-pointer blue-text-color-sunbird']")
	public WebElement addUrl;
	
	@FindBy(xpath="//input[@placeholder='Enter web url (starting with http or https)']")
	public WebElement enterUrl;
	
	//@FindBy(xpath="//span[text()='UPLOAD ATTACHMENT']")
	@FindBy(xpath="(//span[text()='UPLOAD ATTACHMENT'])[8]")
	public WebElement uploadDocument;
	
	@FindBy(xpath="//button[contains(text(),'SELECT RECIPIENTS')]")
	//@FindBy(xpath="(//button[contains(text(),'SELECT RECIPIENTS')])[2]")
	public WebElement selectRecipient;
	
	//@FindBy(xpath="//input[@placeholder='Search for location' and @class='ng-valid ng-touched ng-dirty']")
	@FindBy(xpath="//input[@placeholder='Search for location']")
	public WebElement searchForLocation;
	
	@FindBy(xpath="//sui-checkbox[@class='ui checkbox ng-untouched ng-pristine ng-valid']")
	public WebElement checkLocation;
	
	@FindBy(xpath="//div[text()='CONFIRM RECIPIENTS']")
	public WebElement confirmRecipients;
	
	@FindBy(xpath="//span[text()='BACK']")
	public WebElement back;
	
	@FindBy(xpath="//div[text()='PREVIEW ANNOUNCEMENT']")
	public WebElement previewAnnouncement;
	
	@FindBy(xpath="//span[contains(text(),'VIEW')]")
	public WebElement view;
	
	@FindBy(xpath="//button[contains(text(),'SEND ANNOUNCEMENT')]")
	public WebElement sendAnnouncement;
	
	@FindBy(xpath="//button[@class='ui primary basic button announcement-button btn-width-34 approve']")
	public WebElement announcementcreatedConfirmation;
	
	@FindBy(xpath="//button[@class=\"ui basic button announcement-button btn-width-26 announcement-button-text\"]")
	public WebElement stopCreatingAnnouncementMessageYes;
	
	@FindBy(xpath="//button[@class=\"ui button primary announcement-button btn-width-26 close\"]")
	public WebElement stopCreatingAnnouncementMessageNo;
	
	
	@FindBy(xpath="//div[@class=\"item headerDropdownProfile\"]")
	public WebElement userNameInDropDown;
	
	@FindBy(xpath="//a[text()='Profile']")
	public WebElement profileInDropDown;
	
	@FindBy(xpath="//a[text()='Workspace']")
	public WebElement workspaceInDropDown;
	
	@FindBy(xpath="//a[text()='My Activity']")
	public WebElement myActivityInDropdown;
	
	@FindBy(xpath="//td[@class=\"cursor-pointer red-text-color-sunbird\"]")
	public WebElement deleteAnnouncementButton;
	
	@FindBy(xpath="//td[@class=\"cursor-pointer red-text-color-sunbird\"]/../td[1]")
	public WebElement selectAnnouncementName;
	
	@FindBy(xpath="//div[@class=\"header padding-top-5 segment-heading\"]")
	public List<WebElement> reviewAnnouncement;
	
	@FindBy(xpath="//a[text()='See All']")
	public WebElement seeAllAnnouncement;
	
	@FindBy(xpath="//i[@class=\"right chevron icon\"]")
	public WebElement nextarrowOnHomePage;
	
	@FindBy(xpath="//table[@class=\"ui padded table\"]/thead/tr/th[1]")
	public WebElement announcementTableName;
	
	@FindBy(xpath="//table[@class=\"ui padded table\"]/thead/tr/th[6]")
	public WebElement announcementTableActions;
	
	@FindBy(xpath="//table[@class=\"ui padded table\"]//tr[@class=\"table-list-name-ann\"]/td")
	public WebElement announcementTableNameContent;
	
	@FindBy(xpath="//table[@class=\"ui padded table\"]//td[@class=\"cursor-pointer blue-text-color-sunbird\"]")
	public WebElement announcementTableActionsContent;
	
	//@FindBy(xpath="//div[@class='padding-top-10 segment-heading']")
	@FindBy(xpath="//div[@class='header padding-top-5']/div")
	public WebElement announcementReview;
	
	
	@FindBy(xpath="//td[@class=\"cursor-pointer blue-text-color-sunbird\"]")
	public WebElement resendAnnouncementButton;
	
	@FindBy(xpath="//td[@class=\"cursor-pointer blue-text-color-sunbird\"]/../td[1]")
	public WebElement resendAnnouncementName;
	
	@FindBy(xpath="//div[@class=\"header padding-top-5 segment-heading\"]")
	public WebElement reviewAnnouncementN;
	
	@FindBy(xpath="//i[@class='close icon']")
	public WebElement popupCloseIcon;
	

}
