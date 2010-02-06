package com.neoprojectmanager.model;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Iterator;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Factory {

	private GraphDatabaseService gdbs = null;
	private String dbFolder = null;

	public Factory(String dbFolder) {
		this.dbFolder = dbFolder;
		if (gdbs == null)
			this.gdbs = new EmbeddedGraphDatabase(this.dbFolder);
	}

	public Iterator<Task> getAllNodes() {
		return new Iterator<Task>() {
			private final Iterator<Node> iterator = gdbs.getAllNodes()
					.iterator();

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public Task next() {
				Node nextNode = iterator.next();
				return new TaskImpl(nextNode, gdbs);
			}

			public void remove() {
			}
		};
	}

	public void close() {
		this.gdbs.shutdown();
		this.gdbs = null;
	}

	public Task createTaskImpl(String name) {
		Transaction tx = this.gdbs.beginTx();
		try {
			Task n = new TaskImpl(name, this.gdbs);
			tx.success();
			return n;
		} finally {
			tx.finish();
		}
	}

	public Project createProject(String name) {
		if (isBlank(name))
			throw new IllegalArgumentException("Name can not be blank");
		Transaction tx = this.gdbs.beginTx();
		try {
			Project n = new Project(name, this.gdbs);
			tx.success();
			return n;
		} finally {
			tx.finish();
		}
	}

	public Task getTaskImplById(long id) {
		Transaction tx = this.gdbs.beginTx();
		try {
			Task t = new TaskImpl(this.gdbs.getNodeById(id), this.gdbs);
			tx.success();
			return t;
		} finally {
			tx.finish();
		}
	}

	public Project getProjectById(long id) {
		Transaction tx = this.gdbs.beginTx();
		try {
			Project p = new Project(this.gdbs.getNodeById(id), this.gdbs);
			tx.success();
			return p;
		} finally {
			tx.finish();
		}
	}

	public void clearDB() {
		Transaction tx = gdbs.beginTx();
		try {
			for (Node n : gdbs.getAllNodes()) {
				for (Relationship r : n.getRelationships()) {
					r.delete();
				}
				n.delete();
			}
			tx.success();
		} finally {
			tx.finish();
		}
	}

	public Transaction beginTx() {
		return gdbs.beginTx();
	}

	public void populateDB() {
		Transaction tx = gdbs.beginTx();
		try {
			Project project1 = createProject("Project 1");
			TaskImpl taskImpl1 = project1.createTaskImpl("TaskImpl 1");
			Task taskImpl2 = project1.createTaskImpl("TaskImpl 2");
			taskImpl1.setDurationInMinutes(60 * 24 * 10);
			taskImpl2.setDurationInMinutes(60 * 24 * 6);
			taskImpl2.addDependentOn(taskImpl1);
			tx.success();
		} finally {
			tx.finish();
		}
	}

	public String getDbFolder() {
		return dbFolder;
	}
}
