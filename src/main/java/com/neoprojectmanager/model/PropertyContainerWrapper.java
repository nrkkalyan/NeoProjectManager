package com.neoprojectmanager.model;

import java.util.Calendar;
import java.util.Date;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Transaction;

/**
 * Implements common methods to manage properties of nodes and relationships.
 * Transparently set some default properties like the creation timestamp or the
 * NodeWrapper subclass that instantiated this entity.
 * 
 * @author xan
 * 
 */
abstract class PropertyContainerWrapper {

	protected enum PROPERTY {
		CREATED_ON, UPDATED_ON, _CLASS
	}

	/**
	 * Used to open/close the transactions.
	 */
	protected GraphDatabaseService gdbs;
	protected PropertyContainer propertyContainer;

	/**
	 * Caled when wrapping an existing entity.
	 * 
	 * @param propertyContainer
	 * @param gdbs
	 */
	PropertyContainerWrapper(PropertyContainer propertyContainer, GraphDatabaseService gdbs) {
		this.gdbs = gdbs;
		this.propertyContainer = propertyContainer;
	}

	/**
	 * Called when a new node is created. Should never be used to instantiate a
	 * Relationship wrapper.
	 * 
	 * @param gdbs
	 */
	PropertyContainerWrapper(GraphDatabaseService gdbs) {
		this.gdbs = gdbs;
		this.propertyContainer = gdbs.createNode();
		setCreationTime();
		setProperty(PROPERTY._CLASS, this.getClass().getCanonicalName());
	}

	protected void setProperty(Enum property, Object value) throws IllegalArgumentException {
		Transaction tx = beginTx();
		try {
			propertyContainer.setProperty(property.name(), value);
			tx.success();
		} finally {
			tx.finish();
		}
	}

	protected Object getPropertyOrNull(Enum property) {
		return getProperty(property, null);
	}

	protected Object getProperty(Enum property, Object defaulz) {
		Transaction tx = beginTx();
		try {
			Object value = propertyContainer.getProperty(property.name(), defaulz);
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

	protected void setCreationTime() {
		setProperty(PROPERTY.CREATED_ON, Calendar.getInstance().getTimeInMillis());
	}

	public Date getCreationTime() {
		return new Date((Long) getProperty(PROPERTY.CREATED_ON));
	}

	public String getWrapperClassName() {
		return (String) getProperty(PROPERTY._CLASS);
	}
}
