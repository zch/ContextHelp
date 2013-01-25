package org.vaadin.jonatan.contexthelp.widgetset.client.ui;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class ContextHelpState extends AbstractComponentState {
	
	public boolean hidden = true;

	public boolean followFocus = false;

	public int helpKey = KeyCode.F1;

	public boolean hideOnBlur = true;
	
	public String selectedComponentId = "";

	public String helpHTML = "";
	
	public Placement placement = null;
}
