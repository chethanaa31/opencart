package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {
	
	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement firstname;
	
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement lastname;
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement email;
	
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement telephone;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement password;
	
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement confirm;
	
	@FindBy(xpath="//input[@name='agree']")
	WebElement agreePolicy;
	
	@FindBy(xpath="//input[@value='Continue']")
	WebElement continue_btn;
	
	@FindBy(xpath="//*[@id=\"content\"]/h1")
	WebElement msgConfirmation;
	
	public void setFirstName(String fname) {
		firstname.sendKeys(fname);
	}
	
	public void setLastName(String lname) {
		lastname.sendKeys(lname);
	}
	
	public void setEmail(String email_add) {
		email.sendKeys(email_add);
	}
	
	public void setTelephone(String telephone_num) {
		telephone.sendKeys(telephone_num);
	}
	
	public void setPassword(String pwd) {
		password.sendKeys(pwd);
	}
	
	public void setConfirmPassword(String pwd) {
		confirm.sendKeys(pwd);
	}
	
	public void clkAgree() {
		agreePolicy.click();
	}
	
	public void clkContinue() {
		continue_btn.click();
	}
	
	public String successMsg() {
		try {
			return (msgConfirmation.getText());
		}
		catch (Exception e) {
			return(e.getMessage());
		}
		
	}
}
