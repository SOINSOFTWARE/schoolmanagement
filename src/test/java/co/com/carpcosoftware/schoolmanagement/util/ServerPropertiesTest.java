/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Junit test case for {@link ServerProperties} class
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 10/03/2015
 */
public class ServerPropertiesTest extends TestCase {

	private ServerProperties serverProperties;

	/**
	 * Constructor
	 */
	public ServerPropertiesTest() {
		super();
		serverProperties = ServerProperties.getInstance();
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ServerPropertiesTest.class);
	}

	/**
	 * Test port value
	 */
	public void testPortValue() {
		assertTrue("Is port value returned?", serverProperties.getPort() != 0);
	}
	
	/**
	 * Test package key
	 */
	public void testPackageKey() {
		assertTrue("Is package key returned?", !serverProperties.getPackageKey().equals(""));
	}
	
	/**
	 * Test package value
	 */
	public void testPackageValue() {
		assertTrue("Is package value returned?", !serverProperties.getPackageValue().equals(""));
	}
}
