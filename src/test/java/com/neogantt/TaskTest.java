package com.neogantt;

import static com.neoprojectmanager.utils.Formatting.domainToJSONString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
	public void testAddAndGetDependent() {
		Task node_1 = emptyProject.createTask("node_1");
		Task node_1_1 = emptyProject.createTask("node_1_1");
		Task node_1_2 = emptyProject.createTask("node_1_2");

		assertFalse(node_1.hasDependents()); // there should be no dependent
		// tasks at this point

		node_1.addDependent(node_1_1); // Add a dependent task
		assertTrue(node_1.hasDependents()); // now there should be a
		// dependent task

		node_1.addDependent(node_1_2); // Add another task
		assertTrue(node_1.hasDependents()); // again there should be
		// dependent tasks

		Iterator<Task> it = node_1.getDependentTaskImpls(); // now let's
		// check
		// what
		// tasks are
		// returned
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
	public void testAddAndGetIncludedTasks() {
		assertFalse(true);
		// TaskImpl node_1 = factory.createNode("node_1");
		// TaskImpl node_1_1 = factory.createNode("node_1_1");
		// TaskImpl node_1_2 = factory.createNode("node_1_2");
		//
		// assertFalse(node_1.hasIncludedTask()); // there should be no Included
		// // tasks at this point
		//
		// node_1.addIncluded(node_1_1); // Add a Included task
		// assertTrue(node_1.hasIncludedTask()); // now there should be a
		// Included
		// // task
		//
		// node_1.addIncluded(node_1_2); // Add another task
		// assertTrue(node_1.hasIncludedTask()); // again there should be
		// Included
		// // tasks
		//
		// Iterator<TaskImpl> it = node_1.includedTasks().iterator(); // now
		// let's
		// // check
		// // what
		// // tasks are
		// // returned
		// assertTrue(it.hasNext()); // there should a next
		//
		// TaskImpl firstNode = it.next(); // let's retrieve it
		// assertNotNull(firstNode); // it should not be null (could return a
		// null
		// // here sometime?)
		//
		// TaskImpl secondNode = it.next(); // there should be another one.
		// assertNotNull(secondNode); // not null...
		//
		// assertFalse(it.hasNext()); // now there should be no more.
		// assertTrue( // let's check if they are the tasks I inserted.
		// // I have to check both cases I don't know in which order they are
		// // retrieved.
		// (firstNode.getName().equals("node_1_1") &&
		// secondNode.getName().equals(
		// "node_1_2"))
		// || //
		// (firstNode.getName().equals("node_1_2") && secondNode.getName()
		// .equals("node_1_1")));
	}

	@Test
	public void testSetAndGetName() {
		assertEquals("emptyProject", emptyProject.getName());
		emptyProject.setName("mario");
		assertEquals("mario", emptyProject.getName());
		emptyProject.setName("emptyProject");
	}

	@Test
	public void testSetAndGetStartDate() {
		Task t = emptyProject.createTask("A Task");
		Calendar now = Calendar.getInstance();
		assertNull(t.getStartDate());
		t.setStartDate(now.getTime());
		assertEquals(now.getTime(), t.getStartDate());
		t.setStartDate(null);
		assertNull(t.getStartDate());
	}

	@Test
	public void testSetAndGetDuration() {
		Task t = emptyProject.createTask("A Task");
		assertNull(t.getDurationInMinutes());
		t.setDurationInMinutes(120);
		assertEquals(120, (int) t.getDurationInMinutes());
		t.setDurationInMinutes(null);
		assertNull(t.getDurationInMinutes());
		try {
			t.setDurationInMinutes(-1);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testSetAndGetResourceName() {
		assertFalse(true);
	}

	@Test
	public void testGetId() {
		assertNotNull(emptyProject.getId());
	}

	@Test
	public void testToJSON() {
		assertFalse(true);
		System.out.println("\nTASK TO JSON");
		System.out.println(domainToJSONString(emptyProject.createTask("An empty task")) + "\n\n");
	}
}
