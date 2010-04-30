package com.neoprojectmanager.utils;

import com.neoprojectmanager.model.Task;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

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
        return JSONArray.fromObject(domain, taskNodeJsonConfig);
	}
	
}
