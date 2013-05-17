package org.vaadin.jonatan.contexthelp.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BubblePage extends AbstractPageObject {

    @FindBy(className = "v-contexthelp-bubble")
    public WebElement bubble;

    public BubblePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        return isElementPresent(By.className("v-contexthelp-bubble"));
    }

    public boolean isOnTheRight() {
        return bubble.getAttribute("class").contains("right");
    }

    public boolean isOnTheLeft() {
        return bubble.getAttribute("class").contains("left");
    }

    public boolean isAbove() {
        return bubble.getAttribute("class").contains("above");
    }

    public boolean isBelow() {
        return bubble.getAttribute("class").contains("below");
    }

    public String getHelpText() {
        return bubble.findElement(By.className("helpText")).getText();
    }
}
