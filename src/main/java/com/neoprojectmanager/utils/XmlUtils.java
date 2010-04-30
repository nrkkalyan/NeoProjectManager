/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

package com.neoprojectmanager.utils;

public class XmlUtils {

	public static String t(String tag, Object value) {
		return "<" + tag + ">" + value + "</" + tag + ">";
	}

}
