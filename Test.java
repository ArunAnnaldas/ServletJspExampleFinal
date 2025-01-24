java -jar selenium-server-4.15.0.jar standalone --selenium-manager true --session-timeout 999999 --session-request-timeout 999999 --max-sessions 5


  
Playwright playwright Playwright.create();
Browser browser = playwright.chromium().launch(new BrowserType. LaunchOptions().setHeadless (false)); Page page browser.newPage(); page.navigate("https://the-internet.herokuapp.com/javascript_alerts");
page.onDialog(dialog -> {
});
dialog.accept("Testing");
System.out.println(dialog.message());
page.locator("text=Click for JS Alert").click(); page.locator("text=Click for JS Confirm").click();
page.locator("text=Click for JS Prompt").click();
