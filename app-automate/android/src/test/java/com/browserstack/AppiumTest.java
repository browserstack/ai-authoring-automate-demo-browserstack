package com.browserstack;

import java.net.URL;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;


public class AppiumTest {

    public AndroidDriver driver;
    private static final String BROWSERSTACK_HUB = "https://hub-cloud.browserstack.com/wd/hub";

    @BeforeMethod(alwaysRun=true)
    public void setUp() throws Exception {
        UiAutomator2Options capabilities = new UiAutomator2Options();
        driver = new AndroidDriver(new URL(BROWSERSTACK_HUB), capabilities);
    }

    @Test
    public void sampleTest() throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        
        // AI Action 1: Search for "india" on Wikipedia
        System.out.println("Action 1: Tap on the \"Search\" option from below navigation bar and type \" india\" on the search bar and tap on the first suggestion");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Tap on the \\\"Search\\\" option from below navigation bar and type \\\" india\\\" on the search bar and tap on the first suggestion\"]}");
        Thread.sleep(3000); // Wait for search results
        
        // AI Action 2: Extract the title of the search result
        System.out.println("Action 2: Extract the title of the search result...");
        Object titleResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Extract the title of the search result\"]}");
        String extractedTitle = titleResult != null ? titleResult.toString() : "";
        System.out.println("Extracted Title: " + extractedTitle);
        
        // AI Validation: Check if the title contains "India"
        System.out.println("Validation: Checking if title of the search result contains India...");
        Object validationResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Validate if the title of the search result contains India\"]}");
        
        // Convert AI validation result to boolean and assert
        boolean isValidationPassed = false;
        
        if (validationResult instanceof Boolean) {
            isValidationPassed = (Boolean) validationResult;
        } else if (validationResult instanceof Map) {
            // Check if result is an object with a "value" attribute
            Map<?, ?> resultMap = (Map<?, ?>) validationResult;
            if (resultMap.containsKey("value")) {
                Object value = resultMap.get("value");
                if (value instanceof Boolean) {
                    isValidationPassed = (Boolean) value;
                } else if (value instanceof String) {
                    isValidationPassed = Boolean.parseBoolean((String) value);
                } else if (value != null) {
                    isValidationPassed = Boolean.parseBoolean(value.toString());
                }
            }
        } else if (validationResult instanceof String) {
            isValidationPassed = Boolean.parseBoolean((String) validationResult);
        } 
        
        System.out.println("AI Validation Result: " + isValidationPassed);
        
        // Standard TestNG assertion using the AI validation output
        Assert.assertTrue(isValidationPassed, 
            "Article title should contain 'India' but validation failed");
        
        System.out.println("Test completed successfully!");
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}
