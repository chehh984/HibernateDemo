package com.hibernatedemo.config;

import org.hibernate.jpa.HibernatePersistenceProvider;


import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.*;



public final class HibernateConfig {

    private static EntityManagerFactory entityManagerFactory;

    private HibernateConfig(){

    }

    public static EntityManagerFactory getEntityManagerFactory() throws IOException, ClassNotFoundException {

        if(entityManagerFactory != null)
            return entityManagerFactory;

        Class.forName("com.mysql.jdbc.Driver");

        Properties props = new Properties();
        props.load(ClassLoader.getSystemResourceAsStream("default.properties"));

        Map<String,Object> hibernateConfig = new HashMap<>();

        hibernateConfig.put("hibernate.dialect", props.getProperty("hibernate.dialect"));
		hibernateConfig.put("hibernate.show_sql", props.getProperty("hibernate.show_sql"));
		hibernateConfig.put("hibernate.format_sql", props.getProperty("hibernate.format_sql"));
        hibernateConfig.put("javax.persistence.sharedCache.mode", props.getProperty("javax.persistence.sharedCache.mode"));
        hibernateConfig.put("hibernate.enable_lazy_load_no_trans", props.getProperty("hibernate.enable_lazy_load_no_trans"));
        hibernateConfig.put("hibernate.connection.provider_class", props.getProperty("hibernate.connection.provider_class"));
        hibernateConfig.put("hibernate.c3p0.min_size", props.getProperty("hibernate.c3p0.min_size"));
        hibernateConfig.put("hibernate.c3p0.max_size", props.getProperty("hibernate.c3p0.max_size"));
        hibernateConfig.put("hibernate.c3p0.timeout", props.getProperty("hibernate.c3p0.timeout"));
        hibernateConfig.put("hibernate.c3p0.idle_test_period", props.getProperty("hibernate.c3p0.idle_test_period"));
        hibernateConfig.put("hibernate.c3p0.max_statements", props.getProperty("hibernate.c3p0.max_statements"));
        hibernateConfig.put("hibernate.c3p0.acquireRetryAttempts", props.getProperty("hibernate.c3p0.acquireRetryAttempts"));
        hibernateConfig.put("hibernate.c3p0.acquireRetryDelay", props.getProperty("hibernate.c3p0.acquireRetryDelay"));
        hibernateConfig.put("hibernate.c3p0.breakAfterAcquireFailure", props.getProperty("hibernate.c3p0.breakAfterAcquireFailure"));
        hibernateConfig.put("hibernate.connection.driver_class", props.getProperty("jdbc.driverClassName"));
        hibernateConfig.put("hibernate.connection.url", props.getProperty("jdbc.url"));
        hibernateConfig.put("hibernate.connection.username", props.getProperty("jdbc.username"));
        hibernateConfig.put("hibernate.connection.password", props.getProperty("jdbc.password"));
        hibernateConfig.put("hibernate.hbm2ddl.auto", props.getProperty("hibernate.hbm2ddl.auto"));

        entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                archiverPersistenceUnitInfo(),hibernateConfig);

        return entityManagerFactory;
    }

    private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return "ApplicationPersistenceUnit";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }
}
