package org.vaadin.jonatan.contexthelp.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DemoPage extends AbstractPageObject {

    private BubblePage bubblePage;

    public DemoPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        return "Make the help bubble follow focus".equals(getDriver().findElement(By.xpath("(//input)[1]/label")).getText());
    }

    public void open() {
        getDriver().get("http://localhost:8080/?restartApplication");
    }

    public AddressFormPage openAddressForm() {
        selectTab("Address form");
        return PageFactory.initElements(getDriver(), AddressFormPage.class);
    }

    public WrappedFieldsPage openWrappedFields() {
        selectTab("Wrapped fields");
        return PageFactory.initElements(getDriver(), WrappedFieldsPage.class);
    }

    public GeneralHelpPage openGeneralHelp() {
        selectTab("General help");
        return PageFactory.initElements(getDriver(), GeneralHelpPage.class);
    }

    public PlacementPage openPlacement() {
        selectTab("Placement");
        return PageFactory.initElements(getDriver(), PlacementPage.class);
    }

    public ConfigurePage openConfigure() {
        selectTab("Configure the help key");
        return PageFactory.initElements(getDriver(), ConfigurePage.class);
    }

    public ProgrammaticPage openProgrammaticControl() {
        selectTab("Programmatic control");
        return PageFactory.initElements(getDriver(), ProgrammaticPage.class);
    }

    public BubblePage getBubble() {
        if (bubblePage == null) {
            bubblePage = PageFactory.initElements(getDriver(), BubblePage.class);
        }
        return bubblePage;
    }
}
