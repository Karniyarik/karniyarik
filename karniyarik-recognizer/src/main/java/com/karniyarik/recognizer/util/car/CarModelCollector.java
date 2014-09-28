package com.karniyarik.recognizer.util.car;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.recognizer.xml.item.ItemType;
import com.karniyarik.recognizer.xml.item.ItemsType;
import com.karniyarik.recognizer.xml.item.ReferenceType;
import com.karniyarik.recognizer.xml.item.ReferencesType;
import com.karniyarik.recognizer.xml.item.RootType;

public class CarModelCollector
{
	String brandXPath = "/body/div[@id='zentriert']/div[6]/div[@id='tabcontent1']/table/tbody/tr/td[$index1]/font[$index]/a";
	String modelXPath = "/body/div[@id='zentriert']/div[@id='left2']/table[1]/tbody/tr[2]/td[2]/table/tbody/tr/td/form/table[1]/tbody/tr";
	
	private Map<String, ItemType> brandMap = new HashMap<String, ItemType>();
	private Map<String, ItemType> modelMap = new HashMap<String, ItemType>();
	
	public CarModelCollector() throws Exception
	{
		JAXBContext contentFileJaxbContext = JAXBContext.newInstance("com.karniyarik.recognizer.xml.item");
		Unmarshaller unmarshaller = contentFileJaxbContext.createUnmarshaller();
		JAXBElement<RootType> rootType= null; 
		InputStream inputStream = StreamUtil.getStream("car.xml");
		rootType = (JAXBElement<RootType>) unmarshaller.unmarshal(inputStream);
		//RootType contentRoot = (RootType) contentJaxbContext.get 
		inputStream.close();
		RootType root = rootType.getValue();
		
		ItemsType brandItemsType = root.getItems().get(0);
		for(ItemType item:brandItemsType.getItem())
		{
			brandMap.put(normalize(item.getName()), item);
		}
		
		ItemsType modelItemsType = root.getItems().get(1);
		for(ItemType item:modelItemsType.getItem())
		{
			modelMap.put(normalize(item.getName()), item);
		}
		
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();

		URL url = new URL("http://www.araba.com/");
		URLConnection con = url.openConnection();
		inputStream = con.getInputStream();
		String content = IOUtils.toString(inputStream, "iso-8859-9");
		inputStream.close();
		TagNode node = cleaner.clean(content);
		List<String> brands = new ArrayList<String>();
		
		for(int index=0; index<30; index++)
		{
			for(int subIndex=0; subIndex<30; subIndex++)
			{
				String xPath = brandXPath.replace("$index1", Integer.toString(index));
				xPath = xPath.replace("$index", Integer.toString(subIndex));
				Object[] myNodes = node.evaluateXPath(xPath);
				for(Object obj: myNodes)
				{
					String str=((TagNode)obj).getText().toString();
					if(StringUtils.isNotBlank(str))
					{
						brands.add(str);
					}
				}
			}			
		}
		
		for(String brand: brands)
		{
			String newBrand = brand.toLowerCase();
			newBrand = newBrand.trim();
			if(brand.equals("Fiat - Tofaş"))
			{
				newBrand="fiat-tofas";
			}
			else
			{
				newBrand  = newBrand.replaceAll("\\s", "-");	
			}
			
			System.out.println(newBrand);
			url = new URL("http://www.araba.com/ilan/"+newBrand);
			con = url.openConnection();
			inputStream = con.getInputStream();
			content = IOUtils.toString(inputStream, "iso-8859-9");
			inputStream.close();
			node = cleaner.clean(content);
			Object[] nodes = node.evaluateXPath(modelXPath);
			if(!brandMap.containsKey(normalize(brand)))
			{
				System.out.println("Adding brand: " + brand);
				ItemType itemType = new ItemType();
				itemType.setName(brand);
				brandItemsType.getItem().add(itemType);
			}
			
			if(nodes.length>0)
			{
				String text = ((TagNode)nodes[0]).getText().toString();
				String[] models = text.split("»");
				for(String model: models)
				{
					model = model.trim();
					
					if(StringUtils.isNotBlank(model) && !model.equals("Diğer") && !modelMap.containsKey(normalize(model)))
					{
						System.out.println("Adding model: " + model);
						ItemType itemType = new ItemType();
						itemType.setName(model);
						ReferenceType ref = new ReferenceType();
						ref.setId("car.brands." + brand);
						itemType.setReferences(new ReferencesType());
						itemType.getReferences().getReference().add(ref);
						modelItemsType.getItem().add(itemType);						
					}
				}
			}
		}
		
		JAXBContext jaxbContext = JAXBContext.newInstance("com.karniyarik.recognizer.xml.item");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
		marshaller.marshal(root,new FileOutputStream("jaxbOutput.xml"));
	}
	
	public String normalize(String value)
	{
		value = StringUtil.convertTurkishCharacter(value);
		value = value.toLowerCase(Locale.ENGLISH);
		value = value.replaceAll("[\\p{Punct}]", "");
		value = value.replaceAll("[\\s]", "");
		return value;
	}


	public static void main(String[] args) throws Exception
	{
		new CarModelCollector();
	}
}
