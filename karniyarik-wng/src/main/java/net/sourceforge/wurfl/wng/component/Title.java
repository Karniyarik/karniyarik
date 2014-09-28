/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class Title extends LeafComponent {

    private static final long serialVersionUID = 10L;

    private String content;

    public Title() {
    	// Empty
    }

    public Title(String content) {
        this.content = content;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	/* (non-Javadoc)
	 * @see net.sourceforge.wurfl.wng.component.Component#initStyle()
	 */
	protected void initStyle() {
		getStyle().addProperty(CssProperties.TEXT_ALIGN, "center");
		getStyle().addProperty(CssProperties.FONT_WEIGHT, "bold");
		getStyle().addProperty(CssProperties.FONT_FAMILY, "Verdana, Arial");
	}
	
	// Business methods ***************************************************

	public void validate() throws ValidationException {
		if (this.content == null) {
			throw new ValidationException(this, "Title : The content must not be null");
		}
	}

	
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Title) {
			Title other = (Title) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(content, other.content);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(content);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(content);

		return builder.toString();
	}


}
