package com.standard.demo.springboot.helloworld.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class ApplicationConfiguration {

  /* =================  Enable Property Placeholders ==================== */

  /**
   * PropertySourcesPlaceholderConfigurer resolves ${...} placeholders within bean definition
   * property values.
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PropertySourcesPlaceholderConfigurer prop = new PropertySourcesPlaceholderConfigurer();
    prop.setIgnoreUnresolvablePlaceholders(true);
    return prop;
  }

  /* =================  Enable Encrypted Properties ==================== */

  @Value("${jasypt.encryptor.password}")
  private String jasyptEncryptorPassword;

  /**
   * Bean for decrypting encrypted password. Password encryption/decryption uses a master password
   * that is stored as environment variable.
   */
  @Bean(name = "jasyptStringEncryptor")
  public StringEncryptor stringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    EnvironmentPBEConfig config = new EnvironmentPBEConfig();
    encryptor.setConfig(config);
    config.setPassword(jasyptEncryptorPassword);
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    return encryptor;
  }

}
