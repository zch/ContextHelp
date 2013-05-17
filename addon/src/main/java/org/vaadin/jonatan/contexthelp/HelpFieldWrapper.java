package org.vaadin.jonatan.contexthelp;

import java.util.Collection;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class HelpFieldWrapper<T> extends CustomComponent implements Field<T>,
		ClickListener {

	private CssLayout layout;
	private final Field<T> field;
	private Button helpButton;
	private ContextHelp contextHelp;

	public HelpFieldWrapper(Field<T> field, ContextHelp contextHelp) {
		if (field == null) {
			throw new IllegalArgumentException("Cannot wrap a null field!");
		}
		this.field = field;
		this.contextHelp = contextHelp;

		layout = new CssLayout();
		layout.addComponent(field);

		helpButton = new Button("&nbsp;");
//        helpButton.setIcon(new ThemeResource("../runo/icons/16/help.png"));
        helpButton.addClickListener(this);
		helpButton.setStyleName(Reindeer.BUTTON_LINK);
		helpButton.addStyleName("context-help");
		layout.addComponent(helpButton);

		setCaption(field.getCaption());
		field.setCaption(null);

		setCompositionRoot(layout);
	}

	public void buttonClick(ClickEvent event) {
		contextHelp.showHelpFor(field);
	}

	/**
	 * Use this method to style the help button. E.g. change the icon or style
	 * name.
	 * 
	 * @return the button that opens the help bubble.
	 */
	public Button getHelpButton() {
		return helpButton;
	}

	/*
	 * Delegate the field interface methods to the wrapped field.
	 */

	public String getDescription() {
		return field.getDescription();
	}

	public boolean isRequired() {
		return field.isRequired();
	}

	public void setRequired(boolean required) {
		field.setRequired(required);
	}

	public void setRequiredError(String requiredMessage) {
		field.setRequiredError(requiredMessage);
	}

	public String getRequiredError() {
		return field.getRequiredError();
	}

	public T getValue() {
		return field.getValue();
	}

	public void setValue(T newValue) throws Property.ReadOnlyException {
		field.setValue(newValue);
	}

	public String toString() {
		return field.toString();
	}

	public Class<? extends T> getType() {
		return field.getType();
	}

	public boolean isReadOnly() {
		return field.isReadOnly();
	}

	public void setReadOnly(boolean newStatus) {
		field.setReadOnly(newStatus);
	}

	public boolean isInvalidCommitted() {
		return field.isInvalidCommitted();
	}

	public void setInvalidCommitted(boolean isCommitted) {
		field.setInvalidCommitted(isCommitted);
	}

	public void commit() throws SourceException, InvalidValueException {
		field.commit();
	}

	public void discard() throws SourceException {
		field.discard();
	}

	public boolean isModified() {
		return field.isModified();
	}

	public void addValidator(Validator validator) {
		field.addValidator(validator);
	}

	public void removeValidator(Validator validator) {
		field.removeValidator(validator);
	}

	public Collection<Validator> getValidators() {
		return field.getValidators();
	}

	public boolean isValid() {
		return field.isValid();
	}

	public void validate() throws InvalidValueException {
		field.validate();
	}

	public boolean isInvalidAllowed() {
		return field.isInvalidAllowed();
	}

	public void setInvalidAllowed(boolean invalidValueAllowed)
			throws UnsupportedOperationException {
		field.setInvalidAllowed(invalidValueAllowed);
	}

	public void addListener(ValueChangeListener listener) {
		field.addListener(listener);
	}

	public void removeListener(ValueChangeListener listener) {
		field.removeListener(listener);
	}

	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
		field.valueChange(event);
	}

	public void setPropertyDataSource(Property newDataSource) {
		field.setPropertyDataSource(newDataSource);
	}

	public Property getPropertyDataSource() {
		return field.getPropertyDataSource();
	}

	public int getTabIndex() {
		return field.getTabIndex();
	}

	public void setTabIndex(int tabIndex) {
		field.setTabIndex(tabIndex);
	}

	public void focus() {
		field.focus();
	}

	@Override
	public void setBuffered(boolean buffered) {
		field.setBuffered(buffered);
	}

	@Override
	public boolean isBuffered() {
		return field.isBuffered();
	}

	@Override
	public void removeAllValidators() {
		field.removeAllValidators();
	}

	@Override
	public void addValueChangeListener(ValueChangeListener listener) {
		field.addValueChangeListener(listener);
	}

	@Override
	public void removeValueChangeListener(ValueChangeListener listener) {
		field.removeValueChangeListener(listener);
	}
}
