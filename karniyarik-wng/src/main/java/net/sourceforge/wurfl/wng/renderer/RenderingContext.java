/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.Component;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: RenderingContext.java 1130 2009-03-23 16:19:53Z filippo.deluca $
 */
public class RenderingContext {
	
	private static final long serialVersionUID = 10L;

	private static final Logger logger = LoggerFactory.getLogger(RenderingContext.class);
	
    private WNGDevice device;
    
    private RendererGroup rendererGroup;

    // Constructors *******************************************************

    /**
     * Creates a new instance of RenderingContext
     */
    public RenderingContext(WNGDevice device, RendererGroup rendererGroup) {

        Validate.notNull(device, "The device must be not null");
        Validate.notNull(rendererGroup, "The rendererGroup must be not null");
        
        this.device = device;
        this.rendererGroup = rendererGroup;

        logger.info("New RenderingContext created: {}",this);

    }

    public WNGDevice getDevice() {
        return device;
    }
            
    public ComponentRenderer getRenderer(Component target) {

    	Validate.notNull(target, "The component must be not null");
    	
    	ComponentRenderer renderer = rendererGroup.getRenderer(target);
    	
    	assert renderer != null : "Renderer for component: " + target + " is null";
    	
    	return renderer;
    }
    
    // Commons methods ****************************************************
    
    public String toString() {

        return new ToStringBuilder(this).append(device).toString();
    }
    
}
