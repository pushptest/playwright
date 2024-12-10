package com.acc.common;

import com.acc.constants.IntegrationConstants;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.acc.constants.IntegrationConstants.*;
import java.util.logging.Logger;
public class LoginHelper {

    private static final Logger logger = Logger.getLogger(LoginHelper.class.getName());

    public static void login(Page page, String username, String password) {

        page.navigate(IntegrationConstants.LOGIN_URL);

        FrameLocator iframeLocator = page.frameLocator("#iFrameAsite");

        logger.info("Filling in username...");
        iframeLocator.locator(IntegrationConstants.USERNAME).fill(username);

        logger.info("Filling in password...");
        iframeLocator.locator(IntegrationConstants.PASSWORD).fill(password);

        logger.info("Clicking login button...");
        iframeLocator.locator(IntegrationConstants.LOGIN_BUTTON_).click();

        // After interacting with the iframe, you're automatically back to the main page context
        logger.info("Login process completed.");

    }


}


