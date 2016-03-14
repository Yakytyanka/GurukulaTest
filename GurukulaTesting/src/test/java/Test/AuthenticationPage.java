package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.AssertJUnit.*;

public class AuthenticationPage extends GurukulaWelcomePage {
	
	private final By messageLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/h1");
	private final By userNameLocator = By.id("username");
	private final By passportLocator = By.id("password");
	private final By loginButtonLocator = By.xpath("//button[@type='submit']");
	private final By authomaticLoginLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/label/input");
	private final By forgetPasswordLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/div[2]/a");
	private final By registerLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/div[3]/a");
	
	private String loginName;
	private String password;
	
	protected AuthenticationPage(){
		super();
		WebElement loginAlert = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(loginButtonLocator));	
	}
	
	public void typePassword(String password){
		this.password = password;
		getBrowser().findElement(passportLocator).click();
		getBrowser().findElement(passportLocator).sendKeys(password);
	}
	
	public void typeLogin(String loginName){
		this.loginName = loginName;
		getBrowser().findElement(userNameLocator).click();
		getBrowser().findElement(userNameLocator).sendKeys(loginName);
	}
	
	public GurukulaWelcomePage submitLogin(){
		getBrowser().findElement(loginButtonLocator).submit();
		return new GurukulaWelcomePage(loginName);
	}
	
	public GurukulaWelcomePage loginAs(String loginName, String password){
		typeLogin(loginName);
		typePassword(password);
		return submitLogin();
		
	}
	
	@Override
	public RegistrationPage openRegistrationByLink(){
		getBrowser().findElement(registerLocator).click();
		return new RegistrationPage();
	}

	public void checkMessage() {
		assertEquals("Authentication", getBrowser().findElement(messageLocator).getText());
		
	}

}
