package com.acc.common;

import com.acc.constants.IntegrationConstants;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.acc.constants.IntegrationConstants.*;

public class LoginHelper {

    public static void login(Page page, String username, String password) {

        page.navigate(IntegrationConstants.LOGIN_URL);

        FrameLocator iframeLocator = page.frameLocator("#iFrameAsite");

        System.out.println("Filling in username...");
        iframeLocator.locator(IntegrationConstants.USERNAME_SELECTOR).fill(username);

        System.out.println("Filling in password...");
        iframeLocator.locator(IntegrationConstants.PASSWORD_SELECTOR).fill(password);

        System.out.println("Clicking login button...");
        iframeLocator.locator(IntegrationConstants.LOGIN_BUTTON_SELECTOR).click();

        // After interacting with the iframe, you're automatically back to the main page context
        System.out.println("Login process completed.");

        // Optional: Continue interacting with elements on the main page if needed
        // Locator someElement = page.locator("some-selector-on-main-page");
        // someElement.click();
    }


}


