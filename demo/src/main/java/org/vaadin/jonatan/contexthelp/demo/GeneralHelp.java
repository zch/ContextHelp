package org.vaadin.jonatan.contexthelp.demo;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class GeneralHelp extends CustomComponent {

	public GeneralHelp() {
		Panel panel = new Panel(
				"Help texts can be attached to panels or layouts as well.");
		panel.addComponent(new TextField("Focus me and hit F1"));
		ContextHelpApplication.getContextHelp().addHelpForComponent(panel,
				"<h2><span style='color:red;'>Hello</span> from the panel!</h2>");
		setCompositionRoot(panel);
	}
}
