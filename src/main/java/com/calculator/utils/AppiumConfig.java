package com.calculator.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppiumConfig {
    private static final Logger logger = LogManager.getLogger(AppiumConfig.class);
    private static AndroidDriver driver;

    public static AndroidDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    private static void initializeDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Device")
                .setUdid("emulator-5554") 
                .setAppPackage("com.google.android.calculator")
                .setAppActivity("com.android.calculator2.Calculator")
                .setAutomationName("UiAutomator2")
                .setNoReset(true)
                .setAvdLaunchTimeout(Duration.ofSeconds(60))
                .setAvdReadyTimeout(Duration.ofSeconds(60))
                .setNewCommandTimeout(Duration.ofSeconds(30));

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            logger.info("Appium driver initialized successfully");
        } catch (MalformedURLException e) {
            logger.error("Failed to initialize Appium driver", e);
            throw new RuntimeException("Failed to initialize Appium driver", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("Appium driver closed successfully");
        }
    }
} 