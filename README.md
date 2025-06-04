# Appium Calculator Test Automation Framework

A robust test automation framework for testing the Android Calculator app using Appium, Java, TestNG, and Allure reporting.

## Framework Architecture

The framework follows the Page Object Model (POM) design pattern with the following structure:

```
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── calculator/
│                   ├── pages/
│                   │   ├── BasePage.java         # Base page with common functionality
│                   │   ├── CalculatorPage.java   # Main calculator page
│                   │   ├── KeypadPage.java       # Numeric keypad component
│                   │   └── OperationsPage.java   # Calculator operations component
│                   ├── tests/
│                   │   └── CalculatorTests.java  # Test cases
│                   └── utils/
│                       └── AppiumConfig.java     # Appium configuration
├── testng.xml           # TestNG configuration
├── pom.xml             # Maven dependencies
├── Jenkinsfile         # CI/CD pipeline
└── README.md
```

## Key Features

- **Page Object Model**: Well-structured page objects for better maintainability
- **Component-Based Design**: Separated keypad and operations components
- **Allure Reporting**: Detailed test reports with steps and screenshots
- **Logging**: Comprehensive logging using Log4j2
- **CI/CD Integration**: Jenkins pipeline for automated testing
- **Cross-Platform**: Supports both emulator and physical devices

## Prerequisites

- Java JDK 17
- Maven
- Node.js and npm
- Appium Server
- Android SDK
- Android Emulator
- Jenkins (for CI/CD)

## Framework Components

### BasePage
- Common functionality for all page objects
- WebElement interaction methods
- Logging setup
- PageFactory initialization

### KeypadPage
- Numeric input handling
- Digit button elements
- Multi-digit number support

### OperationsPage
- Calculator operations (add, subtract, multiply, divide)
- Operation button elements
- Clear and equals functionality

### CalculatorPage
- High-level calculator operations
- Result field handling
- Integration of KeypadPage and OperationsPage

## Test Cases

The framework includes test cases for basic calculator operations:
- Addition (5 + 3 = 8)
- Subtraction (10 - 4 = 6)
- Multiplication (6 * 7 = 42)
- Division (20 / 4 = 5)

## Setup Instructions

1. Install dependencies:
   ```bash
   mvn clean install
   ```

2. Start Appium server:
   ```bash
   appium
   ```

3. Start Android emulator:
   ```bash
   emulator -avd Pixel_4_API_30
   ```

4. Run tests:
   ```bash
   mvn clean test
   ```

5. Generate Allure report:
   ```bash
   mvn allure:serve
   ```

## CI/CD Pipeline

The Jenkins pipeline performs the following steps:
1. Checkout code
2. Setup environment variables
3. Start Appium server
4. Start Android emulator
5. Build and run tests
6. Generate Allure reports
7. Clean up resources

## Reports

Test reports are generated using Allure and can be viewed:
- In Jenkins after each build
- Locally using `mvn allure:serve`
- In the `target/allure-report` directory

## Best Practices

1. **Page Object Model**
   - Each page/component has its own class
   - Elements are defined using annotations
   - Methods represent user actions

2. **Logging**
   - Comprehensive logging for debugging
   - Log4j2 configuration
   - Step-by-step operation logging

3. **Test Organization**
   - Clear test method names
   - Descriptive test annotations
   - Proper setup and teardown

4. **Error Handling**
   - Proper exception handling
   - Meaningful error messages
   - Screenshot capture on failure