package com.calculator.tests;

import com.calculator.pages.CalculatorPage;
import com.calculator.utils.AppiumConfig;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CalculatorTests {
    private static final Logger logger = LogManager.getLogger(CalculatorTests.class);
    private AndroidDriver driver;
    private CalculatorPage calculatorPage;

    @BeforeMethod
    public void setUp() {
        driver = AppiumConfig.getDriver();
        calculatorPage = new CalculatorPage(driver);
        logger.info("Test setup completed");
    }

    @AfterMethod
    public void tearDown() {
        AppiumConfig.quitDriver();
        logger.info("Test teardown completed");
    }

    @Test
    @Description("Test addition of two numbers")
    public void testAddition() {
        logger.info("Starting addition test");
        calculatorPage.performAddition(5, 3);
        Assert.assertEquals(calculatorPage.getResult(), "8", "Addition result is incorrect");
        logger.info("Addition test completed successfully");
    }

    @Test
    @Description("Test subtraction of two numbers")
    public void testSubtraction() {
        logger.info("Starting subtraction test");
        calculatorPage.performSubtraction(10, 4);
        Assert.assertEquals(calculatorPage.getResult(), "6", "Subtraction result is incorrect");
        logger.info("Subtraction test completed successfully");
    }

    @Test
    @Description("Test multiplication of two numbers")
    public void testMultiplication() {
        logger.info("Starting multiplication test");
        calculatorPage.performMultiplication(6, 7);
        Assert.assertEquals(calculatorPage.getResult(), "42", "Multiplication result is incorrect");
        logger.info("Multiplication test completed successfully");
    }

    @Test
    @Description("Test division of two numbers")
    public void testDivision() {
        logger.info("Starting division test");
        calculatorPage.performDivision(20, 4);
        Assert.assertEquals(calculatorPage.getResult(), "5", "Division result is incorrect");
        logger.info("Division test completed successfully");
    }
} 