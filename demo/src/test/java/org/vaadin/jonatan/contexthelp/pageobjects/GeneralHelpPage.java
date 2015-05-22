package org.vaadin.jonatan.contexthelp.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GeneralHelpPage extends AbstractPageObject {

    @FindBy(className = "v-textfield")
    private WebElement field;

    public GeneralHelpPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        return isTabSelected("General help");
    }

    public GeneralHelpPage focusField() {
        field.click();
        return this;
    }
}
