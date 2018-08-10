package org.sunbird.generic;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.startup.BaseTest;
import org.testng.Assert;



public class GenericFunctions extends BaseTest{
	
	public Select select;
	public Action action;

	static Logger log = Logger.getLogger(GenericFunctions.class.getName());
	
	public static void selectValueFromDropdown(WebDriver driver,String locator, String text)
	{	
	
		WebElement lang = driver.findElement(By.xpath(locator));
		List<WebElement> list = lang.findElements(By.xpath(locator));
		for (WebElement opt : list)
		{
			String value = opt.getText();
			if(value.equalsIgnoreCase(text)){
				System.out.println("Value clicked ="+value);
				opt.click();
			}
		}		
	}

	public static void setClipboardData(String string) 
	{
		//StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public static void uploadFile(String fileLocation) 
	{
		try {
			//Setting clipboard with file location
			setClipboardData(fileLocation);
			//native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception exp) {
			System.out.println("Failed uploading document");
			exp.printStackTrace();
		}
	}

	public static void writeNotepad(String processID,String fileName)
	{
		try 
		{
			BufferedWriter bWriter=new BufferedWriter(new FileWriter(fileName));
			bWriter.write(processID);
			System.out.println("Write ID: "+processID);
			bWriter.newLine();
			bWriter.close();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	@SuppressWarnings("resource")
    public static String readFromNotepad(String fileName){
        String path = "";
        try {
            BufferedReader bReader=new BufferedReader(new FileReader(fileName));
            while((path=bReader.readLine())!=null){
                System.out.println("Read ID: "+path);
                String str = bReader.readLine();
               // System.out.println(str);
                return path;
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
		return path;
    }
	
	public static void waitTillTheElementIsVisibleAndClickable(WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitTillTheElementInVisible(WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForPageToLoad(WebElement id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    public static void waitForElementState(WebElement id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.stalenessOf(id));

        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    public static void waitForPageToLoad(List<WebElement> id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfAllElements(id));
    }

    public static void waitForElementToDisAppear(List<WebElement> id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOfAllElements(id));
    }

    public static void waitForElementToDisAppear(List<WebElement> id, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.invisibilityOfAllElements(id));
    }

    public static WebElement waitForElementToAppear(WebElement id) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOf(id));
        return id;
    }
    

    public WebElement waitForElementToAppear(WebElement id, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(id));
        return id;
    }

    public WebElement waitForElement(WebElement arg) {
        waitForPageToLoad(arg);
        WebElement el = arg;
        return el;
    }

    public static void WaitForFrameAndSwitchToIt(WebElement frame){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public static void WaitForFrameAndSwitchToIt(int id){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(id));
    }

    public static void scrollToElement(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitForElements(List<WebElement> arg) {
        waitForPageToLoad(arg);
    }

    public static WebElement waitForElementToAppearOnScreen(WebElement arg) {
        waitForElementToAppear(arg);
        WebElement el = arg;
        return el;
    }

    public static void clickUntilElementExists(WebElement clickLocator, By by) {
        boolean elementOnScreen;
        int i = 0;
        do {
            if (i == 25) {
                break;
            }
            try {
                driver.findElement(by);
                break;
            } catch (NoSuchElementException e) {
                clickLocator.click();
                elementOnScreen = false;
                System.out.println(i);
            }
            i++;
        } while (!elementOnScreen);
    }

    public String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
     }

    public String withBoldHTML(String string) {
        if (!string.trim().isEmpty()) {
            return "<b>" + string + "</b>";
        } else {
            return "";
        }
    }


    public boolean isElementPresent(WebElement locator) {
        try {
            if (locator.isDisplayed())
                System.out.println("Element presend on screen ***********" + locator);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element not present on screen **************" + locator);
            return false;
        }
    }

    public String getCurrentMonth(int month) {
        int i = Calendar.getInstance().get(Calendar.MONTH);
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month + i];
    }

    public static void refreshWebPage() {
        driver.navigate().refresh();
        GenericFunctions.waitWebDriver(1500);
    }

    //Method to wait for time
    public static void waitWebDriver(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Method: waitWebDriver :: exception =  " + e.getMessage());

        }
    }

    public static void switchToNewWindow() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }
    
    public static String genrateRandomInputs(String getRandomIp)
	{
	char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	StringBuilder sb = new StringBuilder(20);
	Random random = new Random();
	for (int i = 0; i < 10; i++) {
	    char c = chars[random.nextInt(chars.length)];
	    sb.append(c);
	}
	String output = sb.toString();
	System.out.println(output);
	return output;
	}
	
  //Method to switchToFrame
  	public static void switchToFrame(WebDriver mDriver,WebElement frame){
  		try 
  		{
  			mDriver.switchTo().frame(frame);
  		} catch (Exception e) {
  			log.error("Method: switchToFrame :: Exception is = " + e.getMessage() );
  		}
  	}
  	
  //Method Tab Operation
  	public static void keyTab(WebDriver mDriver,String locator)	{
  		
  		try	{
  			log.info("MethodName: keyTab :: element found = " + locator);
  			
  			mDriver.findElement(By.cssSelector(locator)).sendKeys(Keys.TAB);
  		} catch(Exception e) {
  			log.error("MethodName: TabKeys :: Exception occured for locator = " + locator + "exception = "+ e.getMessage());
  		}
  	}
  	
 // Method to get value of element
 	public static String getValue(WebDriver mDriver, String identifyBy, String locator) {
 		boolean assertElementExists = false;
 		String value = "";

 		try	{
 			assertElementExists = assertIfElementExists(mDriver, identifyBy, locator);
 			log.info("Method: getValue :: assertElementExists = " + assertElementExists + " for xpath = " + locator);
 			if (assertElementExists==true) {	
 				if (identifyBy.equalsIgnoreCase("xpath")) 
 					value = mDriver.findElement(By.xpath(locator)).getText();
 				else if (identifyBy.equalsIgnoreCase("id")) 
 					value = mDriver.findElement(By.id(locator)).getText();
 				else if (identifyBy.equalsIgnoreCase("name")) 
 					value = mDriver.findElement(By.name(locator)).getText();
 				else if (identifyBy.equalsIgnoreCase("css")) 
 					value = mDriver.findElement(By.cssSelector(locator)).getText();
 			}
 		} catch(Exception e) {
 			log.error("Method: getValue :: Exception occured for locator = " + locator + "exception = "+ e.getMessage());
 			// e.printStackTrace();
 		}
 		log.info("Method: getValue :: value of element = " + value);
 		//System.out.println("Method: getValue :: value of element = " + value); 
 		return value.trim();
 	}
 	

	// Method to check if element exists on page
	public static boolean assertIfElementExists(WebDriver mDriver, String identifyBy, String locator) throws Exception {
		boolean bElementFound = false;
		try	{		
			if (identifyBy.equalsIgnoreCase("xpath")){ 
				bElementFound = mDriver.findElement(By.xpath(locator)).isDisplayed();
			}else if (identifyBy.equalsIgnoreCase("id")){ 
				bElementFound = mDriver.findElement(By.id(locator)).isDisplayed();
			}else if (identifyBy.equalsIgnoreCase("name")){ 
				bElementFound = mDriver.findElement(By.name(locator)).isDisplayed();
			}else if (identifyBy.equalsIgnoreCase("css")){ 
				bElementFound = mDriver.findElement(By.cssSelector(locator)).isDisplayed();
			}else if (identifyBy.equalsIgnoreCase("linktext")){ 
				bElementFound = mDriver.findElement(By.linkText(locator)).isDisplayed();
			}else if (identifyBy.equalsIgnoreCase("partialtext")){ 
				bElementFound = mDriver.findElement(By.partialLinkText(locator)).isDisplayed();
			}else {
				log.info("None of the Element Selected Please check the Element Once: ");
			}
		} catch(Exception e){
			log.error("Method: assertIfElementExists :: Exception occured for locator = " + locator + " exception is = "+ e.getMessage());
			AssertJUnit.assertTrue(false);
			
		}
		log.info("Method: assertIfElementExists for locator = " + locator  + " status = " + bElementFound);	
		return bElementFound;
	}

  	public static boolean assertElementValue(WebDriver mDriver, String identifyBy, String locator, String expectedValue) {
		String actualElementValue = "";
		actualElementValue =  getValue(mDriver, identifyBy, locator);
		expectedValue = expectedValue.trim();

		log.info("Method: assertElementValue :: actualElementValue = " + actualElementValue 
				+ " expectedValue = " + expectedValue);

		if (expectedValue.equals(actualElementValue) == true)
			return true;
		else
			return false;
	}
  	

	public static String currentDateAndTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddHHmmss");
		// String newDate = dtf.toString().toCharArray().toString();
		//	System.out.println(now.format(dtf).toCharArray());
		String date = now.format(dtf);
		return date;
	}

	public static String autoIncrementer()
	{
		String get = GenericFunctions.readFromNotepad(".\\courseNumbers.txt");
		int var=Integer.parseInt(get);
		System.out.println("Reading "+var);
		var=++var;
		String var1 = Integer.toString(var);
		GenericFunctions.writeNotepad(var1,".\\courseNumbers.txt");
		String set=GenericFunctions.readFromNotepad(".\\courseNumbers.txt");
		System.out.println("writing "+set);
		return set;
	}
	
	public static String randomCourseName()
	{

		char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}
	@Test
	public static String testDataIncrementer(String filePath) {

		   String courseNum = GenericFunctions.readFromNotepad(filePath);
		      Pattern compile = Pattern.compile("^(.*?)([9Z]*)$");
		      Matcher matcher = compile.matcher(courseNum);
		      String left="";
		      String right="";
		      if(matcher.matches()){
		           left = matcher.group(1);
		           right = matcher.group(2);
		      }
		      courseNum = !left.isEmpty() ? Long.toString(Long.parseLong(left, 36) + 1,36):"";
		      courseNum += right.replace("Z", "A").replace("9", "0");
		      GenericFunctions.writeNotepad(courseNum,filePath);
		      return courseNum.toUpperCase();
		  }
	
	public boolean isFileDownloaded_Ext(String dirPath, String ext)
	{
		boolean flag=false;
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        flag = false;
	    }
	    
	    for (int i = 1; i < files.length; i++) {
	    	if(files[i].getName().contains(ext)) {
	    		flag=true;
	    	}
	    }
	    return flag;
	}
	
}
