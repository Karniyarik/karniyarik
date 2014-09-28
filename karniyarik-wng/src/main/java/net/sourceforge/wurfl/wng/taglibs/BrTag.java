/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import net.sourceforge.wurfl.wng.component.Br;
import net.sourceforge.wurfl.wng.component.Component;
/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: BrTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class BrTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	protected Component createComponent() {

		return new Br();
	}

}
