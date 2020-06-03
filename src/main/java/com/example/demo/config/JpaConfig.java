package com.example.demo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("com.example.demo.repository")
public class JpaConfig {
    //@Bean // ставится над методом который выводит класс, объект этого класса создаётся и хранится в Spring Application
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        //dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://eu-cdbr-west-03.cleardb.net/heroku_bf2e67055ffabdc?reconnect=true");
        dataSourceBuilder.username("bb3444f3a67bf0");
        dataSourceBuilder.password("74b0a401");
        return dataSourceBuilder.build();
    }
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean() ;
        emfb.setDataSource(getDataSource());
        emfb.setPackagesToScan("com.example.demo.model");
        emfb.setJpaProperties(additionalProperties());
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() ;
        emfb.setJpaVendorAdapter(vendorAdapter);
        return emfb ;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager() ;
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager ;
    }

}
