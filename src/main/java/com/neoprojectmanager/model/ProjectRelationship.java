/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

package com.neoprojectmanager.model;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;

public class ProjectRelationship extends RelationshipWrapper {
	enum PROPERTY {
	}
	
	protected ProjectRelationship(Relationship r, GraphDatabaseService gdbs) {
		super(r, gdbs);
		setCreationTime();
	}
	
}
