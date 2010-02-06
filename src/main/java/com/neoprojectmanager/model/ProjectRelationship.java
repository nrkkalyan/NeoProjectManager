package com.neoprojectmanager.model;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;

public class ProjectRelationship extends RelationshipWrapper {
	
	protected ProjectRelationship(Relationship r, GraphDatabaseService gdbs) {
		super(r, gdbs);
	}
	
}
