package com.karniyarik.search.deneme;
import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.Kelime;

import com.karniyarik.ir.analyzer.ZemberekFactorySolr;

public class ZemberekUserThread implements Runnable {
	private String query = null;
	private int count = 0;

	public ZemberekUserThread(String query, int count) {
		this.query = query;
		this.count =count;
	}
	
	@Override
	public void run() {
		
		Zemberek zemberek = ZemberekFactorySolr.getInstance().create();

		Kelime[] asciidenTurkceyeCevirilmisKelimeler = zemberek.asciiCozumle(query);

		String root = "";
		
		for (Kelime cevirilmisKelime : asciidenTurkceyeCevirilmisKelimeler) {
			root += cevirilmisKelime.kok().icerik();
			root += " ";
		}
		
		System.out.println(count + "-" +  root);
	}
}
