package com.neoprojectmanager.model;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.TraversalPosition;
import org.neo4j.graphdb.Traverser.Order;

public class Project extends NodeWrapper {
	enum PROPERTY {
		NAME
	}

	enum RELATIONSHIP implements RelationshipType {
		HOLD_RESOURCE, INCLUDE_PROJECT, INCLUDE_TASK
	}

	protected Project(Node node, GraphDatabaseService gdbs) {
		super(node, gdbs);
	}

	/**
	 * TODO Check that name is unique; needs indexing...
	 * 
	 * @param name
	 * @param gdbs
	 */
	public Project(String name, GraphDatabaseService gdbs) {
		super(gdbs);
		setName(name);
	}

	public ProjectRelationship allocateResource(Resource resource) {
		return new ProjectRelationship(createRelationShip(this,
				RELATIONSHIP.HOLD_RESOURCE, resource), gdbs);
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

	public Task createTask(String name) {
		Transaction tx = this.gdbs.beginTx();
		try {
			Task n = new Task(name, this.gdbs);
			createRelationShip(this, RELATIONSHIP.INCLUDE_TASK, n);
			tx.success();
			return n;
		} finally {
			tx.finish();
		}
	}

	public String getName() {
		return (String) getPropertyOrNull(PROPERTY.NAME);
	}

	/**
	 * Return the subProjects of this project.
	 * 
	 * @return
	 */
	public Iterator<Project> getSubProjects() {
		return getNodeWrapperIterator(Project.class,
				Order.BREADTH_FIRST, StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE, new RelTup(
						RELATIONSHIP.INCLUDE_PROJECT, Direction.OUTGOING));
	}

	/**
	 * Returns the tasks associated with this project.
	 * 
	 * @return
	 */
	public Iterator<Task> getTasks() {
		return getNodeWrapperIterator(Task.class, Order.BREADTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE, new RelTup(
						RELATIONSHIP.INCLUDE_TASK, Direction.OUTGOING));
	}

	/**
	 * Return not only the dasks attached to this project but the tasks attached
	 * to the subprojects too.
	 * 
	 * @return
	 */
	public Iterator<Task> getTasksAndSubtasks() {
		return getNodeWrapperIterator(Task.class,
				Order.BREADTH_FIRST, StopEvaluator.END_OF_GRAPH,
				new IncludedTaskEvaluator(), new RelTup(
						RELATIONSHIP.INCLUDE_TASK, Direction.OUTGOING),
				new RelTup(RELATIONSHIP.INCLUDE_PROJECT, Direction.OUTGOING));
	}

	/**
	 * Returns the nodes that have an INCLUDE_TASK relationship INCOMING
	 * @author xan
	 * 
	 */
	class IncludedTaskEvaluator implements ReturnableEvaluator {
		public boolean isReturnableNode(TraversalPosition position) {
			return position.lastRelationshipTraversed() != null
					&& position
							.lastRelationshipTraversed()
							.isType(
									com.neoprojectmanager.model.Project.RELATIONSHIP.INCLUDE_TASK);
		}
	}

	public boolean hasResources() {
		return hasRelationship(RELATIONSHIP.HOLD_RESOURCE, Direction.OUTGOING);
	}

	public boolean hasSubProjects() {
		return hasRelationship(RELATIONSHIP.INCLUDE_PROJECT, Direction.OUTGOING);
	}

	public boolean hasTasks() {
		return hasRelationship(RELATIONSHIP.INCLUDE_TASK, Direction.OUTGOING);
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
}
