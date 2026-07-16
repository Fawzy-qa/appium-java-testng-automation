# Mobile Automation Framework

A robust, scalable, and data-driven mobile automation framework built for Android applications using **Java**, **Appium**, and **TestNG**. This framework implements the **Page Object Model (POM)** design pattern and features comprehensive reporting via **Allure**.


## 🎥 Project Demo

<video width="100%" controls>
  <source src="https://github.com/Fawzy-qa/appium-java-testng-automation/blob/main/assets/demo_run.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>
## 🛠️ Tech Stack

* **Language:** Java 17+
* **Automation Engine:** Appium (Java Client v10.1.1) / UiAutomator2
* **Testing Framework:** TestNG (v7.12.0)
* **Build Tool:** Maven
* **Reporting:** Allure (v2.35.2) + AspectJ Weaver (to be added)
* **Data Serialization:** GSON (v2.14.0)

## ✨ Key Features

* **Page Object Model (POM):** Clean separation between UI locators/actions and test logic using `AppiumFieldDecorator`.
* **Data-Driven Testing (DDT):** Test cases are dynamically fed by JSON files using custom TestNG `@DataProviders`.
* **Smart Scrolling Utilities:** Custom `AndroidActions` class featuring `scrollUntilVisible` and `scrollDownThenUp` (peek scroll) to intelligently navigate dynamic mobile UIs.
* **Automated Permissions:** Uses `autoGrantPermissions` capability to seamlessly handle OS-level pop-ups.
* **Automated Screenshots:** A custom `AllureListener` automatically captures and attaches screenshots to the Allure report upon any test failure.
* **UTF-8 Encoding:** Fully supports Arabic language assertions and localization testing.

## 📂 Project Structure

```text
src/
├── main/java/mobileproject/
│   ├── base/               # BaseTest (Driver setup/teardown) & BasePage
│   ├── pages/              # Page Object Models (LoginPage, RegisterPage, UserDashboard, etc.)
│   ├── utils/              # Helper utilities (AndroidActions, JsonReader)
│   └── listeners/          # TestNG Listeners (AllureListener for screenshots)
├── test/java/mobileproject/
│   ├── tests/              # TestNG Test Classes
│   └── dataproviders/      # Classes mapping JSON data to TestNG DataProviders
└── test/resources/
    └── testdata/           # JSON files containing test data (loginData.json, userData.json)
