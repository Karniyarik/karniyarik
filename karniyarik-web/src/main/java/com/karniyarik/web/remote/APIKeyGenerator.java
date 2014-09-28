package com.karniyarik.web.remote;

import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class APIKeyGenerator
{
	public APIKeyGenerator()
	{
		
	}
	
	public String generateKey(String name, Date date)
	{
		String result = null;
		
		try
		{
			KeyGenerator instance = KeyGenerator.getInstance("AES");
			SecureRandom random = new SecureRandom((name + date.toString()).getBytes());
			instance.init(256,random);			
			SecretKey key = instance.generateKey();
			result = byteToString(key.getEncoded()).toUpperCase();
		}
		catch (Throwable e)
		{
			throw new KarniyarikAPIException(e);
		}
		return result;
	}
	
	public String byteToString(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder();
		
		for(byte b: bytes)
		{
			//sb.append(Character.forDigit(b & 240 >> 4, 32));
			sb.append(Character.forDigit(b & 15, 16));
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		String generateKey = new APIKeyGenerator().generateKey("bulut.akisik@ngtyazilim.com", new Date());
		System.out.println(generateKey);
	}
}
