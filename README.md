# Cross-Device Automation Agent Demo (BrowserStack Automate)

[TestNG](http://testng.org) Integration with BrowserStack.

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)

## What Does This Repo Do?

This repository showcases the use of **BrowserStack's Cross-Device Automation Agent** for web automation using TestNG and Selenium. The key feature is the ability to write test steps in **Natural Language (Plain English)**, moving away from explicit Selenium locators and actions.

It contains a demo script:
* **`BstackAIAuthoring.java`**: Runs complex test objectives (like selecting a product in a specific price range or navigating to a quote page) using simple English commands.

---

## How It Works

1.  **Enablement:** The feature is enabled by declaring the capability `aiAuthoring: true` in your `browserstack.yml` configuration file.
2.  **The Command:** Inside the test script (`BstackAIAuthoring.java`), the test uses a standard Selenium `JavascriptExecutor` to pass the Natural Language instruction to the BrowserStack environment:
    ```java
    // Example Objective: Find Google Pixel 3 phone on the website and add 5 of them to the cart
    jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\" Click on the Google button and Add 5 Pixel 3 phones to cart\"]}");
    ```
3.  **Execution:** The BrowserStack Cross-Device Automation Agent intercepts this command, interprets the natural language, and executes the equivalent low-level Selenium actions on the target web page.

---

## Running the Tests

### Using Maven

#### Run sample build

-   Clone the repository
-   Replace `YOUR_USERNAME` and `YOUR_ACCESS_KEY` with your BrowserStack access credentials in `browserstack.yml`.
-   Declare capability **`aiAuthoring: true`** in `browserstack.yml` file (if not already present).
-   Install dependencies `mvn compile`
-   To run the test suite having cross-platform with parallelization, run **`mvn test -P sample-test`**.
-   To run local tests, run **`mvn test -P sample-local-test`**.

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github).

#### Integrate your test suite

* Add maven dependency of `browserstack-java-sdk` in your `pom.xml` file:
    ```xml
    <dependency>
        <groupId>com.browserstack</groupId>
        <artifactId>browserstack-java-sdk</artifactId>
        <version>LATEST</version>
        <scope>compile</scope>
    </dependency>
    ```
* Modify your build plugin to run tests by adding `argLine -javaagent:${com.browserstack:browserstack-java-sdk:jar}` in the Surefire plugin configuration.
* Install dependencies `mvn compile`.

### Using Gradle

#### Prerequisites

-   If using Gradle, Java v9+ is required.

#### Run sample build

-   Clone the repository
-   Install dependencies `gradle build`
-   To run the test suite having cross-platform with parallelization, run **`gradle sampleTest`**.
-   To run local tests, run **`gradle sampleLocalTest`**.

#### Integrate your test suite

* Add `compileOnly 'com.browserstack:browserstack-java-sdk:latest.release'` in dependencies in your `gradle.build`.
* Fetch Artifact Information and add `jvmArgs` property in tasks *SampleTest* and *SampleLocalTest*.
* Install dependencies `gradle build`.

---

## Notes

* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate).
* For detailed documentation on general Selenium and Java setup with BrowserStack Automate, please refer to the [official documentation](https://www.browserstack.com/docs/automate/selenium?fw-lang=java).

## Getting Help

If you are running into any issues or have any queries, please check [Browserstack Support page](https://www.browserstack.com/support/automate) or [get in touch with us](https://www.browserstack.com/contact?ref=help).
