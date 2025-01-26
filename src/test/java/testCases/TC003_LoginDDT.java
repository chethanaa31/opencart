package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="datadriven")
	public void  verify_loginDDT(String email, String pwd, String exp){
		
		try {
		logger.info("*** Starting TC003_LoginDDT ***");
		
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp=new LoginPage(driver);
		lp.setemail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		MyAccountPage map= new MyAccountPage(driver);
		boolean target=map.isMyAccountPageExist();
		
		/*
		 * Data is valid - login success - test passed - logout
		 					login failed - test fail
		 					
		 * Data is invalid - login success - test failed - logout
		 					login failed - test pass
		 */
		
		if(exp.equalsIgnoreCase("valid")) {
			if(target==true) {
				Assert.assertTrue(true);
				map.clkLogout();
			}
			else {
				Assert.assertTrue(false);
			}
			
		}
		
		if(exp.equalsIgnoreCase("invalid")) {
			if(target==true) {
				
				map.clkLogout();
				Assert.assertTrue(false);
			}
			else {
				Assert.assertTrue(true);
			}
			
		}
				
		
	} 
		catch(Exception e) {
			Assert.fail();
		}
		logger.info("*** Finishing TC003_LoginDDT ***");
		
	}
}
