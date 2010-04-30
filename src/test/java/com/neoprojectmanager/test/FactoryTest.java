package com.neoprojectmanager.test;

import com.neoprojectmanager.model.Factory;
import com.neoprojectmanager.model.Project;
import com.neoprojectmanager.model.Resource;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.neo4j.graphdb.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;

public class FactoryTest {

	protected static Factory factory = null;
	private static File tempdir;

	@BeforeClass
	public static void oneTimeSetUp() throws IOException {
		tempdir = File.createTempFile("NEO4J_TEMP_", "_TEST");
		assertTrue(tempdir.delete()); // it's created as a file
		assertTrue(tempdir.mkdir()); // I recreate it as a dir.
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
		assertEquals(factory.getProjectById(p.getId()), p);

		Resource r = factory.createResource("REsource 1");
		assertNotNull(r);
		assertEquals(factory.getResourceById(r.getId()), r);
		
		// check that Project and Resources don't appear in the sema node-space
		assertNull(factory.getResourceById(p.getId()));
		assertNull(factory.getProjectById(r.getId()));
	}

	@Test
	public void testGetInexistentFirstLevelDomainObjects() {

		try {
			Project p = factory.getProjectById(0L);
			assertNull(p); // This should be the "root" node
			factory.getProjectById(-1L);
			assertTrue(false); // Should have thrown an exception
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			factory.getProjectById(1000L);
			assertTrue(false); // Should have thrown an exception
		} catch (org.neo4j.graphdb.NotFoundException e) {
			assertTrue(true);
		}
		
		try {
			Resource r = factory.getResourceById(0L);
			assertNull(r); // This should be the "root" node
			factory.getResourceById(-1L);
			assertTrue(false); // Should have thrown an exception
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			factory.getResourceById(1000L);
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
