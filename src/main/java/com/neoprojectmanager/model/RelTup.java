/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

package com.neoprojectmanager.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;

public class RelTup {
	private RelationshipType r;
	private Direction d;
	
	public RelTup(RelationshipType r, Direction d) {
		if (r == null || d == null)
			throw new IllegalArgumentException("Null values are not allowed.");
		this.r = r;
		this.d = d;
	}
	
	public RelationshipType getRelationship() {
		return r;
	}

	public Direction getDirection() {
		return d;
	}
}
