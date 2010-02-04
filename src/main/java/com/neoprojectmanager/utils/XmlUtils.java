package com.neoprojectmanager.utils;

public class XmlUtils {

	public static String t(String tag, Object value) {
		return "<" + tag + ">" + value + "</" + tag + ">";
	}

}
