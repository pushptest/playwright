package com.acc.tests;

import com.acc.common.FileUploadHelper;
import com.acc.common.LoginHelper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.acc.constants.IntegrationConstants.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class PlaywrightTest  {

    private Playwright playwright;
    protected BrowserContext context;
    private Browser browser;
    private Page page;
    private static final String workspaceId = "2171810$$Yl7hfM";
    private static final String folderId = "135710506$$UNbTHa";
    private static final String ASessionID = "F9Q5vkZ1Q3Qt1BiaqKpxnU8sjaYIeXM8fOQpTIZeRiY=";
    private static final String expectedFileName= "UploadFile3.txt";

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
        context = browser.newContext();
        page = context.newPage();
    }

    @Test
    public void testLogin() {
        // UI Automation - Login
        LoginHelper.login(page, "accname@accname.com", "Asite@123");

        // Use XPath to check for the "Dashboard" element after login
        Locator dashboardLocator = page.locator(LOGIN_DASHBOARD_SELECTOR);

        // Wait for the "Dashboard" element to appear, indicating successful login
        dashboardLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Assert that the element is visible (i.e., login is successful)
        Assert.assertTrue(dashboardLocator.isVisible(), "Dashboard is not visible, login might have failed");

        // File upload if required
        FileUploadHelper.uploadFileUsingDragAndDrop(page);
    }

    @Test(dependsOnMethods = {"testLogin"})
    public void testGetApi() {
        // Add a 2-second delay after UI automation before moving to API test
        try {
            Thread.sleep(2000); // 2000 milliseconds = 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // API Automation - Get request
        String url = BASE_URL_API.replace("{workspaceId}", workspaceId)
                .replace("{folderId}", folderId);

        Response response = given().
                baseUri(url).
                header("ASessionID", ASessionID).
                when().
                get().
                then().extract().response();
        // Extract FileName from the response
        String fileName = response.xmlPath().getString("asiteDataList.documentVO[0].FileName");
        // Print the extracted FileName
        System.out.println("Extracted FileName: " + fileName);
        assertThat("The file name matches",fileName,equalTo(expectedFileName));
    }

    @AfterClass
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
