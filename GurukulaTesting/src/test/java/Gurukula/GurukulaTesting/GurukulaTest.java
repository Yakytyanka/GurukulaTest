package Gurukula.GurukulaTesting;

import org.testng.annotations.*;

import Test.*;

public class GurukulaTest {
	
	private GurukulaWelcomePage WelcomePage;
	@BeforeClass(alwaysRun = true)
	@Parameters("browser")
	public void setUp(String browser) throws Exception {
		if(browser.equalsIgnoreCase("FF")){
			Context.initInstance(Context.BROWSER_FF, "http://127.0.0.1:8080/");
		}
		else if(browser.equalsIgnoreCase("CH")){
			Context.initInstance(Context.BROWSER_CH, "http://127.0.0.1:8080/");
		}
		else {
			Context.initInstance(Context.BROWSER_IE, "http://127.0.0.1:8080/");
		}
		WelcomePage = new GurukulaWelcomePage();
	}	
	
  @Test(priority = 1)
  public void AdminLogIn() throws InterruptedException {	  
	  AuthenticationPage AuthPage = WelcomePage.openAuthenticationByDropdown();
	  AuthPage.checkMessage();
	  WelcomePage = AuthPage.loginAs("admin", "admin");
 	  WelcomePage.checkHomeLoginMessage();	
	  WelcomePage.logOut();
  }
  
  @Test(priority = 2)
  public void AdminLogInWrong() throws InterruptedException {
	  AuthenticationPage AuthPage = WelcomePage.openAuthenticationByDropdown();
	  AuthPage.checkMessage();
	  WelcomePage = AuthPage.loginAs("admin", "admin45345");
	  WelcomePage.checkHomeLoginMessageWrong();	 
	  WelcomePage.openHome();
  }  
  
  @Test(priority = 3)
  public void UserExistsRegistration() throws InterruptedException {
	  String password = "Drowssap_56";
	  RegistrationPage RegistrationPage = WelcomePage.openRegistrationByDropDown();
	  RegistrationPage.checkTitle();
	  RegistrationPage.newUser("admin", "admin@localhost", password, password);
	  RegistrationPage.checkAlreadyExists();	
	  RegistrationPage.openHome();
  }
  
  @Test(priority = 4)
  public void UserRegistration() throws InterruptedException {
	  String password = "Drowssap_56";
	  RegistrationPage RegistrationPage = WelcomePage.openRegistrationByDropDown();
	  RegistrationPage.checkTitle();
	  RegistrationPage.newUser("newuser", "newuser@localhost", password, password);	  
	  RegistrationPage.checkRegisterCorrect();
	  RegistrationPage.logOut();
	  RegistrationPage.checkHomeMessage();
  }
  
  @Test(priority = 5)
  public void BranchCreation() throws InterruptedException {
	  AuthenticationPage AuthPage = WelcomePage.openAuthenticationByDropdown();
	  AuthPage.checkMessage();
	  WelcomePage = AuthPage.loginAs("admin", "admin");
	  WelcomePage.checkHomeLoginMessage();
	  BranchPage BranchPage = WelcomePage.openBranchPage();
	  // Branch creation
	  String branchId = BranchPage.createNew("Moskow", "44");	  
	  // Branch editing
	  BranchPage.edit(branchId, "Moscow", null);	  
	  // Branch deleting
	  BranchPage.delete(branchId);
	  BranchPage.logOut();
	  BranchPage.checkHomeMessage();
  }
  
  @Test(priority = 6)
  public void CreateStuff() throws InterruptedException {
	  String branch_1;
	  String branch_2;
	  
	  AuthenticationPage AuthPage = WelcomePage.openAuthenticationByDropdown();
	  AuthPage.checkMessage();
	  WelcomePage = AuthPage.loginAs("admin", "admin");
	  WelcomePage.checkHomeLoginMessage();
	  BranchPage BranchPage = WelcomePage.openBranchPage();	  
	  
	  branch_1 = BranchPage.getBranchByName("Amsterdam");
	  if (branch_1 == null){
		  branch_1 = BranchPage.createNew("Amsterdam", "01");
	  }
	  
	  branch_2 = BranchPage.getBranchByName("Novosibirsk");
	  if (branch_2 == null){
		  branch_2 = BranchPage.createNew("Novosibirsk", "01");
	  }
	  
	  StuffPage StuffPage = BranchPage.openStuffPage();
	  String stuffId = StuffPage.createNew("Ekaterina", branch_2);
	  StuffPage.edit(stuffId, null, branch_1);
	  StuffPage.delete(stuffId);
	  StuffPage.logOut();
	  BranchPage.checkHomeMessage();
  }

  @AfterTest
  public void afterTest() {
	  Context.getInstance().getBrowser().quit();
  }

}
