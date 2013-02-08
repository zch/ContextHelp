package org.vaadin.jonatan.contexthelp.demo;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.jonatan.contexthelp.ContextHelp;

@SuppressWarnings("serial")
public class CodeSamples extends CustomComponent {

    private ContextHelp contextHelp = ContextHelpDemoUI.getContextHelp();

    private IndexedContainer fooContainer = new IndexedContainer();

    public CodeSamples() {
        final Panel panel = new Panel();
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        panel.setContent(layout);
        addListSelect(layout);

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                fooContainer.addItem("foo" + i);
            } else {
                fooContainer.addItem("bar" + i);
            }
        }

        setCompositionRoot(panel);
    }

    private void addListSelect(VerticalLayout layout) {
        layout.addComponent(new Label("ListSelect sel = new ListSelect(\"ListSelect\");\nsel.setContainerDataSource(fooContainer);\ncontextHelp.addHelpForComponent(sel, \"Select your preferred foobar\");", ContentMode.PREFORMATTED));
        ListSelect sel = new ListSelect("ListSelect");
        sel.setContainerDataSource(fooContainer);
        contextHelp.addHelpForComponent(sel, "Select your preferred foobar");
        layout.addComponent(sel);
    }
}
