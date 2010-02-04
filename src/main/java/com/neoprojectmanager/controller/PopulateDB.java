package com.neoprojectmanager.controller;

import static com.neoprojectmanager.utils.Formatting.taskArrayToJSON;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neoprojectmanager.model.Factory;
import com.neoprojectmanager.model.Task;

@SuppressWarnings("serial")
public class PopulateDB extends HttpServlet {

	enum ACTION {
		POPULATE, CLEAR, RESET, JSON
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ACTION action = ACTION.valueOf(request.getParameter("action"));
		response.setContentType("text/plain");
		Factory tf = (Factory) getServletContext().getAttribute(
				RESOURCE.NEO4J_INSTANCE.name());
		
		out.append("NEO4J INSTANCE ON " + tf.getDbFolder() + "\n");

		switch (action) {
		case POPULATE:
			tf.populateDB();
			out.append("POPULATED WITH FOLLOWING ELEMENTS:\n");
			printJSON(out, tf);
			return;
		case CLEAR:
			tf.clearDB();
			out.append("CLEARED");
			return;
		case RESET:
			tf.clearDB();
			tf.populateDB();
			out.append("POPULATED WITH FOLLOWING ELEMENTS:\n");
			printJSON(out, tf);
			return;
		case JSON:
			printJSON(out, tf);
			return;
		default:
			out.append("ACTION '" + action + "' UNKNOWN\n");
			out.append("SUPPORTED ACTIONS:\n" + ACTION.values());
			return;
		}
	}

	private PrintWriter printJSON(PrintWriter out, Factory tf) {
		Iterator<Task> it = tf.getAllNodes();
		return out.append(taskArrayToJSON(it).toString(3));
	}

	public void init() throws ServletException {
		// TODO REMOVE WHEN TESTED
		String dbPath = getServletContext().getRealPath(
				"NE04J-GANTT-DB-2010-01-29");

		Factory tf = new Factory(dbPath);

		getServletContext().setAttribute(RESOURCE.NEO4J_INSTANCE.name(), tf);
		super.init();
	}

}
