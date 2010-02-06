package com.neoprojectmanager.model;

import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.GraphDatabaseService;

public class Resource extends NodeWrapper {
	enum PROPERTY {
		COST, NAME
	}
	public Resource(String name, GraphDatabaseService gdbs) {
		super(gdbs);
		if (StringUtils.isBlank(name))
			throw new IllegalArgumentException();
		setProperty(PROPERTY.NAME, name);
	}
	
	public void setCost(Long cents) {
		setProperty(PROPERTY.COST, cents);
	}

	public Long getCost() {
		return (Long) getPropertyOrNull(PROPERTY.COST);
	}
	
	public String getName() {
		return (String) getProperty(PROPERTY.NAME);
	}
}
