package testCases;

import org.testng.Assert;
import org.testng.annotations.*;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testCases.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	
	
	@Test (groups= {"Sanity", "Master"})
	public void  verify_login(){
		
		try {
		logger.info("*** Starting TC002_LoginTest ***");
		
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp=new LoginPage(driver);
		lp.setemail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		MyAccountPage map= new MyAccountPage(driver);
		boolean target=map.isMyAccountPageExist();
		Assert.assertEquals(target, true);
		
		
		logger.info("*** Finishing TC002_LoginTest ***");
		}
		catch(Exception e) {
			Assert.fail();
		}
		
	}
	

}
