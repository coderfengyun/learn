package org.tcse.algorithms.learn.entitymanager;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

public abstract class AbstractJPARepository<TAggregateRoot> {
	Logger logger = Logger.getLogger(AbstractJPARepository.class);
	EntityManager entityManager = EntityManagerHelper.getEntityManager();

	public void attach(TAggregateRoot aggregateRoot) {
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager.persist(aggregateRoot);
			this.entityManager.getTransaction().commit();
		} catch (RuntimeException e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}
}
