package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//*[@id=\"input-email\"]")
	WebElement emailAddress;
	
	@FindBy(xpath="//*[@id=\"input-password\"]")
	WebElement pwd;
	
	@FindBy(xpath="//*[@id=\"content\"]/div/div[2]/div/form/input")
	WebElement btn_login;
	
	public void setemail(String email) {
		emailAddress.sendKeys(email);
	}
	
	public void setPassword(String password) {
		pwd.sendKeys(password);
	}
	
	public void clickLogin() {
		btn_login.click();
	}

}
