package org.tcse.algorithms.learn.entitymanager;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public abstract class AbstractJPARepository<TAggregateRoot, IdType extends Serializable> {
	Logger logger = Logger.getLogger(AbstractJPARepository.class);
	EntityManager entityManager = EntityManagerHelper.getEntityManager();
	private Class<?> entityClass;

	AbstractJPARepository(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public void store(TAggregateRoot aggregateRoot) {
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager.persist(aggregateRoot);
			this.entityManager.getTransaction().commit();
		} catch (RuntimeException e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}

	public TAggregateRoot findBy(IdType id) {
		this.entityManager.getTransaction().begin();
		try {
			@SuppressWarnings("unchecked")
			TAggregateRoot result = (TAggregateRoot) this.entityManager.find(
					entityClass, id);
			this.entityManager.getTransaction().commit();
			return result;
		} catch (RuntimeException e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}

	public void detach(IdType id) {
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager.remove(this.entityManager.find(this.entityClass,
					id));
			this.entityManager.getTransaction().commit();
		} catch (RuntimeException e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}
}
