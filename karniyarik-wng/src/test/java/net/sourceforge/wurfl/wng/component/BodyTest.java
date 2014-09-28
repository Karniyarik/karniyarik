/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;


/**
 * @author fanta
 * @testng.test groups ="unit"
 *  
 */
public class BodyTest extends BaseTest {


	protected Object[][] componentsProvider() throws Exception {
		
		Body b3 = new Body();
		b3.setId("bodyBlows");
		Body b4 = new Body();
		b4.setId("bodyBlows");
		
		
		return new Object[][]{{new Body(),new Body()},{b3,b4}};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		
		Body b1 = new Body();
		b1.setId("bodyBlows");
		Body b2 = new Body();
		b2.setId("bodyOfEvidence");
		
		return new Object[][]{{b1,b2}};
	}
}
