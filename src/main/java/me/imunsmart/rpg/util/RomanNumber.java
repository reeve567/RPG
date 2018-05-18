package me.imunsmart.rpg.util;

import java.util.TreeMap;

public class RomanNumber {
	
	public static String toRoman(int number) {
		return new RomanNumerals(number).toString();
	}
	
	public static int fromRoman(String number) {
		return new RomanNumerals(number).toInt();
	}
	
}