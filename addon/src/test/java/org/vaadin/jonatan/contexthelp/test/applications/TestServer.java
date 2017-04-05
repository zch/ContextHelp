package org.vaadin.jonatan.contexthelp.test.applications;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.vaadin.server.VaadinServlet;

public class TestServer {
    private final static int PORT = 9998;

    public static void main(String[] args) {
        try {
            startServer(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Server startServer(int port) throws Exception {
        Server server = new Server(port);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        VaadinServlet vaadinServlet = new VaadinServlet();
        ServletHolder servletHolder = new ServletHolder(vaadinServlet);
        servletHolder.setInitParameter("widgetset", "org.vaadin.jonatan.contexthelp.widgetset.ContexthelpWidgetset");
        servletHolder.setInitParameter("UIProvider", TestUIProvider.class.getName());
        handler.addServletWithMapping(servletHolder, "/*");

        server.start();

        server.join();
        return server;
    }

}
