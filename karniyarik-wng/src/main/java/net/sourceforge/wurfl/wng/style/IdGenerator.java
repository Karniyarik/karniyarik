/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.style;

import org.apache.commons.lang.text.StrBuilder;

/**
 * This class is responsible to generate CSS selector class consistent with the
 * css properties. Two CSS with same properties will have same selector class.
 * 
 * @author Asres Gizaw Fantayeneh
 * 
 * @version $Id$
 */
public final class IdGenerator {
	
	private IdGenerator() {
		// Empty
	}

	private static class CharacterCache {
		private static final int ASCII_CODE_OF_LOWER_CASE_A = 97;
		private static final int NUM_OF_CHARS = 23;

		private CharacterCache() {
		}

		static final char cache[] = new char[NUM_OF_CHARS];
		static {
			for (int i = 0; i < NUM_OF_CHARS; i++) {
				cache[i] = (char) (i + ASCII_CODE_OF_LOWER_CASE_A);
			}
		}
	}

	public static String idOf(int code) {
		StrBuilder idBuilder = new StrBuilder();
		int positiveCode = (code & 0x7fffffff);
		do {
			idBuilder.append(CharacterCache.cache[positiveCode
					% CharacterCache.NUM_OF_CHARS]);
			positiveCode = positiveCode / CharacterCache.NUM_OF_CHARS;
		} while (positiveCode > 1);

		return idBuilder.toString();
	}
}