# Appium Calculator Test Automation

This project contains automated tests for the Android Calculator app using Appium, Java, and TestNG.

## Prerequisites

- Java JDK 17
- Maven
- Node.js and npm
- Appium Server
- Android SDK
- Android Emulator
- Jenkins

## Jenkins Setup Instructions

1. Install Jenkins:
   ```bash
   brew install jenkins
   ```

2. Start Jenkins:
   ```bash
   brew services start jenkins
   ```

3. Install Required Jenkins Plugins:
   - Git plugin
   - Pipeline plugin
   - Allure Jenkins Plugin
   - Android Emulator Plugin

4. Configure Jenkins:
   - Go to Jenkins Dashboard > Manage Jenkins > Global Tool Configuration
   - Configure JDK 17
   - Configure Maven
   - Configure Android SDK

5. Create New Pipeline:
   - Go to Jenkins Dashboard > New Item
   - Enter a name for your pipeline
   - Select "Pipeline" as the project type
   - Click OK
   - In the Pipeline configuration:
     - Select "Pipeline script from SCM"
     - Choose "Git" as SCM
     - Enter your repository URL
     - Set the branch to */main
     - Set the Script Path to "Jenkinsfile"
   - Click Save

6. Run the Pipeline:
   - Click "Build Now" on your pipeline
   - Monitor the build progress in the console output

## Local Development Setup

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

## Project Structure

```
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── calculator/
│   │               ├── pages/
│   │               ├── tests/
│   │               └── utils/
├── testng.xml
├── pom.xml
├── Jenkinsfile
└── README.md
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