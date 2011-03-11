package org.vaadin.jonatan.contexthelp.demo;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class CodeSamples extends CustomComponent {
	private static final long serialVersionUID = -6877593216667244475L;

	private ContextHelp contextHelp = ContextHelpApplication.getContextHelp();

	private IndexedContainer fooContainer = new IndexedContainer();

	public CodeSamples() {
		final Panel panel = new Panel();
		((VerticalLayout)panel.getContent()).setSpacing(true);
		addListSelect(panel);

		for (int i = 0; i < 20; i++) {
			if (i % 2 == 0) {
				fooContainer.addItem("foo" + i);
			} else {
				fooContainer.addItem("bar" + i);
			}
		}
		
		setCompositionRoot(panel);
	}

	private void addListSelect(Panel panel) {
		panel.addComponent(new Label("ListSelect sel = new ListSelect(\"ListSelect\");\nsel.setContainerDataSource(fooContainer);\ncontextHelp.addHelpForComponent(sel, \"Select your preferred foobar\");", Label.CONTENT_PREFORMATTED));
		ListSelect sel = new ListSelect("ListSelect");
		sel.setContainerDataSource(fooContainer);
		contextHelp.addHelpForComponent(sel, "Select your preferred foobar");
		panel.addComponent(sel);
	}
}
