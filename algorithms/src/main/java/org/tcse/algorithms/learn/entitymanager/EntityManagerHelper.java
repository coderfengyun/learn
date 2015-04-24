package org.tcse.algorithms.learn.entitymanager;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.jpa.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class EntityManagerHelper {
	private static final String PERSISTENCE_UNIT = "AbstractJPARepository";
	private static EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();
	private static EntityManager entityManager = entityManagerFactory
			.createEntityManager();

	private static EntityManagerFactory buildEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}

	private static Properties buildSettings() {
		Properties properties = new Properties();
		try {
			properties.load(AbstractJPARepository.class.getClassLoader()
					.getResourceAsStream("config.properties"));
			properties.put(AvailableSettings.PROVIDER,
					HibernatePersistenceProvider.class.getName());
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
