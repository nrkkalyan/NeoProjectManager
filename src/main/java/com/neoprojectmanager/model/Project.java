package com.neoprojectmanager.model;

import static org.apache.commons.lang.StringUtils.*;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

public class Project extends NodeWrapper {
	enum PROPERTY {
		NAME
	}

	enum RELATIONSHIP implements RelationshipType {
		INCLUDE_PROJECT, INCLUDE_TASK, HOLD_RESOURCE
	}

	/**
	 * TODO Check that name is unique; needs indexing...
	 * @param name
	 * @param gdbs
	 */
	public Project(String name, GraphDatabaseService gdbs) {
		super(gdbs.createNode(), gdbs);
		setName(name);
	}

	public Task createTask(String name) {
		Transaction tx = this.gdbs.beginTx();
		try {
			Task n = new Task(this.gdbs.createNode(), this.gdbs);
			n.setName(name);
			tx.success();
			return n;
		} finally {
			tx.finish();
		}
	}

	/**
	 * The scenario of this method is that a task is included by one and only
	 * one project. So I take the incoming relationship with a project. If such
	 * a relationship is present and the including project is the current one,
	 * then I delete the relationship with the project and the task itself.
	 * 
	 * @param id
	 */
	public void removeTaskById(final long id) {
		Transaction tx = gdbs.beginTx();
		try {
			Node task = gdbs.getNodeById(id);
			Relationship r = task.getSingleRelationship(
					RELATIONSHIP.INCLUDE_TASK, Direction.INCOMING);
			if (r != null && r.getStartNode().getId() == this.node.getId()) {
				r.delete();
				task.delete();
			}
			tx.success();
		} finally {
			tx.finish();
		}
	}

	public void setName(String value) {
		if (isBlank(value))
			throw new IllegalArgumentException();
		setProperty(PROPERTY.NAME, value);
	}

	public String getName() {
		return (String) getPropertyOrNull(PROPERTY.NAME);
	}

	public Project createSubProject(String name) {
		Project subProject = new Project(name, gdbs);
		TaskRelationship r = createRelationShip(this, RELATIONSHIP.INCLUDE_PROJECT, subProject);
		return subProject;
	}

	public TaskRelationship allocateResource(Resource resource) {
		return createRelationShip(this, RELATIONSHIP.HOLD_RESOURCE, resource);
	}

}
