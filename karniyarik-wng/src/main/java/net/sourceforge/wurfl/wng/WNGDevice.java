/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.wurfl.core.CapabilityNotDefinedException;
import net.sourceforge.wurfl.core.Device;
import net.sourceforge.wurfl.core.MarkUp;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The WNG Device.
 * <p/>
 * <p>
 * This is a class to support WNG to obtain the necessary information from a device WURFL.
 * </p>
 *
 * @author Asres Gizaw Fantayeneh
 * @version $Id: WNGDevice.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class WNGDevice implements Device, Serializable {

    private static final long serialVersionUID = 100L;
    
    private static final Logger logger = LoggerFactory.getLogger(WNGDevice.class);

    /**
     * The WURFL Device
     */
    private Device device;

    /**
     * Builds a WNGDevice from WURFL {@link Device}
     *
     * @param device The backend WURFL device.
     */
    public WNGDevice(Device device) {

        Validate.notNull(device, "The device must be not null");

        this.device = device;
    }

    /**
     * Returns the WURFL device id.
     *
     * @return String identifies the WURFL device.
     */
    public String getId() {

        return device.getId();
    }

    public Map getCapabilities() {
        return device.getCapabilities();
    }

    public String getCapability(String name)
            throws CapabilityNotDefinedException {
        return device.getCapability(name);
    }

    public String getUserAgent() {
        return device.getUserAgent();
    }


    /**
     * Returns the preferred mime-type for this device.
     *
     * @return String containing the preferred mime-type of the device.
     */
    public String getPreferredMimeType() {

        String mimeType = Constants.WML_CONTENT_TYPE;

        if (MarkUp.XHTML_SIMPLE.equals(device.getMarkUp())
                || MarkUp.XHTML_ADVANCED.equals(device.getMarkUp())) {
            mimeType = device
                    .getCapability(Constants.CN_XHTMLMP_PREFERRED_MIME_TYPE);
        }

        return mimeType;
    }


    public int getResolutionWidth() {
        return Integer.valueOf(
                device.getCapability(Constants.CN_RESOLUTION_WIDTH)).intValue();
    }


    public int getMaxImageWidth() {
        return Integer.valueOf(
                device.getCapability(Constants.CN_MAX_IMAGE_WIDTH)).intValue();
    }

    /**
     * Return the preferred markup (WML, CHTML, XHTML_SIMPLE, XHTML_ADVANCED)
     * for this device.
     *
     * @return Markup representing the preferred markup for this device.
     */   
    public MarkUp getMarkUp() {

        int xhtmlSupportLevel;
        MarkUp markUp = null;
        try {
            xhtmlSupportLevel = Integer.parseInt(getCapability(net.sourceforge.wurfl.core.Constants.CN_XHTML_SUPPORT_LEVEL));
        } catch (CapabilityNotDefinedException e) {

            logger.error("It is not possible getting markUp from capabilities: " + e.getLocalizedMessage());

            throw new RuntimeException(e.getLocalizedMessage(), e);
        }

        switch (xhtmlSupportLevel) {
            case 4:
            case 3:
                markUp = MarkUp.XHTML_ADVANCED;
                break;
            case 2:
            case 1:
                markUp = MarkUp.XHTML_SIMPLE;
                break;
            default:
                markUp = MarkUp.WML;
        }


        return markUp;
    }

    /**
     * The supported image formats for this device
     *
     * @return List of String represent the supported images format for this
     *         device.
     */
    public List getImagesFormats() {

        List supportedFormats = new ArrayList();
        String[] formats = {"jpg", "gif", "png", "bmp", "wbmp"};

        for (int i = 0; i < formats.length; i++) {
            if (device.getCapability(formats[i]).compareToIgnoreCase("true") == 0) {
                supportedFormats.add(formats[i]);
            }
        }

        return supportedFormats;
    }

    /**
     * Returns the preferred image format for this device.
     *
     * @return String represent the preferred image format for this device.
     */
    public String getPreferredFormat() {

        return getPreferredFormat(getImagesFormats());

    }

    /**
     * Returns the preferred image format from given formats.
     *
     * @param formats The formats of images to choose from among the favorite.
     * @return The better image format from given formats.
     */
    public String getPreferredFormat(List formats) {

        for (Iterator i = formats.iterator(); i.hasNext();) {
            String format = (String) i.next();
            if (device.getCapability(format).compareToIgnoreCase("true") == 0) {
                return format;
            }
        }

        return null;
    }

    // Commons methods ****************************************************

    public boolean equals(Object obj) {

        if (!(obj instanceof WNGDevice)) {
            return false;
        }

        EqualsBuilder eb = new EqualsBuilder();
        eb.appendSuper(getClass().isInstance(obj));

        if (eb.isEquals()) {
            WNGDevice other = (WNGDevice) obj;
            eb.append(device, other.device);
        }

        return eb.isEquals();
    }

    public int hashCode() {

        return new HashCodeBuilder().append(device).toHashCode();
    }

    public String toString() {

        return new ToStringBuilder(this).append(device).toString();
    }


}
