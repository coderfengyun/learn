package org.tcse.algorithms.learn.entitymanager;

import static org.junit.Assert.*;

import org.junit.Test;

public class Test_EntityWithPrivateConstructor {
	private EntityWithPrivateConstructorRepository repo = new EntityWithPrivateConstructorRepository();

	@Test
	public void test() {
		int id = 1;
		this.repo.store(new EntityWithPrivateConstructor(id));
		EntityWithPrivateConstructor entity = this.repo.findBy(id);
		assertNotNull(entity);
		assertEquals(id, entity.getId());
	}
}
