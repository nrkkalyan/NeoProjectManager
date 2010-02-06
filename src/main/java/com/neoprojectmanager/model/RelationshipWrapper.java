package com.neoprojectmanager.model;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class RelationshipWrapper extends PropertyContainerWrapper {

	private Relationship relationship;

	RelationshipWrapper(Relationship r, GraphDatabaseService gdbs) {
		super(r, gdbs);
		this.relationship = r;
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
