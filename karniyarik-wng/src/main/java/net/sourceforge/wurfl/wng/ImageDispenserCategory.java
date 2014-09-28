/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo de Luca
 * 
 * @version $Id: ImageDispenserCategory.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public final class ImageDispenserCategory implements Serializable, Comparable {
	
	private static final long serialVersionUID = 10L;
	
	public static final ImageDispenserCategory SMALL = new ImageDispenserCategory(128);
	public static final ImageDispenserCategory MEDIUM = new ImageDispenserCategory(176);
	public static final ImageDispenserCategory LARGE = new ImageDispenserCategory(240);
	
	private final int width;
	
	private ImageDispenserCategory(int width) {
		this.width = width;
	}
	
	public ImageDispenserCategory fromDevice(WNGDevice device) {
		
		Validate.notNull(device, "The device is null");
		int deviceWidth = device.getMaxImageWidth();
		
		if(deviceWidth <= SMALL.width){
			return SMALL;
		}
		else if(deviceWidth <= MEDIUM.width) {
			return MEDIUM;
		}
		else {
			return LARGE;
		}	
	}
	
	// Commons methods ****************************************************
	
	public int compareTo(Object obj) {
		
		ImageDispenserCategory other = (ImageDispenserCategory)obj;
		
		return Integer.valueOf(width).compareTo(Integer.valueOf(other.width));
	}
	
	public boolean equals(Object obj) {
		EqualsBuilder eb = new EqualsBuilder();
		
		eb.appendSuper(obj instanceof ImageDispenserCategory);
		if(eb.isEquals() && obj instanceof ImageDispenserCategory) {
			ImageDispenserCategory other = (ImageDispenserCategory)obj;
			eb.append(width, other.width);
		}

		return eb.isEquals();
	}
	
	public int hashCode() {

		return new HashCodeBuilder().append(getClass()).append(width).toHashCode();
	}

}
