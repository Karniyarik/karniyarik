/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class Css implements Serializable {

	private static final long serialVersionUID = 10L;
	
	private String selector;

	private Map/*String, String*/ properties = new HashMap();

	public Css() {
		// Empty
	}
	
	public Css(Css other) {
		this.selector = other.selector;
		this.properties.putAll(other.getProperties());
	}
	
	public String getSelector() {
		return selector;
	}
	
	public void setSelector(String id) {
		this.selector = id;
	}
	
	public Map/*String,String*/ getProperties() {
		return new HashMap(properties);
	}
	
	public void setProperties(Map properties) {
		this.properties.clear();
		this.properties.putAll(properties);
	}
	
	// Business methods ***************************************************

	public boolean isEmpty() {
		return properties.size() == 0;
	}

	public CssProperty getProperty(String name) {
		return (CssProperty) this.properties.get(name);
	}

	public String getPropertyValue(String name) {
		if (this.properties.containsKey(name)) {
			return ((CssProperty) this.properties.get(name)).getValue();
		}
		
		return null;
	}

	public void addProperty(CssProperty property) {
		this.properties.put(property.getName(), property);
	}

	public void addProperty(String name, String value) {
		if (name != null && value != null) {
			this.properties.put(name, new CssProperty(name, value));
		}
	}
	
	public boolean containsProperty(String name) {
		return getProperty(name)!=null;
	}
	
	public void removeProperty(String name) {
		Validate.notEmpty(name, "name must not be null");
		this.properties.remove(name);
	}

	public void updateWith(Css css) {
		
		Validate.notNull(css, "The css must be not null");

		this.properties.putAll(css.properties);
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Css) {
			Css other = (Css) obj;
			builder.append(selector, other.selector).append(properties, other.properties);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(getClass()).append(selector).append(properties);

		return builder.toHashCode();
	}

	public String toString() {

		ToStringBuilder builder = new ToStringBuilder(this);

		builder.append(selector).append(properties);

		return builder.toString();
	}

	

}
