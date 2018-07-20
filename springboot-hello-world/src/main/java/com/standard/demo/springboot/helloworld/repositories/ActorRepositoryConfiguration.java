package com.standard.demo.springboot.helloworld.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Defines actor repository configuration.
 */
@Configuration
@EntityScan(basePackages = {"com.standard.demo.springboot"})
@EnableJpaRepositories(basePackages = {"com.standard.demo.springboot.helloworld.repositories"})
@EnableTransactionManagement
public class ActorRepositoryConfiguration extends RepositoryRestConfigurerAdapter {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

  }

}
