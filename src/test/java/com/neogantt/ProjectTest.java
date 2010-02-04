package com.neogantt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.neoprojectmanager.model.Task;

public class ProjectTest extends FactoryTest {

	@Test
	public void testCreateTask() {
		Task task = factory.createTask("Task");
		assertNotNull(task);
		assertNotNull(factory.getNodeById(task.getId()));
		assertEquals("Task", task.getName());
	}

}
