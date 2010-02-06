package com.neoprojectmanager.utils;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.neoprojectmanager.model.Task;

public class Formatting {

	private static JsonConfig taskNodeJsonConfig = new JsonConfig();
	static {
		taskNodeJsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor());
	}
	
	public static String domainToJSONString(Task taskImpl) {
		return domainToJSON(taskImpl).toString();
	}
	
	public static JSONArray domainToJSON(Object domain) {
		JSONArray jsonObject = JSONArray.fromObject(domain, taskNodeJsonConfig);
		return jsonObject;
	}
	
}
