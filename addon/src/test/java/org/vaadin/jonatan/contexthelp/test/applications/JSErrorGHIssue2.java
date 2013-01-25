package org.vaadin.jonatan.contexthelp.test.applications;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.jonatan.contexthelp.ContextHelp;

@SuppressWarnings("serial")
public class JSErrorGHIssue2 extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        final ContextHelp ch = new ContextHelp();
        ch.extend(this);

        final Button button = new Button("test");
        content.addComponent(button);

        ch.addHelpForComponent(button, "This is a test");
        ch.showHelpFor(button);
    }
}
