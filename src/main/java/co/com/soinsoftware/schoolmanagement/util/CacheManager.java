/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.util;

import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.entity.AbstractBO;
import co.com.soinsoftware.schoolmanagement.entity.AbstractWithCodeBO;

/**
 * Cache manager that will allow store objects
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
@Service
public class CacheManager {

	/**
	 * Logger object
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(CacheManager.class);

	/**
	 * EhCache manager
	 */
	private net.sf.ehcache.CacheManager cacheManager;

	/**
	 * Constructor
	 */
	public CacheManager() {
		super();
		cacheManager = net.sf.ehcache.CacheManager.create();
	}

	/**
	 * Gets specific cache object using parameter
	 * 
	 * @param cacheName
	 *            Name of cache that must be loaded
	 * @return {@link Cache} object
	 */
	public synchronized Cache getCache(String cacheName) {
		return this.cacheManager.getCache(cacheName);
	}

	/**
	 * Puts new objects into specific cache
	 * 
	 * @param cache
	 *            {@link Cache} that must be populated
	 * @param objectSet
	 *            {@link Set} with new objects that will be added to cache
	 * @return true if objects were added to cache, false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public synchronized boolean putObjectsInCache(Cache cache, Set objectSet) {
		boolean success = true;
		for (Object object : objectSet) {
			if (!this.putObjectInCache(cache, object)) {
				success = false;
				break;
			}
		}
		return success;
	}

	/**
	 * Puts new object into specific cache
	 * 
	 * @param cache
	 *            {@link Cache} that must be populated
	 * @param object
	 *            {@link AbstractBO} that will be stored
	 * @return true if object was added to cache, false otherwise
	 */
	public synchronized boolean putObjectInCache(Cache cache, Object object) {
		boolean success = true;
		try {
			Element element = new Element(((AbstractBO) object).getId(), object);
			cache.put(element);
			LOGGER.info("Putting element = {} in cache = {}",
					element.toString(), cache.getName());
		} catch (IllegalArgumentException | IllegalStateException
				| CacheException ex) {
			LOGGER.error(ex.getMessage());
			success = false;
		}
		return success;
	}

	/**
	 * Gets all objects stored in cache
	 * 
	 * @param cache
	 *            {@link Cache} that will be used to load objects
	 * @return {@link Set} of objects loaded from cache
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized Set getObjectsFromCache(Cache cache) {
		Set objectSet = null;

		for (Object key : cache.getKeys()) {
			if (objectSet == null) {
				objectSet = new HashSet<>();
			}
			objectSet.add(this.getObjectFromCache(cache, (Integer) key));
		}

		return objectSet;
	}

	/**
	 * Gets object stored in cache using specified key
	 * 
	 * @param cache
	 *            {@link Cache} that will be used to load object
	 * @param key
	 *            Identifier from object
	 * @return Object using specified key stored in cache
	 */
	public synchronized Object getObjectFromCache(Cache cache, Integer key) {
		Object object = null;
		try {
			Element element = cache.get(key);
			if (element != null) {
				object = (Object) element.getObjectValue();
				LOGGER.info("Loading object = {} from cache = {}",
						object.toString(), cache.getName());
			} else {
				LOGGER.info("Object with key = {} was not found in cache", key);
			}
		} catch (IllegalStateException | CacheException ex) {
			LOGGER.error(ex.getMessage());
		}
		return object;
	}

	/**
	 * Gets object stored in cache using specified key
	 * 
	 * @param cache
	 *            {@link Cache} that will be used to load object
	 * @param code
	 *            Unique code from object
	 * @return Object using specified code stored in cache
	 */
	public synchronized Object getObjectFromCache(Cache cache, String code) {
		Object object = null;
		for (Object key : cache.getKeys()) {
			Object objectFromCache = this.getObjectFromCache(cache,
					(Integer) key);
			if (objectFromCache != null
					&& ((AbstractWithCodeBO) objectFromCache).getCode().equals(code)) {
				object = objectFromCache;
				break;
			}
		}
		return object;
	}

	/**
	 * Verifies is cache is still empty
	 * 
	 * @param cacheName
	 *            Name of cache that must be loaded
	 * @return True if cache is still empty, false otherwise
	 */
	public synchronized boolean isCacheEmpty(String cacheName) {
		return this.getCache(cacheName).getSize() == 0;
	}
}