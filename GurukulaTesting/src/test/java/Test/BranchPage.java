package Test;

import static org.testng.AssertJUnit.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BranchPage extends GurukulaWelcomePage{
	private final By createNewLocator = By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[1]/button");
	private final By searchFieldLocator = By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/div/input");
	private final By searchButtonLocator = By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button");
	private final By PageTitleLocator = By.xpath("/html/body/div[3]/div[1]/div/h2");
	
	private final By nameLocator = By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/input");
	private final By codeLocator = By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/input");
	private final By cancelLocator = By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[1]");
	private final By saveLocator = By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[2]");
	private final By deleteLocator = By.xpath("/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[2]");
	
	private final String tableXPath = "/html/body/div[3]/div[1]/div/div[4]/table/tbody";
	
	private String id_col = "1";
	private String name_col = "2";
	private String code_col = "3";
	private String buttons_col = "4";
	private String t_view_button = "1";
	private String t_edit_button = "2";
	private String t_delete_button = "3";
	
	public BranchPage() {	
		WebElement createNewButton = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(createNewLocator));			
		assertEquals("Branches", getBrowser().findElement(PageTitleLocator).getText());
	}	

	public String createNew(String name, String code) throws InterruptedException{			
		getBrowser().findElement(createNewLocator).click();
		WebElement codeField = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(codeLocator));		
		waitForLoadPage();
		getBrowser().findElement(codeLocator).click();
		getBrowser().findElement(codeLocator).sendKeys(code);
		getBrowser().findElement(nameLocator).click();
		getBrowser().findElement(nameLocator).sendKeys(name);
		assertTrue(getBrowser().findElement(saveLocator).isEnabled());
		return submit();
	}
	
	public String submit(){			
		getBrowser().findElement(saveLocator).click();
		getBrowser().navigate().refresh();		
		//todo: проверка на страницу	
		List <WebElement> table = getBrowser().findElements(By.xpath(tableXPath + "/tr"));
		return getBrowser().findElement(By.xpath(tableXPath + "/tr[" + Integer.toString(table.size()) + "]/td[" + id_col +"]")).getText();
	}		
	
	public WebElement findById(String id){			
		String branchId;
		int i = 1;		
		while(isElementPresented(tableXPath + "/tr[" + Integer.toString(i) + "]/td[" + id_col +"]")){			
			branchId = getBrowser().findElement(By.xpath(tableXPath + "/tr[" + Integer.toString(i) +"]/td[" + id_col +"]")).getText();
			if (id.equalsIgnoreCase(branchId)){
				return getBrowser().findElement(By.xpath(tableXPath + "/tr[" + i + "]"));				
			}
			else{
				i++;
			}
		}
		return null;
	}
	
	public String getBranchRow(String id){
		int i = 1;		
		while (isElementPresented(tableXPath + "/tr[" + i + "]/td[" + id_col + "]")){			
			if (id.equals(getBrowser().findElement(By.xpath(tableXPath + "/tr[" + i + "]/td[" + id_col + "]")).getText())){
				return Integer.toString(i);
			}
			i++;
		}		
		return null;
	}
	
	public String getBranchByName(String name) {
		int i = 1;		
		while (isElementPresented(tableXPath + "/tr[" + i + "]/td[" + id_col + "]")){			
			if (name.equals(getBrowser().findElement(By.xpath(tableXPath + "/tr[" + i + "]/td[" + name_col + "]")).getText())){
				return getBrowser().findElement(By.xpath(tableXPath + "/tr[" + i + "]/td[" + id_col + "]")).getText();
			}
			i++;
		}		
		return null;
	}

	public void edit(String id, String newName, String newCode) throws InterruptedException{		
		String edit_row = getBranchRow(id);
		assertFalse(edit_row == null);
		getBrowser().findElement(By.xpath(tableXPath + "/tr[" + edit_row + "]" 
																		+ "/td[" + buttons_col +"]/button[" + t_edit_button + "]")).click();
		waitForLoadPage();
		if (newName != null){
			WebElement name = getBrowser().findElement(nameLocator);
			name.click();
			name.clear();
			name.sendKeys("" + newName);
		}
		if (newCode != null){
			WebElement code = getBrowser().findElement(codeLocator);
			code.click();
			code.clear();
			code.click();
			code.sendKeys("" + newCode);
		}
		assertTrue(getBrowser().findElement(saveLocator).isEnabled());
		getBrowser().findElement(saveLocator).click();
		getBrowser().navigate().refresh();
	}

	public void delete(String id) {
		String delete_row = getBranchRow(id);
		assertFalse(delete_row == null);			
		getBrowser().findElement(By.xpath(tableXPath + "/tr[" + delete_row + "]" 
																		+ "/td[" + buttons_col +"]/button[" + t_delete_button + "]")).click();
		WebElement deleteButton = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(deleteLocator));
		
		getBrowser().findElement(deleteLocator).submit();;	
		getBrowser().navigate().refresh();
	}
}
