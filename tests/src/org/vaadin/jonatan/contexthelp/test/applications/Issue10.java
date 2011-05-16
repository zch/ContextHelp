package org.vaadin.jonatan.contexthelp.test.applications;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class Issue10 extends Application {

	private VerticalLayout  mainContent= null;
    private HorizontalLayout hl= null;

    @Override
    public void init()
    {
        initLayout();
    }

    private void initLayout()
    {
        Panel mainPanel= new Panel();
        setMainWindow(new Window("Test ContextHelp", mainPanel));
        mainContent = new VerticalLayout();
        mainPanel.addComponent(mainContent);
        Button toggle= new Button("Toggle content");
        mainContent.addComponent(toggle);
        toggle.addListener(new ClickListener()
            {
                public void buttonClick(ClickEvent event)
                {
                    if (hl != null)
                    {
                        mainContent.removeComponent(hl);
                        hl= null;
                    }
                    else
                    {
                        hl= new HorizontalLayout();
                        mainContent.addComponent(hl);
                        ContextHelp contextHelp = new ContextHelp();
                        hl.addComponent(contextHelp);
                        TextField bar = new TextField("bar");
                        hl.addComponent(bar);
                        contextHelp.addHelpForComponent(bar, "My help for textfield");
                        contextHelp.setFollowFocus(true);
                    }
                }
            });
        Button testButton= new Button("Test button");
        mainContent.addComponent(testButton);

    }
}
