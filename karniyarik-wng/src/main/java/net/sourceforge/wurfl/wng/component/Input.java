/**
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
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class Input extends BaseInput {

	private static final long serialVersionUID = 10L;

	public static final String TYPE_TEXT = "text";
	public static final String TYPE_PASSWORD = "password";

	private String accesskey;

	private String format;

	private boolean disabled;

	private String emptyok;

	private String maxlength;

	private String size;

	private String title;

	public Input() {
		this(TYPE_TEXT);
	}
	
	public Input(String type) {
				
		super(type);
		
		Validate.isTrue(TYPE_TEXT.equals(type) || TYPE_PASSWORD.equals(type), "The type must be one of {text} or {password}");
	}
	
	

	public void setName(String name) {
		
		Validate.notEmpty(name, "input name must not be empty");
		
		super.setName(name);
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getEmptyok() {
		return emptyok;
	}

	public void setEmptyok(String emptyok) {
		this.emptyok = emptyok;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// Business methods ***************************************************

	public static Input newPasswordInput() {
		return new Input(TYPE_PASSWORD);
	}

	public static Input newTextInput() {
		return new Input(TYPE_TEXT);
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if (obj instanceof Input) {
			Input other = (Input) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(title, other.title);
			builder.append(accesskey, other.accesskey);
			builder.append(size, other.size);
			builder.append(maxlength, other.maxlength);
			builder.append(format, other.format);
			builder.append(emptyok, other.emptyok);
			builder.append(disabled, other.disabled);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(title);
		builder.append(accesskey);
		builder.append(size);
		builder.append(maxlength);
		builder.append(format);
		builder.append(emptyok);
		builder.append(disabled);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(title);
		builder.append(accesskey);
		builder.append(size);
		builder.append(maxlength);
		builder.append(format);
		builder.append(emptyok);
		builder.append(disabled);

		return builder.toString();
	}

}
