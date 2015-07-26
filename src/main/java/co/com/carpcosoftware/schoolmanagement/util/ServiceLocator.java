/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.util;

import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring service locator
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 10/03/2015
 */
@EnableCaching
public class ServiceLocator implements ApplicationContextAware {

	private static final String SPRING_CONFIGURATION_FILE = "/spring_config.xml";
	
	private static ApplicationContext context = null;

	private ServiceLocator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) context.getBean(beanName);
	}

	public static void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				SPRING_CONFIGURATION_FILE);
		ServiceLocator serviceLocator = new ServiceLocator();
		serviceLocator.setApplicationContext(context);
	}
}
