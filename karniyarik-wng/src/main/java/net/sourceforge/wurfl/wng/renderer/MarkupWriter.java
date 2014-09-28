/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;

/**
 * @author Filippo De Luca
 *
 */
public interface MarkupWriter {
	
	String writeMarkup(Component component, Map/*String,Object*/ arguments);

}
