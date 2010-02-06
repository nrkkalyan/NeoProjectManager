package com.neoprojectmanager.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.neoprojectmanager.model.Task;
import com.neoprojectmanager.model.TaskImpl;

public class Formatting {

	private static JsonConfig taskNodeJsonConfig = new JsonConfig();
	static {
		taskNodeJsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor());
	}
	
	public static String toCSV(Iterator<TaskImpl> it) {
		StringBuilder sb = new StringBuilder("");
		while (it.hasNext()) {
			sb.append(it.next().getId());
			if (it.hasNext())
				sb.append(",");
		}
		return sb.toString();
	}

	public static String getNodeIdOrEmptyStr(Task parent) {
		if (parent == null)
			return "";
		else
			return String.valueOf(parent.getId());
	}

	public static String formatOrEmptyString(Date date) {
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		if (date == null)
			return "";
		else
			return df.format(date);
	}

	public static String boolToIntString(Boolean b) {
		if (b == null)
			return "";
		else
			return b ? "1" : "0";
	}
	
	public static String taskImplToJSONString(Task taskImpl) {
		return taskImplToJSON(taskImpl).toString();
	}
	
	public static String taskImplArrayToJSONString(Iterator<Task> task) {
		return taskImplArrayToJSON(task).toString();
	}

	public static JSONObject taskImplToJSON(Task taskImpl) {
		JSONObject jsonObject = JSONObject.fromObject(taskImpl, taskNodeJsonConfig);
		return jsonObject;
	}
	
	public static JSONArray taskImplArrayToJSON(Iterator<Task> task) {
		JSONArray jsonObject = JSONArray.fromObject(task, taskNodeJsonConfig);
		return jsonObject;
	}
	
}
