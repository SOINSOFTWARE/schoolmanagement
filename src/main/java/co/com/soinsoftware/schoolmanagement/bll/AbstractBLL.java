package co.com.soinsoftware.schoolmanagement.bll;

import java.util.Set;

import net.sf.ehcache.Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.soinsoftware.schoolmanagement.util.CacheManager;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 28/01/2016
 */
public abstract class AbstractBLL {

	protected static final String CLASS_KEY = "classes";
	
	protected static final String CLASSROOM_KEY = "classrooms";
	
	protected static final String GRADE_KEY = "grades";
	
	protected static final String PERIOD_KEY = "periods";
	
	protected static final String SCHOOL_KEY = "schools";

	protected static final String SUBJECT_KEY = "subjects";
	
	protected static final String TIME_KEY = "times";
	
	protected static final String USER_KEY = "users";
	
	protected static final String USERTYPE_KEY = "userTypes";
	
	protected static final String YEAR_KEY = "years";

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractBLL.class);

	private final CacheManager cacheManager = CacheManager.getInstance();

	public boolean isCacheEmpty(final String cacheKey) {
		return this.cacheManager.isCacheEmpty(cacheKey);
	}

	public Cache getCacheUsingKey(final String cacheKey) {
		return this.cacheManager.getCache(cacheKey);
	}

	protected abstract Set<?> getObjectsFromCache();

	@SuppressWarnings("rawtypes")
	public Set getObjectsFromCache(final String cacheKey) {
		return this.cacheManager.getObjectsFromCache(this
				.getCacheUsingKey(cacheKey));
	}

	public Object getObjectFromCache(final String cacheKey, final Integer key) {
		final Cache cache = this.getCacheUsingKey(cacheKey);
		return this.cacheManager.getObjectFromCache(cache, key);
	}

	public Object getObjectFromCache(final String cacheKey, final String code) {
		final Cache cache = this.getCacheUsingKey(cacheKey);
		return this.cacheManager.getObjectFromCache(cache, code);
	}

	@SuppressWarnings("rawtypes")
	public boolean putObjectsInCache(final String cacheKey, final Set objectSet) {
		final Cache cache = this.getCacheUsingKey(cacheKey);
		return this.cacheManager.putObjectsInCache(cache, objectSet);
	}

	public boolean putObjectInCache(final String cacheKey, final Object object) {
		final Cache cache = this.getCacheUsingKey(cacheKey);
		return this.cacheManager.putObjectInCache(cache, object);
	}
}