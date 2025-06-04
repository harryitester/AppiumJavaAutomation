package com.calculator.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CalculatorPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CalculatorPage.class);

    @AndroidFindBy(id = "com.google.android.calculator:id/result_final")
    private WebElement resultField;

    private final KeypadPage keypad;
    private final OperationsPage operations;

    public CalculatorPage(AndroidDriver driver) {
        super(driver);
        this.keypad = new KeypadPage(driver);
        this.operations = new OperationsPage(driver);
    }

    @Step("Get calculation result")
    public String getResult() {
        return getText(resultField, "result");
    }

    @Step("Perform addition: {num1} + {num2}")
    public void performAddition(int num1, int num2) {
        keypad.clickDigit(num1);
        operations.clickPlus();
        keypad.clickDigit(num2);
        operations.clickEquals();
        logger.info("Performed addition: {} + {}", num1, num2);
    }

    @Step("Perform subtraction: {num1} - {num2}")
    public void performSubtraction(int num1, int num2) {
        keypad.clickDigit(num1);
        operations.clickMinus();
        keypad.clickDigit(num2);
        operations.clickEquals();
        logger.info("Performed subtraction: {} - {}", num1, num2);
    }

    @Step("Perform multiplication: {num1} * {num2}")
    public void performMultiplication(int num1, int num2) {
        keypad.clickDigit(num1);
        operations.clickMultiply();
        keypad.clickDigit(num2);
        operations.clickEquals();
        logger.info("Performed multiplication: {} * {}", num1, num2);
    }

    @Step("Perform division: {num1} / {num2}")
    public void performDivision(int num1, int num2) {
        keypad.clickDigit(num1);
        operations.clickDivide();
        keypad.clickDigit(num2);
        operations.clickEquals();
        logger.info("Performed division: {} / {}", num1, num2);
    }
} 