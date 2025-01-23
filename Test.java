import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class FetchCdpUrl {
    public static String getDevtoolsUrl() {
        try {
            // Set capabilities for Chromium browser
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "chrome");

            // Start WebDriver session on Selenium Grid
            RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

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
