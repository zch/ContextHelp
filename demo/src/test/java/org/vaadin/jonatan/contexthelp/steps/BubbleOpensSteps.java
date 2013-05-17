package org.vaadin.jonatan.contexthelp.steps;

import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.vaadin.jonatan.contexthelp.pageobjects.AddressFormPage;
import org.vaadin.jonatan.contexthelp.pageobjects.BubblePage;
import org.vaadin.jonatan.contexthelp.pageobjects.DemoPage;
import org.vaadin.jonatan.contexthelp.pageobjects.WrappedFieldsPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BubbleOpensSteps extends TestBenchTestCase {

    private DemoPage demoPage;
    private AddressFormPage addressForm;
    private WrappedFieldsPage wrappedFields;
    private BubblePage bubble;

    @Before
    public void beforeScenario() {
//        setDriver(TestBench.createDriver(new PhantomJSDriver(DesiredCapabilities.phantomjs())));
        setDriver(TestBench.createDriver(new ChromeDriver()));
        demoPage = PageFactory.initElements(getDriver(), DemoPage.class);
        bubble = PageFactory.initElements(getDriver(), BubblePage.class);
    }

    @After
    public void afterScenario() {
        getDriver().quit();
    }

    @Given("^the demo page$")
    public void I_have_a_UI_extended_with_ContextHelp() throws Throwable {
        demoPage.open();
        addressForm = demoPage.openAddressForm();
    }

    @Given("^the \"([^\"]*)\" tab is open$")
    public void the_tab_is_open(String tabName) throws Throwable {
        if ("Wrapped fields".equals(tabName)) {
            wrappedFields = demoPage.openWrappedFields();
        }
    }

    @When("^I focus the \"([^\"]*)\" field$")
    public void I_focus_the_field(String field) throws Throwable {
        addressForm.focusField(field);
    }

    @When("^I press F1$")
    public void I_press_F1() throws Throwable {
        demoPage.pressKeys(Keys.F1);
    }

    @Then("^it should display the help bubble$")
    public void it_should_display_the_help_bubble() throws Throwable {
        assertTrue(bubble.isOpen());
    }

    @Then("^the bubble text should read \"([^\"]*)\"$")
    public void the_bubble_text_should_read(String helpText) throws Throwable {
        assertEquals(helpText, bubble.getHelpText());
    }


    @When("^I click the icon of the \"([^\"]*)\" field$")
    public void I_click_the_icon_of_the_field(String fieldName) throws Throwable {
        if ("company".equals(fieldName)) {
            wrappedFields.clickCompanyButton();
        }
    }

}
