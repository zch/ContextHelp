package org.vaadin.jonatan.contexthelp.test.applications.feature;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.jonatan.contexthelp.ContextHelp;
import org.vaadin.jonatan.contexthelp.HelpFieldWrapper;

public class HelpFieldWrapperTest extends UI {

    @Override
    protected void init(VaadinRequest request) {
        ContextHelp ch = new ContextHelp();
        ch.extend(this);

        VerticalLayout vl = new VerticalLayout();
        TextField f = new TextField("Test");

        ch.addHelpForComponent(f, "Foo bar help");

        HelpFieldWrapper w = new HelpFieldWrapper(f, ch);
        vl.addComponent(w);

        setContent(vl);
    }
}
