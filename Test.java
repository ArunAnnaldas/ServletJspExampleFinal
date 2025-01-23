import com.microsoft.playwright.*;

public class PlaywrightWithSeleniumGrid {
    public static void main(String[] args) {
        // Selenium Grid URL
        String seleniumGridUrl = "http://localhost:4444/wd/hub";

        // Browser session ID (fetch this dynamically or use the session started from Grid)
        String browserCdpUrl = "ws://localhost:4444/devtools/browser/<browser_id>"; // Replace <browser_id>

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
}
