package Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Context {
    public static final String BROWSER_IE = "*iexplore";
    public static final String BROWSER_FF = "*firefox";
    public static final String BROWSER_CH = "*chrome";
    
	private static Context context;
    private static String siteUrl;
    private static WebDriver browser;
    private Context() {
    }
    
    public static void initInstance(String browserType, String siteURL) {
        context = new Context();
        siteUrl = siteURL;
        
        switch (browserType) {
        	case BROWSER_IE : browser = new InternetExplorerDriver(); break;
        	case BROWSER_FF : browser = new FirefoxDriver(); break;
        	case BROWSER_CH : browser = new ChromeDriver(); break;
        }
        
        browser.get(siteUrl);
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    public static Context getInstance() {
        if (context == null) {
            throw new IllegalStateException("Context is not initialized");
        }
        return context;
    }
    
    public WebDriver getBrowser() {
        if (browser != null) {
            return browser;
        }
        throw new IllegalStateException("WebBrowser is not initialized");
    }
    
    public String getSiteUrl() {
       return siteUrl;
    }
}
