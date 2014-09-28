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
 * @version $Id: BannerRow.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class BillBoard extends Component {

	private static final long serialVersionUID = 10L;

	private Image image;

	public BillBoard() {
		// Empty
	}

	public BillBoard(Image image) {
		Validate.notNull(image, "The image must not be null");
		this.image = image;
	}
		
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		
		Validate.notNull(image, "The image in Bilboard must be not null");
	
	    this.image = image;
	}
	
	// Business methods ***************************************************
	
	protected void initStyle() {
		getStyle().addProperty(CssProperties.TEXT_ALIGN, "center");
		getStyle().addProperty(CssProperties.PADDING, "0px");
		getStyle().addProperty(CssProperties.MARGIN, "0px");
		getStyle().addProperty(CssProperties.BORDER_STYLE, "none");
		getStyle().addProperty(CssProperties.BORDER_WIDTH, "0px");
		getStyle().addProperty(CssProperties.BORDER_SPACING, "0px");
		getStyle().addProperty(CssProperties.BORDER, "0px");
	}
	
	public void accept(ComponentVisitor visitor) throws ComponentException {
		super.accept(visitor);
		
		if(image!=null){
			image.accept(visitor);
		}
	}
	
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof BillBoard) {
			builder.appendSuper(super.equals(obj));
			BillBoard other = (BillBoard) obj;

			builder.append(image, other.image);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode()).append(image);

		return builder.toHashCode();
	}


}
