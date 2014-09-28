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

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class IllustratedItem extends CompositeComponent {

	private static final long serialVersionUID = 10L;

	private Image image;

	public IllustratedItem() {
		// Empty
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		Validate.notNull(image, "image must not be null");
		this.image = image;
	}

	protected void initStyle() {
		getStyle().addProperty(CssProperties.PADDING, "2px");
		getStyle().addProperty(CssProperties.VERTICAL_ALIGN, "top");
	}

	// Business methods ***************************************************

	public void accept(ComponentVisitor visitor) throws ComponentException {

		super.accept(visitor);

		if (image != null) {
			image.accept(visitor);
		}
	}

	protected boolean isChildAllowed(Component child) {
		return child instanceof Image || child instanceof Text
				|| child instanceof Link || child instanceof Br;
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		builder.appendSuper(super.equals(obj));
		if (obj instanceof IllustratedItem) {
			IllustratedItem other = (IllustratedItem) obj;

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
}
