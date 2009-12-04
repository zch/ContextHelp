package com.vaadin.incubator.contexthelp;

import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ContextHelpApplication extends Application {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() {
		Window mainWindow = new Window("Context Help");
		ContextHelp contextHelp = new ContextHelp();
		mainWindow.addComponent(contextHelp);

		Label label = new Label("Hello Vaadin user");
		Panel panel = new Panel("panel");
		mainWindow.addComponent(panel);

		TextField bar = new TextField("bar");
		TextField foo = new TextField("foo");
		panel.addComponent(label);
		panel.addComponent(bar);
		panel.addComponent(foo);
		panel.addComponent(new TextField("No help"));

		contextHelp.addHelpForComponent(bar, "The text field allows you to write many, many, many, many, many things.");
		contextHelp.addHelpForComponent(foo, "<b>foo</b> can be <i>formatted</i> using standard <b style='font-size: 20px; color: red;'>HTML</b> and inline <b>CSS</b>.");
		
		VerticalLayout layout = new VerticalLayout();
		contextHelp.addHelpForComponent(layout, "Some fields in this layout have no help.<br/><br/>Instead of field specific help, this message for the entire panel is displayed.");
		layout.addComponent(new TextField("Try me!"));
		layout.addComponent(new TextField("oy!"));
		Select helpedField = new Select();
		layout.addComponent(helpedField);
		contextHelp.addHelpForComponent(helpedField, "Selects also work!<br/><span style='font-size: 10px;'>(As do all fields that can be focused)</span>");
		
		mainWindow.addComponent(layout);
		
		setMainWindow(mainWindow);
	}

}
