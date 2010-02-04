package com.neoprojectmanager.utils;

public class Converter {

	public static String getHexColor(int i) {
		final String[] zeroPadding = { "0", "00", "000", "0000", "00000" };
		StringBuilder sb = new StringBuilder(6);
		sb.append(Integer.toHexString(Math.abs(i % 0xffffff)));
		if (sb.length() < 6)
			sb.insert(0, zeroPadding[6 - sb.length() - 1]);
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(Converter.getHexColor(Integer.decode("#f70c53")));
	}
}
