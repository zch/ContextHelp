package org.vaadin.jonatan.contexthelp.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddressFormPage extends AbstractPageObject {

    @FindBy(id="address.company")
    private WebElement company;

    @FindBy(id="address.name")
    private WebElement name;

    @FindBy(id="address.street")
    private WebElement street;

    @FindBy(id="address.postalCode")
    private WebElement postalCode;

    @FindBy(id="address.country")
    private WebElement country;

    public AddressFormPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        return isTabSelected("Address form");
    }

    public AddressFormPage focusCompanyField() {
        company.click();
        return this;
    }

    public AddressFormPage focusNameField() {
        name.click();
        return this;
    }

    public AddressFormPage focusStreetField() {
        street.click();
        return this;
    }

    public AddressFormPage focusPostalCodeField() {
        postalCode.click();
        return this;
    }

    public AddressFormPage focusCountryField() {
        country.click();
        return this;
    }

    public AddressFormPage focusField(String field) {
        String s = field.toLowerCase();
        if (s.equals("company")) {
            focusCompanyField();
        } else if (s.equals("name")) {
            focusNameField();
        } else if (s.equals("street")) {
            focusStreetField();
        } else if (s.equals("postalcode")) {
            focusPostalCodeField();
        } else if (s.equals("country")) {
            focusCountryField();
        }
        return this;
    }
}
