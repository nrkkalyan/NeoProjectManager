package com.neoprojectmanager.model;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Transaction;

/**
 * Implements utility methods to manage properties common to nodes and relationships wrappers.
 * @author xan
 *
 */
abstract class PropertyContainerWrapper {

	/**
	 * Used to open/close the transactions.
	 */
	protected GraphDatabaseService gdbs;
	protected PropertyContainer propertyContainer;

	PropertyContainerWrapper(PropertyContainer propertyContainer, GraphDatabaseService gdbs) {
		this.gdbs = gdbs;
		this.propertyContainer = propertyContainer;
	}

	protected void setProperty(Enum property, Object value)
			throws IllegalArgumentException {
		Transaction tx = beginTx();
		try {
			propertyContainer.setProperty(property.name(), value);
			tx.success();
		} finally {
			tx.finish();
		}
	}

	protected Object getPropertyOrNull(Enum property) {
		Transaction tx = beginTx();
		try {
			Object value = propertyContainer.getProperty(property.name(), null);
			tx.success();
			return value;
		} finally {
			tx.finish();
		}
	}
	
	protected Object getProperty(Enum property) {
		Transaction tx = beginTx();
		try {
			Object value = propertyContainer.getProperty(property.name());
			tx.success();
			return value;
		} finally {
			tx.finish();
		}
	}

	protected void setOrRemoveProperty(Enum property, Object value) {
		Transaction tx = beginTx();
		try {
			if (value != null)
				propertyContainer.setProperty(property.name(), value);
			else
				propertyContainer.removeProperty(property.name());
			tx.success();
		} finally {
			tx.finish();
		}
	}

	protected void removeProperty(Enum property) {
		Transaction tx = beginTx();
		try {
			propertyContainer.removeProperty(property.name());
			tx.success();
		} finally {
			tx.finish();
		}
	}

	Transaction beginTx() {
		return gdbs.beginTx();
	}

}
