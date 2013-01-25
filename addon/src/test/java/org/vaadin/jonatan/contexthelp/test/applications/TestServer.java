package org.vaadin.jonatan.contexthelp.test.applications;

import com.vaadin.server.VaadinServlet;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;

import java.io.File;

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
        Server server = new Server();

        final Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        server.setConnectors(new Connector[] { connector });

        WebAppContext context = new WebAppContext();
        VaadinServlet vaadinServlet = new VaadinServlet();
        ServletHolder servletHolder = new ServletHolder(vaadinServlet);
        servletHolder.setInitParameter("widgetset",
                "org.vaadin.jonatan.contexthelp.widgetset.ContexthelpWidgetset");
        servletHolder.setInitParameter("UIProvider",
                TestUIProvider.class.getName());

        File file = new File("addon/target/testwebapp");
        context.setWar(file.getPath());
        context.setContextPath("/");

        context.addServlet(servletHolder, "/*");
        server.setHandler(context);
        server.start();
        return server;
    }

}
