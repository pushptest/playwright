package com.acc.constants;

public class IntegrationConstants {

    public static final String BASE_URL = "https://systemsb.asite.com";
    public static final String LOGIN_URL = BASE_URL + "/login";

    // Locators
    public static final String USERNAME = ("input[name='_58_login']");

    public static final String PASSWORD= "input[name='_58_password']";
    public static final String LOGIN_BUTTON_= "input[id='login-cloud']";
    public static final String LOGIN_DASHBOARD_ = "div[id='wrap'] div[id='menuNav'] a[id='navdashboard']";
    public static final String SYSTEM_DASHBOARD_LOCATOR= "//h2[@id='dashboar-title']";
    public static final String FILES_TAB = "//a[@id='navfiles']";
    public static final String FILES_TAB_ACTIVE = "li[id='li-navfiles'][class='active']";
    public static final String FOLDER_= "[title='INF']";
    public static final String UPLOAD_FILE_DROPDOWN_= "div[class*='pull-right settings'] button:not([disabled]) i[class*='upload']";
    public static final String UPLOAD_FILE_OPTION = "//div[@class='dropdown-menu show']/a[contains(.,'Files')]";
    public static final String FILE_INPUT_= "//div[@class='upload-overlay bootstrap-latest open']//input[@name='files']";
    public static final String UPLOAD_BUTTON= "//div[@class='upload-overlay bootstrap-latest open']//button[.='Upload']";
    public static final String UPLOAD_SUCCESS_MESSAGE_= "div[class*='simple-notification-wrapper'] div[class*='success']";
    public static final String ORIGINAL_FILE_PATH = "D:/DNT/PlayWright New/psIntegrationAutomation/psIntegrationAutomation/playwright/src/main/resources/UploadFile.txt";
    public static final String UPLOAD_DIRECTORY = "D:/DNT/PlayWright New/psIntegrationAutomation/psIntegrationAutomation/playwright/src/main/resources";
    public static final String ACC_URL= "https://developer.api.autodesk.com/authentication/v2/authorize?response_type=code&client_id=bWThP2AEraQQR5UxyikTNw8cYgq1qTJT&redirect_uri=https://integrationqa.asite.com/partner/code&scope=data:read";
    public static final String ACC_USERNAME= "//input[@id='userName']";
    public static final String ACC_VERIFY_BUTTON= "//button[@id='verify_user_btn']";
    public static final String ACC_PASSWORD= "//input[@id='password']";
    public static final String ACC_SUBMIT_BUTTON= "//button[@id='btnSubmit']";
    public static final String ACC_ALLOW_BUTTON= "//a[@id='allow_btn']";
    public static final String ACC_WAIT_FOR_ASSERTION_URL= "**/partner/code?*";






    public static final String BASE_URL_API = "https://dmsqaak.asite.com/api/workspace/{workspaceId}/folder/{folderId}/firstpage_doclist";
}
