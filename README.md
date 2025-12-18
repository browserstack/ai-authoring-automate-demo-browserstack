# Cross-Device Automation Agent Demo (BrowserStack App Automate)

[TestNG](http://testng.org) Integration with BrowserStack App Automate for Android and iOS.

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)

## What Does This Repo Do?

This repository showcases the use of **BrowserStack's Cross-Device Automation Agent** for mobile app automation using TestNG and Appium. The key feature is the ability to write test steps in **Natural Language (Plain English)**, moving away from explicit Appium locators and actions.

It contains demo scripts:
* **Android - `AppiumTest.java`**: Runs test objectives on the Wikipedia Android app using simple English commands like searching for articles and validating results.
* **iOS - `AppiumTest.java`**: Runs test objectives on the BBC News iOS app using simple English commands like navigating to Culture tab and validating page headings.

---

## How It Works

1.  **Enablement:** The feature is enabled by declaring the capability `aiAuthoring: true` in your `browserstack.yml` configuration file.
2.  **The Command:** Inside the test scripts, the test uses a standard Selenium `JavascriptExecutor` to pass the Natural Language instruction to the BrowserStack environment:
    ```java
    // Example for Android: Search for "india" on Wikipedia app
    jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Tap on the \\\"Search\\\" option from below navigation bar and type \\\" india\\\" on the search bar and tap on the first suggestion\"]}");
    
    // Example for iOS: Navigate to Culture tab
    jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Tap on culture tab from the top navigation bar\"]}");
    ```
3.  **Execution:** The BrowserStack Cross-Device Automation Agent intercepts this command, interprets the natural language, and executes the equivalent low-level Appium actions on the mobile app.

---

## Setup

### Requirements

1. Java 8+
    - If Java is not installed, follow these instructions:
        - For Windows, download latest java version from [here](https://java.com/en/download/) and run the installer executable
        - For Mac and Linux, run `java -version` to see what java version is pre-installed. If you want a different version download from [here](https://java.com/en/download/)

2. Maven (Only required if using Maven as the build tool)
   - If Maven is not downloaded, download it from [here](https://maven.apache.org/download.cgi)
   - For installation, follow the instructions [here](https://maven.apache.org/install.html)

3. Gradle (Only required if using Gradle as the build tool)
  - If Gradle is not downloaded, download it from [here](https://gradle.org/releases/)  
  - For installation, follow the instructions [here](https://gradle.org/install/)

---

## Running the Tests

### For Android

#### Using Maven

-   Clone the repository
-   Navigate to the Android directory: 
    ```sh
    cd android
    ```
-   Replace `YOUR_USERNAME` and `YOUR_ACCESS_KEY` with your BrowserStack access credentials in `browserstack.yml`.
-   Declare capability **`aiAuthoring: true`** in `browserstack.yml` file (if not already present).
-   Install dependencies 
    ```java 
    mvn install
    ```
-   To run the test suite having cross-platform with parallelization, run 
    ```java
    mvn test -P sample-test
    ```
-   To run local tests, run 
    ```java 
    mvn test -P sample-local-test
    ```

#### Using Gradle

-   Clone the repository
-   Navigate to the Android directory: 
    ```sh 
    cd android
    ```
-   Replace `YOUR_USERNAME` and `YOUR_ACCESS_KEY` with your BrowserStack access credentials in `browserstack.yml`.
-   Declare capability **`aiAuthoring: true`** in `browserstack.yml` file (if not already present).
-   Install dependencies 
    ```java 
    gradle build
    ```
-   To run the test suite having cross-platform with parallelization, run 
    ```java
    gradle sampleTest
    ```
-   To run local tests, run 
    ```java 
    gradle sampleLocalTest
    ```

### For iOS

#### Using Maven

-   Clone the repository
-   Navigate to the iOS directory: 
    ```ios 
    cd ios
    ```
-   Replace `YOUR_USERNAME` and `YOUR_ACCESS_KEY` with your BrowserStack access credentials in `browserstack.yml`.
-   Declare capability **`aiAuthoring: true`** in `browserstack.yml` file (if not already present).
-   Install dependencies 
    ```java 
    mvn install
    ```
-   To run the test suite having cross-platform with parallelization, run `mvn test -P sample-test`.
-   To run local tests, run 
    ```java
    mvn test -P sample-local-test
    ```

#### Using Gradle

-   Clone the repository
-   Navigate to the iOS directory: 
    ```sh 
    cd ios
    ```
-   Replace `YOUR_USERNAME` and `YOUR_ACCESS_KEY` with your BrowserStack access credentials in `browserstack.yml`.
-   Declare capability **`aiAuthoring: true`** in `browserstack.yml` file (if not already present).
-   Install dependencies 
    ```java 
    gradle build
    ```
-   To run the test suite having cross-platform with parallelization, run 
    ```java
    gradle sampleTest
    ```
-   To run local tests, run 
    ```java 
    gradle sampleLocalTest
    ```

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github).

---

## Integrate Your Test Suite

### Maven Integration

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

### Gradle Integration

* Add `compileOnly 'com.browserstack:browserstack-java-sdk:latest.release'` in dependencies in your `build.gradle`.
* Fetch Artifact Information and add `jvmArgs` property in tasks.
* Install dependencies `gradle build`.

---

## Notes

* You can view your test results on the [BrowserStack App Automate dashboard](https://www.browserstack.com/app-automate).
* For detailed documentation on Appium and Java setup with BrowserStack App Automate, please refer to the [official documentation](https://www.browserstack.com/docs/appium).

## Getting Help

If you are running into any issues or have any queries, please check [Browserstack Support page](https://www.browserstack.com/support/app-automate) or [get in touch with us](https://www.browserstack.com/contact?ref=help).
