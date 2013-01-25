package org.vaadin.jonatan.contexthelp.test.applications;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class TestUIProvider extends UIProvider {

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        String path = event.getRequest().getPathInfo().replace("/", "");
        if (path.isEmpty()) {
            return null;
        }

        String pkgName = getClass().getPackage().getName();
        String className = pkgName + "." + path;
        try {
            return (Class<? extends UI>)Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
