package com.neoprojectmanager.model;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser.Order;

import com.neoprojectmanager.model.Project.RELATIONSHIP;

public class TaskImpl extends NodeWrapper implements Task {
	enum PROPERTY {
		NAME, CREATED_ON, START_DATE, DURATION_IN_MINUTES, COST
	}
	
	enum RELATIONSHIP implements RelationshipType {
		DEPEND_ON
	}
	
	TaskImpl(Node node, GraphDatabaseService gdbs) {
		super(node, gdbs);
	}
	
	TaskImpl(String name, GraphDatabaseService gdbs) {
		super(gdbs);
		setName(name);
	}

	public TaskRelationship addDependentOn(Task other) {
		return createRelationShip((TaskImpl) other, RELATIONSHIP.DEPEND_ON,
				this);
	}

	public TaskRelationship addDependent(Task other) {
		return createRelationShip(this, RELATIONSHIP.DEPEND_ON,
				(TaskImpl) other);
	}

	public Iterator<Task> getDependentTaskImpls() {
		return new Iterator<Task>() {
			private final Iterator<Node> iterator = traverse(
					Order.BREADTH_FIRST, StopEvaluator.DEPTH_ONE,
					ReturnableEvaluator.ALL_BUT_START_NODE,
					RELATIONSHIP.DEPEND_ON, Direction.OUTGOING)
					.iterator();

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public Task next() {
				Node nextNode = iterator.next();
				return new TaskImpl(nextNode, gdbs);
			}

			public void remove() {
				iterator.remove();
			}
		};
	}

	public boolean hasDependents() {
		return hasRelationship(RELATIONSHIP.DEPEND_ON,
				Direction.OUTGOING);
	}
	

	public boolean hasDependencies() {
		return hasRelationship(RELATIONSHIP.DEPEND_ON,
				Direction.OUTGOING);
	}

	public void setName(String name) {
		if (isBlank(name))
			throw new IllegalAccessError("Name can not be blank");
		setProperty(PROPERTY.NAME, name);
	}

	public String getName() {
		return (String) getPropertyOrNull(PROPERTY.NAME);
	}

	public void setStartDate(Date value) {
		if (value != null)
			setOrRemoveProperty(PROPERTY.START_DATE, value.getTime());
		else
			removeProperty(PROPERTY.START_DATE);
	}

	public Date getStartDate() {
		Calendar startDate = GregorianCalendar.getInstance();
		Long timeInMillis = (Long) getPropertyOrNull(PROPERTY.START_DATE);
		if (timeInMillis == null)
			return null;
		startDate.setTimeInMillis(timeInMillis);
		return startDate.getTime();
	}

	/**
	 * @param durationInMinutes
	 */
	public void setDurationInMinutes(Integer durationInMinutes)
			throws IllegalArgumentException {
		if (durationInMinutes != null && durationInMinutes < 0)
			throw new IllegalArgumentException("Duration must be positive.");
		setOrRemoveProperty(PROPERTY.DURATION_IN_MINUTES, durationInMinutes);
	}

	public Integer getDurationInMinutes() {
		return (Integer) getPropertyOrNull(PROPERTY.DURATION_IN_MINUTES);
	}

	public Task getProject() {
		Transaction tx = this.gdbs.beginTx();
		try {
			Relationship r = getSingleRelationship(
					Project.RELATIONSHIP.INCLUDE_TASK, Direction.INCOMING);
			if (r == null)
				return null;
			Node n = r.getStartNode();
			tx.success();
			return new TaskImpl(n, this.gdbs);
		} finally {
			tx.finish();
		}
	}

	public Date getEndDate() {
		if (this.getStartDate() == null)
			return null;
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(this.getStartDate());
		endDate.add(Calendar.MINUTE, this.getDurationInMinutes());
		return endDate.getTime();
	}

	public String toString() {
		return "TaskImpl@" + getId();
	}

}
