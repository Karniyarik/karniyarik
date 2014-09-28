/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
/**
 * 
 */
package net.sourceforge.wurfl.wng.renderer;

import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.Document;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public interface DocumentRenderer {
	
	RenderedDocument renderDocument(Document document, WNGDevice device) throws RenderingException;
}
