package com.neoprojectmanager.test;

import static com.neoprojectmanager.utils.Formatting.domainToJSONString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static com.neoprojectmanager.utils.Converter.*;

import java.util.Calendar;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neoprojectmanager.model.Project;
import com.neoprojectmanager.model.Task;

public class TaskTest extends ProjectTest {

	private Project emptyProject = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		emptyProject = factory.createProject("emptyProject");
	}

	@After
	public void tearDown() throws Exception {
		emptyProject = null;
		super.tearDown();
	}

	@Test
	public void testObjectRelationships() {
		// Given an empty project, add some task and set dependencies
		// Add subProjects, subTasks and set dependencies
		// Create resources and allocate them on the project
		// test that all the relative relationship methods relfect the given structure.
		assertFalse(emptyProject.hasTasks());
		Task node_1 = emptyProject.createTask("node_1");
		assertTrue(emptyProject.hasTasks());
		assertEquals(node_1.getProject().getId(), emptyProject.getId());
		Task node_1_1 = emptyProject.createTask("node_1_1");
		Task node_1_2 = emptyProject.createTask("node_1_2");

		assertFalse(node_1.hasDependents()); // there should be no dependent
		// tasks at this point

		node_1.addDependent(node_1_1); // Add a dependent task
		assertTrue(node_1.hasDependents()); // now there should be a
		// dependent task

		node_1.addDependent(node_1_2); // Add another task
		// again there should be dependent tasks
		assertTrue(node_1.hasDependents()); 
		
		// now let's check  what tasks are returned
		Iterator<Task> it = node_1.getDependentTasks(); 
		assertTrue(it.hasNext()); // there should a next

		Task firstNode = it.next(); // let's retrieve it
		assertNotNull(firstNode); // it should not be null (could return a null
		// here sometime?)

		Task secondNode = it.next(); // there should be another one.
		assertNotNull(secondNode); // not null...

		assertFalse(it.hasNext()); // now there should be no more.
		assertTrue( // let's check if they are the tasks I inserted.
		// I have to check both cases I don't know in which order they are
		// retrieved.
		(firstNode.getName().equals("node_1_1") && secondNode.getName().equals(
				"node_1_2"))
				|| // 
				(firstNode.getName().equals("node_1_2") && secondNode.getName()
						.equals("node_1_1")));
	}

	@Test
	public void testSetAndGetName() {
		// NAME
		Task t = emptyProject.createTask("A Task");
		assertEquals("A Task", t.getName());
		t.setName("mario");
		assertEquals("mario", t.getName());
		// START DATE
		Calendar now = Calendar.getInstance();
		assertNull(t.getStartDate());
		t.setStartDate(now.getTime());
		assertEquals(now.getTime(), t.getStartDate());
		t.setStartDate(null);
		assertNull(t.getStartDate());
		// DURATION IN MINUTES
		assertNull(t.getDurationInMinutes());
		t.setDuration(inDays(2));
		assertEquals(inDays(2), (long) t.getDurationInMinutes());
		t.setDuration(null);
		assertNull(t.getDurationInMinutes());
		try {
			t.setDuration(inDays(-1));
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testToJSON() {
		System.out.println("\nTASK TO JSON");
		System.out.println(domainToJSONString(emptyProject.createTask("An empty task")) + "\n\n");
	}
}
