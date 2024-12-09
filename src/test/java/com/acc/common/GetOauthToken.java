package com.acc.common;

import com.microsoft.playwright.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.acc.constants.IntegrationConstants.*;

public class GetOauthToken {

    public static String getAccessToken(String username, String password) throws Exception {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            // Step 1: Open the URL
            page.navigate(ACC_URL);

            // Step 2: Input the username
            page.locator(ACC_USERNAME).fill(username);

            // Step 3: Click the verify button
            page.locator(ACC_VERIFY_BUTTON).click();

            // Step 4: Input the password
            page.locator(ACC_PASSWORD).fill(password);

            // Step 5: Click the sign-in button
            page.locator(ACC_SUBMIT_BUTTON).click();

            // Step 6: Click the Allow button
            page.locator(ACC_ALLOW_BUTTON).click();

            // Step 7: Wait for the URL to include the code
            page.waitForURL(ACC_WAIT_FOR_ASSERTION_URL);

            // Step 8: Extract the code from the URL
            String url = page.url();
            String code = url.split("code=")[1];

            browser.close();

            // Step 9: Use the code to get the access token
            return fetchAccessToken(code);
        }
    }

    private static String fetchAccessToken(String code) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        Map<Object, Object> data = new HashMap<>();
        data.put("grant_type", "authorization_code");
        data.put("code", code);
        data.put("redirect_uri", "https://integrationqa.asite.com/partner/code");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://developer.api.autodesk.com/authentication/v2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic YldUaFAyQUVyYVFRUjVVeHlpa1ROdzhjWWdxMXFUSlQ6T0V3c3hEV0FmSVpZYU1wRw==")
                .header("Cookie", "inst-id=9L5jXhKwk5xgkuNW1m5bIyUuw6g.QcH3lv-y3rql0zQtFAlvkfYbgdwBb027we0Oofi-ZbU")
                .POST(BodyPublishers.ofString(data.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"))))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String responseBody = response.body();

        // Extract the access token from the response
        return extractAccessToken(responseBody);
    }

    private static String extractAccessToken(String responseBody) {
        // Assuming the response is in JSON format
        int startIndex = responseBody.indexOf("\"access_token\":\"") + 16;
        int endIndex = responseBody.indexOf("\"", startIndex);
        return responseBody.substring(startIndex, endIndex);
    }

}