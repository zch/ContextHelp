package com.vaadin.incubator.contexthelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

@com.vaadin.ui.ClientWidget(com.vaadin.incubator.contexthelp.widgetset.client.ui.VContextHelp.class)
public class ContextHelp extends AbstractComponent {
	private static final long serialVersionUID = 3852216539762314709L;

	private static int helpComponentIdCounter = 0;

	private HashMap<String, String> helpHTML;
	private List<Component> components;

	private String selectedComponentId = "";

	public ContextHelp() {
		helpHTML = new HashMap<String, String>();
		components = new ArrayList<Component>();
	}

	@SuppressWarnings("unchecked")
	public void changeVariables(Object source, Map variables) {
		selectedComponentId = (String) variables.get("selectedComponentId");
		requestRepaint();
	}

	public void paintContent(PaintTarget target) throws PaintException {
		target.addVariable(this, "selectedComponentId", selectedComponentId);
		if (selectedComponentId != null && !selectedComponentId.equals("")
				&& helpHTML.containsKey(selectedComponentId)) {
			target.addAttribute("helpText", helpHTML.get(selectedComponentId));
		}
	}

	public void addHelpForComponent(Component component, String help) {
		if (component.getDebugId() == null) {
			component.setDebugId(generateComponentId());
		}
		components.add(component);
		helpHTML.put(component.getDebugId(), help);
	}

	private String generateComponentId() {
		return "help_" + helpComponentIdCounter++;
	}

}
