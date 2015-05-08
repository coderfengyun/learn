package org.tcse.algorithms.learn.hibernate_ehcache;

import org.junit.Test;
import org.tcse.algorithms.hibernate_ehcache.CachedEntity;
import org.tcse.algorithms.hibernate_ehcache.CachedEntityRepository;
import org.tcse.algorithms.learn.entitymanager.EntityManagerHelper;

public class Test_CachedEntityRepository {
	private CachedEntityRepository repo = new CachedEntityRepository();

	@Test
	public void testGet3Times() {
		int id = 1;
		// clear it first
		this.repo.remove(id);
		this.repo.store(new CachedEntity(id, "ahah"));
		this.repo.findBy(id);
		this.repo.findBy(id);
		this.repo.findBy(id);
		// clear it at last
		this.repo.remove(id);
	}
}
