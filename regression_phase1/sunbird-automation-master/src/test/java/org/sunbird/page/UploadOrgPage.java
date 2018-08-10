package org.sunbird.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class UploadOrgPage {

	WebDriver driver;

	public UploadOrgPage(WebDriver driver)
	{ 
		this.driver=driver; 
	}

	@FindBy(xpath="//button[.='LOGIN']")
	public WebElement Loginbutton;

	@FindBy(id="username")
	public WebElement username;

	@FindBy(id="password")
	public WebElement password;

	//@FindBy(xpath="//button[.='Login']")
	@FindBy(id="kc-login")
	public WebElement clickLogin;
	
	@FindBy(name="login")
	public WebElement clickLogin1;

	@FindBy(id="dropdown-menu-list-header")
	public WebElement dropdown;

	@FindBy(xpath="//a[contains(text(),'Profile')]")
	public WebElement clickProfileIcon;

	@FindBy(xpath="//a[contains(text(),'Check Upload Status')]")
	public WebElement uploadStatus;

	@FindBy(xpath="//a[contains(text(),'Upload Organizations')]")
	public WebElement organizationUpload;

	@FindBy(xpath="//button[contains(text(),'UPLOAD ORGANIZATIONS CSV')]")
	public WebElement uploadCSV;

	@FindBy(xpath="//strong[.='Invalid column name.']")
	public WebElement invalidUpload;

	//@FindBy(className="iziToast-close")//
	//@FindBy(xpath="//div[@class='iziToast-texts']/strong[contains(text(),'Invalid column:')]")
	@FindBy(xpath="//div[@class='iziToast-texts']/strong[@class='iziToast-title slideIn']")
	public WebElement uploadErrorToast;

	//@FindBy(xpath="//div[.='Upload Organizations']/../div[@class='one wide column actions']")
	@FindBy(xpath="//i[@class='close icon']")
	public WebElement closePopup;

	@FindBy(xpath="//div[4]//a[.='Course']")
	public WebElement elementpresent; 

	@FindBy(xpath="//div[@class='WorkSpaceTextContent']")
	public WebElement elementWorkspace; 

	@FindBy(xpath="//p[contains(text(),'Process ID:')]")
	public WebElement processID;
	
	//Updated on 30 May 2018
	//@FindBy(xpath="//a[.='Upload Users']")
	@FindBy(xpath="//a[contains(text(),'Upload Users')]")
	public WebElement uploadUsers;
	
	@FindBy(xpath="//input[@placeholder='OrgId']")
	public WebElement clickOrgId;
 
	//Updated on 30 May 2018
	//@FindBy(xpath="//button[.='UPLOAD USERS CSV']")
	@FindBy(xpath="//button[contains(text(),'UPLOAD USERS CSV')]")
	public WebElement uploadUsersCsv;
	 
	//@FindBy(xpath="//a[.='Check Upload Status']")
	
	//Updated on 30 May 2018
	@FindBy(xpath="//a[contains(text(),'Check Upload Status')]")
	public WebElement checkUploadStatus;
	
	@FindBy(xpath="//input[@placeholder='Process ID']")
	public WebElement cProcessId;
	
	//@FindBy(xpath="//thead[@class='ng-scope']/..//td[@class='ng-binding'][1]")
	//Updated on 30 May 2018
	@FindBy(xpath="//table//tbody//td[1]")
	public WebElement getOrgId;
	
	@FindBy(xpath="//h5[contains(.,'Uploaded File: ')]")
	public WebElement uploadedRUser;
	
	@FindBy(xpath="//button['CHECK STATUS']")
	public WebElement clickCheckStatus;
	
	//Updated on 30 May 2018
	//@FindBy(xpath="//div[@id='statusBulkUpload']//i")
	@FindBy(xpath="//i[@class='close icon']")
	public WebElement closeWindow;
	
	@FindBy(xpath="//div[@id='userBulkUpload']/..//i[@class='ui remove icon']")
	public WebElement closeWindow1;
	
	//Updated both xpaths of externalId and provider
	//@FindBy(xpath="//thead[@class='ng-scope']/..//td[@class='ng-binding'][5]")
	@FindBy(xpath="//table//tbody//td[5]")
	public WebElement externalId;
	
	//@FindBy(xpath="//thead[@class='ng-scope']/..//td[@class='ng-binding'][6]")
	@FindBy(xpath="//table//tbody//td[6]")
	public WebElement provider;
	
	//Updated on 31 may
	//@FindBy(xpath="//input[@placeholder='External Id']")
	@FindBy(xpath="//input[@placeholder='OrgExternalId']")
	public WebElement uploadUserExternal_Id;
	
	
	//@FindBy(xpath="//input[@placeholder='Provider']")
	@FindBy(xpath="//input[@placeholder='OrgProvider']")
	public WebElement uploadUserProvider;
	
	@FindBy(xpath="//a[.='Admin dashboard']")
	//@FindBy(xpath="//div[@class='menu transition hidden']//a[.='Admin dashboard']")
	public WebElement clickAdminDashboardIcon;
	
	//@FindBy(xpath="//div[.='Select Organization']")
	@FindBy(xpath="//div[@class='ui dropdown']//div[.='Select Organization']")
	public WebElement selectOrganizationDropdown;
	
	@FindBy(xpath="//div[contains(text(),'ORG25')]")
	public WebElement selectOrg;
	
	@FindBy(xpath="//span[1]//i[@class='dropdown icon']")//  //div[@ng-reflect-ng-class='[object Object]'][text()='Creation']
	public WebElement rDropdownList;
	
	@FindBy(xpath="//div[contains(text(),'Creation')]")
	public WebElement clickCreation;
	
	@FindBy(xpath="//div[.='Consumption']")
	public WebElement clickConsumption;
	
	@FindBy(xpath="//div[@class='meta dashBoardCardText ng-binding']")
	public String dashboardCards;
	
	@FindBy(xpath="//div[.='Number of contents created']")
	public WebElement contentsCreated;
	
	@FindBy(xpath="//div[.='Number of authors']")
	public WebElement authors;
	
	@FindBy(xpath="//div[.='Number of reviewers']")
	public WebElement reviewers	;
	
	//@FindBy(xpath="//a[.='CSV']")
	@FindBy(xpath="//a[contains(text(),'CSV')]")
	public WebElement csvLink;
	
	@FindBy(xpath="//i[@class='close icon']")
	public WebElement closeThanks;
	
	@FindBy(xpath="//span[contains(text(),'LAST 14 DAYS')]")
	public WebElement last14Days;
	
	@FindBy(xpath="//span[contains(text(),'LAST 5 WEEKS')]")
	public WebElement last5Weeks;
	
	@FindBy(xpath="//div[.='Number of visits by users']")
	public WebElement visits;
	
	@FindBy(xpath="//div[.='Content consumption time']")
	public WebElement consumptionTime;
	
	@FindBy(xpath="//div[.='Average time spent by user per visit']")
	public WebElement avgTime;
	
//	@FindBy(xpath="//a[.='Logout']")
	@FindBy(xpath="//div[@id='dropdown-menu-list-header']//a[.='Logout']")
	public WebElement logout;

}




