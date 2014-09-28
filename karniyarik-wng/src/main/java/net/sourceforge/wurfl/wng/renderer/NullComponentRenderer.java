/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import net.sourceforge.wurfl.wng.component.Component;

/**
 * 
 * @author Asres Gizaw Fantayeneh
 * @version $Id$
 */
public class NullComponentRenderer implements ComponentRenderer {
	
	public static final NullComponentRenderer INSTACE = new NullComponentRenderer();
	
	private NullComponentRenderer(){}
	
	public RenderedComponent renderComponent(Component component,
			RenderingContext renderingContext) {
		return RenderedComponent.EMPTY;
	}

}
