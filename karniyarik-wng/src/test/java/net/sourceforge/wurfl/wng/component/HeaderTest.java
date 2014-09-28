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
public class HeaderTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		Image image = new Image("images/fig.gif", "fig", "200", "100");
		Text text = new Text("text");
		Header one = new Header();
		one.setId("id");
		one.setImage(image);
		one.setText(text);
		
		Header two = new Header();
		two.setId("id");
		two.setImage(image);
		two.setText(text);
		
		return new Object[][] { 
				{ new Header(), new Header() },
				{ one, two}
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		Image image = new Image("images/fig.gif", "fig", "200", "100");
		Text text = new Text("text");
		
		return new Object[][] { 
				{ new Header(), new Header(image, text)},
				{ new Header(new Image(), new Text()), new Header(image, text)}
		};
	}
}
