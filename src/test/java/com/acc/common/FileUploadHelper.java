package com.acc.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import java.util.logging.Logger;

import java.io.File;
import java.nio.file.Paths;

import static com.acc.constants.IntegrationConstants.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileUploadHelper {
//    private static final String ORIGINAL_FILE_PATH = "D:/DNT/PlayWright New/psIntegrationAutomation/psIntegrationAutomation/playwright/src/main/resources/UploadFile.txt";
//    private static final String UPLOAD_DIRECTORY = "D:/DNT/PlayWright New/psIntegrationAutomation/psIntegrationAutomation/playwright/src/main/resources";
    private static final Logger logger = Logger.getLogger(FileUploadHelper.class.getName());
    public static String UPLOAD_FILE_PATH;

    // Method to generate a new file name and copy the original file
    private static void prepareFileForUpload() throws IOException {
        // Get current date and time in the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        // Generate the new file name
        String newFileName = "UploadFile-" + formattedDateTime + ".txt";

        // Define the new file path
        Path newFilePath = Paths.get(UPLOAD_DIRECTORY , newFileName);

        // Copy the original file to the new file path
        Files.copy(Paths.get(ORIGINAL_FILE_PATH), newFilePath);

        // Update the UPLOAD_FILE_PATH to the new file
        UPLOAD_FILE_PATH = newFilePath.toString();

        logger.info("Prepared file for upload: " + UPLOAD_FILE_PATH);
    }

    public static void uploadFile(Page page) throws IOException {
        // Prepare the file (rename and update the path)
        prepareFileForUpload();

        page.waitForTimeout(2000);

        Locator filesTabLocator = page.locator(FILES_TAB);

        // Wait for the "Files" tab to be visible before clicking
        filesTabLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Click on the "Files" tab
        filesTabLocator.click();
        logger.info("Navigated to the 'Files' tab.");

        Locator activeTabLocator = page.locator(FILES_TAB_ACTIVE);
        activeTabLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)); // Wait for it to be visible

        Assert.assertTrue(activeTabLocator.isVisible(), "The 'Files' tab is not active as expected.");
        logger.info("The 'Files' tab is active.");

        page.waitForTimeout(2000);
        Locator folderTabLocator = page.locator(FOLDER_);
        folderTabLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)); // Wait for it to be visible

        folderTabLocator.click();

        logger.info("Selected the folder");

        page.waitForTimeout(2000);

        Locator uploadDropdown = page.locator(UPLOAD_FILE_DROPDOWN_);
        uploadDropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        uploadDropdown.click();

        Locator chooseFileOption = page.locator(UPLOAD_FILE_OPTION);
        chooseFileOption.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        chooseFileOption.click();

        logger.info("choose upload file option");

        Locator selectFileButton = page.locator(FILE_INPUT_);
        selectFileButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Use the dynamically updated file path
        selectFileButton.setInputFiles(Paths.get(UPLOAD_FILE_PATH));
        page.waitForLoadState();

        Locator uploadButton = page.locator(UPLOAD_BUTTON);
        uploadButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        uploadButton.click();
        page.waitForLoadState();

        Locator uploadSuccess = page.locator(UPLOAD_SUCCESS_MESSAGE_);
        uploadSuccess.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        logger.info("File upload successful.");
    }

    public static String getLatestFileName() {
        return new File(UPLOAD_FILE_PATH).getName();
    }
}


