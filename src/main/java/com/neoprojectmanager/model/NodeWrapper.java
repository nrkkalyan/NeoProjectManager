package com.neoprojectmanager.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

class NodeWrapper extends PropertyContainerWrapper {

	Node node;

	NodeWrapper(GraphDatabaseService gdbs) {
		super(gdbs);
		this.node = (Node) super.propertyContainer;
	}

	NodeWrapper(Node node, GraphDatabaseService gdbs) {
		super(node, gdbs);
		this.node = node;
	}

	Relationship createRelationShip(NodeWrapper from, RelationshipType type,
			NodeWrapper destination) {
		Transaction tx = gdbs.beginTx();
		try {
			Relationship tr = from.getNode().createRelationshipTo(destination.getNode(),
					type);
			tx.success();
			return tr;
		} finally {
			tx.finish();
		}
	}

	public Node getNode() {
		return this.node;
	}

	public long getId() {
		return node.getId();
	}

	Traverser traverse(Order order, StopEvaluator stopEvaluator,
			ReturnableEvaluator returnableEvaluator,
			RelationshipType relationship, Direction direction) {

		return node.traverse(order, stopEvaluator, returnableEvaluator,
				relationship, direction);
	}

	protected boolean hasRelationship(RelationshipType relationship,
			Direction direction) {
		return node.hasRelationship(relationship, direction);
	}

	Relationship getSingleRelationship(RelationshipType relationship,
			Direction direction) {
		return node.getSingleRelationship(relationship, direction);
	}
}
