package com.karniyarik.recognizer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.recognizer.xml.file.FilesType;
import com.karniyarik.recognizer.xml.item.ItemType;
import com.karniyarik.recognizer.xml.item.ItemsType;
import com.karniyarik.recognizer.xml.item.RootType;

public class ItemStore
{
	//holds Items.name, Map<processed Item.name, Item>
	private Map<String, Map<String, List<ItemType>>> valueMap = null;
	private ControllerMap controllerMap = null;
	
	public ItemStore(ControllerMap controllerMap)
	{
		this.controllerMap = controllerMap; 
		valueMap = new HashMap<String, Map<String,List<ItemType>>>();
		List<RootType> contentRootList = read();
		prepareIndexMap(contentRootList);
	}
	
	@SuppressWarnings("unchecked")
	public List<RootType> read() throws RuntimeException
	{
		List<RootType> result = new ArrayList<RootType>();
		
		try
		{
			InputStream inputStream = StreamUtil.getStream("files.xml"); 
			JAXBContext indexFileJaxbContext = JAXBContext.newInstance("com.karniyarik.recognizer.xml.file");
			Unmarshaller unmarshaller = indexFileJaxbContext.createUnmarshaller();
			JAXBElement<FilesType> files = (JAXBElement<FilesType>) unmarshaller.unmarshal(inputStream);
			inputStream.close();
			
			JAXBContext contentFileJaxbContext = JAXBContext.newInstance("com.karniyarik.recognizer.xml.item");
			unmarshaller = contentFileJaxbContext.createUnmarshaller();
			JAXBElement<RootType> content = null; 
			for(String fileName: files.getValue().getFile())
			{
				inputStream = StreamUtil.getStream(fileName);
				content = (JAXBElement<RootType>) unmarshaller.unmarshal(inputStream);
				//RootType contentRoot = (RootType) contentJaxbContext.get 
				inputStream.close();
				result.add(content.getValue());
			}
			
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	private void prepareIndexMap(List<RootType> contentRootList)
	{
		Map<String,List<ItemType>> itemTypeMap = null;
		
		//visit all file contents
		for(RootType root: contentRootList)
		{
			//each file can have multiple feature sets
			for(ItemsType items: root.getItems())
			{
				//check if you already have that feature
				//if you have the same feature (name) defined in two different
				//files than they will be appended
				itemTypeMap = valueMap.get(items.getName());
				BaseFeatureRecognizer recognizer = controllerMap.getRecognizer(items.getName());
				
				if(itemTypeMap == null)
				{
					itemTypeMap = new HashMap<String, List<ItemType>>();
					valueMap.put(items.getName(), itemTypeMap);
				}
				
				//now construct the feature value sets
				for(ItemType itemType: items.getItem())
				{
					String itemTypeName = normalize(itemType.getName(), recognizer);
					
					putItemType(itemTypeMap, itemType, itemTypeName);
					
					//put the alternate names into the map
					if(itemType.getAlternatenames() != null)
					{
						for(String str: itemType.getAlternatenames().getName())
						{
							putItemType(itemTypeMap, itemType, normalize(str, recognizer));
						}
					}
				}
			}
		}
	}

	private void putItemType(Map<String, List<ItemType>> itemTypeMap, ItemType itemType, String itemTypeName)
	{
		List<ItemType> tmpList = itemTypeMap.get(itemTypeName);
		if(tmpList == null)
		{
			tmpList = new ArrayList<ItemType>();	
		}
		tmpList.add(itemType);
		//put the main name into the map
		itemTypeMap.put(itemTypeName, tmpList);
	}
	
	public Map<String, List<ItemType>> getValueMap(String featureName)
	{
		return valueMap.get(featureName);
	}

	protected String normalize(String str, BaseFeatureRecognizer recognizer)
	{
		if(recognizer == null)
		{
			str = str.replaceAll("[\\p{Punct}]", "");
			str = str.replaceAll("[\\s]", "");
			str = StringUtil.convertTurkishCharacter(str);
			str = str.toLowerCase(Locale.ENGLISH);
		}
		else
		{
			str = recognizer.normalize(str);
		}
		
		return str;
	}

	/*
		JAXBContext jaxbContext = JAXBContext.newInstance("com.karniyarik.recognizer.xml.item");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
		marshaller.marshal(root,new FileOutputStream("jaxbOutput.xml"));
	 */
}
