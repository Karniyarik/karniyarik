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

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class CheckBox extends BaseInput {

	private static final long serialVersionUID = 10L;

	public static final String TYPE = "checkbox";

	private boolean checked;

	private String title;
	
	private boolean disabled;

	public CheckBox() {	
		super(TYPE); 
	}
	
	public void setName(String name) {
		
		Validate.notEmpty(name, "input name must not be empty");
		
		super.setName(name);
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		
		if (obj instanceof CheckBox) {
			builder.appendSuper(super.equals(obj));
			
			CheckBox other = (CheckBox) obj;
			builder.append(checked, other.checked)
					.append(title, other.title).append(disabled, other.disabled);

		}
		else {
			builder.appendSuper(false);
		}


		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(checked).append(title).append(disabled);

		return builder.toHashCode();
	}

}
