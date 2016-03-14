package Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class StuffPage extends GurukulaWelcomePage{	
	private final By createNewLocator = By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[1]/button");
	private final By searchFieldLocator = By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/div/input");
	private final By searchButtonLocator = By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button");
	private final By PageTitleLocator = By.xpath("html/body/div[3]/div[1]/div/h2");
	private final By nameLocator = By.xpath(".//*[@id='saveStaffModal']/div/div/form/div[2]/div[2]/input");
	private final By branchLocator = By.xpath(".//*[@id='saveStaffModal']/div/div/form/div[2]/div[3]/select");
	private final By saveLocator = By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[2]");
	private final String tableXPath = "html/body/div[3]/div[1]/div/div[4]/table/tbody";
	private final By deleteLocator = By.xpath("/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[2]");
	
	private String id_col = "1";
	private String name_col = "2";
	private String branch_col = "3";
	private String buttons_col = "4";
	private String t_view_button = "1";
	private String t_edit_button = "2";
	private String t_delete_button = "3";
	
	public StuffPage(){
		WebElement createNewButton = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(createNewLocator));			
		assertEquals("Staffs", getBrowser().findElement(PageTitleLocator).getText());
	}
	
	public String submit(){			
		getBrowser().findElement(saveLocator).submit();;
		getBrowser().navigate().refresh();		
		//todo: проверка на страницу	
		List <WebElement> table = getBrowser().findElements(By.xpath(tableXPath + "/tr"));
		return getBrowser().findElement(By.xpath(tableXPath + "/tr[" + Integer.toString(table.size()) + "]/td[" + id_col +"]")).getText();
	}	
	
	public String createNew(String name, String branch_id) throws InterruptedException{			
		getBrowser().findElement(createNewLocator).click();
		waitForLoadPage();
		WebElement codeField = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(nameLocator));			
		selectBranch(branch_id);		
		getBrowser().findElement(branchLocator).sendKeys(branch_id);
		getBrowser().findElement(nameLocator ).click();
		getBrowser().findElement(nameLocator).sendKeys(name);
		assertTrue(getBrowser().findElement(saveLocator).isEnabled());
		return submit();
	}

	private void selectBranch(String branch_id) {
		Select branchList = new Select(getBrowser().findElement(branchLocator));		
		branchList.selectByValue("number:" + branch_id);
	}
	
	public String getStuffRow(String id){
		int i = 1;		
		while (isElementPresented(tableXPath + "/tr[" + i + "]/td[" + id_col + "]")){			
			if (id.equals(getBrowser().findElement(By.xpath(tableXPath + "/tr[" + i + "]/td[" + id_col + "]")).getText())){
				return Integer.toString(i);
			}
			i++;
		}		
		return null;
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
	
	public void edit(String id, String newName, String newBranch){		
		String edit_row = getStuffRow(id);
		assertFalse(edit_row == null);
		getBrowser().findElement(By.xpath(tableXPath + "/tr[" + edit_row + "]" 
																		+ "/td[" + buttons_col +"]/button[" + t_edit_button + "]")).click();
		if (newName != null){
			getBrowser().findElement(nameLocator).clear();
			getBrowser().findElement(nameLocator).sendKeys("" + newName);
		}
		if (newBranch != null){
			selectBranch(newBranch);
		}
		assertTrue(getBrowser().findElement(saveLocator).isEnabled());
		getBrowser().findElement(saveLocator).submit();
		getBrowser().navigate().refresh();
	}

	public void delete(String id) {
		String delete_row = getStuffRow(id);
		assertFalse(delete_row == null);			
		getBrowser().findElement(By.xpath(tableXPath + "/tr[" + delete_row + "]" 
																		+ "/td[" + buttons_col +"]/button[" + t_delete_button + "]")).click();
		WebElement deleteButton = (new WebDriverWait(getBrowser(), 10))
				  .until(ExpectedConditions.presenceOfElementLocated(deleteLocator));
		
		getBrowser().findElement(deleteLocator).submit();	
		getBrowser().navigate().refresh();
	}
	
}
