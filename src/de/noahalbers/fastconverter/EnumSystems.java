package de.noahalbers.fastconverter;

import java.util.function.Function;

import de.noahalbers.fastconverter.utils.NumberUtils;

public enum EnumSystems {

	ASCII("ASCII",NumberUtils.getInstance()::decToPlainText,NumberUtils.getInstance()::plainTextToDec,"."),
	DECIMAL("Dec",NumberUtils.getInstance()::decToDec,NumberUtils.getInstance()::decToDec,"\\d"),
	BINARY("Bin",NumberUtils.getInstance()::decToBin,NumberUtils.getInstance()::binToDec,"[01]"),
	OCTAL("Oct",NumberUtils.getInstance()::decToOct,NumberUtils.getInstance()::octToDec,"[0-7]"),
	HEXADECIMAL("Hex",NumberUtils.getInstance()::decToHex,NumberUtils.getInstance()::hexToDec,"[\\da-fA-F]"),
	BASE64("Base64",NumberUtils.getInstance()::decToBase64,NumberUtils.getInstance()::base64ToDec,".*");
	
	private String name,validRegex;
	private Function<Long[],String> inconverter;
	private Function<String[], Long[]> outconverter;
	
	private EnumSystems(String name,Function<Long[],String> inconverter,Function<String[], Long[]> outconverter,String validRegex) {
		this.name = name;
		this.inconverter = inconverter;
		this.outconverter = outconverter;
		this.validRegex = validRegex;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Function<Long[], String> getInconverter() {
		return this.inconverter;
	}
	public Function<String[], Long[]> getOutconverter() {
		return this.outconverter;
	}
	
	public String getValidRegex() {
		return this.validRegex;
	}
}
