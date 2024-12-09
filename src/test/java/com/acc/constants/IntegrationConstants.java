package com.acc.constants;

public class IntegrationConstants {

    public static final String BASE_URL = "https://systemqa.asite.com";
    public static final String LOGIN_URL = BASE_URL + "/login";

    // Locators
    public static final String USERNAME_SELECTOR = ("input[name='_58_login']");

    public static final String PASSWORD_SELECTOR = "input[name='_58_password']";
    public static final String LOGIN_BUTTON_SELECTOR = "input[id='login-cloud']";
    public static final String LOGIN_DASHBOARD_SELECTOR = "//span[.='Dashboard']";
    public static final String FILES_SELECTOR = "//span[.='Files']";
    public static final String FILES_TAB_ACTIVE = "li[id='li-navfiles'][class='active']";
    public static final String FOLDER_SELECTOR= "[title='INF']";
    public static final String UPLOAD_FILE_DROPDOWN_LOCATOR= "div[class*='pull-right settings'] button:not([disabled]) i[class*='upload']";
    public static final String UPLOAD_FILE_OPTION = "//div[@class='dropdown-menu show']/a[contains(.,'Files')]";
    public static final String FILE_INPUT_LOCATOR= "//div[@class='upload-overlay bootstrap-latest open']//input[@name='files']";
    public static final String UPLOAD_BUTTON= "//div[@class='upload-overlay bootstrap-latest open']//button[.='Upload']";
    public static final String UPLOAD_SUCCESS_MESSAGE_LOCATOR= "div[class*='simple-notification-wrapper'] div[class*='success']";



    public static final String UPLOAD_FILE_PATH= "D:/DNT/PlayWright New/Integration Automation/integrationsAutomation/src/main/resources/UploadFile3.txt";


    public static final String BASE_URL_API = "https://dmsqaak.asite.com/api/workspace/{workspaceId}/folder/{folderId}/firstpage_doclist";
}
