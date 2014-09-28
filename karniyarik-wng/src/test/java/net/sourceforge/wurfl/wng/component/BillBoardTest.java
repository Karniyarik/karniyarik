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
public class BillBoardTest extends BaseTest {


	protected Object[][] componentsProvider() throws Exception {

		BillBoard billBoard1 = new BillBoard();
		billBoard1.setId("123");
		BillBoard billBoard2 = new BillBoard();
		billBoard2.setId("123");
		
		BillBoard billBoardWithImage1 = new BillBoard();
		billBoardWithImage1.setImage(new Image());
		BillBoard billBoardWithImage2 = new BillBoard();
		billBoardWithImage2.setImage(new Image());
		
		BillBoard billBoardWithImage3 = new BillBoard();
		billBoardWithImage3.setImage(new Image("images/palin.JPG"));
		BillBoard billBoardWithImage4 = new BillBoard();
		billBoardWithImage4.setImage(new Image("images/palin.JPG"));
		
		
		return new Object[][]{{new BillBoard(),new BillBoard()},{billBoard1,billBoard2},
				{billBoardWithImage1,billBoardWithImage2},{billBoardWithImage3,billBoardWithImage4}
				};
	}


	protected Object[][] differentComponentsProvider() throws Exception {
		
		Css css1 = new Css();
		css1.setSelector("firstCss");
		BillBoard bbWithCss1 = new BillBoard();
		bbWithCss1.setStyle(css1);
		
		Css css2 = new Css();
		css2.setSelector("secondCss");
		BillBoard bbWithCss2 = new BillBoard();
		bbWithCss2.setStyle(css2);
		
		BillBoard billBoardWithImage1 = new BillBoard();
		billBoardWithImage1.setImage(new Image("images/palin.PNG"));
		BillBoard billBoardWithImage2 = new BillBoard();
		billBoardWithImage2.setImage(new Image("images/palin.JPG"));
		
		return new Object[][]{{bbWithCss1,bbWithCss2},{billBoardWithImage1,billBoardWithImage2}};
	}
	
	
}
