package com.standard.demo.springboot.helloworld.domain;

public class Greeting {

  private String message;
  
  public Greeting() {
    
  }
  
  public Greeting(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

 
}
