package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;


public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter; //UI of report
	public ExtentReports extent; //populate common info on the report
	public ExtentTest test; // creating TC entries in report and update status of test methods.
	
	String repName;
	
	  public void onStart(ITestContext testcontext) {
		  
		  String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		  repName="Test-Report - " + timeStamp + ".html";
		    sparkReporter = new ExtentSparkReporter(System.getProperty(".\\reports\\")+repName);
		    sparkReporter.config().setDocumentTitle("Opencart Automation Report"); //Title of report
		    sparkReporter.config().setReportName("Opencart Functional Testing"); //Name of report
		    sparkReporter.config().setTheme(Theme.DARK); //Theme of report
		    
		    extent=new ExtentReports();
		    extent.attachReporter(sparkReporter);
		    
		    extent.setSystemInfo("Application", "Opencart");
		    extent.setSystemInfo("Module", "Admin");
		    extent.setSystemInfo("Sub-module", "Customer");
		    extent.setSystemInfo("Tester name", System.getProperty("user.name"));
		    extent.setSystemInfo("Environment", "QA");
		   
		    String os = testcontext.getCurrentXmlTest().getParameter("os");
		    extent.setSystemInfo("Operating system", os);
		    
		    String browser = testcontext.getCurrentXmlTest().getParameter("browser");
		    extent.setSystemInfo("Browser name", browser);
		    
		    List<String> includedGroups=testcontext.getCurrentXmlTest().getIncludedGroups();
		    if(!includedGroups.isEmpty()) {
		    	extent.setSystemInfo("Groups", includedGroups.toString());
		    }
		  }
	  
	  public void onTestSuccess(ITestResult result) {
		    test = extent.createTest(result.getTestClass().getName()); //create new entry in report
		    test.assignCategory(result.getMethod().getGroups());
		    test.log(Status.PASS, "TC Passed is: "+ result.getName()); //update status
		  }
	  
	  public void onTestFailure(ITestResult result) {
		  test = extent.createTest(result.getTestClass().getName()); //create new entry in report
		    test.assignCategory(result.getMethod().getGroups());
		    test.log(Status.FAIL, "TC Failed is: "+ result.getName()); //update status
		    test.log(Status.INFO, "TC Failed cause is: "+ result.getThrowable().getMessage());
		    
		    try {
		    	String imgPath = new BaseClass().captureScreen(result.getName());
		    	test.addScreenCaptureFromPath(imgPath);
		    }
		    catch(IOException e1) {
		    	e1.printStackTrace();
		    }
		  }
	  
	  public void onTestSkipped(ITestResult result) {
		    test = extent.createTest(result.getTestClass().getName()); //create new entry in report
		    test.assignCategory(result.getMethod().getGroups());
		    test.log(Status.SKIP, "TC Skiped is: "+ result.getName()); //update status
		    test.log(Status.INFO, result.getThrowable().getMessage());
		  }
	  
	  public void onFinish(ITestResult context) {
		  extent.flush();
		  
		  String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
		  File extentReport = new File(pathOfExtentReport);
		  
		  try {
			  Desktop.getDesktop().browse(extentReport.toURI());
		  }
		  catch(IOException e) {
			  e.printStackTrace();
		  }
		  
		/*  try {
			  URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
			  
			  //Creating email message
			  ImageHtmlEmail email = new ImageHtmlEmail();
			  email.setDataSourceResolver(new DataSourceUrlResolver(url));
			  email.setHostName("chethanaa31@gmail.com");
			  email.setSmtpPort(465);
			  email.setAuthenticator(new DefaultAuthenticator("chethanab31@gmail.com","madeshbharathi"));
			  email.setSSLOnConnect(true);
			  email.setFrom("chethanab31@gmail.com");
			  email.setSubject("Test Results");
			  email.setMsg("PFA");
			  email.addTo("chethanaa31@gmail.com");
			  email.attach(url,"extent report", "please check report");
			  email.send(); //send email
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  } */
	  }
}
