/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.NavigationBar;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: NavigationBarTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class NavigationBarTag extends ComponentTag {
	
	private static final long serialVersionUID = 1L;

	private String separator;

	/**
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	protected Component createComponent() {

		return new NavigationBar(separator);
	}

}
