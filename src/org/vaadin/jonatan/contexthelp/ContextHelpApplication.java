package org.vaadin.jonatan.contexthelp;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class ContextHelpApplication extends Application {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		Window mainWindow = new Window("Context Help");
		final ContextHelp contextHelp = new ContextHelp();
		mainWindow.addComponent(contextHelp);

		CheckBox followFocus = new CheckBox("Follow focus",
				new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				contextHelp.setFollowFocus((Boolean) event.getButton()
						.getValue());
			}
		});
		followFocus.setImmediate(true);
		mainWindow.addComponent(followFocus);

		VerticalLayout layout = new VerticalLayout();
		mainWindow.addComponent(layout);

		final TextField bar = new TextField("bar");
		TextField foo = new TextField("foo");
		layout.addComponent(bar);
		layout.addComponent(foo);
		layout.addComponent(new TextField("No help"));

		layout.addComponent(new Button("Open help for bar",
				new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					public void buttonClick(ClickEvent event) {
						contextHelp.showHelpFor(bar);
					}
				}));

		contextHelp
				.addHelpForComponent(bar,
						"The text field allows you to write many, many, many, many, many things.");
		contextHelp
				.addHelpForComponent(
						foo,
						"<b>foo</b> can be <i>formatted</i> using standard <b style='font-size: 20px; color: red;'>HTML</b> and inline <b>CSS</b>.");

		Panel panel = new Panel("Panel");
		contextHelp
				.addHelpForComponent(
						panel,
						"Some fields in this panel have no help.<br/><br/>Instead of field specific help, this message for the entire panel is displayed.<br/><br/>In case this bubble would go <i>outside</i> the view if it was placed to the left of the component, it is automatically placed pointing at the center.");
		panel.addComponent(new TextField("Try me!"));
		panel.addComponent(new TextField("me too!"));
		Select helpedField = new Select();
		panel.addComponent(helpedField);
		contextHelp
				.addHelpForComponent(
						helpedField,
						"Selects also work!<br/><span style='font-size: 10px;'>(As do all fields that can be focused)</span>");

		mainWindow.addComponent(panel);

		setMainWindow(mainWindow);
	}

}
