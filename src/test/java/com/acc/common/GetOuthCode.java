package com.acc.common;

import com.microsoft.playwright.*;



// NOT NEEDED BUT KEPT FOR BACKUP

public class GetOuthCode {

    public static String getAuthCode(String username, String password) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            // Step 1: Open the URL
            page.navigate("https://developer.api.autodesk.com/authentication/v2/authorize?response_type=code&client_id=bWThP2AEraQQR5UxyikTNw8cYgq1qTJT&redirect_uri=https://integrationqa.asite.com/partner/code&scope=data:read");

            // Step 2: Input the username
            page.locator("//input[@id='userName']").fill(username);

            // Step 3: Click the verify button
            page.locator("//button[@id='verify_user_btn']").click();

            // Step 4: Input the password
            page.locator("//input[@id='password']").fill(password);

            // Step 5: Click the sign-in button
            page.locator("//button[@id='btnSubmit']").click();

            // Step 6: Click the Allow button
            page.locator("//a[@id='allow_btn']").click();

            // Step 7: Wait for the URL to include the code
            page.waitForURL("**/partner/code?*");

            // Step 8: Extract the code from the URL
            String url = page.url();
            String code = url.split("code=")[1];

            browser.close();
            return code;
        }
    }
}


