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

	public enum PROPERTY {
		_CLASS
	}
	Node node;

	NodeWrapper(GraphDatabaseService gdbs) {
		super(gdbs.createNode(), gdbs);
		this.node = (Node) super.propertyContainer;
		setProperty(PROPERTY._CLASS, this.getClass().getName());
	}

	NodeWrapper(Node node, GraphDatabaseService gdbs) {
		super(node, gdbs);
		this.node = node;
	}

	TaskRelationship createRelationShip(NodeWrapper from,
			RelationshipType type, NodeWrapper to) {
		Transaction tx = gdbs.beginTx();
		try {
			TaskRelationship tr = new TaskRelationship(from.getNode()
					.createRelationshipTo(to.getNode(), type), gdbs);
			tx.success();
			return tr;
		} finally {
			tx.finish();
		}
	}

	public Node getNode() {
		// TODO Auto-generated method stub
		return null;
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
