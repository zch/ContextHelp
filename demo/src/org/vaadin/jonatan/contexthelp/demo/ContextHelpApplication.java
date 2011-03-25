package org.vaadin.jonatan.contexthelp.demo;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

public class ContextHelpApplication extends Application implements TransactionListener {
	private static final long serialVersionUID = 1L;

	final ContextHelp contextHelp = new ContextHelp();

	public static ThreadLocal<ContextHelpApplication> currentApplication = new ThreadLocal<ContextHelpApplication>();

	@Override
	public void init() {
		Window mainWindow = new Window("Context Help");
		mainWindow.addComponent(contextHelp);
		setCurrent(this);
		
		getContext().addTransactionListener(this);

		CheckBox followFocus = new CheckBox("Make the help bubble follow focus",
				new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					public void buttonClick(ClickEvent event) {
						contextHelp.setFollowFocus((Boolean) event.getButton()
								.getValue());
					}
				});
		followFocus.setImmediate(true);
		mainWindow.addComponent(followFocus);

		TabSheet tabs = new TabSheet();
		tabs.addTab(new AddressForm(), "Address form", null);
		tabs.addTab(new GeneralHelp(), "General help", null);
		tabs.addTab(new PlacementDemo(), "Placement", null);
		tabs.addTab(new HelpKey(), "Configure the help key", null);
//		tabs.addTab(new CodeSamples(), "Code examples", null);
		mainWindow.addComponent(tabs);

		setMainWindow(mainWindow);
	}

	private void setCurrent(ContextHelpApplication app) {
		currentApplication.set(app);
	}
	
	public static ContextHelpApplication getCurrent() {
		return currentApplication.get();
	}

	public static ContextHelp getContextHelp() {
		return getCurrent().contextHelp;
	}

	public void transactionStart(Application application, Object transactionData) {
		if (application == this) {
			setCurrent(this);
		}
	}

	public void transactionEnd(Application application, Object transactionData) {
		if (application == this) {
			setCurrent(null);
		}
	}
}
