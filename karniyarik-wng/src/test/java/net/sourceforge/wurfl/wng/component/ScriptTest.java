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
public class ScriptTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		String content = "alert(\"hello\")";
		return new Object[][] { 
				{ new Script(), new Script() },
				{ new Script(content), new Script(content)},
				{ new Script("scripts/wng.js", "text/javascript"), new Script("scripts/wng.js", "text/javascript")}
				
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		String hello = "alert(\"hello\")";
		String wng = "alert(\"wng\")";
		return new Object[][] { 
				{ new Script(), new Script(hello) },
				{ new Script(wng), new Script(hello)},
				{ new Script("scripts/wng.js", "text/css"), new Script("scripts/wng.js", "text/javascript")}
				
		};
	}
}
