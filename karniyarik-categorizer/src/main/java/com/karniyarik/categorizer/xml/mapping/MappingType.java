//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.08 at 11:39:21 PM EET 
//


package com.karniyarik.categorizer.xml.mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.simpleframework.xml.Transient;

import com.karniyarik.categorizer.xml.category.CategoryType;


/**
 * <p>Java class for mappingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mappingType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="from" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="to" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mappingType", propOrder = {
    "value"
})
public class MappingType {

    @XmlValue
    protected String value;
    @XmlAttribute(required = true)
    protected String from;
    @XmlAttribute(required = true)
    protected String to;
    
    @Transient
    @XmlTransient
    private CategoryType fromCat;
    
    @Transient
    @XmlTransient
    private CategoryType toCat;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTo(String value) {
        this.to = value;
    }
    
    public CategoryType getFromCat()
	{
		return fromCat;
	}
    
    public void setFromCat(CategoryType fromCat)
	{
		this.fromCat = fromCat;
		setFrom(fromCat.getId());
	}
    
    public CategoryType getToCat()
	{
		return toCat;
	}
    
    public void setToCat(CategoryType toCat)
	{
		this.toCat = toCat;
		setTo(toCat.getId());
	}
    
    @Override
    public String toString()
    {
    	StringBuffer str = new StringBuffer();
    	
    	if(fromCat != null)
    	{
    		str.append(fromCat.getName());
    		str.append("(");
    		str.append(fromCat.getId());
    		str.append(")");
    	}
    	else
    	{
    		str.append(from);
    	}
    	
    	str.append("<-->");
    	
    	if(toCat != null)
    	{
    		str.append(toCat.getName());
    		str.append("(");
    		str.append(toCat.getId());
    		str.append(")");
    	}
    	else
    	{
    		str.append(to);
    	}
    	return str.toString();
    }
}
