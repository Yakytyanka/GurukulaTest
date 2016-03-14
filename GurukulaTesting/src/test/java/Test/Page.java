package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class Page {
	private Context context;
	
	public Page() {        
        setContext(Context.getInstance());
    }
	
	private void setContext(Context instance) {
        this.context = instance;
    }
	
	protected WebDriver getBrowser() {
        return context.getBrowser();
	}
	
	protected boolean isElementPresented(String xPath){
		int count = getBrowser().findElements(By.xpath(xPath)).size();		
		if (count == 0){
			return false;
		}
		else
		{
			return true;
		}	
	}
	
	public void waitForLoadPage() throws InterruptedException {
		Thread.sleep(1000);
        /*ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver)   {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    } };
        (new WebDriverWait(getBrowser(), 10)).until(pageLoadCondition);
        */
    }
}
