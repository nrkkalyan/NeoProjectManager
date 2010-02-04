package com.neoprojectmanager.model;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

class NodeWrapper extends PropertyContainerWrapper {

	protected Node node;

	NodeWrapper(GraphDatabaseService gdbs) {
		super(gdbs.createNode(), gdbs);
		this.node = (Node) super.propertyContainer;
	}
	
	NodeWrapper(Node node, GraphDatabaseService gdbs) {
		super(node, gdbs);
		this.node = node;
	}

	protected TaskRelationship createRelationShip(NodeWrapper from,
			RelationshipType type, NodeWrapper to) {
		Transaction tx = gdbs.beginTx();
		try {
			TaskRelationship tr = new TaskRelationship(from.node
					.createRelationshipTo(to.node, type), gdbs);
			tx.success();
			return tr;
		} finally {
			tx.finish();
		}
	}
}
