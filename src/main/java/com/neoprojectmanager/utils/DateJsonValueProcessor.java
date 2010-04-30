package com.neoprojectmanager.utils;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateJsonValueProcessor implements JsonValueProcessor {

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	private Object process(Object value, JsonConfig jsonConfig) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		if (value != null)
			return df.format((Date) value);
		else
			return null;
	}
}