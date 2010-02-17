package com.neoprojectmanager.test;

import static com.neoprojectmanager.utils.Converter.inDays;
import static com.neoprojectmanager.utils.Formatting.domainToJSONString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

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
	public void testSetAndGetProperties() {
		Date startTest = new Date();
		//PROJECT
		emptyProject = factory.createProject("emptyProject");
		// CREATION DATE
		assertTrue(startTest.compareTo(emptyProject.getCreationTime()) >= 0);
		assertTrue(emptyProject.getCreationTime().before(new Date()));

		// _CLASS
		assertEquals(Project.class.getCanonicalName(), emptyProject.getWrapperClassName());

		// NAME
		assertEquals("emptyProject", emptyProject.getName());
		emptyProject.setName("Move to Bangkok");
		assertEquals("Move to Bangkok", emptyProject.getName());

		//TASK
		Task t = emptyProject.createTask("A Task");
		// CREATION_DATE
		assertTrue(startTest.before(t.getCreationTime()));
		assertTrue(t.getCreationTime().before(new Date()));

		// _CLASS
		assertEquals(Task.class.getCanonicalName(), t.getWrapperClassName());

		// NAME
		assertEquals("A Task", t.getName());
		t.setName("mario");
		assertEquals("mario", t.getName());

		// START_DATE
		Date now = new Date();
		assertNull(t.getStartDate());
		t.setStartDate(now);
		assertEquals(now, t.getStartDate());
		t.setStartDate(null);
		assertNull(t.getStartDate());

		// DURATION
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

		// END_DATE
		assertNull(t.getEndDate());
		t.setDuration(inDays(10));
		Calendar cal = Calendar.getInstance();
		t.setStartDate(cal.getTime());
		cal.add(Calendar.DATE, 10);
		assertTrue(cal.getTime().compareTo(t.getEndDate()) == 0);

		assertEquals(t.getProject(), emptyProject);
	}

	@Test
	public void testToJSON() {
		System.out.println("\nTASK TO JSON");
		System.out.println(domainToJSONString(emptyProject.createTask("An empty task")) + "\n\n");
	}
}
