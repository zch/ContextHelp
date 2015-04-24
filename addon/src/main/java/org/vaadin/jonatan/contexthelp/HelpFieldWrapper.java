package org.vaadin.jonatan.contexthelp;

import java.util.Collection;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class HelpFieldWrapper<T> extends CustomComponent implements Field<T>, ClickListener {

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
        helpButton.setHtmlContentAllowed(true);
        helpButton.addClickListener(this);
        helpButton.setStyleName(Reindeer.BUTTON_LINK);
        helpButton.addStyleName("context-help");
        layout.addComponent(helpButton);

        setCaption(field.getCaption());
        field.setCaption(null);

        setCompositionRoot(layout);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        contextHelp.showHelpFor(field);
    }

    /**
     * Use this method to style the help button. E.g. change the icon or style name.
     * 
     * @return the button that opens the help bubble.
     */
    public Button getHelpButton() {
        return helpButton;
    }

    /*
     * Delegate the field interface methods to the wrapped field.
     */

    @Override
    public String getDescription() {
        return field.getDescription();
    }

    @Override
    public boolean isRequired() {
        return field.isRequired();
    }

    @Override
    public void setRequired(boolean required) {
        field.setRequired(required);
    }

    @Override
    public void setRequiredError(String requiredMessage) {
        field.setRequiredError(requiredMessage);
    }

    @Override
    public String getRequiredError() {
        return field.getRequiredError();
    }

    @Override
    public T getValue() {
        return field.getValue();
    }

    @Override
    public void setValue(T newValue) throws Property.ReadOnlyException {
        field.setValue(newValue);
    }

    @Override
    public String toString() {
        return field.toString();
    }

    @Override
    public Class<? extends T> getType() {
        return field.getType();
    }

    @Override
    public boolean isReadOnly() {
        return field.isReadOnly();
    }

    @Override
    public void setReadOnly(boolean newStatus) {
        field.setReadOnly(newStatus);
    }

    @Override
    public boolean isInvalidCommitted() {
        return field.isInvalidCommitted();
    }

    @Override
    public void setInvalidCommitted(boolean isCommitted) {
        field.setInvalidCommitted(isCommitted);
    }

    @Override
    public void commit() throws SourceException, InvalidValueException {
        field.commit();
    }

    @Override
    public void discard() throws SourceException {
        field.discard();
    }

    @Override
    public boolean isModified() {
        return field.isModified();
    }

    @Override
    public void addValidator(Validator validator) {
        field.addValidator(validator);
    }

    @Override
    public void removeValidator(Validator validator) {
        field.removeValidator(validator);
    }

    @Override
    public Collection<Validator> getValidators() {
        return field.getValidators();
    }

    @Override
    public boolean isValid() {
        return field.isValid();
    }

    @Override
    public void validate() throws InvalidValueException {
        field.validate();
    }

    @Override
    public boolean isInvalidAllowed() {
        return field.isInvalidAllowed();
    }

    @Override
    public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {
        field.setInvalidAllowed(invalidValueAllowed);
    }

    @Override
    public void addListener(ValueChangeListener listener) {
        field.addListener(listener);
    }

    @Override
    public void removeListener(ValueChangeListener listener) {
        field.removeListener(listener);
    }

    @Override
    public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
        field.valueChange(event);
    }

    @Override
    public void setPropertyDataSource(Property newDataSource) {
        field.setPropertyDataSource(newDataSource);
    }

    @Override
    public Property getPropertyDataSource() {
        return field.getPropertyDataSource();
    }

    @Override
    public int getTabIndex() {
        return field.getTabIndex();
    }

    @Override
    public void setTabIndex(int tabIndex) {
        field.setTabIndex(tabIndex);
    }

    @Override
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

    @Override
    public boolean isEmpty() {
        return field.isEmpty();
    }

    @Override
    public void clear() {
        field.clear();
    }
}
