package org.vaadin.jonatan.contexthelp.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfigurePage extends AbstractPageObject {
    public ConfigurePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        return isTabSelected("Configure the help key");
    }

    public ConfigurePage selectHelpKey(String keyId) {
        WebElement select = getDriver().findElement(By.className("v-filterselect-input"));
        select.sendKeys(keyId + Keys.RETURN);
        return this;
    }

    public ConfigurePage focusTextfield() {
        getDriver().findElement(By.id("configure.textfield")).click();
        return this;
    }
}
