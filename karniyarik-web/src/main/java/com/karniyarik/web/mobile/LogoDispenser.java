package com.karniyarik.web.mobile;

import net.sourceforge.wurfl.wng.ImageDispenser;
import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.Image;

public class LogoDispenser implements ImageDispenser {
	
    private Image image;
    
    private String dirName = "/m/images/logo/";     

    public LogoDispenser(WNGDevice device) {
		
		int width = device.getMaxImageWidth();
	
		if (width <= 160) {
		    image = new Image(dirName+"karniyarik150x30.png", "Karnıyarık", "150","30"); 
		} else if (width <= 240) {
		    image = new Image(dirName+"karniyarik160x32.png", "Karnıyarık", "160","32"); 
		} else { 
		    image = new Image(dirName+"karniyarik240x50.png", "Karnıyarık", "240","50"); 
		}
    }

    public Image getImage() {
    	return image;
    }
}
