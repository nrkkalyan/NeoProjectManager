package com.neoprojectmanager.model;

import java.util.Calendar;
import java.util.Date;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class TaskRelationship extends PropertyContainerWrapper {

	enum PROPERTY {
		CREATED_ON
	}

	private Relationship relationship;

	protected TaskRelationship(Relationship r, GraphDatabaseService gdbs) {
		super(r, gdbs);
		this.relationship = r;
		this.gdbs = gdbs;
		setCreationTime();
	}

	private void setCreationTime() {
		setProperty(PROPERTY.CREATED_ON, Calendar.getInstance()
				.getTimeInMillis());
	}

	private Date getCreationTime() {
		return new Date((Long) getProperty(PROPERTY.CREATED_ON));
	}

	public void delete() {
		Transaction tx = beginTx();
		try {
			relationship.delete();
			relationship = null;
			tx.success();
		} finally {
			tx.finish();
		}
	}
}
