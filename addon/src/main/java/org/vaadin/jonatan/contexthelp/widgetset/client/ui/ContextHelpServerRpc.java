package org.vaadin.jonatan.contexthelp.widgetset.client.ui;

import com.vaadin.shared.communication.ServerRpc;

public interface ContextHelpServerRpc extends ServerRpc {
	void selectComponent(String id);
	void setHidden(boolean hidden);
}
