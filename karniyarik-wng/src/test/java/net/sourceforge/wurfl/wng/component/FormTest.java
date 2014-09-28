/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.util.Collection;

import org.testng.Assert;

/**
 * @author fanta
 * @testng.test groups ="unit"
 * 
 */
public class FormTest extends BaseTest {

	public void shouldReturnOnlyInputComponents() throws Exception {
		Form form = new Form();
		BaseInput input = new Input(Input.TYPE_TEXT);
		form.add(Br.INSTANCE);
		form.add(input);

		Collection inputs = form.getInputComponents();

		Assert.assertTrue(inputs.contains(input));
		Assert.assertFalse(inputs.contains(Br.INSTANCE));
	}

	protected Object[][] componentsProvider() throws Exception {
		return new Object[][]{{new Form(),new Form()},{new Form("ciao.do",Form.METHOD_GET), new Form("ciao.do",Form.METHOD_GET)}};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][]{{new Form("go.do",Form.METHOD_GET), new Form("fo.do",Form.METHOD_GET)},{new Form("go.do",Form.METHOD_POST), new Form("fo.do",Form.METHOD_GET)},
				{new Form("fo.do",Form.METHOD_POST), new Form("fo.do",Form.METHOD_GET)}};
	}
}
