/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 06/26/2018

* Purpose: Navigate to announcement Dashboard.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.CreatorAnnouncementPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;


public class Announcement_TC01 extends BaseTest {
	
	@Test
	public void NavigateToAnnouncementDashboard_TC01() throws Exception
	{
		
		//Step1: Login as Creator
		
		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(CREATOR);
		
		//Step2: Navigate to Announcement Dashboard in Dropdown
		
		CreatorAnnouncementPageObj creatorAnnouncementPageObj =new CreatorAnnouncementPageObj();
		creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();
		
		GenericFunctions.waitWebDriver(2000);
		//Logout as Creator
		creatorLogin.userLogout();
	}
	

}
