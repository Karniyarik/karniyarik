/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: BannerRow.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public abstract class BaseInput extends LeafComponent {

	private static final long serialVersionUID = 10L;

	private String type;
	
	private String name;
	
	private String value;

	public BaseInput() {
		// Empty
	}
	
	public BaseInput(String type) {
		
		Validate.notEmpty(type, "input type must not be empty");
		
		this.type = type;
	}

	public BaseInput(String type, String name, String value) {
		this(type);
		this.name = name;
		this.value = value;
	}

	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		
		if (obj instanceof BaseInput) {
			builder.appendSuper(super.equals(obj));
			
			BaseInput other = (BaseInput) obj;
			builder.append(type, other.type);
			builder.append(name, other.name);
			builder.append(value, other.value);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(type);
		builder.append(name);
		builder.append(value);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(type);
		builder.append(name);
		builder.append(value);

		return builder.toString();
	}

}