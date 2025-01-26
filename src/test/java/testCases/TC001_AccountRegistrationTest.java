/*
 --> Logging - record all the events in the form of text.
 --> Log Levels - All < Trace < Debug < Info < Warn < Error < Fatal < Off
 
 --> Appenders and Loggers: (main file - log4j2.xml file)
 	* Appenders - where to generate logs (Console/File)
 	* Loggers - what type of logs generate (All < Trace < Debug < Info < Warn < Error < Fatal < Off)
 */

package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {

	


	@Test(groups= {"Regression", "Master"})
	public void verify_accountRegister() {
		
		logger.info("**** Starting TC001_AccountRegistrationTest ****");
		
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on My account link.");
		
		hp.clickRegister();
		logger.info("Clicked on Register link.");
		
		AccountRegistrationPage arp=new AccountRegistrationPage(driver);
		
		//Hardcoding data - works for only 1st time.. cant register multiple times
		logger.info("Providing customer details.");
		arp.setFirstName(randomData().toUpperCase());
		arp.setLastName("M");
		//arp.setEmail("chethanaaa31@gmail.com");
		arp.setEmail(randomData()+"@gmail.com");
		arp.setTelephone("12345678");
		
		
		//arp.setPassword("abc123");
		String password=randomAlphanumeric();
		arp.setPassword(password);
		
		arp.setConfirmPassword(password);
		arp.clkAgree();
		arp.clkContinue();
		
		logger.info("Validating expected message.");
		String conf_msg=arp.successMsg();
		Assert.assertEquals(conf_msg, "Your Account Has Been Created!");
		}
		catch(Exception e) {
			logger.error("Test Failed");
			logger.debug("Debug logs.");
			Assert.fail();
		}
		logger.info("**** Finished TC001_AccountRegistrationTest ****");
	}
		
		
		
	
	
	
}
