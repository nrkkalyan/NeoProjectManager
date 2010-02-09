package com.neoprojectmanager.model;

import java.util.Iterator;

import org.apache.commons.lang.NotImplementedException;
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

	private Node node;

	/**
	 * Used when instantiating a NodeWrapper on a new node.
	 * 
	 * @param gdbs
	 */
	NodeWrapper(GraphDatabaseService gdbs) {
		super(gdbs);
		this.node = (Node) super.propertyContainer;
	}

	/**
	 * Used to instantiate a NodeWrapper on an existing node (i.e. used in
	 * wrapping iterators)
	 * 
	 * @param node
	 * @param gdbs
	 */
	NodeWrapper(Node node, GraphDatabaseService gdbs) {
		super(node, gdbs);
		this.node = node;
	}

	Node getNode() {
		return this.node;
	}

	public long getId() {
		return node.getId();
	}

	/**
	 * Convenience method used in subclasses
	 * 
	 * @param from
	 * @param type
	 * @param destination
	 * @return
	 */
	Relationship createRelationShip(NodeWrapper from, RelationshipType type, NodeWrapper destination) {
		Transaction tx = gdbs.beginTx();
		try {
			Relationship tr = from.getNode().createRelationshipTo(destination.getNode(), type);
			tx.success();
			return tr;
		} finally {
			tx.finish();
		}
	}

	/**
	 * Convenience method used in subclasses
	 * 
	 * @param order
	 * @param stopEvaluator
	 * @param returnableEvaluator
	 * @param relTuples
	 * @return
	 */
	Traverser traverse(Order order, StopEvaluator stopEvaluator, ReturnableEvaluator returnableEvaluator,
			RelTup... relTuples) {
		return node.traverse(order, stopEvaluator, returnableEvaluator, flattenRelTuples(relTuples));
	}

	/**
	 * Convenience method used in subclasses
	 * 
	 * @param relationship
	 * @param direction
	 * @return
	 */
	protected boolean hasRelationship(RelationshipType relationship, Direction direction) {
		return node.hasRelationship(relationship, direction);
	}

	/**
	 * Convenience method used in subclasses
	 * 
	 * @param relationship
	 * @param direction
	 * @return
	 */
	Relationship getSingleRelationship(RelationshipType relationship, Direction direction) {
		return node.getSingleRelationship(relationship, direction);
	}

	/**
	 * Convenience method used in subclasses. This calls the static one passing
	 * the node we want to iterate from and the GraphDatabaseService
	 * 
	 * @param <T>
	 *            The type of NodeWrapper the node should be wrapped in.
	 * @param clazz
	 *            The class object used to instantiane the desired NodeWrapper
	 * @param order
	 * @param stopEv
	 * @param retEv
	 * @param relTuple
	 * @return
	 */
	protected <T extends NodeWrapper> Iterator<T> getNodeWrapperIterator(final Class<T> clazz, final Order order,
			final StopEvaluator stopEv, final ReturnableEvaluator retEv, final RelTup... relTuple) {
		return getNodeWrapperIterator(clazz, gdbs, node, order, stopEv, retEv, relTuple);
	}

	/**
	 * Creates an Iterator of NodeWrapper that iterates over the node returned
	 * traversing the specified relationship starting from the given node.
	 * 
	 * @param <T>
	 * @param nodeWrapperClass
	 * @param gdbs
	 * @param node
	 * @param order
	 * @param stopEv
	 * @param retEv
	 * @param relTuple
	 * @return
	 */
	public static <T extends NodeWrapper> Iterator<T> getNodeWrapperIterator(final Class<T> nodeWrapperClass,
			final GraphDatabaseService gdbs, final Node node, final Order order, final StopEvaluator stopEv,
			final ReturnableEvaluator retEv, final RelTup... relTuple) {
		return new Iterator<T>() {
			/**
			 * WARNING: checking that the nodes returned from this traverser are
			 * correctly managed by the NodeWrapper class specified can be
			 * performed only at runtime.
			 */
			private final Iterator<Node> iterator = node.traverse(order, stopEv, retEv, flattenRelTuples(relTuple))
					.iterator();

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public T next() {
				Node nextNode = iterator.next();
				try {
					return nodeWrapperClass.getDeclaredConstructor(Node.class, GraphDatabaseService.class).newInstance(
							nextNode, gdbs);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			public void remove() {
				throw new NotImplementedException("This method is not allowed.");
			}
		};
	}

	public static Object[] flattenRelTuples(RelTup... r) {
		if (r == null)
			return null;
		Object[] result = new Object[r.length * 2];
		for (int i = 0; i < r.length; i++) {
			result[i * 2] = r[i].getRelationship();
			result[i * 2 + 1] = r[i].getDirection();
		}
		return result;
	}
}
