package org.sunbird.page;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sunbird.startup.BaseTest;

public class SignUp extends BaseTest {
	WebDriver driver;
	
	@FindBy(xpath="//div/button[.='SIGN UP']")
	public WebElement Sbtn;
	
	@FindBy(name="userName")
	public WebElement Un;
	
	@FindBy(name="password")
	public WebElement Pw;
	
	@FindBy(name="firstName")
	public WebElement FName;
	
	@FindBy(xpath="//div/input[@placeholder='Last Name']")
	public WebElement LName;
	
	@FindBy(name="phone")
	public WebElement Phone;
	
	@FindBy(name="email")
	public WebElement Email;
	
	@FindBy(xpath="//div[.='SIGN UP']")
	public WebElement Sign; 
	
	@FindBy(xpath="//i[@class='dropdown icon']")
	public WebElement Language;
	
	@FindBy(xpath="//div[.='English']")
	public WebElement LangS;
	

}

