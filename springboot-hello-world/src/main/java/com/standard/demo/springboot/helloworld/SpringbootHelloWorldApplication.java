package com.standard.demo.springboot.helloworld;

import com.standard.sfg.audit.springboot.SfgAuditableFactory;
import com.standard.sfg.security.springboot.SfgJwtFactory;
import com.standard.sfg.security.springboot.SfgSecurityConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEncryptableProperties
@Import({
    SfgSecurityConfiguration.class,
    SfgJwtFactory.class,
    SfgAuditableFactory.class
})
public class SpringbootHelloWorldApplication {

  private static final Logger log = LoggerFactory.getLogger(SpringbootHelloWorldApplication.class);

  public static void main(String[] args) {
    log.info("SpringbootHelloWorldApplication::::Started Application");
    SpringApplication.run(SpringbootHelloWorldApplication.class, args);
  }
 
}
