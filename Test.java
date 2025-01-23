import com.microsoft.playwright.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class PlaywrightWithSeleniumGridDynamic {
    public static void main(String[] args) {
        String seleniumGridUrl = "http://localhost:4444/wd/hub";

        // Fetch the CDP URL dynamically
        String browserCdpUrl = fetchCdpUrl(seleniumGridUrl);

        if (browserCdpUrl == null) {
            System.err.println("Failed to fetch CDP URL from Selenium Grid.");
            return;
        }

        // Connect to Selenium Grid via CDP
        try (Playwright playwright = Playwright.create()) {
            // Connect to the browser session
            Browser browser = playwright.chromium().connectOverCDP(browserCdpUrl);

            // Create a new browser context
            BrowserContext context = browser.newContext();

            // Open a new page
            Page page = context.newPage();

            // Navigate to a URL
            page.navigate("https://example.com");

            // Print the title of the page
            System.out.println("Page Title: " + page.title());

            // Close the browser
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String fetchCdpUrl(String gridUrl) {
        try {
            // Set capabilities for Chromium browser
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "chrome");

            // Start WebDriver session on Selenium Grid
            RemoteWebDriver driver = new RemoteWebDriver(new URL(gridUrl), capabilities);

            // Fetch CDP URL from session details
            String cdpUrl = driver.getCapabilities().getCapability("se:cdp").toString();

            // Quit the WebDriver session
            driver.quit();

            return cdpUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
