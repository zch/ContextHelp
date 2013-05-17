package org.vaadin.jonatan.contexthelp.demo;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.jonatan.contexthelp.widgetset.client.ui.Placement;

@SuppressWarnings("serial")
public class PlacementDemo extends CustomComponent {

    private GridLayout layout;

    public PlacementDemo() {
        layout = new GridLayout(6, 6);
        setHeight("500px");
        setWidth("100%");
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        setCompositionRoot(vl);
        vl.addComponent(layout);
        vl.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

        TextField below = new TextField("Below");
        below.setId("placement.below");
        layout.addComponent(below, 1, 0);
        ContextHelpDemoUI.getContextHelp().addHelpForComponent(below,
                "Opens below the component", Placement.BELOW);

        TextField right = new TextField("To the right");
        right.setId("placement.right");
        layout.addComponent(right, 0, 1);
        ContextHelpDemoUI.getContextHelp().addHelpForComponent(right,
                "Opens to the right of the component", Placement.RIGHT);

        TextField left = new TextField("To the left");
        left.setId("placement.left");
        layout.addComponent(left, 2, 1);
        ContextHelpDemoUI.getContextHelp().addHelpForComponent(left,
                "Opens to the left of the component", Placement.LEFT);

        TextField above = new TextField("Above");
        above.setId("placement.above");
        layout.addComponent(above, 1, 2);
        ContextHelpDemoUI.getContextHelp().addHelpForComponent(above,
                "Opens above the component", Placement.ABOVE);
    }
}
