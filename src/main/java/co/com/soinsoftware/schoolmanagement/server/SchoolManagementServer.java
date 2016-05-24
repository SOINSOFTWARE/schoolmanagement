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
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SchoolManagementServer.class);

	/**
	 * Initialize school management server application using parameter from
	 * server.properties file
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		ServerProperties serverProperties = ServerProperties.getInstance();
		Server server = new Server();

		ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"co.com.soinsoftware.schoolmanagement.request.AppConfig");
		servletHolder.setInitParameter(serverProperties.getPackageKey(),
				serverProperties.getPackageValue());
		servletHolder.setInitParameter(
				"com.sun.jersey.api.json.POJOMappingFeature", "true");

		ServletContextHandler context = new ServletContextHandler(server, "/",
				ServletContextHandler.SESSIONS);
		context.addServlet(servletHolder, "/*");
		context.setClassLoader(SchoolManagementServer.class.getClassLoader());
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.addHandler(context);
		
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(serverProperties.getPort());

		server.setConnectors(new Connector[] { connector });
		server.setHandler(context);
		server.setHandler(contexts);

		try {
			server.start();
			server.dumpStdErr();
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
		ClassBLL.getInstance().findAll();
		ClassRoomBLL.getInstance().findAll();
		GradeBLL.getInstance().findAll();
		PeriodBLL.getInstance().findAll();
		SchoolBLL.getInstance().findAll();
		SubjectBLL.getInstance().findAll();
		UserBLL.getInstance().findAll();
		UserTypeBLL.getInstance().findAll();
		YearBLL.getInstance().findAll();
	}
}
