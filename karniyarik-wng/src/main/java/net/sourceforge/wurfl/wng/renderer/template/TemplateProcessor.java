/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
/**
 * 
 */
package net.sourceforge.wurfl.wng.renderer.template;

import java.util.Map;


/**
 * @author Asres Gisaw Fantayeneh
 * @version $Id$
 */
public interface TemplateProcessor {
	
    String process(Template template);
    String process(Template template, Map parameteres);

}
