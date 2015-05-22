package org.vaadin.jonatan.contexthelp.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.jonatan.contexthelp.ContextHelp;

@Theme("demo-theme")
public class ContextHelpDemoUI extends UI {
    private static final long serialVersionUID = 1L;

    final ContextHelp contextHelp = new ContextHelp();

    @Override
    protected void init(VaadinRequest request) {
        contextHelp.extend(this);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        setCurrent(this);

        CheckBox followFocus = new CheckBox("Make the help bubble follow focus");
        followFocus.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                contextHelp.setFollowFocus((Boolean) event.getProperty().getValue());
            }
        });
        followFocus.setImmediate(true);
        mainLayout.addComponent(followFocus);

        TabSheet tabs = new TabSheet();
        tabs.addTab(new AddressForm(), "Address form", null);
        tabs.addTab(new WrappedFields(), "Wrapped fields", null);
        tabs.addTab(new GeneralHelp(), "General help", null);
        tabs.addTab(new PlacementDemo(), "Placement", null);
        tabs.addTab(new HelpKey(), "Configure the help key", null);
//		tabs.addTab(new CodeSamples(), "Code examples", null);
        tabs.addTab(new ProgrammaticControl(), "Programmatic control", null);
        mainLayout.addComponent(tabs);

        setContent(mainLayout);
    }

    public static ContextHelp getContextHelp() {
        return ((ContextHelpDemoUI) getCurrent()).contextHelp;
    }
}
