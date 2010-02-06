package com.neoprojectmanager.model;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class TaskRelationship extends PropertyContainerWrapper {

	private Relationship relationship;

	protected TaskRelationship(Relationship r, GraphDatabaseService gdbs) {
		super(r, gdbs);
		this.relationship = r;
		this.gdbs = gdbs;
		setCreationTime();
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
