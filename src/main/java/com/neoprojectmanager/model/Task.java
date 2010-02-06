package com.neoprojectmanager.model;

import java.util.Date;
import java.util.Iterator;

public interface Task {

	public abstract TaskRelationship addDependentOn(Task other);

	public abstract TaskRelationship addDependent(Task other);

	public abstract Iterator<Task> getDependentTaskImpls();

	public abstract boolean hasDependents();

	public abstract boolean hasDependencies();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setStartDate(Date value);

	public abstract Date getStartDate();

	/**
	 * @param durationInMinutes
	 */
	public abstract void setDurationInMinutes(Integer durationInMinutes)
			throws IllegalArgumentException;

	public abstract Integer getDurationInMinutes();

	public abstract long getId();

	public abstract Task getProject();

	public abstract Date getEndDate();

	public abstract String toString();

}