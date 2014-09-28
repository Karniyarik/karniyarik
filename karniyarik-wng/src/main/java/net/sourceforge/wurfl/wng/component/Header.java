/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: Header.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class Header extends Component {

	private static final long serialVersionUID = 10L;

	private Image image;

	private Text text;

	public Header() {
		// Empty
	}

	public Header(Image linkImage, Text text) {
		this.image = linkImage;
		this.text = text;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image linkImage) {
		this.image = linkImage;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	  /* (non-Javadoc)
	 * @see net.sourceforge.wurfl.wng.component.Component#initStyle()
	 */
	protected void initStyle() {
		getStyle().addProperty(CssProperties.PADDING, "0px");
		getStyle().addProperty(CssProperties.FONT_FAMILY, "Verdana, Arial");
	}
	

	// Business methods ***************************************************

	public void accept(ComponentVisitor visitor) throws ComponentException {

		super.accept(visitor);

		if (image != null) {
			image.accept(visitor);
		}
		if (text != null) {
			text.accept(visitor);
		}
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if (obj instanceof Header) {
			builder.appendSuper(super.equals(obj));
			
			Header other = (Header) obj;
			builder.append(image, other.image);
			builder.append(text, other.text);
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
		builder.append(text);

		return builder.toHashCode();
	}

}
