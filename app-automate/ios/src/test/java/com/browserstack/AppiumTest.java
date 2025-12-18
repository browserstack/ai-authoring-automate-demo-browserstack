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
        
        // AI Action 1: Select culture tab
        System.out.println("Action 1: Select culture tab");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Tap on culture tab from the top navigation bar\"]}");
        Thread.sleep(3000); // Wait for culture tab to load
        
        // AI Action 2: Extract the page heading
        System.out.println("Action 2: Extract the page heading...");
        Object headingResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Extract the main heading text displayed at the top of the page below the BBC logo\"]}");
        String extractedHeading = headingResult != null ? headingResult.toString() : "";
        System.out.println("Extracted Heading: " + extractedHeading);
        Thread.sleep(3000);
        
        // AI Validation: Check if the page heading is Culture
        System.out.println("Validation: Checking if the page heading is Culture");
        Object validationResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Check if the main heading text at the top of the page is Culture\"]}");
        
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
            "Page heading should be Culture but validation failed");
        
        System.out.println("Test completed successfully!");
    }


    @AfterMethod(alwaysRun=true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
