package com.neogantt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Iterator;

import org.junit.Test;

import com.neoprojectmanager.model.Project;
import com.neoprojectmanager.model.Task;

public class ProjectTest extends FactoryTest {

	@Test
	public void testCreateProject() {
		Project p = factory.createProject("NeoPM");
		assertNotNull(p);
		assertNotNull(factory.getProjectById(p.getId()));
		assertEquals("NeoPM", p.getName());
		assertFalse(p.hasTasks() || p.hasResources() || p.hasSubProjects());
		
		Task t = p.createTask("Task1");
		assertTrue(p.hasTasks());
		Iterator<Task> it = p.getTasks();
		assertTrue(it.hasNext());
		assertEquals(t.getName(), it.next().getName());
		assertFalse(it.hasNext());
		Project sp = p.createSubProject("SubProject");
		assertEquals("SubProject", sp.getName());
		assertTrue(!sp.hasTasks() && !sp.hasResources() && !sp.hasSubProjects() && p.hasSubProjects());
		
		Task st = sp.createTask("Task1.1");
		assertTrue(sp.hasTasks());
		it = p.getTasksAndSubtasks();
		assertTrue(it.hasNext());
		assertEquals(t.getName(), it.next().getName());
		assertEquals(st.getName(), it.next().getName());
		assertFalse(it.hasNext());
		
		assertTrue(p.hasSubProjects());
		assertTrue(p.getCreationTime().before(Calendar.getInstance().getTime()));
		p.removeTaskById(st.getId());
		assertTrue(p.hasTasks());
		assertTrue(sp.hasTasks());
		p.removeTaskById(t.getId());
		assertFalse(p.hasTasks());
		assertTrue(sp.hasTasks());
		sp.removeTaskById(st.getId());
		assertFalse(p.hasTasks());
		assertFalse(sp.hasTasks());
		p.setName("NOW WHAT HEPPEN?");
	}
	
	@Test
	public void testCreateSubProject() {
		Project project = factory.createProject("Project1");
		assertNotNull(project);
		Project subProject = project.createSubProject("Project1.1");
		assertNotNull(subProject);
		assertEquals("Project1.1", subProject.getName());
		Iterator<Project> it = project.getSubProjects();
		assertTrue(it.hasNext());
		assertEquals("Project1.1", it.next().getName());
	}

}
