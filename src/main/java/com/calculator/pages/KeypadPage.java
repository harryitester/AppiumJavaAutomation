package com.calculator.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class KeypadPage extends BasePage {
    @AndroidFindBy(id = "com.google.android.calculator:id/digit_0")
    private WebElement digit0;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_1")
    private WebElement digit1;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_2")
    private WebElement digit2;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_3")
    private WebElement digit3;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_4")
    private WebElement digit4;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_5")
    private WebElement digit5;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_6")
    private WebElement digit6;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_7")
    private WebElement digit7;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_8")
    private WebElement digit8;

    @AndroidFindBy(id = "com.google.android.calculator:id/digit_9")
    private WebElement digit9;

    public KeypadPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Click digit: {digit}")
    public void clickDigit(int digit) {
        String digitStr = String.valueOf(digit);
        for (char c : digitStr.toCharArray()) {
            int singleDigit = Character.getNumericValue(c);
            WebElement digitElement = switch (singleDigit) {
                case 0 -> digit0;
                case 1 -> digit1;
                case 2 -> digit2;
                case 3 -> digit3;
                case 4 -> digit4;
                case 5 -> digit5;
                case 6 -> digit6;
                case 7 -> digit7;
                case 8 -> digit8;
                case 9 -> digit9;
                default -> throw new IllegalArgumentException("Invalid digit: " + singleDigit);
            };
            click(digitElement, "digit " + singleDigit);
        }
    }
} 