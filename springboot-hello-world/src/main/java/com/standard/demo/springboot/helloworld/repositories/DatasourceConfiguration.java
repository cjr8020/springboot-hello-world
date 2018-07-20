package com.standard.demo.springboot.helloworld.repositories;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DatasourceConfiguration {

  /**
   * CONFIGURATION FOR H2 Embedded DataSource. Configuring the DML to create the table and data
   * used by this sample application.
   *
   * NOTE: EmbeddedDatabaseBuilder completely ignores whatever `spring.datasource.*` properties
   * you might have in any of your property files.
   *
   * @return DataSource data source
   */
  @Bean
  @Primary
  public DataSource dataSource() {
    return (new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .setName("DEMO-ONLY-DB;MODE=Oracle;MV_STORE=FALSE;MVCC=FALSE")
        .addScript("classpath:db/dropcreate.sql")
        .addScript("classpath:db/initdata.sql")
    ).build();
  }
}