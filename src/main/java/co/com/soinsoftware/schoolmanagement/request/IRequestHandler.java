/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 28/01/2015
 */
public interface IRequestHandler<T> {

	public Set<T> findAll();

	public T save(@FormParam("object") String jsonObject);

	public String findByCode(@QueryParam("code") String code);
}