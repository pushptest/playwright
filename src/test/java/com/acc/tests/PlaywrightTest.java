//package com.acc.tests;
//
//import com.acc.common.*;
//import com.acc.common.LoginHelper;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.microsoft.playwright.*;
//import com.microsoft.playwright.options.*;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//import static com.acc.constants.IntegrationConstants.*;
//import static io.restassured.RestAssured.*;
//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.Matchers.*;
//import static org.testng.Assert.assertEquals;
//
//public class PlaywrightTest  {
//
//    private Playwright playwright;
//    protected BrowserContext context;
//    private Browser browser;
//    private Page page;
//    private static final String workspaceId = "2171810$$Yl7hfM";
//    private static final String folderId = "135710506$$UNbTHa";
//    private static final String ASessionID = "F9Q5vkZ1Q3Qt1BiaqKpxnU8sjaYIeXM8fOQpTIZeRiY=";
//    private static final String expectedFileName= "UploadFile5.txt";
//
//    @BeforeClass
//    public void setUp() {
//        playwright = Playwright.create();
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
//        context = browser.newContext();
//        page = context.newPage();
//    }
//
//    @Test
//    public void testLogin() {
//        // UI Automation - Login
//        LoginHelper.login(page, "accsb@namechange.com", "Asite@1234");
//
//        // Use XPath to check for the "Dashboard" element after login
//        Locator dashboardLocator = page.locator(LOGIN_DASHBOARD_SELECTOR);
//
//        // Wait for the "Dashboard" element to appear, indicating successful login
//        dashboardLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
//
//        // Assert that the element is visible (i.e., login is successful)
//        Assert.assertTrue(dashboardLocator.isVisible(), "Dashboard is not visible, login might have failed");
//
//        // File upload if required
//        FileUploadHelper.uploadFileUsingDragAndDrop(page);
//    }
//
//    //@Test(dependsOnMethods = {"testLogin"})
//    public void testGetApi() {
//        // Add a 2-second delay after UI automation before moving to API test
//        try {
//            Thread.sleep(2000); // 2000 milliseconds = 2 seconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // API Automation - Get request
//        String url = BASE_URL_API.replace("{workspaceId}", workspaceId)
//                .replace("{folderId}", folderId);
//
//        Response response = given().
//                baseUri(url).
//                header("ASessionID", ASessionID).
//                when().
//                get().
//                then().extract().response();
//        // Extract FileName from the response
//        String fileName = response.xmlPath().getString("asiteDataList.documentVO[0].FileName");
//        // Print the extracted FileName
//        System.out.println("Extracted FileName: " + fileName);
//        assertThat("The file name matches",fileName,equalTo(expectedFileName));
//    }
//    @Test
//    public void getAccFilesListing() throws Exception {
//
//        String bearerToken= GetOauthToken.getAccessToken("acc.integration@asite.com","Asite@1234");
//        System.out.println("Extracted OuthCode: " + bearerToken);
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://developer.api.autodesk.com/data/v1/projects/b.a079daf9-4b63-4e0a-a8d8-d1486cf77ae9/folders/urn:adsk.wipemea:fs.folder:co.-9_1jC2XS3GpSS4ev5gBqQ/contents"))
//                .header("Authorization", "Bearer " + bearerToken)
//                .header("Cookie", "inst-id=9L5jXhKwk5xgkuNW1m5bIyUuw6g.QcH3lv-y3rql0zQtFAlvkfYbgdwBb027we0Oofi-ZbU")
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println("API Response: " + response.body());
//
//        // Parse the JSON response
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode rootNode = objectMapper.readTree(response.body());
//
//        // Search for the key "displayName" and assert its value
//        String expectedValue = "1111111111111111111111111.txt"; // Replace with your expected value
//        boolean found = false;
//
//        for (JsonNode node : rootNode.get("data")) {
//            if (node.has("attributes") && node.get("attributes").has("displayName")) {
//                String displayName = node.get("attributes").get("displayName").asText();
//                if (displayName.equals(expectedValue)) {
//                    found = true;
//                    break;
//                }
//            }
//        }
//
//        assertEquals(true, found, "Expected displayName value not found in the response");
//    }
//
//
//
//
//    @AfterClass
//    public void tearDown() {
//        if (page != null) {
//            page.close();
//        }
//        if (browser != null) {
//            browser.close();
//        }
//        if (playwright != null) {
//            playwright.close();
//        }
//    }
//}





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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.acc.constants.IntegrationConstants.*;
import static org.testng.Assert.assertEquals;

public class PlaywrightTest {

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
    public void testLogin() {
        // UI Automation - Login
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
        FileUploadHelper.uploadFileUsingDragAndDrop(page);
    }

    @Test(priority = 2, dependsOnMethods = {"testLogin"}) // Execute this test after testLogin
    public void getAccFilesListing() throws Exception {
        // Wait for 5 seconds before starting the next test
        Thread.sleep(5000);

        String bearerToken = GetOauthToken.getAccessToken("acc.integration@asite.com", "Asite@1234");
        System.out.println("Extracted OauthCode: " + bearerToken);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://developer.api.autodesk.com/data/v1/projects/b.a079daf9-4b63-4e0a-a8d8-d1486cf77ae9/folders/urn:adsk.wipemea:fs.folder:co.-9_1jC2XS3GpSS4ev5gBqQ/contents"))
                .header("Authorization", "Bearer " + bearerToken)
                .header("Cookie", "inst-id=9L5jXhKwk5xgkuNW1m5bIyUuw6g.QcH3lv-y3rql0zQtFAlvkfYbgdwBb027we0Oofi-ZbU")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("API Response: " + response.body());

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        // Search for the key "displayName" and assert its value
        String expectedValue = "UploadFile5.txt"; // Replace with your expected value
        boolean found = false;

        for (JsonNode node : rootNode.get("data")) {
            if (node.has("attributes") && node.get("attributes").has("displayName")) {
                String displayName = node.get("attributes").get("displayName").asText();
                if (displayName.equals(expectedValue)) {
                    found = true;
                    break;
                }
            }
        }

        assertEquals(true, found, "Expected displayName value not found in the response");
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
