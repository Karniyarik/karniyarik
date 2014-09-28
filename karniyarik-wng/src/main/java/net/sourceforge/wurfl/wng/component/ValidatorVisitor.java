/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

/**
 * This visitor calls validate on each visited component.
 * 
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class ValidatorVisitor implements ComponentVisitor {

	public void visit(Component component) throws ComponentException {
		
		component.validate();

	}

}
