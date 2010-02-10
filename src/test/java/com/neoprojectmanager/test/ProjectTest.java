package com.neoprojectmanager.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

import com.neoprojectmanager.model.Project;
import com.neoprojectmanager.model.Resource;
import com.neoprojectmanager.model.Task;

public class ProjectTest extends FactoryTest {

	/**
	 * Basic creation and identity tests where performed in FactoryTest. Now we
	 * will focus on creating a Resource/project/task structure and we will
	 * check that all related methods return values consistent with the
	 * underlying structure.<br>
	 * The test is focused on the structure, or relationships, of the domain
	 * objects. Properties will be tested in the object-sepecific test.
	 */
	@Test
	public void testCreateProject() {
		Project p = factory.createProject("NeoPM");
		assertFalse(p.hasTasks());
		assertFalse(p.hasResources());
		assertFalse(p.hasSubProjects());
		try {
			Task t1 = p.createTask("Task1");
			assertTrue(p.hasTasks());
			assertFalse(p.hasResources());
			assertFalse(p.hasSubProjects());

			Iterator<Task> it = p.getTasks();
			assertTrue(it.hasNext());
			assertEquals(t1, it.next());
			assertFalse(it.hasNext());

			Task t1_1 = p.createTask("Task1.1");
			t1_1.addDependentOn(t1);
			assertTrue(t1.hasDependents());
			assertFalse(t1.hasDependencies());
			assertTrue(t1_1.hasDependencies());
			assertFalse(t1_1.hasDependents());

			int counter = 0;
			it = p.getTasks();
			assertTrue(it.hasNext());
			while (it.hasNext()) {
				counter++;
				Task t = it.next();
				assertTrue((t.equals(t1_1) && !t.equals(t1)) || (t.equals(t1) && !t.equals(t1_1)));
			}
			assertTrue(counter == 2);

			it = t1.getDependentTasks();
			assertTrue(it.hasNext());
			assertEquals(t1_1, it.next());
			assertFalse(it.hasNext());

		} catch (Exception e) {
			assertTrue(false);
		}

		Project sp = p.createSubProject("SubProject");
		assertTrue(p.hasSubProjects());
		assertFalse(sp.hasSubProjects());
		assertFalse(sp.hasResources());
		assertFalse(sp.hasTasks());
		try {
			Task st1 = sp.createTask("SubTask1");
			assertFalse(sp.hasSubProjects());
			assertFalse(sp.hasResources());
			assertTrue(sp.hasTasks());
			Task st1_1 = sp.createTask("SubTask1.1");
			st1.addDependent(st1_1);
			int counter = 0;
			boolean subTask1Found = false;
			boolean subTask1_1Found = false;
			
			Iterator<Task> it = p.getTasksAndSubtasks();
			while (it.hasNext()) {
				counter++;
				Task t = it.next();
				if (st1.equals(t))
						subTask1Found = true;
				else if (st1_1.equals(t))
					subTask1_1Found = true;
			}
			assertFalse(it.hasNext());
			assertTrue(counter == 4);
			assertTrue(subTask1Found && subTask1_1Found);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		Iterator<Project> subIt = p.getSubProjects();
		assertTrue(subIt.hasNext());
		assertEquals(sp, subIt.next());
		assertFalse(subIt.hasNext());
		
		Resource r1 = factory.createResource("Carpenter1");
		p.allocateResource(r1);
		assertTrue(p.hasTasks());
		assertTrue(p.hasResources());
		assertTrue(p.hasSubProjects());
		
	}

}
