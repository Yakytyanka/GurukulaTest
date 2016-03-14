package Test;

import javax.naming.InitialContext;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.javascript.host.Map;

import static org.testng.AssertJUnit.*;

import java.util.Map.*;
import java.util.HashMap;
import java.util.List;

public class GurukulaWelcomePage extends Page{
	private String userName;
	private HashMap<String, String> dropdownMap = new HashMap<String, String>();
	private final String dropdownLocater = ".//*[@id='navbar-collapse']/ul/li";
	private final By homeButtonLocator = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[1]/a[2]");
	private final By accountButtonLocator = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/a");
	private final By accountButtonLocatorNew = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[3]/a");
	private final By authenticateButtonLocator = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/ul/li[1]/a");
	private final By registerButtonLocator = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/ul/li[2]/a");
	private final By messageLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/h1");
	private final By loginLinkLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/div/div[1]/a");
	private final By registerLinkLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/div/div[2]/a");
	private final By loginMessageLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/div/div");
	private final By loginWrongMessageLocator = By.xpath("/html/body/div[3]/div[1]/div/div/div/div[1]/strong");
	
	private final By logOutButtonLocator = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[3]/ul/li[4]/a");
	private final By entitiesButtonLocator = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/a");
	private final By branchButtonLocator = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/ul/li[1]/a");
	private final By stuffButtonLocator = By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/ul/li[2]/a");
	
	public GurukulaWelcomePage(){
		super();
		this.userName = "";
		//reloadDropdown();
	}
	
	public GurukulaWelcomePage(String userName) {
		this();
		this.userName = userName;	
	}	
	
	public AuthenticationPage openAuthenticationByDropdown(){
		/*getBrowser().findElement(By.xpath(dropdownMap.get("Account"))).click();
		getBrowser().findElement(By.xpath(dropdownMap.get(" Authenticate"))).click(); */
		getBrowser().findElement(accountButtonLocator).click();
		getBrowser().findElement(authenticateButtonLocator).click(); 
		
		return new AuthenticationPage();
	}
	
	public RegistrationPage openRegistrationByDropDown(){
		/*getBrowser().findElement(By.xpath(dropdownMap.get("Account"))).click();
		getBrowser().findElement(By.xpath(dropdownMap.get(" Register"))).click();*/
		getBrowser().findElement(accountButtonLocator).click();
		getBrowser().findElement(registerButtonLocator).click();
		return new RegistrationPage();
	}
	
	public AuthenticationPage openAuthenticationPageByLink(){
		getBrowser().findElement(loginLinkLocator).click();
		return new AuthenticationPage();
	}
	
	public RegistrationPage openRegistrationByLink(){
		getBrowser().findElement(registerButtonLocator).click();
		return new RegistrationPage();
	}
	
	public void openHome(){
		//getBrowser().findElement(By.xpath(dropdownMap.get("Home"))).click();
		getBrowser().findElement(homeButtonLocator).click();
	}
	
	public void logOut(){
		/*getBrowser().findElement(By.xpath(dropdownMap.get("Account"))).click();
		getBrowser().findElement(By.xpath(dropdownMap.get(" Log out"))).click();*/
		getBrowser().findElement(accountButtonLocatorNew).click();
		getBrowser().findElement(logOutButtonLocator).click();
		reloadDropdown();
	}
	
	private void reloadDropdown(){			
		List<WebElement> dropdown = getBrowser().findElements(By.xpath(dropdownLocater));				
		dropdownMap.clear();
		
		for (int i=1; i <= dropdown.size(); i++){
			String dropdownFirstLocator = dropdownLocater + "[" + i + "]";
			WebElement dropdownFirst = getBrowser().findElement(By.xpath(dropdownFirstLocator));
			dropdownMap.put(dropdownFirst.getText(), dropdownFirstLocator);
			dropdownFirstLocator = dropdownFirstLocator + "/ul/li";
			
			if(isElementPresented(dropdownFirstLocator)){
				List<WebElement> dropdownSecondList = getBrowser().findElements(By.xpath(dropdownFirstLocator));
				dropdownFirst.click();	//?getText doesn't work without click
				for (int j=1; j <= dropdownSecondList.size(); j++){
					String dropdownSecondLocator = dropdownFirstLocator + "[" + j + "]";
					WebElement dropdownSecond = getBrowser().findElement(By.xpath(dropdownSecondLocator));
					dropdownMap.put(dropdownSecond.getText(), dropdownSecondLocator);	
				}
				dropdownFirst.click();
			}
		}
		
	}
	
	public void checkHomeMessage(){		
		assertEquals("Welcome to Gurukula!", getBrowser().findElement(messageLocator).getText());
	}
	
	public void checkHomeLoginMessage(){	
		
		WebElement loginAlert = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(loginMessageLocator));		
		assertEquals("You are logged in as user \"" + userName + "\".", loginAlert.getText());
	}

	public void checkHomeLoginMessageWrong() throws InterruptedException {
		waitForLoadPage();
		WebElement loginWrongAlert = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(loginWrongMessageLocator));		
		assertEquals("Authentication failed!", loginWrongAlert.getText());
	}

	public BranchPage openBranchPage() {
		/*getBrowser().findElement(By.xpath(dropdownMap.get("Entities"))).click();
		getBrowser().findElement(By.xpath(dropdownMap.get(" Branch"))).click();*/
		getBrowser().findElement(entitiesButtonLocator).click();
		getBrowser().findElement(branchButtonLocator).click();
		return new BranchPage();
	}
	
	public StuffPage openStuffPage() {
		/*getBrowser().findElement(By.xpath(dropdownMap.get("Entities"))).click();
		getBrowser().findElement(By.xpath(dropdownMap.get(" Staff"))).click();*/
		getBrowser().findElement(entitiesButtonLocator).click();
		getBrowser().findElement(stuffButtonLocator).click();
		return new StuffPage();
	}
}

