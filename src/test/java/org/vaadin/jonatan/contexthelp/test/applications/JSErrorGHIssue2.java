package org.vaadin.jonatan.contexthelp.test.applications;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class JSErrorGHIssue2 extends Application {

	@Override
	public void init() {
        final Window mainWindow = new Window("Playground Application");
        setMainWindow(mainWindow);

        final ContextHelp ch = new ContextHelp();
        mainWindow.addComponent(ch);

        final Button button = new Button("test");
        mainWindow.addComponent(button);

        ch.addHelpForComponent(button, "This is a test");
        ch.showHelpFor(button);
	}

}
