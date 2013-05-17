package org.vaadin.jonatan.contexthelp.test.applications;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.jonatan.contexthelp.ContextHelp;

/**
 * Created on 17.5.2013 13:41
 *
 * @author jonatan
 */
public class FeatureTest extends UI {
    private ContextHelp help = new ContextHelp();

    @Override
    protected void init(VaadinRequest request) {
        help.extend(this);

        VerticalLayout content = new VerticalLayout();
        setContent(content);

        TextField textField = new TextField("TextField");
        help.addHelpForComponent(textField, request.getParameter("helpText"));

        content.addComponent(textField);
    }

}
