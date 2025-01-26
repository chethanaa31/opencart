package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{
	public MyAccountPage(WebDriver driver) {
		super(driver);
		
	}

	@FindBy(xpath="//*[@id=\"content\"]/h2[1]")
	WebElement myaccountHeading;
	
	@FindBy(xpath="//*[@id=\"top-links\"]/ul/li[2]/ul/li[5]/a")
	WebElement btn_logout;
	
	public boolean isMyAccountPageExist() {
		try {
			return (myaccountHeading.isDisplayed());
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public void clkLogout() {
		btn_logout.click();
	}
}
