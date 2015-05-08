package org.tcse.algorithms.hibernate_ehcache;

import org.tcse.algorithms.learn.entitymanager.AbstractJPARepository;

public class CachedEntityRepository extends
		AbstractJPARepository<CachedEntity, Integer> {

	public CachedEntityRepository() {
		super(CachedEntity.class);
	}

}
