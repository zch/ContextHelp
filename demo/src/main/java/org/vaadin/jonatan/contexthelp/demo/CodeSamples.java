package org.vaadin.jonatan.contexthelp.demo;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.jonatan.contexthelp.ContextHelp;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class CodeSamples extends CustomComponent {

    private ContextHelp contextHelp = ContextHelpDemoUI.getContextHelp();

    private ListDataProvider<String> fooProvider;

    public CodeSamples() {
        final Panel panel = new Panel();
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        panel.setContent(layout);
        addListSelect(layout);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                list.add("foo" + i);
            } else {
                list.add("bar" + i);
            }
        }
        fooProvider = new ListDataProvider<>(list);

        setCompositionRoot(panel);
    }

    private void addListSelect(VerticalLayout layout) {
        layout.addComponent(new Label("ListSelect sel = new ListSelect(\"ListSelect\");\nsel.setContainerDataSource(fooProvider);\ncontextHelp.addHelpForComponent(sel, \"Select your preferred foobar\");", ContentMode.PREFORMATTED));
        ListSelect sel = new ListSelect("ListSelect");
        sel.setDataProvider(fooProvider);
        contextHelp.addHelpForComponent(sel, "Select your preferred foobar");
        layout.addComponent(sel);
    }
}
