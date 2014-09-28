/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.BaseInput;
import net.sourceforge.wurfl.wng.component.CheckBox;
import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Hidden;
import net.sourceforge.wurfl.wng.component.Input;
import net.sourceforge.wurfl.wng.component.Submit;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: InputTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class InputTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private String accesskey;
	private String format;
	private boolean checked;
	private boolean disabled;
	private String emptyok;
	private String maxlength;
	private String name;
	private String size;
	private String title;
	private String type;
	private String value;

	protected Component createComponent() {

		if (Submit.TYPE.equalsIgnoreCase(type)) {
			return new Submit();
		} else if(CheckBox.TYPE.equalsIgnoreCase(type)) {
			return new CheckBox();
		} else if(Hidden.TYPE.equalsIgnoreCase(type)) {
			return new Hidden();
		} else {
			return new Input(type);
		}
	}

	protected void configureComponent(Component component) throws JspException {
		
		super.configureComponent(component);
		
		setCommonAttributes((BaseInput)component);

		if(component instanceof Submit) {
			Submit submit = (Submit) component;
			submit.setDisabled(disabled);
		}
		if(component instanceof CheckBox) {
			CheckBox checkBox = (CheckBox)component;
			checkBox.setChecked(checked);
		} else if (component instanceof Input) {
			Input input = (Input) component;
			input.setSize(size);
			input.setFormat(format);
			input.setMaxlength(maxlength);
			input.setAccesskey(accesskey);
			input.setEmptyok(emptyok);
			input.setDisabled(disabled);
			input.setTitle(title);
		}
		
	}

	private void setCommonAttributes(BaseInput baseInput) {
		baseInput.setName(name);
		baseInput.setValue(value);
	}

	
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setEmptyok(String emptyok) {
		this.emptyok = emptyok;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
