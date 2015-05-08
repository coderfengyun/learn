package org.tcse.algorithms.learn.entitymanager;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.spi.Bootstrap;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;
import org.tcse.algorithms.hibernate_ehcache.CachedEntity;

public class EntityManagerHelper {
	private static final String PERSISTENCE_UNIT_NAME = "AbstractJPARepository";
	private static EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();
	private static EntityManager entityManager = entityManagerFactory
			.createEntityManager();

	private static EntityManagerFactory buildEntityManagerFactory() {
		PersistenceUnitDescriptor persistenceUnitDescriptor = new JpaPersistenceUnitDescriptor();
		return Bootstrap.getEntityManagerFactoryBuilder(
				persistenceUnitDescriptor, buildSettings()).build();
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}

	private static Properties buildSettings() {
		Properties properties = new Properties();
		try {
			properties.load(AbstractJPARepository.class.getClassLoader()
					.getResourceAsStream("config.properties"));
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static class JpaPersistenceUnitDescriptor implements
			PersistenceUnitDescriptor {

		public URL getPersistenceUnitRootUrl() {
			return null;
		}

		public String getName() {
			return PERSISTENCE_UNIT_NAME;
		}

		public String getProviderClassName() {
			return HibernatePersistenceProvider.class.getName();
		}

		public boolean isUseQuotedIdentifiers() {
			return false;
		}

		public boolean isExcludeUnlistedClasses() {
			return false;
		}

		public PersistenceUnitTransactionType getTransactionType() {
			return PersistenceUnitTransactionType.RESOURCE_LOCAL;
		}

		public ValidationMode getValidationMode() {
			return null;
		}

		public SharedCacheMode getSharedCacheMode() {
			return null;
		}

		public List<String> getManagedClassNames() {
			return Arrays.asList(User.class.getName(), Account.class.getName(),
					CachedEntity.class.getName());
		}

		public List<String> getMappingFileNames() {
			return null;
		}

		public List<URL> getJarFileUrls() {
			return null;
		}

		public Object getNonJtaDataSource() {
			return null;
		}

		public Object getJtaDataSource() {
			return null;
		}

		public Properties getProperties() {
			return null;
		}

		public ClassLoader getClassLoader() {
			return null;
		}

		public ClassLoader getTempClassLoader() {
			return null;
		}

		public void pushClassTransformer(List<String> entityClassNames) {
		}

	}
}
