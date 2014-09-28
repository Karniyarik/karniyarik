/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
/*
 * $Id: Script.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Filippo De Luca
 * 
 * @version $Id: Script.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class Script extends Component {

	private static final long serialVersionUID = 10L;
	
	private String src;
	
	private String content;
	
	private String type;
	
	public Script() {
		type = "text/javascript";
	}

	public Script(String content) {
		this();
		this.content = content;
	}
	
	public Script(String src, String type) {
		Validate.notNull(src, "src must not be null");
		Validate.notNull(type, "type must not be not null");
		this.src = src;
		this.type = type;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Script) {
			builder.appendSuper(super.equals(obj));
			
			Script other = (Script) obj;
			builder.append(type, other.type);
			builder.append(content, other.content);
			builder.append(src, other.src);

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
		builder.append(content);
		builder.append(src);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(type);
		builder.append(content);
		builder.append(src);

		return builder.toString();
	}


}
