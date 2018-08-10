package org.sunbird.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;


public class CreatorUserPage {
	WebDriver driver;

	public  CreatorUserPage(WebDriver driver)
	{ 
		this.driver=driver; 
	}

	@FindBy(id="dropdown-menu-list-header")
	public WebElement dropDown;

	@FindBy(xpath="//i[@class='close icon']")
	public WebElement closeIcon;

	//	@FindBy(xpath="//a[.='Profile']")
	//	@FindBy(xpath="//a[@class='item margin-top-10 active']")
	//	@FindBy(xpath="//div[@class='ui secondary pointing menu']/app-menu-item/a]")
	//	public WebElement headerProfile;

	//	@FindBy(xpath="//div//a[5][contains(text(),'Profile')]")
	//	@FindBy(xpath="//a[.='Profile']")
	@FindBy(xpath="//div[@class='ui secondary pointing menu']//a[contains(text(),'Profile')]")
	public WebElement headerProfile;

	//@FindBy(xpath="//a[.='Workspace']")
	//@FindBy(xpath="//a[contains(text(),'Workspace')]")
	//@FindBy(xpath="//div[@class='menu transition hidden']//a[contains(text(),'Workspace')]")
	@FindBy(xpath="//span[.='View your workspace']")
	public WebElement workSpace;

	@FindBy(xpath="//input[@placeholder='Name']")
	//	@FindBy(xpath="//div[@class='ui modal ng-scope transition visible active']//input[@placeholder='Name']")
	//	@FindBy(xpath="//div[@class='ui modal scroll transition active visible normal']/..//input[@placeholder='Name']")
	//	@FindBy(xpath="//label[contains(text(),'Name')]/following-sibling::div/input")
	//	@FindBy(xpath="//form[@class='ui form ng-untouched ng-pristine ng-valid']//input")
	public WebElement courseName;

	@FindBy(xpath="//input[@placeholder='Description']")
	public WebElement courseDescription;

	@FindBy(xpath="//button[contains(text(),'START CREATING')]")
	public WebElement startCreating;

	@FindBy(tagName="iframe")
	public WebElement iFrame;

	//@FindBy(xpath="//div[@class='ui two buttons table-of-contents-btn']//button[2]")
	//@FindBy(xpath="//button[@class='ui basic button text-transform-none'][2]//a[2]")
	//@FindBy(xpath="//button[@class='ui basic button text-transform-none'][2]")
	//@FindBy(xpath="(//button//a//i)[2]")
	@FindBy(xpath="//button[@class='ui basic button text-transform-none'][2]//a[2]")
	public WebElement newChild;

	//	@FindBy(xpath="//input[@class='ng-pristine ng-valid-maxlength ng-not-empty ng-valid ng-valid-required ng-touched']")
	//	@FindBy(xpath="//input[@class='ng-valid-maxlength ng-dirty ng-touched ng-not-empty ng-valid ng-valid-required']")
	//	@FindBy(xpath="//form[@name='contentMetaForm']//input[@placeholder='Enter title for Book']")
	@FindBy(xpath="//label[.='Title']/following-sibling::div//following-sibling::input")
	public WebElement titleName;

	@FindBy(xpath="//textarea[@placeholder='Brief description about the course unit']")
	public WebElement titleDescription;

	@FindBy(xpath="//button[.='ADD RESOURCE']")
	public WebElement addResource;

	//@FindBy(xpath="//img[@class='resourceMetaImage ']")
	//	@FindBy(xpath="//div[@class='facetList ng-scope'][1]//div[@id='checkBox_do_212514765720756224117756']")
	@FindBy(xpath="//div[@class='facetList ng-scope'][1]//div/div[1]")
	public WebElement selectResource;

	@FindBy(xpath="//strong[contains(text(),'Find & Select Activities')]")
	public WebElement findSelectActivities;		

	//@FindBy(xpath="//button[@class='ui right floated primary tiny button btnAttribute']")
	//@FindBy(xpath="//button[@class='ui right floated primary tiny button proceed-btn']")
	@FindBy(xpath="//div[@class=' ui clearing segment']/..//button[contains(text(),'PROCEED')]")
	public WebElement proceedButton;

	@FindBy(xpath="//span[.='Save']")
	public WebElement saveCourse;

	@FindBy(xpath="//div[@class='ui button text-part popup-item']")
	public WebElement sendForReview;

	@FindBy(xpath="//div[@class='ui appIconSelector']")
	public WebElement clickAppIcon;

	//	@FindBy(xpath="//img[@class='asset_thumbnail']")
	@FindBy(xpath="//div[@class='ui image']//img[@data_id='do_2125451247114403841452']")
	public WebElement checkAppIcon;

	@FindBy(xpath="//div[@class='ui image']//img[@data_id='do_2125450286821949441339']")
	public WebElement checkAppIcon1;
	
	
	@FindBy(xpath="//button[.='Select']")
	public WebElement selectAppIcon;

	@FindBy(xpath="//button[.='Save']")
	public WebElement saveButton;	

	//	@FindBy(xpath="//div[@class='ui search dropdown placeholder-padding ng-pristine ng-untouched ng-empty ng-invalid ng-invalid-required selection active visible']")
	@FindBy(xpath="//div[.='Select Curriculum']")
	public WebElement clickOnSelectCurriculum;

	@FindBy(xpath="//div[@class='item selected']")
	public WebElement selectCurriculum;

	@FindBy(xpath="//div[.='Select Class']")
	public WebElement clickOnSelectClass;

	@FindBy(xpath="//div[@class='menu transition visible']//following-sibling::div")
	public WebElement selectClass;
	
	@FindBy(xpath="//label[contains(text(),'SUBJECT')]")
	public WebElement clickOnHeaderSubject;

	@FindBy(xpath="//div[.='Select Subject']")
	public WebElement clickOnSelectSubject;

	@FindBy(xpath="//div[@data-value='Mathematics']")
	public WebElement selectSubject;

	@FindBy(xpath="//div[.='Select Medium']")
	public WebElement clickOnSelectMedium;

	@FindBy(xpath="(//div[@data-value='English'])[2]")
	public WebElement selectMedium;

	@FindBy(xpath="//div[@class='text']//span[.='All']")
	public WebElement headerDropdown;

	@FindBy(xpath="//a[contains(text(),'Course')]")
	public WebElement headerCourse;

	@FindBy(xpath="//div[@id='headerSearch']//span[.='Courses']")
	public WebElement headerCourseClick;

	@FindBy(xpath="//input[@id='keyword']")
	public WebElement searchInput;

	@FindBy(xpath="//i[@class='circular search link icon']")
	public WebElement searchIcon;

	@FindBy(xpath="//a[contains(text(),'Review Submissions')]")
	public WebElement reviewSubmission;

	@FindBy(xpath="//div[@class='cardImageText center aligned ']/span")
	public WebElement reviewCourseName;

	@FindBy(xpath="//div[@class='cardImageText center aligned ']/span")
	public List<WebElement> reviewSubmittedCourse;

	//updated on June 13
	//@FindBy(xpath="(//div[@class='content']/..//span)[2]")
	@FindBy(xpath="//span[.='Course']")
	public WebElement createCourse;

	//@FindBy(xpath="(//div[@class='content']/..//span)[1]")
	//Updated on June 15
	@FindBy(xpath="//div[@class='content']/..//span[.='Book']")
	public WebElement createBook;

	//@FindBy(xpath="(//div[@class='content']/..//span)[3]")
	//Updated on June 15
	@FindBy(xpath="//div[@class='content']/..//span[.='Resource']")
	public WebElement createResource;


	//@FindBy(xpath="(//div[@class='content']/..//span)[4]")
	//Updated on 15 June
	@FindBy(xpath="//div[@class='content']/..//span[.='Collection']")
	public WebElement createCollection;


	//@FindBy(xpath="(//div[@class='content']/..//span)[5]")
	//Updated on 15 June
	@FindBy(xpath="//div[@class='content']/..//span[.='Lesson Plan']")
	public WebElement createLesson;

	//@FindBy(xpath="(//div[@class='content']/..//span)[6]")
	//Updated on 15 June
	@FindBy(xpath="//div[@class='content']/..//span[.='Upload Content']")
	public WebElement createUploadContent;

	@FindBy(xpath="(//input[@type='text'])[3]")
	public WebElement bookName;

	@FindBy(xpath="//sui-select[@id='board']")
	public WebElement clickBBoard;

	//@FindBy(xpath="(//sui-select[@id='board']/..//sui-select-option[@class='item'])[1]")
	@FindBy(xpath="//sui-select-option[@class='item']//span[.='CBSE']")
	public WebElement selectBBoard;

	@FindBy(xpath="//sui-multi-select[@id='gradeLevel']")
	public WebElement clickBGrade;

	//@FindBy(xpath="//sui-select-option[@class='item']//span[.='Class 3']")
	//@FindBy(xpath="//sui-select-option[@class='item selected']//span[.='KG']")

	@FindBy(xpath="//sui-multi-select[@id='gradeLevel']//span[.='KG']")
	public WebElement selectBGrade;

	@FindBy(xpath="//sui-select[@id='subject']")
	public WebElement clickBSubject;

	@FindBy(xpath="//sui-select[@id='subject']//span[.='English']")
	public WebElement selectBSubject;

	@FindBy(xpath="//sui-select[@id='medium']")
	public WebElement clickBMedium;

	@FindBy(xpath="//sui-select[@id='medium']//span[contains(text(),'English')]")
	public WebElement selectBMedium;

	@FindBy(xpath="//sui-select[@id='year']")
	public WebElement clickBYear;

	@FindBy(xpath="//sui-select[@id='year']//span[contains(text(),'2010')]")
	public WebElement selectBYear;

	@FindBy(xpath="//input[@placeholder='Publisher']")
	public WebElement BPublisher;

	@FindBy(xpath="//p[.='TABLE OF CONTENTS']")
	public WebElement tableOfContents;

	//@FindBy(xpath="(//textarea[@placeholder='Brief description about the unit']")
	//@FindBy(xpath="//label[.='Description']/..//div[@class='ui input']//textarea[@placeholder='Brief description about the unit']")
	//@FindBy(xpath="//label[.='Description']/..//div//textarea[@placeholder='Brief description about the unit']")
	//@FindBy(xpath="//label[.='Description']/..//textarea[@ng-model='unit.description']")
	//@FindBy(xpath="//*[@id="contents-data-form"]/div/div/div[2]/div/textarea")
	//label[.='Description']/../div/textarea[@placeholder='Brief description about the unit']

	@FindBy(xpath="//label[.='Description']/../div/textarea[@placeholder='Brief description about the unit']")
	public WebElement bookDescription;

	//@FindBy(xpath="(//input[@name='name'])[1]")
	//@FindBy(xpath="(//input[@placeholder='Enter title for Book'])[1]")
	//@FindBy(xpath="(//div[@class='ui input']//input)[1]")


	//@FindBy(xpath="(//form[@id='contents-data-form'])[4]")
	@FindBy(xpath="(//label[.='Name'])[2]")
	public WebElement clickBookForm;

	//@FindBy(xpath="(//input[@placeholder='Enter title for Book'])[3]")
	@FindBy(xpath="//label[.='Name']/../div[@class='ui input']/..//input[@placeholder='Enter title for Book']")
	public WebElement bookTitle;

	//@FindBy(xpath="//div[@class='tags focused']")
	//@FindBy(xpath="(//input[@placeholder='Enter Keywords'])[3]")
	@FindBy(xpath="//label[.='Keywords']/..//div[@class='tags']//input[@placeholder='Enter Keywords']")
	//	@FindBy(xpath="//div[@class='sixteen wide column metacontent-holder metadata-form-container ng-scope']//tags-input[@placeholder='Enter Keywords']")
	public WebElement bookKeywords;

	@FindBy(xpath="//input[@placeholder='Enter code here']")
	public WebElement bookDialcode;

	@FindBy(xpath="(//div[@class='ui image']//img[@data_id='do_2124646169195151361273'])[1]")
	public WebElement clickBookIcon;

	@FindBy(xpath="(//div[@class='ui image']//img[@data_id='do_212450905413713920137'])[1]")
	public WebElement selectBookIcon;

	@FindBy(xpath="(//img[@class='resourceMetaImage '])[2]")
	public WebElement selectBookResource;

	//@FindBy(xpath="//i[@class='large blue check circle icon']")
	@FindBy(xpath="//i[@class='large check circle icon']")
	public WebElement acceptDialcode;

	@FindBy(xpath="//a[contains(text(),'Up For Review')]")
	public WebElement upForReview;

	//--------------------------------------------------------------
	//Elements for Create A Lesson plan

	@FindBy(xpath="//input[@placeholder='Enter title for unit']")
	public WebElement lessonTitle;

	//@FindBy(xpath="//label[.='Description']/..//textarea[@placeholder='Brief description about the unit']")
	@FindBy(xpath="(//div//textarea[@placeholder='Brief description about the unit'])[2]")
	public WebElement lessonDescription;

	@FindBy(xpath="//input[@placeholder='Add Notes']")
	public WebElement lessonNotes;

	@FindBy(xpath="(//img[@class='resourceMetaImage '])[3]")
	public WebElement lessonResource;

	@FindBy(xpath="//div[@class='ui image']//img[@data_id='do_2124646193555292161285']")
	public WebElement selectLessonIcon;

	//	@FindBy(xpath="//input[@class='upForReviewSearchBox ng-pristine ng-valid ng-touched']")
	//	@FindBy(xpath="//div[@class='workspacesegment eight wide column']//input[@class='upForReviewSearchBox ng-pristine ng-valid ng-touched']")
	@FindBy(xpath="//input[@placeholder='Search content']")
	public WebElement searchForReview;

	@FindBy(xpath="//img[@class='ui tiny image UpReviewTinyImage']/..//div[@class='UpReviewHeader']")
	public WebElement searchedContentForPublish;

	@FindBy(xpath="//span[.='Publish']")
	public WebElement clickPublishIcon;

	@FindBy(xpath="//span[.='Request Changes']")
	public WebElement clickReqChangesIcon;

	@FindBy(xpath="//input[@class='listItem']")
	public List<WebElement> selectAllCB; 

	//@FindBy(xpath="//button[@class='ui blue button ng-binding']")
	//@FindBy(xpath="//button[contains(text(),'PUBLISH')]")
	@FindBy(xpath="//div[@class='row margin-top-10']//button[contains(text(),'PUBLISH')]")
	public WebElement publishButton;
	
	
	@FindBy(xpath="//button[contains(text(),'Publish')]")
	public WebElement popupPublishButton;

	@FindBy(xpath="(//div[@class='UpReviewHeader'])[1]")
	public WebElement contentForReject;

	@FindBy(xpath="//button[contains(text(),'REQUEST CHANGES')]")
	public WebElement clickRequestChanges;

	@FindBy(xpath="//h5[.='Appropriateness']/..//input[@class='listItem']")
	public WebElement rejectReason1;

	@FindBy(xpath="//h5[.='Content details']/..//input[@class='listItem']")
	public WebElement rejectReason2;

	@FindBy(xpath="//h5[.='Usability']/..//input[@class='listItem']")
	public WebElement rejectReason3;

	
	//Updated on 25/07/2018
	//@FindBy(xpath="(//div[@id='review-footer']/..//button[.='Request changes'])[2]")
	
	//Updated on 26/07/2018
	@FindBy(xpath="//button[.='Request changes']")
	//@FindBy(xpath="//div[@class='actions footer']/..//button[.='Request changes']")
	public WebElement requestChangesButton;
	
	@FindBy(xpath="(//div[@id='review-footer']/..//button[.='Request changes'])[2]")
	public WebElement requestChangesButton1;
	

	//Added locator for fetching list of courses up for review
	@FindBy(xpath="//div[@class='UpReviewHeader']")
	public List<WebElement> searchCoursesUpForReview;

	@FindBy(xpath="//span[contains(text(),'Publish')]")
	public WebElement publishCourseButton;

	@FindBy(xpath="//input[@class='listItem']")
	public List<WebElement> checkbox;

	@FindBy(xpath="//span[@class='sliderCardHeading text-cencapitalize']")
	public List<WebElement> searchPublishedCourses;

	@FindBy(xpath="//a[contains(text(),'Library')]")
	public WebElement headerLibrary;

	//Elements for upload documents -

	@FindBy(xpath="//input[@type='file']")
	public WebElement browseButton;

	@FindBy(xpath="//div[@class='qq-uploader-selector qq-uploader custom-qq-uploader']")
	public WebElement clickUploadSection;

	@FindBy(xpath="//label[.='URL']/following-sibling::div//following-sibling::input")
	public WebElement enterUrl;

	@FindBy(xpath="//button[.='Upload']")
	public WebElement UploadButton;

	@FindBy(xpath="//input[@id='name']")
	public WebElement contentMp4Title;

	@FindBy(xpath="//textarea[@id='description']")
	public WebElement contentMp4Desc;

	@FindBy(xpath="//input[@placeholder='Add a tag']")
	public WebElement contentKeywords;

	@FindBy(xpath="//input[@id='searchMyImageAssets']")
	public WebElement searchUploadImage;

	@FindBy(xpath="//input[@id='searchMyImageAssets']/..//i[@class='circular search link icon inverted']")
	public WebElement clickImageSearch;

	@FindBy(xpath="(//div[@class='ui image']//img[@data_id='do_21252875847904460811925'])[1]")
	public WebElement checkContentIcon;

	@FindBy(xpath="//input[@name='concepts']")
	public WebElement clickConcepts;

	@FindBy(xpath="//a[.='Artificial_Intelligence']")
	public WebElement selectConcept1;

	@FindBy(xpath="//a[.='Deep_Learning']")
	public WebElement selectConcept2;

	@FindBy(xpath="//a[.='Perceptron']")
	public WebElement selectSubConcept1;

	@FindBy(xpath="//a[.='RELU']")
	public WebElement selectSubConcept2;

	@FindBy(xpath="//a[.='Softmax']")
	public WebElement selectSubConcept3;

	@FindBy(xpath="(//a[.='Done'])[2]")
	public WebElement doneButton;

	@FindBy(xpath="//div[.='no-results']")
	public WebElement noResults;

	//---------------------------------------------------------------
	//Elements for creating a Collection

	@FindBy(xpath="(//label[.='Name']/following-sibling::div//following-sibling::input)[2]")
	public WebElement collectionTitle;


	@FindBy(id="dropdown-menu-list-header")
	public WebElement menuListHeader;

	@FindBy(xpath="//a[.='Workspace']")
	public WebElement listHeaderProfile;

	@FindBy(xpath="//label[.='Keywords']/..//div[@class='tags']//input[@placeholder='Enter Keywords']")
	public WebElement collectionKeywords;

	@FindBy(xpath="//div[contains(text(),'Latest Resource')]/..//span[.='View All']")
	public WebElement viewAllButton;

	@FindBy(xpath="//div[@id='lessonBrowser_lessonType']")
	public WebElement filterCategory;

	@FindBy(xpath="//div[@data-value='Collection']")
	public WebElement collectionFilter;

	@FindBy(xpath="//div[@data-value='Resource']")
	public WebElement resourceFilter;

	@FindBy(id="apply-lesson-filter-button")
	public WebElement applyFilter;

	@FindBy(xpath="//div[contains(text(),'1425')]")
	public WebElement selectCollection;

	@FindBy(xpath="//div[@class='ui image']//img[@data_id='do_21253030058741760013212']")
	public WebElement checkCollectionIcon;

	//Added for Test case 13
	//@FindBy(xpath="//textarea[@id='review-comments']")
	@FindBy(xpath="//h5[.='Comments']/..//textarea")
	public WebElement reviewComments;

	@FindBy(xpath="//a[contains(text(),'Drafts')]")
	public WebElement drafts;

	@FindBy(xpath="//span[@class='sliderCardHeading text-cencapitalize']")
	public WebElement getCourseName;
	
	@FindBy(xpath="//img[@class='resourceMetaImage ']")
	public WebElement selectExtraResource;
	
	//Elements for Test case 14
	@FindBy(xpath="//div[@data-content='Send for review']/..//div[@class='ui pointing dropdown icon button']")
	public WebElement limitedSharingArrow;
	
	@FindBy(xpath="//div[.=' Limited sharing']")
	public WebElement clickLimitedSharing;
	//---------------------------------------------
	@FindBy(xpath="//input[@id='treePicker']")
	public WebElement selectConcept;
	
	
	//Updated on 26/07/2018
	//@FindBy(xpath="//div[@id='conceptSelector_treePicker']//input[@placeholder='Search']")
	@FindBy(xpath="//div[@id='conceptSelector_defaultTemplate-concept']//input[@placeholder='Search']")
	public WebElement searchConcept;
	
	//Updated on 26/07/2018
	//@FindBy(xpath="//a[contains(text(),'Choose All')]")
	@FindBy(xpath="//div[@id='conceptSelector_defaultTemplate-concept']//a[contains(text(),'Choose All')]")
	public WebElement conceptChooseAll;
	
	//Updated on 26/07/2018
	//@FindBy(xpath="//a[contains(text(),'Done')]")
	@FindBy(xpath="//div[@id='conceptSelector_defaultTemplate-concept']//a[contains(text(),'Done')]")
	public WebElement conceptDoneButton;
	
	@FindBy(xpath="//sui-select[@id='resourceType']")
	public WebElement clickResourceType;
	
	@FindBy(xpath="//sui-select-option[@class='item']//span[.='Study material']")
	public WebElement selectResouceType;
	
	@FindBy(xpath="//div[@class='content-title-container row custom-row-1']/label")
	public WebElement untitledCollection;
	
	@FindBy(xpath="//i[@class='fa fa-picture-o custom-menu-icon']")
	public WebElement addImageIcon;
	
	//Element added on 02/07/2018
	
	@FindBy(xpath="//div[.='Uploading..']")
	public WebElement waitForUpload;
	
	@FindBy(xpath="//button[contains(text(),'Close')]")
	public WebElement closeButton;
	
	@FindBy(xpath="//button[contains(text(),'PUBLISH')]")
	public WebElement publishResource;
	
	@FindBy(xpath="//a[@class='item' and contains(text(),'My Activity')]")
	public WebElement myActivity;
	
	@FindBy(xpath="//div[contains(text(),'Select Course To See Dashboard')]")
	public WebElement searchCourseInActivity;
	
	//Elements for Test case 14
	
	@FindBy(xpath="//button[.='Close']")
	public WebElement closeContentPopup;
	
	//Adding elements for Test case 16
	
	@FindBy(xpath="//a[contains(text(),'Published')]")
	public WebElement published;
	
	//Adding elements for Test case 18
	@FindBy(xpath="//a[.='Edit']")
	public WebElement editDialCode;
	
	//Adding element for Reviewer test case 16
	
	@FindBy(xpath="//i[@class='remove icon custom-cursor']")
	public WebElement editorCloseIcon;
	
	@FindBy(xpath="//div[contains(text(),'TextBook')]")
	public WebElement contentType;
	
	//Adding elements for test case 14
	@FindBy(xpath="//span[@class='browse item cursor-pointer']")
	public WebElement filterIcon;
	
	@FindBy(xpath="//sui-multi-select[@id='board']")
	public WebElement clickFilterBoard;
	
	@FindBy(xpath="//sui-multi-select[@id='subject']")
	public WebElement clickFilterSubject;
	
	@FindBy(xpath="//sui-multi-select[@id='subject']//span[.='English']")
	public WebElement selectFilterSubject;
	
	@FindBy(xpath="//sui-multi-select[@id='medium']")
	public WebElement clickFilterMedium;
	
	@FindBy(xpath="//sui-multi-select[@id='medium']//span[.='English']")
	public WebElement selectFilterMedium;
	
	@FindBy(xpath="//sui-multi-select[@id='contentType']")
	public WebElement clickContentType;
	
	@FindBy(xpath="//sui-multi-select[@id='contentType']//span[.='LessonPlan']")
	public WebElement selectContentType;
	
	@FindBy(xpath="//a[.='APPLY']")
	public WebElement applyButton;
	
	@FindBy(xpath="//div[@class='ui image']//img[@data_id='do_21252947427956326412231']")
	public WebElement contentResourceIcon;
	
	//Adding for Maintainance of Test case 2
	@FindBy(xpath="//button[contains(text(),'Upload and use')]")
	public WebElement uploadAndUseButton;
	
	@FindBy(xpath="//label[contains(text(),'Yes, make it available to everyone')]")
	public WebElement yesRadioButton;
	
	@FindBy(xpath="//input[@id='assetfile']")
	public WebElement chooseFileButton;
	
	@FindBy(xpath="//button[@class='ui blue button submit button']")
	public WebElement uploadAndUseButtonRight;
}


