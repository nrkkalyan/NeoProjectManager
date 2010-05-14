/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

package com.neoprojectmanager.controller;

import com.neoprojectmanager.model.Factory;
import com.neoprojectmanager.model.Project;
import org.neo4j.graphdb.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import static com.neoprojectmanager.utils.Formatting.domainToJSON;

@SuppressWarnings("serial")
public class PopulateDB extends HttpServlet {

	enum ACTION {
		POPULATE, CLEAR, RESET, JSON, NONE
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ACTION action;
        try {
            action = ACTION.valueOf(request.getParameter("action"));
        } catch (Exception e) {
            action = ACTION.NONE;
        }
		response.setContentType("text/plain");
		Factory tf = (Factory) getServletContext().getAttribute(
				RESOURCE.NEO4J_INSTANCE.name());

        out.append("NEO4J INSTANCE ON ").append(tf.getDbFolder()).append("\n");

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
            out.append("ACTION '").append(String.valueOf(action)).append("' UNKNOWN\n");
            out.append("SUPPORTED ACTIONS:\n");
            for (ACTION s: ACTION.values())
                out.append(String.format("   %s\n", s.name()));
			return;
		}
	}

	private void printJSON(PrintWriter out, Factory tf) {
        Transaction t = tf.beginTx();
		Iterator<Project> it = tf.getAllProjects();
		out.append(domainToJSON(it).toString(3));
        t.success();
        t.finish();
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
