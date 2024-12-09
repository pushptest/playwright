package com.acc.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;

import java.nio.file.Paths;

import static com.acc.constants.IntegrationConstants.*;

public class FileUploadHelper {
    public static void uploadFileUsingDragAndDrop(Page page) {


        page.waitForTimeout(2000);

        Locator filesTabLocator = page.locator(FILES_SELECTOR);

        // Wait for the "Files" tab to be visible before clicking
        filesTabLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Click on the "Files" tab
        filesTabLocator.click();
        System.out.println("Navigated to the 'Files' tab.");

        Locator activeTabLocator = page.locator(FILES_TAB_ACTIVE);
        activeTabLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)); // Wait for it to be visible

        Assert.assertTrue(activeTabLocator.isVisible(), "The 'Files' tab is not active as expected.");
        System.out.println("The 'Files' tab is active.");


        page.waitForTimeout(2000);
        Locator folderTabLocator = page.locator(FOLDER_SELECTOR);
        folderTabLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)); // Wait for it to be visible

        folderTabLocator.click();

        System.out.println("Selected the folder");

        page.waitForTimeout(2000);

        Locator uploadDropdown = page.locator(UPLOAD_FILE_DROPDOWN_LOCATOR);
        uploadDropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        uploadDropdown.click();

        Locator chooseFileOption = page.locator(UPLOAD_FILE_OPTION);
        chooseFileOption.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        chooseFileOption.click();

        System.out.println("choose upload file option");

        Locator selectFileButton = page.locator(FILE_INPUT_LOCATOR);
        selectFileButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        selectFileButton.setInputFiles(Paths.get(UPLOAD_FILE_PATH));
        page.waitForLoadState();

        Locator uploadButton= page.locator(UPLOAD_BUTTON);
        uploadButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        uploadButton.click();
        page.waitForLoadState();

        Locator uploadSuccess = page.locator(UPLOAD_SUCCESS_MESSAGE_LOCATOR);
        uploadSuccess.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        System.out.println("File upload successful.");







    }

}
