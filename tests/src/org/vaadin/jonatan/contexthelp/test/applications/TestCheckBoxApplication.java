package org.vaadin.jonatan.contexthelp.test.applications;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.vaadin.Application;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class TestCheckBoxApplication extends Application {

	private ContextHelp help = new ContextHelp();
	
	@Override
	public void init() {
		Window mainWindow = new Window("CheckBox test");
		setMainWindow(mainWindow);
		
		mainWindow.addComponent(help);
		
		CheckBox check = new CheckBox("Checkity check check");
		help.addHelpForComponent(check, "Helpity help help");

		TextField textField = new TextField("Dummy");
		help.addHelpForComponent(textField, "textitiy text text");
		mainWindow.addComponent(textField);
		mainWindow.addComponent(check);
	}

}
