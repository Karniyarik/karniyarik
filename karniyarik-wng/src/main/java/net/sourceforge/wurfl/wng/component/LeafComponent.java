/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public abstract class LeafComponent extends Component {
	
	private static final long serialVersionUID = 10L;
	
	public boolean hasChildren() {
		return false;
	}

	public List getChildren() {
		return ListUtils.EMPTY_LIST;
	}
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof LeafComponent) {
			builder.appendSuper(super.equals(obj));
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}


}
