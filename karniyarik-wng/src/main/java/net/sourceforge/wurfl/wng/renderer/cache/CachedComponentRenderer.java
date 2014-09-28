/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.cache;

import java.io.Serializable;

import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.renderer.ComponentRenderer;
import net.sourceforge.wurfl.wng.renderer.RenderedComponent;
import net.sourceforge.wurfl.wng.renderer.RenderingContext;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class CachedComponentRenderer implements ComponentRenderer {

	private static final Logger logger = LoggerFactory
			.getLogger(CachedComponentRenderer.class);

	private ComponentRenderer delegate;

	private RenderedComponentCache cache;

	public CachedComponentRenderer(ComponentRenderer delegate,
			RenderedComponentCache cache) {
		this.delegate = delegate;
		this.cache = cache;
	}

	public CachedComponentRenderer(ComponentRenderer delegate) {
		this(delegate, new LRUMapCache(1000));
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext renderingContext) {

		Object key = createKey(component, renderingContext);
		RenderedComponent cached = cache.getRenderedComponent(key);

		if (cached == null) {
			logger.trace("RenderedComponent not found in cache with key: {}",
					key);
			cached = delegate.renderComponent(component, renderingContext);
			cache.putRenderedComponent(key, cached);
		} else if (logger.isTraceEnabled()) {
			logger.trace("RenderedComponent found in cache with key: {}", key);
		}

		return cached;
	}

	protected Object createKey(Component component,
			RenderingContext renderingContext) {

		return new RenderingComponentKey(component, renderingContext
				.getDevice());
	}

	private static class RenderingComponentKey implements Serializable {

		private static final long serialVersionUID = 10L;

		private Component component;

		private WNGDevice device;

		private transient int hashCode = 0;

		public RenderingComponentKey(Component component, WNGDevice device) {
			this.component = component;
			this.device = device;
		}

		public Component getComponent() {
			return component;
		}

		public WNGDevice getDevice() {
			return device;
		}

		public boolean equals(Object obj) {
			EqualsBuilder builder = new EqualsBuilder();

			builder.appendSuper(obj instanceof RenderingComponentKey);
			if (builder.isEquals() && obj instanceof RenderingComponentKey) {
				RenderingComponentKey other = (RenderingComponentKey) obj;
				builder.append(component, other.component);
				builder.append(device.getId(), other.device.getId());
			}

			return builder.isEquals();

		}

		public int hashCode() {

			if (hashCode == 0) {

				HashCodeBuilder builder = new HashCodeBuilder();

				builder.append(getClass());
				builder.append(component);
				builder.append(device.getId());

				hashCode = builder.toHashCode();

			}

			return hashCode;
		}

		public String toString() {
			return new ToStringBuilder(this).append(component).append(device)
					.toString();
		}

	}

}
