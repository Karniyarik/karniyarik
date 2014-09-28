package com.karniyarik.common.vo;

public enum CurrencyType {

	TL("TL", "TL", "TL"),
	EURO("€", "EUR", "EURO"),
	DOLLAR("$", "USD", "DOLAR"),
	AED("AED", "AED", ""),
	AUD("AUD", "AUD", ""),
	BHD("BHD", "BHD", ""),
	BRL("BRL", "BRL", ""),
	CAD("CAD", "CAD", ""),
	CHF("CHF", "CHF", ""),
	DKK("DKK", "DKK", ""),
	FJD("FJD", "FJD", ""),
	GBP("GBP", "GBP", ""),
	HKD("HKD", "HKD", ""),
	JOD("JOD", "JOD", ""),
	JPY("JPY", "JPY", ""),
	KWD("KWD", "KWD", ""),
	MYR("MYR", "MYR", ""),
	NOK("NOK", "NOK", ""),
	NZD("NZD", "NZD", ""),
	OMR("OMR", "OMR", ""),
	PLN("PLN", "PLN", ""),
	QAR("QAR", "QAR", ""),
	RUB("RUB", "RUB", ""),
	SAR("SAR", "SAR", ""),
	SEK("SEK", "SEK", ""),
	SGD("SGD", "SGD", ""),
	THB("THB", "THB", ""),
	TWD("TWD", "TWD", ""),
	ZAR("ZAR", "ZAR", "");
	
	private String symbol;
	private String code;
	private String name;

	private CurrencyType(String symbol, String code, String name) {
		this.symbol = symbol;
		this.code = code;
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
