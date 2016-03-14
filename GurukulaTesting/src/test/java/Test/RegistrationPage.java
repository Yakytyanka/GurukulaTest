package Test;

import static org.testng.AssertJUnit.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends GurukulaWelcomePage {
	
	private String loginName;
	
	private final By userNameFieldLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/input");
	private final By userEmailFieldLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/input");
	private final By userPasswordFieldLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/input");
	private final By userPasswordConFieldLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[4]/input");
	private final By RegisterButtonLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/form/button");
	private final By PageTitleLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/h1");
	private final By loginLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/div[6]/a");
	private final By errorUserExistLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/div[3]");
	
	public RegistrationPage(){
	}	
	
	public RegistrationPage typeLogin(String login){
		this.loginName = login;
		getBrowser().findElement(userNameFieldLocator).click();
		getBrowser().findElement(userNameFieldLocator).sendKeys(login);
		return this;
	}
	
	public RegistrationPage typeEmail(String email){
		getBrowser().findElement(userEmailFieldLocator).click();
		getBrowser().findElement(userEmailFieldLocator).sendKeys(email);
		return this;
	}
	
	public RegistrationPage typePassword(String password){
		getBrowser().findElement(userPasswordFieldLocator).click();
		getBrowser().findElement(userPasswordFieldLocator).sendKeys(password);
		return this;
	}
	
	public RegistrationPage typePasswordConf(String passwordConf){
		getBrowser().findElement(userPasswordConFieldLocator).click();
		getBrowser().findElement(userPasswordConFieldLocator).sendKeys(passwordConf);
		return this;
	}
	
	public void submit(){		
		getBrowser().findElement(RegisterButtonLocator).click();	
	}
	
	@Override
	public AuthenticationPage openAuthenticationPageByLink(){
		getBrowser().findElement(loginLocator).click();
		return new AuthenticationPage();
	}

	public void checkTitle() {
		WebElement pageTitleElement = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(PageTitleLocator));	
		//assertEquals("Registration", pageTitleElement.getText());
		
	}

	public void newUser(String login, String email, String password, String passwordConf) {
		typeLogin(login);
		typeEmail(email);
		typePassword(password);
		typePasswordConf(passwordConf);		
		submit();
	}

	public void checkAlreadyExists() throws InterruptedException {
		waitForLoadPage();
		WebElement errorUserExist = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(errorUserExistLocator));		
		assertEquals("Login name already registered! Please choose another one.", errorUserExist.getText());
		
	}

	public void checkRegisterCorrect() throws InterruptedException {
		waitForLoadPage();
		WebElement errorUserExist = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(errorUserExistLocator));		
		assertEquals("User \"" + loginName + "\" is registered", errorUserExist.getText());
		
	}
	
}
