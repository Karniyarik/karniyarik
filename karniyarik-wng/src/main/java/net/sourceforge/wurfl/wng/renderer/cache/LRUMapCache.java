/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.cache;

import java.util.Map;

import net.sourceforge.wurfl.wng.renderer.RenderedComponent;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang.Validate;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class LRUMapCache implements RenderedComponentCache {

	private Map/*Object,RenderedComponent*/ delegate;
	
	public LRUMapCache() {
		delegate = MapUtils.synchronizedMap(new LRUMap());
	}

	public LRUMapCache(int size) {
		
		Validate.isTrue(size > 0, "The size must be >0");
		
		delegate = MapUtils.synchronizedMap(new LRUMap(size));
	}

	public RenderedComponent getRenderedComponent(Object key) {
		return (RenderedComponent)delegate.get(key);
	}

	public void putRenderedComponent(Object key, RenderedComponent item) {
		
		delegate.put(key, item);
	}

}
