/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

/**
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class ValidationException extends ComponentException {

	private static final long serialVersionUID = 10L;
	
	public ValidationException(Component source, String message) {
		super(source, message);
	}

}
