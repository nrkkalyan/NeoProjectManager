package com.neoprojectmanager.controller;

import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class TaskCtrl extends HttpServlet {
//
//	
//	private static final String MM_DD_YYYY = "MM/dd/yyyy";
//	private static final String INSERT = "insert";
//	private static final String DELETE = "delete";
//	private static final String UPDATE = "update";
//	private static final String DETAILS = "details";
//	private static final String ACTION = "action";
//	private static final String NAME = "name";
//	private static final String DD_MM_YYYY = "dd/MM/yyyy";
//	private static final String END_DATE = "endDate";
//	private static final String START_DATE = "startDate";
//	private static final String ID = "id";
//
//	private Factory getFactory() {
//		return (Factory) getServletContext().getAttribute(IS_A_RESOURCE.NEO4J_INSTANCE.name());
//	}
//
//	private void details(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//
//		Long nodeId = null;
//		try {
//			nodeId = Long.parseLong(request.getParameter(ID));
//		} catch (NumberFormatException e) {
//			getServletContext().log("Error parsing node id: ", e);
//			return;
//		}
//
//		if (nodeId <= 0) {
//			getServletContext().log("The node id should be a positive integer");
//			return;
//		}
//
//		Task taskImpl = null;
//		Factory factory = getFactory();
//		if (nodeId != null) {
//			try {
//				taskImpl = factory.getTaskImplById(nodeId);
//			} catch (NotFoundException e) {
//				response.sendError(410, "Node with id " + nodeId
//						+ "does not exist.");
//				return;
//			}
//
//			response.setContentType("application/json");
//			PrintWriter out = response.getWriter();
//			out.println(taskImplToJSONString(taskImpl));
//			out.close();
//			return;
//
//		}
//	}
//
//	private void update(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		Factory tf = getFactory();
//		Task updateTaskImpl;
//		if (isLong(request.getParameter(ID)))
//			updateTaskImpl = tf.getTaskImplById(Long
//					.parseLong(request.getParameter(ID)));
//		else
//			throw new ServletException("The passed id is not valid.");
//
//		String name = request.getParameter(NAME);
//		if (!isEmpty(name))
//			updateTaskImpl.setName(name);
//
//		String startDate = request.getParameter(START_DATE);
//		String endDate = request.getParameter(END_DATE);
//		if (!isEmpty(startDate) && !isEmpty(endDate))
//			try {
//				Date newStartDate = new SimpleDateFormat(DD_MM_YYYY)
//						.parse(String.valueOf(startDate));
//				Date newEndDate = new SimpleDateFormat(DD_MM_YYYY).parse(String
//						.valueOf(endDate));
//				updateTaskImpl
//						.setDurationInMinutes((int) (newEndDate.getTime() - newStartDate
//								.getTime()) / 1000 / 60);
//				updateTaskImpl.setStartDate(newStartDate);
//			} catch (ParseException e) {
//				response.sendError(410, "ERROR PARSING THE DATES");
//				return;
//			}
//
//		response.getWriter().write("UPDATE SUCCESSFUL");
//		response.getWriter().close();
//		return;
//	}
//
//	@Override
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String action = request.getParameter(ACTION);
//		if (DETAILS.equalsIgnoreCase(action)) {
//			details(request, response);
//		}
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		super.doPost(req, resp); // WHAT IS THIS FOR?!?
//		try {
//			String action = req.getParameter(ACTION);
//
//			/*
//			 * UPDATE TASK
//			 */
//			if (UPDATE.equalsIgnoreCase(action)) {
//				update(req, resp);
//			}
//
//			/*
//			 * DELETE TASK
//			 */
//			else if (DELETE.equalsIgnoreCase(action)) {
//				delete(req, resp);
//			}
//
//			/*
//			 * INSERT TASK
//			 */
//			else if (action.equalsIgnoreCase(INSERT)) {
//				insert(req, resp);
//				return;
//			}
//
//		} catch (NotFoundException e) {
//			resp.sendError(410, e.getMessage());
//		} finally {
//			if (!resp.isCommitted())
//				getServletContext()
//						.log(
//								"WARNING: IT SEEMS ONE OF THE METHODS DID NOT CLOSE THE STREAM...");
//		}
//	}
//
//	private void insert(HttpServletRequest req, HttpServletResponse resp)
//			throws IOException {
//		Factory tf = getFactory();
//
//		// create
//		String name = req.getParameter(NAME);
//		String projectId = req.getParameter();
//		Task insertTaskImpl = tf.createTask(name);
//
//		// get and set startDate
//		String startDate = req.getParameter(START_DATE);
//		if (!isEmpty(startDate))
//			try {
//				Date newStartDate = new SimpleDateFormat(MM_DD_YYYY)
//						.parse(String.valueOf(startDate));
//				insertTaskImpl.setStartDate(newStartDate);
//			} catch (ParseException e) {
//				resp.sendError(410, "ERROR PARSING THE START DATE");
//				return;
//			}
//		// get and set endDate
//		String endDate = req.getParameter(END_DATE);
//
//		if (!isEmpty(endDate))
//			try {
//				Date date = new SimpleDateFormat(MM_DD_YYYY).parse(String
//						.valueOf(endDate));
//				insertTaskImpl
//						.setDurationInMinutes((int) (date.getTime() - insertTaskImpl
//								.getStartDate().getTime()) / 1000 / 60);
//			} catch (ParseException e) {
//				resp.sendError(410, "ERROR PARSING THE START DATE");
//				return;
//			}
//
//		resp.getWriter().write("UPDATE SUCCESSFUL");
//		resp.getWriter().close();
//		return;
//	}
//
//	public void delete(HttpServletRequest req, HttpServletResponse resp)
//			throws IOException {
//		Factory tf = (Factory) getServletContext().getAttribute(
//				IS_A_RESOURCE.NEO4J_INSTANCE.name());
//		long taskID = Long.valueOf(req.getParameter(ID));
////		tf.removeTaskById(taskID);
//		resp.getWriter().write("DELETE SUCCESSFUL");
//		resp.getWriter().close();
//		return;
//	}
}
