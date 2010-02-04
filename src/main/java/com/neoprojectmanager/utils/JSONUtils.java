package com.neoprojectmanager.utils;

public class JSONUtils {
	public static String j(String tag, Object value) {
		
		return "\""+tag+"\":"+"\""+value+"\"";
	}
}
