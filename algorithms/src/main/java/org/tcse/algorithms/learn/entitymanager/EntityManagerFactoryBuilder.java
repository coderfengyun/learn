package org.tcse.algorithms.learn.entitymanager;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.spi.Bootstrap;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;

public class EntityManagerFactoryBuilder {
	private Properties properties = new Properties();
	private String[] annotatedClassNames = new String[0];
	private String persistentUnitName = "default";

	public EntityManagerFactoryBuilder() {
		try {
			this.properties.load(this.getClass().getClassLoader()
					.getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public EntityManagerFactoryBuilder addPersistentUnitName(
			String persistentUnitName) {
		this.persistentUnitName = persistentUnitName;
		return this;
	}

	public EntityManagerFactoryBuilder addSettings(Properties properties) {
		this.properties = properties;
		return this;
	}

	public EntityManagerFactoryBuilder addAnnotatedClasses(String[] classNames) {
		this.annotatedClassNames = classNames;
		return this;
	}

	public EntityManagerFactory build() {
		PersistenceUnitDescriptor descriptor = new PersistenceUnitDescriptor() {

			@Override
			public URL getPersistenceUnitRootUrl() {
				return null;
			}

			@Override
			public String getName() {
				return EntityManagerFactoryBuilder.this.persistentUnitName;
			}

			@Override
			public String getProviderClassName() {
				return HibernatePersistenceProvider.class.getName();
			}

			@Override
			public boolean isUseQuotedIdentifiers() {
				return false;
			}

			@Override
			public boolean isExcludeUnlistedClasses() {
				return false;
			}

			@Override
			public PersistenceUnitTransactionType getTransactionType() {
				return PersistenceUnitTransactionType.RESOURCE_LOCAL;
			}

			@Override
			public ValidationMode getValidationMode() {
				return null;
			}

			@Override
			public SharedCacheMode getSharedCacheMode() {
				return null;
			}

			@Override
			public List<String> getManagedClassNames() {
				return Arrays
						.asList(EntityManagerFactoryBuilder.this.annotatedClassNames);
			}

			@Override
			public List<String> getMappingFileNames() {
				return null;
			}

			@Override
			public List<URL> getJarFileUrls() {
				return null;
			}

			@Override
			public Object getNonJtaDataSource() {
				return null;
			}

			@Override
			public Object getJtaDataSource() {
				return null;
			}

			@Override
			public Properties getProperties() {
				return null;
			}

			@Override
			public ClassLoader getClassLoader() {
				return null;
			}

			@Override
			public ClassLoader getTempClassLoader() {
				return null;
			}

			@Override
			public void pushClassTransformer(List<String> entityClassNames) {

			}

		};

		return Bootstrap.getEntityManagerFactoryBuilder(descriptor,
				EntityManagerFactoryBuilder.this.properties).build();
	}
}
