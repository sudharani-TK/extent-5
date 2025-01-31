package customWait

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.Status

public class setPrefrenceHidden {

	@Keyword
	def setprefrence(def preValue, extentTest) {

		WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
		extentTest.log(LogStatus.PASS, 'Click on profile tab')

		WebUI.click(findTestObject('PageNavigation/Preferences/Preference'))
		extentTest.log(LogStatus.PASS, 'Click on preference menu item')

		TestObject	 prefer = WebUI.modifyObjectProperty(findTestObject('PageNavigation/Preferences/Choice'), 'text', 'equals',"Files", true)
		WebUI.click(prefer)
		extentTest.log(LogStatus.PASS, 'Click on preference - Files')

		WebUI.delay(2)

		def result=WebUI.verifyElementChecked(findTestObject('PageNavigation/Preferences/CheckBox_HiddenFiles'), 2,FailureHandling.CONTINUE_ON_FAILURE)

		extentTest.log(LogStatus.PASS, 'result value -- '+ result)
		extentTest.log(LogStatus.PASS, 'preValue value -- '+ preValue)


		if(preValue) {
			if(result) {
				extentTest.log(LogStatus.PASS, 'Enable hidden items is checked')
				extentTest.log(LogStatus.PASS, 'pref - true , res - true ')
			}
			else {
				WebUI.click(findTestObject('PageNavigation/Preferences/Tickmark'))
				extentTest.log(LogStatus.PASS, ' Checked the Enable hidden items checkbox')
				extentTest.log(LogStatus.PASS, 'pref - true , res - false ')
			}
		}
		else {
			if(result) {
				extentTest.log(LogStatus.PASS, 'Enable hidden items is checked')
				WebUI.click(findTestObject('PageNavigation/Preferences/Tickmark'))
				extentTest.log(LogStatus.PASS, ' UnChecked the Enable hidden items checkbox')
				extentTest.log(LogStatus.PASS, 'pref - false , res - true ')
			}
			else {
				extentTest.log(LogStatus.PASS, ' Enable hidden items checkbox is unchecked')
				extentTest.log(LogStatus.PASS, 'pref - false , res - fasle ')
			}
		}

		WebUI.click(findTestObject('PageNavigation/Preferences/Back'))
		extentTest.log(LogStatus.PASS, 'Click on back')
	}

	@Keyword
	def checkHiddenItems(def preValue, TestCaseName, extentTest) {
		TestObject newFileObj
		TestObject newFolderObj
		def isFilePresent
		def isFolderPresent
		def result



		if (TestCaseName.contains('tile view')) {
			WebUI.delay(2)
			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'data-automation-id', 'equals', ".hiddenFile",true)
			newFolderObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/FolderRowItem_TileView'), 'data-automation-id', 'equals',".hiddenFolder", true )
		} else {
			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals', ".hiddenFile",true)
			newFolderObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/FolderRowItem_ListView'), 'data-automation-id','equals', ".hiddenFolder", true)
		}

		isFilePresent=WebUI.verifyElementPresent(newFileObj, 3, FailureHandling.CONTINUE_ON_FAILURE)
		isFolderPresent=WebUI.verifyElementPresent(newFolderObj, 3, FailureHandling.CONTINUE_ON_FAILURE)

		if(preValue) {
			if(isFilePresent && isFilePresent) {
				extentTest.log(LogStatus.PASS, 'Hidden File Present - '+isFilePresent+' Hidden Folder present - '+isFilePresent)
				result=true
			}
			else {
				extentTest.log(LogStatus.FAIL, 'Hidden File/Folder not listed ')
				result=false
			}
		}
		else {
			if(isFilePresent && isFilePresent) {
				extentTest.log(LogStatus.FAIL, 'Hidden File Present - '+isFilePresent+' Hidden Folder present - '+isFilePresent +"- for prefrece set to false" )
				result=false
			}
			else {
				extentTest.log(LogStatus.PASS, 'Hidden File/Folder not listed ')
				result=true
			}
		}
	}
	@Keyword
	def navigateTo(String TestCaseName , String userChoice ,extentTest,user) {

		def navLocation =(new generateFilePath.filePath()).execLocation()
		def location
		if(user=="admin") {
			location = "/stage/pbsworks"+"/ForHidden/"
		}
		else {
			location = navLocation + '/ForHidden/'
		}
		WebUI.delay(2)
		WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
		WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.BACK_SPACE))
		WebUI.delay(3)

		//WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),AllJobsUser)
		WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), 'hidden')
		WebUI.delay(2)
		if(userChoice=='Input'||userChoice=='Output') {
			//WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))
			TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
					'Completed', true)
			WebUI.click(newJobFilter)
			WebUI.delay(2)
			extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + 'Completed')
			TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals','Completed', true)
			WebUI.rightClick(newJobRow)
			WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
			extentTest.log(LogStatus.PASS, 'Click on view details job')
			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/JobDetailsPage/Input_tab'))
		}
		if(userChoice=='Running') {
			//	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))
			TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
					'Running', true)
			WebUI.click(newJobFilter)
			WebUI.delay(2)
			extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + 'Running')
			TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals','Running', true)
			WebUI.rightClick(newJobRow)
			WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
			extentTest.log(LogStatus.PASS, 'Click on view details job')

			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/JobDetailsPage/Running_tab'))
			WebUI.waitForElementVisible(findTestObject('Object Repository/LoginPage/NewJobPage/HiddenFolder_Jobdetailspage'), 10)
			WebUI.doubleClick(findTestObject('Object Repository/LoginPage/NewJobPage/HiddenFolder_Jobdetailspage'))
		}

		switch (userChoice) {
			case 'FilesTab':
			case 'Files':
				WebUI.delay(2)
				def filesTab = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('GenericObjects/FilesTab_disabled'), 5,extentTest, 'Files Tab')
				if (filesTab) {
					WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
				}
				extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
				WebUI.delay(2)
				(new operations_FileModule.ChangeView()).changePageView(TestCaseName,extentTest)
				WebUI.delay(2)
				println(TestCaseName)
			/*WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			 WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
			 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			 extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)*/

				(new generateFilePath.filePath()).navlocation(location, extentTest)

				break

			case 'RFB':

				def jobsTab =(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 5,extentTest, 'Jobs Tab')
				if (jobsTab) {
					WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
				}
				extentTest.log(LogStatus.PASS, 'Navigated Job Tab')
				WebUI.delay(2)
				TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals',
						'ShellScript', true)
				WebUI.click(newAppObj)
				extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - '+'ShellScript')
				WebUI.delay(2)
				WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
				WebUI.delay(2)
			/*	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			 WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
			 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			 extentTest.log(LogStatus.PASS, 'Navigated to '+location)*/

				(new generateFilePath.filePath()).navlocation(location, extentTest)
				break

			case 'Input':

				WebUI.click(findTestObject('JobMonitoringPage/InputFolder'))
				extentTest.log(LogStatus.PASS, 'Click on Input Folder')
				break
			case 'Output':
				WebUI.click(findTestObject('JobMonitoringPage/OutputFolder'))
				extentTest.log(LogStatus.PASS, 'Click on Output Folder')
			//	WebUI.waitForElementVisible(findTestObject('Object Repository/LoginPage/NewJobPage/HiddenFolder_Jobdetailspage'), 10)
			//	WebUI.doubleClick(findTestObject('Object Repository/LoginPage/NewJobPage/HiddenFolder_Jobdetailspage'))
				break
			case 'Running':
				WebUI.click(findTestObject('JobMonitoringPage/RunningFolder'))
				extentTest.log(LogStatus.PASS, 'Click on Running Folder')
				break
		}
	}
}




