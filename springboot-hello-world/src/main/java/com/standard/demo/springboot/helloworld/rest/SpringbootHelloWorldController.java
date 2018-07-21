package com.standard.demo.springboot.helloworld.rest;

import com.standard.demo.springboot.helloworld.domain.Actor;
import com.standard.demo.springboot.helloworld.domain.Greeting;
import com.standard.demo.springboot.helloworld.service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringbootHelloWorldController {

  private static final Logger logger
      = LoggerFactory.getLogger(SpringbootHelloWorldController.class);

  @Autowired
  private ActorService actorService;

  /**
   * Provides a list of actors.
   *
   * @return Iterable of Actor
   */
  @RequestMapping(value = "/listActors", method = RequestMethod.GET)
  public Iterable<Actor> listActors() {
    return actorService.listAllActors();
  }


  /**
   * Says "hello" in lowercase.
   *
   * @return Greeting
   */
  @RequestMapping("/hello")
  public Greeting sayHello() {
    return new Greeting("Hello there!");
  }


  /**
   * Says "hello" in caps.
   *
   * @return Greeting
   */
  @RequestMapping("/helloUpper")
  public Greeting sayHelloUpper() {
    return new Greeting("HELLO THERE!");
  }


}
