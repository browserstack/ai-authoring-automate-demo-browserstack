package com.browserstack;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
        // NL AI Objective 1: Navigate to tupperware and add any product to cart in the price range of 20 to 50$
          driver.get("https://www.tupperware.com/collections/fresh-picks");
          jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Select any product between the price range of 20 and 50$ and add to cart \"]}");

        // NL AI Objective 2: Get to the quote page from the homepage
          driver.get("https://www.travelinsured.com/");
          jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Click on the get quote button\"]}");

        // NL AI Objective 3: Find Google Pixel 3 phone on the website and add 5 of them to the cart
          driver.get("https://www.bstackdemo.com/");
          jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\" Click on the Google button and Add 5 Pixel 3 phones to cart\"]}");

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
