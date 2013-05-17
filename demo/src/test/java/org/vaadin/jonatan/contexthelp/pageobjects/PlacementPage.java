package org.vaadin.jonatan.contexthelp.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PlacementPage extends AbstractPageObject {

    @FindBy(id="placement.below")
    private WebElement below;

    @FindBy(id="placement.right")
    private WebElement right;

    @FindBy(id="placement.left")
    private WebElement left;

    @FindBy(id="placement.above")
    private WebElement above;

    public PlacementPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        return isTabSelected("Placement");
    }

    public PlacementPage focusBelow() {
        below.click();
        return this;
    }

    public PlacementPage focusRight() {
        right.click();
        return this;
    }

    public PlacementPage focusLeft() {
        left.click();
        return this;
    }

    public PlacementPage focusAbove() {
        above.click();
        return this;
    }
}
