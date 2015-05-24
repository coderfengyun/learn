package org.tcse.algorithms.learn.entitymanager;

public class EntityWithPrivateConstructorRepository extends
		AbstractJPARepository<EntityWithPrivateConstructor, Integer> {

	protected EntityWithPrivateConstructorRepository() {
		super(EntityWithPrivateConstructor.class);
	}

}
