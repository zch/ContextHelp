package org.vaadin.jonatan.contexthelp.test.applications;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.jonatan.contexthelp.ContextHelp;

@SuppressWarnings("serial")
public class TestScrollingApplication extends UI {

	private ContextHelp help = new ContextHelp();

	@Override
    protected void init(VaadinRequest request) {
        help.extend(this);

        VerticalLayout content = new VerticalLayout();
        content.setHeight("2000px");
        setContent(content);

		CheckBox check = new CheckBox("Checkity check check");
		help.addHelpForComponent(check, "Helpity help help");

		TextField textField = new TextField("Dummy");
		help.addHelpForComponent(textField, "textitiy text text");
		content.addComponent(textField);
		content.addComponent(check);
	}
}
