package com.timesjob.one.utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.util.Properties;


import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import org.apache.commons.io.FileUtils;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
public class Utility {
	
	public static WebDriver driver;
	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	static Cell Cell;
	static int rowcount;
	static int columncount;
	static String xData[][];

//Method to launch the browser//
	public static WebDriver Browser(String test_browser) 
	{
		
		
		switch (test_browser) 
		{
		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver","drivers//geckodriver.exe");
			driver = (WebDriver) new FirefoxDriver();
			return driver;

		case "IE":
			System.out.println("Inside IE");
			System.setProperty("webdriver.ie.driver","drivers//IEDriverServer.exe");
			System.out.println("Got driver");
			driver =(WebDriver)new InternetExplorerDriver();
			return driver;

		case "CHROME":
			System.out.println("Inside Chrome");
			System.setProperty("webdriver.chrome.driver","drivers//chromedriver.exe");
			System.out.println("Got driver");
			ChromeOptions chromeOption = new ChromeOptions();
			chromeOption.addArguments("--disable-popup-blocking");
			driver=(WebDriver) new ChromeDriver(chromeOption);
			System.out.println("Driver created");
			return  driver;
		
		default:
			System.out.println("We don't support this Browser Sorry!!!!!!!!!!");
			throw new RuntimeException("Browser type unsupported");
		}
	}
	
	//Method to read data from excel sheet//
	public static void getExcelSheetDetails(String path,String sheetname) throws Exception
	{
	
		
		System.out.println("Entered getExcelSheetDetails");
		System.out.println("path="+path+ "sheetname="+sheetname);
		FileInputStream fis=new FileInputStream(new File(path));
		wb=new XSSFWorkbook(fis);
		sheet=wb.getSheet(sheetname);
	}
	
	//Method to get CellData from data sheet//
	public static String getCellData(int rownum,int colnum) throws Exception
	{
		Cell=sheet.getRow(rownum).getCell(colnum);
		String CellData=Cell.getStringCellValue();
		return CellData;
	}
	
	//Method to get number of rows//
	public static int getRowCount()
	{
		rowcount=sheet.getLastRowNum();
		return rowcount;
	}
		
	//Method to get number of columns//
	public static int getColumnCount()
	{
		
		columncount=sheet.getRow(0).getLastCellNum();
		return columncount;
	}
	
	//Method to read data from property file
	public static String readTestDataFromProperties(String propertiesfilename,String Xlpath) 
	{
		Properties properties = new Properties();
		try 
		{ //  Log.info("Reading Properties files");
			properties.load(new FileInputStream(propertiesfilename));
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			System.out.println("[ERROR] File Not Found");
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
		System.out.println("[ERROR] File reading fail");
		}
		String testdataPath = properties.getProperty(Xlpath);
		// System.out.println(testdataPath);
		return testdataPath;
	}
	
	
	//Method to write to excel
	
		public static void setExcelSheetDetails(String path,String sheetname,int rownum,int columnnum,String Result) throws Exception
		{
		
			
			System.out.println("Entered setExcelSheetDetails");
			System.out.println("path="+path+ "sheetname="+sheetname);
			File src=(new File(path));
			FileInputStream fis=new FileInputStream(src);
			wb=new XSSFWorkbook(fis);
			sheet=wb.getSheet(sheetname);
			sheet.getRow(rownum).createCell(columnnum).setCellValue(Result);
			FileOutputStream fout=new FileOutputStream(src);
			wb.write(fout);
			wb.close();
			
		}
		
	//Method to read data from excel
	//Reading excel
			public static String[][] xlRead(String path, String sheetname)
					throws IOException 
			{   
				
				System.out.println("Entered getExcelSheetDetails");
				System.out.println("path="+path+ "sheetname="+sheetname);
				FileInputStream fis=new FileInputStream(new File(path));
				wb=new XSSFWorkbook(fis);
				sheet=wb.getSheet(sheetname);
				rowcount = sheet.getLastRowNum();
				columncount = sheet.getRow(0).getLastCellNum();
				System.out.println("Rows are " + rowcount);
				System.out.println("Cols are " + columncount);
				xData = new String[rowcount][columncount];
				for (int i = 0; i < rowcount; i++)
				{
					Row row = sheet.getRow(i);
					for (int j = 0; j < rowcount; j++)
					{
		
						Cell cell = row.getCell(j); // To read value from each col in  each row
						if (cell != null)
						{
							String value = cell.getStringCellValue();
							xData[i][j] = value;
							System.out.print("  ");
						} 
						else 
						{
							String value = "";
							xData[i][j] = value;
						}
					}
					System.out.println("");
				}
				System.out.println("Got xdata");
				return xData;
				
			}
			
			public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{

			    //Convert web driver object to TakeScreenshot

			    TakesScreenshot scrShot =((TakesScreenshot)webdriver);

			    //Call getScreenshotAs method to create image file

			            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

			        //Move image file to new destination

			            File DestFile=new File(fileWithPath);

			            //Copy file at destination

			            FileUtils.copyFile(SrcFile, DestFile);

			}
			
}
