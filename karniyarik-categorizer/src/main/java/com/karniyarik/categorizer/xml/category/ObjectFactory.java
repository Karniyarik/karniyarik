//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.10 at 10:18:29 PM EET 
//


package com.karniyarik.categorizer.xml.category;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.karniyarik.categorizer.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Root_QNAME = new QName("", "root");
    private final static QName _CategoryTypeCategory_QNAME = new QName("", "category");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.karniyarik.categorizer.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CategoryType }
     * 
     */
    public CategoryType createCategoryType() {
        return new CategoryType();
    }

    /**
     * Create an instance of {@link RootType }
     * 
     */
    public RootType createRootType() {
        return new RootType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RootType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "root")
    public JAXBElement<RootType> createRoot(RootType value) {
        return new JAXBElement<RootType>(_Root_QNAME, RootType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "category", scope = CategoryType.class)
    public JAXBElement<CategoryType> createCategoryTypeCategory(CategoryType value) {
        return new JAXBElement<CategoryType>(_CategoryTypeCategory_QNAME, CategoryType.class, CategoryType.class, value);
    }

}
