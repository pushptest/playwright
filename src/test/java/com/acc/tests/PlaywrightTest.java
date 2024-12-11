package com.acc.tests;

import com.acc.common.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.acc.constants.IntegrationConstants.*;
import static org.testng.Assert.assertEquals;
import java.util.logging.Logger;

public class PlaywrightTest {

    private static final Logger logger = Logger.getLogger(PlaywrightTest.class.getName());
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
        page = browser.newPage();
    }

    @Test(priority = 1) // Execute this test first
    public void testLogin() throws IOException {
        /// UI Automation - Login
        LoginHelper.login(page, "accsb@namechange.com", "Asite@1234");

        // Use XPath to check for the "Dashboard" element after login
        Locator dashboardLocator = page.locator(LOGIN_DASHBOARD_ );

        // Wait for the "Dashboard" element to appear, indicating successful login
        dashboardLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Locator systemdashboardLocator = page.locator(SYSTEM_DASHBOARD_LOCATOR);
        systemdashboardLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Assert that the element is visible (i.e., login is successful)
        Assert.assertTrue(dashboardLocator.isVisible(), "Dashboard is not visible, login might have failed");

        // File upload if required
        FileUploadHelper.uploadFile(page);
    }

    @Test(priority = 2, dependsOnMethods = {"testLogin"}) // Execute this test after testLogin
    public void getAccFilesListing() throws Exception {
        String bearerToken = GetOauthToken.getAccessToken("acc.integration@asite.com", "Asite@1234");
        logger.info("Extracted OauthCode: " + bearerToken);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://developer.api.autodesk.com/data/v1/projects/b.a079daf9-4b63-4e0a-a8d8-d1486cf77ae9/folders/urn:adsk.wipemea:fs.folder:co.-9_1jC2XS3GpSS4ev5gBqQ/contents"))
                .header("Authorization", "Bearer " + bearerToken)
                .header("Cookie", "inst-id=9L5jXhKwk5xgkuNW1m5bIyUuw6g.QcH3lv-y3rql0zQtFAlvkfYbgdwBb027we0Oofi-ZbU")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedValue = FileUploadHelper.getLatestFileName();
        logger.info("Expected File Name: " + expectedValue);

        boolean found = false;
        int maxRetries = 30; // Retry for 5 minutes (10 seconds per retry)
        int retryInterval = 10000; // 10 seconds in milliseconds

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode dataNode = rootNode.get("data");

            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode node : dataNode) {
                    if (node.has("attributes") && node.get("attributes").has("displayName")) {
                        String displayName = node.get("attributes").get("displayName").asText();
                        if (displayName.equals(expectedValue)) {
                            found = true;
                            logger.info("Expected value found in API response on attempt " + attempt);
                            break;
                        }
                    }
                }
            }

            if (found) break;

            logger.info("Attempt " + attempt + ": Expected value not found, retrying after 10 seconds...");
            Thread.sleep(retryInterval);
        }

        assertEquals(true, found, "Expected displayName value not found in the response within 5 minutes");
    }



    @AfterMethod
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
