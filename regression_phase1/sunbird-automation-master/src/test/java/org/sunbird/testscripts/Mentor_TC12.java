
/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/12/2018

* Purpose: Search the organisations and download it and verify it.

*/package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Mentor_TC12 extends BaseTest {
	
	CreatorUserPage createUserPage1=PageFactory.initElements(driver, CreatorUserPage.class);
	String courseName;
	String startDate;
	String endDate;
	String fileName = "Organisations";
	
	
	@Test
	public void testcaseMentor12() throws Exception
	{
		
		//Step1: Login as Creator
		
		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(ADMIN);

		
		//Step2: Navigate to profile and search the org and download
		
		CreatorUserPageObj cretorUserPageObj =new CreatorUserPageObj();
		cretorUserPageObj.navigateToProfileAndSearchOrg();
		GenericFunctions.waitWebDriver(1000);
		System.out.println("Download is done");
		
		
		//Step3: check the download
		
		String downloadPath = "/home/" + System.getProperty("user.name") + "/Downloads/";
		GenericFunctions generics = new GenericFunctions();
		generics.isFileDownloaded_Ext(downloadPath, fileName);
		System.out.println("downloaded file exist");
		
		
	}
}
