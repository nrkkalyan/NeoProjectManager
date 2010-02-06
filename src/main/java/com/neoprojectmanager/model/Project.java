package com.neoprojectmanager.model;

import static org.apache.commons.lang.StringUtils.*;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

import com.neoprojectmanager.model.TaskImpl.RELATIONSHIP;

public class Project extends NodeWrapper {
	enum PROPERTY {
		NAME
	}

	enum RELATIONSHIP implements RelationshipType {
		INCLUDE_PROJECT, INCLUDE_TASK, HOLD_RESOURCE
	}

	/**
	 * TODO Check that name is unique; needs indexing...
	 * 
	 * @param name
	 * @param gdbs
	 */
	public Project(String name, GraphDatabaseService gdbs) {
		super(gdbs.createNode(), gdbs);
		setName(name);
	}

	Project(Node node, GraphDatabaseService gdbs) {
		super(node, gdbs);
	}

	public TaskImpl createTask(String name) {
		Transaction tx = this.gdbs.beginTx();
		try {
			TaskImpl n = new TaskImpl(this.gdbs.createNode(), this.gdbs);
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
			if (r != null && r.getStartNode().getId() == this.getId()) {
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
		Transaction tx = gdbs.beginTx();
		try {
			Project subProject = new Project(name, gdbs);
			createRelationShip(this, RELATIONSHIP.INCLUDE_PROJECT, subProject);
			tx.success();
			return subProject;
		} finally {
			tx.finish();
		}
	}

	public TaskRelationship allocateResource(Resource resource) {
		return createRelationShip(this, RELATIONSHIP.HOLD_RESOURCE, resource);
	}

	public Iterator<Project> getSubProjects() {
		return new Iterator<Project>() {
			private final Iterator<Node> iterator = traverse(
					Order.BREADTH_FIRST, StopEvaluator.DEPTH_ONE,
					ReturnableEvaluator.ALL_BUT_START_NODE,
					RELATIONSHIP.INCLUDE_PROJECT, Direction.OUTGOING).iterator();

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public Project next() {
				// Should return an immutable object?
				Node nextNode = iterator.next();
				return new Project(nextNode, gdbs);
			}

			public void remove() {
				iterator.remove();
			}
		};
	}
}
