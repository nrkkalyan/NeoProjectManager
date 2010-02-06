package com.neogantt;

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

	@Test
	public void testCreateFirstLevelDomainObjects() {
		Project p = factory.createProject("Node1");
		assertNotNull(p); // A node was returned.
		assertNotNull(factory.getProjectById(p.getId())); // It is actually in the
		// database
		assertEquals("Node1", p.getName()); // contains the mandatory property
		// set before.
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
		tx.success();
		tx.finish();

		tx = factory.beginTx();
		Iterator<Project> it = factory.getAllProjects();
		assertFalse(it.hasNext());
		factory.populateDB();
		tx.success();
		tx.finish();

		tx = factory.beginTx();
		 it = factory.getAllProjects();
		assertTrue(it.hasNext());
		factory.clearDB();
		tx.success();
		tx.finish();
	}

}
