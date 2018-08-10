package org.sunbird.pageobjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.UploadOrgPage;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.Assert;


public class UploadOrgObj extends BaseTest
{
	static Logger log = Logger.getLogger(UploadOrgObj.class.getName());

	WebDriverWait wait=new WebDriverWait(driver,15);
	UploadOrgPage orgUploadPage=PageFactory.initElements(driver, UploadOrgPage.class);


	//updated on 17/07/2018 method return type.
	public boolean uploadRootAndSubOrg(String uploadDocument) throws Exception 
	{
		GenericFunctions.waitForElementToAppearOnScreen(orgUploadPage.dropdown);
		orgUploadPage.dropdown.click();
		GenericFunctions.waitForElementToAppearOnScreen(orgUploadPage.clickProfileIcon);
		orgUploadPage.clickProfileIcon.click();
		GenericFunctions.waitWebDriver(15000);
		WebElement element=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.organizationUpload));
		driver.navigate().refresh();
		element.click();
		if(element.isDisplayed())
		{
			GenericFunctions.waitForElementToAppear(orgUploadPage.uploadCSV);
			orgUploadPage.uploadCSV.click();
			GenericFunctions.waitWebDriver(3000);
			if(uploadDocument.equalsIgnoreCase(UPLOAD_ROOT_ORG))
			{
				String path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_ROOT_ORG;
				System.out.println("Uploaded file name: "+path);
				//log.info("Uploaded file name: "+path);
				GenericFunctions.waitWebDriver(3000);
				GenericFunctions.uploadFile(path);
				GenericFunctions.waitForElementToAppear(orgUploadPage.processID);
				GenericFunctions.waitWebDriver(3000);	
				//String getProcessID = orgUploadPage.processID.getText().substring(12,33);	
				String getProcessID = orgUploadPage.processID.getText();
				System.out.println(getProcessID);
				String [] iD=getProcessID.split("ID: ");
				for(String idVal:iD)
				{
					GenericFunctions.writeNotepad(idVal,".\\RootOrgProcessIds.txt");
				}
				
				orgUploadPage.closePopup.click();
				System.out.println("Succesful on Root Org upload");
				log.info("Process ID: "+getProcessID+"Succesful on Root upload");
			
			}
			else if(uploadDocument.equalsIgnoreCase(UPLOAD_SUB_ORG))
			{
				String path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_SUB_ORG;
				System.out.println("Uploaded file name: "+path);
				log.info("Uploaded file name: "+path);
				GenericFunctions.waitWebDriver(3000);
				GenericFunctions.uploadFile(path);
				GenericFunctions.waitWebDriver(3000);
				//String getProcessID = orgUploadPage.processID.getText().substring(12,32);	
				
				String getProcessID = orgUploadPage.processID.getText();
				System.out.println(getProcessID);
				String [] iD=getProcessID.split("ID: ");
				for(String idVal:iD)
				{
					GenericFunctions.writeNotepad(idVal,".\\SubOrgProcessIds.txt");
				}
				//GenericFunctions.writeNotepad(getProcessID,".\\SubOrgProcessIds.txt");
				orgUploadPage.closePopup.click();
				System.out.println("Succesful on Sub Org upload");
				/*String errorMsg = orgUploadPage.uploadErrorToast.getText();
				Thread.sleep(1000);	
				System.out.println(errorMsg+"Please upload a valid file");
				log.info(errorMsg);
				Thread.sleep(2000);*/
				//orgUploadPage.uploadErrorToast.click();

			}

		}
		else
		{
			driver.navigate().refresh();
			log.error("No Files were uploaded");
			System.out.println("No Files were uploaded");
		}
		return true;

	}

	public void uploadRootAndSubOrgUserWithOrgId(String uploadDocument) throws Exception
	{
		Robot robot =new Robot();
		GenericFunctions.waitForElementToAppearOnScreen(orgUploadPage.dropdown);
		GenericFunctions.waitWebDriver(3000);
		orgUploadPage.dropdown.click();
		GenericFunctions.waitForElementToAppearOnScreen(orgUploadPage.clickProfileIcon);
		orgUploadPage.clickProfileIcon.click();
		GenericFunctions.waitForElementToAppear(orgUploadPage.organizationUpload);
		WebElement element=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.organizationUpload));
		if(element.isDisplayed())
		{	 String processID="";
		String path="";
		orgUploadPage.checkUploadStatus.click();
		if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_ROOT_ORG))
		{
			processID=GenericFunctions.readFromNotepad("./RootOrgProcessIds.txt");
			log.info("Process Id: "+processID);

		}
		else if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_SUB_ORG))
		{
			processID=GenericFunctions.readFromNotepad("./SubOrgProcessIds.txt");
		}
		orgUploadPage.cProcessId.sendKeys(processID);
		GenericFunctions.waitWebDriver(3000);
		orgUploadPage.clickCheckStatus.click();
		GenericFunctions.waitWebDriver(3000);
		String orgId = orgUploadPage.getOrgId.getText();
		System.out.println("ID:"+orgId);
		orgUploadPage.closeWindow.click();
		GenericFunctions.waitWebDriver(3000);
		orgUploadPage.uploadUsers.click();
		GenericFunctions.waitWebDriver(3000);
		orgUploadPage.clickOrgId.sendKeys(orgId);
		orgUploadPage.uploadUsersCsv.click();
		GenericFunctions.waitWebDriver(3000);
		if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_ROOT_ORG))
		{
			path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_USERS_ROOT_ORG;
		}
		else if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_SUB_ORG))
		{
			path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_USERS_SUB_ORG;
		}
		System.out.println("Uploaded file name: "+path);
		log.info("Uploaded file name: "+path);
		GenericFunctions.waitWebDriver(3000);
		GenericFunctions.uploadFile(path);
		GenericFunctions.waitWebDriver(4000);
		orgUploadPage.closePopup.click();
		System.out.println("Users for Root/Sub Org Uploaded with Org ID: "+orgUploadPage.uploadedRUser.getText());
		GenericFunctions.waitWebDriver(2000);
		//orgUploadPage.closeWindow1.click();


		}
		else 
		{
			System.out.println("Element is not displayed");
		}



	}

	public void rootAndSubOrgUserExternalAndProvider(String uploadDocument) throws Exception
	{
		String processID="";
		String path="";

		/*orgUploadPage.dropdown.click();
		GenericFunctions.waitWebDriver(10000);*/
		GenericFunctions.waitForElementToAppear(orgUploadPage.clickProfileIcon);
		orgUploadPage.clickProfileIcon.click();
		GenericFunctions.waitWebDriver(10000);
		WebElement element=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.organizationUpload));
		if(element.isDisplayed())
		{		
			orgUploadPage.checkUploadStatus.click();
			if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_ROOT_ORG))
			{
				processID=GenericFunctions.readFromNotepad(".\\RootOrgProcessIds.txt");
				log.info("Process Id: "+processID);

			}
			else if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_SUB_ORG))
			{
				processID=GenericFunctions.readFromNotepad(".\\SubOrgProcessIds.txt");
			}

			System.out.println(processID);
			orgUploadPage.cProcessId.sendKeys(processID);
			GenericFunctions.waitWebDriver(3000);
			orgUploadPage.clickCheckStatus.click();
			GenericFunctions.waitWebDriver(3000);
			String externalId=orgUploadPage.externalId.getText();
			String provider=orgUploadPage.provider.getText();
			System.out.println("External ID: "+externalId);
			System.out.println("Provider :"+provider);
			orgUploadPage.closeWindow.click();
			GenericFunctions.waitWebDriver(4000);
			orgUploadPage.uploadUsers.click();
			GenericFunctions.waitWebDriver(3000);
			orgUploadPage.uploadUserExternal_Id.sendKeys(externalId);
			orgUploadPage.uploadUserProvider.sendKeys(provider);
			GenericFunctions.waitWebDriver(2000);
			orgUploadPage.uploadUsersCsv.click();
			GenericFunctions.waitWebDriver(3000);
			if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_ROOT_ORG))
			{
				path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_USERS_ROOT_ORG;
			}
			else
			{
				path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_USERS_SUB_ORG;
			}

			System.out.println("Uploaded file name: "+path);
			log.info("Uploaded file name: "+path);
			GenericFunctions.waitWebDriver(3000);
			GenericFunctions.uploadFile(path);
			GenericFunctions.waitWebDriver(3000);
			//GenericFunctions.waitForElementToAppear(orgUploadPage.uploadedRUser);
			System.out.println("Users for Root/Sub Org Uploaded with Provider and externalId: "+orgUploadPage.uploadedRUser.getText());
			GenericFunctions.waitWebDriver(3000);
			//OrgUploadPage.closeWindow1.click();
		}
		else
		{
			System.out.println("Element is not displayed");
		}
	}

	public void adminDashboard() throws Exception
	{
		orgUploadPage.dropdown.click();
		GenericFunctions.waitForElementToAppear(orgUploadPage.clickProfileIcon);
		orgUploadPage.clickProfileIcon.click();
		GenericFunctions.waitWebDriver(3000);
		String aprofileUrl=driver.getCurrentUrl();
		String eProfileUrl="https://staging.open-sunbird.org/profile";
		GenericFunctions.waitWebDriver(2000);
		log.info(aprofileUrl);
		log.info(eProfileUrl);
		Assert.assertEquals(aprofileUrl, eProfileUrl, "Assert fails on matching URLs");
		if(aprofileUrl.equalsIgnoreCase(eProfileUrl))
		{
			
			Assert.assertTrue(true);
			log.info("Profile page is displayed succesfully");
			System.out.println("Profile page is displayed succesfully");
		}

		else
		{
			log.info("Profile page is not loaded");
			System.out.println("Profile page is not loaded");

		} 
		GenericFunctions.waitWebDriver(2000);
		WebElement element1= GenericFunctions.waitForElementToAppear(orgUploadPage.organizationUpload);
		WebElement element2= GenericFunctions.waitForElementToAppear(orgUploadPage.uploadUsers);
		WebElement element3 = GenericFunctions.waitForElementToAppear(orgUploadPage.checkUploadStatus);
		if(element1.isDisplayed())
		{		
			if(element2.isDisplayed())
			{
				if(element3.isDisplayed())
				{
				Assert.assertTrue(true);
				log.info("Upload Organizations option is present");
				System.out.println("Upload Organizations option is present");
				log.info("Upload Organizations option is present");
				System.out.println("Upload Organizations option is present");
				log.info("Check upload status option is present");
				System.out.println("Check upload status option is present");
				}
			}
		}
		else
		{
			System.out.println("Options are present in Profile page");
		}
		
		/*WebElement element=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.organizationUpload));
		if(element.isDisplayed())
		{
			Assert.assertTrue(true);
			log.info("Upload Organizations option is present");
			System.out.println("Upload Organizations option is present");
		}
		else 
		{
			log.info("Upload Organizations option is not present");
			System.out.println("Upload Organizations option is not present");
		}

		WebElement element1=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.uploadUsers));
		if(element1.isDisplayed())
		{
			Assert.assertTrue(true);
			log.info("Upload Users option is present");
			System.out.println("Upload Users option is present");
		}

		else
		{
			log.info("Upload Users option is not present");
			System.out.println("Upload Users option is not present");
		}

		WebElement element2=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.checkUploadStatus));
		if(element2.isDisplayed())
		{
			Assert.assertTrue(true);
			log.info("Check upload status option is present");
			System.out.println("Check upload status option is present");
		}

		else
		{
			log.info("Check upload status option is not present");
			System.out.println("Check upload status option is not present");
		}*/


	}

	public void adminCreationConsumption(String filterName) throws Exception
	{

		orgUploadPage.dropdown.click();
		//GenericFunctions.waitTillTheElementIsVisibleAndClickable(orgUploadPage.clickAdminDashboardIcon);
		GenericFunctions.waitWebDriver(4500);
		orgUploadPage.clickAdminDashboardIcon.click();
		GenericFunctions.waitWebDriver(3000);
		orgUploadPage.selectOrganizationDropdown.click();
		GenericFunctions.waitWebDriver(2000);
		orgUploadPage.selectOrg.click();
		GenericFunctions.waitWebDriver(2000);
		orgUploadPage.rDropdownList.click();
		GenericFunctions.waitWebDriver(2000);
		String creationText=orgUploadPage.clickCreation.getText();
		String consumptionText=orgUploadPage.clickConsumption.getText();
		System.out.println(creationText); 
		if(orgUploadPage.clickCreation.getText().equalsIgnoreCase(filterName))
		{

			orgUploadPage.clickCreation.click();
			GenericFunctions.waitWebDriver(2000);
			verifyPageElements();
			GenericFunctions.waitWebDriver(2000);
			orgUploadPage.last14Days.click();
			GenericFunctions.waitWebDriver(2000);
			verifyPageElements();
			GenericFunctions.waitWebDriver(2000);
			orgUploadPage.last5Weeks.click();
			GenericFunctions.waitWebDriver(2000);
			verifyPageElements();
			GenericFunctions.waitWebDriver(2000);
		}
		else if(orgUploadPage.clickConsumption.getText().equalsIgnoreCase(filterName))
		{
			System.out.println(consumptionText); 
			orgUploadPage.clickConsumption.click();
			GenericFunctions.waitWebDriver(2000);
			verifyPageElements1();
			GenericFunctions.waitWebDriver(2000);
			orgUploadPage.last14Days.click();
			GenericFunctions.waitWebDriver(2000);
			verifyPageElements1();
			GenericFunctions.waitWebDriver(2000);
			orgUploadPage.last5Weeks.click();
			GenericFunctions.waitWebDriver(2000);
			verifyPageElements1();
			GenericFunctions.waitWebDriver(2000);
		}
		else
		{
			System.out.println("None of the filters are passed");
		}
	}
	public void verifyPageElements() throws Exception
	{	
		if(orgUploadPage.contentsCreated.isDisplayed())
		{
			GenericFunctions.waitWebDriver(2000);
			if(orgUploadPage.contentsCreated.isDisplayed())
			{
				log.info(orgUploadPage.contentsCreated.getText()+" element is displayed");
				GenericFunctions.waitWebDriver(2000);
				if(orgUploadPage.authors.isDisplayed())
				{	
					log.info(orgUploadPage.authors.getText()+" element is displayed");
					GenericFunctions.waitWebDriver(2000);
					if(orgUploadPage.reviewers.isDisplayed())
					{
						log.info(orgUploadPage.reviewers.getText()+" element is displayed");
						GenericFunctions.waitWebDriver(2000);
						if(orgUploadPage.csvLink.isDisplayed())
						{
							GenericFunctions.scrollToElement(orgUploadPage.csvLink);
							log.info(orgUploadPage.csvLink.getText()+" element is displayed");
							GenericFunctions.waitWebDriver(3000);
							orgUploadPage.csvLink.click();
							GenericFunctions.waitWebDriver(3000);
							orgUploadPage.closeThanks.click();
						}
					}
				}
			}
			else
			{
				log.info(orgUploadPage.contentsCreated.isDisplayed()+" element is not displayed");
			}
		}

	}	
	public void verifyPageElements1() throws Exception
	{
		if(orgUploadPage.visits.isDisplayed())
		{
			log.info(orgUploadPage.visits.getText()+" element is displayed");
			GenericFunctions.waitWebDriver(2000);
			if(orgUploadPage.consumptionTime.isDisplayed())
			{
				log.info(orgUploadPage.consumptionTime.getText()+" element is displayed");
				GenericFunctions.waitWebDriver(2000);
				if(orgUploadPage.avgTime.isDisplayed())
				{
					log.info(orgUploadPage.avgTime.getText()+" element is displayed");
					GenericFunctions.waitWebDriver(2000);
					if(orgUploadPage.csvLink.isDisplayed())
					{
						GenericFunctions.scrollToElement(orgUploadPage.csvLink);
						log.info(orgUploadPage.csvLink.getText()+" element is displayed");
						GenericFunctions.waitWebDriver(3000);
						orgUploadPage.csvLink.click();
						GenericFunctions.waitWebDriver(3000);
						orgUploadPage.closeThanks.click();
					}
				}
			}
		}
		else
		{
			log.info(orgUploadPage.visits.getText()+" element is not displayed");
		}
	}

}

