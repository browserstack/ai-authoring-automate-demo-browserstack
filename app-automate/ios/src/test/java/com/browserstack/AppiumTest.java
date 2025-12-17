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
        
        // AI Action 1: Fill signup form with email and password
        System.out.println("Action 1: Filling signup form...");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Click on create account button, enter abc@gmail.com in the email field, type 12345678 in the password field\"]}");
        Thread.sleep(3000); // Wait for form to be filled
        
        // AI Action 2: Set date of birth
        System.out.println("Action 2: Setting date of birth...");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Set the date of birth to 8 Jan 1994\"]}");
        Thread.sleep(3000); // Wait for date to be set
        
        // AI Validation: Verify date of birth
        System.out.println("Validation: Checking if date of birth is set to 8 Jan 1994...");
        Object validationResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Validate if the date of birth is 8 Jan 1994\"]}");
        
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
            "Date of birth should be set to 8 Jan 1994 but validation failed");
        
        System.out.println("Test completed successfully!");
    }


    @AfterMethod(alwaysRun=true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
