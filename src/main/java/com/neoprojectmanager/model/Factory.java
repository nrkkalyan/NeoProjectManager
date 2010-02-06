package com.neoprojectmanager.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.commons.lang.StringUtils.*;
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
				return new Task(nextNode, gdbs);
			}

			public void remove() {
			}
		};
	}

	public void close() {
		this.gdbs.shutdown();
		this.gdbs = null;
	}

	public Task createTask(String name) {
		Transaction tx = this.gdbs.beginTx();
		try {
			Task n = new Task(name, this.gdbs);
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

	public Task getTaskById(long id) {
		Transaction tx = this.gdbs.beginTx();
		try {
			return new Task(this.gdbs.getNodeById(id), this.gdbs);
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
		Project project1 = createProject("Project 1");
		Task task1 = project1.createTask("Task 1");
		Task task2 = project1.createTask("Task 2");
		task1.setDurationInMinutes(60 * 24 * 10);
		task2.setDurationInMinutes(60 * 24 * 6);
		task2.addDependentOn(task1);
	}

	public String getDbFolder() {
		return dbFolder;
	}
}
