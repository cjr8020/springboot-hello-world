package com.standard.demo.springboot.helloworld.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.core.style.ToStringCreator;

/**
 * Actor entity - represents application actor entity as it is persisted in the DB
 */
@Entity
@Table(name = "actor")
public class Actor {

  @Id
  @Column(name = "id")
  private Integer id;
  
  @Column(name = "username")
  private String username;
  
  @Column(name = "email")
  private String email;
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
      return new ToStringCreator(this)
              .append("id", id)
              .append("username", username)
              .append("email", email)
              .toString(); 
  }
}
