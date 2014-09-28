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
public class CheckBoxTest extends BaseTest {


	protected Object[][] componentsProvider() throws Exception {
		CheckBox cb1 = new CheckBox();
		cb1.setChecked(true);
		CheckBox cb2 = new CheckBox();
		cb2.setChecked(true);
		
		CheckBox cb3 = new CheckBox();
		cb3.setDisabled(true);
		CheckBox cb4 = new CheckBox();
		cb4.setDisabled(true);
		
		CheckBox cb5 = new CheckBox();
		cb5.setId("213333");
		CheckBox cb6 = new CheckBox();
		cb6.setId("213333");
		
		CheckBox cb7 = new CheckBox();
		cb7.setName("controllaScatola");
		cb7.setTitle("seventh checkbox");
		cb7.setValue("true");
		CheckBox cb8 = new CheckBox();
		cb8.setName("controllaScatola");
		cb8.setTitle("seventh checkbox");
		cb8.setValue("true");
		
		
		return new Object[][]{{new CheckBox(),new CheckBox()},{cb1,cb2},{cb3,cb4},{cb5,cb6},{cb7,cb8}};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		CheckBox cb1 = new CheckBox();
		cb1.setChecked(false);
		CheckBox cb2 = new CheckBox();
		cb2.setChecked(true);
		
		CheckBox cb3 = new CheckBox();
		cb3.setDisabled(false);
		CheckBox cb4 = new CheckBox();
		cb4.setDisabled(true);
		
		CheckBox cb5 = new CheckBox();
		cb5.setId("31312");
		CheckBox cb6 = new CheckBox();
		cb6.setId("213333");
		
		CheckBox cb7 = new CheckBox();
		cb7.setName("controllaScatola");
		cb7.setTitle("seventh checkbox");
		cb7.setValue("true");
		CheckBox cb8 = new CheckBox();
		cb8.setName("scatolaDelControllo");
		cb8.setTitle("seventh checkbox");
		cb8.setValue("true");
		
		return new Object[][]{{cb1,cb2},{cb3,cb4},{cb5,cb6},{cb7,cb8}};
	}
}
