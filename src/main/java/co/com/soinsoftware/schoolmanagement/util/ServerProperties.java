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
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerProperties.class);

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
	
	private static final String DRIVER_CLASS_NAME = "driver_class_name";
	
	private static final String DB_URL = "db_url";
	
	private static final String DB_USERNAME = "db_username";
	
	private static final String DB_PASSWORD = "db_password";
	
	/**
	 * Instance from {@link ServerProperties}
	 */
	private static ServerProperties instance;
	
	/**
	 * Server properties constructor
	 */
	private ServerProperties() {
		super();
		
		try (InputStream input = ServerProperties.class.getResourceAsStream(PROPERTY_FILE)) {
			if (input != null) {
				LOGGER.info("Loading properties from {} file", PROPERTY_FILE);
				this.load(input);
			}
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage());
		}
	}
	
	/**
	 * Gets {@link ServerProperties} instance
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
	 * @return Port number
	 */
	public int getPort() {
		int port = 1517;
		if (this.containsKey(PORT)) {
			port = Integer.parseInt(this.getProperty(PORT));
			
			LOGGER.info("Server port {} successfully loaded from {} file", port, PROPERTY_FILE);
		}
		return port;
	}
	
	/**
	 * Gets package key
	 * @return Package key
	 */
	public String getPackageKey() {
		String packageKey = this.getProperty(PACKAGE_KEY, PACKAGE_KEY_DEFAULT);
		
		LOGGER.info("Property {} loaded with value {}", PACKAGE_KEY, packageKey);
		
		return packageKey;
	}
	
	/**
	 * Gets package value
	 * @return Package value
	 */
	public String getPackageValue() {
		String packageValue = this.getProperty(PACKAGE_VALUE, PACKAGE_VALUE_DEFAULT); 
		
		LOGGER.info("Property {} loaded with value {}", PACKAGE_VALUE, packageValue);
		
		return packageValue;
	}
	
	/**
	 * Gets driver class name
	 * @return Driver class name
	 */
	public String getDriverClassName() {
		final String driverClassName = this.getProperty(DRIVER_CLASS_NAME);		
		LOGGER.info("Property {} loaded with value {}", DRIVER_CLASS_NAME, driverClassName);		
		return driverClassName;
	}
	
	/**
	 * Gets database URL
	 * @return Database URL
	 */
	public String getDbUrl() {
		final String dbUrl = this.getProperty(DB_URL);		
		LOGGER.info("Property {} loaded with value {}", DB_URL, dbUrl);		
		return dbUrl;
	}
	
	/**
	 * Gets database user name
	 * @return Database user name
	 */
	public String getDbUserName() {
		final String dbUserName = this.getProperty(DB_USERNAME);		
		LOGGER.info("Property {} loaded with value {}", DB_USERNAME, dbUserName);		
		return dbUserName;
	}
	
	/**
	 * Gets database password
	 * @return Database password
	 */
	public String getDbPassword() {
		final String dbPassword = this.getProperty(DB_PASSWORD);		
		LOGGER.info("Property {} loaded with value {}", DB_PASSWORD, dbPassword);		
		return dbPassword;
	}
}
