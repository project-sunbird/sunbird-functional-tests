/**

 * Created by Qualitrix Technologies Pvt Ltd.

 * @author: Abhinav kumar singh

 * Date: 07/12/2018

 * Purpose: Check for sub org member can't add org member while creating a invite only batch.

 */
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.page.CreateMentorPage;
import org.sunbird.page.CreatorAnnouncementPage;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.pageobjects.CreateMentorPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Mentor_TC09 extends BaseTest 
{
	CreatorUserPage createUserPage=PageFactory.initElements(driver, CreatorUserPage.class);
	List <TestDataForSunbird> objListOFTestDataForSunbird= null;
	CreateMentorPage createMentorPage=PageFactory.initElements(driver, CreateMentorPage.class);
	static Logger log = Logger.getLogger(CreatorAnnouncementPage.class.getName());

	@Test
	public void suborgMentorInvitingOrgMentor_TC09() throws Exception
	{


		//Step1: Login as sub org member.

		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(MENTORS_S);


		//Step2: Navigate to course and search the course created by other user.
		CreateMentorPageObj createMentorPageObj =new CreateMentorPageObj();
		createMentorPageObj.createInviteOnlyBatchForSuborg();
		GenericFunctions.waitWebDriver(1000);





	}
}