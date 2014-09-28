/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

/**
 * @testng.test groups ="unit"
 *  
 */
public class RackMenuTest extends BaseTest {


	protected Object[][] componentsProvider() throws Exception {
		
		Link firstLink = new Link("href", "first");
		Link secondLink = new Link("href", "second");
		
		RackMenu one = new RackMenu();
		one.setId("id");
		one.setEvenBackgroundColor("even");
		one.setOddBackgroundColor("odd");
		one.addLink(firstLink);
		one.addLink(secondLink);
		
		RackMenu two = new RackMenu();
		two.setId("id");
		two.setEvenBackgroundColor("even");
		two.setOddBackgroundColor("odd");
		two.addLink(firstLink);
		two.addLink(secondLink);
		
		return new Object[][] { 
				{ new RackMenu(), new RackMenu() },
				{ one, two}
		};
	}


	protected Object[][] differentComponentsProvider() throws Exception {
		RackMenu one = new RackMenu();
		one.setId("id");
		one.setEvenBackgroundColor("even");
		one.setOddBackgroundColor("odd");
		
		RackMenu two = new RackMenu();
		two.setId("id");
		two.setEvenBackgroundColor("odd");
		two.setOddBackgroundColor("even");
	
		return new Object[][] { 
				{ one, two},
				{ new RackMenu(), two},
				{one, new RackMenu()}
		};
	}
}
