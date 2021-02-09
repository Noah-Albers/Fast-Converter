package de.noahalbers.fastconverter.utils;

import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

public class NumberUtils {

	private static NumberUtils instance;
	
	public NumberUtils() {
		instance = this;
	}
	
	public static NumberUtils getInstance() {
		return instance;
	}
	
	/*
	 * Converts binary strings into decimal numbers
	 * */
	public Long[] binToDec(String... bin){
		return Arrays.stream(bin).map(i->Long.valueOf(i,2)).toArray(Long[]::new);
	}
	
	/*
	 * Converts decimal numbers into binary strings
	 * */
	public String decToBin(Long... dec) {
		return Arrays.stream(dec).map(i->Long.toBinaryString(i)).collect(Collectors.joining(" "));
	}
	
	

	/*
	 * Converts octal strings into decimal numbers
	 * */
	public Long[] octToDec(String... oct){
		return Arrays.stream(oct).map(i->Long.valueOf(i,8)).toArray(Long[]::new);
	}
	
	/*
	 * Converts decimal numbers into octal strings
	 * */
	public String decToOct(Long... dec) {
		return Arrays.stream(dec).map(i->Long.toOctalString(i)).collect(Collectors.joining(" "));
	}

	
	
	/*
	 * Converts decimal strings into decimal numbers
	 * */
	public Long[] decToDec(String... dec){
		return Arrays.stream(dec).map(i->Long.valueOf(i)).toArray(Long[]::new);
	}
	
	/*
	 * Converts decimal numbers into decimal strings
	 * */
	public String decToDec(Long... dec) {
		return Arrays.stream(dec).map(i->Long.toString(i)).collect(Collectors.joining(" "));
	}
	
	

	/*
	 * Converts hex strings into decimal numbers
	 * */
	public Long[] hexToDec(String... hex){
		return Arrays.stream(hex).map(i->Long.valueOf(i,16)).toArray(Long[]::new);
	}
	
	/*
	 * Converts decimal numbers into hex strings
	 * */
	public String decToHex(Long... dec) {
		return Arrays.stream(dec).map(i->Long.toHexString(i)).collect(Collectors.joining(" "));
	}

	

	/*
	 * Converts plaintext into decimal numbers
	 * */
	public Long[] plainTextToDec(String... plainText){
		return Arrays.stream(plainText)
				.collect(Collectors.joining(" "))
				.chars()
				.mapToObj(i->(long)i)
				.toArray(Long[]::new);
	}
	
	/*
	 * Converts decimal numbers into plaintext
	 * */
	public String decToPlainText(Long... dec) {
		return Arrays.stream(dec)
				.map(i->String.valueOf((char)i.intValue()))
				.collect(Collectors.joining(""));
	}
	
	
	
	/*
	 * Converts base64 into ascii numbers
	 * */
	public Long[] base64ToDec(String... plainText){
		try {
			return this.plainTextToDec(new String(Base64.getDecoder().decode(plainText[0])));
		} catch (Exception e) {
			return new Long[0];
		}
	}
	
	/*
	 * Converts decimal numbers into a base64 string
	 * */
	public String decToBase64(Long... dec) {
		return Base64.getEncoder().encodeToString(this.decToPlainText(dec).getBytes());
	}
}
