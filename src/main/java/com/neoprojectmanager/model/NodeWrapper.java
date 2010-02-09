package com.neoprojectmanager.model;

import java.util.Iterator;

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

	protected static Object[] flattenRelTuples(RelTup... r) {
		if (r == null)
			return null;
		Object[] result = new Object[r.length * 2];
		for (int i = 0; i < r.length; i++) {
			result[i * 2] = r[i].getRelationship();
			result[i * 2 + 1] = r[i].getDirection();
		}
		return result;
	}

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
			Relationship tr = from.getNode().createRelationshipTo(
					destination.getNode(), type);
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
			ReturnableEvaluator returnableEvaluator, RelTup... relTuple) {

		return node.traverse(order, stopEvaluator, returnableEvaluator,
				flattenRelTuples(relTuple));
	}

	Traverser traverse(Order order, StopEvaluator stopEvaluator,
			ReturnableEvaluator returnableEvaluator,
			RelationshipType relationship, Direction direction,
			RelationshipType relationship2, Direction direction2) {

		return node.traverse(order, stopEvaluator, returnableEvaluator,
				relationship, direction, relationship2, direction2);
	}

	protected boolean hasRelationship(RelationshipType relationship,
			Direction direction) {
		return node.hasRelationship(relationship, direction);
	}

	Relationship getSingleRelationship(RelationshipType relationship,
			Direction direction) {
		return node.getSingleRelationship(relationship, direction);
	}

	protected <T extends NodeWrapper> Iterator<T> getNodeWrapperIterator(
			final Class<T> t, final Order order, final StopEvaluator stopEv,
			final ReturnableEvaluator retEv, final RelTup... relTuple) {
		return getNodeWrapperIterator(t, gdbs, node, order, stopEv, retEv,
				relTuple);
	}

	public static <T extends NodeWrapper> Iterator<T> getNodeWrapperIterator(
			final Class<T> t, final GraphDatabaseService gdbs, final Node node,
			final Order order, final StopEvaluator stopEv,
			final ReturnableEvaluator retEv, final RelTup... relTuple) {
		return new Iterator<T>() {
			/**
			 * That the nodes returned from this traverser are correctly managed
			 * by the NodeWrapper class specified can be checked only at
			 * runtime.
			 */
			private final Iterator<Node> iterator = node.traverse(order,
					stopEv, retEv, flattenRelTuples(relTuple)).iterator();

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public T next() {
				Node nextNode = iterator.next();
				try {
					return t.getDeclaredConstructor(Node.class,
							GraphDatabaseService.class).newInstance(nextNode,
							gdbs);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			public void remove() {
			}
		};
	}
}
