package org.vaadin.jonatan.contexthelp.demo;

import com.vaadin.data.Binder;
import com.vaadin.ui.FormLayout;
import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class AddressForm extends FormLayout {

	private static final String companyHelp = "Fill the <i>company</i> field with the name of your company if your address is for official company business.";
	private static final String nameHelp = "Fill the <i>name</i> field with your name or the name of the contact person for your company.";
	private static final String streetHelp = "Fill the <i>street</i> field with the name of the street along with the street number, e.g. <b>Mallikuja 21 B 2</b>.";
	private static final String postalCodeHelp = "Fill the <i>Postal code</i> field with the postal code and city name, e.g. <b>20500 Turku</b>.";
	private static final String countryHelp = "Fill the <i>Country</i> field with the country where this address is situated.";

	private TextField companyField = new TextField("Company");
	private TextField nameField = new TextField("Name");
	private TextField streetField = new TextField("Street");
	private TextField postalCodeField = new TextField("Postal code");
	private TextField countryField = new TextField("Country");

	public AddressForm() {
        // Set custom IDs to allow for easier TestBench testing
        companyField.setId("address.company");
        nameField.setId("address.name");
        streetField.setId("address.street");
        postalCodeField.setId("address.postalCode");
        countryField.setId("address.country");

		Binder<Address> binder = new Binder<>(Address.class);
		binder.forField(companyField).withNullRepresentation("").bind("company");
        binder.forField(nameField).withNullRepresentation("").bind("name");
        binder.forField(streetField).withNullRepresentation("").bind("street");
        binder.forField(postalCodeField).withNullRepresentation("").bind("postalCode");
        binder.forField(countryField).withNullRepresentation("").bind("country");

		ContextHelp contextHelp = ContextHelpDemoUI.getContextHelp();
		contextHelp.addHelpForComponent(companyField, companyHelp);
		contextHelp.addHelpForComponent(nameField, nameHelp);
		contextHelp.addHelpForComponent(streetField, streetHelp);
		contextHelp.addHelpForComponent(postalCodeField, postalCodeHelp);
		contextHelp.addHelpForComponent(countryField, countryHelp);

		Address address = new Address();
        binder.readBean(address);

        addComponents(companyField, nameField, streetField, postalCodeField, countryField);
	}
}
