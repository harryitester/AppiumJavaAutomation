package com.calculator.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class OperationsPage extends BasePage {
    @AndroidFindBy(id = "com.google.android.calculator:id/op_add")
    private WebElement plusButton;

    @AndroidFindBy(id = "com.google.android.calculator:id/op_sub")
    private WebElement minusButton;

    @AndroidFindBy(id = "com.google.android.calculator:id/op_mul")
    private WebElement multiplyButton;

    @AndroidFindBy(id = "com.google.android.calculator:id/op_div")
    private WebElement divideButton;

    @AndroidFindBy(id = "com.google.android.calculator:id/eq")
    private WebElement equalsButton;

    @AndroidFindBy(id = "com.google.android.calculator:id/clr")
    private WebElement clearButton;

    public OperationsPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Click plus button")
    public void clickPlus() {
        click(plusButton, "plus");
    }

    @Step("Click minus button")
    public void clickMinus() {
        click(minusButton, "minus");
    }

    @Step("Click multiply button")
    public void clickMultiply() {
        click(multiplyButton, "multiply");
    }

    @Step("Click divide button")
    public void clickDivide() {
        click(divideButton, "divide");
    }

    @Step("Click equals button")
    public void clickEquals() {
        click(equalsButton, "equals");
    }

    @Step("Click clear button")
    public void clickClear() {
        click(clearButton, "clear");
    }
} 