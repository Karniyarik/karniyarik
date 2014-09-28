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
public class InputTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		return new Object[][] { { new Input(), new Input() },
				{ new Input(), Input.newTextInput() },
				{ Input.newTextInput(), Input.newTextInput() },
				{ Input.newPasswordInput(), Input.newPasswordInput() },
				{ createInput(), createInput() } };
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] {
				{ new Input(), Input.newPasswordInput() },
				{ Input.newTextInput(), Input.newPasswordInput() } };
	}

	private Component createInput() {
		Input input = new Input();
		input.setAccesskey("a");
		input.setDisabled(true);
		input.setEmptyok("emptyOk");
		input.setFormat("format");
		input.setMaxlength("10");
		input.setName("name");
		input.setSize("10");

		return input;
	}
}
