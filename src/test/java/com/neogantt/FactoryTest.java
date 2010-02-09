package com.neogantt;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.Transaction;

import com.neoprojectmanager.model.Factory;
import com.neoprojectmanager.model.Project;
import com.neoprojectmanager.model.Resource;

public class FactoryTest {

	protected static Factory factory = null;
	private static File tempdir;

	@BeforeClass
	public static void oneTimeSetUp() throws IOException {
		tempdir = File.createTempFile("NEO4J_TEMP_", "_TEST");
		tempdir.delete(); // it's created as a file
		tempdir.mkdir(); // I recreate it as a dir.
		FileUtils.forceDeleteOnExit(tempdir);
		factory = new Factory(tempdir.getCanonicalPath());
		System.out.println("CREATED " + tempdir.getCanonicalPath());
	}

	@AfterClass
	public static void oneTimeTearDown() throws IOException {
		factory.close();
		FileUtils.deleteDirectory(tempdir);
		System.out.println("DELETED " + tempdir.getCanonicalPath());
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * First level objects are actually Project and Resources, as they can exist
	 * independently.
	 */
	@Test
	public void testCreateFirstLevelDomainObjects() {
		Project p = factory.createProject("Project 1");
		assertNotNull(p); // A node was returned.
		assertNotNull(factory.getProjectById(p.getId())); // It is actually in
															// the
		// database
		assertEquals("Project 1", p.getName()); // contains the mandatory
												// property
		// set before.

		Resource r = factory.createResource("REsource 1");
		assertNotNull(r);
		assertNull(factory.getProjectById(r.getId())); // Just to check that it can distinguish project and resources
		
	}

	@Test
	public void testGetInexistentFirstLevelDomainObjects() {
		Project p = factory.getProjectById(0L);
		assertNotNull(p); // This should be the "root" node
		try {
			p = factory.getProjectById(-1L);
			assertTrue(false); // Should have thrown an exception
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		try {
			p = factory.getProjectById(1000L);
			assertTrue(false); // Should have thrown an exception
		} catch (org.neo4j.graphdb.NotFoundException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testPopulateAndClearDb() {
		Transaction tx = factory.beginTx();
		factory.clearDB();
		Iterator<Project> it = factory.getAllProjects();
		assertFalse(it.hasNext());
		factory.populateDB();
		it = factory.getAllProjects();
		assertTrue(it.hasNext());
		// factory.clearDB();
		tx.success();
		tx.finish();
	}

}
