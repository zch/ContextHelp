package org.vaadin.jonatan.contexthelp.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WrappedFieldsPage extends AbstractPageObject {
    @FindBy(id="wrapped.company")
    private WebElement company;

    @FindBy(id="wrapped.name")
    private WebElement name;

    @FindBy(id="wrapped.street")
    private WebElement street;

    @FindBy(id="wrapped.postalCode")
    private WebElement postalCode;

    @FindBy(id="wrapped.country")
    private WebElement country;

    @FindBy(id="wrapped.dob")
    private WebElement dob;

    public WrappedFieldsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        return isTabSelected("Wrapped fields");
    }

    public WrappedFieldsPage focusCompanyField() {
        company.click();
        return this;
    }

    public WrappedFieldsPage focusNameField() {
        name.click();
        return this;
    }

    public WrappedFieldsPage focusStreetField() {
        street.click();
        return this;
    }

    public WrappedFieldsPage focusPostalCodeField() {
        postalCode.click();
        return this;
    }

    public WrappedFieldsPage focusCountryField() {
        country.click();
        return this;
    }

    public WrappedFieldsPage focusDoBField() {
        dob.click();
        return this;
    }

    public WrappedFieldsPage clickCompanyButton() {
        getDriver().findElement(By.xpath("id('wrapped.company')/following-sibling::*")).click();
        return this;
    }

    public WrappedFieldsPage clickNameButton() {
        getDriver().findElement(By.xpath("id('wrapped.name')/following-sibling::*")).click();
        return this;
    }

    public WrappedFieldsPage clickStreetButton() {
        getDriver().findElement(By.xpath("id('wrapped.street')/following-sibling::*")).click();
        return this;
    }

    public WrappedFieldsPage clickPostalCodeButton() {
        getDriver().findElement(By.xpath("id('wrapped.postalCode')/following-sibling::*")).click();
        return this;
    }

    public WrappedFieldsPage clickCountryButton() {
        getDriver().findElement(By.xpath("id('wrapped.country')/following-sibling::*")).click();
        return this;
    }

    public WrappedFieldsPage clickDoBButton() {
        getDriver().findElement(By.xpath("id('wrapped.dob')/following-sibling::*")).click();
        return this;
    }
}
