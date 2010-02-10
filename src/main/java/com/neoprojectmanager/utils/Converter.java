package com.neoprojectmanager.utils;

public class Converter {

	private final static String[] zeroPadding = { "0", "00", "000", "0000", "00000" };
	
	public static String getHexColor(int i) {
		StringBuilder sb = new StringBuilder(6);
		sb.append(Integer.toHexString(Math.abs(i % 0xffffff)));
		if (sb.length() < 6)
			sb.insert(0, zeroPadding[6 - sb.length() - 1]);
		return sb.toString();
	}

	public static long inMinutes(long minutes) {
		return minutes;
	}

	public static long inHours(long minutes) {
		return minutes / 60;
	}

	public static long inDays(long minutes) {
		return minutes / 60 / 24;
	}

	public static void main(String[] args) {
		System.out.println(Converter.getHexColor(Integer.decode("#f70c53")));
	}
}
