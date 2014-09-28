<div xmlns="http://www.w3.org/1999/xhtml"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:gr="http://purl.org/goodrelations/v1#"
	xmlns:owl="http://www.w3.org/2002/07/owl#"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
	xmlns:vcard="http://www.w3.org/2006/vcard/ns#" class="rdf2rdfa">
<div class="description" about="http://www.karniyarik.com/"
	typeof="owl:Ontology">
<div rel="owl:imports" resource="http://www.w3.org/2006/vcard/ns"></div>
<div rel="owl:imports" resource="http://purl.org/goodrelations/v1"></div>
<div property="rdfs:label"
	content="RDF/XML data for Karniyarik Ltd., based on http://purl.org/goodrelations/"
	xml:lang="en"></div>
</div>
<div class="description"
	about="http://www.karniyarik.com/#BusinessEntity"
	typeof="gr:BusinessEntity">
<div rel="vcard:adr" resource="http://www.karniyarik.com/#address"></div>
<div rel="gr:hasPOS">
<div class="description" about="http://www.karniyarik.com/#LOSOSP_1"
	typeof="gr:LocationOfSalesOrServiceProvisioning">
<div rel="vcard:adr">
<div class="description" about="http://www.karniyarik.com/#address"
	typeof="vcard:Address">
<div property="vcard:country-name" content="Turkey" xml:lang="en"></div>
<div property="vcard:email" content="info@karniyarik.com"></div>
<div property="vcard:locality" content="Ankara"></div>
<div property="vcard:postal-code" content="06561 "></div>
<div property="vcard:street-address"
	content="KOSGEB Teknoloji Geliştirme Merkezi No:302 ODTÜ Teknokent"></div>
<div property="vcard:tel" content="+90-312-2101300-312"></div>
<div rel="vcard:url" resource="http://www.karniyarik.com/"></div>
</div>
</div>
<div rel="rdfs:isDefinedBy" resource="http://www.karniyarik.com/"></div>
<div property="rdfs:label" content="Karniyarik Ltd." xml:lang="en"></div>
</div>
</div>
<div rel="rdfs:isDefinedBy" resource="http://www.karniyarik.com/"></div>
<div property="gr:legalName" content="Karniyarik Ltd."
	datatype="xsd:string"></div>
<div rel="gr:offers">
<div class="description" about="http://www.karniyarik.com/#Offering_1"
	typeof="gr:Offering">
<div rel="gr:availableAtOrFrom"
	resource="http://www.karniyarik.com/#LOSOSP_1"></div>
<div rel="gr:eligibleCustomerTypes"
	resource="http://purl.org/goodrelations/v1#Enduser"></div>
<div property="gr:eligibleRegions" content="TR" datatype="xsd:string"></div>
<div rel="rdfs:isDefinedBy" resource="http://www.karniyarik.com/"></div>
<div property="gr:validFrom" content="2010-01-01T00:00:00+02:00"
	datatype="xsd:dateTime"></div>
<div property="gr:validThrough" content="2015-01-01T23:59:00+02:00"
	datatype="xsd:dateTime"></div>
</div>
</div>
<div rel="rdfs:seeAlso" resource="http://www.karniyarik.com/"></div>
</div>
</div>