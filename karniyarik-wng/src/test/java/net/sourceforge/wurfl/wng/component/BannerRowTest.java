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
public class BannerRowTest extends BaseTest {
	

	/**
	 * @testng.data-provider name="componentsProvider"
	 */
	protected Object[][] componentsProvider() throws Exception {
		return new Object[][]{{new BannerRow(),new BannerRow()}};
	}

	
	/**
	 * @testng.data-provider name="differentComponentsProvider"
	 */
	protected Object[][] differentComponentsProvider() throws Exception {
		BannerRow bannerRow1 = new BannerRow();
		BannerRow bannerRow2 = new BannerRow();
		
		bannerRow1.add(new Image("images/palin.JPG"));
		bannerRow1.add(new Image("images/palin.PNG"));
		
		return new Object[][]{{bannerRow1,bannerRow2}};
	}
}
