package org.vaadin.jonatan.contexthelp.pageobjects;

import com.vaadin.testbench.By;
import org.openqa.selenium.WebDriver;

public class ProgrammaticPage extends AbstractPageObject {
    public ProgrammaticPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        return isTabSelected("Programmatic control");
    }

    public ProgrammaticPage toggleHelp() {
        buttonWithCaption("Toggle help").click();
        return this;
    }

    public ProgrammaticPage toggleAutohide() {
        buttonWithCaption("Toggle autohide").click();
        return this;
    }

    public ProgrammaticPage focusField() {
        getDriver().findElement(By.id("programmatic.textfield")).click();
        return this;
    }
}
