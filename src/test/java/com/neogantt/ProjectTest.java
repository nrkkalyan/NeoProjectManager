package com.neogantt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.neoprojectmanager.model.Project;

public class ProjectTest extends FactoryTest {

	@Test
	public void testCreateProject() {
		Project project = factory.createProject("NeoPM");
		assertNotNull(project);
		assertNotNull(factory.getProjectById(project.getId()));
		assertEquals("NeoPM", project.getName());
	}
	
	@Test
	public void testCreateSubProject() {
		Project project = factory.createProject("Project1");
		assertNotNull(project);
		Project subProject = project.createSubProject("Project1.1");
		assertNotNull(subProject);
		project.getSubProjects();
		assertEquals("Project1.1", project.getName());
	}

}
