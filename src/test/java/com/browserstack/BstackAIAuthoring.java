package com.browserstack;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;

public class BstackAIAuthoring {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Make sure you have chromedriver in your PATH or set the path here
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void openGoogle() {

        JavascriptExecutor jse = (JavascriptExecutor)driver;

        //Natural language authoring in Automate using Browserstack AI Agent
        // Navigate to BrowserStack Demo website
        driver.get("https://bstackdemo.com/");
        
        try {
            Thread.sleep(3000); // Wait for page to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // AI Action 1: Click on Apple vendor filter
        System.out.println("Action 1: Click on Apple vendor filter");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Click on the Apple button in the Vendors section\"]}");
        
        try {
            Thread.sleep(3000); // Wait for products to filter
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // AI Action 2: Validate that Add to cart buttons are visible
        System.out.println("Action 2: Validate that Add to cart buttons are visible");
        Object validationResult = jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Check if Add to cart button is present on the screen\"]}");
        
        // Parse validation result
        boolean isValidationPassed = false;
        String validationDetails = "";
        try {
            if (validationResult != null && validationResult instanceof Map) {
                Map<?, ?> resultMap = (Map<?, ?>) validationResult;
                if (resultMap.containsKey("value")) {
                    Object valueObj = resultMap.get("value");
                    if (valueObj instanceof Map) {
                        Map<?, ?> valueMap = (Map<?, ?>) valueObj;
                        if (valueMap.containsKey("execution_status")) {
                            String execStatus = valueMap.get("execution_status").toString();
                            if ("completed".equals(execStatus)) {
                                if (valueMap.containsKey("result")) {
                                    Object result = valueMap.get("result");
                                    if (result instanceof Map) {
                                        Map<?, ?> resultInnerMap = (Map<?, ?>) result;
                                        if (resultInnerMap.containsKey("assertion_status")) {
                                            Object assertionStatus = resultInnerMap.get("assertion_status");
                                            if (assertionStatus instanceof Boolean) {
                                                isValidationPassed = (Boolean) assertionStatus;
                                            }
                                        }
                                    }
                                }
                            }
                            validationDetails = "Execution: " + execStatus;
                        }
                    }
                }
            } else {
                validationDetails = "Unexpected result: " + validationResult;
            }
        } catch (Exception e) {
            validationDetails = "Error: " + e.getMessage();
        }
        
        System.out.println("Validation Result: " + (isValidationPassed ? "PASSED" : "FAILED") + " - " + validationDetails);
        
        System.out.println("\n=== DEMO TEST SUMMARY ===");
        System.out.println("✓ Action 1: Clicked on Apple vendor filter - COMPLETED");
        System.out.println((isValidationPassed ? "✓" : "⚠") + " Action 2: Validated Add to cart buttons - " + (isValidationPassed ? "PASSED" : "FAILED"));
        System.out.println("  Details: " + validationDetails);
        System.out.println("==========================");

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
