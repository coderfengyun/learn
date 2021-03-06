package org.tcse.algorithms.learn.entitymanager;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

public abstract class AbstractJPARepository<TAggregateRoot, IdType extends Serializable> {
	Logger logger = Logger.getLogger(AbstractJPARepository.class);
	EntityManager entityManager = EntityManagerHelper.getEntityManager();
	private Class<TAggregateRoot> entityClass;

	protected AbstractJPARepository(Class<TAggregateRoot> entityClass) {
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

	public void remove(IdType id) {
		this.entityManager.getTransaction().begin();
		try {
			TAggregateRoot find = this.entityManager.find(this.entityClass, id);
			if (find != null) {
				this.entityManager.remove(find);
			}
			this.entityManager.getTransaction().commit();
		} catch (RuntimeException e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}

	public TAggregateRoot findBy(IdType id) {
		this.entityManager.getTransaction().begin();
		try {
			TAggregateRoot result = (TAggregateRoot) this.entityManager.find(
					entityClass, id);
			this.entityManager.getTransaction().commit();
			return result;
		} catch (RuntimeException e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}

	public TAggregateRoot findBy(Optional<Predicate> uniqueSpecification) {
		this.entityManager.getTransaction().begin();
		try {
			CriteriaQuery<TAggregateRoot> query = createCriteriaQuery(uniqueSpecification);
			TAggregateRoot result = entityManager.createQuery(query)
					.getSingleResult();
			this.entityManager.getTransaction().commit();
			return result;
		} catch (RuntimeException e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}

	private CriteriaQuery<TAggregateRoot> createCriteriaQuery(
			Optional<Predicate> specification) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<TAggregateRoot> query = builder
				.createQuery(this.entityClass);
		Root<TAggregateRoot> resultRoot = query.from(this.entityClass);
		if (specification.isPresent()) {
			query.select(resultRoot).where(specification.get());
		}
		return query;
	}

	public List<TAggregateRoot> findAllBy(Optional<Predicate> specification) {
		this.entityManager.getTransaction().begin();
		try {
			List<TAggregateRoot> result = this.entityManager.createQuery(
					createCriteriaQuery(specification)).getResultList();
			this.entityManager.getTransaction().commit();
			return result;
		} catch (RuntimeException e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}
}
