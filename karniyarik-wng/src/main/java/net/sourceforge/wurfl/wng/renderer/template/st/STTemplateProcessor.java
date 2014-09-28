/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.template.st;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.wurfl.wng.renderer.template.Template;
import net.sourceforge.wurfl.wng.renderer.template.TemplateProcessor;

import org.antlr.stringtemplate.NoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;
import org.apache.commons.lang.Validate;

public class STTemplateProcessor implements TemplateProcessor {

    public STTemplateProcessor() {
        // Empty
    }

    public String process(Template template) {
        return process(template, new HashMap());
    }

    /**
     * XXX Cache-able
     */
    public String process(Template template, Map parameteres) {
    	
    	Validate.isTrue(template instanceof STTemplate);

        StringTemplate st = ((STTemplate)template).getDelegate();
        st.setAttributes(parameteres);
        
        // Patch by Magnus Kvalheim<magnus@kvalheim.dk>
        StringWriter writer = new StringWriter();
        StringTemplateWriter stWriter = createStringTemplateWriter(writer);
        
        try {
			st.write(stWriter);
		} catch (IOException e) {
			// FIXME declare exception
			throw new RuntimeException(e);
		}
        
        return writer.toString();
    }
    
    protected StringTemplateWriter createStringTemplateWriter(Writer writer) {
    	return new NoIndentWriter(writer);
    }
    
}
