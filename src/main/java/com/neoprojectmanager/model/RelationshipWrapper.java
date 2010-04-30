/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RelationshipWrapper) {
			RelationshipWrapper other = (RelationshipWrapper) obj;
			return this.relationship.equals(other.relationship);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.relationship.hashCode();
	}
}
