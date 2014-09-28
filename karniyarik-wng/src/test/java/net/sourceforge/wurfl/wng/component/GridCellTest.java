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
public class GridCellTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		GridCell one = new GridCell();
		one.setId("id");
		one.setIcon(new Image("images/fig.gif", "alt", "200", "30"));
		one.setLink(new Link("http://nowheretogo", "nowhere", "x"));
		
		GridCell two = new GridCell();
		two.setId("id");
		two.setIcon(new Image("images/fig.gif", "alt", "200", "30"));
		two.setLink(new Link("http://nowheretogo", "nowhere", "x"));
		
		return new Object[][] { 
				{ new GridCell(), new GridCell()},
				{ one, two}
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		
		GridCell cell = new GridCell();
		cell.setIcon(new Image("images/fig.gif", "alt", "200", "30"));
		cell.setLink(new Link("http://nowheretogo", "nowhere", "x"));
		
		
		return new Object[][] { 
				{ new GridCell(), cell}
		};
	}
}
