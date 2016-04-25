/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.soinsoftware.schoolmanagement.bll.ClassBLL;
import co.com.soinsoftware.schoolmanagement.bll.ClassRoomBLL;
import co.com.soinsoftware.schoolmanagement.bll.GradeBLL;
import co.com.soinsoftware.schoolmanagement.bll.PeriodBLL;
import co.com.soinsoftware.schoolmanagement.bll.SchoolBLL;
import co.com.soinsoftware.schoolmanagement.bll.SubjectBLL;
import co.com.soinsoftware.schoolmanagement.bll.UserBLL;
import co.com.soinsoftware.schoolmanagement.bll.UserTypeBLL;
import co.com.soinsoftware.schoolmanagement.bll.YearBLL;
import co.com.soinsoftware.schoolmanagement.util.ServerProperties;
import co.com.soinsoftware.schoolmanagement.util.ServiceLocator;

import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Initialize new server that will be responding for all request
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
public class SchoolManagementServer {
	
	/**
	 * Logger instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SchoolManagementServer.class);
	
	/**
	 * Initialize school management server application using parameter from server.properties file
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		ServerProperties serverProperties = ServerProperties.getInstance();
		
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(serverProperties.getPort());

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
        servletHolder.setInitParameter(serverProperties.getPackageKey(), serverProperties.getPackageValue());

        contexts.addHandler(context);

        server.setConnectors(new Connector[] {connector});
        server.setHandler(context);
        context.addServlet(servletHolder, "/*");
        server.setHandler(contexts);
        
        try {
			server.start();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
        
        ServiceLocator.init();
        setupInitialData();
    }
	
	/**
	 * Setups initial data from each cache used
	 */
	private static void setupInitialData() {
		ServiceLocator.getBean(ClassBLL.class).findAll();
		ServiceLocator.getBean(ClassRoomBLL.class).findAll();
		ServiceLocator.getBean(GradeBLL.class).findAll();
		ServiceLocator.getBean(PeriodBLL.class).findAll();
		ServiceLocator.getBean(SchoolBLL.class).findAll();
		ServiceLocator.getBean(SubjectBLL.class).findAll();
		ServiceLocator.getBean(UserBLL.class).findAll();
		ServiceLocator.getBean(UserTypeBLL.class).findAll();
		ServiceLocator.getBean(YearBLL.class).findAll();
	}
}
