/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.template;

/**
 * 
 * @author Asres Gizaw Fantayeneh
 * @version $Id$
 */
public interface TemplateProvider {
	Template get(TemplateRequest request);
}
