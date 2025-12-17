package com.browserstack;

import java.net.URL;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;


public class AppiumTest {

    public IOSDriver driver;

    @BeforeMethod(alwaysRun=true)
    public void setUp() throws Exception {
        MutableCapabilities options = new XCUITestOptions();
        driver = new IOSDriver(new URL("https://hub-cloud.browserstack.com/wd/hub"),options);
        System.out.println(driver);
    }


    @Test
    public void sampleTest() throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        
        // AI Action 1: Select culture tab (no scrolling needed)
        System.out.println("Action 1: Select culture tab");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Tap on culture tab from the top navigation bar\"]}");
        Thread.sleep(3000); // Wait for culture tab to load
        
        // AI Action 2: Extract the title of the third article
        System.out.println("Action 2: Fetch the title of the third article from the start...");
        Object titleResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Context: An article on this page consists of an image, a title, an optional description (usually only available for the first article), followed by a timestamp and a category label. Task: Extract the title text of the third article from the top of the page\"]}");
        String extractedTitle = titleResult != null ? titleResult.toString() : "";
        System.out.println("Extracted Title: " + extractedTitle);
        Thread.sleep(3000);
        
        // AI Action 3: Extract the label below the third article
        System.out.println("Action 3: Extracting the label below the third article...");
        Object labelResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Context: An article on this page consists of an image, a title, an optional description (usually only available for the first article), followed by a timestamp and a category label. Task: If the third article along with timestamp and category label is not completely visible on screen, scroll a few pixels at a time until it is fully in view. Then extract the category label text that appears after the timestamp of the third article\"]}");
        String extractedLabel = labelResult != null ? labelResult.toString() : "";
        System.out.println("Extracted Label: " + extractedLabel);
        Thread.sleep(3000);
        
        // AI Validation: Check if the third article has culture label below it
        System.out.println("Validation: Checking if the third article has culture category label");
        Object validationResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Context: An article on this page consists of an image, a title, an optional description (usually only available for the first article), followed by a timestamp and a category label. Task: Locate the third article from the top. Check if this article has the text 'Culture' as its category label after the timestamp. Return true if the Culture label exists, return false if it does not exist or shows a different category\"]}");
        
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
            "Third article should have culture category label but validation failed");
        
        System.out.println("Test completed successfully!");
    }


    @AfterMethod(alwaysRun=true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
