package com.timesjob.one.driverScript;


import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.timesjob.one.utils.Utility;

public class DriverScript extends Utility 
{
	
	String myXLPath;
	static String xData[][];
	public static WebDriver driver;

	
//@DataProvider(name = "myTest")
public String [][] createData() throws Exception
{
	 
	myXLPath = Utility.readTestDataFromProperties("Properties/config.properties","TestData");
	xData=Utility.xlRead(myXLPath,"TestData");
	return xData;
}

public static void launch(File folder,String execute,String browser,String url,int rownum,int columnnum) throws Exception
{
	if (execute.equals("Y")) 
	{	
		try{			
			// Open the browser
				if (!(browser.equals("FIREFOX") || browser.equals("IE") || browser.equals("CHROME"))) 
				{
					System.out.println("Unsupported Brow");
					Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases", rownum, columnnum, "Fail");
					Utility.takeSnapShot(driver,folder+"//launchfailed.png");
				} 
				else 
				{
					System.out.println("Inside launch");
					//Open browser
					driver = Utility.Browser(browser);
					System.out.println("Driver intiated");
					//Opening the URL
					driver.get(url);
					driver.manage().window().maximize();
					Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases", 1, 2, "Pass");
					Utility.takeSnapShot(driver,folder+"//launchpassed.png");
				}
		}
		catch(Exception e)
		{
			System.out.println("Exception occured"+e);
			Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases", 1, 2, "Fail");
			Utility.takeSnapShot(driver,folder+"//launchfailed.png");
		}
	}
}

//code for registration
public static void register(File folder,String execute,String emailId,
		String password,String confirmpassword,String mobileno,String jobfunction,
		String exp_years,String exp_months,String currentLocation,int rownum,int columnnum) throws Exception
{
	if (execute.equals("Y")) 
	{	
		try{			
				driver.findElement(By.xpath("//a[contains(.,'Login')]/parent::li/following-sibling::li[1]/a")).click();
				driver.findElement(By.id("emailAdd")).sendKeys(emailId);
				driver.findElement(By.id("passwordField")).sendKeys(password);
				driver.findElement(By.id("retypePassword")).sendKeys(confirmpassword);
				driver.findElement(By.id("mobNumber")).sendKeys(mobileno);
				
				driver.findElement(By.xpath("//b[contains(.,'Please Select from Below Option')]")).click();
				driver.findElement(By.xpath("//input[contains(@placeholder,'Functional Area')]")).clear();
				driver.findElement(By.xpath("//input[contains(@placeholder,'Functional Area')]")).sendKeys(jobfunction);
				driver.findElement(By.xpath("//label/span[contains(.,'"+jobfunction+"')]")).click();
				driver.findElement(By.id("cboWorkExpYear")).sendKeys(exp_years);
				driver.findElement(By.id("cboWorkExpMonth")).sendKeys(exp_months);
				
				Select location = new Select(driver.findElement(By.id("curLocation")));
				location.selectByVisibleText(currentLocation);
				
				driver.findElement(By.id("basicSubmit")).click();
				
				Thread.sleep(2000);
				String title=driver.getTitle();
				System.out.println(title);
				int size=driver.findElements(By.xpath("//h4[contains(.,'Congratulations')]")).size();
				System.out.println(size);
				if(driver.findElements(By.xpath("//h4[contains(.,'Congratulations')]")).size() != 0)
				{
					System.out.println("User got registered successfully");
					Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases", rownum	, columnnum, "Pass");
					Utility.takeSnapShot(driver,folder+"//registerpassed.png");

				}
				else
				{
					System.out.println("User cannot be registered");
					Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases", rownum	, columnnum, "Fail");
					Utility.takeSnapShot(driver,folder+"//registerfailed.png");

				}
				driver.findElement(By.xpath("//a[contains(.,'Logout')]")).click();
				driver.findElement(By.linkText("home page")).click();
			
		 }
		 catch(Exception e)
		 {
			System.out.println("Exception occured"+e);
			Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases", rownum	, columnnum, "Fail");
			Utility.takeSnapShot(driver,folder+"//registerfailed.png");

		 }
	 }
   }

public static WebDriver NewWindowDriver(String url, WebDriver driver)
		throws Exception 
{
	// String parentWindowHandler = driver.getWindowHandle(); // Store your
	// parent window
	Thread.sleep(3000);
	for (String handles : driver.getWindowHandles())
		driver.switchTo().window(handles);
	if (driver.getCurrentUrl().contains(url)) {
		
		System.out.println("Selected Window URL : "	+ driver.getCurrentUrl());
		return driver;
	}
	
	System.out.println("Not Required Window URL :" + driver.getCurrentUrl());
	return driver;
}

public static WebDriver NewFrame(String frame,WebDriver driver) throws Exception
{
	WebElement fr = driver.findElement(By.id(frame));
	driver.switchTo().frame(fr);
	//driver.switchTo().defaultContent();
	return driver;
}


public static void login(File folder,String execute,String emailId,String password, int rownum,int columnnum) throws Exception
{
	if (execute.equals("Y")) 
	{	
		try{
	driver.findElement(By.xpath("//a[(text()='Login')]")).click();
	Thread.sleep(5000);
	//NewFrame("GB_frame1", driver);
	//Thread.sleep(5000);
	//NewFrame("GB_frame",driver);
	Thread.sleep(2000);
	driver.findElement(By.id("j_username")).sendKeys(emailId);
	driver.findElement(By.id("j_password")).sendKeys(password);
	driver.findElement(By.xpath("//input[contains(@value,'Login') and (@type='button')]")).click();
	Thread.sleep(5000);
	if(driver.findElements(By.xpath("//b[contains(.,'Upload your resume')]")).size() != 0)
	{
		System.out.println("Login successfully");
		Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases", rownum	, columnnum,"Pass");
		Utility.takeSnapShot(driver,folder+"//loginpassed.png");

	}
	else
	{
		System.out.println("Login failed");
		Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases",rownum,columnnum, "Fail");
		Utility.takeSnapShot(driver,folder+"//loginfailed.png");

	}
	driver.findElement(By.xpath("//a[contains(.,'Logout')]")).click();
	driver.findElement(By.linkText("home page")).click();
	}

	catch(Exception e)
		{
			System.out.println("Exception occured"+e);
			Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases",rownum,columnnum, "Fail");
			Utility.takeSnapShot(driver,folder+"//loginfailed.png");
			

		 }
	}
}


public static void search(File folder,String execute,String skill,String location,String experience,int rownum,int columnnum) throws Exception
{
	if (execute.equals("Y")) 
	{	
		try{

	driver.findElement(By.id("txtKeywords")).sendKeys(skill);
	driver.findElement(By.id("txtLocation")).sendKeys(location);
	
	Select exp = new Select(driver.findElement(By.id("cboWorkExp1")));
	exp.selectByValue(experience);
	driver.findElement(By.xpath("//form[contains(@id,'quickSearch')]/button")).click();
	Thread.sleep(5000);
	if(driver.findElements(By.xpath("//span[contains(.,'Jobs Found')]")).size() != 0)
	{
		System.out.println("Searched successfully");
		Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases",rownum,columnnum, "Pass");
		Utility.takeSnapShot(driver,folder+"//searchpassed.png");

	}
	else
	{
		System.out.println("No jobs found matching your criteria");
		Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases",rownum,columnnum, "Fail");
		Utility.takeSnapShot(driver,folder+"//searchfailed.png");

	}
		}

	catch(Exception e)
		{
			System.out.println("Exception occured"+e);
			Utility.setExcelSheetDetails("Testdata//TimesJobTestData.xlsx", "TestCases",rownum,columnnum, "Fail");
			Utility.takeSnapShot(driver,folder+"//searchfailed.png");


		 }
	}
}

public static void main(String[]args) throws Exception
{	Utility.getExcelSheetDetails("Testdata//TimesJobTestData.xlsx","TestData");
	String Foldername = new SimpleDateFormat("MM-dd-YYYY-HH_mm_ss").format(new Date());
	File destFolder = new File("Screenshots//"+"ScreenShots_At_"+Foldername);
	if (!destFolder.exists()) {
		System.out.println("File created " + destFolder);
		destFolder.mkdir();
	}
	
	int testcaserownum=1;
	int testcasecolumnnum=2;
	//DriverScript ds=new DriverScript();
	for (int i=1;i<=Utility.getRowCount();i++)
	{
			String execute=Utility.getCellData(i,0);
			String browser=Utility.getCellData(i,1);
			String url=Utility.getCellData(i,2);
			String email=Utility.getCellData(i,3);
			String password=Utility.getCellData(i,4);
			String confirmpwd=Utility.getCellData(i,5);
			String mobileno=Utility.getCellData(i,6);
			String jobfunction=Utility.getCellData(i,7);
			String exp_years=Utility.getCellData(i,8);
			String exp_months=Utility.getCellData(i,9);
			String location=Utility.getCellData(i,10);
			System.out.println("Browser="+browser+"Url="+url+ "email="+email+ "password="+password+ "confirm="+confirmpwd+ "mobileNo="+mobileno+ "jobfunction="+jobfunction+ "exp_Years="+exp_years+ "exp_months="+exp_months+ "location="+location);
			launch(destFolder,execute,browser,url,testcaserownum,testcasecolumnnum);
			testcaserownum=testcaserownum+1;
			register(destFolder,execute,email,password,confirmpwd,mobileno,jobfunction,exp_years,exp_months,location,testcaserownum,testcasecolumnnum);
			testcaserownum=testcaserownum+1;
			login(destFolder,execute,email,password,testcaserownum,testcasecolumnnum);
			testcaserownum=testcaserownum+1;
			search(destFolder,execute,jobfunction,location,exp_years,testcaserownum,testcasecolumnnum);
			
	}
	driver.close();
	
	//String Xdata[][]=Utility.xlRead("Testdata//TimesJobTestData.xlsx","Register");
	//System.out.println("Browser="+Xdata[1][1]);
	
}


}
