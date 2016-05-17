/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
public class ServerProperties extends Properties {

	/**
	 * Logger instance
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerProperties.class);

	/**
	 * Auto generated serial version
	 */
	private static final long serialVersionUID = 2238009377057310242L;

	/**
	 * Property file name
	 */
	private static final String PROPERTY_FILE = "/server.properties";

	/**
	 * Server port
	 */
	private static final String PORT = "port";

	/**
	 * Package key
	 */
	private static final String PACKAGE_KEY = "package_key";

	/**
	 * Package key default value
	 */
	private static final String PACKAGE_KEY_DEFAULT = "com.sun.jersey.config.property.packages";

	/**
	 * Package value
	 */
	private static final String PACKAGE_VALUE = "package_value";

	/**
	 * Package value default value
	 */
	private static final String PACKAGE_VALUE_DEFAULT = "co.com.carpcosoftware.schoolmanagement.request";

	/**
	 * Report folder location
	 */
	private static final String REPORT_FOLDER = "report_folder";

	/**
	 * Instance from {@link ServerProperties}
	 */
	private static ServerProperties instance;

	/**
	 * Server properties constructor
	 */
	private ServerProperties() {
		super();
		try (final InputStream input = this.getClass().getResourceAsStream(
				PROPERTY_FILE)) {
			if (input != null) {
				LOGGER.info("Loading properties from {} file", PROPERTY_FILE);
				this.load(input);
			}
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	/**
	 * Gets {@link ServerProperties} instance
	 * 
	 * @return
	 */
	public static ServerProperties getInstance() {
		if (instance == null) {
			LOGGER.info("Creating a new instance of ServerProperties class");
			instance = new ServerProperties();
		}
		return instance;
	}

	/**
	 * Gets server port number
	 * 
	 * @return Port number
	 */
	public int getPort() {
		int port = 1517;
		if (this.containsKey(PORT)) {
			port = Integer.parseInt(this.getProperty(PORT));
			this.logInfo(PROPERTY_FILE, String.valueOf(port));
		}
		return port;
	}

	/**
	 * Gets package key
	 * 
	 * @return Package key
	 */
	public String getPackageKey() {
		final String packageKey = this.getProperty(PACKAGE_KEY,
				PACKAGE_KEY_DEFAULT);
		this.logInfo(PACKAGE_KEY, packageKey);
		return packageKey;
	}

	/**
	 * Gets package value
	 * 
	 * @return Package value
	 */
	public String getPackageValue() {
		final String packageValue = this.getProperty(PACKAGE_VALUE,
				PACKAGE_VALUE_DEFAULT);
		this.logInfo(PACKAGE_VALUE, packageValue);
		return packageValue;
	}

	/**
	 * Gets report folder location
	 * 
	 * @return Report folder location
	 */
	public String getReportFolder() {
		final String reportFolder = this.getProperty(REPORT_FOLDER);
		this.logInfo(REPORT_FOLDER, reportFolder);
		return reportFolder;
	}

	private void logInfo(final String property, final String value) {
		LOGGER.info("Property {} loaded with value {}", property, value);
	}
}