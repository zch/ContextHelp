package org.vaadin.jonatan.contexthelp.demo;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ProgrammaticControl extends VerticalLayout {
	private static final String fieldHelp = "Foo.";

	private TextField field = new TextField("TextField");

	public ProgrammaticControl() {
		setSizeFull();

        field.setId("programmatic.textfield");
		addComponent(field);
		final ContextHelp contextHelp = ContextHelpDemoUI.getContextHelp();
		contextHelp.addHelpForComponent(field, fieldHelp);

		addComponent(new Button("Toggle help", new Button.ClickListener() {
			boolean hidden = true;

			public void buttonClick(ClickEvent event) {
				if (hidden) {
					contextHelp.showHelpFor(field);
					hidden = false;
				} else {
					contextHelp.hideHelp();
					hidden = true;
				}
			}
		}));

		addComponent(new Button("Toggle autohide", new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				contextHelp.setHideOnBlur(!contextHelp.isHideOnBlur());
			}
		}));
	}
}
