/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

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

	public static int inMinutes(int minutes) {
		return minutes;
	}

	public static int inHours(int hours) {
		return hours * 60;
	}

	public static int inDays(int days) {
		return days * 24 * 60;
	}

	public static void main(String[] args) {
		System.out.println(Converter.getHexColor(Integer.decode("#f70c53")));
	}
}
