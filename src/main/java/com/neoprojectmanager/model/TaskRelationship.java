package com.neoprojectmanager.model;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;

public class TaskRelationship extends RelationshipWrapper {

	protected TaskRelationship(Relationship r, GraphDatabaseService gdbs) {
		super(r, gdbs);
	}

}
