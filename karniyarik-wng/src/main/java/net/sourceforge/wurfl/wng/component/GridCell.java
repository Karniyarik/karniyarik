/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class GridCell extends Component {

	private static final long serialVersionUID = 10L;

	private Image icon;

	private Link link;

	// Constructor ********************************************************

	public GridCell() {
		// Empty
	}

	public GridCell(Image icon) throws InvalidContainmentException {

		this.icon = icon;
	}

	public GridCell(Image icon, Link link) throws InvalidContainmentException {

		this.icon = icon;
		this.link = link;
	}

	// Access methods *****************************************************

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	// Business methods ***************************************************

	public void accept(ComponentVisitor visitor) throws ComponentException {
		
		super.accept(visitor);
		
		if (icon != null) {
			icon.accept(visitor);
		}
		if (link != null) {
			link.accept(visitor);
		}
	}

	public void validate() throws ValidationException {

		if (icon == null) {
			throw new ValidationException(this, "The icon must be not null");
		}

		if (icon.getWidth() == null) {
			throw new ValidationException(this,
					"The icon must have the width property");
		}
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		
		if (obj instanceof GridCell) {
			builder.appendSuper(super.equals(obj));
			
			GridCell other = (GridCell) obj;
			builder.append(icon, other.icon);
			builder.append(link, other.link);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(icon).append(link);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());

		builder.append(icon).append(link);

		return builder.toString();
	}

}
