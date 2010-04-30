/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

package com.neoprojectmanager.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class DummyJSON extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		
		out.println("[{" + //
				"\"id\":10," + //
				"\"name\":\"Complete the prototype till MONDAY!!!\"," + //
				"\"startDate\":\"\"," + //
				"\"endDate\":\"\"," + //
				"\"color\":\"0000ff\"," + //
				"\"link\":\"\"," + //
				"\"isMilestone\":0," + //
				"\"resourceName\":\"\"," + //
				"\"percentage\":0," + //
				"\"group\":1," + //
				"\"parent\":0," + //
				"\"isOpen\":1," + //
				"\"depend\":[]}," + //
				
				"{\"id\":20," + //
				"\"name\":\"Design and model\"," + //
				"\"startDate\":\"9/11/2008\"," + //
				"\"endDate\":\"9/15/2008\"," + //
				"\"color\":\"0000ff\"," + //
				"\"link\":\"http://www.google.com\"," + //
				"\"isMilestone\":0," + //
				"\"resourceName\":\"rich\"," + //
				"\"percentage\":10," + //
				"\"group\":0," + //
				"\"parent\":10," + //
				"\"isOpen\":1," + //
				"\"depend\":[]," + //
				"\"caption\":\"brian\"}," + //
				
				"{\"id\":30," + //
				"\"name\":\"Implement\"," + //
				"\"startDate\":\"9/19/2008\"," + //
				"\"endDate\":\"9/21/2008\"," + //
				"\"color\":\"0000ff\"," + //
				"\"link\":\"\"," + //
				"\"isMilestone\":0," + //
				"\"resourceName\":\"shlomy\"," + //
				"\"percentage\":50," + //
				"\"group\":0," + //
				"\"parent\":10," + //
				"\"isOpen\":1," + //
				"\"depend\":[20]," + //
				"\"caption\":\"shlomy\"}," + //
				
				"{\"id\":40,\"name\":\"present\"," + //
				"\"startDate\":\"9/23/2008\"," + //
				"\"endDate\":\"9/24/2008\"," + //
				"\"color\":\"0000ff\"," + //
				"\"link\":\"\"," + //
				"\"isMilestone\":0," + //
				"\"resourceName\":\"shlomy\"," + //
				"\"percentage\":30," + //
				"\"group\":0," + //
				"\"parent\":0," + //
				"\"isOpen\":1," + //
				"\"depend\":[20,30]," + //
				"\"caption\":\"shlomy\"}]");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
