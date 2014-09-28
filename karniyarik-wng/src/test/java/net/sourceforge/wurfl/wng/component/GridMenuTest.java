/*
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
public class GridMenuTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		GridCell cell = new GridCell();
		cell.setIcon(new Image("images/fig.gif", "alt", "200", "30"));
		cell.setLink(new Link("http://nowheretogo", "nowhere", "x"));
		
		GridMenu one = new GridMenu();
		one.setId("id");
		one.addCell(cell);
		
		GridMenu two = new GridMenu();
		two.setId("id");
		two.addCell(cell);
		
		
		return new Object[][] { 
				{ new GridMenu(), new GridMenu() },
				{ one, two}
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		
		GridCell cell = new GridCell();
		cell.setIcon(new Image("images/fig.gif", "alt", "200", "30"));
		cell.setLink(new Link("http://nowheretogo", "nowhere", "x"));
		
		GridMenu one = new GridMenu();
		one.setId("id");
		one.addCell(cell);
		
		GridMenu two = new GridMenu();
		two.setId("id");
		two.addCell(new GridCell());
		
		return new Object[][] { 
				{ new GridMenu(), two}
		};
	}
}
