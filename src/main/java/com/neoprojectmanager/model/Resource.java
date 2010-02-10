package com.neoprojectmanager.model;

import static org.apache.commons.lang.StringUtils.isBlank;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public class Resource extends NodeWrapper {
	enum PROPERTY {
		COST, NAME
	}
	
	public Resource(String name, GraphDatabaseService gdbs) {
		super(gdbs);
		setName(name);
	}
	
	protected Resource(Node node, GraphDatabaseService gdbs) {
		super(node, gdbs);
	}
	
	public void setName(String value) {
		if (isBlank(value))
			throw new IllegalArgumentException();
		setProperty(PROPERTY.NAME, value);
	}
	
	public String getName() {
		return (String) getProperty(PROPERTY.NAME);
	}
	
	public void setCost(Long cents) {
		setProperty(PROPERTY.COST, cents);
	}

	public Long getCost() {
		return (Long) getPropertyOrNull(PROPERTY.COST);
	}

}
