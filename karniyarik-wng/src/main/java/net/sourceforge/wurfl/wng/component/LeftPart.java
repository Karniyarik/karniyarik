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
public class LeftPart extends LeafComponent {

    private static final long serialVersionUID = 10L;
    
    private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		Validate.notNull(image, "image must not be null");
		this.image = image;
	}
    
	
	protected void initStyle() {
		getStyle().addProperty(CssProperties.VERTICAL_ALIGN, "top");
	}
	
	// Commons methods ****************************************************
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof LeftPart) {
			LeftPart other = (LeftPart) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(image, other.image);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(image);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(image);

		return builder.toString();
	}

    
}
