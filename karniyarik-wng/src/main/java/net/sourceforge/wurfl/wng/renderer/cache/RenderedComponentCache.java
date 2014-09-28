/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.cache;

import net.sourceforge.wurfl.wng.renderer.RenderedComponent;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public interface RenderedComponentCache {
	
	RenderedComponent getRenderedComponent(Object key);
	
	void putRenderedComponent(Object key, RenderedComponent item);

}
